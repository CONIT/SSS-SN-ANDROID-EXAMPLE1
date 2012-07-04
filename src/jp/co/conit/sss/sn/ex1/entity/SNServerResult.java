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

/**
 * SamuraiNotificationServerへの通信結果を格納するモデルクラスです。
 * 
 * @author conit
 */
public class SNServerResult {

    public int mHttpStatus;

    public String mResponseString;

    public Exception mCauseException;

    public SNServerResult() {
        mHttpStatus = -1;
        mResponseString = null;
        mCauseException = null;
    }

}
