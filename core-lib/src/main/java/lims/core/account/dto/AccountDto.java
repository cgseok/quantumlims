package lims.core.account.dto;

import lims.core.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto extends BaseDto {

    private String tkey;

    private String userId;

    private String userPassword;

    private String passwordChangeDate;

    private int licenseGubun;

    private String licenseGubunName;

    private String activeFlag;

    private String userName;

    private String deptCode;

    private String deptName;

    private String gradeCode;

    private String gradeName;

    private String email;

    private String note;

    private String AccessToken;
}
