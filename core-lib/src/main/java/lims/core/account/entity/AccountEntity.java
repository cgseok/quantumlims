package lims.core.account.entity;


import lims.core.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADM_USER")
//@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountEntity extends BaseEntity {

    @Id
    @Column(name="TKEY")
    private String tkey;

    @Column(name="USER_ID", length=20,nullable = false)
    private String userId;

    @Column(name="USER_PASSWORD", length=400)
    private String userPassword;

    @Column(name="PASSWORD_CHANGE_DATE", length=8)
    private String passwordChangeDate;

    @Column(name="LICENSE_GUBUN", length=1)
    private int licenseGubun;

    @Column(name="LICENSE_GUBUN_NAME", length=200)
    private String licenseGubunName;


    @Column(name="ACTIVE_FLAG", length=1)
    private String activeFlag;


    @Column(name="USER_NAME", length = 50)
    private String userName;

    @Column(name="DEPT_CODE", length = 20)
    private String deptCode;

    @Column(name="DEPT_NAME", length = 200)
    private String deptName;

    @Column(name="GRADE_CODE", length=20)
    private String gradeCode;

    @Column(name="GRADE_NAME", length=200)
    private String gradeName;

    @Column(name="EMAIL", length=200)
    private String email;

    @Column(name="NOTE", length=4000)
    private String note;


}
