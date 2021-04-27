package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.dto.StatisticsDTO;
import br.com.ibicos.ibicos.entity.Statistics;
import org.mapstruct.Mapper;

@Mapper
public interface StatisticsMapper {
    StatisticsDTO statisticsToStatisticsDTO(Statistics statistics);
}
