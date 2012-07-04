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

/**
 * Receiver、ServiceからのレスポンスをActivityへ受け渡す機能を提供するクラスです。<br>
 * <br>
 * レスポンスを受け取りたいActivityはAbstRegistObserverを実装し、<br>
 * registerメソッドでObserverを設定する必要があります 。
 * 
 * @author conit
 */
public class ResponseHandler {

    private static AbstRegistObserver sRegistObserver;

    public static synchronized void register(AbstRegistObserver observer) {
        sRegistObserver = observer;
    }

    public static synchronized void unregister(AbstRegistObserver observer) {
        sRegistObserver = null;
    }

    public static void registedResponse() {
        if (sRegistObserver != null) {
            sRegistObserver.onRegisted();
        }
    }

}
