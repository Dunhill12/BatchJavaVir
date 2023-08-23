package batch.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fic_ambassade")
public class FicAmbassade {
        @Id
        @Column(name = "id")
        private Long id;

        @Column(name = "nom")
        private String nom;


    public Long getId() {
        return id;
    }
}

