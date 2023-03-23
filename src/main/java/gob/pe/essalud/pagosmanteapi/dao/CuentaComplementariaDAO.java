package gob.pe.essalud.pagosmanteapi.dao;

import gob.pe.essalud.pagosmanteapi.entity.CuentaComplementaria;
import gob.pe.essalud.pagosmanteapi.service.CronogramaService;
import org.springframework.stereotype.Repository;
import gob.pe.essalud.pagosmanteapi.config.JpaGenericRepository;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class CuentaComplementariaDAO extends JpaGenericRepository implements ICuentaComplementariaDAO {

	final Logger logger = LoggerFactory.getLogger(CuentaComplementariaDAO.class);

	@Override
	public String cambiarEstado(String correlativo, String usuario) {
		String resultado = "";
		try {
			StoredProcedureQuery storedProcedure = createEntityManager()
					.createStoredProcedureQuery("USRCSA.PKG_GESTIONPAGOS_SP_NSP_RV.SP_NSP_CAMBIAR_ESTADO");
			// set parameters
			storedProcedure.registerStoredProcedureParameter("CORRELATIVO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_USUARIO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("RESULTADO", String.class, ParameterMode.OUT);

			storedProcedure.setParameter("CORRELATIVO", correlativo);
			storedProcedure.setParameter("PI_USUARIO", usuario);
			storedProcedure.execute();

			resultado = storedProcedure.getOutputParameterValue("RESULTADO").toString();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			createEntityManager().close();
		}
		return resultado;
	}

	@Override
	public List<String> valActualizaCuota(CuentaComplementaria cta) {
		List<String> resultado = new ArrayList<String>();
		try {
			StoredProcedureQuery storedProcedure = createEntityManager()
					.createStoredProcedureQuery("USRCSA.PKG_GESTIONPAGOS_SP_NSP_RV.SP_NSP_VAL_ACTUALIZA_CUOTA");
			// set parameters
			storedProcedure.registerStoredProcedureParameter("PI_CORRELATIVO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_RENOVACION", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_CUOTA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_VENCIMIENTO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_NUMERO_CONTRATO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_ESSALUD", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_MAS_VIDA", Double.class, ParameterMode.IN);

			storedProcedure.registerStoredProcedureParameter("PO_CODIGO", String.class, ParameterMode.OUT);
			storedProcedure.registerStoredProcedureParameter("PO_MENSAJE", String.class, ParameterMode.OUT);

			storedProcedure.setParameter("PI_CORRELATIVO", cta.getCorrelativoRegistroComplemen());
			storedProcedure.setParameter("PI_RENOVACION", cta.getNumRenovaciContrato());
			storedProcedure.setParameter("PI_CUOTA", cta.getNumCuota());
			storedProcedure.setParameter("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago());
			storedProcedure.setParameter("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc());
			storedProcedure.setParameter("PI_MONTO_ESSALUD", cta.getNumMontoEssalud());
			storedProcedure.setParameter("PI_MONTO_MAS_VIDA", cta.getNumMontoEvida());
			storedProcedure.execute();

			resultado.add(storedProcedure.getOutputParameterValue("PO_CODIGO").toString());
			resultado.add(storedProcedure.getOutputParameterValue("PO_MENSAJE").toString());

			logger.info("DAO", resultado.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			createEntityManager().close();
		}
		return resultado;
	}

	@Override
	public List<String> actualizarCuota(CuentaComplementaria cta) {
		List<String> resultado = new ArrayList<String>();
		try {
			StoredProcedureQuery storedProcedure = createEntityManager()
					.createStoredProcedureQuery("USRCSA.PKG_GESTIONPAGOS_SP_NSP_RV.SP_NSP_ACTUALIZA_CUOTA");
			// set parameters
			storedProcedure.registerStoredProcedureParameter("PI_RENOVACION", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_CUOTA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_VENCIMIENTO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_NUMERO_CONTRATO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_ESSALUD", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_MAS_VIDA", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_USUARIO", String.class, ParameterMode.IN);

			storedProcedure.registerStoredProcedureParameter("PI_FECHA_INI_COBERTURA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_FIN_COBERTURA", String.class, ParameterMode.IN);

			storedProcedure.registerStoredProcedureParameter("PO_CODIGO", String.class, ParameterMode.OUT);
			storedProcedure.registerStoredProcedureParameter("PO_MENSAJE", String.class, ParameterMode.OUT);

			storedProcedure.setParameter("PI_RENOVACION", cta.getNumRenovaciContrato());
			storedProcedure.setParameter("PI_CUOTA", cta.getNumCuota());
			storedProcedure.setParameter("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago());
			storedProcedure.setParameter("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc());
			storedProcedure.setParameter("PI_MONTO_ESSALUD", cta.getNumMontoEssalud());
			storedProcedure.setParameter("PI_MONTO_MAS_VIDA", cta.getNumMontoEvida());
			storedProcedure.setParameter("PI_USUARIO", cta.getCodUsuarioSistema());
			storedProcedure.setParameter("PI_FECHA_INI_COBERTURA", cta.getFecInicioAcredi());
			storedProcedure.setParameter("PI_FECHA_FIN_COBERTURA", cta.getFecFinAcredi());

			storedProcedure.execute();

			resultado.add(storedProcedure.getOutputParameterValue("PO_CODIGO").toString());
			resultado.add(storedProcedure.getOutputParameterValue("PO_MENSAJE").toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			createEntityManager().close();
		}
		return resultado;
	}

	@Override
	public List<String> valNuevaCuota(CuentaComplementaria cta) {
		// Double resultado = 0.0;
		List<String> resultado = new ArrayList<String>();
		try {
			StoredProcedureQuery storedProcedure = createEntityManager()
					.createStoredProcedureQuery("USRCSA.PKG_GESTIONPAGOS_SP_NSP_RV.SP_NSP_VAL_NUEVA_CUOTA");
			// set parameters
			storedProcedure.registerStoredProcedureParameter("PI_NUMERO_CONTRATO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_RENOVACION", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_CUOTA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_VENCIMIENTO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_ESSALUD", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_MAS_VIDA", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PO_CODIGO", String.class, ParameterMode.OUT);
			storedProcedure.registerStoredProcedureParameter("PO_MENSAJE", String.class, ParameterMode.OUT);

			storedProcedure.setParameter("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc());
			storedProcedure.setParameter("PI_RENOVACION", cta.getNumRenovaciContrato());
			storedProcedure.setParameter("PI_CUOTA", cta.getNumCuota());
			storedProcedure.setParameter("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago());
			storedProcedure.setParameter("PI_MONTO_ESSALUD", cta.getNumMontoEssalud());
			storedProcedure.setParameter("PI_MONTO_MAS_VIDA", cta.getNumMontoEvida());
			logger.info("PI_NUMERO_CONTRATO " + cta.getNumContratoInscripc());
			logger.info("PI_RENOVACION " + cta.getNumRenovaciContrato());
			logger.info("PI_CUOTA " + cta.getNumCuota());
			logger.info("PI_FECHA_VENCIMIENTO " + cta.getFecVencimiePago());
			logger.info("PI_MONTO_ESSALUD " + cta.getNumMontoEssalud());
			logger.info("PI_MONTO_MAS_VIDA " + cta.getNumMontoEvida());

			storedProcedure.execute();

			// resultado = (Double)
			// storedProcedure.getOutputParameterValue("PO_MONTO_PRIMA");
			resultado.add(storedProcedure.getOutputParameterValue("PO_CODIGO").toString());
			resultado.add(storedProcedure.getOutputParameterValue("PO_MENSAJE").toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			createEntityManager().close();
		}
		return resultado;
	}

	@Override
	public List<String> nuevaCuota(CuentaComplementaria cta) {
		List<String> resultado = new ArrayList<String>();
		try {
			StoredProcedureQuery storedProcedure = createEntityManager()
					.createStoredProcedureQuery("USRCSA.PKG_GESTIONPAGOS_SP_NSP_RV.SP_NSP_INGRESO_NUEVA_CUOTA");
			// set parameters
			storedProcedure.registerStoredProcedureParameter("PI_NUMERO_CONTRATO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_RENOVACION", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_CUOTA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_VENCIMIENTO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_ESSALUD", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_MAS_VIDA", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_USUARIO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_INI_COBERTURA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_FIN_COBERTURA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PO_CODIGO", String.class, ParameterMode.OUT);
			storedProcedure.registerStoredProcedureParameter("PO_MENSAJE", String.class, ParameterMode.OUT);

			storedProcedure.setParameter("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc());
			storedProcedure.setParameter("PI_RENOVACION", cta.getNumRenovaciContrato());
			storedProcedure.setParameter("PI_CUOTA", cta.getNumCuota());
			storedProcedure.setParameter("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago());
			storedProcedure.setParameter("PI_MONTO_ESSALUD", cta.getNumMontoEssalud());
			storedProcedure.setParameter("PI_MONTO_MAS_VIDA", cta.getNumMontoEvida());
			storedProcedure.setParameter("PI_USUARIO", cta.getCodUsuarioSistema());
			storedProcedure.setParameter("PI_FECHA_INI_COBERTURA", cta.getFecInicioAcredi());
			storedProcedure.setParameter("PI_FECHA_FIN_COBERTURA", cta.getFecFinAcredi());

			logger.info("PI_NUMERO_CONTRATO " + cta.getNumContratoInscripc());
			logger.info("PI_RENOVACION " + cta.getNumRenovaciContrato());
			logger.info("PI_CUOTA " + cta.getNumCuota());
			logger.info("PI_FECHA_VENCIMIENTO " + cta.getFecVencimiePago());
			logger.info("PI_MONTO_ESSALUD " + cta.getNumMontoEssalud());
			logger.info("PI_MONTO_MAS_VIDA " + cta.getNumMontoEvida());
			logger.info("PI_USUARIO " + cta.getCodUsuarioSistema());
			logger.info("PI_FECHA_INI_COBERTURA " + cta.getFecInicioAcredi());
			logger.info("PI_FECHA_FIN_COBERTURA " + cta.getFecFinAcredi());

			storedProcedure.execute();

			// resultado = storedProcedure.getOutputParameterValue("PO_MENSAJE").toString();

			resultado.add(storedProcedure.getOutputParameterValue("PO_CODIGO").toString());
			resultado.add(storedProcedure.getOutputParameterValue("PO_MENSAJE").toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			createEntityManager().close();
		}
		return resultado;
	}

	@Override
	public List<String> ingresarPagoCuota(CuentaComplementaria cta) {
		List<String> resultado = new ArrayList<>();
		try {
			StoredProcedureQuery storedProcedure = createEntityManager()
					.createStoredProcedureQuery("USRCSA.PKG_GESTIONPAGOS_SP_NSP_RV.SP_NSP_INGRESO_PAGO_CUOTA");
			// set parameters
			storedProcedure.registerStoredProcedureParameter("PI_RENOVACION", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_CUOTA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_VENCIMIENTO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_NUMERO_CONTRATO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_COD_BANCO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_NUM_OPE", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FEC_PAGO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_ESSALUD", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_MONTO_VIDA", Double.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_USUARIO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_INI_COBERTURA", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_FECHA_FIN_COBERTURA", String.class, ParameterMode.IN);

			storedProcedure.registerStoredProcedureParameter("PO_CODIGO", String.class, ParameterMode.OUT);
			storedProcedure.registerStoredProcedureParameter("PO_MENSAJE", String.class, ParameterMode.OUT);

			storedProcedure.setParameter("PI_RENOVACION", cta.getNumRenovaciContrato());
			storedProcedure.setParameter("PI_CUOTA", cta.getNumCuota());
			storedProcedure.setParameter("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago());
			storedProcedure.setParameter("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc());
			storedProcedure.setParameter("PI_COD_BANCO", cta.getCodAgenciaBancaria());
			storedProcedure.setParameter("PI_NUM_OPE", cta.getNumOperacioBancaria());
			storedProcedure.setParameter("PI_FEC_PAGO", cta.getFecAportePago());
			storedProcedure.setParameter("PI_MONTO_ESSALUD", cta.getNumMontoEssalud());
			storedProcedure.setParameter("PI_MONTO_VIDA", cta.getNumMontoEvida());
			storedProcedure.setParameter("PI_USUARIO", cta.getCodUsuarioSistema());
			storedProcedure.setParameter("PI_FECHA_INI_COBERTURA", cta.getFecInicioAcredi());
			storedProcedure.setParameter("PI_FECHA_FIN_COBERTURA", cta.getFecFinAcredi());
			storedProcedure.execute();

			resultado.add(storedProcedure.getOutputParameterValue("PO_CODIGO").toString());
			resultado.add(storedProcedure.getOutputParameterValue("PO_MENSAJE").toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			createEntityManager().close();
		}
		return resultado;
	}

	@Override
	public String eliminarCuota(String correlativo, String usuario) {
		String resultado = "";
		try {
			StoredProcedureQuery storedProcedure = createEntityManager()
					.createStoredProcedureQuery("USRCSA.PKG_GESTIONPAGOS_SP_NSP_RV.SP_NSP_ELIMINAR_CUOTA");
			// set parameters
			storedProcedure.registerStoredProcedureParameter("CORRELATIVO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("PI_USUARIO", String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter("RESULTADO", String.class, ParameterMode.OUT);

			storedProcedure.setParameter("CORRELATIVO", correlativo);
			storedProcedure.setParameter("PI_USUARIO", usuario);
			storedProcedure.execute();

			resultado = storedProcedure.getOutputParameterValue("RESULTADO").toString();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			createEntityManager().close();
		}
		return resultado;
	}

}
