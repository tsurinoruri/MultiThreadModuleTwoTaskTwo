import java.util.concurrent.locks.ReentrantLock;

public final class LockableResource {
   private final String name;      // Например: "Name-A" / "Name-B"
   private final int id;           // Для глобального порядка (фикс №1)
   private final ReentrantLock lock = new ReentrantLock();
   private final Object monitor = new Object(); // Для synchronized-блоков
  
   public LockableResource(String name, int id){
    this.name = name;
    this.id = id;
   }

   public String getName(){ return this.name; }
   public int getId(){ return this.id; }
   public ReentrantLock getLock() { return lock; }
   public Object getMonitor() { return monitor; }
}