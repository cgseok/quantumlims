package lims.core.audit.service.impl;

import lims.core.audit.entity.RequestLogEntity;
import lims.core.audit.repository.RequestLogRepository;
import lims.core.audit.service.RequestLogService;
import lims.core.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestLogServiceImpl extends CommonService implements RequestLogService {

    @Autowired
    private RequestLogRepository repoRequestLog;


    @Override
    @Transactional
    public void requestLogRegist(RequestLogEntity mesRequestLog) {
        repoRequestLog.save(mesRequestLog);

    }
}
