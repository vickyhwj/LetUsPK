package redistest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

class Peo{
	int age;
	public Peo(int age){
		this.age=age;
	}
}
public class AtmicTest {
	static Peo peo=new Peo(0);
	@Test
	public void t1(){
		ExecutorService pool=Executors.newCachedThreadPool();
		for(int i=1;i<=1000;++i)
			pool.submit(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					peo=new Peo(peo.age+1);
				}
			});
		pool.shutdown();
		System.out.println(peo.age);
	}
	@Test
	public void t2(){
		final AtomicReference<Peo> aPeo=new AtomicReference<Peo>(peo);
		ExecutorService pool=Executors.newCachedThreadPool();
		for(int i=1;i<=100000;++i)
			pool.submit(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true){
						Peo p=aPeo.get();
						if(aPeo.compareAndSet(p, new Peo(p.age+1))){
						
							break;
						}
					}
				}
			});
		pool.shutdown();
		System.out.println(aPeo.get().age);
	}
	@Test
	public void t3(){
		final AtomicInteger ai=new AtomicInteger(0);
		ExecutorService pool=Executors.newCachedThreadPool();
		for(int i=1;i<=100000;++i)
			pool.submit(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true){
						Integer p=ai.get();
						ai.incrementAndGet();break;
					//	if(ai.compareAndSet(p, p+1)){						
					//		break;
					//	}
					}
				}
			});
		pool.shutdown();
		System.out.println(ai.get());
	}
}
