package jp.co.conit.testc2dm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigData {
	String sn_token=null;
	String emailaddress=null;
	
	static ConfigData loadConfigData(Context ctx){
		ConfigData data = new ConfigData();
		
		SharedPreferences pref = ctx.getSharedPreferences("c2dmtest_pref", Activity.MODE_PRIVATE); 
		data.sn_token = pref.getString("SN_TOKEN", "アプリ登録画面で表示されるアクセストークン");
		data.emailaddress = pref.getString("SENDER_ADDRESS", "C2DM登録を行ったユーザのGoogleID(メールアドレス)");
		
		return data;
	}
	static void saveConfigData(Context ctx,ConfigData cd){
		SharedPreferences pref = ctx.getSharedPreferences("c2dmtest_pref", Activity.MODE_PRIVATE); 
		Editor editor =  pref.edit();
		editor.putString("SN_TOKEN", cd.sn_token);
		editor.putString("SENDER_ADDRESS", cd.emailaddress);
		editor.commit();
		
	}

}
