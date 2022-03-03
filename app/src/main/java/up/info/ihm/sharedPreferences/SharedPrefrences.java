package up.info.ihm.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class SharedPrefrences {

    private SharedPreferences sharedPreferences;

    public SharedPrefrences(Context applicationContext) {
        String sharedPreferencesS = "sharedPreferences";
        sharedPreferences = applicationContext.getSharedPreferences(sharedPreferencesS, Context.MODE_PRIVATE);
    }

    public void setCodePin(Integer code_pin) {
        sharedPreferences.edit().putInt("code_pin", code_pin).apply();
    }

    public int getCodePin() {
        return sharedPreferences.getInt("code_pin", 99999);
    }

    public void setIsRequieredPin(boolean requieredPin) {
        sharedPreferences.edit().putBoolean("requieredPin", requieredPin).apply();
    }

    public boolean isRequieredPin() {
        return sharedPreferences.getBoolean("requieredPin", true);
    }

    public void setCodePinOperation() {
        sharedPreferences.edit().putString("codePinOperation", "updatePinCode").apply();
    }

    public String getCodePinOperation() {
        return sharedPreferences.getString("codePinOperation", "createCodePin");
    }

    public void setAppLanguage(String language) {
        sharedPreferences.edit().putString("language", language).apply();
    }

    public String getAppLanguage() {
        return sharedPreferences.getString("language", Locale.getDefault().getLanguage());
    }

    public void setMode(String mode) {
        sharedPreferences.edit().putString("mode", mode).apply();
    }

    public String getMode() {
        return sharedPreferences.getString("mode", "light");
    }

    public void clearAllPrefrences() {
        sharedPreferences.edit().clear().apply();
    }
}
