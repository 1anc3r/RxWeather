package me.lancer.rxweather.activity.module;

import me.lancer.rxweather.presenter.HomePagePresenter;

import dagger.Module;
import dagger.Provides;
import me.lancer.rxweather.contract.HomePageContract;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link HomePagePresenter}
 *
 * @author 1anc3r
 *         2016/11/30
 */
@Module
public class HomePageModule {

    private HomePageContract.View view;

    public HomePageModule(HomePageContract.View view) {

        this.view = view;
    }

    @Provides
    HomePageContract.View provideHomePageContractView() {
        return view;
    }

}
