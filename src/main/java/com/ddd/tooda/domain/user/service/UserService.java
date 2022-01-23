package com.ddd.tooda.domain.user.service;


import com.ddd.tooda.domain.user.model.SocialName;
import com.ddd.tooda.domain.user.model.SocialAccount;
import com.ddd.tooda.domain.user.model.User;
import com.ddd.tooda.domain.user.repository.SocialAccountRepository;
import com.ddd.tooda.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SocialAccountRepository socialAccountRepository;


    @Transactional
    public User signUpOrLogin(SocialName socialName, String socialId) {
        Optional<SocialAccount> socialAccount = getSocialAccountWithUser(socialName,socialId);
        if(socialAccount.isPresent()) {
            return socialAccount.get().getUser();
        }
        SocialAccount newSocialAccount = new SocialAccount(socialId, socialName);
        User user = new User(newSocialAccount);
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    private Optional<SocialAccount> getSocialAccountWithUser(SocialName socialName, String socialId) {
        return socialAccountRepository.findFirstBySocialNameAndSocialId(socialName,socialId);
    }



}
