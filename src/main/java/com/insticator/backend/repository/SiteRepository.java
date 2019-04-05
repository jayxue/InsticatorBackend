package com.insticator.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insticator.backend.model.Site;

import java.util.UUID;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

	@Query(value = "SELECT s.* FROM site s WHERE s.site_uuid = ?1", nativeQuery = true)
	Site findByUuid(UUID siteUUID);
}