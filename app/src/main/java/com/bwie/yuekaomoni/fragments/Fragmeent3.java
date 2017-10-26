package com.bwie.yuekaomoni.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.mycartutils.bean.ChildBean;
import com.bwie.mycartutils.bean.GroupBean;
import com.bwie.mycartutils.utils.CartUtils;
import com.bwie.yuekaomoni.Bean;
import com.bwie.yuekaomoni.R;
import com.bwie.yuekaomoni.utils.GsonObjectCallback;
import com.bwie.yuekaomoni.utils.OkHttp3Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class Fragmeent3 extends Fragment {
    @BindView(R.id.elv)
    ExpandableListView elv;
    @BindView(R.id.all)
    CheckBox all;
    @BindView(R.id.all_count)
    TextView allCount;
    @BindView(R.id.all_money)
    TextView allMoney;
    @BindView(R.id.lin_end)
    LinearLayout linEnd;
    Unbinder unbinder;
    private List<GroupBean> flist;
    private List<List<ChildBean>> slist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        flist = new ArrayList<>();
        slist = new ArrayList<>();
        getData();

    }

    public void getData(){
        OkHttp3Utils.getInstance().doGet("http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page=1", new GsonObjectCallback<Bean>() {
            @Override
            public void onUi(Bean bean) {
                List<Bean.DataBean> data = bean.getData();
                System.out.println("==============+"+data.size());
                for (int i = 0; i <data.size() ; i++) {
                    flist.add(new GroupBean(data.get(i).getUserName(),false));
                }
                for (int i = 0; i <data.size() ; i++) {
                    List<ChildBean> clist=new ArrayList<ChildBean>();
                    for (int j = 0; j <data.size(); j++) {
                        clist.add(new ChildBean(data.get(j).getTitle(),"100",data.get(j).getUserImg(),false,1));
                    }
                    slist.add(clist);
                }
                CartUtils.setCartData(getActivity(),flist,slist,elv,all,allCount,allMoney);


            }

            @Override
            public void onFailed(Call call, IOException e) {
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
