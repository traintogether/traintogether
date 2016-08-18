package com.codepath.traintogether;

import android.app.Application;

import com.codepath.traintogether.utils.Constants;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ameyapandilwar on 8/18/16 at 4:36 AM.
 */
public class TrainTogetherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(Constants.DEFAULT_FONT_ASSET_PATH)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
