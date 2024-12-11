package com.helmes.assignment.services;

import com.helmes.assignment.entity.models.MyUser;
import com.helmes.assignment.entity.models.MyUserDetails;
import com.helmes.assignment.entity.repositories.MyUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    private final MyUserRepository myUserRepository;

    public MyUserDetailService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> myUserOptional = myUserRepository.findByUsername(username);
        if (myUserOptional.isPresent()) {
            MyUser user = myUserOptional.get();

            return new MyUserDetails(user.getUsername(), user.getPassword(), user.getRole());

        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
