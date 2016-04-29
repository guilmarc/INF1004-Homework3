import java.io.Serializable;
import java.util.ArrayList;

/*
    INF1004 Structure de données et algoritmes Devoir 3
    Auteurs : Marco Guilmette, Jala Aymeric, Mathieu Larouche
    Classe Course : Représente les cours
 */
public class Course implements Serializable{

    private String code;
    private String name;
    private int maximumOfInscriptions;
    private int numberOfInscriptions;
    private ArrayList<Course> prerequisites;
    private Link firstStudent;


    //Constructeur
    public Course(String code, String name, int maximumOfInscriptions, Course... prerequisites) {
        this.code = code;
        this.name = name;
        this.maximumOfInscriptions = maximumOfInscriptions;
        this.prerequisites = new ArrayList<Course>();

        for(Course prerequisite : prerequisites) {
            this.prerequisites.add(prerequisite);
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaximumOfInscriptions() {
        return maximumOfInscriptions;
    }

    public void setMaximumOfInscriptions(int maximumOfInscriptions) {
        this.maximumOfInscriptions = maximumOfInscriptions;
    }

    public int getNumberOfInscriptions() {
        return numberOfInscriptions;
    }

    public void setNumberOfInscriptions(int numberOfInscriptions) {
        this.numberOfInscriptions = numberOfInscriptions;
    }

    public void incrementNumberOfInscription(){
        numberOfInscriptions++;
    }

    public void decrementNumberOfInscription(){
        numberOfInscriptions--;
    }

    public ArrayList<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(ArrayList<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Link getFirstStudent() {
        return firstStudent;
    }

    public void setFirstStudent(Link firstStudent) {
        this.firstStudent = firstStudent;
    }

    public boolean available() {
        return (this.numberOfInscriptions < this.maximumOfInscriptions);
    }

    // toString()
    @Override
    public String toString(){
        return "[" + this.code + "] - " + this.name;
    }
}
