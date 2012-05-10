package jp.co.conit.testc2dm;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import jp.co.conit.SSS_SN_ANDROID_EXAMPLE1.R;

public class C2DMtestappActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView t = (TextView) findViewById(R.id.main_textview);
        t.setText("application start");

        TextView t_senderemail = (TextView) findViewById(R.id.c2dm_sender_email);
        TextView t_sn_token = (TextView) findViewById(R.id.sn_application_token);
		ConfigData data = ConfigData.loadConfigData(this);
		t_senderemail.setText(data.emailaddress.trim());
		t_sn_token.setText(data.sn_token.trim());
        
        
        Button btn = (Button) findViewById(R.id.send_deviceid_btn);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//ボタンクリックでデバイス識別IDのリクエスト実行。リターンはReceiver側に届く
				sendDeviceIDtoServer();
			}
		});
    }
    
    
    
    
    
    private void sendDeviceIDtoServer(){
        TextView t_senderemail = (TextView) findViewById(R.id.c2dm_sender_email);
        TextView t_sn_token = (TextView) findViewById(R.id.sn_application_token);
		
		ConfigData data = new ConfigData();
		data.emailaddress = t_senderemail.getText().toString();
		data.sn_token = t_sn_token.getText().toString();
		ConfigData.saveConfigData(this,data);


        TextView t = (TextView) findViewById(R.id.main_textview);
        t.setText("Request registration_id to google");
    	        
        Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
        registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
        registrationIntent.putExtra("sender", data.emailaddress.trim());
        startService(registrationIntent);
        
    }
}


