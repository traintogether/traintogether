package com.codepath.traintogether;

import com.codepath.traintogether.models.User;
import com.codepath.traintogether.utils.Constants;

import net.danlew.android.joda.JodaTimeAndroid;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ameyapandilwar on 8/18/16 at 4:36 AM.
 */
public class TrainTogetherApplication extends Application {

    private static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        TrainTogetherApplication.currentUser = currentUser;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(Constants.DEFAULT_FONT_ASSET_PATH)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        setCurrentUser(null);
    }
}
