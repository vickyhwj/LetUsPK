package jmstest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.sql.DataSource;
import javax.swing.JEditorPane;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import cache.JedisPoolUtils;
import cache.JedisPoolUtils.WorkRunnable;

class SynRunnable implements Runnable {
	static Object lock = new Object();
	CountDownLatch latch;
	long sleepTime = 1;

	public SynRunnable(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized (lock) {
			try {
				Thread.sleep(sleepTime);
				System.out.println(Thread.currentThread().getName());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				latch.countDown();
			}
		}

	}
}

class ReetRunnable implements Runnable {
	CountDownLatch latch;
	static ReentrantLock lock = new ReentrantLock();
	static long waitTime = 10000;
	long sleepTime = 1;

	public ReetRunnable(CountDownLatch latch) {
		super();
		this.latch = latch;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			if (lock.tryLock(waitTime, TimeUnit.MILLISECONDS)) {
				System.out.println(Thread.currentThread().getName());
				Thread.sleep(sleepTime);
				lock.unlock();
			} else {
				System.out.println("fail");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			latch.countDown();
		}

	}

}

public class TimeUserTest {
	@Test
	public void redisTest() {
		int count = 100000;
		long start = System.currentTimeMillis();
		for (int i = 1; i <= count; ++i) {
			final int j = i;
			JedisPoolUtils.work(new WorkRunnable<Void>() {

				@Override
				public Void run(Jedis jedis) {
					// TODO Auto-generated method stub
					jedis.rpush("order", j + ":a");
					return null;
				}
			});
		}
		System.out.println("timeuse:" + (System.currentTimeMillis() - start));
	}

	@Test
	public void mysqlTest() throws SQLException {
		int count = 100000;
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"dataSource.xml");
		DataSource dataSource = (DataSource) ac.getBean("dataSource");
		PreparedStatement preparedStatement = dataSource.getConnection()
				.prepareStatement(
						"insert into myorder(userId,goodId) values(?,?)");
		long start = System.currentTimeMillis();
		for (int i = 1; i <= count; ++i) {
			final int j = i;
			preparedStatement.setString(1, String.valueOf(i));
			preparedStatement.setString(2, "a");
			preparedStatement.execute();
		}
		System.out.println("timeuse:" + (System.currentTimeMillis() - start));
	}

	@Test
	public void synTest() throws InterruptedException {
		int count = 10000;
		CountDownLatch latch = new CountDownLatch(count);
		long start = System.currentTimeMillis();
		for (int i = 1; i <= count; ++i) {
			new Thread(new SynRunnable(latch)).start();
		}
		latch.await();
		System.out.println(System.currentTimeMillis() - start);
	}

	@Test
	public void reetTest() throws InterruptedException {
		int count = 10000;
		CountDownLatch latch = new CountDownLatch(count);
		long start = System.currentTimeMillis();
		for (int i = 1; i <= count; ++i) {
			new Thread(new ReetRunnable(latch)).start();
		}
		latch.await();
		System.out.println(System.currentTimeMillis() - start);
	}

	@Test
	public void redisWatch() throws InterruptedException {

		final int count = 100000;
		JedisPoolUtils.work(new WorkRunnable<Void>() {

			@Override
			public Void run(Jedis jedis) {
				// TODO Auto-generated method stub
				jedis.set("a", String.valueOf(count));
				return null;
			}
		});
		final CountDownLatch latch = new CountDownLatch(count);
		long start=System.currentTimeMillis();
		for (int i = 1; i <= count; ++i) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					JedisPoolUtils.work(new WorkRunnable<Void>() {

						@Override
						public Void run(Jedis jedis) {
							// TODO Auto-generated method stub
							long start = System.currentTimeMillis();
							long c = jedis.decr("a");
				//			System.out.println(c);

							return null;
						}
					});
					latch.countDown();

				}
			}).start();
		}
		latch.await();
		System.out.println(System.currentTimeMillis()-start);
	}

	@Test
	public void jdbcTest() throws SQLException, InterruptedException {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"dataSource.xml");
		final DataSource dataSource = (DataSource) ac.getBean("dataSource");
		int count = 100000;
		Connection conn = dataSource.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("update good set count=? where id=?");
		ps.setInt(1, count);
		ps.setString(2, "a");
		ps.execute();
		long start = System.currentTimeMillis();
		final CountDownLatch latch = new CountDownLatch(count);
		for (int i = 1; i <= count; ++i) {
			new Thread(new Runnable() {
				public void run() {
					try {
						Connection mconn = dataSource.getConnection();
						PreparedStatement ps = mconn
								.prepareStatement("update good set count=count-1");
						ps.execute();
						ps.close();
						mconn.close();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						latch.countDown();
					}
				}
			}).start();
		}
		latch.await();
		System.out.println(System.currentTimeMillis() - start);
	}
	
	@Test
	public void redisMs() throws InterruptedException {

		final int count = 100000;
		JedisPoolUtils.work(new WorkRunnable<Void>() {

			@Override
			public Void run(Jedis jedis) {
				// TODO Auto-generated method stub
				jedis.set("a", "10000");
				jedis.del("order");
				return null;
			}
		});
		final CountDownLatch latch = new CountDownLatch(count);
		long start=System.currentTimeMillis();
		for (int i = 1; i <= count; ++i) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					JedisPoolUtils.work(new WorkRunnable<Void>() {

						@Override
						public Void run(Jedis jedis) {
							// TODO Auto-generated method stub
							if(Integer.parseInt(jedis.get("a"))<=0)
								return null;
							long re=jedis.decr("a");
							if(re<0){
								jedis.incr("a");
								return null;
							}
							else{
								jedis.rpush("order",Thread.currentThread().getName()+":"+"a");
							}
							return null;
						}
					});
					latch.countDown();

				}
			}).start();
		}
		latch.await();
		System.out.println(System.currentTimeMillis()-start);
	}

}
