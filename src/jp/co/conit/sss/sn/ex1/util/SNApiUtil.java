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

    public static final String SN_TOKEN = "1234567890abcdefghijklmnopqrstu1234567890";

    private static final String SN_DOMAIN = "https://abcdefg.conit.jp/v2/android/";

    private static final String SN_DEVICES = "devices/";

    private static final String SN_UNREGISTER = "unregister/";

    private SNApiUtil() {

    }

    /**
     * SamuraiNotificationのdevicesAPIを使用します。
     * 
     * @param snParam
     * @return
     */
    public static SNServerResult devices(SNParam snParam) {

        SNServerResult result = new SNServerResult();
        String url = SN_DOMAIN + SN_DEVICES;

        List<NameValuePair> postData = new ArrayList<NameValuePair>();
        if (!StringUtil.isEmpty(snParam.getLang())) {
            postData.add(new BasicNameValuePair("lang", snParam.getLang()));
        }
        if (!StringUtil.isEmpty(snParam.getTagsStr())) {
            postData.add(new BasicNameValuePair("tags", snParam.getTagsStr()));
        }
        if (!StringUtil.isEmpty(snParam.getMid())) {
            postData.add(new BasicNameValuePair("mid", snParam.getMid()));
        }

        postData.add(new BasicNameValuePair("token", snParam.getToken()));
        postData.add(new BasicNameValuePair("device_token", snParam.getDeviceToken()));

        return post(url, postData, result);
    }

    /**
     * SamuraiNotificationのunregisterAPIを使用します。
     * 
     * @param devicetoken
     * @return
     */
    public static SNServerResult unregister(String devicetoken) {

        SNServerResult result = new SNServerResult();
        String url = SN_DOMAIN + SN_UNREGISTER;

        List<NameValuePair> postData = new ArrayList<NameValuePair>();
        postData.add(new BasicNameValuePair("token", SN_TOKEN));
        postData.add(new BasicNameValuePair("device_token", devicetoken));

        return post(url, postData, result);
    }

    private static SNServerResult post(String url, List<NameValuePair> postData,
            SNServerResult result) {

        try {
            HttpClient httpCli = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(postData, "utf-8"));

            HttpResponse response = httpCli.execute(post);
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
