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

package jp.co.conit.sss.sn.ex1.entity;

import java.util.Arrays;

/**
 * Samurai NotificationのデバイストークンAPIのパラメータをまとめたモデルクラスです。
 * 
 * @author conit
 */
public class SNParam {

    private String mToken;

    private String mDeviceToken;

    private String mSever;

    private String mLang;

    private String[] mTags;

    private String mMid;

    public SNParam() {
    }

    public String getLang() {
        return mLang;
    }

    public void setLang(String lang) {
        mLang = lang;
    }

    public String[] getTags() {
        return mTags;
    }

    public String getTagsStr() {

        if (mTags == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mTags.length; i++) {
            sb.append(mTags[i]);
            if (i < mTags.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public void setTags(String[] tags) {
        mTags = tags;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getSever() {
        return mSever;
    }

    public void setSever(String sever) {
        mSever = sever;
    }

    public String getDeviceToken() {
        return mDeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        mDeviceToken = deviceToken;
    }

    public String getMid() {
        return mMid;
    }

    public void setMid(String mid) {
        mMid = mid;
    }

    @Override
    public String toString() {
        return "SNParam [mToken=" + mToken + ", mDeviceToken=" + mDeviceToken + ", mSever="
                + mSever + ", mLang=" + mLang + ", mTags=" + Arrays.toString(mTags) + ", mMid="
                + mMid + "]";
    }

}
