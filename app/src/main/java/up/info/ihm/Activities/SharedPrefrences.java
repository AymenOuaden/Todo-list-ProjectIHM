package up.info.ihm.Activities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class SharedPrefrences {

    private SharedPreferences sharedPreferences;

    public SharedPrefrences(Context applicationContext) {
        String sharedPreferencesS = "sharedPreferences";
        sharedPreferences = applicationContext.getSharedPreferences(sharedPreferencesS, Context.MODE_PRIVATE);
    }

    void setCodePin(Integer code_pin) {
        sharedPreferences.edit().putInt("code_pin", code_pin).apply();
    }

    int getCodePin() {
        return sharedPreferences.getInt("code_pin", 99999);
    }

    void setIsRequieredPin(boolean requieredPin) {
        sharedPreferences.edit().putBoolean("requieredPin", requieredPin).apply();
    }

    boolean isRequieredPin() {
        return sharedPreferences.getBoolean("requieredPin", true);
    }

    void setCodePinOperation() {
        sharedPreferences.edit().putString("codePinOperation", "updatePinCode").apply();
    }

    String getCodePinOperation() {
        return sharedPreferences.getString("codePinOperation", "createCodePin");
    }

    void setAppLanguage(String language) {
        sharedPreferences.edit().putString("language", language).apply();
    }

    String getAppLanguage() {
        return sharedPreferences.getString("language", Locale.getDefault().getLanguage());
    }

    void setMode(String mode) {
        sharedPreferences.edit().putString("mode", mode).apply();
    }

    String getMode() {
        return sharedPreferences.getString("mode", "light");
    }

    public void clearAllPrefrences() {
        sharedPreferences.edit().clear().apply();
    }
}
