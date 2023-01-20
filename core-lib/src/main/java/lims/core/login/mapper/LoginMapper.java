package lims.core.login.mapper;

import lims.core.account.dto.AccountDto;
import lims.core.account.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper
public interface LoginMapper {
    AccountDto AccountDtoToEntity(AccountEntity accontentity);
}
