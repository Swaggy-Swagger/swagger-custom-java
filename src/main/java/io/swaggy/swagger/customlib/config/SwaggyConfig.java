package io.swaggy.swagger.customlib.config;

import io.swaggy.swagger.customlib.utils.ControllerMethodOrderUtil;
import io.swagger.v3.oas.models.Paths;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Configuration
public class SwaggerConfig {

    private final ApplicationContext applicationContext;

    public SwaggerConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public OpenApiCustomizer sortOperationsCustomizer() {
        return openApi -> {
            Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
            List<String> sortedPathsOrder = new ArrayList<>();
            controllers.values().forEach(controller -> {
                Class<?> controllerClass = controller.getClass();
                List<Method> orderedMethods = ControllerMethodOrderUtil.getOrderedMethods(controllerClass);
                orderedMethods.forEach(method -> {
                    String[] paths = ControllerMethodOrderUtil.getMethodPaths(controllerClass, method);
                    for (String path : paths) {
                        if (!sortedPathsOrder.contains(path)) {
                            sortedPathsOrder.add(path);
                        }
                    }
                });
            });

            Paths sortedPaths = new Paths();
            sortedPathsOrder.forEach(path -> {
                if (openApi.getPaths().get(path) != null) {
                    sortedPaths.addPathItem(path, openApi.getPaths().get(path));
                }
            });
            openApi.setPaths(sortedPaths);
        };
    }
}

