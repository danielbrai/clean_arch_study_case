package br.com.danielbrai.clean_arch_study_case.voyage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
}
