package com.doubledi.common.model.domain;

import com.doubledi.common.model.validator.ValidateConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import java.io.Serializable;
import java.time.Instant;

@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AuditableDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(name = "created_by", length = ValidateConstant.LENGTH.NAME_MAX_LENGTH, updatable = false)
    protected String createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt = Instant.now();

    @LastModifiedBy
    @Column(name = "last_modify_by", length = ValidateConstant.LENGTH.NAME_MAX_LENGTH)
    protected String lastModifyBy;

    @LastModifiedDate
    @Column(name = "last_modify_at", nullable = false)
    protected Instant lastModifyAt;
}
