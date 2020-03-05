package com.bene.pictures.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.EditText;
import android.widget.TextView;

import com.bene.pictures.R;
import com.bene.pictures.data.Constant;
import com.bene.pictures.ui.base.BaseActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

public class Util {

    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getPackageName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    @SuppressLint("HardwareIds")
    public static String getPhoneNumber(Context context) {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "permission";
        }

        // 전화번호가 국가코드가 붙어 내려오는것과 관련하여 +82 을 없애주어야 한다.
        // 나이스 인증사에서는 국가코드가 없이 내려온다. 국가코드는 한국의 경우 앞에서 세자리이므로 3번째 index부터 얻으면 된다.

        try {
            return mTelephonyMgr != null ? mTelephonyMgr.getLine1Number().replace("+82", "0") : "";
        } catch (Exception e) {
            Toaster.showShort(context, "전화번호가 없는 기기에서는 이용하실수 없습니다.");
            return "";
        }
    }

    public static int px2dp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((px / displayMetrics.density) + 0.5);
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    public static String dateFormat(String date, String inputFormat, String outputFormat) {
        if (date == null || date.isEmpty()) {
            return "";
        }
        if (inputFormat.isEmpty()) {
            inputFormat = "yyyy-MM-dd HH:mm:ss";
        }
        if (outputFormat.isEmpty()) {
            outputFormat = "yyyy.MM.dd";
        }

        SimpleDateFormat fmt = new SimpleDateFormat(inputFormat, Locale.getDefault());
        String outputDate = "";

        try {
            Date srcDate = fmt.parse(date);

            fmt = new SimpleDateFormat(outputFormat, Locale.getDefault());
            outputDate = fmt.format(srcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }

    public static boolean checkPermissions(Context context, String[] strArr) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String checkSelfPermission : strArr) {
                if (ContextCompat.checkSelfPermission(context, checkSelfPermission) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean convertPermissionResult(int[] iArr) {
        for (int i : iArr) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    public static Map<String, Typeface> typefaceCache = new HashMap<String, Typeface>();

    public static void setTypeface(AttributeSet attrs, TextView textView) {
        if (textView.isInEditMode()) return;

        Context context = textView.getContext();

        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.BaseTextView);
        String typefaceName = values.getString(R.styleable.BaseTextView_typeface);

        if (typefaceCache.containsKey(typefaceName)) {
            textView.setTypeface(typefaceCache.get(typefaceName));
        } else {
            Typeface typeface;
            try {

                if (typefaceName == null || typefaceName.isEmpty()) {
                    typefaceName = Constant.FONT_NANUM;
                }

                if (textView.getTypeface() != null) {
                    int style = textView.getTypeface().getStyle();
                    if (style == Typeface.BOLD) {
                        typefaceName = Constant.FONT_NANUMBOLD;
                    }
                }

                typeface = Typeface.createFromAsset(textView.getContext().getAssets(), typefaceName);
            } catch (Exception e) {
                return;
            }

            typefaceCache.put(typefaceName, typeface);
            textView.setTypeface(typeface);
        }
    }

    /**
     *
     */
    public static void setFilter(Context context, EditText editText, int filterType) {
        StringFilter stringFilter = new StringFilter(context);
        InputFilter[] curFilters = editText.getFilters();
        InputFilter[] newFilters = new InputFilter[curFilters.length + 1];
        for (int i = 0; i < curFilters.length; i++)
            newFilters[i] = curFilters[i];

        switch (filterType) {
            case StringFilter.ALLOW_HANGUL:
                newFilters[curFilters.length] = stringFilter.allowHangul;
                break;
            case StringFilter.ALLOW_ALPHA:
                newFilters[curFilters.length] = stringFilter.allowAlpha;
                break;
            case StringFilter.ALLOW_NUMERIC:
                newFilters[curFilters.length] = stringFilter.allowNumeric;
                break;
            case StringFilter.ALLOW_HANGUL_ALPHA:
                newFilters[curFilters.length] = stringFilter.allowHangulAlpha;
                break;
            case StringFilter.ALLOW_HANGUL_NUMERIC:
                newFilters[curFilters.length] = stringFilter.allowHangulNumeric;
                break;
            case StringFilter.ALLOW_ALPHA_NUMERIC:
                newFilters[curFilters.length] = stringFilter.allowAlphaNumeric;
                break;
            case StringFilter.ALLOW_HANGUL_ALPHA_NUMERIC:
                newFilters[curFilters.length] = stringFilter.allowHangulAlphaNumeric;
                break;
            case StringFilter.ALLOW_LOWER_ALPHA_NUMERIC:
                newFilters[curFilters.length] = stringFilter.allowLowerAlphaNumeric;
                break;
            case StringFilter.ALLOW_UPPER_ALPHA_ONE_NUMERIC:
                newFilters[curFilters.length] = stringFilter.allowUpperAlphaOneNumeric;
                break;
            case StringFilter.ALLOW_NUMERIC_LINE:
                newFilters[curFilters.length] = stringFilter.allowNumericLine;
                break;
        }

        editText.setFilters(newFilters);
    }

    public static File createFile() {
        File folder = new File(getFolderPath());
        if (!folder.exists())
            folder.mkdirs();

        Long tsLong = System.currentTimeMillis() / 1000;
        String ext = ".png";
        String filename = tsLong.toString() + ext;
        return new File(folder.toString(), filename);
    }

    public static File createFile(String ext) {
        File folder = new File(getFolderPath());
        if (!folder.exists())
            folder.mkdirs();

        Long tsLong = System.currentTimeMillis() / 1000;
        String filename = tsLong.toString() + "." + ext;
        return new File(folder.toString(), filename);
    }

    private static String getFolderPath() {
        return Environment.getExternalStorageDirectory() + "/" + Constant.SDCARD_FOLDER;
    }

    public static void saveBitmapToFile(BaseActivity activity, Bitmap bitmap, String filepath) {
        if (bitmap == null || filepath.isEmpty()) {
            return;
        }

        if (activity.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            OutputStream fOut = null;
            File file = new File(filepath);
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            activity.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x11);
        }
    }

    public static void saveBitmapToJPGFile(BaseActivity activity, Bitmap bitmap, String filepath) {
        if (bitmap == null || filepath.isEmpty()) {
            return;
        }

        if (activity.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            OutputStream fOut = null;
            File file = new File(filepath);
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            activity.requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x11);
        }
    }

    public static void deleteDir(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            File[] childFileList = dir.listFiles();

            if (childFileList == null) {
                return;
            }

            for (File childFile : childFileList) {
                if (childFile.isDirectory()) {
                    deleteDir(childFile.getAbsolutePath()); // 하위 디렉토리 루프
                } else {
                    childFile.delete(); // 하위 파일삭제
                }
            }
//            dir.delete(); // root 삭제
        }
    }

    public static boolean isEmail(String string) {
        if (TextUtils.isEmpty(string))
            return false;
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(string);
        return m.matches();
    }

    public static String getCountryCode(String res) {
        if (res.isEmpty()) {
            return "";
        }

        return res.replaceAll("[^-+\\d]", "").trim();
    }

    public static boolean isInstalledPackage(Context context, String pkgName) {
        if (pkgName != null) {
            try {
                PackageManager pm = context.getPackageManager();
                pm.getInstallerPackageName(pkgName);
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    public static String stripHtml(String html) {
//        return Html.fromHtml(html).toString();
        return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replace("&nbsp;", "").replace("&#39;", "").replace("&quot;", "")
                .replace("&rsquo;", "").replace("&lsquo;", "").replace("&ldquo;", "").replace("&rdquo;", "").replace("&amp;", "")
                .replace("&lsquo;", "").replace("&rsquo;", "");
    }


    /**
     * Check Wifi enable
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    /**
     * Wifi Enable/Disable
     *
     * @param context
     * @param enabled
     */
    public static void setWifiEnabled(Context context, boolean enabled) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(enabled);
    }

    /**
     * Check 3G/4G enable
     *
     * @param context
     * @return
     */
    public static boolean isMobileDataEnabled(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileInfo.getState() == NetworkInfo.State.CONNECTED;
    }

    /**
     * 3G/4G Enable / Disable
     *
     * @param context
     * @param enabled
     */
    public static boolean setMobileDataEnabled(Context context, boolean enabled) {
        final ConnectivityManager conman =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        try {
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            final Object iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(
                    iConnectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = iConnectivityManagerClass
                    .getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);

            setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 금액(double)을 금액표시타입(소숫점2자리)으로 변환한다.
     *
     * @param moneyString 금액(double 형)
     * @return 변경된 금액 문자렬
     */
    public static String makeMoneyType(String moneyString) {
        String format = "#,###.##"/* "#.##0.00" */;
        DecimalFormat df = new DecimalFormat(format);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();

        dfs.setGroupingSeparator(',');
        df.setGroupingSize(3);
        df.setDecimalFormatSymbols(dfs);

        try {
            return (df.format(Float.parseFloat(moneyString))).toString();
        } catch (Exception e) {
            return moneyString;
        }
    }

    public static String makeMoneyType(int val) {
        return makeMoneyType(String.valueOf(val));
    }

    public static String makeMoneyType(float val) {
        return makeMoneyType(String.valueOf(val));
    }

    public static Bitmap LoadRoundedImageFromWebUrl(Context context,
                                                    String strUrl, int sample) {

        try {

            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = sample;
            option.inPurgeable = true;
            option.inDither = true;

            URL url = new URL(strUrl);
            HttpGet httpRequest = null;

            httpRequest = new HttpGet(url.toURI());

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = (HttpResponse) httpclient
                    .execute(httpRequest);

            HttpEntity entity = response.getEntity();
            BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
            InputStream input = b_entity.getContent();

            Bitmap bitmap = BitmapFactory.decodeStream(input, null, option);

            if (input != null)
                input.close();

            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertHiddenString(String src){
        String dst = src;
        switch (src.length()){
            case 2:
                dst = src.substring(0,1) + "*";
                break;
            case 3:
                dst = src.substring(0,1) + "*" + src.substring(2);
                break;
            case 4:
                dst = src.substring(0,1) + "**" + src.substring(3) ;
                break;
            case 5:
                dst = src.substring(0,2) + "**" + src.substring(4) ;
                break;
            case 6:
                dst = src.substring(0,2) + "**" + src.substring(4) ;
                break;
            default:
                dst = src.substring(0,3) + "**" + src.substring(src.length()-2);
                break;
        }

        return dst;
    }
}
