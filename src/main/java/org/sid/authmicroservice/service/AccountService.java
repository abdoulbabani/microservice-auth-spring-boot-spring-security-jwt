package org.sid.authmicroservice.service;

import org.sid.authmicroservice.entities.Role;
import org.sid.authmicroservice.entities.Utilisateur;

import java.util.List;

public interface AccountService {
    Utilisateur addutilisateur(Utilisateur utilisateur);
    Role addRole(Role roles);
    void AddRoleToUtilisateur(String name,String roleName);
    Utilisateur loadUtilisateurByusername(String username);
    List<Utilisateur>listUsers();

}
