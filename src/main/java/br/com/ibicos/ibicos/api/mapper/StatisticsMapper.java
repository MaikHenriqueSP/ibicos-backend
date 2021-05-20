package br.com.ibicos.ibicos.api.mapper;

import br.com.ibicos.ibicos.api.dto.StatisticsDTO;
import br.com.ibicos.ibicos.domain.entity.Statistics;
import org.mapstruct.Mapper;

@Mapper
public interface StatisticsMapper {
    StatisticsDTO statisticsToStatisticsDTO(Statistics statistics);
}
