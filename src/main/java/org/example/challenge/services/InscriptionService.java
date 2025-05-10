package org.example.challenge.services;

import org.example.challenge.entities.Inscription;
import org.example.challenge.repositories.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscriptionService {
    private final InscriptionRepository inscriptionRepository;
    public InscriptionService(InscriptionRepository inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
    }

    public void addInscription(Inscription inscription) {
        inscriptionRepository.save(inscription);
    }
    public List<Inscription> getInscriptionsByStudent(Long id) {
        return inscriptionRepository.findByEtudiantId(id);
    }
}
