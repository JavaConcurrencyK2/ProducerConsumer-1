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
public class Producer<T> extends Process {

    T data;

    Producer(int key, T data, Bundle bundle) {
        super(key, bundle);
        this.data = data;
    };

    private void insertData(T data) throws InterruptedException {
        p(bundle.write);
        p(bundle.mutex);
        bundle.list.add(key + ": " + data.toString());
        v(bundle.mutex);
        v(bundle.read);
    }

    public void run() {
        while (true) {
            try {
                System.out.println(offset + "insert");
                insertData(data);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }
    }
}
