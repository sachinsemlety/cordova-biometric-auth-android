package com.sachinsemlety.faceauth;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;

import com.example.hello.MainActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.Executor;

/**
 * This class echoes a string called from JavaScript.
 */
public class FaceAuth extends CordovaPlugin {

    private static final String TAG = "bio_prompt_demo_control";

    private static final String KEY_COUNTER = "saved_counter";
    private static final String KEY_LOG = "saved_log";

    private static final String BIOMETRIC_SUCCESS_MESSAGE = "BIOMETRIC_SUCCESS_MESSAGE";
    private static final String BIOMETRIC_ERROR_HW_UNAVAILABLE_MESSAGE =
            "BIOMETRIC_ERROR_HW_UNAVAILABLE";
    private static final String BIOMETRIC_ERROR_NONE_ENROLLED_MESSAGE =
            "BIOMETRIC_ERROR_NONE_ENROLLED";
    private static final String BIOMETRIC_ERROR_NO_HARDWARE =
            "BIOMETRIC_ERROR_NO_HARDWARE";
    private static final String BIOMETRIC_ERROR_UNKNOWN = "Error unknown return result";
    private MainActivity mActivity;
    BiometricPrompt mBiometricPrompt;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    final Executor mExecutor = mHandler::post;
    private int mCounter;
    public static CallbackContext mCallbackContext;


    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        initialize();
        super.initialize(cordova, webView);
    }

    private void initialize() {
        mActivity = (MainActivity) cordova.getActivity();
        mBiometricPrompt = new BiometricPrompt(mActivity.getFragmentActivity(), mExecutor, mAuthenticationCallback);
    }


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        mCallbackContext = callbackContext;
        if (action.equals("isBiometricAvailable")) {
            canAuthenticate();
        }else if (action.equals("biometricAuth")) {
            authenticateBiometric();
        }else{
            return false;
        }
        return true;
    }


    public void canAuthenticate() {
        BiometricManager biometricManager = BiometricManager.from(cordova.getContext().getApplicationContext());
        String message;
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                message = BIOMETRIC_SUCCESS_MESSAGE;
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                message = BIOMETRIC_ERROR_HW_UNAVAILABLE_MESSAGE;
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                message = BIOMETRIC_ERROR_NONE_ENROLLED_MESSAGE;
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                message = BIOMETRIC_ERROR_NO_HARDWARE;
                break;
            default:
                message = BIOMETRIC_ERROR_UNKNOWN;
        }
        // if (message.equals(BIOMETRIC_SUCCESS_MESSAGE)) {
        //     cordova.getActivity().runOnUiThread(() -> startAuthentication());
        // } else {
            mCallbackContext.success(message);
        // }

    }


    public void authenticateBiometric(){
        cordova.getActivity().runOnUiThread(() -> startAuthentication());
    }


    final BiometricPrompt.AuthenticationCallback mAuthenticationCallback =
            new BiometricPrompt.AuthenticationCallback() {
                private int mNumberFailedAttempts = 0;

                @Override
                public void onAuthenticationError(int err, @NonNull CharSequence message) {
                    Log.i(TAG, "onAuthenticationError " + err + ": " + message);
                    mNumberFailedAttempts = 0;
                    mCallbackContext.success("onAuthenticationError " + err + ": " + message);
                }

                @Override
                public void onAuthenticationSucceeded(
                        @NonNull BiometricPrompt.AuthenticationResult result) {
                    Log.i(TAG, "onAuthenticationSucceeded");
                    mNumberFailedAttempts = 0;
                    mCallbackContext.success("onAuthenticationSucceeded");
                }

                @Override
                public void onAuthenticationFailed() {
                    Log.i(TAG, "onAuthenticationFailed");
                    mNumberFailedAttempts++;
                    mCallbackContext.success("onAuthenticationFailed");
                    // Cancel authentication after 3 failed attempts to test the cancel() method.
                    if (mNumberFailedAttempts == 3) {
                        mBiometricPrompt.cancelAuthentication();
                    }
                }
            };

    private void startAuthentication() {
        // Build the biometric prompt info
        BiometricPrompt.PromptInfo.Builder builder = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Authentication");
//                .setSubtitle("Face & Fingerprint Authentication");
            builder.setDeviceCredentialAllowed(true);
//        builder.setNegativeButtonText("Close");
        BiometricPrompt.PromptInfo info = builder.build();
        mCounter++;

        // Show the biometric prompt.
        mBiometricPrompt.authenticate(info);
    }
}
