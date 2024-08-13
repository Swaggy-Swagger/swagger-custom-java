package com.test.swaggercustom.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OpenApiChangeTracker implements OpenApiCustomizer {

    private final String changeLogPath = "src/main/resources/openapi_change_log.json";
    private final String newFilePath = "src/main/resources/new_openapi.json";
    private final String oldFilePath = "src/main/resources/old_openapi.json";;

    @Override
    public void customise(OpenAPI openApi) {
        saveAndTrackChanges(openApi);
    }

    private void saveAndTrackChanges(OpenAPI openAPI) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

            // 기존 스냅샷을 old 파일로 이동
            moveOldSnapshot(newFilePath, oldFilePath);

            // 새로운 스냅샷 저장
            objectMapper.writeValue(new File(newFilePath), openAPI);

            Map<String, Object> oldSpec = readSpecFromFile(objectMapper, oldFilePath);
            Map<String, Object> newSpec = readSpecFromFile(objectMapper, newFilePath);

            // 현재 버전
            String currentVersion = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

            List<Map<String, Object>> differences = findChangedEndpoints(oldSpec, newSpec, currentVersion);
            updateChangeLog(objectMapper, differences);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveOldSnapshot(String newFilePath, String oldFilePath) throws IOException {
        File newFile = new File(newFilePath);
        if (newFile.exists()) {
            Files.move(Paths.get(newFilePath), Paths.get(oldFilePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } else {
            // old 파일이 없을 경우 초기화
            new File(oldFilePath).createNewFile();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(oldFilePath), new HashMap<String, Object>());
        }
    }

    private Map<String, Object> readSpecFromFile(ObjectMapper objectMapper, String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), HashMap.class);
    }

    private void updateChangeLog(ObjectMapper objectMapper, List<Map<String, Object>> differences) throws IOException {
        Map<String, List<Map<String, Object>>> groupedByVersion = differences.stream()
                .collect(Collectors.groupingBy(difference -> difference.get("version").toString()));

        List<Map<String, Object>> groupedChanges = groupedByVersion.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> groupedEntry = new HashMap<>();
                    groupedEntry.put("version", entry.getKey());
                    groupedEntry.put("changes", entry.getValue());
                    return groupedEntry;
                }).toList();

        File changeLogFile = new File(changeLogPath);
        List<Map<String, Object>> existingChangeLog = new ArrayList<>();
        if (changeLogFile.exists()) {
            existingChangeLog = objectMapper.readValue(changeLogFile, ArrayList.class);
        }
        existingChangeLog.addAll(0, groupedChanges); // 최신 내용을 앞에 추가
        objectMapper.writeValue(changeLogFile, existingChangeLog);
    }

    private List<Map<String, Object>> findChangedEndpoints(Map<String, Object> oldSpec, Map<String, Object> newSpec, String currentVersion) {
        List<Map<String, Object>> changedEndpoints = new ArrayList<>();

        Map<String, Object> oldPaths = (Map<String, Object>) oldSpec.getOrDefault("paths", new HashMap<>());
        Map<String, Object> newPaths = (Map<String, Object>) newSpec.getOrDefault("paths", new HashMap<>());

        newPaths.forEach((path, newMethods) -> {
            Map<String, Object> oldMethods = (Map<String, Object>) oldPaths.get(path);

            ((Map<String, Object>) newMethods).forEach((method, newMethodDetails) -> {
                Object oldMethodDetails = (oldMethods != null) ? oldMethods.get(method) : null;

                String newSummary = (newMethodDetails != null) ? ((Map<String, Object>) newMethodDetails).get("summary").toString() : null;
                String oldSummary = (oldMethodDetails != null) ? ((Map<String, Object>) oldMethodDetails).get("summary").toString() : null;

                if (oldMethodDetails == null) {
                    changedEndpoints.add(createChangeLogEntry("added", path, method, newSummary, null, currentVersion));
                } else if (!newSummary.equals(oldSummary)) {
                    changedEndpoints.add(createChangeLogEntry("modified", path, method, newSummary, oldSummary, currentVersion));
                }
            });

            if (oldMethods != null) {
                oldMethods.forEach((method, oldMethodDetails) -> {
                    if (!((Map<String, Object>) newMethods).containsKey(method)) {
                        String oldSummary = (oldMethodDetails != null) ? ((Map<String, Object>) oldMethodDetails).get("summary").toString() : null;
                        changedEndpoints.add(createChangeLogEntry("removed", path, method, null, oldSummary, currentVersion));
                    }
                });
            }
        });

        oldPaths.keySet().stream()
                .filter(path -> !newPaths.containsKey(path))
                .forEach(path -> {
                    Map<String, Object> oldMethods = (Map<String, Object>) oldPaths.get(path);
                    oldMethods.forEach((method, oldMethodDetails) -> {
                        String oldSummary = (oldMethodDetails != null) ? ((Map<String, Object>) oldMethodDetails).get("summary").toString() : null;
                        changedEndpoints.add(createChangeLogEntry("removed", path, method, null, oldSummary, currentVersion));
                    });
                });

        return changedEndpoints;
    }

    private Map<String, Object> createChangeLogEntry(String changeType, String path, String method, String newSummary, String oldSummary, String version) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("changeType", changeType);
        logEntry.put("endpoint", path);
        logEntry.put("method", method);
        logEntry.put("version", version);

        if (newSummary != null) {
            logEntry.put("summary", newSummary);
        }
        if (oldSummary != null) {
            logEntry.put("oldSummary", oldSummary);
        }

        return logEntry;
    }
}
