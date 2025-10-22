package hsf.fa25.pe.demo_pe3.repository;

import hsf.fa25.pe.demo_pe3.entity.SonyAccounts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<SonyAccounts, Integer> {
    SonyAccounts findByPhoneAndPassword(String phone, String password);
}
