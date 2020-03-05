package com.bene.pictures.util;

import android.content.Context;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

/**
 * @author hyogij@gmail.com
 * @description : Inputfilter class to constrain the EditText changes
 */
public class StringFilter {
    public static final int ALLOW_HANGUL = 0;
    public static final int ALLOW_ALPHA = 1;
    public static final int ALLOW_NUMERIC = 2;
    public static final int ALLOW_HANGUL_ALPHA = 3;
    public static final int ALLOW_HANGUL_NUMERIC = 4;
    public static final int ALLOW_ALPHA_NUMERIC = 5;
    public static final int ALLOW_HANGUL_ALPHA_NUMERIC = 6;

    public static final int ALLOW_LOWER_ALPHA_NUMERIC = 7;
    public static final int ALLOW_UPPER_ALPHA_ONE_NUMERIC = 8;

    public static final int ALLOW_NUMERIC_LINE = 9;

    public static final int TOAST_LELNGTH = 400;
    private static final String CLASS_NAME = StringFilter.class
            .getCanonicalName();
    private Context context = null;
    // Allows only hangul. Filters special
    // characters.
    public InputFilter allowHangul = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_HANGUL);
        }
    };
    // Allows only alpha. Filters special
    // characters.
    public InputFilter allowAlpha = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_ALPHA);
        }
    };
    // Allows only numeric. Filters special
    // characters.
    public InputFilter allowNumeric = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_NUMERIC);
        }
    };
    // Allows only alpha and hangul characters. Filters special
    // characters.
    public InputFilter allowHangulAlpha = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_HANGUL_ALPHA);
        }
    };
    // Allows only hangulnumeric. Filters special
    // characters.
    public InputFilter allowHangulNumeric = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_HANGUL_NUMERIC);
        }
    };
    // Allows only alphanumeric characters. Filters special and hangul
    // characters.
    public InputFilter allowAlphaNumeric = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_ALPHA_NUMERIC);
        }
    };
    // Allows only alphanumeric and hangul characters. Filters special
    // characters.
    public InputFilter allowHangulAlphaNumeric = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_HANGUL_ALPHA_NUMERIC);
        }
    };
    // Allows only alphanumeric and hangul characters. Filters special
    // characters.
    public InputFilter allowLowerAlphaNumeric = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_LOWER_ALPHA_NUMERIC);
        }
    };
    // Allows only alphanumeric and hangul characters. Filters special
    // characters.
    public InputFilter allowUpperAlphaOneNumeric = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_UPPER_ALPHA_ONE_NUMERIC);
        }
    };
    // Allows numeric and line. Filters special
    // characters.
    public InputFilter allowNumericLine = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            return filteredString(source, start, end, ALLOW_NUMERIC_LINE);
        }
    };

    public StringFilter(Context context) {
        this.context = context;
    }

    // Returns the string result which is filtered by the given mode
    private CharSequence filteredString(CharSequence source, int start,
                                        int end, int mode) {
//        Pattern pattern = null;
//
//        switch (mode) {
//            case ALLOW_HANGUL:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_hangul));
//                break;
//
//            case ALLOW_ALPHA:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_alpha));
//                break;
//
//            case ALLOW_NUMERIC:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_numeric));
//                break;
//
//            case ALLOW_HANGUL_ALPHA:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_hangul_alpha));
//                break;
//
//            case ALLOW_HANGUL_NUMERIC:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_hangul_numeric));
//                break;
//
//            case ALLOW_ALPHA_NUMERIC:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_alpha_numeric));
//                break;
//
//            case ALLOW_HANGUL_ALPHA_NUMERIC:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_hangul_alpha_numeric));
//                break;
//
//            case ALLOW_LOWER_ALPHA_NUMERIC:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_lower_alpha_numeric));
//                break;
//
//            case ALLOW_UPPER_ALPHA_ONE_NUMERIC:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_upper_alpha_one_numeric));
//                break;
//
//            case ALLOW_NUMERIC_LINE:
//                pattern = Pattern.compile(context
//                        .getString(R.string.pattern_numeric_line));
//                break;
//        }
//
//        boolean keepOriginal = true;
//        StringBuilder stringBuilder = new StringBuilder(end - start);
//        for (int i = start; i < end; i++) {
//            char c = source.charAt(i);
//            if (pattern.matcher(Character.toString(c)).matches()) {
//                stringBuilder.append(c);
//            } else {
//                if (mode == ALLOW_ALPHA_NUMERIC) {
//                    showToast(context.getString(R.string.input_error_alphanum));
//                } else if (mode == ALLOW_HANGUL_ALPHA) {
//                    showToast(context.getString(R.string.input_error_alpha_hangul));
//                } else if (mode == ALLOW_HANGUL_NUMERIC) {
//                    showToast(context.getString(R.string.input_error_hangulnum));
//                } else if (mode == ALLOW_NUMERIC) {
//                    showToast(context.getString(R.string.input_error_numeric));
//                } else if (mode == ALLOW_HANGUL) {
//                    showToast(context.getString(R.string.input_error_hangul));
//                } else if (mode == ALLOW_LOWER_ALPHA_NUMERIC) {
//                    showToast(context.getString(R.string.input_error_lower_alpha_numeric));
//                } else if (mode == ALLOW_UPPER_ALPHA_ONE_NUMERIC) {
//                    showToast(context.getString(R.string.input_error_upper_alpha_one_numeric));
//                } else if (mode == ALLOW_NUMERIC_LINE) {
//                    showToast(context.getString(R.string.input_error_numeric_line));
//                } else {
//                    showToast(context
//                            .getString(R.string.input_error_alphanumeric_hangul));
//                }
//
//                keepOriginal = false;
//            }
//        }
//
//        if (keepOriginal) {
//            return null;
//        } else {
//            if (source instanceof Spanned) {
//                SpannableString spannableString = new SpannableString(
//                        stringBuilder);
//                TextUtils.copySpansFrom((Spanned) source, start,
//                        stringBuilder.length(), null, spannableString, 0);
//                return spannableString;
//            } else {
//                return stringBuilder;
//            }
//        }

        return "";
    }

    // Shows toast with specify delay that is shorter than Toast.LENGTH_SHORT
    private void showToast(String msg) {
        final Toast toast = Toast.makeText(context.getApplicationContext(),
                msg, Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, TOAST_LELNGTH);
    }
}