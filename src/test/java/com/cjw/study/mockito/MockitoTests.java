package com.cjw.study.mockito;

import com.cjw.study.mockito.service.MockitoSecondService;
import com.cjw.study.mockito.service.MockitoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class MockitoTests {

    /**
     * Mock 객체에 stubing 하지 않으면 반환형에 따라 기본값 출력
     * String: null
     * int, Integer: 0
     * List: size 0인 리스트
     */
    @Test
    void 기본값_테스트() {
        MockitoService mockitoService = Mockito.mock(MockitoService.class);
        assertNull(mockitoService.getString());
        assertEquals(0, mockitoService.getInt());
        assertEquals(0, mockitoService.getWrapperInteger());
        assertEquals(0, mockitoService.getList().size());
    }

    /**
     * doReturn():
     * - 메소드를 실제 호출하지 않으면서 리턴 값을 임의로 정의 할 수 있다.
     */
    @Test
    void doReturn_테스트() {
        MockitoService mockitoService = Mockito.mock(MockitoService.class);
        //원래 로그 출력후 "hello" 반환
        Mockito.doReturn("test").when(mockitoService).getPrintAndGet();
        assertEquals("test", mockitoService.getPrintAndGet());
    }

    /**
     * Spy: Mock과 다르게 stub 되지 않은 메소드는 기존 기능을 그대로 수행
     */
    @Test
    void Spy_테스트() {
        MockitoSecondService mockitoSecondService = Mockito.spy(MockitoSecondService.class);
        //getStr() 메소드의 결과값을 "test"로 반환
        Mockito.doReturn("test").when(mockitoSecondService).getStr();
        //기존 기능 수행
        mockitoSecondService.printStr();
        //stub 메소드 테스트
        assertEquals("test", mockitoSecondService.getStr());
    }

    /**
     * * @Spy, @Mock: Mockito.spy(), Mockito.mock()을 어노테이션으로 처리
     * * @InjectMocks: Mock객체 내부의 의존성들을 Mock객체로 채워줌
     * * -> EX) MockitoService 의존성인 MockitoSecondService를 Mock객체로 넣어줌 (MockitoSecondService Mock객체가 있어야함)
     * * -> @Mock 선언된 객체는 자동으로 채워주지만 @Spy 선언된 객체의 의존성들은 무조건 Mock객체가 만들어져있어야함 **
     */
    @Spy
    @InjectMocks
    MockitoService spyMockitoService;
    @Mock
    MockitoService mockMockitoService;
    @Mock
    MockitoSecondService mockitoSecondService;
    @Test
    void Mock_Spy_테스트() {
        //@Mock으로 선언된 객체는 다로 MockitoSecondService Mock 객체를 넣어주지 않아도 됨
        int anInt = mockMockitoService.getInt();
        assertEquals(0, anInt);
    }

    /**
     * thenReturn():
     * 메소드를 실제 호출하지만 리턴 값은 임의로 정의 할 수 있다.
     */
    @Test
    void thenReturn_테스트() {
        Mockito.when(spyMockitoService.getPrintAndGet()).thenReturn("test");
        //기존에 "hello" 출력후 "hello" 반환
        String printAndGet = spyMockitoService.getPrintAndGet();
        //로그 출력후 "test" 반환
        assertEquals("test", printAndGet);
    }

    /**
     * *@Spy Mock에서 의존성 Mock 사용
     */
    @Test
    void 의존성_Mock_테스트() {
        Mockito.doReturn("secondTest").when(mockitoSecondService).getStr();
        String secondStr = spyMockitoService.getSecondStr();
        assertEquals("secondTest", secondStr);
    }

    /**
     * doAnswer: void 메소드를 stub 할 때 사용
     * invocation을 통해 함수 호출시 받은 인자들을 가져올 수 있음
     */
    @Test
    void doAnswer_테스트() {
        Mockito.doAnswer(invocation -> {
            //메소드 첫번째 인자 값 가져오기
            String argument = invocation.getArgument(0, String.class);
            System.out.println("test: " + argument);
            return null;
        }).when(mockitoSecondService).printParam(Mockito.anyString());  //anyString: 아무 문자열을 받을 때 stub
        mockitoSecondService.printParam("do-answer");
    }

    /**
     * doNoting: void로 선언된 메소드에 when() 걸 때 사용(아무 동작 안함)
     * doAnswer에 return null만 한 경우랑 똑같지 않나
     */
    @Test
    void doNothing_테스트() {
        Mockito.doNothing()
                .when(mockitoSecondService).printParam(Mockito.any());
        //아무 동작 하지 않음
        mockitoSecondService.printParam("test");
    }

    /**
     * verify: 해당 메소드의 호출 관련 체크
     * 몇번 호출, 호출 안했는지, 최소 몇번 호출, 최대 몇번 호출, 특정 시간안에 호출
     */
    @Test
    void verify_테스트() {
        mockitoSecondService.printStr();
        //Mockito.verify(mockitoSecondService, Mockito.never()).printStr();
        Mockito.verify(mockitoSecondService, Mockito.times(1)).printStr();
        Mockito.verify(mockitoSecondService, Mockito.atLeast(0)).printStr();
        Mockito.verify(mockitoSecondService, Mockito.atMost(1)).printStr();
        Mockito.verify(mockitoSecondService, Mockito.timeout(100)).printStr();
    }
}
