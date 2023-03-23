package pe.gob.essalud.pagosmanteapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import pe.gob.essalud.pagosmanteapi.dao.ICuentaComplementariaDAO;
import pe.gob.essalud.pagosmanteapi.entity.Compendio;
import pe.gob.essalud.pagosmanteapi.entity.CuentaComplementaria;
import pe.gob.essalud.pagosmanteapi.model.CuentaComplementariaResult;
import pe.gob.essalud.pagosmanteapi.model.Usuario;
import pe.gob.essalud.pagosmanteapi.repository.CompendioRespository;
import pe.gob.essalud.pagosmanteapi.repository.CuentaComplementariaRepository;
import pe.gob.essalud.pagosmanteapi.util.UsuarioAuthoriza;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class CronogramaService implements ICronogramaService{
    @Autowired
    CuentaComplementariaRepository cuentaComplementariaRepository;

    @Autowired
    private ICuentaComplementariaDAO cuentaComplementariaDAO;

    @Autowired
    private CompendioRespository compendioRespository;

    @Autowired
    UsuarioAuthoriza usuarioAuthoriza;

    final Logger logger = LoggerFactory.getLogger(CronogramaService.class);


    public Page<CuentaComplementaria> cargarListaCronograma(String contrato, String fecVencimiento, String estado, Pageable pageable){
        String fv = null;
        /*
        * si fv es distinto de null se busca por fecha
        * si estado es distinto de null se busca por estado
        * si fv es null y estado es null se busca por num contrato
        * */
        if((fecVencimiento.equalsIgnoreCase("null") || fecVencimiento == null)
            && (estado.equalsIgnoreCase("null") || estado == null)
            ){
            return cuentaComplementariaRepository.findAllByNumContratoInscripcOrderByNumRenovaciContratoDescNumCuotaNumDesc(contrato, pageable);
        } else if(!"null".equalsIgnoreCase(fecVencimiento)) {
            fv = fecVencimiento.replace("-","");
            return cuentaComplementariaRepository.findAllByNumContratoInscripcAndFecVencimiePagoGreaterThanEqualOrderByNumRenovaciContratoDescNumCuotaNumDesc(contrato,fv,pageable);
        } else {
            return cuentaComplementariaRepository.findAllByNumContratoInscripcAndCodEstadoAporteOrderByNumRenovaciContratoDescNumCuotaNumDesc(contrato,estado,pageable);
        }
    }
    /*
// VERSION DE LLAMADA A PROCEDIMIENTO
    public Page<CuentaComplementaria> cargarListaCronograma(String contrato, String fecVencimiento, Pageable pageable){
        return (Page)cuentaComplementariaRepository.buscarCronograma(contrato,pageable);
    }
*/
    public String cargarTipoSeguro(String contrato){
        return cuentaComplementariaRepository.getTipoSeguro(contrato);
    }

    @Override
    public CuentaComplementaria saveCronograma(CuentaComplementaria cta) {
        CuentaComplementariaResult result = cuentaComplementariaRepository.getCuentaCompleMax(cta.getNumContratoInscripc()).get();
        cta.setIdeNumericoPersona(result.getIdenumericopersona());
        cta.setIdeNumericoCtaindiv(result.getIdenumericoctaindiv());
        cta.setIdeNumericoTicket(result.getIdenumericoticket());
        cta.setHpafill(result.getHpafill());
        cta.setCodEstadoAporte("C");
        cta.setCodSituacioAporte("V");
       CuentaComplementaria ctaResponse =  cuentaComplementariaRepository.save(cta);
       return ctaResponse;
    }

    public String cambiarEstado(CuentaComplementaria cta){
       logger.info("cta.getCorrelativoRegistroComplemen()::"+cta.getCorrelativoRegistroComplemen());

        Usuario usuario = usuarioAuthoriza.getUsuario();

        logger.info("Usuario:"+ usuario.getCodUsuario());
        logger.info("IdeUsuario:"+ usuario.getIdeUsuario());
        logger.info("PI_RENOVACION:"+ cta.getNumRenovaciContrato());
        logger.info("PI_CUOTA:"+ cta.getNumCuota());

       String corr =  cta.getCorrelativoRegistroComplemen();
       String cambiarestado = cuentaComplementariaDAO.cambiarEstado(corr,usuario.getCodUsuario());

       logger.info("cambiarestado::"+cambiarestado);

       return cambiarestado;
    }

    public List<String> actualizarCuota(CuentaComplementaria cta){
        List<String> resultado = new ArrayList<>();
        List<String> valCuota= cuentaComplementariaDAO.valActualizaCuota(cta);
        logger.info("valCuota:"+valCuota.toString());
        if(valCuota.get(0).equals("1")){
            //actualizo la cuota
            Usuario usuario = usuarioAuthoriza.getUsuario();
            cta.setCodUsuarioSistema(usuario.getCodUsuario());
            List<String> actCuota = cuentaComplementariaDAO.actualizarCuota(cta);

            resultado.add(actCuota.get(0));
            resultado.add(actCuota.get(1));

        } else {
            resultado.add(valCuota.get(0));
            resultado.add(valCuota.get(1));
        }
        return resultado;
    }

    public List<String> nuevaCuota(CuentaComplementaria cta){
        List<String> resultado = new ArrayList<>();
        List<String> valNuevaCuota= cuentaComplementariaDAO.valNuevaCuota(cta);
        logger.info("valNuevaCuota:"+valNuevaCuota.toString());
        if(valNuevaCuota.get(0).equals("1")){
            //actualizo la cuota
            Usuario usuario = usuarioAuthoriza.getUsuario();
            cta.setCodUsuarioSistema(usuario.getCodUsuario());

            List<String> insNuevaCuota = cuentaComplementariaDAO.nuevaCuota(cta);

            resultado.add(insNuevaCuota.get(0));
            resultado.add(insNuevaCuota.get(1));

            logger.info("insNuevaCuotaCod:"+insNuevaCuota.get(0).toString());
            logger.info("insNuevaCuotaMens:"+insNuevaCuota.get(1).toString());

        } else {
            resultado.add(valNuevaCuota.get(0));
            resultado.add(valNuevaCuota.get(1));
        }
        return resultado;

    }

    public List<String> ingresarPagoCuota(CuentaComplementaria cta){
        List<String> resultado = new ArrayList<>();
        String fecPago = "";

        Usuario usuario = usuarioAuthoriza.getUsuario();
        cta.setCodUsuarioSistema(usuario.getCodUsuario());
        fecPago = cta.getFecAportePago().replace("-","");
        cta.setFecAportePago(fecPago);

        List<String> ingPagoCuota = cuentaComplementariaDAO.ingresarPagoCuota(cta);
        resultado.add(ingPagoCuota.get(0));
        resultado.add(ingPagoCuota.get(1));

        return resultado;
    }

    public List<Compendio> listaBancos(){
         List<Compendio> resultado = compendioRespository.getListaBancos();
         return resultado;
    }

    public String eliminarCuota(CuentaComplementaria cta){
        logger.info("cta.getCorrelativoRegistroComplemen()::"+cta.getCorrelativoRegistroComplemen());

        Usuario usuario = usuarioAuthoriza.getUsuario();

        logger.info("Usuario:"+ usuario.getCodUsuario());
        logger.info("IdeUsuario:"+ usuario.getIdeUsuario());
        logger.info("PI_RENOVACION:"+ cta.getNumRenovaciContrato());
        logger.info("PI_CUOTA:"+ cta.getNumCuota());

        String corr =  cta.getCorrelativoRegistroComplemen();
        String eliminaCuota = cuentaComplementariaDAO.eliminarCuota(corr,usuario.getCodUsuario());

        logger.info("cambiarestado::"+eliminaCuota);

        return eliminaCuota;
    }

    public String getUsuario(){
        Usuario usuario = usuarioAuthoriza.getUsuario();
        return usuario.getCodUsuario();
    }


}















