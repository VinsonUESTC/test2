package auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WeixinUtil {   
	/**
	 * 定义常量
	 */
	// 获取access_token的接口地址（GET） 限2000（次/天）  
    public final static String access_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRECT";  
    //获取user_ticket接口地址（POST)
    public final static String user_ticket_url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";  
    //获取user_detail接口地址（POST）
    public final static String user_detail_url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";  
    //公司ID
    private final static String CorpID = "wwf32367ba7d119a07";
    //应用ID
    private final static String SECRECT = "yn0MNF5-72krtaOBi94fkThYJBkGkYX8t5SHFk0ZroA";
    //log
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	//public static void main(String[] args){
		//System.out.println(getUserDetail(getAccessToken(CorpID,SECRECT).getToken(),getUserTicket(getAccessToken(CorpID,SECRECT).getToken(),"eYPDYwk16GoBepKqM1hUdCQngAyu-9oFppiMUacQRE4")));
		//System.out.println(getAccessToken(CorpID,SECRECT).getToken());
	//}
  
    //发起https请求并获取结果 
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  

      
    //获取access_token 
    public static AccessToken getAccessToken() {  
        AccessToken accessToken = null;  
        String requestUrl = access_token_url.replace("ID", CorpID).replace("SECRECT", SECRECT);  
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
        // 如果请求成功  
        if (null != jsonObject) {  
            try {  
                accessToken = new AccessToken();  
                accessToken.setToken(jsonObject.getString("access_token"));  
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));  
            } catch (JSONException e) {  
                accessToken = null;  
                // 获取token失败  
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            }  
        }  
        return accessToken;  
    }  
    
    //获取user_ticket
    public static JSONObject getUserTicket(String access_token, String code) {  
        String requestUrl = user_ticket_url.replace("ACCESS_TOKEN", access_token).replace("CODE", code);  
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
        System.out.println(jsonObject);
        String temp = "{\"user_ticket\":\""+jsonObject.getString("user_ticket")+"\"}";
		return JSONObject.fromObject(temp);  
    }  
   
    //获取user_detail
    public static  JSONObject getUserDetail(String token,JSONObject user_ticket){  
        //消息json格式  
        String jsonContext=user_ticket.toString();  
        JSONObject responseBody = null;
		try {  
             CloseableHttpClient httpclient = HttpClients.createDefault();  
             HttpPost httpPost= new HttpPost("https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token="+token);  
             //发送json格式的数据  
             StringEntity myEntity = new StringEntity(jsonContext,   
                       ContentType.create("text/plain", "UTF-8"));  
             //设置需要传递的数据  
             httpPost.setEntity(myEntity);  
             // Create a custom response handler  
            ResponseHandler<JSONObject> responseHandler = new ResponseHandler<JSONObject>() {  
                //对访问结果进行处理  
                public JSONObject handleResponse(  
                        final HttpResponse response) throws ClientProtocolException, IOException {  
                    int status = response.getStatusLine().getStatusCode();  
                    if (status >= 200 && status < 300) {  
                        HttpEntity entity = response.getEntity();  
                        if(null!=entity){  
                            String result= EntityUtils.toString(entity);  
                            //根据字符串生成JSON对象  
                            JSONObject resultObj = JSONObject.fromObject(result);  
                            return resultObj;  
                        }else{  
                            return null;  
                        }  
                    } else {  
                        throw new ClientProtocolException("Unexpected response status: " + status);  
                    }  
                }  
  
            };  
          //返回的json对象  
            responseBody = httpclient.execute(httpPost, responseHandler);  
            System.out.println(responseBody);  
            httpclient.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
         return responseBody;  
    } 
	//获取user_position
	public static JSONObject getUserPosition(String access_token, String userid)
	{
		String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID".replace("ACCESS_TOKEN", access_token).replace("USERID", userid);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		System.out.println(jsonObject);
		String temp = "{\"position\":\"" + jsonObject.getString("position") + "\"}";
		return JSONObject.fromObject(temp);
	}
}  