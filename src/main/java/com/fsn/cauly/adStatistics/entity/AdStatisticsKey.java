package com.fsn.cauly.adStatistics.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Entity : 데이터베이스의 테이블과 1:1 매칭되는 객체
 * Getter,Setter,Builder,Constructor : lombok 관련 언어 (실무에선 사용 고려해야함.)
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdStatisticsKey implements Serializable {
	
	private String coldate;
	
	private String colhour;
			
}
