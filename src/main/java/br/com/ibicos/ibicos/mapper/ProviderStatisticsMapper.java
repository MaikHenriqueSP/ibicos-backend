package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.dto.ProviderStatisticsDTO;
import br.com.ibicos.ibicos.domain.entity.ProviderStatistics;
import org.mapstruct.Mapper;

@Mapper
public interface ProviderStatisticsMapper {
    ProviderStatisticsDTO providerStatisticsToProviderStatisticsDTO(ProviderStatistics providerStatistics);
}
