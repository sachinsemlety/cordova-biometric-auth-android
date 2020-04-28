# Installation
```
cordova plugin add cordova-plugin-android-fragmentactivity --save
```

or

```
phonegap plugin add cordova-plugin-android-fragmentactivity
```

# Usage
No manual configuration required.

# How it works
This plugin comes bundled with a CordovaFragment (which is a Fragment similar to CordovaActivity) and a MainActivity that extends AppCompatActivity and adds CordovaFragment using FragmentTransactions.

AndroidManifest.xml is automatically updated to use the new MainActivity.

# Credits
Based on cordova-android-fragments (https://github.com/rajivnarayana/CordovaFragments)

# History
## 0.0.9
- Fix missing import

## 0.0.8
- Add onActivityResult callback

## 0.0.7
- Merge FrameLayout change
- Merge ${mypackage} fix

## 0.0.6
- Fix stupid cordova-android 7 detection

## 0.0.5
- Attempt to avoid crash only seen on Galaxy J1 Ace (5.1.1)

## 0.0.4
- Experimental support for cordova-android 7
- Increased dependency versions

## 0.0.3
- Added missing method to activity to handle permission request results

## 0.0.2
- Correct log message
- Add history to README

## 0.0.1
Initial release
