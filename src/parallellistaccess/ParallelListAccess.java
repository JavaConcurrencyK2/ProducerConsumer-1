/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 * This program is created while attending the courses
 * at Hochschule Muenchen Fak07, Germany in SS14.
 */

package parallellistaccess;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author T500
 */
public class ParallelListAccess {

    int data_count;
    Bundle bundle;

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) throws InterruptedException {

        int maxData = 5;
        int producers = 5;
        int consumers = 5;

        final ParallelListAccess pla = new ParallelListAccess(maxData);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {}
            }}).start();

        for (int i = 0; i < producers; i++) {

            new Producer<>(i, "data", pla.bundle).start();
            Thread.sleep(1000);
        }

        for (int i = 0; i < consumers; i++) {
            new Consumer<>(i + producers, pla.bundle).start();
            Thread.sleep(1000);
        }
    }

    public ParallelListAccess(int maxData) {
        data_count = 0;

        bundle = new Bundle();
        bundle.mutex = new Semaphore(1);
        bundle.read = new Semaphore(0);
        bundle.write = new Semaphore(maxData);
        bundle.list = new ArrayList<>(maxData);
    }
}

class Semaphore {
    int counter;
    Queue queue;

    public Semaphore(int counter) {
        queue = new LinkedList<>();
        this.counter = counter;
    }
}

class Bundle {
    List list;
    Semaphore mutex;
    Semaphore read;
    Semaphore write;
}