package org.example.challenge.entities;

import jakarta.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String titre;
    @Column(nullable = false)
    String matiere;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  User enseignant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public User getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(User enseignant) {
        this.enseignant = enseignant;
    }

    public Course(String titre, String matiere, User enseignant) {
        this.titre = titre;
        this.matiere = matiere;
        this.enseignant = enseignant;
    }
    public Course() {
        super();
    }
}
