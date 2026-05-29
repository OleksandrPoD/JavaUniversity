class WordThread implements Runnable {

    private String name;
    private String[] words;

    public WordThread(String name, String[] words) {
        this.name = name;
        this.words = words;
    }

    @Override
    public void run() {
        for (String word : words) {
            System.out.println(name + ": " + word);

            try {
                Thread.sleep(500); // затримка, щоб було видно паралельність
            } catch (InterruptedException e) {
                System.out.println(name + " interrupted");
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Thread t1 = new Thread(new WordThread("Thread-1",
                new String[]{"Java", "is", "cool"}));

        Thread t2 = new Thread(new WordThread("Thread-2",
                new String[]{"Multithreading", "makes", "sense"}));

        Thread t3 = new Thread(new WordThread("Thread-3",
                new String[]{"Lab", "9", "done"}));

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }

        System.out.println("All threads finished");
    }
}