package hsf.fa25.pe.demo_pe3.service;

import hsf.fa25.pe.demo_pe3.entity.SonyAccounts;

public interface AccountService {
    SonyAccounts getAccounts(String phone, String password);
     public boolean addAccount(SonyAccounts account);
}
