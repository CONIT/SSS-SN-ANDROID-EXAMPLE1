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

import jp.co.conit.sss.sn.ex1.R;
import jp.co.conit.sss.sn.ex1.entity.SNParam;
import jp.co.conit.sss.sn.ex1.entity.SNServerResult;
import jp.co.conit.sss.sn.ex1.util.PrefrerencesUtil;
import jp.co.conit.sss.sn.ex1.util.SNApiUtil;
import jp.co.conit.sss.sn.ex1.util.StringUtil;

import org.apache.http.HttpStatus;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * SamuraiNotificationServerにGCMから返却されたregistration_idの登録を行うサービスです。
 * 
 * @author conit
 */
public class RegistService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // registration_idが発行されている場合、SamuraiNotificationServerにregistration_idを登録します
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String registrationId = extras.getString("registration_id");
            if (!StringUtil.isEmpty(registrationId)) {
                SendSamuraiNotificationServerTask task = new SendSamuraiNotificationServerTask(
                        getApplicationContext());
                task.execute(registrationId);
            } else {
                ResponseHandler.registedResponse();
            }
        }
        return START_NOT_STICKY;
    }

    /**
     * SamuraiNotificationサーバーにGCMより発行されたregistration_idを登録するタスクです。
     * 
     * @author conit
     */
    public class SendSamuraiNotificationServerTask extends
            AsyncTask<String, Integer, SNServerResult> {

        private Context mContext;

        private String mRegistrationId;

        public SendSamuraiNotificationServerTask(Context context) {
            mContext = context;
        }

        @Override
        protected SNServerResult doInBackground(String... args) {
            mRegistrationId = args[0];

            SNParam snParam = SNApiUtil.generateSNPraram();
            snParam.setDeviceToken(mRegistrationId);
            snParam.setLang(StringUtil.getLocale());
            String gender = PrefrerencesUtil.getString(mContext, "gender", "male");
            String[] tags = {
                gender
            };
            snParam.setTags(tags);

            return SNApiUtil.devices(snParam);
        }

        @Override
        protected void onPostExecute(SNServerResult result) {

            if (result.mCauseException != null) {
                Toast.makeText(mContext, mContext.getString(R.string.push_regist_failed),
                        Toast.LENGTH_LONG).show();
            } else {
                if (result.mHttpStatus == HttpStatus.SC_OK) {
                    Toast.makeText(mContext, mContext.getString(R.string.push_regist_succeeded),
                            Toast.LENGTH_LONG).show();
                    saveRegistId(mRegistrationId);
                } else {
                    Toast.makeText(mContext, mContext.getString(R.string.push_regist_failed),
                            Toast.LENGTH_LONG).show();
                }
            }
            ResponseHandler.registedResponse();
            stopSelf();
        }
    }

    /**
     * 登録状態をSharedPreferenceに保存します。
     * 
     * @param registId
     */
    private void saveRegistId(String registId) {
        PrefrerencesUtil.setString(getApplicationContext(), "regist_id", registId);
    }

}
