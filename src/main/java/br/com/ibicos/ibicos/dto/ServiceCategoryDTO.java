package br.com.ibicos.ibicos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCategoryDTO {

    private String categoryName;
}
