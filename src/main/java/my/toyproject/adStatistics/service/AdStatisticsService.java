package my.toyproject.adStatistics.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import my.toyproject.adStatistics.dto.AdStatisticsDto;
import my.toyproject.adStatistics.entity.AdStatistics;
import my.toyproject.adStatistics.repository.AdStatisticsRepository;



@Service
public class AdStatisticsService {
	
    private final AdStatisticsRepository adStatisticsRepository;

    public AdStatisticsService(AdStatisticsRepository adStatisticsRepository) {
    	this.adStatisticsRepository = adStatisticsRepository;
    }

    public AdStatisticsDto getAdStatisticsByDate(String coldate) {
		
    	// 광고 요청 수
		int req_cnt = adStatisticsRepository.sumReqCntByColdate(coldate);
		// 광고 응답 수
		int res_cnt = adStatisticsRepository.sumResCntByColdate(coldate);
		// 광고 클릭 수
		int ctr_cnt = adStatisticsRepository.sumCtrCntByColdate(coldate);
		
		AdStatisticsDto adStatisticsDto = new AdStatisticsDto();
		
		// Dto 형태로 반환
		return adStatisticsDto.builder()
				.coldate(coldate)
				.ctr_cnt(ctr_cnt)
				.req_cnt(req_cnt)
				.res_cnt(res_cnt)
				.build();
		
	}

    public AdStatisticsDto getAdStatisticsByHour(String coldate, String colhour) {
		
    	// 날짜, 시간으로 광고 요청 수, 응답 수 , 클릭 수 조회
		AdStatistics adStatistics = adStatisticsRepository.findByColdateAndColhour(coldate, colhour);
		
		AdStatisticsDto adStatisticsDto = new AdStatisticsDto();
		
		return adStatisticsDto.from(adStatistics);
		
	}
    
    public void upsertAdStatisticsByHour(Object obj) {
    	
    	// json file 파싱
		JSONObject jsonObject = (JSONObject) obj;

		// json Array adStatistics parsing
		JSONArray arr = (JSONArray) jsonObject.get("adStatistics");

		for (int i = 0; i < arr.size(); i++) {
			
			JSONObject tmp = (JSONObject) arr.get(i);// 인덱스 번호로 접근해서 가져온다.

			String coldate = (String) tmp.get("coldate");
			String colhour = (String) tmp.get("colhour");
			int req_cnt = Integer.parseInt((String) tmp.get("req_cnt"));
			int res_cnt = Integer.parseInt((String) tmp.get("res_cnt"));
			int ctr_cnt = Integer.parseInt((String) tmp.get("ctr_cnt"));

			AdStatistics adStatistics = new AdStatistics();
			
			adStatistics = adStatisticsRepository.findByColdateAndColhour(coldate, colhour);
			
			if(adStatistics != null) {
				
				// 데이터 있으므로 update
				adStatistics.setReq_cnt(req_cnt);
				adStatistics.setRes_cnt(res_cnt);
				adStatistics.setCtr_cnt(ctr_cnt);
				
			} else {
				
				adStatistics = new AdStatistics();
				// 데이터 없으므로 insert
				adStatistics.setColdate(coldate);
				adStatistics.setColhour(colhour);
				adStatistics.setReq_cnt(req_cnt);
				adStatistics.setRes_cnt(res_cnt);
				adStatistics.setCtr_cnt(ctr_cnt);
				
			}
			
			// Insert or Update
			adStatisticsRepository.save(adStatistics);
			
		}
    }
}
