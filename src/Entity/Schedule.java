package Entity;

public class Schedule {

    public Entity.Course[] courses;

    public Schedule(Entity.Course[] courses) {
        this.courses = courses;
    }

    public void showSchedule() {
        for (int i = 0; i < courses.length; i++) {
            System.out.println(courses[i].name);
        }
    }
}