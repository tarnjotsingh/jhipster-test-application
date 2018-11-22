package com.reading.tvirdee.project.jhipexample.repository;

import com.reading.tvirdee.project.jhipexample.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
