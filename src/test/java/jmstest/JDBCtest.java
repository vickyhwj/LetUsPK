package jmstest;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class JDBCtest {
	static void init(DataSource ds) throws SQLException{
		java.sql.Connection conn=ds.getConnection();
		conn.prepareStatement("delete from good").execute();
		conn.prepareStatement("delete from myorder").execute();
		conn.prepareStatement("insert into good values('a',1000)").execute();
		conn.close();
		
	}

	public static void main(String[] args) throws InterruptedException, SQLException {
		ApplicationContext ac=new ClassPathXmlApplicationContext("dataSource.xml");
		DataSource dataSource= (DataSource) ac.getBean("dataSource");
		init(dataSource);
		SqlSessionFactory sessionFactory=(SqlSessionFactory) ac.getBean("sqlSessionFactory");
		int threadCount=500000;
		CountDownLatch latch=new CountDownLatch(threadCount);
		long start=System.currentTimeMillis();
		for(int i=1;i<=threadCount;++i){
			new ServiceThread(sessionFactory,latch).start();
		}
		latch.await();
		System.out.println("time"+(System.currentTimeMillis()-start));
	}

}
class ServiceThread extends Thread{
	SqlSessionFactory sessionFactory;
	CountDownLatch latch;
	
	public ServiceThread(SqlSessionFactory sessionFactory, CountDownLatch latch) {
		super();
		this.sessionFactory = sessionFactory;
		this.latch = latch;
	}

	public void run(){
		SqlSession session=null;
		java.sql.Connection connection=null;
		try {
			session=sessionFactory.openSession();
			connection= session.getConnection();
			java.sql.PreparedStatement ps= connection.prepareStatement("update good set count=count-1 where id='a' and count>0");
			int re=ps.executeUpdate();
			if(re>0){
				java.sql.PreparedStatement ps2= connection.prepareStatement("insert into myorder(userId,goodId) values(?,?)");
				ps2.setString(1, Thread.currentThread().getName());
				ps2.setString(2, "a");
				ps2.execute();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				
				connection.close();
				session.close();
				latch.countDown();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
