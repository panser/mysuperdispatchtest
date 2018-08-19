package com.backend.tasks.repository;

import com.backend.tasks.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByOrganizationId(Long orgId);

    Optional<User> findOneByIdAndOrganizationId(Long userId, Long orgId);

    @Transactional
    void deleteByIdAndOrganizationId(Long userId, Long orgId);
}
