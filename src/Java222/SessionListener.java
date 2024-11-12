package Java222;


import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class SessionListener implements HttpSessionListener,ServletRequestListener {
	static int count =0;
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		count++;
		System.out.println("家里小贼还有"+count);
//		se.getSession().setMaxInactiveInterval(5);
		//������ѯ
		
		System.out.println("退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		count--;
		System.out.println("家里小贼还有"+count);
		System.out.println("退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退退");
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		System.out.println("进来了"+sre.getServletRequest().getRemoteHost());
		
	}
}
