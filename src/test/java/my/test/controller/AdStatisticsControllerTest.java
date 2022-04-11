package com.project.fsn.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fsn.cauly.FsnApplication;
import com.fsn.cauly.adStatistics.controller.AdStatisticsController;
import com.fsn.cauly.adStatistics.service.AdStatisticsService;

/*
 * 통계 조회 기능, 통계 데이터 업로드 테스트
 */

/**
 * FSN 카울리 > 광고 플랫폼 Rest API > 통계 조회 기능, 통계 데이터 업로드 기능
 *
 * @author gtgyun
 * @since 2022.02.20
 */


@ContextConfiguration(classes = FsnApplication.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdStatisticsControllerTest {

	@InjectMocks    
	private AdStatisticsController adStatisticsController;    
	
	@Mock    
	private AdStatisticsService dStatisticsService;

	@Autowired
	private MockMvc mockMvc;    
	
	// 광고 통계 json 파일 경로 
    @Value("${custom.uploadfile.adStatisticsJson}")
	String filePath;

	@Test
	@DisplayName("TEST1 - AD Statistics By Date")
	public void test1_AdStatisticsByDate() {

		/*
		 * 해당 날짜 광고 요청수, 응답 수, 클릭 수 합계 테스트 
		 * 
		 * @테스트 Param
		 * 날짜 : 2022-02-16
		 * 
		 * @예상 응답
		 * 광고 요청 수 합계 : 51
		 * 광고 응답 수 합계 : 75
		 * 광고 클릭 수 합계 : 117
		 * 
		 */
		
		String req_cnt = "51";
		String res_cnt = "75";
		String ctr_cnt = "117";
		
		try {
			
			mockMvc.perform(
						get("/fsn-cauly/view/adStatisticsByDate")
							.param("coldate", "2022-02-16"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.req_cnt").value(req_cnt))
					.andExpect(jsonPath("$.res_cnt").value(res_cnt))
					.andExpect(jsonPath("$.ctr_cnt").value(ctr_cnt))
					.andDo(print());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	@DisplayName("TEST2 - AD Statistics By Hour")
	public void test2_AdStatisticsByHour() {
		
		/*
		 * 해당 날짜,시간에 광고 요청수, 응답 수, 클릭 수
		 * 
		 * @테스트 Param
		 * 날짜 : 2022-02-16, 시간 : 12
		 * 
		 * @예상 응답
		 * 광고 요청 수 : 13
		 * 광고 응답 수 : 24
		 * 광고 클릭 수 : 39
		 * 
		 */

		String req_cnt = "13";
		String res_cnt = "24";
		String ctr_cnt = "39";
		
		try {
			
			mockMvc.perform(
						get("/fsn-cauly/view/adStatisticsByHour")
							.param("coldate", "2022-02-16")
							.param("colhour", "12"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.req_cnt").value(req_cnt))
					.andExpect(jsonPath("$.res_cnt").value(res_cnt))
					.andExpect(jsonPath("$.ctr_cnt").value(ctr_cnt))
					.andDo(print());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    
	@Test
	@DisplayName("TEST3 - Upload AD Statistics Json File")
	public void test3_UploadAdStatisticsByHour() {

		/*
		 * 광고 통계 Json 파일 업로드  
		 * 
		 * @테스트 Param
		 * 광고 통계 Json 파일 (Multipartfile)
		 * 
		 * @예상 응답
		 * 200
		 * 
		 */
		
		 try {
			 
			 // 현재 작업 경로
			 String path = System.getProperty("user.dir"); 

			 // filePath : 광고 통계 json 파일 경로 (application.yml 참고)
			 File file = new File(path + filePath);
			 FileInputStream input = new FileInputStream(file);
			 MockMultipartFile multipartFile = new MockMultipartFile("uploadfile",
			             file.getName(), "application/json", IOUtils.toByteArray(input));
	        
	        this.mockMvc.perform(
	        		multipart("/fsn-cauly/upload/adStatisticsByHour")
	        		.file(multipartFile))
	        		.andDo(print())
	        		.andExpect(status().is(200));

		 } catch (Exception e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }
		
	 }

	@Test
	@DisplayName("TEST4 - AD Statistics By Date")
	public void test4_AdStatisticsByDate() {

		/*
		 * 해당 날짜 광고 요청수, 응답 수, 클릭 수 합계 테스트 
		 * 
		 * @테스트 Param
		 * 날짜 : 2022-02-16
		 * 
		 * @예상 응답
		 * 광고 요청 수 합계 : 54
		 * 광고 응답 수 합계 : 76
		 * 광고 클릭 수 합계 : 113
		 * 
		 */
		
		String req_cnt = "54";
		String res_cnt = "76";
		String ctr_cnt = "113";
		
		try {
			
			mockMvc.perform(
						get("/fsn-cauly/view/adStatisticsByDate")
							.param("coldate", "2022-02-16"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.req_cnt").value(req_cnt))
					.andExpect(jsonPath("$.res_cnt").value(res_cnt))
					.andExpect(jsonPath("$.ctr_cnt").value(ctr_cnt))
					.andDo(print());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
