package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.dto.AdDTO;
import br.com.ibicos.ibicos.entity.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface AdMapper {

    AdDTO AdToAdDTO(Ad ad);
    Ad AdDTOToAd(AdDTO adDTO);
}
