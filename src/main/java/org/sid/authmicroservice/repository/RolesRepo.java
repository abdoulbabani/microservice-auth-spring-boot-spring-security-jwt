package org.sid.authmicroservice.repository;

import org.sid.authmicroservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Role,Long> {
    Role findByRoleName(String name);
}
