package logic;

import io.netty.channel.ChannelHandlerContext;


/*
 * �û���
 * 
 */

public class User {
   
	 private int id=-1;//���ID
	 
	 public int getId()
	 {
		 return id;
	 }
	 
	 private String name="";//�������
	 
	 public String getName()
	 {
		 return name;
	 }
	 
	 private int tableindex=0;//����������ϵ����
	 
	 public int getTableIndex()
	 {
		 return tableindex;
	 }
	 
	 private ChannelHandlerContext ctx;//ͨ�Źܵ� 
	 
	 public ChannelHandlerContext getHandlerContext()
	 {
		 return ctx;
	 }
	 
	 private Table table=null;//�����������
	 
	 public Table getTable()
	 {
		 return table;
	 }
	 
}
