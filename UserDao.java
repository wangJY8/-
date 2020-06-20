package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.User;

public interface UserDao {
	//获取所有学生信息
	List<User> getUser();
	//根据id获取个人用户信息
	User getUserById(int id);
	
	//修改学生信息
	int modify(
			@Param("id")String id,
			@Param("name")String name,
			@Param("age")String age,
			@Param("address")String address,
			@Param("tele")String tele);
	//添加学生
	int addUser(User user);
	
	//删除学生
	int delUserById(int id);
}
