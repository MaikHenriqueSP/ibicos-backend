package br.com.ibicos.ibicos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderStatisticsDTO {
    private StatisticsDTO statistics;
    private Integer id;
    private Integer visualizations;
    private ServiceCategoryDTO category;


}
