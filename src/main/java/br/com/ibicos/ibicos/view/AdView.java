package br.com.ibicos.ibicos.view;

import br.com.ibicos.ibicos.entity.Ad;
import br.com.ibicos.ibicos.entity.ProviderStatistics;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class AdView {
    private Ad ad;
    private ProviderStatistics providerStatistics;
}
