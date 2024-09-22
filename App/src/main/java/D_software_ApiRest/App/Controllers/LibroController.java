package D_software_ApiRest.App.Controllers;


import D_software_ApiRest.App.Entities.Libro;
import D_software_ApiRest.App.Services.LibroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(libroService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(libroService.findById(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Libro libro){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(libroService.save(libro));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Libro libro){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(libroService.update(id, libro));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
             return ResponseEntity.status(HttpStatus.NO_CONTENT).body(libroService.delete(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PostMapping("/{libroId}/addPersona/{personaId}")
    public ResponseEntity<?> addPersona(@PathVariable Long libroId,  @PathVariable Long personaId){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(libroService.addPersona(libroId, personaId));
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
