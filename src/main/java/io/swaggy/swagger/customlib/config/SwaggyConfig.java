package io.swaggy.swagger.customlib.config;

import io.swagger.v3.oas.models.tags.Tag;
import io.swaggy.swagger.customlib.properties.SwaggyProperties;
import io.swaggy.swagger.customlib.utils.ControllerMethodOrderUtil;
import io.swagger.v3.oas.models.Paths;
import io.swaggy.swagger.customlib.utils.TagsOrderUtil;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;
import java.util.*;

@Configuration
@EnableConfigurationProperties(SwaggyProperties.class)
public class SwaggyConfig {
    private final ApplicationContext applicationContext;

    private final SwaggyProperties swaggyProperties;

    public SwaggyConfig(ApplicationContext applicationContext, SwaggyProperties swaggyProperties) {
        this.applicationContext = applicationContext;
        this.swaggyProperties = swaggyProperties;
    }

    @Bean
    public OpenApiCustomizer sortCustomizer() {
        return openApi -> {
            /*
            sort operations customizer
             */
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

            /*
            sort tags customizer
             */
            List<Tag> tags = openApi.getTags();
            List<String> customTags = swaggyProperties.getTags();

            TagsOrderUtil.sortTagList(openApi, tags, customTags);
        };
    }

}

