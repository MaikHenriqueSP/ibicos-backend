package br.com.ibicos.ibicos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdWithProviderStatisticsDTO {
    private AdDTO adDTO;
    private ProviderStatisticsDTO providerStatisticsDTO;
}
