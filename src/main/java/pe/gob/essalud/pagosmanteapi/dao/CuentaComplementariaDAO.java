package pe.gob.essalud.pagosmanteapi.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.gob.essalud.pagosmanteapi.entity.CuentaComplementaria;
import pe.gob.essalud.pagosmanteapi.util.ConstanteUtil;
import pe.gob.essalud.pagosmanteapi.util.MapUtil;

@Repository
public class CuentaComplementariaDAO implements ICuentaComplementariaDAO {

	final Logger logger = LoggerFactory.getLogger(CuentaComplementariaDAO.class);

	@Autowired
	DataSource dataSource;

	@Override
	public String cambiarEstado(String correlativo, String usuario) {
		String resultado = "";

		SimpleJdbcCall jdbcCall;
		try {
			jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.BD_SCHEMA_USRCSA)
					.withCatalogName(ConstanteUtil.BD_PKG_GESTIONPAGOS_SP_NSP_RV)
					.withProcedureName("SP_NSP_CAMBIAR_ESTADO");

			// set parameters
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("CORRELATIVO", correlativo, Types.VARCHAR);
			in.addValue("PI_USUARIO", usuario, Types.VARCHAR);
			logger.info("[CAMBIAR ESTADO][DAO][INPUT PROCEDIMIENTO][" + in.toString() + "]");

			Map<String, Object> out = jdbcCall.execute(in);
			logger.info("[CAMBIAR ESTADO][DAO][OUTPUT PROCEDIMIENTO][" + out.toString() + "]");

			resultado = MapUtil.getString(out.get("RESULTADO"));
		} catch (Exception e) {
			logger.info("[CAMBIAR ESTADO][DAO][ERROR]");
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public List<String> valActualizaCuota(CuentaComplementaria cta) {
		List<String> resultado = new ArrayList<String>();

		SimpleJdbcCall jdbcCall;
		try {
			jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.BD_SCHEMA_USRCSA)
					.withCatalogName(ConstanteUtil.BD_PKG_GESTIONPAGOS_SP_NSP_RV)
					.withProcedureName("SP_NSP_VAL_ACTUALIZA_CUOTA");

			// set parameters
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("PI_CORRELATIVO", cta.getCorrelativoRegistroComplemen(), Types.VARCHAR);
			in.addValue("PI_RENOVACION", cta.getNumRenovaciContrato(), Types.VARCHAR);
			in.addValue("PI_CUOTA", cta.getNumCuota(), Types.VARCHAR);
			in.addValue("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago(), Types.VARCHAR);
			in.addValue("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc(), Types.VARCHAR);
			in.addValue("PI_MONTO_ESSALUD", cta.getNumMontoEssalud(), Types.NUMERIC);
			in.addValue("PI_MONTO_MAS_VIDA", cta.getNumMontoEvida(), Types.NUMERIC);
			logger.info("[VAL ACTUALIZAR CUOTA][DAO][INPUT PROCEDIMIENTO][" + in.toString() + "]");

			Map<String, Object> out = jdbcCall.execute(in);
			logger.info("[VAL ACTUALIZAR CUOTA][DAO][OUTPUT PROCEDIMIENTO][" + out.toString() + "]");

			resultado.add(MapUtil.getString(out.get("PO_CODIGO")));
			resultado.add(MapUtil.getString(out.get("PO_MENSAJE")));
		} catch (Exception e) {
			logger.info("[VAL ACTUALIZAR CUOTA][DAO][ERROR]");
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public List<String> actualizarCuota(CuentaComplementaria cta) {
		List<String> resultado = new ArrayList<String>();

		SimpleJdbcCall jdbcCall;
		try {
			jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.BD_SCHEMA_USRCSA)
					.withCatalogName(ConstanteUtil.BD_PKG_GESTIONPAGOS_SP_NSP_RV)
					.withProcedureName("SP_NSP_ACTUALIZA_CUOTA");

			// set parameters
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("PI_RENOVACION", cta.getNumRenovaciContrato(), Types.VARCHAR);
			in.addValue("PI_CUOTA", cta.getNumCuota(), Types.VARCHAR);
			in.addValue("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago(), Types.VARCHAR);
			in.addValue("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc(), Types.VARCHAR);
			in.addValue("PI_MONTO_ESSALUD", cta.getNumMontoEssalud(), Types.NUMERIC);
			in.addValue("PI_MONTO_MAS_VIDA", cta.getNumMontoEvida(), Types.NUMERIC);
			in.addValue("PI_USUARIO", cta.getCodUsuarioSistema(), Types.VARCHAR);
			in.addValue("PI_FECHA_INI_COBERTURA", cta.getFecInicioAcredi(), Types.VARCHAR);
			in.addValue("PI_FECHA_FIN_COBERTURA", cta.getFecFinAcredi(), Types.VARCHAR);
			logger.info("[ACTUALIZAR CUOTA][DAO][INPUT PROCEDIMIENTO][" + in.toString() + "]");

			Map<String, Object> out = jdbcCall.execute(in);
			logger.info("[ACTUALIZAR CUOTA][DAO][OUTPUT PROCEDIMIENTO][" + out.toString() + "]");

			resultado.add(MapUtil.getString(out.get("PO_CODIGO")));
			resultado.add(MapUtil.getString(out.get("PO_MENSAJE")));
		} catch (Exception e) {
			logger.info("[ACTUALIZAR CUOTA][DAO][ERROR]");
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public List<String> valNuevaCuota(CuentaComplementaria cta) {
		// Double resultado = 0.0;
		List<String> resultado = new ArrayList<String>();

		SimpleJdbcCall jdbcCall;
		try {
			jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.BD_SCHEMA_USRCSA)
					.withCatalogName(ConstanteUtil.BD_PKG_GESTIONPAGOS_SP_NSP_RV)
					.withProcedureName("SP_NSP_VAL_NUEVA_CUOTA");

			// set parameters
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc(), Types.VARCHAR);
			in.addValue("PI_RENOVACION", cta.getNumRenovaciContrato(), Types.VARCHAR);
			in.addValue("PI_CUOTA", cta.getNumCuota(), Types.VARCHAR);
			in.addValue("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago(), Types.VARCHAR);
			in.addValue("PI_MONTO_ESSALUD", cta.getNumMontoEssalud(), Types.NUMERIC);
			in.addValue("PI_MONTO_MAS_VIDA", cta.getNumMontoEvida(), Types.NUMERIC);
			logger.info("[VAL NUEVA CUOTA][DAO][INPUT PROCEDIMIENTO][" + in.toString() + "]");

			Map<String, Object> out = jdbcCall.execute(in);
			logger.info("[VAL NUEVA CUOTA][DAO][OUTPUT PROCEDIMIENTO][" + out.toString() + "]");

			resultado.add(MapUtil.getString(out.get("PO_CODIGO")));
			resultado.add(MapUtil.getString(out.get("PO_MENSAJE")));
		} catch (Exception e) {
			logger.info("[VAL NUEVA CUOTA][DAO][ERROR]");
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public List<String> nuevaCuota(CuentaComplementaria cta) {
		List<String> resultado = new ArrayList<String>();

		SimpleJdbcCall jdbcCall;
		try {
			jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.BD_SCHEMA_USRCSA)
					.withCatalogName(ConstanteUtil.BD_PKG_GESTIONPAGOS_SP_NSP_RV)
					.withProcedureName("SP_NSP_INGRESO_NUEVA_CUOTA");

			// set parameters
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc(), Types.VARCHAR);
			in.addValue("PI_RENOVACION", cta.getNumRenovaciContrato(), Types.VARCHAR);
			in.addValue("PI_CUOTA", cta.getNumCuota(), Types.VARCHAR);
			in.addValue("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago(), Types.VARCHAR);
			in.addValue("PI_MONTO_ESSALUD", cta.getNumMontoEssalud(), Types.NUMERIC);
			in.addValue("PI_MONTO_MAS_VIDA", cta.getNumMontoEvida(), Types.NUMERIC);
			in.addValue("PI_USUARIO", cta.getCodUsuarioSistema(), Types.VARCHAR);
			in.addValue("PI_FECHA_INI_COBERTURA", cta.getFecInicioAcredi(), Types.VARCHAR);
			in.addValue("PI_FECHA_FIN_COBERTURA", cta.getFecFinAcredi(), Types.VARCHAR);
			logger.info("[NUEVA CUOTA][DAO][INPUT PROCEDIMIENTO][" + in.toString() + "]");

			Map<String, Object> out = jdbcCall.execute(in);
			logger.info("[NUEVA CUOTA][DAO][OUTPUT PROCEDIMIENTO][" + out.toString() + "]");

			resultado.add(MapUtil.getString(out.get("PO_CODIGO")));
			resultado.add(MapUtil.getString(out.get("PO_MENSAJE")));
		} catch (Exception e) {
			logger.info("[NUEVA CUOTA][DAO][ERROR]");
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public List<String> ingresarPagoCuota(CuentaComplementaria cta) {
		List<String> resultado = new ArrayList<>();

		SimpleJdbcCall jdbcCall;
		try {
			jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.BD_SCHEMA_USRCSA)
					.withCatalogName(ConstanteUtil.BD_PKG_GESTIONPAGOS_SP_NSP_RV)
					.withProcedureName("SP_NSP_INGRESO_PAGO_CUOTA");

			// set parameters
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("PI_RENOVACION", cta.getNumRenovaciContrato(), Types.VARCHAR);
			in.addValue("PI_CUOTA", cta.getNumCuota(), Types.VARCHAR);
			in.addValue("PI_FECHA_VENCIMIENTO", cta.getFecVencimiePago(), Types.VARCHAR);
			in.addValue("PI_NUMERO_CONTRATO", cta.getNumContratoInscripc(), Types.VARCHAR);
			in.addValue("PI_COD_BANCO", cta.getCodAgenciaBancaria(), Types.VARCHAR);
			in.addValue("PI_NUM_OPE", cta.getNumOperacioBancaria(), Types.VARCHAR);
			in.addValue("PI_FEC_PAGO", cta.getFecAportePago(), Types.VARCHAR);
			in.addValue("PI_MONTO_ESSALUD", cta.getNumMontoEssalud(), Types.NUMERIC);
			in.addValue("PI_MONTO_VIDA", cta.getNumMontoEvida(), Types.NUMERIC);
			in.addValue("PI_USUARIO", cta.getCodUsuarioSistema(), Types.VARCHAR);
			in.addValue("PI_FECHA_INI_COBERTURA", cta.getFecInicioAcredi(), Types.VARCHAR);
			in.addValue("PI_FECHA_FIN_COBERTURA", cta.getFecFinAcredi(), Types.VARCHAR);
			logger.info("[CAMBIAR ESTADO][DAO][INPUT PROCEDIMIENTO][" + in.toString() + "]");

			Map<String, Object> out = jdbcCall.execute(in);
			logger.info("[CAMBIAR ESTADO][DAO][OUTPUT PROCEDIMIENTO][" + out.toString() + "]");

			resultado.add(MapUtil.getString(out.get("PO_CODIGO")));
			resultado.add(MapUtil.getString(out.get("PO_MENSAJE")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}

	@Override
	public String eliminarCuota(String correlativo, String usuario) {
		String resultado = "";

		SimpleJdbcCall jdbcCall;
		try {
			jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName(ConstanteUtil.BD_SCHEMA_USRCSA)
					.withCatalogName(ConstanteUtil.BD_PKG_GESTIONPAGOS_SP_NSP_RV)
					.withProcedureName("SP_NSP_ELIMINAR_CUOTA");

			// set parameters
			MapSqlParameterSource in = new MapSqlParameterSource();
			in.addValue("CORRELATIVO", correlativo, Types.VARCHAR);
			in.addValue("PI_USUARIO", usuario, Types.VARCHAR);
			logger.info("[ELIMINAR CUOTA][DAO][INPUT PROCEDIMIENTO][" + in.toString() + "]");

			Map<String, Object> out = jdbcCall.execute(in);
			logger.info("[ELIMINAR CUOTA][DAO][OUTPUT PROCEDIMIENTO][" + out.toString() + "]");

			resultado = MapUtil.getString(out.get("RESULTADO"));
		} catch (Exception e) {
			logger.info("[ELIMINAR CUOTA][DAO][ERROR]");
			e.printStackTrace();
		}
		return resultado;
	}

}
