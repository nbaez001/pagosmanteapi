package gob.pe.essalud.pagosmanteapi.repository;

import gob.pe.essalud.pagosmanteapi.entity.Inscripcion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends CrudRepository<Inscripcion, Long> {
    public Inscripcion findByNumContratoInscripc(String contrato);
}
