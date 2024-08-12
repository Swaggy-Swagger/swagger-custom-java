package com.test.swaggercustom.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class OpenApiSaver implements OpenApiCustomizer {

    private final ApplicationContext context;
    private final String filePath = "src/main/resources/openapi.json";

    public OpenApiSaver(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void customise(OpenAPI openApi) {
        saveApiSpecToFile(openApi);
    }

    private void saveApiSpecToFile(OpenAPI openAPI) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), openAPI);
            System.out.println("OpenAPI Snapshot saved in " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
