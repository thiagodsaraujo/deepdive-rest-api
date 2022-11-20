package com.thg.springboot.deepdiverestapi.user;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {

    //Se quiser criar outro método no repositorio, como por exemplo achar por Role, tem que fazer com o nome igual
    List<UserDetails> findByRole(String role);

}
