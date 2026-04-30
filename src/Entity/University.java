public class University {

    String name;
    Teacher[] teachers;

    University(String name, Teacher[] teachers) {
        this.name = name;
        this.teachers = teachers;
    }

    void showTeachers() {
        for (int i = 0; i < teachers.length; i++) {
            System.out.println(teachers[i].name);
        }
    }

    static class Department {
        String name;

        Department(String name) {
            this.name = name;
        }

        void show() {
            System.out.println("Department: " + name);
        }
    }
}