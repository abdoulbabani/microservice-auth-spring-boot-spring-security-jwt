package org.sid.authmicroservice;

import org.sid.authmicroservice.entities.Role;
import org.sid.authmicroservice.entities.Utilisateur;
import org.sid.authmicroservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class AuthMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthMicroserviceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner start(AccountService accountService){
        return args->{

            accountService.addRole(new Role(null,"USER"));
            accountService.addRole(new Role(null,"ADMIN"));
            accountService.addRole(new Role(null,"MANAGER"));



            accountService.addutilisateur(new Utilisateur(null,"user1","1234",new ArrayList<>()));
            accountService.addutilisateur(new Utilisateur(null,"user2","1234",new ArrayList<>()));
            accountService.addutilisateur(new Utilisateur(null,"admin","1234",new ArrayList<>()));
            accountService.addutilisateur(new Utilisateur(null,"user3","1234",new ArrayList<>()));


            accountService.AddRoleToUtilisateur("user1","USER");
            accountService.AddRoleToUtilisateur("admin","ADMIN");
            accountService.AddRoleToUtilisateur("admin","USER");
            accountService.AddRoleToUtilisateur("user2","USER");
            accountService.AddRoleToUtilisateur("user2","MANAGER");
            accountService.AddRoleToUtilisateur("user3","USER");

        };
    }

}
