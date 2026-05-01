import Entity.*;

public class Main {
    public static void main(String[] args) {

        University.info();

        Teacher t1 = new Teacher("Ivan");
        Teacher t2 = new Teacher("Petro");

        Student s1 = new Student("Oleh", t1);
        Student s2 = new Student("Anna", t2);

        Student[] students = {s1, s2};
        Teacher[] teachers = {t1, t2};

        Group g = new Group("KN-21", students);
        University u = new University("TNTU", teachers);

        System.out.println("\n=== Student Record ===");
        Student.Record r = s1.new Record();
        r.show();

        System.out.println("\n=== Students ===");
        g.showStudents();

        System.out.println("\n=== Teachers ===");
        u.showTeachers();

        System.out.println("\n=== Actions ===");
        s1.study();
        t1.teach();

        Course c1 = new Course("Math", t1);
        Course c2 = new Course("Programming", t2);

        Course[] courses = {c1, c2};

        System.out.println("\n=== Courses ===");
        c1.show();
        c2.show();

        Schedule schedule = new Schedule(courses);

        System.out.println("\n=== Schedule ===");
        schedule.showSchedule();

        System.out.println("\n=== Department ===");
        University.Department d = new University.Department("IT");
        d.show();

        System.out.println("\n=== Lesson ===");
        class Lesson {
            void start() {
                System.out.println("Lesson started");
            }
        }

        Lesson l = new Lesson();
        l.start();
    }
}