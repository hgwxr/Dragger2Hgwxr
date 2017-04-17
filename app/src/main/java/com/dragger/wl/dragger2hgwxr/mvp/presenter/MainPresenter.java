package com.dragger.wl.dragger2hgwxr.mvp.presenter;

import android.text.TextUtils;
import android.widget.Toast;

import com.dragger.wl.dragger2hgwxr.BuildConfig;
import com.dragger.wl.dragger2hgwxr.DemoApplication;
import com.dragger.wl.dragger2hgwxr.injection.components.DaggerAppComponent;
import com.dragger.wl.dragger2hgwxr.injection.modules.ApiService;
import com.dragger.wl.dragger2hgwxr.injection.modules.ApiServiceModel;
import com.dragger.wl.dragger2hgwxr.injection.modules.CaipuBase;
import com.dragger.wl.dragger2hgwxr.injection.modules.MainApi;
import com.dragger.wl.dragger2hgwxr.mvp.ui.IMainView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author wl
 * @version :
 * @date 2017/4/14
 * @描述
 */

public class MainPresenter extends BasePresenter<IMainView> {


//    @Inject
    private MainApi mMainApi;
    public MainPresenter() {
        mMainApi= ApiService.createService(MainApi.class);
//        DaggerAppComponent.builder().apiServiceModel(new ApiServiceModel()).
//        this.mMainApi=mainApi;
    }
    public void setText(){
        Observable<CaipuBase> observable = mMainApi.getCaiPuBase();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CaipuBase>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(DemoApplication.mContext,"Complete",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) {
                            e.printStackTrace();
                        }
                        if (mView != null) {
                            // mView.onError(e);
                        }
                    }
                    @Override
                    public void onNext(CaipuBase caipuBase) {
                        mView.setText(caipuBase);
                    }
                });

    }
}
