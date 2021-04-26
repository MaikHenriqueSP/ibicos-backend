package br.com.ibicos.ibicos.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProviderSelfStatisticsDTO {
    private Double averageEvaluation;
    private Long totalEvaluations;
    private Long totalHiredServices;
    private Long totalMessagesReceived;
    private Long totalAdsVisualizations;

}