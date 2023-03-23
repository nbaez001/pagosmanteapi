package gob.pe.essalud.pagosmanteapi.dao;

import gob.pe.essalud.pagosmanteapi.entity.CuentaComplementaria;
import java.util.List;
public interface ICuentaComplementariaDAO {

    public String cambiarEstado(String correlativo, String usuario);

    public List<String> valActualizaCuota(CuentaComplementaria cta);
    public List<String> actualizarCuota(CuentaComplementaria cta);

    public List<String> valNuevaCuota(CuentaComplementaria cta);
    public List<String> nuevaCuota(CuentaComplementaria cta);

    public List<String> ingresarPagoCuota(CuentaComplementaria cta);

    public String eliminarCuota(String correlativo, String usuario);

}
