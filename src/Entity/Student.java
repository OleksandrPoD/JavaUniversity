public class Student extends Person {

    Teacher teacher;

    Student(String name, Teacher teacher) {
        super(name);
        this.teacher = teacher;
    }

    void study() {
        System.out.println(name + " studies with " + teacher.name);
    }

    class Record {
        void show() {
            System.out.println("Record of student: " + name);
        }
    }
}