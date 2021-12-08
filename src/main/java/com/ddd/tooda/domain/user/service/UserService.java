package com.ddd.tooda.domain.user.service;

import com.ddd.tooda.domain.user.model.User;
import com.ddd.tooda.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User signUp(String deviceId) {
        Optional<User> user = userRepository.findByDeviceId(deviceId);

        if(user.isEmpty()) {
           User newUser = User.builder()
                    .deviceId(deviceId)
                    .build();
            userRepository.save(newUser);
            return newUser;
        }

        return user.get();
    }




}
