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

import android.content.Context;

import com.google.android.gcm.GCMBroadcastReceiver;

/**
 * GCMのブロードキャストレシーバーです。<br>
 * 当プロジェクトではGCMIntentServiceをパケージ名直下に格納しないため、<br>
 * getGCMIntentServiceClassNameをオーバーライドしService名を返却するように実装しています。
 * 
 * @author conit
 */
public class SNGCMBroadcastReceiver extends GCMBroadcastReceiver {

    @Override
    protected String getGCMIntentServiceClassName(Context context) {
        return GCMIntentService.class.getName();
    }

}
