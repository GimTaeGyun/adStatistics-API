package my.toyproject.adStatistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import my.toyproject.adStatistics.entity.AdStatistics;


public interface AdStatisticsRepository extends JpaRepository<AdStatistics, Long> {
	
	// 광고 요청 수 Sum 쿼리
	@Query(value = 
			" SELECT SUM(req_cnt) AS req_cnt"+ 
			" FROM AdStatistics " + 
			" WHERE coldate = :col_date")
	int sumReqCntByColdate(@Param("col_date") String col_date);
	
	// 광고 응답 수 Sum 쿼리
	@Query(value = 
			" SELECT SUM(res_cnt) AS res_cnt"+ 
			" FROM AdStatistics " + 
			" WHERE coldate = :col_date")
	int sumResCntByColdate(@Param("col_date") String col_date);
	
	// 광고 클릭 수 Sum 쿼리
	@Query(value = 
			" SELECT SUM(ctr_cnt) AS ctr_cnt"+ 
			" FROM AdStatistics " + 
			" WHERE coldate = :col_date")
	int sumCtrCntByColdate(@Param("col_date") String col_date);
	
	// 조회된 결과중 첫번째만 return
	AdStatistics findByColdateAndColhour(String col_date, String col_hour);
	
}
