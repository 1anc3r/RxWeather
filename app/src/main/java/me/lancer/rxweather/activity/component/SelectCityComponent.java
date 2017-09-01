package me.lancer.rxweather.activity.component;

import me.lancer.rxweather.activity.SelectCityActivity;
import me.lancer.rxweather.activity.module.SelectCityModule;
import me.lancer.rxweather.util.ActivityScoped;

import dagger.Component;
import me.lancer.rxweather.ApplicationComponent;

/**
 * @author 1anc3r
 *         2016/11/30
 */
@ActivityScoped
@Component(modules = SelectCityModule.class, dependencies = ApplicationComponent.class)
public interface SelectCityComponent {

    void inject(SelectCityActivity selectCityActivity);
}
