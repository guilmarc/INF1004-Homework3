import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by guilmarc on 2016-03-28.
 */
public class Course implements Serializable{

    private String code;
    private String name;
    public int maximumOfInscriptions;
    public int numberOfInscriptions;
    private ArrayList<Course> prerequisites;
    public Link firstStudent;

    public Course(String code, String name, int maximumOfInscriptions, Course... prerequisites) {
        this.code = code;
        this.name = name;
        this.maximumOfInscriptions = maximumOfInscriptions;
        this.prerequisites = new ArrayList<Course>();

        for(Course prerequisite : prerequisites) {
            this.prerequisites.add(prerequisite);
        }
    }

    public boolean available() {
        return (this.numberOfInscriptions < this.maximumOfInscriptions);
    }

    @Override
    public String toString(){
        return "[" + this.code + "] - " + this.name;
    }
}
