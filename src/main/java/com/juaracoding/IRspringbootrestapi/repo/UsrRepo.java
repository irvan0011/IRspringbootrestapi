package com.juaracoding.IRspringbootrestapi.repo;

import com.juaracoding.IRspringbootrestapi.model.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsrRepo extends JpaRepository<Usr,Long> {

    //SELECT * FROM MstUser WHERE UserName = ? OR NoHp = ? OR Email = ?
    public Optional<Usr> findByUserNameOrNoHpOrEmail(String userName, String noHp, String email);
    public Optional<Usr> findByUserName(String userName);
    public Optional<Usr> findByUserNameAndIsActive(String userName,Boolean isActive);
}
