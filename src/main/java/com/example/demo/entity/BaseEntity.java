package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass//jpa가 해당클래스는 테이블로 생성하지 않음
@EntityListeners(value = {AuditingEntityListener.class})//엔티티에 변화를 감지하는 리스터 저장
@Getter
public abstract class BaseEntity {
	
	@CreatedDate
	LocalDateTime regDate;
	
	@LastModifiedDate
	LocalDateTime modDate;
}
