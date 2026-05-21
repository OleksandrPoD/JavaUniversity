package Entity;

public class Teacher extends Entity.Person {

    public Teacher(String name) {
        super(name);
    }

    public void teach() {
        System.out.println(name + " teaches students");
    }

    @Override
    public void show() {
        System.out.println("Teacher: " + name);
    }
}