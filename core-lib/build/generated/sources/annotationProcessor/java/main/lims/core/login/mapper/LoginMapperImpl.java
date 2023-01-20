package lims.core.login.mapper;

import javax.annotation.processing.Generated;
import lims.core.account.dto.AccountDto;
import lims.core.account.entity.AccountEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-20T23:27:43+0900",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.12 (Microsoft)"
)
public class LoginMapperImpl implements LoginMapper {

    @Override
    public AccountDto AccountDtoToEntity(AccountEntity accontentity) {
        if ( accontentity == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        return accountDto;
    }
}
