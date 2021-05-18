package br.com.ibicos.ibicos.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@PrimaryKeyJoinColumn(name="fk_id_statistics")
public class CustomerStatistics extends Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer_statistics")
    private Integer id;

}
