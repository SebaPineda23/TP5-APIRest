package D_software_ApiRest.App.Services;

import D_software_ApiRest.App.Entities.Autor;
import D_software_ApiRest.App.Entities.Libro;
import D_software_ApiRest.App.Repositories.AutorRepository;
import D_software_ApiRest.App.Repositories.LibroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class AutorService implements BaseService<Autor>{

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LibroRepository libroRepository;

    @Override
    @Transactional
    public List<Autor> findAll() throws Exception {
        try{
            List<Autor> autores = autorRepository.findAll();
            return autores;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Autor findById(Long id) throws Exception {
        try{
            Optional<Autor> autorOptional = autorRepository.findById(id);
            return autorOptional.get();
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Autor save(Autor entity) throws Exception {
        try{
            entity = autorRepository.save(entity);
            return entity;
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Autor update(Long id, Autor entity) throws Exception {
        try{
            Optional<Autor> autorOptional = autorRepository.findById(id);
            if (autorOptional.isPresent()){
                Autor articuloActualizado = autorOptional.get();

                articuloActualizado.setNombre(entity.getNombre());
                articuloActualizado.setApellido(entity.getApellido());
                articuloActualizado.setBiografia(entity.getBiografia());
                return autorRepository.save(articuloActualizado);
            }else{
                throw new Exception("No se encontro un autor con id igual a "+id);
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    @Override
    public boolean delete(Long autorId) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un autor con el ID " + autorId));

        // Desvincular los libros del autor (opcional si quieres eliminar la relación)
        for (Libro libro : autor.getLibros()) {
            libro.setAutor(null);
            libroRepository.save(libro);
        }

        // Eliminar el autor sin borrar los libros
        autorRepository.deleteById(autorId);
        return true;
    }
    @Transactional
    public Autor vincularLibro(Long autorId, Long libroId) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un autor con el ID " + autorId));

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un libro con ID " + libroId));

        // Verificar si el libro ya está vinculado al autor
        if (autor.getLibros().contains(libro)) {
            throw new IllegalArgumentException("El libro con ID "+libroId+" ya está vinculado al autor con ID "+autorId);
        }

        // Vincular el libro al autor
        autor.getLibros().add(libro);
        libro.getAutores().add(autor);

        return autorRepository.save(autor);
    }
}
