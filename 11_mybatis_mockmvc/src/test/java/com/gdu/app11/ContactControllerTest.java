package com.gdu.app11;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

// JUnit4
@RunWith(SpringJUnit4ClassRunner.class) // JUnit4를 사용하겠다고 지정

// 테스트에서 사용할 Bean이 @Component로 생성되었다
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml") // component를 찾아볼 위치 지정

// 메소드 이름 순으로 테스트를 수행한다. (알파벳 순서)
@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 테스트 순서 지정하는 MethodOrder 

// WebApplicationContext를 사용할 수 있다. (Spring Container에 Bean을 생성해 둔다.)
@WebAppConfiguration

// 로그
@Slf4j

public class ContactControllerTest {

  // MockMvc 객체가 생성될 때 필요한 WebApplicationContext
  @Autowired  // @WebAppConfiguration로 Bean생성이 이미 됐기 때문에 가져오기만 하면 됨
  private WebApplicationContext webApplicationContext;
  
  // MockMvc 객체 : 테스트를 수행하는 객체
  private MockMvc mockMvc;
  
  // 테스트 수행 이전에 MockMvc mockMvc 객체를 만든다.
  @Before  // aspect가 아닌 junit의 @Before임
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();  // Builder를 이용해서 mockMvc 테스트 수행 객체 만들기
  }
  
  // 삽입테스트
  // 요청 방식     : POST
  // 요청 주소     : /contact/add.do
  // 요청 파라미터 : name, tel, email, address
  @Test
  public void test01_삽입() throws Exception {
    log.info(mockMvc
              .perform(MockMvcRequestBuilders                 // 요청 수행
                         .post("/contact/add.do")             // 요청 방식, 요청 주소
                         .param("name", "뽀로로")             // 요청 파라미터
                         .param("tel", "02-1111-1111")
                         .param("email", "pororo@naver.com")
                         .param("address", "pororo villiage"))
              .andReturn()                                     // 요청 결과
              .getFlashMap()                                   // 요청 결과가 저장된 flash attribute를 Map으로 가져옴
              .toString());
  }
  
  
  // 상세조회테스트
  // 요청 방식     : GET
  // 요청 주소     : /contact/detail.do
  // 요청 파라미터 : contactNo
  @Test
  public void test02_상세조회() throws Exception {
    log.info(mockMvc
              .perform(MockMvcRequestBuilders
                        .get("/contact/detail.do")
                        .param("contactNo", "1"))
              .andReturn()
              .getModelAndView()
              .toString()); 
  }
  
  
  
  
  
  
  
  
  
  
}
