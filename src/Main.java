import Entity.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Teacher t1 = new Teacher("Ivan");

        Student s1 = new Student("Oleh", t1);
        Student s2 = new Student("Anna", t1);
        Student s3 = new Student("Petro", t1);
        Student s4 = new Student("Oleh", t1); // duplicate

        // 1. ArrayList
        ArrayList<Student> arrayList = new ArrayList<>();

        arrayList.add(s1);
        arrayList.add(s2);
        arrayList.add(s3);
        arrayList.add(s4);

        System.out.println("=== ArrayList ===");

        for (Student s : arrayList) {
            System.out.println(s.name);
        }

        // 2. HashSet
        HashSet<Student> hashSet = new HashSet<>(arrayList);

        System.out.println("\n=== HashSet ===");

        for (Student s : hashSet) {
            System.out.println(s.name);
        }

        // 3. Sorting
        Collections.sort(arrayList);

        System.out.println("\n=== Sorted ArrayList ===");

        for (Student s : arrayList) {
            System.out.println(s.name);
        }

        // 4. TreeSet
        TreeSet<Student> treeSet = new TreeSet<>(arrayList);

        System.out.println("\n=== TreeSet ===");

        for (Student s : treeSet) {
            System.out.println(s.name);
        }

        // 5. TreeMap
        TreeMap<Integer, Student> treeMap = new TreeMap<>();

        treeMap.put(1, s1);
        treeMap.put(2, s2);
        treeMap.put(3, s3);

        System.out.println("\n=== TreeMap ===");

        for (Integer key : treeMap.keySet()) {
            System.out.println(key + " -> " + treeMap.get(key).name);
        }

        // 6. LinkedList
        LinkedList<Student> linkedList = new LinkedList<>(arrayList);

        System.out.println("\n=== LinkedList ===");

        for (Student s : linkedList) {
            System.out.println(s.name);
        }

        // 7. Queue
        Queue<Student> queue = new LinkedList<>();

        queue.add(s1);
        queue.add(s2);
        queue.add(s3);

        System.out.println("\n=== Queue ===");

        while (!queue.isEmpty()) {
            System.out.println(queue.poll().name);
        }

        // 8. PriorityQueue
        PriorityQueue<Student> priorityQueue = new PriorityQueue<>();

        priorityQueue.add(s3);
        priorityQueue.add(s1);
        priorityQueue.add(s2);

        System.out.println("\n=== PriorityQueue ===");

        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll().name);
        }
    }
}