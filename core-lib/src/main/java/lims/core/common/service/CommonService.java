package lims.core.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonService {
    protected final transient Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    protected HttpSession getSession() {
        return getHttpServletRequest().getSession();
    }
}
