package com.sadam.ui4.FragmentSelfPage.FragmentComposition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sadam.ui4.Data.Video;
import com.sadam.ui4.MainActivity;
import com.sadam.ui4.R;

import java.util.ArrayList;
import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private List<Video> videos = new ArrayList<>();
    private MainActivity mainActivity;

    public VideoAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_recycler_view_item_layout, parent, false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(view);

        int count_of_item_show_on_one_screen = 1;//一个屏幕能显示的item 数目
        int parentHeight = parent.getHeight();
        parent.getWidth();
        ViewGroup.LayoutParams layoutParams = videoViewHolder.itemView.getLayoutParams();
        layoutParams.height = (parentHeight / count_of_item_show_on_one_screen);

        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Toast.makeText(mainActivity, "this is Position:" + position, Toast.LENGTH_LONG).show();
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
