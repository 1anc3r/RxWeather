package me.lancer.rxweather.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.lancer.library.adapter.BaseRecyclerViewAdapter;
import me.lancer.rxweather.R;
import me.lancer.rxweather.model.db.entities.minimalist.LifeIndex;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 1anc3r
 *         2016/12/13
 */
public class LifeIndexAdapter extends BaseRecyclerViewAdapter<LifeIndexAdapter.ViewHolder> {

    private Context context;
    private List<LifeIndex> indexList;

    public LifeIndexAdapter(Context context, List<LifeIndex> indexList) {
        this.context = context;
        this.indexList = indexList;
    }

    @Override
    public LifeIndexAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_life_index, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(LifeIndexAdapter.ViewHolder holder, int position) {
        LifeIndex index = indexList.get(position);
        holder.indexIconImageView.setImageDrawable(getIndexDrawable(context, index.getName()));
        holder.indexLevelTextView.setText(index.getIndex());
        holder.indexNameTextView.setText(index.getName());
    }

    @Override
    public int getItemCount() {
        return indexList == null ? 0 : indexList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.index_icon_image_view)
        ImageView indexIconImageView;
        @BindView(R.id.index_level_text_view)
        TextView indexLevelTextView;
        @BindView(R.id.index_name_text_view)
        TextView indexNameTextView;

        ViewHolder(View itemView, LifeIndexAdapter adapter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> adapter.onItemHolderClick(LifeIndexAdapter.ViewHolder.this));
        }
    }

    private Drawable getIndexDrawable(Context context, String indexName) {


        int colorResourceId = R.drawable.ic_comfort;
        if (indexName.contains("穿衣")) {
            colorResourceId = R.drawable.ic_dress;
        } else if (indexName.contains("运动")) {
            colorResourceId = R.drawable.ic_sport;
        } else if (indexName.contains("洗车")) {
            colorResourceId = R.drawable.ic_car_wash;
        } else if (indexName.contains("感冒")) {
            colorResourceId = R.drawable.ic_flu;
        } else if (indexName.contains("旅游")) {
            colorResourceId = R.drawable.ic_travel;
        } else if (indexName.contains("紫外线")) {
            colorResourceId = R.drawable.ic_ultraviolet;
        } else if (indexName.contains("空气质量")) {
            colorResourceId = R.drawable.ic_air_quality;
        } else if (indexName.contains("空气质量")) {
            colorResourceId = R.drawable.ic_comfort;
        }
        return context.getResources().getDrawable(colorResourceId);
    }
}
