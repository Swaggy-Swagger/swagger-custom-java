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

@Component
public class OpenApiChangeTracker implements OpenApiCustomizer {

    private final String changeLogPath = "./logs/openapi_change_log.json";
    private final String newFilePath = "./logs/new_openapi.json";
    private final String oldFilePath = "./logs/old_openapi.json";

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

            // current version
            String currentVersion = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());

            List<Map<String, Object>> differences = findChangedEndpoints(oldSpec, newSpec);
            updateChangeLog(objectMapper, differences, currentVersion);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveOldSnapshot(String newFilePath, String oldFilePath) throws IOException {
        File newFile = new File(newFilePath);
        if (newFile.exists()) {
            File oldFile = new File(oldFilePath);
            oldFile.getParentFile().mkdirs();
            Files.move(Paths.get(newFilePath), Paths.get(oldFilePath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } else {
            saveToFile(new ObjectMapper(), oldFilePath, new HashMap<String, Object>());
        }
    }

    private Map<String, Object> readSpecFromFile(ObjectMapper objectMapper, String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), HashMap.class);
    }

    private void saveToFile(ObjectMapper objectMapper, String filePath, Object data) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // 디렉토리 생성
        }
        objectMapper.writeValue(file, data);
    }

    private void updateChangeLog(ObjectMapper objectMapper, List<Map<String, Object>> differences, String currentVersion) throws IOException {
        File changeLogFile = new File(changeLogPath);
        List<Map<String, Object>> existingChangeLog = new ArrayList<>();
        if (changeLogFile.exists()) {
            existingChangeLog = objectMapper.readValue(changeLogFile, ArrayList.class);
        }

        Map<String, Object> newLogEntry = new HashMap<>();
        newLogEntry.put("version", currentVersion);
        newLogEntry.put("changes", differences);

        // change log
        existingChangeLog.add(0, newLogEntry);
        saveToFile(objectMapper, changeLogPath, existingChangeLog);
    }

    private List<Map<String, Object>> findChangedEndpoints(Map<String, Object> oldSpec, Map<String, Object> newSpec) {
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
                    changedEndpoints.add(createChangeLogEntry("added", path, method, newSummary, null));
                } else if (!newSummary.equals(oldSummary)) {
                    changedEndpoints.add(createChangeLogEntry("modified", path, method, newSummary, oldSummary));
                }
            });

            if (oldMethods != null) {
                oldMethods.forEach((method, oldMethodDetails) -> {
                    if (!((Map<String, Object>) newMethods).containsKey(method)) {
                        String oldSummary = (oldMethodDetails != null) ? ((Map<String, Object>) oldMethodDetails).get("summary").toString() : null;
                        changedEndpoints.add(createChangeLogEntry("removed", path, method, null, oldSummary));
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
                        changedEndpoints.add(createChangeLogEntry("removed", path, method, null, oldSummary));
                    });
                });

        return changedEndpoints;
    }

    private Map<String, Object> createChangeLogEntry(String changeType, String path, String method, String newSummary, String oldSummary) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("changeType", changeType);
        logEntry.put("endpoint", path);
        logEntry.put("method", method);

        if (newSummary != null) {
            logEntry.put("summary", newSummary);
        }
        if (oldSummary != null) {
            logEntry.put("oldSummary", oldSummary);
        }

        return logEntry;
    }
}
