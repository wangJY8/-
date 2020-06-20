package service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dao.BaseDao;
import dao.MessageDao;
import dao.UserDao;
import entity.Message;
import entity.MessageVo;
import entity.User;
import service.MessageService;

public class MessageServiceImpl implements MessageService{

	@Override
	public boolean sendMsg(int sidFrom, String content) {
		if (BaseDao.getSession(true).getMapper(MessageDao.class).sendMsg(sidFrom, content)>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Message> getUnReadMsg() {
		// TODO Auto-generated method stub
		Integer[] status = {0};
 		return BaseDao.getSession().getMapper(MessageDao.class).getMessage(status);
	}

	@Override
	public List<Message> getAgreeMsg() {
		// TODO Auto-generated method stub
		Integer[] status = {2};
		return BaseDao.getSession().getMapper(MessageDao.class).getMessage(status);
	}

	@Override
	public List<Message> getRefMsg() {
		// TODO Auto-generated method stub
		Integer[] status = {3};
 		return BaseDao.getSession().getMapper(MessageDao.class).getMessage(status);
	}
	
	@Override
	public boolean deal(int mid, String type) {
		// TODO Auto-generated method stub
		int status = 0;
		if (type.equals("agree")) {
			status = 2;
		}else if(type.equals("disagree")) {
			status = 3;
		}
		
		if (BaseDao.getSession(true).getMapper(MessageDao.class).deal(mid, status)>0) {
			return true;
		}
		return false;
	}

	@Override
	public List<MessageVo> getUnDealedMsg() {
		Integer[] status = {0,1};
		List<Message> mlist = BaseDao.getSession().getMapper(MessageDao.class).getMessage(status);
		System.out.println(mlist);
		List<MessageVo> mvList=new ArrayList<MessageVo>();
		for (Message message : mlist) {
			mvList.add(changeMsgVo(message));
		}
		return mvList;
	}

	@Override
	public List<Message> getMessageById(int id) {
		// TODO Auto-generated method stub
		return BaseDao.getSession().getMapper(MessageDao.class).getMessageById(id);
	}

	@Override
	public List<Message> getAllMessage() {
		// TODO Auto-generated method stub
		return BaseDao.getSession().getMapper(MessageDao.class).getAllMessage();
	}

	private MessageVo changeMsgVo(Message msg) {
		MessageVo vo=new MessageVo();
		vo.setMid(msg.getMid());
		vo.setContent(msg.getContent());
		System.out.println(msg.getSidFrom());
		User user = BaseDao.getSession().getMapper(UserDao.class).getUserById(msg.getSidFrom());
		vo.setSname(user.getName());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setCtime(sdf.format(msg.getCreatedTime()));
		if (msg.getUpdateTime() == null) {
			vo.setUtime("2020-01-01 00:00:00");;
		}else {
			
			vo.setUtime(sdf.format(msg.getUpdateTime()));
		}
		System.out.println(vo);
		return vo;
	}
	@Override
	public boolean feedBack(int mid) {
		int status = 4;
		if (BaseDao.getSession(true).getMapper(MessageDao.class).deal(mid, status)>0) {
			return true;
		}
		return false;
	}
	@Override
	public boolean readMessage() {
		BaseDao.getSession(true).getMapper(MessageDao.class).readMessage();
		return true;
	}
}
