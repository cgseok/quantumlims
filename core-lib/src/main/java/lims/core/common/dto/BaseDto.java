package lims.core.common.dto;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public class BaseDto {
    private  String companycode;

    @CreatedDate
    private LocalDateTime createDate;

    @CreatedBy
    private String createUserId;

    private String createIp;

    @LastModifiedDate
    private LocalDateTime modifiyDate;

    @LastModifiedBy
    private String modifiyUserId;

    private String modifiyIp;

    private String delFlag;
}
