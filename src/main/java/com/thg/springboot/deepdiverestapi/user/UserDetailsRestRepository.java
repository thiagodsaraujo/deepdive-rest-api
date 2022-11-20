package com.thg.springboot.deepdiverestapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails,Long> {

    //Se quiser criar outro método no repositorio, como por exemplo achar por Role, tem que fazer com o nome igual
    List<UserDetails> findByRole(String role);

}
