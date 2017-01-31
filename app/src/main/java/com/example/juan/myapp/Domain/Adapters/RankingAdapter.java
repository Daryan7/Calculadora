package com.example.juan.myapp.Domain.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juan.myapp.Domain.User;
import com.example.juan.myapp.R;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {
    private List<User> userList;

    public RankingAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranking_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nickView.setText(userList.get(position).getNickName());
        holder.pointsView.setText(Integer.toString(userList.get(position).getPoints()));
        holder.positionView.setText(Integer.toString(position+1));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nickView;
        public TextView pointsView;
        public TextView positionView;

        public ViewHolder(View itemView) {
            super(itemView);
            nickView = (TextView)itemView.findViewById(R.id.nickName);
            pointsView = (TextView) itemView.findViewById(R.id.points);
            positionView = (TextView)itemView.findViewById(R.id.position);
        }
    }
}
