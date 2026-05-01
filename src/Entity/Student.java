package Entity;

public class Student extends Person {

    public Teacher teacher;

    public Student(String name, Teacher teacher) {
        super(name);
        this.teacher = teacher;
    }

    public void study() {
        System.out.println(name + " studies with " + teacher.name);
    }

    @Override
    public void show() {
        System.out.println("Student: " + name);
    }

    // INNER CLASS
    public class Record {
        public void show() {
            System.out.println("Record of student: " + name);
        }
    }
}