package com.bene.pictures.ui.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bene.pictures.R;
import com.bene.pictures.data.Constant;
import com.bene.pictures.model.MError;
import com.bene.pictures.ui.widget.ProgressWheel;
import com.bene.pictures.util.PrefMgr;
import com.bene.pictures.util.Toaster;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity implements Constant {

    /*  +---------------------------------------------------------+
    | UI controls & Data members
    +---------------------------------------------------------+   */

    private boolean mFinishAppWhenPressedBackKey = true;
    private boolean mFinish = false;

    ProgressDialog mDlgProgress = null;

    @Nullable
    @BindView(R.id.containerView)
    ViewGroup containerView;

    public PrefMgr prefMgr;

    public static final String[] PERMISSION_ALL_MEDIA = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] PERMISSION_TAKE_PICTURE = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] PERMISSION_TAKE_VIDEO = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] PERMISSION_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String[] PERMISSION_CONTACT = {Manifest.permission.GET_ACCOUNTS, Manifest.permission.READ_CONTACTS};
    public static final String[] PERMISSION_ACCESS_LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    /*  +---------------------------------------------------------+
    | Overrides
    +---------------------------------------------------------+   */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        SharedPreferences prefs = getSharedPreferences(PrefMgr.BENEPICTURE_PREFS,
                Context.MODE_PRIVATE);
        prefMgr = new PrefMgr(prefs);

        initUI();

        if (containerView != null) {
            setupUI(containerView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        overridePendingTransition(0, 0);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (isGrantedResult(grantResults))
            onPermissionGranted(requestCode);
        else
            onPermissionDenied(requestCode);
    }

    @Override
    public void onBackPressed() {
        if (mFinishAppWhenPressedBackKey) {
            if (!mFinish) {
                mFinish = true;
                Toaster.showShort(this, R.string.app_finish_message);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFinish = false;
                    }
                }, 2000);
            } else {
                finish();
            }

            return;
        }

        super.onBackPressed();
    }

    /*  +---------------------------------------------------------+
    | Event handlers
    +---------------------------------------------------------+   */


    /*  +---------------------------------------------------------+
    | Helpers
    +---------------------------------------------------------+   */

    public void setFinishAppWhenPressedBackKey() {
        mFinishAppWhenPressedBackKey = true;
    }

    private void transitionIn() {
        overridePendingTransition(R.anim.fade, R.anim.hold);
    }

    private void transitionOut() {
        overridePendingTransition(R.anim.hold, R.anim.fadeout);
    }

    public boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(String[] permissions, int code) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> deniedPermissions = getDeniedPermissions(this, permissions);
            if (deniedPermissions.isEmpty()) {
                onPermissionGranted(code);
            } else {
                permissions = new String[deniedPermissions.size()];
                deniedPermissions.toArray(permissions);
                ActivityCompat.requestPermissions(this, permissions, code);
            }
        } else {
            onPermissionGranted(code);
        }
    }

    private static List<String> getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedList = new ArrayList<>(2);
        for (String permission : permissions) {
            if (PermissionChecker.checkSelfPermission(context, permission) != PermissionChecker.PERMISSION_GRANTED) {
                deniedList.add(permission);
            }
        }
        return deniedList;
    }

    private static boolean isGrantedResult(int... grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) return false;
        }
        return true;
    }

    protected void onPermissionGranted(int code) {
    }

    protected void onPermissionDenied(int code) {
        if (code == PERMISSIONS_MEDIA_REQUEST) {
            // 권한허용을 하지 않았으므로 알림팝업을 띄우고 앱을 종료한다.
            new AlertDialog.Builder(this)
                    .setMessage(R.string.permission_msg)
                    .setPositiveButton(R.string.permission_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 앱세팅페이지로 이행
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }

    protected void initUI() {
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(BaseActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            try {
                inputMethodManager.hideSoftInputFromWindow(
                        activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {

            }
        }
    }

    public void showProgress(Context ctx) {
        if (mDlgProgress != null && mDlgProgress.isShowing())
            return;

        mDlgProgress = new ProgressDialog(ctx);

        mDlgProgress.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDlgProgress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDlgProgress.getWindow().setDimAmount(0);

        mDlgProgress.setCancelable(false);
        mDlgProgress.setCanceledOnTouchOutside(false);
        mDlgProgress.setIndeterminate(false);

        mDlgProgress.show();

        mDlgProgress.setContentView(R.layout.popup_progress);
        ProgressWheel progressWheel = mDlgProgress.findViewById(R.id.progressWheel);
        progressWheel.spin();
    }

    public void hideProgress() {
        if (mDlgProgress != null && mDlgProgress.isShowing()) {
            mDlgProgress.dismiss();
            mDlgProgress = null;
        }
    }

    public void showErrorMsg(MError response) {
        if (response.res_msg != null && !response.res_msg.isEmpty()) {
            Toaster.showShort(this, response.res_msg);
            return;
        }

        switch (response.res_code) {
            case 1:
                Toaster.showShort(this, String.format("%s %s", getString(R.string.error_parameter), response.res_msg));
                break;
            case 2:
                Toaster.showShort(this, R.string.error_db);
                break;
            case 3:
                Toaster.showShort(this, R.string.error_no_info);
                break;
            case 4:
                Toaster.showShort(this, R.string.error_incorrect_info);
                break;
            case 5:
                Toaster.showShort(this, R.string.error_duplicate);
                break;
            case 6:
                Toaster.showShort(this, R.string.error_privilege);
                break;
            case 7:
                Toaster.showShort(this, R.string.error_file_upload);
                break;
            default:
                Toaster.showShort(this, R.string.error_unknown);
        }
    }
}
