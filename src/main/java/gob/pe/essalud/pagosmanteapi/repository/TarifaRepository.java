package gob.pe.essalud.pagosmanteapi.repository;

import gob.pe.essalud.pagosmanteapi.entity.Tarifa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends CrudRepository<Tarifa, Integer> {
}
