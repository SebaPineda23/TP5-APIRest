package D_software_ApiRest.App.Controllers;

import D_software_ApiRest.App.Entities.Localidad;
import D_software_ApiRest.App.Services.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/localidades")
public class LocalidadController {
    @Autowired
    private LocalidadService localidadService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(localidadService.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(localidadService.findById(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente más tarde\"}");
        }
    }
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Localidad categoria){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(localidadService.save(categoria));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Localidad categoria){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(localidadService.update(id, categoria));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(localidadService.delete(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
}
