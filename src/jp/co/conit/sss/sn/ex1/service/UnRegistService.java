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
import jp.co.conit.sss.sn.ex1.util.PrefrerencesUtil;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * GCMで登録解除されたregistration_idを破棄するサービスです。
 * 
 * @author conit
 */
public class UnRegistService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(getApplicationContext(), R.string.push_unregist_succeeded,
                Toast.LENGTH_SHORT).show();
        PrefrerencesUtil.setString(getApplicationContext(), "regist_id", "");
        ResponseHandler.registedResponse();

        return START_NOT_STICKY;
    }

}
