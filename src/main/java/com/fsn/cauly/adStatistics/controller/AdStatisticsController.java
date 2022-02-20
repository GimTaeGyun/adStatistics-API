package com.fsn.cauly.adStatistics.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fsn.cauly.adStatistics.dto.AdStatisticsDto;
import com.fsn.cauly.adStatistics.dto.FileDto;
import com.fsn.cauly.adStatistics.service.AdStatisticsService;

/*
 * 통계 조회 기능, 통계 데이터 업로드 기능
 */

/**
 * FSN 카울리 > 광고 플랫폼 Rest API > 통계 조회 기능, 통계 데이터 업로드 기능
 *
 * @author gtgyun
 * @since 2022.02.16
 */

@RestController
@RequestMapping("/fsn-cauly")
public class AdStatisticsController {

	private final AdStatisticsService adStatisticsService;

	public AdStatisticsController(AdStatisticsService adStatisticsService) {
		this.adStatisticsService = adStatisticsService;
	}

	// 광고 통계 - 날짜로 합계 조회
	@GetMapping("/view/adStatisticsByDate")
	public ResponseEntity<AdStatisticsDto> viewAdStatisticsByDate(@RequestParam String coldate) {
		/*
		 * 1. DB 조회
		 * 2. 해당 날짜 요청 수 , 응답 수, 클릭 수 Sum
		 * 3. return req_cnt, res_cnt, ctr_cnt
		 */ 
				 
		return ResponseEntity.ok(adStatisticsService.getAdStatisticsByDate(coldate));
	}

	// 광고 통계 - 날짜, 시간으로 조회
	@GetMapping("/view/adStatisticsByHour")
	public ResponseEntity<AdStatisticsDto> viewAdStatisticsByHour(@RequestParam String coldate, String colhour) {

		/*
		 * 1. DB 조회
		 * 2. 해당 날짜, 시간의 요청 수 , 응답 수, 클릭 수 조회
		 * 3. return req_cnt, res_cnt, ctr_cnt
		 */
		
		return ResponseEntity.ok(adStatisticsService.getAdStatisticsByHour(coldate, colhour));
	}

	// application.properties에 등록된 파일의 경로를 가져온다.
	@Value("${spring.servlet.multipart.location}")
	String filePath;

	@PostMapping("/upload/adStatisticsByHour")
	// 업로드하는 파일들을 MultipartFile 형태의 파라미터로 전달된다.
	public HashMap<String, String> upload(@RequestParam MultipartFile uploadfile, Model model)
			throws IllegalStateException, IOException {
		
		FileDto dto = new FileDto(UUID.randomUUID().toString(), uploadfile.getOriginalFilename(),
				uploadfile.getContentType());

		// 저장 경로 : application.yml에 저장한 multipart : location
		File newFileName = new File(filePath + "/"+ dto.getUuid() + "_" + dto.getFileName());
		
		// 물리적 파일로 변환
		uploadfile.transferTo(newFileName);

		HashMap<String, String> response = new HashMap<>();
		try {

			JSONParser parser = new JSONParser();
			// mutipart location에 저장된 경로 + uuid_filename 으로 위에서 저장한 파일을 가져온다.
			Object obj = parser.parse(new FileReader(filePath + "/" + dto.getUuid() + "_" + dto.getFileName()));

			adStatisticsService.upsertAdStatisticsByHour(obj);
			
	        response.put("status", "S");
	        response.put("message", "success");
	        return response;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			response.put("status", "F");
	        response.put("message", e.getLocalizedMessage());
	        e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response.put("status", "F");
	        response.put("message", e.getLocalizedMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch 
			response.put("status", "F");
	        response.put("message", e.getLocalizedMessage());
			e.printStackTrace();
		}

		return response;
	}

}
