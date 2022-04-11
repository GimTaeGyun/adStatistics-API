package my.toyproject.adStatistics.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import my.toyproject.adStatistics.entity.AdStatistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 계층간 데이터 교환이나 View와 데이터를 교환할 때 사용하는 객체
 * 로직을 갖고 있지 않은 데이터 객체이며, getter/setter 메서드만을 갖는다
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdStatisticsDto {
	   
	   @NotNull
	   @Size(min = 0, max = 50)
	   private String coldate;
	   
	   private String colhour;
	   
	   private int req_cnt;
	   
	   private int res_cnt;
	   
	   private int ctr_cnt;
	   
	   public static AdStatisticsDto from(AdStatistics adStatistics) {
		   if(adStatistics == null) return null;
		   
		   return AdStatisticsDto.builder()
	    		  .coldate(adStatistics.getColdate())
	    		  .colhour(adStatistics.getColhour())
	    		  .req_cnt(adStatistics.getReq_cnt())
	    		  .res_cnt(adStatistics.getRes_cnt())
	    		  .ctr_cnt(adStatistics.getCtr_cnt())
	              .build();
	   }	   
	   
}