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

import static jp.co.conit.sss.sn.ex1.service.RedrawService.MID_SEND_SERVER_FAILD;
import static jp.co.conit.sss.sn.ex1.service.RedrawService.MID_SEND_SERVER_SUCCCEEDED;
import jp.co.conit.sss.sn.ex1.entity.SNParam;
import jp.co.conit.sss.sn.ex1.entity.SNServerResult;
import jp.co.conit.sss.sn.ex1.util.IntentUtil;
import jp.co.conit.sss.sn.ex1.util.SNApiUtil;
import jp.co.conit.sss.sn.ex1.util.StringUtil;

import org.apache.http.HttpStatus;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * メッセージIDをSamuraiNotificationServerへ送信するServiceです。<br>
 * 
 * @author conit
 */
public class SendMessageIdIntentService extends IntentService {

    public SendMessageIdIntentService() {
        super("SendMessageIdIntentService");
    }

    public SendMessageIdIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        if (extras != null) {
            String mid = extras.getString("mid");
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext());
            String registrationId = preferences.getString("regist_id", "");

            if (!StringUtil.isEmpty(registrationId)) {
                SNParam param = SNApiUtil.generateSNPraram();
                param.setDeviceToken(registrationId);
                param.setMid(mid);
                sendMidSamuraiNotificationServer(param);
            }
        }
    }

    /**
     * MessageIdをSamuraiNotificationServerへ送信します。<br>
     * 
     * @param param
     */
    private void sendMidSamuraiNotificationServer(SNParam param) {

        SNServerResult result = SNApiUtil.devices(param);

        if (result.mCauseException != null) {
            IntentUtil.startRedrawService(getApplicationContext(), MID_SEND_SERVER_FAILD);
        } else {
            if (result.mHttpStatus == HttpStatus.SC_OK) {
                IntentUtil.startRedrawService(getApplicationContext(), MID_SEND_SERVER_SUCCCEEDED);
            } else {
                IntentUtil.startRedrawService(getApplicationContext(), MID_SEND_SERVER_FAILD);
            }
        }
    }

}
