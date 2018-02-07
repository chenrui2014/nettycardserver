package logic;

import io.netty.channel.ChannelHandlerContext;


/*
 * 用户类
 * 
 */

public class User {
   
	 private int weixinid=-1;//玩家微信ID
	 
	 public int getId()
	 {
		 return weixinid;
	 }
	 
	 private String name="";//玩家姓名
	 
	 public String getName()
	 {
		 return name;
	 }
	 
	 private int tableindex=0;//玩家在桌子上的序号
	 
	 public int getTableIndex()
	 {
		 return tableindex;
	 }
	 
	 private ChannelHandlerContext ctx;//通信管道 
	 
	 public ChannelHandlerContext getHandlerContext()
	 {
		 return ctx;
	 }
	 
	 private Room room=null;//玩家所在房间
	 
	 public Room getRoom()
	 {
		 return room;
	 }
	 
}
