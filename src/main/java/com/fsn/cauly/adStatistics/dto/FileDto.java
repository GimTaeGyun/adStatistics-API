package com.fsn.cauly.adStatistics.dto;

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
public class FileDto {
	private String uuid;	// unique 한 파일 이름을 만들기 위한 속성
    private String fileName;	// 실제 파일 이름
    private String contentType;
    
    public FileDto() {}

    public FileDto(String uuid, String fileName, String contentType) {
        this.uuid = uuid;
        this.fileName = fileName;
        this.contentType = contentType;
        System.out.println(contentType);
    }

	 	// setter/getter는 생략

}
