package br.com.ibicos.ibicos.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTokenConfigDTO {
	
	private String receiverName;
	private String token;
	private String htmlTemplateName;
	private String subject;
	private String fromEmail;
	private String fromTitle;
	private String toEmail;

}
