package lims.core.audit.interceptor;

import lims.core.audit.entity.RequestLogEntity;
import lims.core.audit.service.RequestLogService;
import lims.core.common.security.JwtTokenUtil;
import lims.core.util.QuantumStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;


@Component
public class AuditRequestLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtTokenUtil HhpMesJwtTokenUtil;

    @Autowired
    private Environment env;

    @Value("${token.header}")
    private String tokenHeader;

    @Resource(name="auditRequestLogService")
    private RequestLogService auditRequestLogService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        request.setAttribute("startTime", System.currentTimeMillis());
        request.setAttribute("requestId", QuantumStringUtil.generateUUID());

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        long startTime = (Long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = (endTime - startTime);

        RequestLogEntity limsRequestLog = new RequestLogEntity();
        limsRequestLog.setRequestDt(LocalDateTime.now());
        limsRequestLog.setRequestId( (String) request.getAttribute("requestId"));
        limsRequestLog.setContextPath(StringUtils.replace(request.getContextPath(), "/", ""));
        limsRequestLog.setRequestUri( request.getRequestURI());
        limsRequestLog.setStartTime(startTime);
        limsRequestLog.setEndTime(endTime);
        limsRequestLog.setExecuteTime(executeTime);
        limsRequestLog.setQueryString(request.getQueryString());
        limsRequestLog.setClientIp(getClientIP(request));

        String token = request.getHeader(tokenHeader);


        if(StringUtils.isNotBlank(token)) {
            try {
                limsRequestLog.setSessionUsid(HhpMesJwtTokenUtil.getSubject(token));
            } catch(Exception e) {

            }
        }

        try {
            auditRequestLogService.requestLogRegist(limsRequestLog);
        } catch(Exception e) {

        }

    }

    private String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
