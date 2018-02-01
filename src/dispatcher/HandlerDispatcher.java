package dispatcher;

import handler.GameHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import logic.LogicMain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.GameRequest;
import domain.MessageQueue;
import domain.MessageWorker;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��23�� ����11:07:38 

��Ϣ����

 */

public class HandlerDispatcher implements Runnable {

	
    private  final Logger logger=LoggerFactory.getLogger(getClass());
	
    private  ConcurrentHashMap<Integer,MessageQueue> sessionMsgQ=null;
    
    //�߳�
    private Executor messageExecutor;
    
    //��Ϣ�ַ�
    private Map<Integer,GameHandler> handleMap=null;
    
    //�Ƿ�������
    private boolean running;
    
    //����ʱ��
    private long sleepTime;
    
    public HandlerDispatcher()
    {
    	this.sessionMsgQ=new ConcurrentHashMap<Integer,MessageQueue>();
    	this.running=true;
    	this.sleepTime=200L;
    	
    }
    
    public void setHandlerMap(Map<Integer,GameHandler> handlerMap)
    {
    	this.handleMap=handlerMap;
    }
    
    public Map<Integer,GameHandler>  getHandlerMap()
    {
    	return this.handleMap;
    }
    
    public void setMessageExecutor(Executor  messageExecutor)
    {
    	this.messageExecutor=messageExecutor;
    }
    
    public void setSleepTime(long sleepTime)
    {
    	this.sleepTime=sleepTime;
    }
    
    /*
     * Ϊ���������Ϣ����
     */
    
    public void addMessageQueue(ChannelHandlerContext channel,MessageQueue messageQueue){
    	this.sessionMsgQ.put(channel.hashCode(), messageQueue);
    }
    
    /*
     * Ϊ����ɾ����Ϣ����
     */
    public void removeMessageQueue(ChannelHandlerContext channel)
    {
    	MessageQueue queue=(MessageQueue)this.sessionMsgQ.remove(channel.handler());
    	if(queue!=null)
    	{
    		queue.clear();
    		queue=null;
    	}
    }
    
    /*
     * �����Ϣ
     */
    
    public void addMessage(GameRequest request)
    {
    	try
    	{
    		MessageQueue messageQueue=(MessageQueue)this.sessionMsgQ
    				.get(Integer.valueOf(request.GetChannelContext().hashCode()));
    		if(messageQueue==null)
    		{
    			messageQueue=new MessageQueue();
    			sessionMsgQ.put(Integer.valueOf(request.GetChannelContext().hashCode()),messageQueue);
    		    messageQueue.add(request);
    		}
    		else
    		{
    			messageQueue.add(request);
    		}
    	}
    	catch(Exception ex)
    	{
    		logger.error("�쳣��Ϣ:"+ex);
    	}
    }
    
	@Override
	public void run() {
	   
		while(this.running)
		{
			try
			{
				for(MessageQueue messageQueue:sessionMsgQ.values())
				{
					if((messageQueue!=null) && (messageQueue.size()>0) && (!messageQueue.isRunning()))
					{
						GameRequest request= (GameRequest)messageQueue.getRequestQueue().poll();
						int messageId=request.getRequestId();
						Map<Integer,GameHandler> maphandlers=LogicMain.getInstance().getDispatcher().getHandlerMap();
					    GameHandler handler=maphandlers.get(messageId);
					    if(handler!=null)
					    {
					    	handler.execute(request);
					    }
					    else
					    {
					    	logger.info("û���ҵ����ʵĴ������!");
					    }
						/*MessageWorker messageWorker=new MessageWorker(messageQueue);
						if(this.messageExecutor!=null)
						{
						    this.messageExecutor.execute(messageWorker);
						    messageQueue.setRunning(true);
						}
						else
						{
							logger.info("messageExecutor is null!");
						}*/
					}
				}
			}catch(Exception ex)
			{
				logger.error("�쳣��Ϣ��"+ex);
			}
			try
			{
				Thread.sleep(this.sleepTime);
			}
			catch(Exception ex)
			{
			    logger.error("�쳣��Ϣ:"+ex);
			}
		}
		
	}
	
	public void stop()
	{
		running=false;
	}

}
