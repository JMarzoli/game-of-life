package it.unicam.cs.pa.jgol;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestConcurrency {

    public static volatile boolean done = false;

    public static volatile int counter = 0;

    public static void threadCounter(Object lock, int taskId) {
        for(int i=0; i<1000; i++) {
            synchronized (lock) {
                counter++;
            }
        }
        System.out.println("TaskId "+taskId+" counter="+counter);
    }

    public static void thread1( ) {
        for(int i=0 ;i<100;i++) {
            System.out.println("Hello! "+i);
        }
        done = true;
    }

    public static void thread2() {
        int counter = 0;
        while (!done) {
            counter++;
        }
        System.out.println("Goodbye! "+counter);
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
//        es.execute(TestConcurrency::thread1);
//        es.execute(TestConcurrency::thread2);
        Integer lock = 10;
        for(int i=0;i<100;i++) {
            int taskId = i;
            es.execute(() -> threadCounter(lock, taskId));
        }
    }

}
