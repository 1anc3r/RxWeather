package me.lancer.rxweather.util.stetho;

import android.content.Context;

import me.lancer.library.util.StethoHelper;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * @author 1anc3r
 *         2017/7/25
 */
public class DebugStethoHelper implements StethoHelper {

    @Override
    public void init(Context context) {
        Stetho.initializeWithDefaults(context);
    }

    @Override
    public OkHttpClient.Builder addNetworkInterceptor(OkHttpClient.Builder builder) {
        return builder.addNetworkInterceptor(new StethoInterceptor());
    }
}
