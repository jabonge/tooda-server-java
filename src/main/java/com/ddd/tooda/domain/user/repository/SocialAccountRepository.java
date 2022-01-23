package com.ddd.tooda.domain.user.repository;

import com.ddd.tooda.domain.user.model.SocialName;
import com.ddd.tooda.domain.user.model.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount,Long> {
    Optional<SocialAccount> findFirstBySocialNameAndSocialId(SocialName socialName, String socialId);
}
