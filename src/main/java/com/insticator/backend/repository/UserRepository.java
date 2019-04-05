package com.insticator.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insticator.backend.model.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT u.* FROM user u WHERE u.user_uuid = ?1", nativeQuery = true)
	User findByUuid(UUID userUUID);

}