package constant;

/*
@author YHL
@qq: 1357098586
@version ����ʱ�䣺2018��2��6�� ����3:51:13 

�ط����ͻ��˵���Ϣ

 */

public enum ResponseHandlerId {

     _createroom(128),//��������ɹ�
	 _nofindroom(129);//û�д˷���
     
     private final int value;
     
     private ResponseHandlerId(int value)
     {
    	 this.value=value;
     }
     
     public int getValue()
     {
    	 return value;
     }
}
