package up.info.ihm;

import android.content.Context;
import android.content.SharedPreferences;

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
        return sharedPreferences.getInt("code_pin", 0);
    }

    public void setIsRequieredPin(boolean requieredPin) {
        sharedPreferences.edit().putBoolean("requieredPin", requieredPin).apply();
    }

    public boolean isRequieredPin() {
        return sharedPreferences.getBoolean("requieredPin", true);
    }

    public void clearAllPrefrences() {
        sharedPreferences.edit().clear().apply();
    }
}
