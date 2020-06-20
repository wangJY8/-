package service;

import java.util.List;

import entity.Message;
import entity.MessageVo;

public interface MessageService {
	boolean sendMsg(int sidFrom,String content);
	
	List<Message> getUnReadMsg();
	
	List<MessageVo> getUnDealedMsg();
	
	List<Message> getAgreeMsg();
	
	List<Message> getRefMsg();
	
	boolean deal(int mid,String type);
	
	List<Message> getAllMessage();
	//学生获取自己的留言信息
	List<Message> getMessageById(int id);
	
	//是否撤回
	boolean feedBack(int mid);
	
	//是否已读
	boolean readMessage();
	
	
}
