package com.helmes.assignment.entity.repositories;

import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);
    List<MyUser> findAllByRole(Role role);
    MyUser findMyUserByUsername(String username);
}
