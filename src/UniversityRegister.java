import java.lang.runtime.SwitchBootstraps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.Objects;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UniversityRegister {
    private final List<Student> students;

    private UniversityRegister(List<Student> students) {
        this.students = students;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("starting program...");

        //Add some students to the register
        UniversityRegister register = new UniversityRegister(List.of(
            new Student("123", "John", "Doe", "Computer Science", "CI405"),
            new Student("124", "Jane", "Smith", "Biology", "BI302"),
            new Student("125", "Alice", "Johnson", "Mathematics", "MA201"),
            new Student("126", "Bob", "Williams", "Physics", "PH101"),
            new Student("127", "Nora", "Vik", "Chemistry", "CH202"),
            new Student("128", "Charlie", "Brown", "Computer Science", "CI405"),
            new Student("129", "David", "Miller", "Biology", "BI302"),
            new Student("130", "Emily", "McGovern", "Mathematics", "MA201"),
            new Student("131", "Frank", "Wilson", "Physics", "PH101"),
            new Student("132", "Taylor", "Swift", "Chemistry", "CH202")
        ));

        //Print instructions on how to use the program
        boolean continueProgram = true;
        while (continueProgram) {
            System.out.println("------------");
            System.out.println("To use the Student Register");
            System.out.println("Type any one of the following words: ");
            System.out.println("- ViewRegister");
            System.out.println("- AddStudent");
            System.out.println("- RemoveStudent");
            System.out.println("- Query");
            System.out.println("To stop the program type: Quit");
            System.out.println("------------");

            //Enter data using Scanner and BufferedReader
            Scanner scanner = new Scanner(System.in);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Reading user input using readLine and printing it
            String keyword = reader.readLine();
            System.out.println("Input: " + keyword);

            // Map keywords to corresponding functions
            // Since a key value of functional programming is immutability. I have used a hashmap to associate keywords
            //with functions instead of doing for example a switch case.


            // Map keywords to corresponding functions
            Function<UniversityRegister, UniversityRegister> action = getAction(keyword);
            register = Objects.requireNonNullElseGet(action, () -> r -> {
                System.out.println("Invalid input");
                return r;
            }).apply(register);

            if ("Quit".equals(keyword)) {
                continueProgram = false;
                System.out.println("Exiting the program. Goodbye!");
            }
        }
    }

    private static Function<UniversityRegister, UniversityRegister> getAction(String keyword){
        return register ->
                switch (keyword) {
            case "ViewRegister" -> viewRegister(register);
            case "AddStudent" -> addStudent(register);
            case "RemoveStudent" -> removeStudent(register);
            case "Query" -> queryStudent(register);
            default -> null;
        };
    }

    private static UniversityRegister viewRegister(UniversityRegister register) {
        System.out.println("View Student Register:");
        register.getAllStudents().forEach(System.out::println);
        return register;
    }
    private static UniversityRegister addStudent(UniversityRegister register) {
        System.out.println("Add student:");
        System.out.println("Enter student details:");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Student ID (format:000): ");
        String studentId = scanner.nextLine();
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Course: ");
        String course = scanner.nextLine();
        System.out.print("Module (format:AA111): ");
        String module = scanner.nextLine();

        Student newStudentInput = new Student(studentId, firstName, lastName, course, module);
        return register.addStudent(newStudentInput);
    }

    private static UniversityRegister removeStudent(UniversityRegister register) {
        System.out.println("Remove student:");
        System.out.println("What is the student ID of the student to be removed?");
        Scanner scanner = new Scanner(System.in);
        String studentID = scanner.nextLine();
        return register.removeStudent(studentID);
    }

    private UniversityRegister addStudent(Student student) {
        List<Student> newStudents = new ArrayList<>(students);
        newStudents.add(student);
        return new UniversityRegister(newStudents);
    }

    private UniversityRegister removeStudent(String studentId) {
        System.out.println("Removing student:");
        System.out.println(studentId);

       List<Student> newStudents = students.stream()
               .filter(student -> !student.getId().equals(studentId))
               .toList();
        return new UniversityRegister(newStudents);
    }

    private List<Student> getAllStudents() {
        return Collections.unmodifiableList(students);
    }

    private static UniversityRegister queryStudent(UniversityRegister register) {
        System.out.println("What do you want to query by? (Name, Course, Id or Module)");
        Scanner scanner = new Scanner(System.in);
        String queryBy = scanner.nextLine();

        switch (queryBy) {
            case "Name":
                System.out.println("What name do you want to query?");
                String name = scanner.nextLine();
                List<Student> filteredStudents = register.queryStudentsByName(name);
                System.out.println("Students named " + name + ":");
                System.out.println(filteredStudents);
                return register;
            case "ID":
                System.out.println("What ID do you want to query?");
                String id = scanner.nextLine();
                filteredStudents = register.queryStudentsByID(id);
                System.out.println(filteredStudents);
                return register;
            case "Module":
                System.out.println("What module do you want to query?");
                String module = scanner.nextLine();
                filteredStudents = register.queryStudentsByModule(module);
                System.out.println(filteredStudents);
                return register;
            case "Course":
                System.out.println("What course do you want to query?");
                String course = scanner.nextLine();
                filteredStudents = register.queryStudentsByCourse(course);
                System.out.println(filteredStudents);
                return register;
            default:
                System.out.println("Invalid query option");
                return register;
        }
    }
    public List<Student> queryStudentsByName(String name) {
        return students.stream()
                .filter(student -> student.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Student> queryStudentsByID(String id)   {
        return students.stream()
                .filter(student -> student.getId().toLowerCase().contains(id.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Student> queryStudentsByModule(String module)   {
        return students.stream()
                .filter(student -> student.getModule().toLowerCase().contains(module.toLowerCase()))
                .collect(Collectors.toList());
    }
    public List<Student> queryStudentsByCourse(String course)   {
        return students.stream()
                .filter(student -> student.getCourse().toLowerCase().contains(course.toLowerCase()))
                .collect(Collectors.toList());
    }

}