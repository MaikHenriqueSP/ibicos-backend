package br.com.ibicos.ibicos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AdWithProviderStatisticsDTO {
    @JsonProperty("ad")
    private AdDTO adDTO;
    @JsonProperty("providerStatistics")
    private ProviderStatisticsDTO providerStatisticsDTO;
}
