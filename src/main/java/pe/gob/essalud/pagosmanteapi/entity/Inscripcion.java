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
@Table(name="CSATINSCRI")
public class Inscripcion {
    @Id
    private Long ideNumericoInscrip;
    private Integer codTclasificCobertur;
    private String codEclasificCobertur;
    private String numContratoInscripc;
    private Long ideNumericoPesona;
    private Long ideNumericoTitular;
}
