package me.lancer.rxweather.util.stetho;

import android.content.Context;

import me.lancer.library.util.StethoHelper;

import okhttp3.OkHttpClient;

/**
 * @author 1anc3r
 *         2017/7/25
 */
public class ReleaseStethoHelper implements StethoHelper {

    @Override
    public void init(Context context) {

    }

    @Override
    public OkHttpClient.Builder addNetworkInterceptor(OkHttpClient.Builder builder) {
        return null;
    }
}
