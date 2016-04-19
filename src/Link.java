import java.io.Serializable;

/**
 * Created by guilmarc on 2016-03-28.
 */
public class Link implements Serializable {

    public int studentIndex;
    public int courseIndex;

    public Link nextStudent;
    public Link nextCourse;

    public Link(int studentIndex, int courseIndex ) {
        this.studentIndex = studentIndex;
        this.courseIndex = courseIndex;
    }

    public void displayLink() // display ourself
    {
        System.out.print("{" + studentIndex + "} ");
    }
}
