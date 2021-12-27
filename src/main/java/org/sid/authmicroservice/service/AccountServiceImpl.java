package org.sid.authmicroservice.service;

import org.sid.authmicroservice.entities.Role;
import org.sid.authmicroservice.entities.Utilisateur;
import org.sid.authmicroservice.repository.RolesRepo;
import org.sid.authmicroservice.repository.UtilisateurRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private UtilisateurRepo utilisateurRepo;
    private RolesRepo rolesRepo;
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(UtilisateurRepo utilisateurRepo, RolesRepo rolesRepo,PasswordEncoder passwordEncoder) {
        this.utilisateurRepo = utilisateurRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public Utilisateur addutilisateur(Utilisateur utilisateur) {
        String password=utilisateur.getPassword();
        utilisateur.setPassword(passwordEncoder.encode(password));
        return  utilisateurRepo.save(utilisateur);
    }

    @Override
    public Role addRole(Role role) {
        return rolesRepo.save(role);
    }

    @Override
    public void AddRoleToUtilisateur(String name, String roleName) {

        Utilisateur u=utilisateurRepo.findByUsername(name);
        Role r=rolesRepo.findByRoleName(roleName);
        u.getRoles().add(r);

    }

    @Override
    public Utilisateur loadUtilisateurByusername(String username) {
        return utilisateurRepo.findByUsername(username);
    }

    @Override
    public List<Utilisateur> listUsers() {
        return utilisateurRepo.findAll();
    }
}
