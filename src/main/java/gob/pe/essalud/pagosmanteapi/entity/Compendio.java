package gob.pe.essalud.pagosmanteapi.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="CDGMDETALL")
public class Compendio {
    @Id
    private Long ideDetalle;
    private String txtDescripcCorto;
    private Integer ideNumericoTabla;
    private String codElementoTabla;

}
