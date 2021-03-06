package me.lancer.rxweather.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.lancer.library.activity.BaseActivity;
import me.lancer.library.util.ActivityUtils;
import me.lancer.library.util.DateConvertUtils;
import me.lancer.rxweather.R;
import me.lancer.rxweather.WeatherApplication;
import me.lancer.rxweather.activity.component.DaggerHomePageComponent;
import me.lancer.rxweather.activity.module.HomePageModule;
import me.lancer.rxweather.model.db.entities.minimalist.Weather;
import me.lancer.rxweather.presenter.DrawerMenuPresenter;
import me.lancer.rxweather.presenter.HomePagePresenter;
import me.lancer.rxweather.view.fragment.DrawerMenuFragment;
import me.lancer.rxweather.view.fragment.HomePageFragment;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页
 *
 * @author 1anc3r
 */
public class MainActivity extends BaseActivity
        implements HomePageFragment.OnFragmentInteractionListener, DrawerMenuFragment.OnSelectCity {


    @BindView(R.id.refresh_layout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    //基本天气信息
    @BindView(R.id.backdrop_image_view)
    ImageView backdropImageView;
    @BindView(R.id.weather_icon_image_view)
    ImageView weatherIconImageView;
    @BindView(R.id.temp_text_view)
    TextView tempTextView;
    @BindView(R.id.weather_text_view)
    TextView weatherNameTextView;
    @BindView(R.id.publish_time_text_view)
    TextView realTimeTextView;

    @Inject
    HomePagePresenter homePagePresenter;
    DrawerMenuPresenter drawerMenuPresenter;

    private String currentCityId;
    private String[] backdrops = {
            "https://raw.githubusercontent.com/1anc3r/Pocket/master/ic_day.png",
            "https://raw.githubusercontent.com/1anc3r/Pocket/master/ic_night.png",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //设置 Header 为 Material风格
        ClassicsHeader header = new ClassicsHeader(this);
        header.setPrimaryColors(this.getResources().getColor(R.color.colorPrimary), Color.WHITE);
        this.smartRefreshLayout.setRefreshHeader(header);
        this.smartRefreshLayout.setOnRefreshListener(refreshLayout -> homePagePresenter.loadWeather(currentCityId, true));

        SimpleDateFormat formatter = new SimpleDateFormat("HH");
        int date = Integer.parseInt(formatter.format(new Date(System.currentTimeMillis())));
        if (date > 6 && date < 18) {
            Glide.with(this).load(backdrops[0]).into(backdropImageView);
        } else {
            Glide.with(this).load(backdrops[1]).into(backdropImageView);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawerLayout != null;
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        HomePageFragment homePageFragment = HomePageFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homePageFragment, R.id.fragment_container);

        DaggerHomePageComponent.builder()
                .applicationComponent(WeatherApplication.getInstance().getApplicationComponent())
                .homePageModule(new HomePageModule(homePageFragment))
                .build().inject(this);

        DrawerMenuFragment drawerMenuFragment = DrawerMenuFragment.newInstance(1);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), drawerMenuFragment, R.id.fragment_container_drawer_menu);

        drawerMenuPresenter = new DrawerMenuPresenter(this, drawerMenuFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_about) {
            return true;
        } else if (id == R.id.action_feedback) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updatePageTitle(Weather weather) {
        currentCityId = weather.getCityId();
        smartRefreshLayout.finishRefresh();
        toolbar.setTitle(weather.getCityName());
        collapsingToolbarLayout.setTitle(weather.getCityName());

        tempTextView.setText(weather.getWeatherLive().getTemp() + "°C");
        weatherNameTextView.setText(weather.getWeatherLive().getWeather());
        realTimeTextView.setText(getString(R.string.string_publish_time) + DateConvertUtils.timeStampToDate(weather.getWeatherLive().getTime(), DateConvertUtils.DATA_FORMAT_PATTEN_YYYY_MM_DD_HH_MM));

        if (weather.getWeatherLive().getWeather().contains("雷阵雨")) {
            weatherIconImageView.setImageResource(R.mipmap.ic_thunderstorms);
        } else if (weather.getWeatherLive().getWeather().contains("小雨")) {
            weatherIconImageView.setImageResource(R.mipmap.ic_drizzle_rainy);
        } else if (weather.getWeatherLive().getWeather().contains("雨")) {
            weatherIconImageView.setImageResource(R.mipmap.ic_rainy);
        } else if (weather.getWeatherLive().getWeather().contains("雨夹雪")) {
            weatherIconImageView.setImageResource(R.mipmap.ic_drizzle_snowy);
        } else if (weather.getWeatherLive().getWeather().contains("雪")) {
            weatherIconImageView.setImageResource(R.mipmap.ic_snowy);
        } else if (weather.getWeatherLive().getWeather().contains("雾")) {
            weatherIconImageView.setImageResource(R.mipmap.ic_haze);
        } else if (weather.getWeatherLive().getWeather().contains("云")) {
            weatherIconImageView.setImageResource(R.mipmap.ic_cloudy);
        } else if (weather.getWeatherLive().getWeather().contains("晴")) {
            weatherIconImageView.setImageResource(R.mipmap.ic_sunny);
        }
    }

    @Override
    public void addOrUpdateCityListInDrawerMenu(Weather weather) {
        drawerMenuPresenter.loadSavedCities();
    }

    @Override
    public void onSelect(String cityId) {

        assert drawerLayout != null;
        drawerLayout.closeDrawer(GravityCompat.START);

        new Handler().postDelayed(() -> homePagePresenter.loadWeather(cityId, false), 250);
    }
}
