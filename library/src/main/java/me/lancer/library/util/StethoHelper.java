package me.lancer.library.util;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * @author 1anc3r
 *         2017/7/25
 */
public interface StethoHelper {

    void init(Context context);

    OkHttpClient.Builder addNetworkInterceptor(OkHttpClient.Builder builder);
}
