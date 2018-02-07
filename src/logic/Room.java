package logic;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ResponseMessage;


/*
 * ������
 */

public class Room {
   
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private final int maxusers=8;//8����λ
	
	private ConcurrentHashMap<Integer,User> users=new ConcurrentHashMap<Integer,User>(); //�����ϵ�����
	
	private Integer roomno=-1; //����
	
	private Vector roomindexno=new Vector();//��λ����
	
	private Object lockobj=new Object();//lock
	
	public Room()
	{
		for(int i=0;i<maxusers;i++)
		{
	    	roomindexno.add(i);
		}
	}
	
	/**
	 * ���÷����
	 * @param roomno
	 */
	public void setRoomNo(int roomno)
	{
		this.roomno=roomno;
	}
	
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
		if(users.size()>maxusers)
		{
			logger.info("������������");
			return;
		}
		
		synchronized(lockobj)
		{
		  //������λ��
		  int roomindex=(int) roomindexno.get(roomindexno.size()-1);
		  user.setRoomIndex(roomindex);
		}
		
		users.put(user.hashCode(), user);
	    
	}
	
	/**
	 * ɾ�������ĳ����
	 * @param user
	 */
	public void remoUser(User user)
	{
		users.remove(users.hashCode());
		
		synchronized(lockobj)
		{
	    	//������λ��
		    roomindexno.add(user.getRoomIndex());
		}
		
	}
	
}
