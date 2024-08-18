package io.swaggy.swagger.customlib.utils;

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

            List<Map<String, Object>> changedParameters = findChangedParameters(oldSpec, newSpec);
            updateChangeLog(objectMapper, changedParameters, currentVersion);

            List<Map<String, Object>> changedSchemas = findChangedSchemas(oldSpec, newSpec);
            updateChangeLog(objectMapper, changedSchemas, currentVersion);

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

    private List<Map<String, Object>> findChangedSchemas(Map<String, Object> oldSpec, Map<String, Object> newSpec) {
        List<Map<String, Object>> schemaChanges = new ArrayList<>();

        // Extract the schemas from old and new specs
        Map<String, Object> oldSchemas = (Map<String, Object>) oldSpec.get("components");
        oldSchemas = oldSchemas != null ? (Map<String, Object>) oldSchemas.get("schemas") : new HashMap<>();

        Map<String, Object> newSchemas = (Map<String, Object>) newSpec.get("components");
        newSchemas = newSchemas != null ? (Map<String, Object>) newSchemas.get("schemas") : new HashMap<>();

        // Iterate over the schemas in new spec
        Map<String, Object> finalOldSchemas = oldSchemas;

        newSchemas.forEach((schemaName, newSchema) -> {
            Map<String, Object> oldSchema = (Map<String, Object>) finalOldSchemas.get(schemaName);

            if (oldSchema == null) {
                // Schema added
                schemaChanges.add(createSchemaChangeLogEntry("schema_added", schemaName, (Map<String, Object>) newSchema, null));
            } else if (!oldSchema.equals(newSchema)) {
                // Schema modified
                schemaChanges.add(createSchemaChangeLogEntry("schema_modified", schemaName, (Map<String, Object>) newSchema, oldSchema));
            }
        });

        // Iterate over old schemas to find removed schemas
        Map<String, Object> finalNewSchemas = newSchemas;

        oldSchemas.forEach((schemaName, oldSchema) -> {
            if (!finalNewSchemas.containsKey(schemaName)) {
                schemaChanges.add(createSchemaChangeLogEntry("schema_removed", schemaName, null, (Map<String, Object>) oldSchema));
            }
        });

        return schemaChanges;
    }

    private Map<String, Object> createSchemaChangeLogEntry(String changeType, String schemaName, Map<String, Object> newSchema, Map<String, Object> oldSchema) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("changeType", changeType);
        logEntry.put("schemaName", schemaName);

        if (newSchema != null) {
            logEntry.put("newSchema", newSchema);
        }
        if (oldSchema != null) {
            logEntry.put("oldSchema", oldSchema);
        }

        return logEntry;
    }


    private List<Map<String, Object>> findChangedParameters(Map<String, Object> oldSpec, Map<String, Object> newSpec) {
        List<Map<String, Object>> changedParameters = new ArrayList<>();

        // Get paths from both old and new specs
        Map<String, Object> oldPaths = (Map<String, Object>) oldSpec.getOrDefault("paths", new HashMap<>());
        Map<String, Object> newPaths = (Map<String, Object>) newSpec.getOrDefault("paths", new HashMap<>());

        // Iterate over new paths to detect added or modified parameters
        newPaths.forEach((path, newMethods) -> {
            Map<String, Object> oldMethods = (Map<String, Object>) oldPaths.get(path);

            ((Map<String, Object>) newMethods).forEach((method, newMethodDetails) -> {
                Object oldMethodDetails = (oldMethods != null) ? oldMethods.get(method) : null;

                // Skip comparison if oldMethodDetails is null
                if (oldMethodDetails == null) {
                    return;
                }

                List<Map<String, Object>> newParameters = (List<Map<String, Object>>) ((Map<String, Object>) newMethodDetails).get("parameters");
                List<Map<String, Object>> oldParameters = (List<Map<String, Object>>) ((Map<String, Object>) oldMethodDetails).get("parameters");

                if (oldParameters == null && newParameters != null) {
                    // Parameters added
                    changedParameters.add(createParameterChangeLogEntry("parameters_added", path, method, newParameters, null));
                } else if (oldParameters != null && newParameters == null) {
                    // Parameters removed
                    changedParameters.add(createParameterChangeLogEntry("parameters_removed", path, method, null, oldParameters));
                } else if (oldParameters != null && newParameters != null && !oldParameters.equals(newParameters)) {
                    // Parameters modified
                    changedParameters.add(createParameterChangeLogEntry("parameters_modified", path, method, newParameters, oldParameters));
                }
            });
        });

        // Iterate over old paths to detect removed parameters
        oldPaths.forEach((path, oldMethods) -> {
            if (!newPaths.containsKey(path)) {
                ((Map<String, Object>) oldMethods).forEach((method, oldMethodDetails) -> {
                    List<Map<String, Object>> oldParameters = (List<Map<String, Object>>) ((Map<String, Object>) oldMethodDetails).get("parameters");
                    if (oldParameters != null) {
                        // Parameters removed because the entire endpoint was removed
                        changedParameters.add(createParameterChangeLogEntry("parameters_removed", path, method, null, oldParameters));
                    }
                });
            }
        });

        return changedParameters;
    }

    private Map<String, Object> createParameterChangeLogEntry(String changeType, String path, String method, List<Map<String, Object>> newParameters, List<Map<String, Object>> oldParameters) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("changeType", changeType);
        logEntry.put("endpoint", path);
        logEntry.put("method", method);

        if (newParameters != null) {
            logEntry.put("newParameters", newParameters);
        }
        if (oldParameters != null) {
            logEntry.put("oldParameters", oldParameters);
        }

        return logEntry;
    }
}
