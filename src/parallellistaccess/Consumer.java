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
        T data = (T) bundle.list.remove(0);
        System.out.print(offset + "remove");
//        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t".substring(key) + bundle.list);
        v(bundle.mutex);
        v(bundle.write);

        return data;
    }

    public void run() {
        while (true) {
            try {
                readData();
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
        }
    }
}
