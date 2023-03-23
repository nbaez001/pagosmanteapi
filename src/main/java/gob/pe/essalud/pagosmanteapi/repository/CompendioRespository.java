package gob.pe.essalud.pagosmanteapi.repository;

import gob.pe.essalud.pagosmanteapi.entity.Compendio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompendioRespository extends CrudRepository<Compendio, Long> {
    public Compendio findByIdeNumericoTablaAndCodElementoTabla(Integer tabla, String elemento);

    @Query(value = "SELECT IDE_DETALLE, CONCAT(CONCAT(COD_ELEMENTO_TABLA, ' - '),TXT_DESCRIPC_CORTO)  AS \"TXT_DESCRIPC_CORTO\",  IDE_NUMERICO_TABLA, COD_ELEMENTO_TABLA \n" +
            "FROM CDGMDETALL \n" +
            "WHERE IDE_NUMERICO_TABLA=202 AND TXT_REFERENC_1='04'\n" +
            "            ORDER BY TXT_DESCRIPC_CORTO", nativeQuery = true)
    List<Compendio> getListaBancos();
}
