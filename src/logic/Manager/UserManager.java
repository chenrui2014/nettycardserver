package logic.Manager;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ResponseMessage;
import logic.User;

/*
 *  �û�������
 */

public class UserManager {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private static UserManager _instance=new UserManager();//��ֹ������������������ֱ��new
	
	private UserManager()
	{
		
	}
	
	public static UserManager getInstance()
	{
		return _instance;
	}
	
	private ConcurrentHashMap<Integer,User> players=new ConcurrentHashMap<Integer,User>();//�������
	private ConcurrentHashMap<ChannelHandlerContext,User> cus=new ConcurrentHashMap<ChannelHandlerContext,User>();//��ҵ�channelcontext,user
	
	//������Ϣ��ָ�����
	public void sendMessage(User user,ResponseMessage message)
	{
		user.Send(message);
	}
	
	//������Ϣ��������
	public void sendUsers(User[] users,ResponseMessage message)
	{
		for(int i=0;i<users.length;i++)
		{
			users[i].Send(message);
		}
	}
	
	//���͸��������
	public void sendAll(ResponseMessage message)
	{
		for(User user : players.values())
		{
	    	user.Send(message);
	    }
	}
	
	//����û�
	public  void addUser(User user)
	{
		 players.put(user.hashCode(),user);
		 cus.put(user.getHandlerContext(),user);
	}
	
	//ɾ���û�
	public  void removeUser(User user)
	{
		players.remove(user.hashCode());
		cus.remove(user.getHandlerContext());
	}
	
	/**
	 * ������ҵ�ctx
	 * @param ctx
	 * @return
	 */
	public User getUser(ChannelHandlerContext ctx)
	{
		return cus.get(ctx);
	}
	
	//��ȡ�û�����
	public int getUserCount()
	{
		return players.size();
	}
	
}
