package com.example.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.library.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
