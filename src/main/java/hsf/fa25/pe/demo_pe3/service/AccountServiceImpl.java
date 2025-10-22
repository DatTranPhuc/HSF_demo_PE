package hsf.fa25.pe.demo_pe3.service;

import hsf.fa25.pe.demo_pe3.entity.SonyAccounts;
import hsf.fa25.pe.demo_pe3.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public SonyAccounts getAccounts(String phone, String password) {
        return accountRepository.findByPhoneAndPassword(phone, password);
    }

    @Override
    public boolean addAccount(SonyAccounts accounts) {
        return accountRepository.save(accounts) != null;
    }
}
