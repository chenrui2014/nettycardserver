package logic;

import handler.CreateRoomHandler;
import handler.ExitHandler;
import handler.GameHandler;
import handler.LoginHandler;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import constant.RequestHandlerId;
import dispatcher.HandlerDispatcher;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��23�� ����3:08:16 

����������

 */

public class LogicMain {
	
   private HandlerDispatcher handlerdispatcher=new HandlerDispatcher();
	
   public HandlerDispatcher getDispatcher()
   {
	   return handlerdispatcher;
   }
   
   private static LogicMain _instance;
	
   private LogicMain()
   {
	   
	   //���ô���handler
	   Map<Integer,GameHandler> handleMap=new LinkedHashMap<Integer,GameHandler>();
	   
	   handleMap.put(RequestHandlerId.login.ordinal(), new LoginHandler());
	   handleMap.put(RequestHandlerId.exit.ordinal(), new ExitHandler());
	   handleMap.put(RequestHandlerId.createroom.ordinal(), new CreateRoomHandler());
	  
	   handlerdispatcher.setHandlerMap(handleMap);
	  
	   //��Ϣ�ַ�����ʼ����
	   handlerdispatcher.setMessageExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2));
	 
	   new Thread(handlerdispatcher).start();;
     
   };
   
   public static LogicMain getInstance()
   {
	   if(_instance==null)
	   {
		   _instance=new LogicMain();
	   }
	   return _instance;
   }
   
}
