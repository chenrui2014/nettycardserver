package logic;

import handler.ExitHandler;
import handler.GameHandler;
import handler.LoginHandler;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import constant.HandlerId;
import dispatcher.HandlerDispatcher;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��23�� ����3:08:16 

����������

 */

public class Manager {
	
   private HandlerDispatcher handlerdispatcher=new HandlerDispatcher();
	
   public HandlerDispatcher getDispatcher()
   {
	   return handlerdispatcher;
   }
   
   private static Manager _instance;
	
   private Manager()
   {
	   //���ô���handler
	   Map<Integer,GameHandler> handleMap=new LinkedHashMap<Integer,GameHandler>();
	   handleMap.put(HandlerId.login.ordinal(), new LoginHandler());
	   handleMap.put(HandlerId.exit.ordinal(), new ExitHandler());
	   handlerdispatcher.setHandlerMap(handleMap);
	  
	   //��Ϣ�ַ�����ʼ����
	   handlerdispatcher.setMessageExecutor(Executors.newFixedThreadPool(10));
	   new Thread(handlerdispatcher).start();;
     
   };
   
   public static Manager getInstance()
   {
	   if(_instance==null)
	   {
		   _instance=new Manager();
	   }
	   return _instance;
   }
   
}
