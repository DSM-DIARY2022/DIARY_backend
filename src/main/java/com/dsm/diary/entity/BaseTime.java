package com.dsm.diary.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

    // 생성한 날짜
    @CreatedDate
    private LocalDate createdDate;

    // 수정한 날짜
    @LastModifiedDate
    private LocalDate modifiedDate;

}
