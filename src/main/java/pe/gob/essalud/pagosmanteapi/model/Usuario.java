package pe.gob.essalud.pagosmanteapi.model;

import lombok.Data;

@Data
public class Usuario {
    private Integer ideUsuario;
    private String codUsuario;
    private String encrytedPassword;
    private boolean flgEstado;
    private Integer ideNumericoPersona;
    private String txtMail;
    private String txtUsuarioModifica;
    private String txtIpusuarioModifica;
    private String fecModifica;
    private String fecUltimoAcceso;
    private Integer numDependencia;
    private String horUltimoAcceso;
    private String fecBaja;
    private String txtRol;
    private String fecCreacion;
}
