package lims.core.login.util;

import lims.core.account.dto.AccountDto;
import lims.core.common.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginUtil {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    public void loginInfoSave(AccountDto accountDto) {

        createToken(accountDto);
        //repoLoginHistory.save(new LIMSUserLoginHistoryEntity(userLogin));
    }


    public void createToken(AccountDto accountDto) {
        try {
            accountDto.setAccessToken(jwtTokenUtil.createAccessToken(accountDto.getUserId()));
            //userLogin.setRefreshToken(hhpJwtTokenUtil.createRefreshToken(userLogin.getUserId()));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
