package me.lancer.rxweather.view.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.lancer.library.adapter.BaseRecyclerViewAdapter;
import me.lancer.rxweather.R;
import me.lancer.rxweather.model.db.entities.minimalist.WeatherForecast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 1anc3r
 *         16/6/23
 */
public class ForecastAdapter extends BaseRecyclerViewAdapter<ForecastAdapter.ViewHolder> {

    private List<WeatherForecast> weatherForecasts;

    public ForecastAdapter(List<WeatherForecast> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(itemView, this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ForecastAdapter.ViewHolder holder, int position) {
        WeatherForecast weatherForecast = weatherForecasts.get(position);
        holder.weekTextView.setText(weatherForecast.getWeek());
        holder.dateTextView.setText(weatherForecast.getDate());
        holder.weatherTextView.setText(TextUtils.isEmpty(weatherForecast.getWeather()) ?
                (weatherForecast.getWeatherDay().equals(weatherForecast.getWeatherNight()) ?
                        weatherForecast.getWeatherDay() : weatherForecast.getWeatherDay() + "转" + weatherForecast.getWeatherNight())
                : weatherForecast.getWeather());
        if (weatherForecast.getWeatherNight().contains("雷阵雨")) {
            holder.weatherIconImageView.setImageResource(R.mipmap.ic_thunderstorms);
        } else if (weatherForecast.getWeatherNight().contains("小雨")) {
            holder.weatherIconImageView.setImageResource(R.mipmap.ic_drizzle_rainy);
        } else if (weatherForecast.getWeatherNight().contains("雨")) {
            holder.weatherIconImageView.setImageResource(R.mipmap.ic_rainy);
        } else if (weatherForecast.getWeatherNight().contains("雨夹雪")) {
            holder.weatherIconImageView.setImageResource(R.mipmap.ic_drizzle_snowy);
        } else if (weatherForecast.getWeatherNight().contains("雪")) {
            holder.weatherIconImageView.setImageResource(R.mipmap.ic_snowy);
        } else if (weatherForecast.getWeatherNight().contains("雾")) {
            holder.weatherIconImageView.setImageResource(R.mipmap.ic_haze);
        } else if (weatherForecast.getWeatherNight().contains("云") || weatherForecast.getWeatherNight().contains("阴")) {
            holder.weatherIconImageView.setImageResource(R.mipmap.ic_cloudy);
        } else if (weatherForecast.getWeatherNight().contains("晴")) {
            holder.weatherIconImageView.setImageResource(R.mipmap.ic_sunny);
        }
        holder.tempMaxTextView.setText(weatherForecast.getTempMax() + "°");
        holder.tempMinTextView.setText(weatherForecast.getTempMin() + "°");
    }

    @Override
    public int getItemCount() {
        return weatherForecasts == null ? 0 : weatherForecasts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.week_text_view)
        TextView weekTextView;
        @BindView(R.id.date_text_view)
        TextView dateTextView;
        @BindView(R.id.weather_icon_image_view)
        ImageView weatherIconImageView;
        @BindView(R.id.weather_text_view)
        TextView weatherTextView;
        @BindView(R.id.temp_max_text_view)
        TextView tempMaxTextView;
        @BindView(R.id.temp_min_text_view)
        TextView tempMinTextView;

        ViewHolder(View itemView, ForecastAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> adapter.onItemHolderClick(ViewHolder.this));
        }
    }
}
