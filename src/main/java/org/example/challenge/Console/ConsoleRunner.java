package org.example.challenge.Console;

import org.example.challenge.entities.Course;
import org.example.challenge.entities.Inscription;
import org.example.challenge.entities.User;
import org.example.challenge.services.CourseService;
import org.example.challenge.services.InscriptionService;
import org.example.challenge.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final UserService userService;
    private final CourseService courseService;
    private final InscriptionService inscriptionService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleRunner(UserService userService, CourseService courseService, InscriptionService inscriptionService) {
        this.userService = userService;
        this.courseService = courseService;
        this.inscriptionService = inscriptionService;
    }

    @Override
    public void run(String... args) {
        // creation d'un enseignant
        User enseignant = new User();
        enseignant.setNom("AFIFI Saad");
        enseignant.setEmail("afifisaad8@gmail.com");
        enseignant.setMotDePasse("1234");
        enseignant.setRole("ENSEIGNANT");
        enseignant = userService.addUser(enseignant);
        // creation d'un etudiant
        User student = new User();
        student.setNom("AMINE Reda");
        student.setEmail("reda@gmail.com");
        student.setMotDePasse("1234");
        student.setRole("ETUDIANT");
        student = userService.addUser(student);
        while (true) {
            System.out.println("\n=== SmartClass Console ===");
            System.out.println("1. Connexion");
            System.out.println("2. S'inscrire");
            System.out.println("3. Quitter");
            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    login();
                    break;
                case "2":
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Email : ");
                    String email = scanner.nextLine();
                    System.out.print("Mot de passe : ");
                    String motDePasse = scanner.nextLine();
                    System.out.print("Role (ENSEIGNANT/ETUDIANT) : ");
                    String role = scanner.nextLine();
                    User newUser = new User(nom, email, motDePasse, role);
                    userService.addUser(newUser);
                    System.out.println("Utilisateur cree : " + nom);
                    break;
                case "3":
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez reessayer.");
                    break;
            }
        }
    }

    private void login() {
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String motDePasse = scanner.nextLine();

        User user = userService.authenticate(email, motDePasse);
        if (user == null) {
            System.out.println("Echec de la connexion. Email ou mot de passe incorrect.");
            return;
        }
        if (user.getRole().equalsIgnoreCase("ENSEIGNANT")) {
            affichageMenuProf(user);
        } else if (user.getRole().equalsIgnoreCase("ETUDIANT")) {
            showStudentMenu(user);
        } else {
            System.out.println("Rôle invalide.");
        }
    }

    private void affichageMenuProf(User enseignant) {
        while (true) {
            System.out.println("\n=== Menu Enseignant ===");
            System.out.println("1. Créer un cours");
            System.out.println("2. Lister mes cours");
            System.out.println("3. Supprimer un cours");
            System.out.println("4. Deconnexion");
            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    System.out.print("Titre du cours : ");
                    String titre = scanner.nextLine();
                    System.out.print("Matière : ");
                    String matiere = scanner.nextLine();
                    Course course = new Course(titre, matiere, enseignant);
                    courseService.addCourse(course);
                    System.out.println("Cours cree : " + titre);
                    break;
                case "2":
                    List<Course> courses = courseService.getCoursesByEnseignant(enseignant.getId());
                    if (courses.isEmpty()) {
                        System.out.println("Aucun cours trouvé.");
                    } else {
                        courses.forEach(c -> System.out.println(c.getId() + ": " + c.getTitre() + " (" + c.getMatiere() + ")"));
                    }
                    break;
                case "3":
                    System.out.print("ID du cours a supprimer : ");
                    try {
                        Long courseId = Long.parseLong(scanner.nextLine());
                        if (courseService.getCourseById(courseId) != null) {
                            courseService.deleteCourse(courseId);
                            System.out.println("Cours supprime.");
                        } else {
                            System.out.println("Cours introuvable.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID invalide.");
                    }
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }
        }
    }

    private void showStudentMenu(User student) {
        while (true) {
            System.out.println("\n=== Menu Etudiant ===");
            System.out.println("1. Lister tous les cours disponibles");
            System.out.println("2. S'inscrire à un cours");
            System.out.println("3. Voir mes cours inscrits");
            System.out.println("4. Déconnexion");
            System.out.print("Choix : ");
            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    List<Course> courses = courseService.getAllCourses();
                    if (courses.isEmpty()) {
                        System.out.println("Aucun cours disponible.");
                    } else {
                        courses.forEach(c -> System.out.println(c.getId() + ": " + c.getTitre() + " (" + c.getMatiere() + ")"));
                    }
                    break;
                case "2":
                    System.out.print("ID du cours : ");
                    try {
                        Long courseId = Long.parseLong(scanner.nextLine());
                        Course course = courseService.getCourseById(courseId);
                        if (course != null) {
                            Inscription inscription = new Inscription(null, student, course);
                            inscriptionService.addInscription(inscription);
                            System.out.println("Inscription reussie au cours : " + course.getTitre());
                        } else {
                            System.out.println("Cours introuvable.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ID invalide.");
                    }
                    break;
                case "3":
                    List<Inscription> inscriptions = inscriptionService.getInscriptionsByStudent(student.getId());
                    if (inscriptions.isEmpty()) {
                        System.out.println("Aucun cours inscrit.");
                    } else {
                        inscriptions.forEach(i -> System.out.println(i.getCours().getId() + ": " + i.getCours().getTitre()));
                    }
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }
        }
    }
}