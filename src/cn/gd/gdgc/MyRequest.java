package cn.gd.gdgc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MyRequest {

	public static String post(String url,String param){
		String re="";
		HttpClient hc=new DefaultHttpClient();
		HttpPost p=new HttpPost(url);
		StringEntity entity=new StringEntity(param,"utf-8");
		p.setEntity(entity);
		try {
			HttpResponse response=hc.execute(p);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity en=response.getEntity();
				re=EntityUtils.toString(en,"utf-8");
			}	
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}
public static String get(String url){
	String re="";
    HttpClient hc=new DefaultHttpClient();
    HttpGet get=new HttpGet(url);
    try {
		HttpResponse response=hc.execute(get);
		if(response.getStatusLine().getStatusCode()==200){
			HttpEntity entity=response.getEntity();
			re=EntityUtils.toString(entity,"utf-8");		
		}
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return re;
}
	
}
