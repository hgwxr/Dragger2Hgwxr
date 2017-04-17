package com.dragger.wl.dragger2hgwxr.injection.modules;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author wl
 * @version :
 * @date 2017/4/14
 * @描述
 */

public interface MainApi {
    //http://www.tngou.net/api/cook/classify
    @GET("api/cook/classify")
    Observable<CaipuBase> getCaiPuBase();
}
