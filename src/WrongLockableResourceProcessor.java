public class WrongLockableResourceProcessor implements LockableResourceProcessor {
  
	static final long pauseBetweenLocksMs = 100;   // для Wrong

	@Override
	public void processResources(LockableResource r1, LockableResource r2) throws InterruptedException {
		Logger.log("Начало обработки ресурсов: %s и %s", r1.getName(), r2.getName());

		synchronized(r1.getMonitor()){
			Logger.log("Захватил первый ресурс: %s", r1.getName());

			Thread.sleep(pauseBetweenLocksMs);

			synchronized(r2.getMonitor()){
				Logger.log("Захватил второй ресурс: %s", r2.getName());
				Thread.sleep(pauseBetweenLocksMs);
				System.out.println("Выход из критической секции");
			}
			Logger.log("Освободил второй ресурс: %s", r2.getName());
		}
		Logger.log("Освободил первый ресурс: %s", r1.getName());
	}
	
}
