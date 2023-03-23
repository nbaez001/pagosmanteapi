package pe.gob.essalud.pagosmanteapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pe.gob.essalud.pagosmanteapi.entity.Inscripcion;

@Repository
public interface InscripcionRepository extends CrudRepository<Inscripcion, Long> {
    public Inscripcion findByNumContratoInscripc(String contrato);
}
