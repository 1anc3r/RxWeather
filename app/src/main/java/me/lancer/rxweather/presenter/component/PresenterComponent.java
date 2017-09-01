package me.lancer.rxweather.presenter.component;

import me.lancer.rxweather.presenter.DrawerMenuPresenter;
import me.lancer.rxweather.presenter.SelectCityPresenter;

import javax.inject.Singleton;

import dagger.Component;
import me.lancer.rxweather.ApplicationModule;
import me.lancer.rxweather.presenter.HomePagePresenter;

/**
 * @author 张磊 (baron[dot]zhanglei[at]gmail[dot]com)
 *         2016/12/2
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface PresenterComponent {

    void inject(HomePagePresenter presenter);

    void inject(SelectCityPresenter presenter);

    void inject(DrawerMenuPresenter presenter);
}
 