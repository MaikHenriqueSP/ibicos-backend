package br.com.ibicos.ibicos.exception;

import java.time.LocalDateTime;

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
}
