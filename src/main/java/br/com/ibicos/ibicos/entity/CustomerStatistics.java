package br.com.ibicos.ibicos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@PrimaryKeyJoinColumn(name="id_customer_statistics")
public class CustomerStatistics extends Statistics {

}
