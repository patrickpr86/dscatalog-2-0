package com.patrick.dscatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patrick.dscatalog.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
