import java.util.Scanner;
/*
        INF 1004 Structures de données et Algorithmes Devoir 3
        Auteurs : Marco Guilmette, Jala Aymeric, Mathieu Larouche

        Le programme permet : Inscription d’un étudiant à un cours,
                              Annulation de l’inscription d’un étudiant à un cours,
                              Modification de l’inscription : annuler une inscription à un cours et le réinscrire à un autre cours,
                              Obtention de la liste des cours suivis par l’étudiant,
                              Obtention de la liste des étudiants inscrits à un cours donné.
                              Sauvegarde des informations relatives aux étudiants, aux cours et aux inscriptions dans des fichiers.
                              Lecture des informations relatives aux étudiants, aux cours et aux inscriptions à partir des  fichiers.
 */
public class Homework3 {

    private static University university = new University();


    /*
        Méthode main : Menu
        La méthode fera appel à une autre méethode appropriée au traitement de la demande de l'utilisateur.

        Input : Choix de l'utilisateur.
        Retour : void.
     */
    public static void main(String[] args) {


        //Homework3.generateData();  //Uncomment this line to read data from code instead of file

        char menuInput;
        Scanner scanner = new Scanner(System.in);

        //Affichage du menu principal
        do {
            System.out.println("******************************************************");
            System.out.println("* [L] = Lecture des données à partir d'un fichier    *");
            System.out.println("* [I] = Inscription de l'étudiant                    *");
            System.out.println("* [E] = Liste des étudiants inscrits à un cours      *");
            System.out.println("* [C] = Liste des cours suivis par l'étudiant        *");
            System.out.println("* [S] = Sauvegarde des données dans un fichier       *");
            System.out.println("******************************************************");
            System.out.println("* [Q] = Quitter le programme                         *");
            System.out.println("******************************************************");

            menuInput = scanner.next().toUpperCase().charAt(0);

            switch (menuInput) {
                case 'L': Homework3.readDataFromFile();
                    break;
                case 'I': Homework3.manageInscriptions(scanner);
                    break;
                case 'E': Homework3.showStudentsForCourse(scanner);
                    break;
                case 'C': Homework3.showCoursesForStudent(scanner);
                    break;
                case 'S': Homework3.saveDataToFile();
                    break;
                case 'Q': //Do nothing, program will close...
                    break;
            }
        }
        while(menuInput != 'Q') ;

        //Closing the scanner
        scanner.close();

    }

    /*
        Méthode readDataFromFile() : Lis les informations d'un fichier
        Méthode appellée par la méthode main.

        Retour : void.
     */
    private static void readDataFromFile() {
        university.loadDataFromFile();
    }


    /*
        Méthode saveDataToFile() : Sauvegarde les données dans un fichier.
        Méthode appellée par la méthode main.

        Retour : void.
     */
    private static void saveDataToFile(){
        university.saveDataToFile();
    }


    /*
        Méthode  manageInscriptions(Scanner scanner) : Gère les inscriptions des étudiants aux cours.
        Méthode appellée par la méthode main.

        Input : Choix de l'utilisateur.
        Retour : void.
     */
    private static void manageInscriptions(Scanner scanner){
        char menuInput;
        do {
            System.out.println("******************************************************");
            System.out.println("* [A] = Ajouter une inscription                      *");
            System.out.println("* [R] = Retirer une inscription                      *");
            System.out.println("* [M] = Modifier une inscrition                      *");
            System.out.println("******************************************************");
            System.out.println("* [Q] = Quitter le menu                              *");
            System.out.println("******************************************************");

            menuInput = scanner.next().toUpperCase().charAt(0);

            switch (menuInput) {
                case 'A': Homework3.addInscription(scanner);
                    break;
                case 'R': Homework3.removeInscription(scanner);
                    break;
                case 'M': Homework3.updateInscription(scanner);
                    break;
                case 'Q': //Do nothing, menu will quit...
                    break;
            }
        }
        while(menuInput != 'Q') ;
    }


    /*
        Méthode addInscription(Scanner scanner) : Ajoute une inscription .
        Méthode appellée par la méthode manageInscriptions(Scanner scanner).

        Input : Choix d'un étudiant à inscrire, choix d'un cours à inscrire.
        Retour : True si l'inscription s'est effectuée avec succès; false sinon.
     */
    private static boolean addInscription(Scanner scanner) {

        boolean added = false;

        System.out.println("Sélection d'un étudiant à inscrire");
        System.out.println("**********************************");
        int studentIndex = Homework3.getStudentIndex(scanner);

        if (studentIndex >= 0) {
            System.out.println("Sélection d'un cours à inscrire");
            System.out.println("*******************************");
            int courseIndex = Homework3.getCourseIndex(scanner);

            if (courseIndex >= 0) {

                if (university.addInscription(studentIndex, courseIndex)) {
                    System.out.println("Étudiant inscrit avec succès !");
                    university.showStudentsForCourseIndex(courseIndex);
                    added = true;
                }
            }
        }

        return added;
    }


    /*
        Méthode removeInscription(Scanner scanner) : Supprime une inscription d'un étudiant à un cours.
        Méthode applée par la méthode manageInscriptions(Scanner scanner).

        Input : Choix d'un étudiant à désinscrire, choix d'un cours à désinscrire.
        Retour : true si la suppression s'est effectuée avec succès.
     */
    private static boolean removeInscription(Scanner scanner) {

        boolean removed = false;

        System.out.println("Sélection d'un étudiant");
        System.out.println("***********************");
        int studentIndex = Homework3.getStudentIndex(scanner);

        if (studentIndex >= 0) {
            System.out.println("Sélection d'un cours à désinscrire");
            System.out.println("**********************************");
            int courseIndex = Homework3.getCourseIndex(scanner);

            if (courseIndex >= 0) {
                if (university.removeInscription(studentIndex, courseIndex)) {

                    System.out.println("Étudiant désinscrit avec succès !");
                    university.showStudentsForCourseIndex(courseIndex);
                    removed = true;
                } else {
                    System.out.print("ERREUR: Cet étudiant n'était pas inscrit à ce cours");
                }
            }
        }

        return removed;
    }


    /*
        Méthode updateInscription(Scanner scanner) : Met à jour une inscription.
        Méthode applée par la méthode manageInscriptions(Scanner scanner).

        Input : Selection d'un étudiant, d'un cours à désinscrire, sélection d'un
                cours à inscrire.
        Retour : void.
     */
    private static void updateInscription(Scanner scanner) {
        System.out.println("Sélection d'un étudiant");
        System.out.println("***********************");
        int studentIndex = Homework3.getStudentIndex(scanner);

        if (studentIndex >= 0) {
            System.out.println("Sélection d'un cours à désinscrire");
            System.out.println("**********************************");
            int sourceCourseIndex = Homework3.getCourseIndex(scanner);

            if (sourceCourseIndex >= 0) {
                if (university.removeInscription(studentIndex, sourceCourseIndex)) {

                    System.out.println("Sélection d'un cours à inscrire");
                    System.out.println("*******************************");
                    int destinationCourseIndex = Homework3.getCourseIndex(scanner);

                    if (destinationCourseIndex >= 0) {

                        if (university.addInscription(studentIndex, destinationCourseIndex)) {
                            System.out.println("Étudiant inscrit avec succès !");
                            university.showStudentsForCourseIndex(destinationCourseIndex);
                        }
                    }

                } else {
                    System.out.print("ERREUR: Cet étudiant n'était pas inscrit à ce cours");
                }
            }
        }
    }


    /*
        Méthode showStudentsForCourse(Scanner scanner)
        Affiche la liste des étudiants inscrits à un cours.
        Méthode appelée par la méthode main.

        Input : Index du cours.
        Retour : void.
     */
    private static void showStudentsForCourse(Scanner scanner){


        int index = Homework3.getCourseIndex(scanner);
        if (index >= 0) {
            university.showStudentsForCourseIndex(index);
        }

    }


    /*
        Méthode showCoursesForStudent(Scanner scanner)
        Affiche la liste des cours d'un certain étudiant.
        Méthode appelée par la méthode main.

        Input : Index de l'étudiant.
        Retour : void.
     */
    private static void showCoursesForStudent(Scanner scanner){

        int index = Homework3.getStudentIndex(scanner);
        if (index >= 0) {
            university.showCoursesForStudentIndex(index);
        }
    }


    /*
        Méthode getStudentIndex(Scanner scanner)

        Input : Choix d'un étudiant.
        Retour : Index de l'étudiant.
     */
    private static int getStudentIndex(Scanner scanner) {

        int studentInput = -1;
        //Scanner scanner = new Scanner(System.in);

        if(university.getStudents().size() > 0) {
            boolean validInput;

            do {
                for (int i = 0; i < university.getStudents().size(); i++) {
                    System.out.println("[" + i + "] " + university.getStudents().get(i));
                }
                System.out.println("\nVeuillez choisir un étudiant [0 à " + (university.getStudents().size() - 1) + "] : ");

                studentInput = scanner.nextInt();
                if ((0 <= studentInput) && (studentInput < university.getStudents().size())) {
                    validInput = true;
                } else {
                    System.out.println("ERREUR: Entrée invalide [" + studentInput + "]");
                    validInput = false;
                }

            } while (! validInput);

        } else {
            System.out.println("Il n'y a actuellement aucun étudiant à l'université");
        }

        //scanner.close();
        return studentInput;
    }


    /*
        Méthode getCourseIndex(Scanner scanner)

        Input : Choix d'un cours.
        Retour : Index du cours.
     */
    private static int getCourseIndex(Scanner scanner) {

        int courseInput = -1;
        //Scanner scanner = new Scanner(System.in);

        if(university.getCourses().size() > 0) {
            boolean validInput;

            do {
                for (int i = 0; i < university.getCourses().size(); i++) {
                    System.out.println("[" + i + "] " + university.getCourses().get(i));
                }
                System.out.println("\nVeuilez choisir un cours [0 à " + (university.getCourses().size() - 1) + "] : ");

                courseInput = scanner.nextInt();
                if ((0 <= courseInput) && (courseInput < university.getCourses().size())) {
                    validInput = true;
                } else {
                    System.out.println("ERREUR: Entrée invalide [" + courseInput + "]");
                    validInput = false;
                }

            } while (! validInput);

        } else {
            System.out.println("Il n'y a actuellement aucun cours de disponible");
        }

        //scanner.close();
        return courseInput;
    }



    private static void generateData(){

        Course INF1001 = new Course("INF1001", "Programmation Web", 4);
        Course INF1002 = new Course("INF1002", "Introduction à la programmation objet", 40);
        Course INF1004 = new Course("INF1004", "Structures de données et algorithmes", 40, INF1002);
        Course INF1006 = new Course("INF1006", "Analyse et modélisation", 40, INF1004);
        Course INF1007 = new Course("INF1007", "Conception de logiciels", 40, INF1006);
        Course PIF1005 = new Course("PIF1005", "Mathématiques pour informaticiens I", 40);
        Course PIF1006 = new Course("PIF1006", "Mathématiques pour informaticiens II", 40, PIF1005);
        Course INF1008 = new Course("INF1008", "Analyse et conception d'algorithmes", 40, PIF1005, INF1004);
        Course SIF1015 = new Course("SIF1015", "Systèmes d'exploitation", 40);
        Course INF1009 = new Course("INF1009", "Réseaux d'ordinateurs I", 40, SIF1015);
        Course INF1010 = new Course("INF1010", "Réseaux d'ordinateurs II", 40, INF1009);
        Course INF1011 = new Course("INF1011", "Génie logiciel", 40, INF1007);
        Course INF1014 = new Course("INF1014", "Aspects juridiques de l'informatique (1 crédit)", 40);
        Course INF1015 = new Course("INF1015", "Stage d'informatique I", 40);
        Course INF1016 = new Course("INF1016", "Stage d'informatique II", 40);
        Course INF1034 = new Course("INF1034", "Introduction aux interfaces utilisateur", 40, INF1002);
        Course INF1035 = new Course("INF1035", "Concepts avancés en objet",40, INF1004);
        Course SIF1053 = new Course("SIF1053", "Architecture des ordinateurs", 40);
        Course SMI1001 = new Course("SMI1001", "Bases de données I", 40);
        Course SMI1002 = new Course("SMI1002", "Bases de données II", 40, SMI1001);
        Course STT1001 = new Course("STT1001", "Probabilités et statistiques", 40);
        Course TIN1003 = new Course("TIN1003", "Science, technologie et société (2 crédits)", 40);

        //Cours optionnels (21 crédits)
        //L'étudiant doit suivre les neuf (9) crédits de cours d'informatique suivants :

        Course INF1020 = new Course("INF1020", "Commerce électronique", 40);
        Course INF1030 = new Course("INF1030", "Introduction au développement d'applications mobiles (version Android)", 40, INF1035);
        Course INF1031 = new Course("INF1031", "Développement d'applications mobiles avancées (version Android)", 40, INF1030);

        //L'étudiant doit suivre six (6) ou neuf (9) crédits parmi les cours d'informatique suivants :

        Course INF1021 = new Course("INF1021", "Les architectures réparties", 40, INF1009);
        Course INF1032 = new Course("INF1032", "Introduction au développement d'applications mobiles (plateforme IOS)", 40);
        Course INF1033 = new Course("INF1033", "Sujets spéciaux en développement de logiciel ou en développement d'applic. mobiles", 40);
        Course SIF1032 = new Course("SIF1032", "Graphisme par ordinateur", 40, PIF1006, INF1004);

        //L'étudiant peut suivre zéro (0) ou trois (3) crédits parmi les cours d'informatique du bloc de cours optionnels du cheminement développement de logiciels.

        Course INF1012 = new Course("INF1012", "Élaboration et gestion de projets scientifiques", 40, INF1011);
        Course INF1013 = new Course("INF1013", "Environnement de développement et composantes logicielles", 40, INF1035, SIF1015);
        Course INF1018 = new Course("INF1018", "Analyse de programmes", 40, INF1035);
        Course PRO1027 = new Course("PRO1027", "Programmation scientifique en C", 40, INF1002);

        //L'étudiant doit suivre trois (3) crédits de cours d'enrichissement parmi la liste suivante :

        Course ANG1015 = new Course("ANG1015", "English in the workspace", 40);
        Course ANG1017 = new Course("ANG1017", "English for Academic and Professional Purposes", 40, ANG1015);
        Course ANG1019 = new Course("ANG1019", "Everyday English I", 40);
        Course ANG1020 = new Course("ANG1020", "Written English for Business", 40);
        Course ANG1036 = new Course("ANG1036", "Grammar", 40);
        Course ESP1012 = new Course("ESP1012", "Espagnol écrit et parlé I", 40);
        Course ESP1013 = new Course("ESP1013", "Espagnol écrit et parlé II", 40, ESP1012);
        Course ESP1017 = new Course("ESP1017", "Espagnol intermédiaire I", 40, ESP1013);

        university.addCourse(INF1001);
        university.addCourse(INF1002);
        university.addCourse(INF1004);
        university.addCourse(INF1006);
        university.addCourse(INF1007);
        university.addCourse(PIF1005);
        university.addCourse(PIF1006);
        university.addCourse(INF1008);
        university.addCourse(SIF1015);
        university.addCourse(INF1009);
        university.addCourse(INF1010);
        university.addCourse(INF1011);
        university.addCourse(INF1014);
        university.addCourse(INF1015);
        university.addCourse(INF1016);
        university.addCourse(INF1034);
        university.addCourse(INF1035);
        university.addCourse(SIF1053);
        university.addCourse(SMI1001);
        university.addCourse(SMI1002);
        university.addCourse(STT1001);
        university.addCourse(TIN1003);

        university.addCourse(INF1020);
        university.addCourse(INF1030);
        university.addCourse(INF1031);

        university.addCourse(INF1021);
        university.addCourse(INF1032);
        university.addCourse(INF1033);
        university.addCourse(SIF1032);

        university.addCourse(INF1012);
        university.addCourse(INF1013);
        university.addCourse(INF1018);
        university.addCourse(PRO1027);

        university.addCourse(ANG1015);
        university.addCourse(ANG1017);
        university.addCourse(ANG1019);
        university.addCourse(ANG1020);
        university.addCourse(ANG1036);
        university.addCourse(ESP1012);
        university.addCourse(ESP1013);
        university.addCourse(ESP1017);

        //String permanentCode, String lastName, String firstName, String programNumber, int credits, float cumulativeAverage, int numberOfInscriptions)
        university.addStudent(new Student("CHOM02057803", "Choinière-Guilmette", "Marco", "7833", 21, 3.9f, 4));
        university.addStudent(new Student("LEMA05068204", "Lemire", "Adam", "7833", 21, 4.1f, 4));
        university.addStudent(new Student("NOUL05049504", "Noundou", "Leonnel", "7833", 21, 3.0f, 4));
        university.addStudent(new Student("LARM17078901", "Larouche", "Mathieu", "7833", 21, 3.9f, 4));
        university.addStudent(new Student("MAUP05067802", "Maurice", "Philippe", "7833", 21, 3.7f, 4));

        university.addInscription(0, 0);
        university.addInscription(0, 2);
        university.addInscription(0, 5);
        university.addInscription(0, 18);

        university.addInscription(1, 0);
        university.addInscription(2, 0);
        university.addInscription(3, 0);
        university.addInscription(4, 0);
    }
}
