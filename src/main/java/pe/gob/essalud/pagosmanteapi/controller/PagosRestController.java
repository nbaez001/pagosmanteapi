package pe.gob.essalud.pagosmanteapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pe.gob.essalud.pagosmanteapi.entity.Compendio;
import pe.gob.essalud.pagosmanteapi.entity.CuentaComplementaria;
import pe.gob.essalud.pagosmanteapi.service.ICronogramaService;

@RestController
@RequestMapping("/api")
public class PagosRestController {
    @Autowired
    ICronogramaService cronogramaService;

    @GetMapping("/pagos/filtrar/{contrato}/{fecVencimiento}/{estado}/{page}")
    @ResponseStatus(HttpStatus.OK)
    public Page<CuentaComplementaria> cargarCronograma(@PathVariable String contrato, @PathVariable String fecVencimiento, @PathVariable String estado, @PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return cronogramaService.cargarListaCronograma(contrato, fecVencimiento, estado, pageable);
    }

    @GetMapping("/pagos/tipo-seguro/{contrato}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> tipoSeguro(@PathVariable String contrato) {
        Map<String, Object> response = new HashMap<>();
        String tipoSeguro = cronogramaService.cargarTipoSeguro(contrato);
        if (tipoSeguro == null) {
            response.put("mensaje", "No se encontro el Tipo de Seguro");
            response.put("error", "Llega como nulo");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);

        }
        response.put("tipoSeguo", tipoSeguro);
        response.put("mensaje", "Existe tipo de seguro");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    @PostMapping("/pagos/cuota")
    public ResponseEntity<?> updateCuota(@RequestBody CuentaComplementaria cuenta){
        Map<String, Object> response = new HashMap<>();
        CuentaComplementaria cta =  cronogramaService.saveCronograma(cuenta);
        response.put("mensaje", "Se actualizo la Cuenta");
        response.put("idCta",cta.getCorrelativoRegistroComplemen());
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }
// cambios
    @PutMapping("/pagos/actcuota")
    public ResponseEntity<?> actualizarCuota(@RequestBody CuentaComplementaria cuenta){
        Map<String, Object> response = new HashMap<>();
        List<String> cta =  cronogramaService.actualizarCuota(cuenta);
        response.put("codigo",cta.get(0).toString());
        response.put("mensaje", cta.get(1).toString());
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @PostMapping("/pagos/nuevacuota")
    public ResponseEntity<?> nuevaCuota(@RequestBody CuentaComplementaria cuenta){
        Map<String, Object> response = new HashMap<>();
        List<String> cta =  cronogramaService.nuevaCuota(cuenta);
        response.put("codigo",cta.get(0).toString());
        response.put("mensaje", cta.get(1).toString());

        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/pagos/actpago")
    public ResponseEntity<?> ingresarPagoCuota(@RequestBody CuentaComplementaria cuenta){
        Map<String, Object> response = new HashMap<>();
        List<String> cta =  cronogramaService.ingresarPagoCuota(cuenta);
        response.put("codigo",cta.get(0));
        response.put("mensaje", cta.get(1));

        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/pagos/estado")
    public ResponseEntity<?> cambiarEstado(@RequestBody CuentaComplementaria cuenta){
        Map<String, Object> response = new HashMap<>();
        String estado =  cronogramaService.cambiarEstado(cuenta);
        response.put("mensaje", "Se presentó problemas para cambiar el estado de la cuota");
        if(estado.equals("1")){
            response.put("mensaje", "Se realizo el cambio de estado de la cuota");
        }
        response.put("codigo",estado);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @GetMapping("/pagos/bancos")
    @ResponseStatus(HttpStatus.OK)
    public List<Compendio> listarBancos() {
        return cronogramaService.listaBancos();
    }

    @DeleteMapping("/pagos/eliminar")
    public ResponseEntity<?> eliminarCuota(@RequestBody CuentaComplementaria cuenta){
        Map<String, Object> response = new HashMap<>();
        String estado =  cronogramaService.eliminarCuota(cuenta);
        response.put("mensaje", "Se presentó problemas al intentar eliminar la cuota");
        if(estado.equals("1")){
            response.put("mensaje", "Se eliminó la cuota");
        }
        response.put("codigo",estado);
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);
    }

    @GetMapping("/pagos/usuario")
    @ResponseStatus(HttpStatus.OK)
    public String getUsuario() {
        return cronogramaService.getUsuario();
    }


}
