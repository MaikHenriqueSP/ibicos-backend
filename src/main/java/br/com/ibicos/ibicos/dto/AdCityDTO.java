package br.com.ibicos.ibicos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdCityDTO {
    private Integer idCity;
    private String cityName;
    private String stateAbb;
    private List<AdRegionAreaDTO> regionArea;
}
