package D_software_ApiRest.App.Services;


import D_software_ApiRest.App.Entities.Autor;
import D_software_ApiRest.App.Entities.Libro;
import D_software_ApiRest.App.Entities.Persona;
import D_software_ApiRest.App.Repositories.AutorRepository;
import D_software_ApiRest.App.Repositories.PersonaRepository;
import D_software_ApiRest.App.Repositories.LibroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class LibroService implements BaseService<Libro>{

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private PersonaRepository personaRepository;

    @Override
    @Transactional
    public List<Libro> findAll() throws Exception {
        try{
            List<Libro> libros = libroRepository.findAll();
            return libros;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro findById(Long id) throws Exception {
        try{
            Optional<Libro> libroOptional = libroRepository.findById(id);
            return libroOptional.get();
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro save(Libro entity) throws Exception {
        try{
            entity = libroRepository.save(entity);
            return entity;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro update(Long id, Libro entity) throws Exception {
        try{
            Optional<Libro> libroOptional = libroRepository.findById(id);
            if(libroOptional.isPresent()){
                Libro libroActualizado = libroOptional.get();
                libroActualizado.setAutor(entity.getAutor());
                libroActualizado.setGenero(entity.getGenero());
                libroActualizado.setPaginas(entity.getPaginas());
                libroActualizado.setTitulo(entity.getTitulo());
                libroActualizado.setFecha(entity.getFecha());
                return libroRepository.save(libroActualizado);
            }else{
                throw new Exception();
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public boolean delete(Long libroId) throws Exception {
        if (!libroRepository.existsById(libroId)) {
            throw new EntityNotFoundException("No se encontró la categoría con ID: " + libroId);
        }

        // Obtener todos los artículos que contienen esta categoría
        List<Autor> autores = autorRepository.findAll(); // O usa un método específico si lo tienes

        for (Autor autor : autores) {
            autor.setLibros(null);
            autorRepository.save(autor);
        }

        // Eliminar la categoría
        libroRepository.deleteById(libroId);
        return true;
    }
    @Transactional
    public Libro addPersona(Long libroId,  Long personaId){
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(()-> new EntityNotFoundException("No se encontró un libro con ID igual a "+libroId));
        Persona persona = personaRepository.findById(personaId)
                        .orElseThrow(()-> new EntityNotFoundException("No se encontró una persona con el ID "+personaId));
        if(libro.getPersona()!= null && libro.getPersona().getId().equals(personaId)){
            throw new IllegalArgumentException("Ya existe un libro con ID "+libroId+" vinculado a una persona con ID "+personaId);
        }
        libro.setPersona(persona);
        return libroRepository.save(libro);
    }
}
