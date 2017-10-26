package com.bwie.yuekaomoni.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.yuekaomoni.Bean;
import com.bwie.yuekaomoni.MyFragment4Adapter;
import com.bwie.yuekaomoni.R;
import com.bwie.yuekaomoni.presenter;
import com.bwie.yuekaomoni.view.HttpView;
import com.google.gson.Gson;

import java.util.List;


/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class Fragmeent4 extends Fragment implements HttpView{

    private RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4, null);
        rv = view.findViewById(R.id.fragemnt4_rv);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));
        presenter presenter = new presenter(this);
        presenter.getDatainfo();

    }


    @Override
    public void getSuccess(Bean bean) {
        List<Bean.DataBean> data = bean.getData();
        MyFragment4Adapter adapter = new MyFragment4Adapter(getActivity(), data);
        rv.setAdapter(adapter);
    }
}
