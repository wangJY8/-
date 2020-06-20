package service;

import java.util.List;

import entity.User;

public interface UserService {
	List<User> getUser();
	
	User getUserById(int id);
	
	boolean modify(String id,String name,String age,String address,String tele);
	
	boolean addUser(String name,int age,String address,int tele,String password);
	
	boolean delUserById(int id);
}
