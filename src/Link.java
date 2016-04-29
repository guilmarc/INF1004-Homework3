import java.io.Serializable;

/*
    INF1004 Structure de données et algoritmes Devoir 3
    Auteurs : Marco Guilmette, Jala Aymeric, Mathieu Larouche

    Classe Link : Sert à faire le lien (Inscription) entre les cours et les étudiants
 */
public class Link implements Serializable {

    private int studentIndex;
    private int courseIndex;

    private Link nextStudent;
    private Link nextCourse;

    //Constructeur
    public Link(int studentIndex, int courseIndex ) {
        this.studentIndex = studentIndex;
        this.courseIndex = courseIndex;
    }

    public int getStudentIndex() {
        return studentIndex;
    }

    public void setStudentIndex(int studentIndex) {
        this.studentIndex = studentIndex;
    }

    public int getCourseIndex() {
        return courseIndex;
    }

    public void setCourseIndex(int courseIndex) {
        this.courseIndex = courseIndex;
    }

    public Link getNextStudent() {
        return nextStudent;
    }

    public void setNextStudent(Link nextStudent) {
        this.nextStudent = nextStudent;
    }

    public Link getNextCourse() {
        return nextCourse;
    }

    public void setNextCourse(Link nextCourse) {
        this.nextCourse = nextCourse;
    }

    public void displayLink() // display ourself
    {
        System.out.print("{" + studentIndex + "} ");
    }
}
