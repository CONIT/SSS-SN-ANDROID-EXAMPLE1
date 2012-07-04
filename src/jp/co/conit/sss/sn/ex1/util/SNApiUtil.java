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

package jp.co.conit.sss.sn.ex1.util;

import java.util.ArrayList;
import java.util.List;

import jp.co.conit.sss.sn.ex1.entity.SNParam;
import jp.co.conit.sss.sn.ex1.entity.SNServerResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * SamuraiNotificationのAPIの実行するユーティリティクラスです。
 * 
 * @author conit
 */
public final class SNApiUtil {

    public static final String SENDER_ID = "123456789012";

    public static final String SN_TOKEN = "1234567890abdefghijklmnopqrstuv1234567890";

    public static final String SN_SERVER = "https://abcdefg.conit.jp/v2/android/devices/";

    private SNApiUtil() {

    }

    /**
     * SamuraiNotificationのdevicesAPIに通信します。
     * 
     * @param snParam
     * @return
     */
    public static SNServerResult devices(SNParam snParam) {

        SNServerResult result = new SNServerResult();
        try {

            HttpClient httpCli = new DefaultHttpClient();
            HttpPost postData = new HttpPost(SN_SERVER);
            List<NameValuePair> postDataBody = new ArrayList<NameValuePair>();
            if (!StringUtil.isEmpty(snParam.getLang())) {
                postDataBody.add(new BasicNameValuePair("lang", snParam.getLang()));
            }
            if (!StringUtil.isEmpty(snParam.getTagsStr())) {
                postDataBody.add(new BasicNameValuePair("tags", snParam.getTagsStr()));
            }
            if (!StringUtil.isEmpty(snParam.getMid())) {
                postDataBody.add(new BasicNameValuePair("mid", snParam.getMid()));
            }

            postDataBody.add(new BasicNameValuePair("token", snParam.getToken()));
            postDataBody.add(new BasicNameValuePair("device_token", snParam.getDeviceToken()));

            postData.setEntity(new UrlEncodedFormEntity(postDataBody, "utf-8"));

            HttpResponse response = httpCli.execute(postData);
            int status = response.getStatusLine().getStatusCode();
            result.mHttpStatus = status;

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBodyText = EntityUtils.toString(entity);
                entity.consumeContent();
                httpCli.getConnectionManager().shutdown();
                result.mResponseString = responseBodyText;
            }

        } catch (Exception e) {
            result.mCauseException = e;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * SamuraiNotificationのdevicesAPIで使用するパラメータを生成します。
     * 
     * @param type
     * @return
     */
    public static SNParam generateSNPraram() {
        SNParam snParam = new SNParam();
        snParam.setToken(SN_TOKEN);
        return snParam;
    }

}
