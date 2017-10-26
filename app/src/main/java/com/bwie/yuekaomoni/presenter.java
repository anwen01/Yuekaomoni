package com.bwie.yuekaomoni;

import com.bwie.yuekaomoni.model.HttpModel;
import com.bwie.yuekaomoni.view.HttpView;

/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class presenter {
    private HttpView httpView;
    private HttpModel httpModel;

    public presenter(HttpView httpView) {
        this.httpView = httpView;
        this.httpModel=new HttpModel();
    }

    public void getDatainfo(){
        httpModel.getData(new HttpModel.ModleListener() {
            @Override
            public void getListener(Bean bean) {
                httpView.getSuccess(bean);
            }
        });
    }
}
