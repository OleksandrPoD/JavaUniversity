package Entity;

public class University {

    public String name;
    public Teacher[] teachers;

    public University(String name, Teacher[] teachers) {
        this.name = name;
        this.teachers = teachers;
    }

    public void showTeachers() {
        for (int i = 0; i < teachers.length; i++) {
            System.out.println(teachers[i].name);
        }
    }

    public static class Department {
        public String name;

        public Department(String name) {
            this.name = name;
        }

        public void show() {
            System.out.println("Department: " + name);
        }
    }

    public static void info() {
        System.out.println("University system");
    }
}