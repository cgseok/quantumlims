package lims.core.audit.service;

import lims.core.audit.entity.RequestLogEntity;

public interface RequestLogService {
    void requestLogRegist(RequestLogEntity requestLog);
}
