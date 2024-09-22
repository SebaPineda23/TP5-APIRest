package D_software_ApiRest.App.Controllers;

import D_software_ApiRest.App.Entities.Autor;
import D_software_ApiRest.App.Entities.Domicilio;
import D_software_ApiRest.App.Services.DomicilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/domicilios")
public class DomicilioController {

    @Autowired
    private DomicilioService domicilioService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(domicilioService.findAll());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
       try{
            return ResponseEntity.status(HttpStatus.OK).body(domicilioService.findById(id));
       } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
       }
    }
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Domicilio domicilio){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(domicilioService.save(domicilio));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Domicilio domicilio){
        try{
             return ResponseEntity.status(HttpStatus.OK).body(domicilioService.update(id, domicilio));
        }catch(Exception e){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(domicilioService.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PostMapping("/{domicilioId}/addLocalidad/{localidadId}")
    public ResponseEntity<Domicilio> vincularLocalidad(
            @PathVariable Long domicilioId,
            @PathVariable Long localidadId) {
        Domicilio domicilioActualizado = domicilioService.vincularLocalidad(domicilioId, localidadId);
        return ResponseEntity.ok(domicilioActualizado);
    }
}
