/*
The Student class represents an individual student entity in the register
 */

public class Student {
    //attributes of the student
    private final String id;
    private final String fName;
    private final String sName;
    private final String course;
    private final String module;

    public Student(String id, String fName, String sName, String course, String module) {
        this.id = id;
        this.fName = fName;
        this.sName = sName;
        this.course = course;
        this.module = module;
    }

    //method to retrieve the student's ID
    public String getId() {
        return id;
    }
    //method to retrieve the student's full name
    public String getFullName() {
        return fName + " " + sName;
    }
    //method to retrieve the student's course
    public String getCourse() { return course; }
    //method to retrieve the student's module
    public String getModule() { return module; }
    //Override of the `toString` method to provide a string representation of the `Student` object.
    @Override
    public String toString() {
        return "Student{" +
                "id'" + id + '\'' +
                ", name='" + fName + " " + sName + '\'' +
                ", course='" + course + '\'' +
                ", module='" + module + '\'' +
                '}';
    }
}
