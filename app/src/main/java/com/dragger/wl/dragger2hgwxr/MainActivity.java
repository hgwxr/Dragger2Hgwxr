package com.dragger.wl.dragger2hgwxr;

import android.os.Bundle;
import android.widget.TextView;

import com.dragger.wl.dragger2hgwxr.injection.components.DaggerActivityComponent;
import com.dragger.wl.dragger2hgwxr.injection.components.DaggerAppComponent;
import com.dragger.wl.dragger2hgwxr.injection.modules.ActivityModule;
import com.dragger.wl.dragger2hgwxr.injection.modules.CaipuBase;
import com.dragger.wl.dragger2hgwxr.mvp.presenter.MainPresenter;
import com.dragger.wl.dragger2hgwxr.mvp.ui.IMainView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView{

    @BindView(R.id.text)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter.setText();
    }

    @Override
    protected void injectPresenter() {
        DaggerActivityComponent.builder()
                .appComponent(DemoApplication.getApp().getmAppComponent())
                .activityModule(new ActivityModule())
                .build().inject(this);
    }

 /* @Override
     protected MainPresenter initPresenter() {
         return new MainPresenter();
     }*/
    @Override
    public void setText(CaipuBase caipuBase) {
        textView.setText(caipuBase.toString());
    }
}
