package io.swaggy.swagger.customlib.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.springframework.web.bind.annotation.*;


import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerMethodOrderUtil {
    public static List<Method> getOrderedMethods(Class<?> controllerClass) {
        try {
            // ASM을 사용하여 클래스의 메서드 순서를 읽어옴
            ClassReader classReader = new ClassReader(controllerClass.getName());
            ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);

            // 원래 순서대로 메서드 이름 리스트를 생성
            List<String> methodNamesInOrder = classNode.methods.stream()
                    .map(methodNode -> methodNode.name)
                    .toList();

            // Reflection을 사용하여 메서드 리스트를 가져옴
            System.out.println(controllerClass.getName() + " " + controllerClass.getDeclaredMethods().length);
            Method[] methods = controllerClass.getDeclaredMethods();

            // 메서드를 원래 순서에 따라 정렬
            return Arrays.stream(methods)
                    .sorted((m1, m2) -> {
                        int index1 = methodNamesInOrder.indexOf(m1.getName());
                        int index2 = methodNamesInOrder.indexOf(m2.getName());
                        return Integer.compare(index1, index2);
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
//            throw new RuntimeException("Failed to read class with ASM", e);
        }
        return new ArrayList<>();
    }

    public static String[] getMethodPaths(Class<?> controllerClass, Method method) {
        RequestMapping classRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
        String[] classPaths = (classRequestMapping != null) ? classRequestMapping.value() : new String[]{""};

        String[] methodPaths = new String[0];
        if (method.isAnnotationPresent(GetMapping.class)) {
            methodPaths = method.getAnnotation(GetMapping.class).value();
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            methodPaths = method.getAnnotation(PostMapping.class).value();
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            methodPaths = method.getAnnotation(PutMapping.class).value();
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            methodPaths = method.getAnnotation(DeleteMapping.class).value();
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            methodPaths = method.getAnnotation(RequestMapping.class).value();
        }

        if (methodPaths.length == 0) {
            methodPaths = new String[]{""}; // 메서드 레벨 경로가 없을 경우 빈 문자열로 설정
        }

        String[] finalMethodPaths = methodPaths;
        return Arrays.stream(classPaths)
                .flatMap(classPath -> Arrays.stream(finalMethodPaths).map(methodPath -> classPath + methodPath))
                .toArray(String[]::new);
    }
}
