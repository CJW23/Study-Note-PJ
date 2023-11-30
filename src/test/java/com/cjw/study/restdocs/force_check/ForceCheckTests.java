package com.cjw.study.restdocs.force_check;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringBootTest
public class ForceCheckTests {

    @Test
    void 작성안된_테스트_검출() {
        AtomicBoolean isComplete = new AtomicBoolean(true);
        //@Test 어노테이션이 달린 메소드 추출
        List<Method> restdocs = ClassReader.getTestMethod("restdocs");

        //@RestController 어노테이션이 달린 클래스의 메소드들 추출
        List<Method> restControllerMethods = ClassReader.getRestControllerMethod("restdocs/controller");

        //작업된 API와 테스트 코드의 메소드명으로 해당 API의 테스트 코드가 작성됐는지 체크
        Optional.ofNullable(restControllerMethods).stream().flatMap(Collection::stream).forEach(rc -> {
            if(Optional.ofNullable(restdocs).stream()
                    .flatMap(Collection::stream)
                    .noneMatch(r -> r.getName().equals(rc.getName()))) {
                System.out.println("Method 작성 안됨: " + rc.getName());
                isComplete.set(false);
            }
        });

        Assertions.assertTrue(isComplete.get());
    }
}
