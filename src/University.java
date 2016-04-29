import java.io.*;
import java.util.ArrayList;

/*
    INF1004 Structure de données et algoritmes Devoir 3
    Auteurs : Marco Guilmette, Jala Aymeric, Mathieu Larouche

    Classe University : Représente l'université
 */
public class University {

    private final String INSCRIPTIONS_FILE = "inscription.dat";
    private final String COURSES_FILE = "courses.dat";
    private final String STUDENTS_FILE = "students.dat";

    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Course> courses = new ArrayList<Course>();


    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    /*
            Méthode addStudent(Student newStudent)
            Ajoute un étudiant.

            Input : newStudent
            Retour : void.
         */
    public void addStudent(Student newStudent) {
        students.add(newStudent);
    }


    /*
        Méthode addCourse(Course newCourse)
        Ajoute un cours.

        Input : newCourse
        Retour : void.
     */
    public void addCourse(Course newCourse) {
        courses.add(newCourse);
    }


    /*
        Méthode showCoursesForStudentIndex(int studentIndex)
        Affiche la liste des cours d'un certain étudiant.

        Input : Index de l`étudiant.
        Retour : void
     */
    public void showCoursesForStudentIndex(int studentIndex) {

        try {
            Student student = students.get(studentIndex);

            Link current = student.getFirstCourse();

            System.out.println("Liste des cours de " + student.toString());
            System.out.println("****************************************************************************");

            while (current != null) {
                Course course = courses.get(current.getCourseIndex());
                System.out.println(course.toString());
                current = current.getNextCourse();
            }

            System.out.println();

        } catch (IndexOutOfBoundsException ex) {
            System.out.println("ERREUR: L'étudiant à l'index " + studentIndex + " n'existe pas !");
        }


    }


    /*
        Méthode showStudentsForCourseIndex(int courseIndex)
        Affiche les étudiants d'un certain cours.

        Input : Index du cours.
        Retour : void.
     */
    public void showStudentsForCourseIndex(int courseIndex) {

        try {

            Course course = courses.get(courseIndex);

            Link current = course.getFirstStudent();

            System.out.println("Liste des étudiants de " + course.toString());
            System.out.println("****************************************************************************");

            while (current != null) {
                Student student = students.get(current.getStudentIndex());
                System.out.println(student.toString());
                current = current.getNextStudent();
            }

            System.out.println();
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("ERREUR: Le cours à l'index " + courseIndex + " n'existe pas !");
        }
    }


    public void switchInscription(int studentIndex, int oldCourseIndex, int newCourseIndex) {
        this.removeInscription(studentIndex, oldCourseIndex);
        this.addInscription(studentIndex, newCourseIndex);
    }


/*
    Méthode addInscription(int studentIndex, int courseIndex)
    Ajoute une inscription (Link) entre un étudiant et un cours.

    Input : Index de l'étudiant, index du cours
    Retour : true si l'inscription s'est effectuée avec succès; false sinon
 */
    public boolean addInscription(int studentIndex, int courseIndex) {

        if (this.findInscription( studentIndex, courseIndex) == null) {

            try {

                Link newLink = new Link(studentIndex, courseIndex);

                Student student = students.get(studentIndex);
                Course course = courses.get(courseIndex);

                if (course.available()) {

                    //Insert the new course for the selected student
                    newLink.setNextCourse(student.getFirstCourse()) ;
                    student.setFirstCourse(newLink);

                    //Insert the new student for the selected course
                    newLink.setNextStudent(course.getFirstStudent()) ;
                    course.setFirstStudent(newLink);

                    course.incrementNumberOfInscription();
                    return true;

                } else {
                    System.out.println("ERREUR: Le cours " + course + " n'est plus disponible.  Il est complet !");
                    return false;
                }
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("ERREUR: Index invalide pour le cours ou l'étudiant !");
                return false;
            }
        } else {
            System.out.println("ERREUR: L'étudiant est déjà inscrit à ce cours !");
            return false;
        }
    }


    /*
        Méthode  removeInscription(int studentIndex, int courseIndex)
        Retire une inscription. ()

        Input : Index de l'étudiant, index du cours
        Retour : true si l'opération s'est effectuée avec succès; false sinon
     */
    public boolean removeInscription(int studentIndex, int courseIndex) {
        Link removedCourse = this.removeCourseForStudentIndex(studentIndex, courseIndex);
        Link removedStudent = this.removeStudentForCourseIndex(courseIndex, studentIndex);

        return ((removedCourse != null) && (removedStudent != null));
    }


    /*
        Méthode removeCourseForStudentIndex(int studentIndex, int courseIndex)
        Retire un cours d'un étudiant

        Input : Index de l'étudiant
        Retour : Link current : Position du cours précédant le cours supprimé.
     */
    private Link removeCourseForStudentIndex(int studentIndex, int courseIndex) {

        try {
            Student student = students.get(studentIndex);

            Link current = student.getFirstCourse();
            Link previous = student.getFirstCourse();

            while (current.getCourseIndex() != courseIndex) {
                if (current.getNextCourse() == null) {
                    return null;
                } else {
                    previous = current;
                    current = current.getNextCourse();
                }
            }

            if (current == student.getFirstCourse()) {
                student.setFirstCourse(student.getFirstCourse().getNextCourse());
            } else {
                previous.setNextCourse(current.getNextCourse());
            }

            return current;
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("ERREUR: L'étudiant à l'index " + studentIndex + " n'existe pas !");
            return null;
        }
    }


    /*
        Méthode removeStudentForCourseIndex(int courseIndex, int studentIndex)
        Retire un étudiant d'un certain cours.

        Input : Index du cours, index de l'étudiant
        Retour : Link current : Position de l'étudiant précédant l'étudiant supprimé.
     */
    private Link removeStudentForCourseIndex(int courseIndex, int studentIndex) {

        try {
            Course course = courses.get(courseIndex);

            Link current = course.getFirstStudent();
            Link previous = course.getFirstStudent();

            while (current.getStudentIndex() != studentIndex) {
                if (current.getNextStudent() == null) {
                    return null;
                } else {
                    previous = current;
                    current = current.getNextStudent();
                }
            }

            if (current == course.getFirstStudent()) {
                course.setFirstStudent(course.getFirstStudent().getNextCourse()) ;
            } else {
                previous.setNextStudent(current.getNextStudent());
            }

            course.decrementNumberOfInscription();

            return current;
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("ERREUR: Le cours à l'index " + courseIndex + " n'existe pas !");
            return null;
        }
    }


    /*
        Méthode findInscription(int studentIndex, int courseIndex)

        Input : Index de l'étudiant, index du cours
        Retour : Link current :
     */
    public Link findInscription(int studentIndex, int courseIndex){

        Student student = students.get(studentIndex);
        Link current = student.getFirstCourse();

        if(current != null) {
            while (current.getCourseIndex() != courseIndex) {
                if (current.getNextCourse() == null) {
                    return null;
                } else {
                    current = current.getNextCourse();
                }
            }
        }
        return current;
    }

    //Sauvegarder les données dans un fichier.
    public void saveDataToFile(){
        saveCourses();
        saveStudents();
        saveInscriptions();
    }

    /*
        Méthode saveCourses()
        Sauvegarde les cours dans un fichier
        Méthode applée par la méthode saveDataToFile()
     */
    public void saveCourses() {
        try (
                OutputStream file = new FileOutputStream(COURSES_FILE);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ){
            output.writeObject(this.courses);
        }
        catch(IOException ex){
            System.out.println("Erreur lors de l'écriture dans le fichier " + COURSES_FILE + ". " + ex.toString());
        }
    }


    /*
        Méthode saveStudents()
        Sauvegarde des étudiants dans un fichier.
        Méthode appellée par la méthode saveDataToFile().
     */
    public void saveStudents() {
        try (
                OutputStream file = new FileOutputStream(STUDENTS_FILE);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ){
            output.writeObject(this.students);
        }
        catch(IOException ex){
            System.out.println("Erreur lors de l'écriture dans le fichier " + STUDENTS_FILE);
        }
    }


    /*
        Méthode saveInscriptions ()
        Sauvegarde les inscriptions dans un fichier
        Méthode appellée par la méthode saveDataToFile()
     */
    public void saveInscriptions() {

        try (
                OutputStream file = new FileOutputStream(INSCRIPTIONS_FILE);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ){
            ArrayList<Link> links = new ArrayList<Link>();

            for (Student student : students) {
                Link current = student.getFirstCourse();
                while (current != null) {
                    links.add(current);
                    //output.writeObject(current);
                    current = current.getNextCourse();
                }
            }

            output.writeObject(links);
        }
        catch(IOException ex){
            System.out.println("Erreur lors de l'écriture dans le fichier " + INSCRIPTIONS_FILE);
        }


    }

    // Charge les données d'un fichier
    public void loadDataFromFile(){
        this.readStudents();
        this.readCourses();
        this.readInscriptions();
    }

    /*
        Méthode void readStudents()
        Lis les étudiants depuis un fichier.
        Méthode appellée par la méthode loadDataFromFile()
     */
    public void readStudents() {
        try(
                InputStream file = new FileInputStream(STUDENTS_FILE);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream (buffer);
        ){
            //deserialize the List
            students = (ArrayList<Student>)input.readObject();
        }
        catch(ClassNotFoundException ex){
            System.out.println("Erreur lors de la lecture des étudiants.  Classe non trouvée ! ");
        }
        catch(IOException ex){
            System.out.println("Erreur lors de la lecture du fichier " + STUDENTS_FILE);
        }
    }


    /*
        Méthode readCourses()
        Lis les cours depuis un fichier.
        Méthode appellée par la méthode loadDataFromFile()
     */
    public void readCourses() {
        try(
                InputStream file = new FileInputStream(COURSES_FILE);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream (buffer);
        ){
            //deserialize the List
            courses = (ArrayList<Course>)input.readObject();
        }
        catch(ClassNotFoundException ex){
            System.out.println("Erreur lors de la lecture des cours.  Classe non trouvée ! ");
        }
        catch(IOException ex){
            System.out.println("Erreur lors de la lecture du fichier " + COURSES_FILE);
        }
    }


    /*
        Méthode readInscriptions()
        Lis les inscriptions depuis un fichier
        Appellée depuis la méthode loadDataFromFile()
     */
    public void readInscriptions() {
        try(
                InputStream file = new FileInputStream(INSCRIPTIONS_FILE);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream (buffer);
        ){
            this.clearInscriptions();

            ArrayList<Link> links = new ArrayList<Link>();
            links = (ArrayList<Link>)input.readObject();
            for (Link link : links) {
                this.addInscription(link.getStudentIndex(), link.getCourseIndex());
            }
        }
        catch(ClassNotFoundException ex){
            System.out.println("Erreur lors de la lecture des inscription.  Classe non trouvée ! ");
        }
        catch(IOException ex){
            System.out.println("Erreur lors de la lecture du fichier " + INSCRIPTIONS_FILE);
        }
    }


    /*
        Méthode clearInscriptions()
        Supprime toutes les inscriptions. "Remise à zéro"
     */
    public void clearInscriptions(){
        for(Student student : this.students){
            student.setFirstCourse(null);
        }

        for (Course course : this.courses) {
            course.setFirstStudent(null);
            course.setNumberOfInscriptions(0);
        }
    }



}
