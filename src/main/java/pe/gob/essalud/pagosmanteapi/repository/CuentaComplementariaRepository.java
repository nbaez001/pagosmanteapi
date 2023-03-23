package pe.gob.essalud.pagosmanteapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pe.gob.essalud.pagosmanteapi.entity.Compendio;
import pe.gob.essalud.pagosmanteapi.entity.CuentaComplementaria;
import pe.gob.essalud.pagosmanteapi.model.CuentaComplementariaResult;

import java.util.*;
import java.util.Optional;

@Repository
public interface CuentaComplementariaRepository extends JpaRepository<CuentaComplementaria, String> {

    Page<CuentaComplementaria> findAllByNumContratoInscripcOrderByNumRenovaciContratoDescNumCuotaNumDesc(String numeroContrato, Pageable pageable);

    Page<CuentaComplementaria> findAllByNumContratoInscripcAndFecVencimiePagoGreaterThanEqualOrderByNumRenovaciContratoDescNumCuotaNumDesc(String numeroContrato, String fecVencimiento, Pageable pageable);

    Page<CuentaComplementaria> findAllByNumContratoInscripcAndCodEstadoAporteOrderByNumRenovaciContratoDescNumCuotaNumDesc(String numeroContrato, String estado, Pageable pageable);



    @Query("FROM CuentaComplementaria comple where comple.numContratoInscripc = ?1 and comple.fecVencimiePagoDate >= ?2")
    Page<CuentaComplementaria> buscarContratoVencimiento(Long ideNumericoCtrl, Date vencimiento, Pageable pageable);

    @Query("SELECT count(pad.correlativoRegistroComplemen) FROM CuentaComplementaria pad where pad.numContratoInscripc = ?1 and " +
            "pad.numRenovaciContrato = ?2 and pad.numCuota = ?3")
    int existeContrato(String contrato, String renovacion, String cuota);

    @Query(value="SELECT CPD.TXT_DESCRIPC_CORTO" +
            "          FROM CDGMDETALL CPD, CSATINSCRI INS " +
            "         WHERE CPD.IDE_NUMERICO_TABLA = INS.COD_TCLASIFIC_COBERTUR" +
            "           AND CPD.COD_ELEMENTO_TABLA = INS.COD_ECLASIFIC_COBERTUR " +
            "           AND INS.NUM_CONTRATO_INSCRIPC = ?1", nativeQuery = true)
    String getTipoSeguro(String contrato);

    @Query( value=" SELECT" +
            " IDE_NUMERICO_CTAINDIV IDENUMERICOCTAINDIV," +
            " IDE_NUMERICO_TICKET IDENUMERICOTICKET," +
            " IDE_NUMERICO_PERSONA IDENUMERICOPERSONA," +
            " HPAFILL" +
            " FROM CSARCTACOM WHERE NUM_CONTRATO_INSCRIPC  = ?1 AND NUM_RENOVACI_CONTRATO  = (" +
            " SELECT MAX(TO_NUMBER(NUM_RENOVACI_CONTRATO)) FROM CSARCTACOM WHERE NUM_CONTRATO_INSCRIPC  = ?1" +
            ") AND NUM_CUOTA = (" +
            "SELECT MAX(TO_NUMBER(NUM_CUOTA)) FROM CSARCTACOM WHERE NUM_CONTRATO_INSCRIPC  = ?1 AND NUM_RENOVACI_CONTRATO = " +
            "(SELECT MAX(NUM_RENOVACI_CONTRATO) FROM CSARCTACOM WHERE NUM_CONTRATO_INSCRIPC  = ?1)" +
            ")", nativeQuery = true)
    Optional<CuentaComplementariaResult> getCuentaCompleMax(String contrato);


}
