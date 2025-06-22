package services;

import data.entites.Compte;

public interface CompteService {
    Compte login(String username, String password);
}
