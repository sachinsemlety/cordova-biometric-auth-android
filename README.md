# Usage
Before installing, this plugin requires Cordova Fragment Activity
Please install this plugin first (https://github.com/ReallySmallSoftware/cordova-plugin-android-fragmentactivity)
This plugin is required because Biometric Auth for Android 10 and above doesn't support Cordova Activity as it supports Fragment Activity.


# Manual Changes
After Installation of both plugins
1. Open the FaceAuth Plugin replace "import com.example.hello.MainActivity" to your MainActivity import;
2. Open MainActivity remove "import android.support.v4.app.FragmentActivity" and replace with "import androidx.fragment.app.FragmentActivity";

# How it works
This Plugin is used for Biometric Authentication using Biometric Prompt Api, for Android device having Android 10 or above will see face,fingerprint and password option, if authentication is enabled and for Android device having version less than 10 will only support fingerprint and password option.

# History
## 0.0.1
Initial release
