package Entity;

public class Student extends Person implements Comparable<Student> {

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

    // hashCode()
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    // equals()
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Student s = (Student) obj;

        return name.equals(s.name);
    }

    // Comparable
    @Override
    public int compareTo(Student s) {
        return name.compareTo(s.name);
    }

    // INNER CLASS
    public class Record {
        public void show() {
            System.out.println("Record of student: " + name);
        }
    }
}