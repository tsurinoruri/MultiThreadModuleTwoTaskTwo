import java.util.concurrent.TimeUnit;

public class TryLockLockableResourceProcessor implements LockableResourceProcessor {

	  static final long tryLockTimeoutMs = 200;   // для TryLock
    static final long backoffMs = 20;    // для TryLock

	@Override
	public void processResources(LockableResource r1, LockableResource r2) throws InterruptedException {
		boolean getFirstLock = false;
		boolean getSecondLock = false;

		try{
			while(!getFirstLock){
				if(r1.getLock().tryLock(50, TimeUnit.MILLISECONDS)){
					getFirstLock = true;
					Logger.log("Захватил первый ресурс: %s", r1.getName());
          Thread.sleep(tryLockTimeoutMs);

					if(r2.getLock().tryLock(50, TimeUnit.MILLISECONDS)){
						getSecondLock = true;
						Logger.log("Захватил второй ресурс: %s", r2.getName());
          	Thread.sleep(tryLockTimeoutMs);
						System.out.println("Оба захвачены");
					}else{
						System.out.println("Второй не захватился");
						r1.getLock().unlock();
						getFirstLock = false;
						Thread.sleep(backoffMs);
					}
				}else{
					System.out.println("Не удалось захватить первый ресурс");
					Thread.sleep(backoffMs);
				}
			}
		}finally{
			if(getFirstLock){
				r1.getLock().unlock();
			}
			if(getSecondLock){
				r2.getLock().unlock();
			}
		}
	}
}
