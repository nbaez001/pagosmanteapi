package pe.gob.essalud.pagosmanteapi.entity;

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
@Table(name="CSAMTARIFA")
public class Tarifa {
    @Id
    private Integer ideNumericoTarifa;
    private Double numValorTarifa1;
    private String codTarifa;
    private String flgVigenciaRegistro;
}
