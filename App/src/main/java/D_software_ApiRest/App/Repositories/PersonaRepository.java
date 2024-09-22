package D_software_ApiRest.App.Repositories;


import D_software_ApiRest.App.Entities.Domicilio;

import D_software_ApiRest.App.Entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Persona findByDomicilio(Domicilio domicilio);
}
