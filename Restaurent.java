import java.util.LinkedList;
import java.util.Queue;

public class Restaurent {
    static class kitchen extends Thread { // process
        /*
         * static String lock="Lock";
         * //main process
         * // critical sectiaon
         * public static void preparefood() {
         * System.out.println("The order has been has been taken " +
         * Thread.currentThread().getName());
         * //by using synchronized block
         * synchronized(lock){
         * try {
         * Thread.sleep(3000);
         * } catch (InterruptedException e) {
         * 
         * e.printStackTrace();
         * }
         * System.out.println("food preparation started " +
         * Thread.currentThread().getName());
         * System.out.println("food preparation done " +
         * Thread.currentThread().getName());
         * }
         * }
         * 
         * @Override
         * public void run() {
         * System.out.println("within the kitchen");
         * preparefood();
         * 
         * }
         * }
         */

        public static void main(String[] args) {

            String lock = "Lock";
            Runnable runnable = new Runnable() {
                // critical section
                public void preparefood() {
                    System.out.println("The order has been has been taken " + Thread.currentThread().getName());
                    // by using synchronized block
                    synchronized (lock) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                        System.out.println("food preparation started " + Thread.currentThread().getName());
                        System.out.println("food preparation done " + Thread.currentThread().getName());
                    }
                }

                @Override
                public void run() {
                    System.out.println("within the kitchen");
                    preparefood();

                }
            };

            /*
             * kitchen K1 = new kitchen();
             * K1.start();
             * kitchen K2 = new kitchen();
             * K2.start();
             * kitchen K3 = new kitchen();
             * K3.start();
             */
            Thread t1 = new Thread(runnable);
            t1.start();
            Thread t2 = new Thread(runnable);
            t2.start();
            Thread t3 = new Thread(runnable);
            t3.start();
            Object key = new Object();
            Queue<Integer> queue = new LinkedList<Integer>();
            // producer thread
            int size = 10;
             Thread producer = new Thread(new Runnable() {

                public void run() {
                    int count = 0;

                    while (true) {
                        synchronized (key) {
                            try {
                                while (queue.size() == size) {
                                    // red signal
                                    key.wait();
                                }
                                // item insert
                                queue.offer(count++);
                                // green signal
                                key.notifyAll();
                                System.out.println("number of momos in the plate " + queue.size());

                                Thread.sleep(2000);

                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                        }

                    }
                }

            });

            Thread consumer = new Thread(new Runnable() {

                public void run() {
                    
                    while (true) {
                        synchronized (key) {
                            try {
                                while (queue.size() == 0) {
                                    // red signal
                                    key.wait();
                                }
                                // item insert
                                queue.poll();
                                // green signal
                                key.notifyAll();
                                System.out.println("number of momos in the plate consumes by size " + queue.size());

                                Thread.sleep(2000);
                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            producer.start();
            consumer.start();
        }
        
    }
}
