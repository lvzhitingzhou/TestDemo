package com.yoyo.testdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoyo.testdemo.R;
import com.yoyo.testdemo.domain.Information;

import java.util.Collections;
import java.util.List;

/**
 * 抽屉的recyclerView中的adapter
 * Created by liumin on 2015/5/5.
 */
public class DrawerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static int VIEW_TYPE_HEADER = 0;
    private static int VIEW_TYPE_ITEM = 1;
    private LayoutInflater inflater;
    private List<Information> data = Collections.emptyList();

    public DrawerRecyclerAdapter(Context context, List<Information> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == VIEW_TYPE_HEADER){
            View view = inflater.inflate(R.layout.drawer_header,viewGroup, false);
            return new RecyclerHeaderHolder(view);
        }else{
            View view = inflater.inflate(R.layout.drawer_item, viewGroup, false);
            return  new RecyclerItemHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return VIEW_TYPE_HEADER;
        }else{
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof RecyclerHeaderHolder){

        }else{
            RecyclerItemHolder itemHolder = (RecyclerItemHolder) viewHolder;
            itemHolder.icon.setImageResource(data.get(position-1).iconId);   // 去掉头布局
            itemHolder.text.setText(data.get(position-1).title);

        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;  // 头布局
    }

    /**
     * list头布局的viewholder
     */
    class RecyclerHeaderHolder extends RecyclerView.ViewHolder{

        public RecyclerHeaderHolder(View headerView) {
            super(headerView);
        }
    }

    /**
     * list item的viewHolder
     */
    class RecyclerItemHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView text;
        public RecyclerItemHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            text = (TextView) itemView.findViewById(R.id.listText);
        }

    }
}
