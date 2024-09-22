package D_software_ApiRest.App.Services;

import D_software_ApiRest.App.Entities.Domicilio;
import D_software_ApiRest.App.Entities.Libro;
import D_software_ApiRest.App.Entities.Persona;
import D_software_ApiRest.App.Repositories.PersonaRepository;
import D_software_ApiRest.App.Repositories.DomicilioRepository;
import D_software_ApiRest.App.Repositories.LibroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaService implements BaseService<Persona>{

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private DomicilioRepository domicilioRepository;
    @Autowired
    private LibroRepository libroRepository;

    @Override
    @Transactional
    public List<Persona> findAll() throws Exception {
        try{
            List<Persona> personas= personaRepository.findAll();
            return personas;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Persona findById(Long id) throws Exception {
        try{
            Optional<Persona> personaOptional = personaRepository.findById(id);
            return personaOptional.get();
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Persona save(Persona entity) throws Exception {
        try{
            entity = personaRepository.save(entity);
            return entity;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Persona update(Long id, Persona entity) throws Exception {
        try{
            Optional<Persona> clienteOptional = personaRepository.findById(id);
            if (clienteOptional.isPresent()){
                Persona personaActualizado = clienteOptional.get();
                personaActualizado.setNombre(entity.getNombre());
                personaActualizado.setApellido(entity.getApellido());
                personaActualizado.setDni(entity.getDni());
                return personaRepository.save(personaActualizado);
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(Long personaId) throws Exception {
        if (!personaRepository.existsById(personaId)) {
            throw new EntityNotFoundException("No se encontró a una persona con ID: " + personaId);
        }

        // Obtener todas las facturas asociadas al cliente específico
        List<Libro> libros = libroRepository.findByPersonaId(personaId); // Usa el método específico que implementaste

        for (Libro libro : libros) {
            libro.setPersona(null); // Desvincular el cliente
        }

        // Guardar todas las facturas actualizadas de una sola vez
        libroRepository.saveAll(libros);

        // Eliminar el cliente
        personaRepository.deleteById(personaId);
        return true;
    }

    @Transactional
    public Persona addDomicilio(Long personaId, Long domicilioId) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró una persona con el id " + personaId));
        Domicilio domicilio = domicilioRepository.findById(domicilioId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un domicilio con el id " + domicilioId));

        // Verificar si la persona ya tiene un domicilio vinculado
        if (persona.getDomicilio() != null && persona.getDomicilio().getId().equals(domicilioId)) {
            throw new IllegalArgumentException("La persona con ID "+personaId+" ya tiene el domicilio vinculado con ID "+domicilioId);
        }

        persona.setDomicilio(domicilio);
        return personaRepository.save(persona);
    }
}
