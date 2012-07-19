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
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * アプリ画面を再描画するサービスです。
 * 
 * @author conit
 */
public class RedrawService extends Service {

    public static final int REGIST_FAILD_GCM = 1;

    public static final int UNREGIST_FAILD = 2;

    public static final int UNREGIST_SUCCCEEDED = 3;
    
    public static final int MID_SEND_SERVER_SUCCCEEDED = 4;
    
    public static final int MID_SEND_SERVER_FAILD = 5;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ResponseHandler.registedResponse();

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int result = bundle.getInt("result_type");
            toastShow(result);
        }

        return START_NOT_STICKY;
    }

    /**
     * 処理結果にあわせたToastを表示します。
     * 
     * @param result
     */
    private void toastShow(int result) {

        int stringId = 0;

        switch (result) {
            case REGIST_FAILD_GCM:
                stringId = R.string.push_regist_failed_gcm;
                break;
            case UNREGIST_FAILD:
                stringId = R.string.push_unregist_failed;
                break;
            case UNREGIST_SUCCCEEDED:
                stringId = R.string.push_unregist_succeeded;
                break;
            case MID_SEND_SERVER_SUCCCEEDED:
                stringId = R.string.mid_send_server_succeeded;
                break;
            case MID_SEND_SERVER_FAILD:
                stringId = R.string.mid_sendserver_failed;
                break;

            default:
                return;
        }

        Toast.makeText(getApplicationContext(), stringId, Toast.LENGTH_SHORT).show();
    }
}
