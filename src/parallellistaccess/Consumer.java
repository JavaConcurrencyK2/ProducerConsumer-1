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
public class Consumer<T> extends Process {

    Consumer(int key, Bundle bundle) {
        super(key, bundle);
    };

    public T readData() throws InterruptedException {
        p(bundle.read);
        p(bundle.mutex);
        T data = (T) bundle.list.remove(bundle.list.size()-1);
        v(bundle.mutex);
        v(bundle.read);

        return data;
    }

    public void run() {
        while (true) {
            try {
                System.out.println(offset + "read");
                readData();
            } catch (InterruptedException ex) {}
        }
    }
}
