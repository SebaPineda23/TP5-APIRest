package D_software_ApiRest.App.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Table(name = "libro")
public class Libro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private int fecha;
    private String genero;
    private int paginas;
    private String autor;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "persona_id")
    private Persona persona;
    @ManyToMany(mappedBy = "libros")
    @JsonIgnore
    private List<Autor> autores = new ArrayList<>();
}
