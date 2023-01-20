package lims.core.common.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(name="COMPANY_CODE",length = 4)
    private  String companycode;

    @Column(name = "CRTD_DATE", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "CRTD_USER_ID", nullable = false, updatable = false,length = 20)
    @CreatedBy
    private String createUserId;

    @Column(name = "CRTD_IP", nullable = false, updatable = false,length = 50)
    private String createIp;


    @Column(name = "UPD_DATE")
    @LastModifiedDate
    private LocalDateTime modifiyDate;

    @Column(name = "UPD_USER_ID",length = 20)
    @LastModifiedBy
    private String modifiyUserId;

    @Column(name = "UPD_IP",length = 50)
    private String modifiyIp;


    @Column(name = "DEL_FLAG", nullable = false,length = 1)
    private String delFlag;

}
