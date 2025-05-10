package org.example.challenge.entities;

import jakarta.persistence.*;

@Entity
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User etudiant;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course cours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(User etudiant) {
        this.etudiant = etudiant;
    }

    public Course getCours() {
        return cours;
    }

    public void setCours(Course cours) {
        this.cours = cours;
    }

    public Inscription(Long id, User etudiant, Course cours) {
        this.id = id;
        this.etudiant = etudiant;
        this.cours = cours;
    }
    public Inscription() {
        super();
    }
}
