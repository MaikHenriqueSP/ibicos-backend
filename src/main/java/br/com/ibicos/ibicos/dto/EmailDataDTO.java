package br.com.ibicos.ibicos.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailDataDTO {

    private String templateName;
    private String to;
    private String subject;
    private String from;
}
