package br.com.ibicos.ibicos.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StatisticsDTO {
    private Integer id;
    private Float evaluation;
    private Integer viewsCounter;
    private Integer evaluationsCounter;
    private Integer hiredServicesCounter;
    private Integer messagesCounter;

}
