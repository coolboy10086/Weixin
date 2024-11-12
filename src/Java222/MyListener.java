package Java222;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class MyListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {//����������
		System.out.println("--------------------家里门开了，一级警报--------------------------");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {//��������ʼ��
		System.out.println("--------------------有人溜走了--------");
	}
	
}
