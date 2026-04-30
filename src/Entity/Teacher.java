public class Teacher extends Person {

    Teacher(String name) {
        super(name);
    }

    void teach() {
        System.out.println(name + " teaches students");
    }
}