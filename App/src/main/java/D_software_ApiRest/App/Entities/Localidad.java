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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@Table(name = "localidad")
public class Localidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denominacion;
    @OneToMany(mappedBy = "localidad",  cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<Domicilio> domicilios = new ArrayList<>();
}
