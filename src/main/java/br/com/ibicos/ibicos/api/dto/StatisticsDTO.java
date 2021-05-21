package br.com.ibicos.ibicos.api.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@SuperBuilder(toBuilder = true)
public class StatisticsDTO {
    private Integer id;
    private Float evaluation;
    private Integer evaluationsCounter;
    private Integer hiredServicesCounter;
    private Integer messagesCounter;
}
