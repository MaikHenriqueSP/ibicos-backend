package br.com.ibicos.ibicos.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@SuperBuilder(toBuilder = true)
public class ProviderStatisticsDTO extends StatisticsDTO{
    private Integer id;
    private Integer visualizations;
    private ServiceCategoryDTO category;
}
