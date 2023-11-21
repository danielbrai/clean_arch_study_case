package br.com.danielbrai.app.dataprovider.db.repository;

import br.com.danielbrai.app.dataprovider.db.table.VoyageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<VoyageEntity, Long> {
}
