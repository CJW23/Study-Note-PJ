package com.cjw.study.restdocs.force_check;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

public class ClassReader {
    private static final String DIR = "user.dir";
    private static final String SLASH = "/";
    private static final String DOT = ".";
    private static final String EMPTY = "";
    private static final String ROOT_PACKAGE = "com.cjw.study";
    private static final String ABSOLUTE_MAIN_PACKAGE = "/src/main/java/com/cjw/study";
    private static final String ABSOLUTE_TEST_PACKAGE = "/src/test/java/com/cjw/study";
    private static final String JAVA_FILE = ".java";

    /**
     * 클래스 파일 추출
     * @param isTestPackage : test 패키지 여부
     * @param rootPkgNm     : 검색할 루트 패키지
     */
    public static List<? extends Class<?>> getClasses(boolean isTestPackage, String rootPkgNm) {
        if (rootPkgNm == null) {
            rootPkgNm = "";
        }
        //해당 프로젝트 절대 경로
        String property = System.getProperty(DIR);
        String absoluteUri = String.format("%s%s", property, !isTestPackage ? ABSOLUTE_MAIN_PACKAGE : ABSOLUTE_TEST_PACKAGE);

        //탐색할 루트 패키지
        File packageFolder = new File(String.format("%s%s", absoluteUri, !isEmpty(rootPkgNm) ? SLASH.concat(rootPkgNm) : EMPTY));
        if (packageFolder.list() == null) return List.of();

        //루트 패키지부터 패키지+클래스명 가져오기
        List<String> classNames = extractClassName(absoluteUri, rootPkgNm, Arrays.asList(requireNonNull(packageFolder.list())));
        if (isEmpty(classNames)) return List.of();

        //클래스 추출
        return classNames.stream()
                .map(c -> {
                    try {
                        return Class.forName(c);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * RestController Annotation 포함된 클래스의 메소드 탐색
     * @param rootPkgNm : 탐색할 루트 패키지명 (Ex: "controller")
     */
    public static List<Method> getRestControllerMethod(String rootPkgNm) {
        //@RestController Annotation 선언된 클래스 탐색
        List<Method> methods = extractMethod(getClassesFilteredAnnotation(false, rootPkgNm, RestController.class));
        //@GetMapping, @PostMapping... Annotation 작성된 메소드 추출
        return methods.stream().filter(m -> {
            Annotation[] declaredAnnotations = m.getDeclaredAnnotations();

            return Stream.of(declaredAnnotations).anyMatch(a ->
                    a.annotationType().equals(RequestMapping.class) ||
                            a.annotationType().equals(GetMapping.class) ||
                            a.annotationType().equals(PostMapping.class) ||
                            a.annotationType().equals(PatchMapping.class) ||
                            a.annotationType().equals(PutMapping.class) ||
                            a.annotationType().equals(DeleteMapping.class)
            );
        }).collect(Collectors.toList());
    }

    /**
     * 테스트 메소드 탐색
     * @param rootPkgNm : 탐색할 루트 패키지명 (Ex: "docs")
     */
    public static List<Method> getTestMethod(String rootPkgNm) {
        List<Method> methods = extractMethod(getClasses(true, rootPkgNm));
        return methods.stream().filter(m -> !isEmpty(m.getAnnotation(Test.class))).collect(Collectors.toList());
    }

    /**
     * 특정 어노테이션이 포함된 클래스 추출
     */
    public static List<? extends Class<?>> getClassesFilteredAnnotation(boolean isTestPackage, String rootPkgNm, Class annotationClass) {
        if (isEmpty(annotationClass)) {
            throw new RuntimeException("annotationClass cannot be null");
        }
        List<? extends Class<?>> classes = getClasses(isTestPackage, !isEmpty(rootPkgNm) ? rootPkgNm : "");
        return classes.stream()
                .filter(c -> !isEmpty(c.getAnnotation(annotationClass)))
                .collect(Collectors.toList());
    }

    /**
     * 클래스 추출 작업
     * 패키지 안에 패키지 존재시 재귀 탐색
     */
    private static List<String> extractClassName(String absoluteUri, String packagePath, List<String> fileNames) {
        List<String> rootClassList = new LinkedList<>();

        //패키지, 클래스 분리
        fileNames.forEach(p -> {
            //자바 파일인 경우
            if (p.contains(JAVA_FILE)) {
                rootClassList.add(String.format("%s%s%s%s",
                        ROOT_PACKAGE,
                        !isEmpty(packagePath) ? DOT.concat(packagePath.replace(SLASH, DOT)) : EMPTY,
                        DOT,
                        p.replace(JAVA_FILE, ""))
                );
            } else if (!p.contains(DOT)) {  //패키지인 경우
                File packageFolder = new File(String.format("%s%s%s%s%s",
                        absoluteUri,
                        SLASH,
                        packagePath.replace(DOT, SLASH),
                        SLASH,
                        p.replace(DOT, SLASH))
                );

                if (isEmpty(packageFolder.list())) return;

                //재귀
                rootClassList.addAll(extractClassName(
                        absoluteUri,
                        (!isEmpty(packagePath) ? packagePath.concat(DOT).concat(p) : p),
                        List.of(requireNonNull(packageFolder.list())))
                );
            }
        });
        return rootClassList;
    }

    /**
     * 클래스 메소드 추출
     */
    private static List<Method> extractMethod(List<? extends Class<?>> classes) {
        return classes.stream()
                .map(c -> List.of(c.getDeclaredMethods()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}