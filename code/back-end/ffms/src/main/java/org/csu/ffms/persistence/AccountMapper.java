package org.csu.ffms.persistence;

import org.csu.ffms.domain.Account;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountMapper {
    Account getAccountByUserid(String userid);

    Account getAccountByUseridAndPassword(Account account);

    void insertAccount(Account account);

    void insertRelation(Account account);

    void insertSignon(Account account);

    void updateAccount(Account account);

    void updateRelation(Account account);

    void updateSignon(Account account);

    List<Account> getAllAccount();

    void deleteAccount(String userid);

    void deleteRelation(String userid);

    void deleteSignon(String userid);
}
