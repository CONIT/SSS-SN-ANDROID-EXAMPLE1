package jp.co.conit.testc2dm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigData {

	static final String DEFAULT_SN_TOKEN = "<<管理画面で表示されるSNアクセストークン>>";
	static final String DEFAULT_SN_SERVER = "<<管理画面で表示されるSNサーバ名とAPIURL>>";
	static final String DEFAULT_SENDER_EMAIL = "<<GoogleでC2DM利用登録したEメールアドレス>>";
	
	String sn_token=null;
	String emailaddress=null;
	String sn_server=null;
	
	static ConfigData loadConfigData(Context ctx){
		ConfigData data = new ConfigData();
		
		SharedPreferences pref = ctx.getSharedPreferences("c2dmtest_pref", Activity.MODE_PRIVATE); 
		data.sn_token = pref.getString("SN_TOKEN", DEFAULT_SN_TOKEN);
		data.sn_server = pref.getString("SERVER_URL", DEFAULT_SN_SERVER);
		data.emailaddress = pref.getString("SENDER_ADDRESS", DEFAULT_SENDER_EMAIL);
		
		return data;
	}
	static void saveConfigData(Context ctx,ConfigData cd){
		SharedPreferences pref = ctx.getSharedPreferences("c2dmtest_pref", Activity.MODE_PRIVATE); 
		Editor editor =  pref.edit();
		editor.putString("SN_TOKEN", cd.sn_token);
		editor.putString("SERVER_URL", cd.sn_server);
		editor.putString("SENDER_ADDRESS", cd.emailaddress);
		editor.commit();
		
	}

}
