package me.lancer.rxweather.activity.module;

import dagger.Module;
import dagger.Provides;

import me.lancer.rxweather.contract.SelectCityContract;

/**
 * @author 1anc3r
 *         2016/11/30
 */
@Module
public class SelectCityModule {

    private SelectCityContract.View view;

    public SelectCityModule(SelectCityContract.View view) {
        this.view = view;
    }

    @Provides
    SelectCityContract.View provideSelectCityContractView() {
        return view;
    }
}
