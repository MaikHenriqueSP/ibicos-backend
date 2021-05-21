package br.com.ibicos.ibicos.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AdCityDTO {
    @JsonProperty("id")
    private Integer idCity;
    private String cityName;
    @JsonProperty("state_name")
    private String stateAbb;
    private List<AdRegionAreaDTO> regionArea;
}
