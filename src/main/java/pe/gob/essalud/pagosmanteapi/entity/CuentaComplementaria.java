package pe.gob.essalud.pagosmanteapi.entity;

import lombok.*;
import org.hibernate.annotations.Formula;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="CSARCTACOM")
public class CuentaComplementaria {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="ctacom_seq")
    @SequenceGenerator(name="ctacom_seq",sequenceName="SQ_CSARCTACOM",allocationSize=1)
    private String correlativoRegistroComplemen;
    private Long ideNumericoCtaindiv;
    private String numContratoInscripc;
    private String fecVencimiePago;
    private String codAgenciaBancaria;
    private String codEstadoAporte;
    private String numRenovaciContrato;
    @Column(name="NUM_CUOTA")
    private String numCuota;

    private String codSituacioAporte;
    private String codPago;
    private String numOperacioBancaria;
    private Long ideNumericoPersona;
    private Double monMontoAporte;
    private Double numMontoEssalud;
    private Double numMontoEvida;
    private String fecRegistroSistema;
    private String horRegistroSistema;
    private String codUsuarioSistema;
    private String codTerminalSistema;
    private Integer ideNumericoTicket;
    private String codPeriodoCuota;
    private Double munMontoNeto;
    private Long ideNumericoCtacomp;
    private String flgConciliaPago;
    private String hpafill;
    private String fecAportePago;
    private String fecInicioAcredi;
    private String fecFinAcredi;
    private Double numMontoMora;
    @Formula("to_date(FEC_VENCIMIE_PAGO,'YYYYMMDD')")
    private Date fecVencimiePagoDate;

    @Formula(value = "CAST(NUM_CUOTA AS NUMERIC(10, 0))")
    private int numCuotaNum;
}
