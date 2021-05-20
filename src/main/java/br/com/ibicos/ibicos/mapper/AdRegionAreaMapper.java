package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.api.dto.AdRegionAreaDTO;
import br.com.ibicos.ibicos.domain.entity.AdRegionArea;
import org.mapstruct.Mapper;

@Mapper
public interface AdRegionAreaMapper {
    AdRegionAreaDTO AdRegionAreaToAdRegionAreaDTO(AdRegionArea adRegionArea);
}
