package jp.co.conit.testc2dm;

import java.util.ArrayList;
import java.util.List;
import jp.co.conit.SSS_SN_ANDROID_EXAMPLE1.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class C2DMReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(
				"com.google.android.c2dm.intent.REGISTRATION")) {
			handleRegistration(context, intent);
		} else if (intent.getAction().equals(
				"com.google.android.c2dm.intent.RECEIVE")) {
			handleMessage(context, intent);
		}
	}

	private void handleRegistration(Context context, Intent intent) {

		if (intent.getStringExtra("error") != null) {
			// 登録エラーの場合は、Samurai Notificationに登録しない
			Log.e("handleRegistration", "register error");
		} else if (intent.getStringExtra("unregistered") != null) {
			// 登録解除の場合は、Samurai Notificationに登録しない
			Log.e("handleRegistration", "unregister success");
		} else if (intent.getStringExtra("registration_id") != null) {
			// HTTPにてSamurai Notificationサーバに送信する
			String registration_id = intent.getStringExtra("registration_id");
			SendSamuraiNotificationServerTask task = new SendSamuraiNotificationServerTask(context);
			task.execute(registration_id);
		}

	}

	private void handleMessage(Context context, Intent intent) {
		//C2DMメッセージ受信時の処理
		
		Log.e("handleMessage", "受信中");

		String sn_ticker = intent.getExtras().getString("sn_ticker");
		String sn_title = intent.getExtras().getString("sn_title");
		String sn_text = intent.getExtras().getString("sn_text");
		String sn_number = intent.getExtras().getString("sn_number");
		Integer n_number = null;
		if (sn_number != null) {
			n_number = Integer.parseInt(sn_number);
		}
		String sn_userdata = intent.getExtras().getString("sn_userdata");
		String sn_message_id = intent.getExtras().getString("sn_message_id");

		// Notification に出す
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Notification notification = new Notification(R.drawable.ic_launcher,
				sn_ticker, System.currentTimeMillis());

		notification.defaults |= Notification.DEFAULT_VIBRATE;
		Intent opneintent = new Intent(context, C2DMtestappActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				opneintent, 0);
		notification.setLatestEventInfo(context, sn_title, sn_text,
				contentIntent);
		if (n_number != null) {
			notification.number = n_number;
		}
		notificationManager.notify(1, notification);

		// ユーザデータがある場合
		if (sn_userdata != null && sn_userdata.length() > 0) {
			Toast.makeText(context, sn_userdata, 20).show();
		}
		// sn_message_idがある場合
		if (sn_message_id != null && sn_message_id.length() > 0) {
			Toast.makeText(context, sn_message_id, 20).show();
		}

	}
	public class SamuraiNotificationServerResult
	{
		int httpStatus;
		String responseString;
		Exception causeException;
		public SamuraiNotificationServerResult(){
			httpStatus = -1;
			responseString = null;
			causeException = null;
		}
	}
	
	
	public class SendSamuraiNotificationServerTask extends AsyncTask<String, Integer, SamuraiNotificationServerResult>{
		private Context dependContext = null;

		public SendSamuraiNotificationServerTask(Context activity) {
			dependContext = activity;
		}
		
		protected void onPreExecute() {
		}
		@Override
		protected void onPostExecute(SamuraiNotificationServerResult result) {
		    
			if(result.causeException != null){
				Toast.makeText(dependContext,"通信エラー("+result.causeException.toString()+")" , 30).show();
			}else{
				if(result.httpStatus == 200){
					Toast.makeText(dependContext,"通信成功(status=200)" , 30).show();
				}else{
					Toast.makeText(dependContext,"通信エラー(status="+result.httpStatus+")" , 30).show();
				}
			}
			
		}
		

		
		@Override
		protected SamuraiNotificationServerResult doInBackground(String... args) {
			String registration_id = args[0];
			SamuraiNotificationServerResult result = new SamuraiNotificationServerResult();
			ConfigData data = ConfigData.loadConfigData(dependContext);
			
			try {

				String lang = "ja";
				String tags = "android_device_test";
				String serveruri = data.sn_server;
				String token = data.sn_token;

				HttpClient httpCli = new DefaultHttpClient();
				HttpPost postData = new HttpPost(serveruri);
				List<NameValuePair> postDataBody = new ArrayList<NameValuePair>();
				postDataBody.add(new BasicNameValuePair("lang", lang));
				postDataBody.add(new BasicNameValuePair("tags", tags));
				postDataBody.add(new BasicNameValuePair("token", token));
				postDataBody.add(new BasicNameValuePair("device_token",
						registration_id));

				postData.setEntity(new UrlEncodedFormEntity(postDataBody,
						"utf-8"));

				HttpResponse response = httpCli.execute(postData);
				int status = response.getStatusLine().getStatusCode();
				result.httpStatus = status;

				HttpEntity entity = response.getEntity();
				if(entity != null){
					String responseBodyText = EntityUtils.toString(entity);
					entity.consumeContent();
					httpCli.getConnectionManager().shutdown();
					result.responseString = responseBodyText;
					Log.e("samurai-notification", "response:"+responseBodyText);
				}
				


			} catch (Exception e) {
				result.causeException = e;
				e.printStackTrace();
				Log.e("samurai-notification", "exception:"+e.toString());
			}
			
			return result;
		}

	}

}
