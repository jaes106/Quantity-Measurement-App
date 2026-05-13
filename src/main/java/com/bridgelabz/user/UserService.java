package com.bridgelabz.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createOrFetchUser(String name, String email, String provider) {

        return repository.findByEmail(email)
                .orElseGet(() -> repository.save(
                        new User(name, email, provider, Role.USER)
                ));
    }
}