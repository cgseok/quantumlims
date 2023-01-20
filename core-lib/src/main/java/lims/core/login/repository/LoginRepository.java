package lims.core.login.repository;

import lims.core.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<AccountEntity, String> {
    AccountEntity findByUserId(String userId);
}
