import data.entites.Compte;
import data.mocks.DataInitializer;
import data.repositories.CategorieRepository;
import data.repositories.CompteRepository;
import data.repositories.PersonneRepository;
import data.repositories.impl.CategorieRepositoryImpl;
import data.repositories.impl.CompteRepositoryImpl;
import data.repositories.impl.PersonneRepositoryImpl;
import services.CategorieService;
import services.CompteService;
import services.impl.CategorieServiceImpl;
import services.impl.CompteServiceImpl;
import vues.AbstractView;
import vues.CategorieView;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        Hydratation du scanner
        AbstractView.setScanner(scanner);
        CompteRepository compteRepository = new CompteRepositoryImpl();
        PersonneRepository personneRepository = new PersonneRepositoryImpl();
        DataInitializer dataInitializer = new DataInitializer(compteRepository, personneRepository);
        dataInitializer.init();
        CompteService compteService = new CompteServiceImpl(compteRepository);
        CategorieRepository categorieRepository = new CategorieRepositoryImpl();
        CategorieService categorieService = new CategorieServiceImpl(categorieRepository);
        CategorieView categorieView = new CategorieView(categorieService);
        Compte compte = null;
        int choix = 0;
        while (true) {
            compte = getLogin(compteService);
            while (compte != null) {
                switch (compte.getRole()) {
                    case VENDEUR -> {
                        choix = menuVendeur();
                    }
                    case GESTIONNAIRE -> {
                        choix = menuGestionnaire();
                        scanner.nextLine();
                        switch (choix) {
                            case 1 -> categorieView.creerCategorie();
                            case 2 -> categorieView.listerCategorie();
                            case 0 -> {
                                System.out.println("Déconnexion");
                                compte = null;
                            }
                            case -1 -> System.exit(0);
                        }
                    }
                    case RESPONSABLE_STOCK, RESPONSABLE_VENTE -> {
                        compte = null;
                        System.out.println("Encours de developpement");
                    }
                }
            }
        }

    }

    private static Compte getLogin(CompteService compteService) {
        Compte compte;
        while (true) {
            System.out.print("Username : ");
            String username = scanner.nextLine();
            System.out.print("Password : ");
            String password = scanner.nextLine();
            compte = compteService.login(username, password);
            if (compte == null) {
                System.out.println("Wrong Credentials");
                System.out.println("Try Again !!!");
            } else {
                System.out.println(compte);
                System.out.println("Connected successfully");
                break;
            }
        }
        return compte;
    }

    private static int menuGestionnaire() {
        System.out.println("MENU GESTIONNAIRE");
        System.out.println("1. Créer categorie");
        System.out.println("2. Lister categories");
        System.out.println("0. Déconnexion");
        System.out.println("-1. Quitter");
        return scanner.nextInt();

    }

    private static int menuVendeur() {
        System.out.println("Menu Vendeur");
        return 0;
    }

}
