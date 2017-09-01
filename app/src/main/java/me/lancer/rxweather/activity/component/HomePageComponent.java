package me.lancer.rxweather.activity.component;

import me.lancer.rxweather.activity.MainActivity;
import me.lancer.rxweather.activity.module.HomePageModule;
import me.lancer.rxweather.util.ActivityScoped;
import me.lancer.rxweather.ApplicationComponent;

import dagger.Component;

/**
 * @author 1anc3r
 *         2016/11/29
 */
@ActivityScoped
@Component(modules = HomePageModule.class, dependencies = ApplicationComponent.class)
public interface HomePageComponent {

    void inject(MainActivity mainActivity);
}
