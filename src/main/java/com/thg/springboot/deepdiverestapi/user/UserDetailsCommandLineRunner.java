package com.thg.springboot.deepdiverestapi.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {


    private Logger logger =  LoggerFactory.getLogger(getClass());

//    public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
//        this.repository = repository;
//    } Na aula ele injeta fazendo dessa forma, nao usa autowired

    @Autowired
    private UserDetailsRepository repository;

    @Override
    public void run(String... args) throws Exception {
//        logger.info(Arrays.toString(args));
        repository.save(new UserDetails("Tita", "Admin"));
        repository.save(new UserDetails("Felipe", "Admin"));
        repository.save(new UserDetails("Caio", "User"));

        repository.count();

//        List<UserDetails> users = repository.findAll();
//        List<UserDetails> usersAdmin = repository.findByRole("Admin");

//        users.forEach(user -> logger.info(user.toString()));
//         usersAdmin.forEach(user -> logger.info(user.toString()));
    }

}
