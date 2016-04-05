package zdm.utils.weixin;

import com.alibaba.fastjson.JSON;

import zdm.utils.http.NetUtils;

public class WinxinPoster {
	
	private static final String msgtype = "text";
	private static final String to ="your user name";				//按照实际情况填写
	private static final Integer appId = 2;
	private static final String corpId = "your corp id";			//按照实际情况填写
	private static final String corpsecret = "your corp secret";	//按照实际情况填写
	private static final String postUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
	private static final String getUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpId+"&corpsecret="+corpsecret;
	public static boolean post(String string) throws Exception{
		
		PostParam param = new PostParam();
		
		param.setMsgtype(msgtype);
		param.setAgentid(appId);
		param.setTouser(to);
		param.setText(string);
		// STEP 1 : 获取token
		String getTokenStr = NetUtils.httpGet(getUrl);
		GetEntity getEntity = JSON.parseObject(getTokenStr, GetEntity.class);
		String token = getEntity.getAccess_token();
		
		// STEP 2: 发送信息
		String postJsonString = JSON.toJSONString(param);
		System.out.println(postJsonString);
		String postStr = NetUtils.postJson(postUrl+token, postJsonString);
		PostEntity postEntity = JSON.parseObject(postStr, PostEntity.class);
		if(postEntity.errCode.equals("0")){
			return true;
		} else {
			return false;
		}
	}

}
