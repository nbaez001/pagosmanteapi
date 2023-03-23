package pe.gob.essalud.pagosmanteapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pe.gob.essalud.pagosmanteapi.entity.Tarifa;

@Repository
public interface TarifaRepository extends CrudRepository<Tarifa, Integer> {
}
