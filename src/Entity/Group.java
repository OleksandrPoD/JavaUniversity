public class Group {

    String name;
    Student[] students;

    Group(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    void showStudents() {
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].name);
        }
    }
}