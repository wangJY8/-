package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import entity.Message;

public interface MessageDao {
	int sendMsg(@Param("id")int sidFrom,@Param("content")String content);
	
	List<Message> getMessage(Integer[] statuses);
	
	int deal(@Param("mid")int mid,@Param("status")int status);
	
	List<Message> getAllMessage();
	
	//读留言
	int readMessage();
	
	//学生获取自己的留言信息
	List<Message> getMessageById(int id);
}
