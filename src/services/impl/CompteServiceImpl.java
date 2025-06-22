package services.impl;

import data.entites.Compte;
import data.repositories.CompteRepository;
import services.CompteService;

public class CompteServiceImpl implements CompteService {
    private final CompteRepository compteRepository;
    public CompteServiceImpl(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    @Override
    public Compte login(String username, String password) {
        return compteRepository.findByUsernameAndPassword(username, password);
    }
}
