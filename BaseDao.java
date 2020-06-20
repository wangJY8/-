package dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//增删改需要自动提交，查询不需要
public class BaseDao {
	
	public static SqlSessionFactory factory=null;
	
	public static void getFactory() {
	//mybatis的配置文件的路径
		String resource = "mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			factory=new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static SqlSession getSession() {
		if (factory==null) {
			getFactory();
		}
		SqlSession session=factory.openSession();
		return session;
	}

	public static SqlSession getSession(boolean isAutoCommit) {
		if (factory==null) {
			getFactory();
		}
		SqlSession session=factory.openSession(isAutoCommit);
		return session;
	}
}