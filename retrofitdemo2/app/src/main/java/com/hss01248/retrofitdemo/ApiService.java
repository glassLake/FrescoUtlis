package com.hss01248.retrofitdemo;

import com.hss01248.retrofitdemo.bean.LoginInfo;
import com.hss01248.retrofitdemo.bean.BaseNetBean;
import com.hss01248.retrofitdemo.bean.Repo;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public interface  ApiService<T extends BaseNetBean> {

    @Headers("{headers}")
    @POST("{url}")
    Call<ResponseBody> executePost(@Path("url") String url, @QueryMap Map<String, String> maps);

    @Headers("{headers}")
    @POST("{url}")
    Call<T> commonPost(@Path("url") String url, @QueryMap Map<String, String> params);


    @POST(Url.USER_LOGIN)
    Call<LoginInfo> login(@Field("username") String username, @Field("password") String password,
                          @Field("platform") String platform, @Field("imei") String imei);

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);






}
