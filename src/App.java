public class App {

    static final int iterations = 1000;

    public static void main(String[] args) throws Exception {
        //demonstrateDeadLock();
        //demonstrateOrderedLock();
        //demonstrateTryLock();
    }

    public static void demonstrateDeadLock() throws InterruptedException{
        System.out.println("==================Демонстрация deadlock=========");
        LockableResource ar1 = new LockableResource("A", 1);
        LockableResource br2 = new LockableResource("B", 2);

        WrongLockableResourceProcessor wrong = new WrongLockableResourceProcessor();
        for(int i = 0; i <iterations; i++){
            Logger.log("Итерация %d", i);

            Thread t1Thread = new Thread(()->{
                try{
                    wrong.processResources(ar1, br2);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });
            Thread t2Thread = new Thread(()->{
                try{
                    wrong.processResources(br2, ar1);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });

            t1Thread.start();
            t2Thread.start();

            t1Thread.join();
            t2Thread.join();
            System.out.println("Если вы это видите, то deadlock не произошел");
        }
    }

    public static void demonstrateOrderedLock() throws InterruptedException{
        System.out.println("==================Демонстрация Ordered lock=========");
        LockableResource ar1 = new LockableResource("A", 1);
        LockableResource br2 = new LockableResource("B", 2);

        OrderedLockableResourceProcessor ordered = new OrderedLockableResourceProcessor();
        for(int i = 0; i <iterations; i++){
            Logger.log("Итерация %d", i);

            Thread t1Thread = new Thread(()->{
                try{
                    ordered.processResources(ar1, br2);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });
            Thread t2Thread = new Thread(()->{
                try{
                    ordered.processResources(br2, ar1);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });

            t1Thread.start();
            t2Thread.start();

            t1Thread.join();
            t2Thread.join();
            System.out.println("Ordered lock завершен");
        }
    }

    public static void demonstrateTryLock() throws InterruptedException{
        System.out.println("==================Демонстрация Try lock=========");
        LockableResource ar1 = new LockableResource("A", 1);
        LockableResource br2 = new LockableResource("B", 2);

        TryLockLockableResourceProcessor trylock = new TryLockLockableResourceProcessor();
        for(int i = 0; i <iterations; i++){
            Logger.log("Итерация %d", i);

            Thread t1Thread = new Thread(()->{
                try{
                    trylock.processResources(ar1, br2);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });
            Thread t2Thread = new Thread(()->{
                try{
                    trylock.processResources(br2, ar1);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            });

            t1Thread.start();
            t2Thread.start();

            t1Thread.join();
            t2Thread.join();
            System.out.println("Try lock завершен");
        }
    }
}
