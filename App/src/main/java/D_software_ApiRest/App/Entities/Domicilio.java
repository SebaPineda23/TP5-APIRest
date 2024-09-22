package D_software_ApiRest.App.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Data
@Table(name = "domicilio")
public class Domicilio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Calle;
    private int numero;
    @OneToOne(mappedBy = "domicilio")
    @JsonIgnore
    private Persona persona;
    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
    @PreRemove
    private void preRemove() {
        // Desvincula la persona antes de eliminar el domicilio
        if (this.persona != null) {
            this.persona.setDomicilio(null);
        }
    }
}
