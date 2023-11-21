package br.com.danielbrai.app.dataprovider.db;

import br.com.danielbrai.app.dataprovider.db.repository.VoyageRepository;
import br.com.danielbrai.app.dataprovider.db.table.VoyageEntity;
import br.com.danielbrai.app.infra.mappers.VoyageCoreDomainToEntityModelMapper;
import br.com.danielbrai.app.infra.mappers.VoyageEntityToCoreDomainMapper;
import br.com.danielbrai.core.dataprovider.VoyageDataProvider;
import br.com.danielbrai.core.domain.Voyage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class VoyageDataBaseDataProvider implements VoyageDataProvider {

    private final VoyageRepository voyageRepository;

    private final VoyageCoreDomainToEntityModelMapper voyageCoreDomainToEntityModelMapper;

    private final VoyageEntityToCoreDomainMapper voyageEntityToCoreDomainMapper;

    @Override
    public Voyage save(Voyage voyage) {
        VoyageEntity voyageEntity = this.voyageCoreDomainToEntityModelMapper.map(voyage);
        VoyageEntity savedVoyage = this.voyageRepository.save(voyageEntity);
        return this.voyageEntityToCoreDomainMapper.map(savedVoyage);
    }
}
