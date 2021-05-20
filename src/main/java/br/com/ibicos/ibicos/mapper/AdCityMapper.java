package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.dto.AdCityDTO;
import br.com.ibicos.ibicos.domain.entity.AdCity;
import org.mapstruct.Mapper;

@Mapper
public interface AdCityMapper {
    AdCityDTO AdCityToAdCityDTO(AdCity adCity);
}
