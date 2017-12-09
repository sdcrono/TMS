package org.fsoft.tms.repository;

import org.fsoft.tms.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Isabella on 8-Jun-2017.
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer>{
    VerificationToken findByToken(String token);
}
