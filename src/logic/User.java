package logic;

import io.netty.channel.ChannelHandlerContext;


/*
 * �û���
 * 
 */

public class User {
   
	 private int weixinid=-1;//���΢��ID
	 
	 public int getId()
	 {
		 return weixinid;
	 }
	 
	 private String name="";//�������
	 
	 public String getName()
	 {
		 return name;
	 }
	 
	 private int roomindex=0;//����������ϵ����
	 
	 public int getRoomIndex()
	 {
		 return roomindex;
	 }
	 
	 /**
	  * 
	  * @param index
	  */
	 public void setRoomIndex(int index)
	 {
		 roomindex=index;
	 }
	 
	 private ChannelHandlerContext ctx;//ͨ�Źܵ� 
	 
	 public ChannelHandlerContext getHandlerContext()
	 {
		 return ctx;
	 }
	 
	 private Room room=null;//������ڷ���
	 
	 public Room getRoom()
	 {
		 return room;
	 }
	 
	 
	 
}
