package D_software_ApiRest.App.Controllers;


import D_software_ApiRest.App.Entities.Persona;
import D_software_ApiRest.App.Services.PersonaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.findById(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Persona persona){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.save(persona));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Persona persona){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(personaService.update(id, persona));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(personaService.delete(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PostMapping("/{personaId}/addDomicilio/{domicilioId}")
    public ResponseEntity<?> addDomicilio(@PathVariable Long personaId, @PathVariable Long domicilioId) {
        try {
            Persona personaActualizada = personaService.addDomicilio(personaId, domicilioId);
            return ResponseEntity.ok(personaActualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Aquí devolvemos el mensaje original de la excepción
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
