package com.bwie.yuekaomoni;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class MyFragment4Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Bean.DataBean> list;

    public MyFragment4Adapter(Context context, List<Bean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = View.inflate(context, R.layout.fragment4_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            myViewHolder.iv.setBorderWidth(2, Color.GRAY);// 设置边框
//            Glide.with(context).load(list.get(position).getUserImg()).into(myViewHolder.iv);
            ImageLoader.getInstance().displayImage(list.get(position).getImg(),myViewHolder.iv);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

       CircleImageView iv;
        public MyViewHolder(View itemView) {
            super(itemView);
           iv= itemView.findViewById(R.id.iv);

        }
    }
}
