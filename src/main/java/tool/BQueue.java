package tool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BQueue {
	static BlockingQueue<String> queue=new ArrayBlockingQueue<String>(10);
	{
		
	}
	public static BlockingQueue<String> getQueue() {
		return queue;
	}

	public static void setQueue(BlockingQueue<String> queue) {
		BQueue.queue = queue;
	}
	

}
