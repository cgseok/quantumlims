package lims.core.login.interceptor;

import lims.core.account.entity.AccountEntity;
import lims.core.common.dto.SessionDto;
import lims.core.common.entity.AccessTokenEntity;
import lims.core.common.exceiption.AuthorizedException;
import lims.core.common.message.QuantumMessageUtil;
import lims.core.common.security.JwtTokenUtil;
import lims.core.login.repository.LoginRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginSessionCheckInterceptor implements HandlerInterceptor {

    protected final transient Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionDto hhpSessionDto;

    @Autowired
    private JwtTokenUtil HhpMesJwtTokenUtil;

    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    private LoginRepository repo;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(StringUtils.equals("OPTIONS", request.getMethod())) {
            return true;
        }

        LOG.info("session check interceptor...........");

        String token = request.getHeader(tokenHeader);
        LOG.info("token : " + token);

        /*
         * if(StringUtils.isBlank(token)) { throw new HhpAuthorizedException("ERR.502",
         * HhpMessageUtil.getMessage("ERR.502")); }
         */

//		String loginId = HhpMesJwtTokenUtil.getSubject(token);
        //String loginId = "Lee";
        /* accessTokenLoginValid(loginId, token); */

//		hhpSessionDto.setRequestId(loginId);

        return true;
    }

    public void accessTokenLoginValid(String loginId, String token) {
        AccountEntity userLogin = repo.findByUserId(loginId);
        if(userLogin == null) {
            throw new AuthorizedException("ERR.514", QuantumMessageUtil.getMessage("ERR.514"));
        }
        AccessTokenEntity accessToken = new AccessTokenEntity();
        if(!StringUtils.equalsIgnoreCase(token, accessToken.getAccessToken())) {
            throw new AuthorizedException("ERR.514", QuantumMessageUtil.getMessage("ERR.514"));
        }

    }
}
