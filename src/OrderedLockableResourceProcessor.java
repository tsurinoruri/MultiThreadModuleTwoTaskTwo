import java.util.Arrays;

public class OrderedLockableResourceProcessor implements LockableResourceProcessor {

	@Override
	public void processResources(LockableResource r1, LockableResource r2) throws InterruptedException {
		Logger.log("Начало обработки ресурсов: %s и %s", r1.getName(), r2.getName());

		LockableResource[] resources = {r1,r2};
    Arrays.sort(resources, (a, b) -> Integer.compare(a.getId(), b.getId()));

    LockableResource first = resources[0];
    LockableResource second = resources[1];

    Logger.log("Упорядоченный захват: сначала %s (ID=%d), затем %s (ID=%d)",
      first.getName(), first.getId(), second.getName(), second.getId());

			synchronized(first.getMonitor()){
        Logger.log("Захватил первый ресурс (по порядку): %s", first.getName());
        Thread.sleep(100);

        synchronized (second.getMonitor()) {
          Logger.log("Захватил второй ресурс (по порядку): %s. Внутри критической секции.",
            second.getName());
          Thread.sleep(50);
          Logger.log("Выход из критической секции.");
            }
            Logger.log("Освободил второй ресурс: %s", second.getName());
        }
        Logger.log("Освободил первый ресурс: %s", first.getName());
    }
			
}
	

