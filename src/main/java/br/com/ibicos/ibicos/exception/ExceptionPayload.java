package br.com.ibicos.ibicos.exception;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionPayload {

	private int statusCode;
	private String title;
	private LocalDateTime timestamp;
	private String description;	
    @JsonInclude(Include.NON_NULL)
	Map<String, String> fieldToMessageMap;
}
