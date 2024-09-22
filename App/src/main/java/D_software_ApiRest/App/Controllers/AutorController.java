package D_software_ApiRest.App.Controllers;

import D_software_ApiRest.App.Entities.Autor;
import D_software_ApiRest.App.Services.AutorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/tp5/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(autorService.findAll());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Porfavor intente de nuevo más tarde.\"}");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(autorService.findById(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Porfavor intente de nuevo más tarde.\"}");
        }
    }
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody Autor articulo){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(autorService.save(articulo));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Autor articulo){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(autorService.update(id, articulo));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Porfavor intente de nuevo más tarde.\"}");
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(autorService.delete(id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Porfavor intente más tarde.\"}");
        }
    }
    @PostMapping("/{articuloId}/addLibro/{libroId}")
    public ResponseEntity<?> vincularLibro(
            @PathVariable Long articuloId,
            @PathVariable Long libroId) {
        try {
            Autor articuloActualizado = autorService.vincularLibro(articuloId, libroId);
            return ResponseEntity.ok(articuloActualizado);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
