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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 文字列を扱うユーティリティクラスです。
 * 
 * @author conit
 */
public class StringUtil {

    private static final String[] LANGUAGE_KEY = {
            "ja_JP", "en_US", "fr_FR", "de_DE", "nl_NL", "it_IT", "es_ES", "pt_PT", "da_DK",
            "fi_FI", "nb_NO", "sv_SE", "ko_KR", "zh_CN", "zh_TW", "ru_RU", "pl_PL", "tr_TR",
            "uk_UA", "ar_EG", "hr_HR", "cs_CZ", "el_GR", "iw_IL", "ro_RO", "sk_SK", "th_TH",
            "in_ID", "ms_MY", "en_GB", "ca_ES", "hu_HU", "vi_VN"

    };

    private static final String[] LANGUAGE_VALUE = {
            "日本語", "英語（アメリカ）", "フランス語", "ドイツ語", "オランダ語", "イタリア語", "スペイン語", "ポルトガル語", "デンマーク語",
            "フィンランド語", "ノルウェー語", "スウェーデン語", "韓国語", "中国語（簡体字）", "中国語（繁体字）", "ロシア語", "ポーランド語",
            "トルコ語", "ウクライナ語", "アラビア語（エジプト）", "クロアチア語", "チェコ語", "ギリシャ語", "ヘブライ語", "ルーマニア語",
            "スロバキア語", "タイ語", "インドネシア語", "マレー語", "英語（イギリス）", "カタルーニャ語", "ハンガリー語", "ベトナム語"
    };

    private static Map<String, String> sMap = new HashMap<String, String>();

    static {
        for (int i = 0; i < LANGUAGE_KEY.length; i++) {
            sMap.put(LANGUAGE_KEY[i], LANGUAGE_VALUE[i]);
        }
    }

    private StringUtil() {
    }

    /**
     * 文字列が空かどうか判定します。
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * デフォルトロケールを取得します。<br>
     * 
     * @return 言語コード ＋ _ ＋ 国コード
     */
    public static String getLanguage() {
        Locale locale = Locale.getDefault();
        return localeToLanguage(locale.toString());
    }

    /**
     * ロケールにあった言語文字列に変換します。
     * 
     * @param locale
     * @return
     */
    private static String localeToLanguage(String locale) {
        return sMap.containsKey(locale) ? sMap.get(locale) : "不明";
    }

    /**
     * 言語コード ＋ _ ＋ 国コードを取得します。
     * 
     * @return
     */
    public static String getLocale() {
        Locale locale = Locale.getDefault();
        return locale.toString();
    }

}
