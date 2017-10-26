package com.bwie.yuekaomoni.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.yuekaomoni.Bean;
import com.bwie.yuekaomoni.MyFragmentAdapter;
import com.bwie.yuekaomoni.R;
import com.bwie.yuekaomoni.utils.GsonObjectCallback;
import com.bwie.yuekaomoni.utils.OkHttp3Utils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;


/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class Fragmeent1 extends Fragment{

    private Banner banner;
    private RecyclerView rv;
    private List<String> imgeList = Arrays.asList("http://cdn.duitang.com/uploads/blog/201308/18/20130818150526_Ru2Bk.thumb.600_0.png",
            "http://www.bkill.com/u/info_img/2012-09/02/2012083116140522302.jpg",
            "http://www.it165.net/uploadfile/2011/1218/20111218070928328.jpg");
    private SwipeRefreshLayout sr;
    private int page=1;
    private List<Bean.DataBean> bigList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);
        banner = view.findViewById(R.id.myBanner);
        rv = view.findViewById(R.id.fragemnt1_rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        sr = view.findViewById(R.id.sr);
        bigList=new ArrayList<Bean.DataBean>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        banner.setImageLoader(new ImageLoaderBanner());
       //设置集合
        banner.setImages(imgeList);
        //banner执行完方法之后调用
        banner.start();
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();

            }
        });
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当前RecyclerView显示出来的最后一个的item的position
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if(layoutManager instanceof LinearLayoutManager){
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }
                    if(lastPosition == recyclerView.getLayoutManager().getItemCount()-1){
                        Toast.makeText(getActivity(), "滑动到底了", Toast.LENGTH_SHORT).show();
                        page++;
                        getData();
                    }

                }
            }
        });

        getData();

    }

    public void getData(){
        OkHttp3Utils.getInstance().doGet("http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page="+page, new GsonObjectCallback<Bean>() {
            @Override
            public void onUi(Bean bean) {
                List<Bean.DataBean> data = bean.getData();
                System.out.println("==============+"+data.size());
                rv.setAdapter(new MyFragmentAdapter(getActivity(),data));
                if (bigList!=null){
                            bigList.addAll(data);
                        }

                        MyFragmentAdapter adapter = new MyFragmentAdapter(getActivity(), bigList);

                        rv.setAdapter(adapter);
                        adapter.SetDianjiListener(new MyFragmentAdapter.SetDianjiListener() {
                            @Override
                            public void setListener(View v, int po) {
                                Toast.makeText(getActivity(), "po"+po, Toast.LENGTH_SHORT).show();
                            }
                        });

                        sr.setRefreshing(false);

            }

            @Override
            public void onFailed(Call call, IOException e) {
            }
        });

//        OkHttp3Utils.doGet("http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page="+page, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String string = response.body().string();
//                System.out.println("==============="+string);
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Gson gson = new Gson();
//                        Bean bean = gson.fromJson(string, Bean.class);
//                        List<Bean.DataBean> data = bean.getData();
//
//                        if (bigList!=null){
//                            bigList.addAll(data);
//                        }
//
//                        MyFragmentAdapter adapter = new MyFragmentAdapter(getActivity(), bigList);
//
//                        rv.setAdapter(adapter);
//                        adapter.SetDianjiListener(new MyFragmentAdapter.SetDianjiListener() {
//                            @Override
//                            public void setListener(View v, int po) {
//                                Toast.makeText(getActivity(), "po"+po, Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        sr.setRefreshing(false);
//                    }
//                });
//
//            }
//        });
    }

    //轮播的类
    public class ImageLoaderBanner extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
//Glide设置图片的简单用法
            Glide.with(context).load(path).into(imageView);
        }
    }
}
