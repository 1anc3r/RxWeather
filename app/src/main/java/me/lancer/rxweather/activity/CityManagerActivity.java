package me.lancer.rxweather.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import me.lancer.library.activity.BaseActivity;
import me.lancer.library.util.ActivityUtils;
import me.lancer.rxweather.R;
import me.lancer.rxweather.WeatherApplication;
import me.lancer.rxweather.activity.component.DaggerCityManagerComponent;
import me.lancer.rxweather.activity.module.DrawerMenuModule;
import me.lancer.rxweather.presenter.DrawerMenuPresenter;
import me.lancer.rxweather.view.fragment.DrawerMenuFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 1anc3r
 */
public class CityManagerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    DrawerMenuPresenter drawerMenuPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        DrawerMenuFragment drawerMenuFragment = DrawerMenuFragment.newInstance(3);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), drawerMenuFragment, R.id.fragment_container);

        DaggerCityManagerComponent.builder()
                .applicationComponent(WeatherApplication.getInstance().getApplicationComponent())
                .drawerMenuModule(new DrawerMenuModule(drawerMenuFragment))
                .build().inject(this);
    }
}
