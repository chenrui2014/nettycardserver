package logic.Manager;

import java.util.ArrayList;
import java.util.List;
import net.ResponseMessage;
import logic.User;

/*
 *  �û�������
 */

public class UserManager {

	private static UserManager _instance;
	
	private UserManager()
	{
		
	}
	
	public static UserManager getInstance()
	{
		if(null==_instance)
		{
			_instance=new UserManager();
		}
		return _instance;
	}
	
	private List<User> players=new ArrayList<User>();//�������
	
	//������Ϣ��ָ�����
	public void sendMessage(User user,ResponseMessage message)
	{
		user.getHandlerContext().writeAndFlush(message);
	}
	
	//������Ϣ��������
	public void sendUsers(User[] users,ResponseMessage message)
	{
		for(int i=0;i<users.length;i++)
		{
			users[i].getHandlerContext().writeAndFlush(message);
		}
	}
	
	//���͸��������
	public void sendAll(ResponseMessage message)
	{
	    for(int i=0;i<players.size();i++)
	    {
	    	players.get(i).getHandlerContext().writeAndFlush(message);
	    }
	}
	
}
