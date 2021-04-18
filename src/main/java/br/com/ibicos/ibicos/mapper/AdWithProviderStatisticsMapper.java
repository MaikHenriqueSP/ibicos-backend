package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.dto.AdWithProviderStatisticsDTO;
import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.entity.ProviderStatistics;
import br.com.ibicos.ibicos.view.AdView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AdWithProviderStatisticsMapper {

    @Mappings({
            @Mapping(source = "adView.ad", target = "adDTO"),
            @Mapping(source = "adView.providerStatistics", target= "providerStatisticsDTO")
    })
    AdWithProviderStatisticsDTO AdViewToAdWithProviderStatisticsDTO(AdView adView);
}
