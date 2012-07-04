/*
 * Copyright (C) 2012 CONIT Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.conit.sss.sn.ex1.service;

import static jp.co.conit.sss.sn.ex1.util.SNApiUtil.SENDER_ID;
import jp.co.conit.sss.sn.ex1.R;
import jp.co.conit.sss.sn.ex1.activity.SettingsActivity;
import jp.co.conit.sss.sn.ex1.util.PrefrerencesUtil;
import jp.co.conit.sss.sn.ex1.util.StringUtil;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * GCMからのレスポンスをハンドリングするサービスです。
 * 
 * @author conit
 */
public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onError(Context context, String errorId) {
        String registId = PrefrerencesUtil.getString(context, "registration_id", "");
        if (StringUtil.isEmpty(registId)) {
            Toast.makeText(context, R.string.push_regist_failed, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.push_unregist_failed, Toast.LENGTH_SHORT).show();
        }
        ResponseHandler.registedResponse();
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        String snTicker = intent.getExtras().getString("sn_ticker");
        String snTitle = intent.getExtras().getString("sn_title");
        String snText = intent.getExtras().getString("sn_text");
        String snUserdata = intent.getExtras().getString("sn_userdata");
        String snMessageId = intent.getExtras().getString("mid");

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = new Notification(R.drawable.ic_status, snTicker,
                System.currentTimeMillis());

        n.flags = Notification.FLAG_AUTO_CANCEL;
        Intent opneIntent = new Intent(context, SettingsActivity.class);
        opneIntent.putExtra("mid", snMessageId);
        if (!StringUtil.isEmpty(snUserdata)) {
            Integer size = Integer.valueOf(snUserdata);
            opneIntent.putExtra("text_size", size);
        }
        int time = (int) System.currentTimeMillis() * 1000;
        PendingIntent pendingIntent = PendingIntent.getActivity(context, time, opneIntent, 0);
        n.setLatestEventInfo(context, snTitle, snText, pendingIntent);
        nm.notify(time, n);
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.d(TAG, "DeletedMessages count:" + total);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Intent intentService = new Intent();
        intentService.setClass(context, RegistService.class);
        intentService.putExtra("registration_id", registrationId);
        context.startService(intentService);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Intent intentService = new Intent();
        intentService.setClass(context, UnRegistService.class);
        context.startService(intentService);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        Log.d(TAG, "errorId:" + errorId);
        return super.onRecoverableError(context, errorId);
    }
}
