
Licensed under the Apache License, Version 2.0 


[概要]  
これは株式会社コニットが提供するSamurai Notificationを利用したサンプルアプリです。  
Push通知にはGoogle Cloud Messaging for Android (GCM)  を使用しています。

ライセンスはApache License2.0です。  
コードの改変、再配布は自由ですが、その上でSamurai Notificationの機能を利用するには一定の手順を踏む必要があります。  
詳しくは下記[コードの改変について]を御覧ください。  

[コードの改変について]  
以下の改変手順が必要となります。
1.Samurai Notificationを利用する前提のアプリとなります。Samurai Notificationに申し込みをしてください。( http://www.conit.jp/ )  
2.Samurai Notificationにアプリケーションを登録してください。登録時にアクセストークンとAPIサーバーホスト名が割り振られます。  
　アクセストークンはSamurai NotificationAPIを呼び出すのに使用します。  
3.GCMとSamurai Notificationを利用するための登録を行います。
   下記URLの手順を参照してください。
   http://sss-developer.conit.jp/samurai_notification/usage/gcm.html
   
   手順に則って、プロジェクトを作成するとプロジェクトのURLは以下の様になります。
   「 https://code.google.com/apis/console/#project:4815162342」  
   「project:」以降の数値をメモしておきます。（この場合は4815162342）
    この数値は後でGCM sender IDとして使用します。
   
4.Samurai Notificationに必要な情報をプログラム上に定義します。  
　GCM sender IDは、src/jp/co/conit/sss/sn/ex1/util/SNApiUtil.java 42行目のSENDER_IDに定義してください。  
　アクセストークンは、src/jp/co/conit/sss/sn/ex1/util/SNApiUtil.java 44行目のSN_TOKENに定義してください。  
　サーバーホスト名は、src/jp/co/conit/sss/sn/ex1/util/SNApiUtil.java 46行目のSN_DOMAINに記述してあるホスト名に置き換えて下さい。
  
ここまでがSamurai Notificationアプリの改修手順となります。  
以下は改変したアプリにプッシュ通知を行う手順となります。  

5.GCMサービスに登録します。
  アプリをインストールし、「プッシュ通知受信:OFF→ON」ボタンをタップしてください。
6.メッセージを作成し送信します。
  下記URLを参考にして送信してください。
  http://sss-developer.conit.jp/console_manual/regi_sn.html
  
※当アプリではメッセージを受け取るとNotificationを表示します。  
 また、Notificationをタップするとアプリが起動します。  
    
[使用上の注意事項]  
・アプリは十分にテストをしていますが、お使いの端末の環境や、プログラムの不具合などによって問題が生じる可能性があります。  
　これによって生じた、いかなる損害に対しても保証は出来かねますので、あらかじめご了承ください。  
  
[関連リンク]  
・Samuri Smartphone Services  http://www.conit.jp/  
・開発者ドキュメント  http://sss-developer.conit.jp/index.html#  
・株式会社コニット  http://www.conit.co.jp/  
・Blog  http://www.conit.co.jp/conitlabs/  
・Facebook  https://www.facebook.com/conit.fan  
・Twitter  https://twitter.com/#!/conit  
_  