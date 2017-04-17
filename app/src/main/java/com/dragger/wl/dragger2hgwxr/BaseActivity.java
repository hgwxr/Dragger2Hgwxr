package com.dragger.wl.dragger2hgwxr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dragger.wl.dragger2hgwxr.mvp.presenter.BasePresenter;
import com.dragger.wl.dragger2hgwxr.mvp.ui.IBaseView;

import javax.inject.Inject;

/**
 * @author wl
 * @version :
 * @date 2017/4/14
 * @描述
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity  implements IBaseView {

    @Inject
    protected T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mPresenter=initPresenter();

        injectPresenter();
        mPresenter.attach(this);


    }

    protected abstract void injectPresenter();

  //  protected abstract T initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
       mPresenter.deAttach();
    }
}
