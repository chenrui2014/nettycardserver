package logic.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.ResponseMessage;
import logic.User;

/*
 *  �û�������
 */

public class UserManager {

	private static UserManager _instance=new UserManager();
	
	private UserManager()
	{
		
	}
	
	public static UserManager getInstance()
	{
		return _instance;
	}
	
	private ConcurrentHashMap<Integer,User> players=new ConcurrentHashMap<Integer,User>();//�������
	
	//������Ϣ��ָ�����
	public void sendMessage(User user,ResponseMessage message)
	{
		user.getHandlerContext().writeAndFlush(message);
	}
	
	//������Ϣ��������
	public void sendUsers(User[] users,ResponseMessage message)
	{
		for(int i=0;i<users.length;i++)
		{
			users[i].getHandlerContext().writeAndFlush(message);
		}
	}
	
	//���͸��������
	public void sendAll(ResponseMessage message)
	{
		for(User user : players.values())
		{
	    	user.getHandlerContext().writeAndFlush(message);
	    }
	}
	
	//����û�
	public  void addUser(User user)
	{
		 players.put(user.hashCode(),user);
	}
	
	//ɾ���û�
	public  void removeUser(User user)
	{
		players.remove(user.hashCode());
	}
	
	//��ȡ�û�����
	public int getUserCount()
	{
		return players.size();
	}
	
}
