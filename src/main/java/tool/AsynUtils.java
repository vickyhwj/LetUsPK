package tool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynUtils {
	public static ExecutorService executor=Executors.newCachedThreadPool();
}
