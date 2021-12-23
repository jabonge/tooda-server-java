package com.ddd.tooda.domain.user.service;

import com.ddd.tooda.domain.user.model.User;
import com.ddd.tooda.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;


    @Test
    @DisplayName("신규 유저 저장")
    void test_1() {
        String deviceId = "test-device-id";

        Mockito.doReturn(Optional.empty()).when(userRepository).findByDeviceId(deviceId);
        User newUser = userService.signUp(deviceId);

        Mockito.verify(userRepository, Mockito.times(1)).save(any());
        assertEquals(newUser.getDeviceId(), deviceId);
    }

    @Test
    @DisplayName("존재하는 유저")
    void test_2() {
        String deviceId = "test-device-id";
        User user = User.builder()
                .deviceId(deviceId)
                .build();

        Mockito.doReturn(Optional.of(user)).when(userRepository).findByDeviceId(deviceId);
        User existUser = userService.signUp(deviceId);

        Mockito.verify(userRepository, Mockito.never()).save(any());
        assertEquals(existUser.getDeviceId(), deviceId);

    }
}