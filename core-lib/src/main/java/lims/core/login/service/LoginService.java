package lims.core.login.service;

import lims.core.common.dto.QuantumResultDto;
import lims.core.login.dto.LoginRequestDto;

public interface LoginService {
    QuantumResultDto Login(LoginRequestDto requestDto);
}
