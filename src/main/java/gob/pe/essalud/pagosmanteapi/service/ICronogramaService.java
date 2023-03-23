package gob.pe.essalud.pagosmanteapi.service;

import gob.pe.essalud.pagosmanteapi.entity.Compendio;
import gob.pe.essalud.pagosmanteapi.entity.CuentaComplementaria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICronogramaService {
    public Page<CuentaComplementaria> cargarListaCronograma(String contrato, String fecVencimiento, String estado, Pageable pageable);
    public String cargarTipoSeguro(String contrato);
    public CuentaComplementaria saveCronograma(CuentaComplementaria cta);
    public String cambiarEstado(CuentaComplementaria cta);

    public List<String> actualizarCuota(CuentaComplementaria cta);
    public List<String> nuevaCuota(CuentaComplementaria cta);
    public List<String>  ingresarPagoCuota(CuentaComplementaria cta);

    public List<Compendio> listaBancos();

    public String eliminarCuota(CuentaComplementaria cta);

    public String getUsuario();

}
