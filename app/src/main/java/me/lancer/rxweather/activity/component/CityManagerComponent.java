package me.lancer.rxweather.activity.component;

import me.lancer.rxweather.activity.CityManagerActivity;

import dagger.Component;
import me.lancer.rxweather.ApplicationComponent;
import me.lancer.rxweather.activity.module.DrawerMenuModule;
import me.lancer.rxweather.util.ActivityScoped;

/**
 * @author 1anc3r
 *         2016/11/30
 */
@ActivityScoped
@Component(modules = DrawerMenuModule.class, dependencies = ApplicationComponent.class)
public interface CityManagerComponent {

    void inject(CityManagerActivity cityManagerActivity);
}
