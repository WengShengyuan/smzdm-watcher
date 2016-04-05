package zdm.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class NetUtils {

	public static final String CHARSET = "UTF-8";
	
	public static String httpGet(String pageUrl, HttpHeader header) throws Exception{
		return getAction(pageUrl, header);
	}
	
	public static String httpGet(String pageUrl) throws Exception{
		return getAction(pageUrl, null);
	}
	
	private static String getAction(String pageUrl, HttpHeader header) throws Exception{
		@SuppressWarnings("resource")
		HttpClient client  = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet();
	    httpGet.setURI(new URI(pageUrl));
	    String content = "";
	    if(header != null){
	    	httpGet = header.attachHeader(httpGet);
	    }
	    BufferedReader in=null;
	    try {
			HttpResponse response = client.execute(httpGet);      
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {      
			    	content = EntityUtils.toString(response.getEntity(), CHARSET);
			        
			} else {
				throw new Exception("网络解析错误:" + response.getStatusLine());
			}
		} catch (Exception e) {
			throw e;
		} finally{
	    	if(in != null){
	    		in.close();
	    	}
	    }
	    return content;
	}
	


	public static String post(String postUrl, HashMap<String, String> map,
			String encoding) throws Exception {
		URL url = new URL(postUrl);
		HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
		try {
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			OutputStreamWriter osw = new OutputStreamWriter(
					connection.getOutputStream(), encoding);
			if (map != null) {
				osw.write(parseParam(map));
			}
			osw.flush();
			osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			// 一定要有返回值，否则无法把请求发送给server端。
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer.toString();

	}

	private static String parseParam(HashMap<String, String> map)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key).append("=").append(map.get(key));
			sb.append("&");
		}
		System.out.println("param:" + sb.substring(0, sb.length() - 1));
		return sb.substring(0, sb.length() - 1);

	}
	
	
	public static String postJson(String urlStr, String json) throws Exception{
		URL url = new URL(urlStr);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setDoOutput(true);  
        http.setDoInput(true);  
        http.setRequestMethod("POST");  
        http.connect();  
        OutputStreamWriter out = new OutputStreamWriter(http.getOutputStream(), "UTF-8"); 
        out.append(json);  
        out.flush();  
        out.close();  
        BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        http.disconnect();
        return buffer.toString();
	}

}
