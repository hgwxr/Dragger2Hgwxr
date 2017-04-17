package com.dragger.wl.dragger2hgwxr.mvp.presenter;

import android.app.Activity;

import com.dragger.wl.dragger2hgwxr.mvp.ui.IBaseView;

/**
 * @author wl
 * @version :
 * @date 2017/4/14
 * @描述
 */

public class BasePresenter<V extends IBaseView> {

    protected  V mView;

   //解除绑定
    public void deAttach() {
        mView=null;
    }
    //绑定
    public void attach(V view ) {
      mView=view;
    }
}
