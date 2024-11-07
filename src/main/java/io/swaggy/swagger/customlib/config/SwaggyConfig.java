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
public class SwaggyConfig {

    private final ApplicationContext applicationContext;

    public SwaggyConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public OpenApiCustomizer sortOperationsCustomizer() {
        return openApi -> {
            Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
            List<String> sortedPathsOrder = new ArrayList<>();
            controllers.values().forEach(controller -> {
                Class<?> controllerClass = getOriginalClass(controller);
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

    private Class<?> getOriginalClass(Object controller) {
        Class<?> controllerClass = controller.getClass();
        if (controllerClass.getName().contains("$$")) {  // Spring 프록시 클래스 식별
            return controllerClass.getSuperclass();  // 실제 클래스 타입으로 변경
        }
        return controllerClass;
    }
}

