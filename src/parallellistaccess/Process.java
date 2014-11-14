/*
 * (C) Nhu-Huy Le, nle@hm.edu
 * Oracle Corporation Java 1.8.0
 * Microsoft Windows 7 Professional
 * 6.1.7601 Service Pack 1 Build 7601
 */

package parallellistaccess;

/**
 *
 * @author T500
 */
public abstract class Process extends Thread {

    int key;
    boolean sleep;
    Bundle bundle;
    String offset = "";

    Process(int key, Bundle bundle) {
        this.key = key;
        this.sleep = false;
        this.bundle = bundle;
        for (int i = 0; i < key; i++) {
            offset += '\t';
        }
    };

    public void allow() {
        sleep = false;
    }

    public void sleep() throws InterruptedException {
        while (sleep) {
            System.out.println(offset + "sleep");
            Thread.sleep(1000);
        }
    }

    public void p(Semaphore sem) throws InterruptedException {
        synchronized (sem) {
            if (--sem.counter < 0) {
                sem.queue.add(this);
                sleep = true;
                System.out.println(offset + sem.counter);
            }
        }
        sleep();
    }

    public void v(Semaphore sem) {
        synchronized (sem) {
            if (++sem.counter <= 0 && !sem.queue.isEmpty()) {
                ((Process) sem.queue.remove()).allow();
            }
        }
    }
}