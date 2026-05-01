package Entity;

public class Course {

    public String name;
    public Teacher teacher;

    public Course(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public void show() {
        System.out.println("Course: " + name + ", Teacher: " + teacher.name);
    }
}