package br.com.danielbrai.app.dataprovider.db;

import br.com.danielbrai.app.dataprovider.db.repository.VoyageRepository;
import br.com.danielbrai.app.dataprovider.db.table.VoyageEntity;
import br.com.danielbrai.app.infra.mappers.VoyageDomainModelToEntityModelMapper;
import br.com.danielbrai.app.infra.mappers.VoyageEntityToDomainModelMapper;
import br.com.danielbrai.core.dataprovider.VoyageDataProvider;
import br.com.danielbrai.core.entity.Voyage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class VoyageDataBaseDataProvider implements VoyageDataProvider {

    private final VoyageRepository voyageRepository;

    private final VoyageDomainModelToEntityModelMapper voyageDomainModelToEntityModelMapper;

    private final VoyageEntityToDomainModelMapper voyageEntityToDomainModelMapper;

    @Override
    public Voyage save(Voyage voyage) {
        VoyageEntity voyageEntity = this.voyageDomainModelToEntityModelMapper.map(voyage);
        VoyageEntity savedVoyage = this.voyageRepository.save(voyageEntity);
        return this.voyageEntityToDomainModelMapper.map(savedVoyage);
    }
}
