package lims.core.login.service.impl;

import lims.core.account.entity.AccountEntity;
import lims.core.account.dto.AccountDto;
import lims.core.common.dto.QuantumResultDto;
import lims.core.common.message.QuantumMessageUtil;
import lims.core.common.service.CommonService;
import lims.core.login.dto.LoginRequestDto;
import lims.core.login.mapper.LoginMapper;
import lims.core.login.repository.LoginRepository;
import lims.core.login.service.LoginService;
import lims.core.login.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userLoginService")
@RequiredArgsConstructor
public class LoginServiceImpl extends CommonService implements LoginService {


    @Autowired
    private LoginRepository repo;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private LoginUtil loginUtil;

    @Override
    public QuantumResultDto Login(LoginRequestDto requestDto) {
        QuantumResultDto resultDto =  new QuantumResultDto();
        AccountEntity userLogin = repo.findByUserId(requestDto.getLoginId());
        AccountDto accountDto = loginMapper.AccountDtoToEntity(userLogin);


        if(accountDto == null) {
            resultDto.setResultCode("ERR.504");
            resultDto.setResultMessage(QuantumMessageUtil.getMessage("ERR.504"));
            return resultDto;
        }
        // loginPassword check
        if(!requestDto.getLoginPassword().equals(userLogin.getUserPassword())) {

            resultDto.setResultCode("ERR.504");
            resultDto.setResultMessage(QuantumMessageUtil.getMessage("ERR.504"));
            return resultDto;
        }

        loginUtil.loginInfoSave(accountDto);
        resultDto.setResultData(accountDto);
        return resultDto;
    }
}
