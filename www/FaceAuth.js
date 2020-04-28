cordova.define("com.sachinsemlety.faceauth.FaceAuth", function(require, exports, module) {
    var exec = require('cordova/exec');

    exports.biometricAuth = function(success, error) {
        exec(success, error, 'FaceAuth', 'biometricAuth', [{}]);
    };

    exports.isBiometricAvailable = function(success, error) {
        exec(success, error, 'FaceAuth', 'isBiometricAvailable', [{}]);
    };
});