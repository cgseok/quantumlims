package lims.core.login.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lims.core.common.dto.QuantumResultDto;
import lims.core.login.dto.LoginRequestDto;
import lims.core.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value="Authentication")
@RestController
@RequestMapping(value = "/logins")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Value("${token.header}")
    private String tokenHeader;

    @ApiOperation(value="1.Login")
    @GetMapping("login")
    public QuantumResultDto Login(@RequestParam String userid,String userpassword){
        LoginRequestDto requestDto = new LoginRequestDto();
        requestDto.setLoginId(userid);
        requestDto.setLoginPassword(userpassword);
        return loginService.Login(requestDto);
    }

}
