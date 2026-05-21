package Entity;

public class Group {

    public String name;
    public Entity.Student[] students;

    public Group(String name, Entity.Student[] students) {
        this.name = name;
        this.students = students;
    }

    public void showStudents() {
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].name);
        }
    }
}