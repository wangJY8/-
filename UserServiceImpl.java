package service.impl;

import java.util.List;

import dao.BaseDao;
import dao.UserDao;
import entity.User;
import service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	public List<User> getUser() {
		// TODO Auto-generated method stub
		return BaseDao.getSession().getMapper(UserDao.class).getUser();
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return BaseDao.getSession().getMapper(UserDao.class).getUserById(id);
	}

	@Override
	public boolean modify(String id,String name, String age, String address, String tele) {
		if (BaseDao.getSession(true).getMapper(UserDao.class).modify(id,name, age, address, tele)>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addUser(String name, int age, String address, int tele,String password) {
		User user = new User();
		user.setAge(age);
		user.setName(name);
		user.setAddress(address);
		user.setTele(tele);
		user.setPassword(password);
		if (BaseDao.getSession(true).getMapper(UserDao.class).addUser(user)>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delUserById(int id) {
		if (BaseDao.getSession(true).getMapper(UserDao.class).delUserById(id)>0) {
			return true;
		}
		return false;
	}

}
