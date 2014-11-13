/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package parallellistaccess;

import java.util.NoSuchElementException;

/**
 *
 * @author T500
 */
public abstract class Process extends Thread {

    int key;
    boolean wait;
    Bundle bundle;
    String offset = "";

    Process(int key, Bundle bundle) {
        this.key = key;
        this.wait = false;
        this.bundle = bundle;
        for (int i = 0; i < key; i++) {
            offset += '\t';
        }
    };

    public void allow() {
        wait = false;
    }

    public void p(Semaphore sem) throws InterruptedException {

        if (--sem.counter < 0) {
            sem.queue.add(this);
            wait = true;

            while (wait) {
                System.out.println(offset + "wait");
                Thread.sleep(1000);
            }
        }
    }

    public void v(Semaphore sem) {
        if (++sem.counter <= 0) {
            try {
                ((Process) sem.queue.remove()).allow();
            } catch (NoSuchElementException e) {}
        }
    }
}