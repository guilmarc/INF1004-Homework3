import java.io.Serializable;

/*
    INF1004 Structure de données et algoritmes Devoir 3
    Auteurs : Marco Guilmette, Jala Aymeric, Mathieu Larouche

    Classe Student : Représente les étudiants
 */
public class Student implements Serializable {

    private String permanentCode;
    private String lastName;
    private String firstName;

    private String programNumber;

    private int credits;
    private float cumulativeAverage;

    private int numberOfInscriptions;

    private Link firstCourse;

    //Constructeur
    public Student(String permanentCode, String lastName, String firstName, String programNumber, int credits, float cumulativeAverage, int numberOfInscriptions) {
        this.permanentCode = permanentCode;
        this.lastName = lastName;
        this.firstName = firstName;
        this.programNumber = programNumber;
        this.credits = credits;
        this.cumulativeAverage = cumulativeAverage;
        this.numberOfInscriptions = numberOfInscriptions;
    }

    public String getPermanentCode() {
        return permanentCode;
    }

    public void setPermanentCode(String permanentCode) {
        this.permanentCode = permanentCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(String programNumber) {
        this.programNumber = programNumber;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public float getCumulativeAverage() {
        return cumulativeAverage;
    }

    public void setCumulativeAverage(float cumulativeAverage) {
        this.cumulativeAverage = cumulativeAverage;
    }

    public int getNumberOfInscriptions() {
        return numberOfInscriptions;
    }

    public void setNumberOfInscriptions(int numberOfInscriptions) {
        this.numberOfInscriptions = numberOfInscriptions;
    }

    public Link getFirstCourse() {
        return firstCourse;
    }

    public void setFirstCourse(Link firstCourse) {
        this.firstCourse = firstCourse;
    }

    //toString()
    public String toString(){
        return this.firstName + " " + this.lastName + " [" + this.permanentCode + "]";
    }

}
