package br.com.ibicos.ibicos.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@PrimaryKeyJoinColumn(name="id_customer_statistics")
public class CustomerStatistics extends Statistics {

}
