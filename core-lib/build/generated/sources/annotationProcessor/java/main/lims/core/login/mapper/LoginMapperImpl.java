package lims.core.login.mapper;

import javax.annotation.processing.Generated;
import lims.core.account.dto.AccountDto;
import lims.core.account.entity.AccountEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-19T18:23:09+0900",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
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
