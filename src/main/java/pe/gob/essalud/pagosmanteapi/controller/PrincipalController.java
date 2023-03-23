package pe.gob.essalud.pagosmanteapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import pe.gob.essalud.pagosmanteapi.model.Usuario;
import pe.gob.essalud.pagosmanteapi.util.UsuarioAuthoriza;


@Controller
public class PrincipalController {

    private static final String USUARIO = "usuario";
    
    @Autowired
    UsuarioAuthoriza usuarioAuthoriza;

    @GetMapping(value="cierre")
    public String cargarCierre(Model model){

        Usuario usuario  = usuarioAuthoriza.getUsuario();
//        Usuario usuario = new Usuario();
//        usuario.setCodUsuario("7265573");
        model.addAttribute(USUARIO, usuario);
        return "cierresubsidios/principal";
    }

   /* @GetMapping(value="programacion")
    public String cargarProgramacion(Model model){

        Usuario usuario  = usuarioAuthoriza.getUsuario();
//        Usuario usuario = new Usuario();
//        usuario.setCodUsuario("7265573");
        Programacion programa = new Programacion();
        model.addAttribute(USUARIO, usuario);
        model.addAttribute("programa", programa);
        return "cierresubsidios/programacion";
    }
*/
    @GetMapping(value="consulta-cierre")
    public String cargarConsultaProcesosCierre(Model model){

        Usuario usuario  = usuarioAuthoriza.getUsuario();
//        Usuario usuario = new Usuario();
//        usuario.setCodUsuario("7265573");
        model.addAttribute(USUARIO, usuario);
        return "cierresubsidios/consulta-cierre";
    }

    @GetMapping(value="consulta-reportes")
    public String cargarConsultaReportesArchivos(Model model){

        Usuario usuario  = usuarioAuthoriza.getUsuario();
//        Usuario usuario = new Usuario();
//        usuario.setCodUsuario("7265573");
        model.addAttribute(USUARIO, usuario);
        return "cierresubsidios/consulta-reportes-archivos";
    }

    @GetMapping(value="cronograma")
    public String cargarMantenimientoCronograma(Model model){

        Usuario usuario  = usuarioAuthoriza.getUsuario();
//        Usuario usuario = new Usuario();
//        usuario.setCodUsuario("7265573");
        model.addAttribute(USUARIO, usuario);
        return "cierresubsidios/mantenimiento-cronograma";
    }

}
