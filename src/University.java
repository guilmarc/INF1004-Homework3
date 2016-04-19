import java.io.*;
import java.util.ArrayList;

/**
 * Created by guilmarc on 2016-03-31.
 */
public class University {

    private final String INSCRIPTIONS_FILE = "inscription.dat";
    private final String COURSES_FILE = "courses.dat";
    private final String STUDENTS_FILE = "students.dat";

    public ArrayList<Student> students = new ArrayList<Student>();
    public ArrayList<Course> courses = new ArrayList<Course>();

    public void addStudent(Student newStudent) {
        students.add(newStudent);
    }

    public void addCourse(Course newCourse) {
        courses.add(newCourse);
    }

    public void showCoursesForStudentIndex(int studentIndex) {

        try {
            Student student = students.get(studentIndex);

            Link current = student.firstCourse;

            System.out.println("Liste des cours de " + student.toString());
            System.out.println("****************************************************************************");

            while (current != null) {
                Course course = courses.get(current.courseIndex);
                System.out.println(course.toString());
                current = current.nextCourse;
            }

            System.out.println();

        } catch (IndexOutOfBoundsException ex) {
            System.out.println("ERREUR: L'étudiant à l'index " + studentIndex + " n'existe pas !");
        }


    }

    public void showStudentsForCourseIndex(int courseIndex) {

        try {

            Course course = courses.get(courseIndex);

            Link current = course.firstStudent;

            System.out.println("Liste des étudiants de " + course.toString());
            System.out.println("****************************************************************************");

            while (current != null) {
                Student student = students.get(current.studentIndex);
                System.out.println(student.toString());
                current = current.nextStudent;
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

    public boolean addInscription(int studentIndex, int courseIndex) {

        if (this.findInscription( studentIndex, courseIndex) == null) {

            try {

                Link newLink = new Link(studentIndex, courseIndex);

                Student student = students.get(studentIndex);
                Course course = courses.get(courseIndex);

                if (course.available()) {

                    //Insert the new course for the selected student
                    newLink.nextCourse = student.firstCourse;
                    student.firstCourse = newLink;

                    //Insert the new student for the selected course
                    newLink.nextStudent = course.firstStudent;
                    course.firstStudent = newLink;

                    course.numberOfInscriptions++;
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

    public boolean removeInscription(int studentIndex, int courseIndex) {
        Link removedCourse = this.removeCourseForStudentIndex(studentIndex, courseIndex);
        Link removedStudent = this.removeStudentForCourseIndex(courseIndex, studentIndex);

        return ((removedCourse != null) && (removedStudent != null));
    }

    private Link removeCourseForStudentIndex(int studentIndex, int courseIndex) {

        try {
            Student student = students.get(studentIndex);

            Link current = student.firstCourse;
            Link previous = student.firstCourse;

            while (current.courseIndex != courseIndex) {
                if (current.nextCourse == null) {
                    return null;
                } else {
                    previous = current;
                    current = current.nextCourse;
                }
            }

            if (current == student.firstCourse) {
                student.firstCourse = student.firstCourse.nextCourse;
            } else {
                previous.nextCourse = current.nextCourse;
            }

            return current;
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("ERREUR: L'étudiant à l'index " + studentIndex + " n'existe pas !");
            return null;
        }
    }

    private Link removeStudentForCourseIndex(int courseIndex, int studentIndex) {

        try {
            Course course = courses.get(courseIndex);

            Link current = course.firstStudent;
            Link previous = course.firstStudent;

            while (current.studentIndex != studentIndex) {
                if (current.nextStudent == null) {
                    return null;
                } else {
                    previous = current;
                    current = current.nextStudent;
                }
            }

            if (current == course.firstStudent) {
                course.firstStudent = course.firstStudent.nextCourse;
            } else {
                previous.nextStudent = current.nextStudent;
            }

            return current;
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("ERREUR: Le cours à l'index " + courseIndex + " n'existe pas !");
            return null;
        }
    }

    public Link findInscription(int studentIndex, int courseIndex){

        Student student = students.get(studentIndex);
        Link current = student.firstCourse;

        if(current != null) {
            while (current.courseIndex != courseIndex) {
                if (current.nextCourse == null) {
                    return null;
                } else {
                    current = current.nextCourse;
                }
            }
        }
        return current;
    }

    public void saveDataToFile(){
        saveCourses();
        saveStudents();
        saveInscriptions();
    }

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

    public void saveInscriptions() {

        try (
                OutputStream file = new FileOutputStream(INSCRIPTIONS_FILE);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ){
            ArrayList<Link> links = new ArrayList<Link>();

            for (Student student : students) {
                Link current = student.firstCourse;
                while (current != null) {
                    links.add(current);
                    //output.writeObject(current);
                    current = current.nextCourse;
                }
            }

            output.writeObject(links);
        }
        catch(IOException ex){
            System.out.println("Erreur lors de l'écriture dans le fichier " + INSCRIPTIONS_FILE);
        }


    }

    public void loadDataFromFile(){
        this.readStudents();
        this.readCourses();
        this.readInscriptions();
    }

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
                this.addInscription(link.studentIndex, link.courseIndex);
            }
        }
        catch(ClassNotFoundException ex){
            System.out.println("Erreur lors de la lecture des inscription.  Classe non trouvée ! ");
        }
        catch(IOException ex){
            System.out.println("Erreur lors de la lecture du fichier " + INSCRIPTIONS_FILE);
        }
    }

    public void clearInscriptions(){
        for(Student student : this.students){
            student.firstCourse = null;
        }

        for (Course course : this.courses) {
            course.firstStudent = null;
            course.numberOfInscriptions = 0;
        }
    }



}
