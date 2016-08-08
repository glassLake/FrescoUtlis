package com.hss01248.retrofitdemo;

import com.hss01248.retrofitdemo.bean.BaseNetBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2016/8/7.
 */
public abstract class MyCallBack<T extends BaseNetBean> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if (response.raw().code() == 200){
            switch (response.body().code){
                case 0:
                    break;

            }
        }

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }
}
