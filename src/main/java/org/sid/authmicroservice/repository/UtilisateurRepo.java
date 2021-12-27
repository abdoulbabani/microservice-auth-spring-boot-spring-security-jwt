package org.sid.authmicroservice.repository;

import org.sid.authmicroservice.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepo  extends JpaRepository<Utilisateur,Long> {
    Utilisateur findByUsername(String name);
}
