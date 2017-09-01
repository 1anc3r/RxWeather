package me.lancer.rxweather.activity.module;

import me.lancer.rxweather.contract.DrawerContract;

import dagger.Module;
import dagger.Provides;

/**
 * @author 1anc3r
 *         2016/11/30
 */
@Module
public class DrawerMenuModule {

    private DrawerContract.View view;

    public DrawerMenuModule(DrawerContract.View view) {
        this.view = view;
    }

    @Provides
    DrawerContract.View provideCityManagerContactView() {
        return view;
    }
}
