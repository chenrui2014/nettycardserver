package domain;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��22�� ����7:57:46 

 */

public class MessageQueue {
    
	private Queue<GameRequest> requestQueue=null;
	private boolean running=false;
	
	public MessageQueue()
	{
		this.requestQueue=new ConcurrentLinkedQueue<GameRequest>();
		this.setRunning(false);
	}
	
	public Queue<GameRequest> getRequestQueue()
	{
		return this.requestQueue;
	}
	
	public void setRequestQueue(Queue<GameRequest> requestQueue)
	{
		this.requestQueue=requestQueue;
	}
	
	public void clear()
	{
		this.requestQueue.clear();
		this.requestQueue=null;
	}
	
	public int size()
	{
		return this.requestQueue!=null?this.requestQueue.size():0;
	}
	
	public boolean add(GameRequest request)
	{
		return this.requestQueue.add(request);
	}
	
	public void setRunning(boolean running)
	{
		this.running=running;
	}
	
	public boolean isRunning()
	{
		return this.running;
	}
	
}
