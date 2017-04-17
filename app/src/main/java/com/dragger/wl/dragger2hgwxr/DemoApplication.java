/*
 * Copyright (C) 2013 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dragger.wl.dragger2hgwxr;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import com.dragger.wl.dragger2hgwxr.injection.components.AppComponent;
import com.dragger.wl.dragger2hgwxr.injection.components.DaggerAppComponent;
import com.dragger.wl.dragger2hgwxr.injection.modules.AppModule;
import com.facebook.stetho.Stetho;

import dagger.Component;
import javax.inject.Inject;
import javax.inject.Singleton;

public class DemoApplication extends Application {



  /*@Singleton
  @Component(modules = AndroidModule.class)
  public interface ApplicationComponent {
    void inject(DemoApplication application);
    void inject(HomeActivity homeActivity);
    void inject(DemoActivity demoActivity);
  }*/
  
//  @Inject LocationManager locationManager; // for some reason.
  public static Context mContext;
//  private ApplicationComponent component;
  private AppComponent mAppComponent;
  @Override public void onCreate() {
    super.onCreate();
    mContext = this;
    /*component = DaggerDemoApplication_ApplicationComponent.builder()
        .androidModule(new AndroidModule(this))
        .build();
    component().inject(this); // As of now, LocationManager should be injected into this.*/
    mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    Stetho.initializeWithDefaults(this);
  }
public static DemoApplication getApp(){
  return ((DemoApplication) mContext);
}
  public  AppComponent getmAppComponent(){
    return mAppComponent;
  }
//  public ApplicationComponent component() {
//    return component;
//  }

  public static DemoApplication get(){
    return (DemoApplication)mContext.getApplicationContext();
  }
}
