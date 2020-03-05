package com.bene.pictures.fcm;

// should be opened
import android.util.Log;

import com.bene.pictures.MyApplication;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = FirebaseInstanceIDService.class.getSimpleName();

    // [START refresh_token]

    // should be opened
    @Override
    public void onTokenRefresh()
    {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + token);

        // [END get_token]
        Log.i(TAG, "FCM Registration Token: " + token);

        // token register
        ((MyApplication)getApplication()).setPushToken(token);

    }
}