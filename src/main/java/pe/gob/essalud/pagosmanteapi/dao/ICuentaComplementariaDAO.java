package pe.gob.essalud.pagosmanteapi.dao;

import java.util.List;

import pe.gob.essalud.pagosmanteapi.entity.CuentaComplementaria;
public interface ICuentaComplementariaDAO {

    public String cambiarEstado(String correlativo, String usuario);

    public List<String> valActualizaCuota(CuentaComplementaria cta);
    public List<String> actualizarCuota(CuentaComplementaria cta);

    public List<String> valNuevaCuota(CuentaComplementaria cta);
    public List<String> nuevaCuota(CuentaComplementaria cta);

    public List<String> ingresarPagoCuota(CuentaComplementaria cta);

    public String eliminarCuota(String correlativo, String usuario);

}
