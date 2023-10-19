import java.util.LinkedList;
import java.util.Queue;

public class ProducerAndConsumer {
    // lock object
    // key->shared resources
    static Object key = new Object();
    static Queue<Integer> queue = new LinkedList<Integer>();// internal item store count++ and count--
    // number of item
    static int size = 10;

    public static void main(String[] args) {

        // producer thread
        Thread producer = new Thread(new Runnable() { // body of runnable interface
            @Override
            public void run() {
                // number of momo
                int count = 0;
                while (true) {

                    // critical section(plate:total number of momos)

                    synchronized (key) {
                        try {
                        while (queue.size() == size) {
                            // wait method
                            
                               //read signal
                                key.wait();
                        }
                            // item insert
                            queue.offer(count++);
                             
                                //green signal
                                key.notifyAll();
                            System.out.println("momo producer, plate size " + queue.size());
                        
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                        } // while loop stop
                    }
                }
            }
        );

        // consumer thread

        Thread consumer = new Thread(new Runnable() { // body of runnable interface
            @Override
            public void run() {
                // critical section(plate:total number of momos)
                while (true) {
                    synchronized (key) {
                        try {
                        while (queue.size() == 0) {
                            
                                //read signal
                                key.wait();
                        }
                                // Item pop from the queue
                                queue.poll();
                                //green signal
                                key.notifyAll();
                                //display
                                System.out.println("momo consumer, plate size " + queue.size()); // plate size decrease
                                // ekta try er vetore
                                // 2 to exception ke handle korlam

                                Thread.sleep(800);
                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                        } // while loop stop
                    }
                }
            }
        );
        // main
        producer.start();
        consumer.start();
    }
}
