package io.swaggy.swagger.customlib.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.models.OpenAPI;
import io.swaggy.swagger.customlib.model.ChangesInPaths;
import io.swaggy.swagger.customlib.model.MethodPath;
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

    private Map<String, List<MethodPath>> schemaPaths = new HashMap<>();
    private List<Map<String, Object>> changedSchemas;
    private ChangesInPaths changesInPaths;

    @Override
    public void customise(OpenAPI openApi) {
        saveAndTrackChanges(openApi);
        addCustomFieldsToOpenApi(openApi);
    }

    private boolean checkIfChanged(String path, String httpMethod, ChangesInPaths changesInPaths) {
        // check changes in endpoints
        boolean endpointChanged = changesInPaths.getChangedEndpoints().stream()
                .anyMatch(endpoint ->
                        endpoint.get("endpoint").equals(path) &&
                                endpoint.get("method").toString().equalsIgnoreCase(httpMethod)
                );

        // check changes in parameters
        boolean parameterChanged = changesInPaths.getChangedParameters().stream()
                .anyMatch(param ->
                        param.get("endpoint").equals(path) &&
                                param.get("method").toString().equalsIgnoreCase(httpMethod)
                );

        // check changes in schemas
        boolean schemaChanged = changedSchemas.stream()
                .anyMatch(schema -> {
                    List<Map<String, Object>> pathInfoList = (List<Map<String, Object>>) schema.get("pathInfo");
                    return pathInfoList.stream()
                            .anyMatch(pathInfo ->
                                    pathInfo.get("path").equals(path) &&
                                            pathInfo.get("method").toString().equalsIgnoreCase(httpMethod)
                            );
                });

        return endpointChanged || parameterChanged || schemaChanged;
    }

    private void addCustomFieldsToOpenApi(OpenAPI openApi) {
        openApi.getPaths().forEach((path, pathItem) -> {
            pathItem.readOperationsMap().forEach((httpMethod, operation) -> {
                String method = httpMethod.toString();
                boolean isChanged = checkIfChanged(path, method, changesInPaths);

                // add custom field to operation
                Map<String, Object> extensions = operation.getExtensions();
                if (extensions == null) {
                    extensions = new HashMap<>();
                    operation.setExtensions(extensions);
                }
                extensions.put("isChanged", isChanged);
            });
        });
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

            this.changesInPaths = findChangesInPaths(oldSpec, newSpec);
            this.changedSchemas = findChangedSchemas(oldSpec, newSpec);

            updateChangeLog(objectMapper, changesInPaths.getChangedEndpoints(), changesInPaths.getChangedParameters(), changedSchemas, currentVersion);

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

    private void updateChangeLog(ObjectMapper objectMapper,
                                 List<Map<String, Object>> changedEndpoints,
                                 List<Map<String, Object>> changedParameters,
                                 List<Map<String, Object>> changedSchemas,
                                 String currentVersion) throws IOException {
        File changeLogFile = new File(changeLogPath);
        List<Map<String, Object>> existingChangeLog = new ArrayList<>();
        if (changeLogFile.exists()) {
            existingChangeLog = objectMapper.readValue(changeLogFile, ArrayList.class);
        }

        Map<String, Object> newLogEntry = new HashMap<>();
        boolean flag = false;

        if (!changedEndpoints.isEmpty()) {
            newLogEntry.put("endpointChanges", changedEndpoints);
            flag = true;
        }
        if (!changedParameters.isEmpty()) {
            newLogEntry.put("parameterChanges", changedParameters);
            flag = true;
        }
        if (!changedSchemas.isEmpty()) {
            newLogEntry.put("schemaChanges", changedSchemas);
            flag = true;
        }

        if (flag) {
            newLogEntry.put("version", currentVersion);

            // change log
            existingChangeLog.add(0, newLogEntry);
            saveToFile(objectMapper, changeLogPath, existingChangeLog);
        }

    }

    private ChangesInPaths findChangesInPaths(Map<String, Object> oldSpec, Map<String, Object> newSpec) {
        List<Map<String, Object>> changedEndpoints = new ArrayList<>();
        List<Map<String, Object>> changedParameters = new ArrayList<>();

        Map<String, Object> oldPaths = (Map<String, Object>) oldSpec.getOrDefault("paths", new HashMap<>());
        Map<String, Object> newPaths = (Map<String, Object>) newSpec.getOrDefault("paths", new HashMap<>());

        newPaths.forEach((path, newMethods) -> {
            Map<String, Object> oldMethods = (Map<String, Object>) oldPaths.get(path);

            ((Map<String, Object>) newMethods).forEach((method, newMethodDetails) -> {
                Object oldMethodDetails = (oldMethods != null) ? oldMethods.get(method) : null;


                /*
                find differences in endpoints
                 */
                String newSummary = (newMethodDetails != null) ? ((Map<String, Object>) newMethodDetails).get("summary").toString() : null;
                String oldSummary = (oldMethodDetails != null) ? ((Map<String, Object>) oldMethodDetails).get("summary").toString() : null;

                if (oldMethodDetails == null) {
                    changedEndpoints.add(createEndPointChangeLogEntry("endpointAdded", path, method, newSummary, oldSummary));
                } else if (!newSummary.equals(oldSummary)) {
                    changedEndpoints.add(createEndPointChangeLogEntry("endpointModified", path, method, newSummary, oldSummary));
                }


                /*
                Update refPaths
                */
                updateRefPaths(path, method, (Map<String, Object>) newMethodDetails);


                /*
                find differences in parameters
                 */
                // Skip comparison if oldMethodDetails is null
                if (oldMethodDetails == null) {
                    return;
                }

                List<Map<String, Object>> newParameters = (List<Map<String, Object>>) ((Map<String, Object>) newMethodDetails).get("parameters");
                List<Map<String, Object>> oldParameters = (List<Map<String, Object>>) ((Map<String, Object>) oldMethodDetails).get("parameters");

                if (oldParameters == null && newParameters != null) {
                    // Parameters added
                    changedParameters.add(createParameterChangeLogEntry("parametersAdded", path, method));
                } else if (oldParameters != null && newParameters == null) {
                    // Parameters removed
                    changedParameters.add(createParameterChangeLogEntry("parametersRemoved", path, method));
                } else if (oldParameters != null && newParameters != null && !oldParameters.equals(newParameters)) {
                    // Parameters modified
                    changedParameters.add(createParameterChangeLogEntry("parametersModified", path, method));
                }
            });

            if (oldMethods != null) {
                oldMethods.forEach((method, oldMethodDetails) -> {
                    if (!((Map<String, Object>) newMethods).containsKey(method)) {
                        String oldSummary = (oldMethodDetails != null) ? ((Map<String, Object>) oldMethodDetails).get("summary").toString() : null;
                        changedEndpoints.add(createEndPointChangeLogEntry("endpointRemoved", path, method, null, oldSummary));
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
                        changedEndpoints.add(createEndPointChangeLogEntry("endpointRemoved", path, method, null, oldSummary));
                    });
                });

        return new ChangesInPaths(changedEndpoints, changedParameters);
    }

    private void updateRefPaths(String path, String method, Map<String, Object> methodDetails) {
        // Extract and process $ref from requestBody
        String requestBodyRef = extractRefFromRequestBody(methodDetails);
        if (requestBodyRef != null) {
            addToRefPaths(requestBodyRef, path, method);
        }

        // Extract and process $ref from responses
        String responseBodyRef = extractRefFromResponses(methodDetails);
        if (responseBodyRef != null) {
            addToRefPaths(responseBodyRef, path, method);
        }
    }

    private String extractRefFromRequestBody(Map<String, Object> methodDetails) {
        Map<String, Object> requestBody = (Map<String, Object>) methodDetails.get("requestBody");
        if (requestBody != null) {
            Map<String, Object> content = (Map<String, Object>) requestBody.get("content");
            if (content != null) {
                Map<String, Object> jsonContent = (Map<String, Object>) content.get("application/json");
                if (jsonContent != null) {
                    Map<String, Object> schema = (Map<String, Object>) jsonContent.get("schema");
                    if (schema != null) {
                        String[] ref = schema.get("$ref").toString().split("/");
                        return ref[ref.length - 1];
                    }
                }
            }
        }
        return null;
    }

    private String extractRefFromResponses(Map<String, Object> methodDetails) {
        Map<String, Object> responses = (Map<String, Object>) methodDetails.get("responses");
        if (responses != null) {
            for (Map.Entry<String, Object> responseEntry : responses.entrySet()) {
                Map<String, Object> responseContent = (Map<String, Object>) responseEntry.getValue();
                Map<String, Object> content = (Map<String, Object>) responseContent.get("content");
                if (content != null) {
                    Map<String, Object> jsonContent = (Map<String, Object>) content.get("*/*");
                    if (jsonContent != null) {
                        Map<String, Object> schema = (Map<String, Object>) jsonContent.get("schema");
                        if (schema != null) {
                            Object $ref = schema.get("$ref");
                            if ($ref != null) {
                                String[] ref = $ref.toString().split("/");
                                return ref[ref.length - 1];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private void addToRefPaths(String ref, String method, String path) {
        MethodPath methodPath = new MethodPath(method, path);

        // Check if refPaths already contains the ref as a key
        if (schemaPaths.containsKey(ref)) {
            // If it does, add the MethodPath to the list
            schemaPaths.get(ref).add(methodPath);
        } else {
            // If it doesn't, create a new list and add the MethodPath
            List<MethodPath> methodPathList = new ArrayList<>();
            methodPathList.add(methodPath);
            schemaPaths.put(ref, methodPathList);
        }
    }

    private Map<String, Object> createEndPointChangeLogEntry(String changeType, String path, String method, String newSummary, String oldSummary) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("changeType", changeType);
        logEntry.put("endpoint", path);
        logEntry.put("method", method);

        if (newSummary != null) {
            logEntry.put("newSummary", newSummary);
        }
        if (oldSummary != null) {
            logEntry.put("oldSummary", oldSummary);
        }

        return logEntry;
    }

    private Map<String, Object> createParameterChangeLogEntry(String changeType, String path, String method) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("changeType", changeType);
        logEntry.put("endpoint", path);
        logEntry.put("method", method);

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

            List<MethodPath> schemaMethodPaths = schemaPaths.get(schemaName);

            if (oldSchema == null) {
                schemaChanges.add(createSchemaChangeLogEntry("schemaAdded", schemaMethodPaths, schemaName));
            } else if (!oldSchema.equals(newSchema)) {
                schemaChanges.add(createSchemaChangeLogEntry("schemaModified", schemaMethodPaths, schemaName));
            }
        });

        // Iterate over old schemas to find removed schemas
        Map<String, Object> finalNewSchemas = newSchemas;

        oldSchemas.forEach((schemaName, oldSchema) -> {
            if (!finalNewSchemas.containsKey(schemaName)) {
                schemaChanges.add(createSchemaChangeLogEntry("schemaRemoved", null, schemaName));
            }
        });

        return schemaChanges;
    }

    private Map<String, Object> createSchemaChangeLogEntry(String changeType, List<MethodPath> schemaMethodPaths, String schemaName) {
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("changeType", changeType);
        logEntry.put("schemaName", schemaName);

        if (schemaMethodPaths != null) {
            List<Map<String, Object>> pathInfoList = new ArrayList<>();
            for (MethodPath methodPath : schemaMethodPaths) {
                Map<String, Object> pathInfo = new HashMap<>();
                pathInfo.put("path", methodPath.getPath());
                pathInfo.put("method", methodPath.getHttpMethod());
                pathInfoList.add(pathInfo);
            }
            logEntry.put("pathInfo", pathInfoList);
        }

        return logEntry;
    }

}
