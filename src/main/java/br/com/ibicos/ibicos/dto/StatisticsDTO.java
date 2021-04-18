package br.com.ibicos.ibicos.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatisticsDTO {
    private Integer id;
    private Float evaluation;
    private Integer viewsCounter;
    private Integer evaluationsCounter;
    private Integer hiredServicesCounter;
    private Integer messagesCounter;

}
