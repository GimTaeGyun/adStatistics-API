package com.fsn.cauly.adStatistics.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Entity : 데이터베이스의 테이블과 1:1 매칭되는 객체
 * Getter,Setter,Builder,Constructor : lombok 관련 언어 (실무에선 사용 고려해야함.)
 */
@Entity
@Table(name = "ad_statistics")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(AdStatisticsKey.class)
public class AdStatistics implements Serializable {
	
	@JsonIgnore
	@Id
	@Column(name = "col_date")
	private String coldate;
	
	@Id
	@Column(name = "col_hour")
	private String colhour;
	
	@Column(name = "req_cnt")
	private int req_cnt;
	
	@Column(name = "res_cnt")
	private int res_cnt;
	
	@Column(name = "ctr_cnt")
	private int ctr_cnt;
			
}
