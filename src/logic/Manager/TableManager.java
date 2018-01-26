package logic.Manager;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import logic.Table;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��1��25�� ����3:58:28 

���ӹ���

 */

public class TableManager {

	
	private TableManager _instance=null;
	
	private TableManager()
	{
		
	}
	
	public TableManager getInstance()
	{
		if(null==_instance)
		{
			_instance=new TableManager();
		}
		return _instance;
	}
	
	private ConcurrentHashMap<Integer,Table> tables=new ConcurrentHashMap<Integer,Table>();
	
}
