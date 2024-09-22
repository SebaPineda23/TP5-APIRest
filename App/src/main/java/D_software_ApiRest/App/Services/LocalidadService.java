package D_software_ApiRest.App.Services;

import D_software_ApiRest.App.Entities.Domicilio;
import D_software_ApiRest.App.Entities.Localidad;
import D_software_ApiRest.App.Repositories.DomicilioRepository;
import D_software_ApiRest.App.Repositories.LocalidadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class LocalidadService implements BaseService<Localidad>{

    @Autowired
    private LocalidadRepository localidadRepository;
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Override
    @Transactional
    public List<Localidad> findAll() throws Exception {
        try {
           List<Localidad> categorias = localidadRepository.findAll();
           return categorias;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Localidad findById(Long id) throws Exception {
        try {
            Optional<Localidad> categoriaOptional = localidadRepository.findById(id);
            return categoriaOptional.get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Localidad save(Localidad entity) throws Exception {
        try {
            entity = localidadRepository.save(entity);
            return entity;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Localidad update(Long id, Localidad entity) throws Exception {
        try{
            Optional<Localidad> categoriaOptional = localidadRepository.findById(id);
            if(categoriaOptional.isPresent()){
                Localidad categoriaActualizada = categoriaOptional.get();

                categoriaActualizada.setDenominacion(entity.getDenominacion());
                return localidadRepository.save(categoriaActualizada);
            }else{
                throw new Exception("No se encontro localidad con el id "+id);
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) throws Exception {
        if (!localidadRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró una localidad con el ID " + id);
        }

        // Eliminar la localidad, lo cual también eliminará los domicilios asociados en cascada.
        localidadRepository.deleteById(id);
        return true;
    }
}
