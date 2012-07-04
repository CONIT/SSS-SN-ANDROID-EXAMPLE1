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
 * registration_idの有効、無効化した際に通知するためのObserverクラスです。
 * 
 * @author conit
 */
public abstract class AbstRegistObserver {

    public AbstRegistObserver() {
    }

    /**
     * registration_idの有効、無効化した際に呼び出されます。
     */
    abstract public void onRegisted();

}
