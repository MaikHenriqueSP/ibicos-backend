package br.com.ibicos.ibicos.mapper;

import br.com.ibicos.ibicos.dto.AdDTO;
import br.com.ibicos.ibicos.domain.entity.Ad;
import org.mapstruct.Mapper;

@Mapper
public interface AdMapper {

    AdDTO AdToAdDTO(Ad ad);
    Ad AdDTOToAd(AdDTO adDTO);
}
