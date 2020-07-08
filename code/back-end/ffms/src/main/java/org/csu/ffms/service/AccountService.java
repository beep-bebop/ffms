package org.csu.ffms.service;
import org.csu.ffms.domain.Account;
import org.csu.ffms.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountMapper accountMapper;

    public Account getAccount(String userid) {
        return accountMapper.getAccountByUserid(userid);
    }

    public Account getAccount(String userid, String password) {
        Account account = new Account();
        account.setUserid(userid);
        account.setPassword(password);
        return accountMapper.getAccountByUseridAndPassword(account);
    }

    @Transactional
    public void insertAccount(Account account) {
        accountMapper.insertAccount(account);
        accountMapper.insertRelation(account);
        accountMapper.insertSignon(account);
    }

    @Transactional
    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
        accountMapper.updateRelation(account);

        if (account.getPassword() != null && account.getPassword().length() > 0) {
            accountMapper.updateSignon(account);
        }
    }

    public List<Account> getAllAccount()
    {
        return accountMapper.getAllAccount();
    }

    @Transactional
    public void passwordReset(String userid)
    {
        Account account = getAccount(userid);
        if(account!=null)
        {
            account.setPassword("000000");
            updateAccount(account);
        }
        else
        {
            System.out.println("account is null");
        }
    }

    @Transactional
    public void deleteAccount(String userid)
    {
        accountMapper.deleteAccount(userid);
        accountMapper.deleteRelation(userid);
        accountMapper.deleteSignon(userid);
    }

}
