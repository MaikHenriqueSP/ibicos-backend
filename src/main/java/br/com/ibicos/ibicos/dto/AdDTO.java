package br.com.ibicos.ibicos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
@JsonRootName(value = "ad")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AdDTO {

    private Integer id;
    private String adDescription;
    private ServiceCategoryDTO serviceCategory;
    private List<AdCityDTO> cities;

}
