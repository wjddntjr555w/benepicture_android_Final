package com.bene.pictures.fcm;

// should be opened

// should be opened

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.bene.pictures.MyApplication;
import com.bene.pictures.R;
import com.bene.pictures.data.MyInfo;
import com.bene.pictures.ui.main.MainActivity;
import com.bene.pictures.ui.main.friendlist.FriendListActivity;
import com.bene.pictures.ui.main.msgbox.MsgBoxActivity;
import com.bene.pictures.ui.main.winnner.WinnerCheckActivity;
import com.bene.pictures.ui.splash.SplashActivity;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = FirebaseMessagingService.class.getSimpleName();

    public static final int EVENT_BUS_MSG_CODE = 0x1000;

    // 서버에서 내려오는 푸시메시지 타입.
    public static final int MSG_TYPE_KEYWORD = 1;                 // 맞춤 키워드 알림
    public static final int MSG_TYPE_FRIEND = 2;                  // 친구 메시지 알림
    public static final int MSG_TYPE_WIN = 3;                     // 당첨 결과 알림
    public static final int MSG_TYPE_NOTICE = 4;                  // 공지 알림
    public static final int MSG_TYPE_APPLY = 5;                   // 응모 횟수 알림
    public static final int MSG_TYPE_ADMIN = 6;                   // 관리자 메시지 알림
    public static final int MSG_TYPE_PUZZLE = 7;                  // 잔여 퍼즐 알림

    // 서버와 일치하는 상수값들이다.
    public static final String MSG_ID = "id";
    public static final String MSG_TYPE = "type";
    public static final String MSG_TITLE = "title";
    public static final String MSG_CONTENT = "message";

    // should be opened
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String pushMsg = "";
        String pushType = "";
        String pushId = "";
        String pushTitle = "";

        try {
            JSONObject jsonObj = new JSONObject(remoteMessage.getData());
            pushMsg = jsonObj.getString(MSG_CONTENT);
            pushType = jsonObj.getString(MSG_TYPE);
            pushTitle = jsonObj.getString(MSG_TITLE);
            pushId = jsonObj.getString(MSG_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendNotification_8(pushMsg, pushTitle, pushType, pushId);
    }

    private void sendNotification(String title, String message) {
        Intent intent = null;
        if (!isApplicationRunningBackground(getApplicationContext())) {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendBigTextStyleNotification(String title, String message) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    // android 8.0 에 대응
    private void sendNotification_8(String msg, String title, String push_type, String push_id) {
        MyApplication m_app = (MyApplication) getApplicationContext();

        Intent intent = null;
        if (!isApplicationRunningBackground(getApplicationContext())) {
            switch (Integer.valueOf(push_type)) {
                case MSG_TYPE_KEYWORD:                 // 맞춤 키워드 알림
                case MSG_TYPE_PUZZLE:                  // 잔여 퍼즐 알림
                case MSG_TYPE_APPLY:                   // 응모 횟수 알림
                    intent = new Intent(this, MainActivity.class);
                    break;
                case MSG_TYPE_FRIEND:                  // 친구 메시지 알림
                    intent = new Intent(this, FriendListActivity.class);
                    break;
                case MSG_TYPE_WIN:                     // 당첨 결과 알림
                    intent = new Intent(this, WinnerCheckActivity.class);
                    break;
                case MSG_TYPE_NOTICE:                  // 공지 알림
                case MSG_TYPE_ADMIN:                   // 관리자 메시지 알림
                    intent = new Intent(this, MsgBoxActivity.class);
                    break;
                default:
                    intent = new Intent(this, MainActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else {
            intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            MyInfo.getInstance().savePush(getApplicationContext(), Integer.valueOf(push_type), Integer.valueOf(push_id));
        }

        // 알람갯수 업데이트
        // MyApplication.getInstance().getMainAct().updateAlarmCnt();

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Bitmap notificationLargeIconBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.mipmap.ic_launcher);

        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "BenePicture";//getString(R.string.channel_name);// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        String strTitle = title;
        if (strTitle.isEmpty())
            strTitle = "베네픽쳐 알림";
        NotificationManager w_notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification w_notification = null;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_HIGH);
            w_notificationManager.createNotificationChannel(mChannel);
            w_notification = new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(notificationLargeIconBitmap)
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle(strTitle)
                    .setContentText(msg)
                    .setChannelId(CHANNEL_ID)
                    .setSound(defaultSoundUri)
                    .build();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                w_notification = new Notification.Builder(getApplicationContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(notificationLargeIconBitmap)
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentTitle(strTitle)
                        .setContentText(msg)
                        .setSound(defaultSoundUri)
                        .build();
            }
        }

        w_notificationManager.notify(notifyID, w_notification);

    }

    static boolean isApplicationRunningBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(am.getRunningAppProcesses().size());
        boolean isBackground = true;
        for (ActivityManager.RunningTaskInfo info : tasks) {
            if (info.topActivity.getPackageName().equals(context.getPackageName())) {
                isBackground = false;
                break;
            }
        }
        return isBackground;
    }
}
