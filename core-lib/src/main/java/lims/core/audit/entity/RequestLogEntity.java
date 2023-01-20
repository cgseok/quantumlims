package lims.core.audit.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="COMM_RELO")
@Data
@NoArgsConstructor
public class RequestLogEntity {

    @Id
    @Column(name="REQUEST_DT", length=100)
    private LocalDateTime requestDt;

    @Column(name="REQUEST_ID", length=100)
    private String requestId;

    @Column(name="CONTEXT_PATH", length=100)
    private String contextPath;

    @Column(name="REQUEST_URI", length=500)
    private String requestUri;

    @Column(name="START_TIME")
    private Long startTime;

    @Column(name="END_TIME")
    private Long endTime;

    @Column(name="EXECUTE_TIME")
    private Long executeTime;

    @Column(name="QUERY_STRING", length=100)
    private String queryString;

    @Column(name="CLIENT_IP", length=100)
    private String clientIp;

    @Column(name="SESSION_USID", length=100)
    private String sessionUsid;

    @Column(name="REGIST_DE", length=100)
    private String registDe;

    @Column(name="REGIST_DT", length=100)
    private String registDt;

    @Column(name="REGISTR_ID", length=100)
    private String registrId;

    @Column(name="MODIFY_DT", length=100)
    private String modifyDt;

    @Column(name="MODIFYR_ID", length=100)
    private String modifyrId;
}
