package logic;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ResponseMessage;


/*
 * ������
 */

public class Room {
   
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private ConcurrentHashMap<Integer,User> users=new ConcurrentHashMap<Integer,User>(); //�����ϵ�����
	
	private Integer roomno=-1; //����
	
	/*
	 * ���ط����
	 */
	public Integer getRoomNo()
	{
		return roomno;
	}
	
	/*
	 * ����ĳ����
	 */
	public void sendToUser(User user,ResponseMessage message)
	{
		ChannelHandlerContext ctx=user.getHandlerContext();
		if(null==ctx)
		{
			logger.info("ctx is error!");
			return;
		}
		user.getHandlerContext().writeAndFlush(message);
	}
	
	/*
	 * ����һЩ��
	 */
	public void sendToUsers(User[] users,ResponseMessage message)
	{
		for(int i=0;i<users.length;i++)
		{
			ChannelHandlerContext ctx=users[i].getHandlerContext();
			if(null==ctx)
			{
				logger.error("ctx is error!");
				return;
			}
			ctx.writeAndFlush(message);
		}
	}
	
	/*
	 * ���͸������������
	 */
	public void sendAll(ResponseMessage message)
	{
		for(User user:users.values())
		{
			user.getHandlerContext().writeAndFlush(message);
		}
	}
	
	/**
	 * �����ҵ�����
	 * @param user
	 */
	public void addUser(User user)
	{
		users.put(user.hashCode(), user);
	
	}
	
	/**
	 * ɾ�������ĳ����
	 * @param user
	 */
	public void remoUser(User user)
	{
		users.remove(users.hashCode());
	}
	
}
