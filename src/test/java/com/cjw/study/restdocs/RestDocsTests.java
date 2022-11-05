package com.cjw.study.restdocs;

import com.cjw.study.restdocs.config.RestDocsTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestDocsTests extends RestDocsTestSupport {

    /**
     * requestParameters: GET queryParam
     * parameterWithName 같이 사용
     *
     */
    @Test
    void test1() throws Exception {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("param1", "test");
        map.add("param2", "1234");
        map.add("param3", "2022-11-05 00:00:00");

        mockMvc.perform(get("/rest-docs/test1").params(map))
                .andExpect(status().isOk())
                .andDo(
                        restDocumentation.document(
                                requestParameters(
                                        parameterWithName("param1").description("param1 설명"),
                                        parameterWithName("param2").description("param2 설명"),
                                        parameterWithName("param3").description("param3 설명")
                                ),
                                responseFields(
                                        fieldWithPath("param1").description("param1 결과"),
                                        fieldWithPath("param2").description("param2 결과"),
                                        fieldWithPath("param3").description("param3 결과")
                                )
                        )
                );
    }

    /**
     * 기본적으로 Json 끝단 모든 필드에 대한 선언이 돼있어야함
     * Ex) test3.a b c, tests[] a,b,c, test4.test4test3 a,b,c
     *
     * subsectionWithPath() 사용하면 하위 속성 선언 필요X
     * Ex) subsectionWithPath("test3") 선언시 test3. a,b,c 선언 필요 X
     *
     * beneathPath()를 사용할 경우 입력한 속성의 하위 속성만 설명
     */
    @Test
    void test2() throws Exception {
        mockMvc.perform(get("/rest-docs/test2"))
                .andExpect(status().isOk())
                .andDo(
                        restDocumentation.document(
                                responseFields(
                                        beneathPath("test4"),
                                        fieldWithPath("test4test3").description("xcxcxcx"),
                                        fieldWithPath("test4test3.a").description("sss"),
                                        fieldWithPath("test4test3.b").description("ccc"),
                                        fieldWithPath("test4test3.c").description("xxx")
                                ),
                                //해당 부분만 responseBody로 따로 표현
                                responseBody(
                                        beneathPath("test3")
                                ),
                                responseFields(
                                        beneathPath("tests"),
                                        fieldWithPath("a").description("a"),
                                        fieldWithPath("b").description("b"),
                                        fieldWithPath("c").description("xcxc")),
                                responseFields(
                                        subsectionWithPath("test3").description("test3 subsection"),
                                        fieldWithPath("test4").description("zxc"),
                                        fieldWithPath("test4.test4test3.a").description("zxc"),
                                        fieldWithPath("test4.test4test3.b").description("zxc"),
                                        fieldWithPath("test4.test4test3.c").description("zxc"),
                                        fieldWithPath("tests[].a").description("zxc"),
                                        fieldWithPath("tests[].b").description("zxc"),
                                        fieldWithPath("tests[].c").description("zxc")
                                )
                                /*responseFields(
                                        subsectionWithPath("test3").description("test3 subsection"),
                                        fieldWithPath("test4.test4test3").description("zxc"),
                                        fieldWithPath("test4.test4test3.a").description("zxc"),
                                        fieldWithPath("test4.test4test3.b").description("zxc"),
                                        fieldWithPath("test4.test4test3.c").description("zxc"),
                                        fieldWithPath("test3.a").description("zxc"),
                                        fieldWithPath("test3.b").description("xcx"),
                                        fieldWithPath("test3.c").description("zzz"),
                                        fieldWithPath("tests[].a").description("zxc"),
                                        fieldWithPath("tests[].b").description("zxc"),
                                        fieldWithPath("tests[].c").description("zxc")
                                )*/
                        )
                );
    }
}
