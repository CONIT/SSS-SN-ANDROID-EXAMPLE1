
package jp.co.conit.sss.sn.ex1.util;

import jp.co.conit.sss.sn.ex1.service.RedrawService;
import android.content.Context;
import android.content.Intent;

public class IntentUtil {

    private IntentUtil() {
    }

    /**
     * アプリ画面を再描画するサービスを起動します。
     * 
     * @param context
     */
    public static void startRedrawService(Context context, int result) {
        Intent intentService = new Intent();
        intentService.setClass(context, RedrawService.class);
        intentService.putExtra("result_type", result);
        context.startService(intentService);
    }

}
