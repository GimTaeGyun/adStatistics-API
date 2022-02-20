package com.project.fsn;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

 
@SpringBootTest
class FsnApplicationTests {
	
	

//	@Autowired 
//	private MockMvc mvc;
//
//	
	@Test
	void contextLoads() {
	}
	
//	@Test public void indexTest() throws Exception { 
//		mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON)) 
//		// "/" 에 대한 request 요청 
//		.andExpect(status().isOk()) // response 가 200 ok 일때 
//		.andExpect((ResultMatcher) content().string(equalTo("Greetings from Spring Boot!"))); 
//		// response로 받은 데이터가 같은지 확인 
//	}
//
//		
//	
//	@Test public void helloTest() throws Exception { 
//		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON)) 
//		// "/hello" 에 대한 request 요청 
//		.andExpect(status().isOk()) 
//		// response 가 200 ok 일때
//		.andExpect((ResultMatcher) content().string(equalTo("Hello world"))); 
//		// response로 받은 데이터가 같은지 확인 
//	}
}

