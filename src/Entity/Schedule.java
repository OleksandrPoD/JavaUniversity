package Entity;

public class Schedule {

    public Course[] courses;

    public Schedule(Course[] courses) {
        this.courses = courses;
    }

    public void showSchedule() {
        for (int i = 0; i < courses.length; i++) {
            System.out.println(courses[i].name);
        }
    }
}