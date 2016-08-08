package com.hss01248.retrofitdemo;


import android.text.TextUtils;

import com.hss01248.retrofitdemo.bean.BaseNetBean;
import com.hss01248.retrofitdemo.bean.SeverCode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class RetrofitManage {
    Retrofit retrofit;
    ApiService service;

    private RetrofitManage() {
        init();

    }

    private void init() {
        //默认情况下，Retrofit只能够反序列化Http体为OkHttp的ResponseBody类型
        //并且只能够接受ResponseBody类型的参数作为@body

        OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();
        OkHttpClient client=httpBuilder.readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS) //设置超时
                .retryOnConnectionFailure(true)//重试
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(Url.HEAD)
                .client(client)
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
                .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                .build();

        service = retrofit.create(ApiService.class);
    }

    public static RetrofitManage getInstance() {
        return RetrofitManager.retrofitManage;
    }

    private static class RetrofitManager {
        private static final RetrofitManage retrofitManage = new RetrofitManage();
    }





    public Call postRequest(final String urlTail, final Map<String,String> map, final MyNetListener listener) {

       // map.put("session_id",UIUtils.getSessionId());
        final Call<ResponseBody> call = service.executePost(urlTail,map);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){
                    try {
                        String json =  response.body().string();
                        JSONObject object  = new JSONObject(json);
                        parseNewJsonResponse(object,urlTail,"",map,listener);//沿用volley里解析的方法

                    } catch (IOException e) {
                        e.printStackTrace();
                        onFailure(call,e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onFailure(call,e);
                    }
                }else {
                    onFailure(call,null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onError(call.toString());

            }
        });

        return call;

       /* final Call<List<Repo>> repos = service.listRepos("glasslake");
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.e("dd","success,daxiao:"+response.body().size());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e("dd","fail,daxiao:");
            }
        });*/
    }


    private void parseNewJsonResponse(JSONObject response, final String urlTail, final String tag, final Map map, final MyNetListener myListener) {
        int code = response.optInt("code");
        Logger.json("code----------"+code);
        Logger.e(response.toString());
        // Logger.json(response.toString());

        switch (code){
            case 0://请求成功
                String str = response.optString("data");
                if (TextUtils.isEmpty(str) || "[]".equals(str) || "{}".equals(str)) {
                    myListener.onEmpty();

                } else {
                    myListener.onSuccess(response, str);
                }
                break;
            case 1://系统错误
                myListener.onError("code"+ code);
                break;
            case 5://未登录
                // myListener.onUnlogin();
                ApiTools.autoLogin(new MyNetListener() {
                    @Override
                    public void onSuccess(Object response, String optStr) {
                        postNewRequest(urlTail,tag,map,myListener);
                        MyToast.showDebugToast("重新登陆后，重新发送请求中。。。");
                    }
                });
                break;
            case 6://已删除
                myListener.onContentDeleted();
                break;
            case 2://表示没有申请过任何状态
                myListener.onNoneProposer("code"+ code);
                break;
            case 9://表示已经申请
                myListener.onRepeatPropser("code"+ code);
                break;
            default:
                myListener.onError(response.toString());
                break;
        }

    }


    public <T extends BaseNetBean> Call  postRequest2(final String urlTail, final Map<String,String> map, final MyNetListener<T> listener) {
        final Call<T > call = service.commonPost(urlTail,map);
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.code() == 200){
                    parseBaseBeanResponse(response.body(),urlTail,map,listener);
                }else {
                   listener.onError(response.code() +"--"+response.message());
                }

            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                listener.onError(t.toString();
            }
        });
    }

    private <T extends BaseNetBean> void parseBaseBeanResponse(T body, String urlTail, Map<String, String> map, MyNetListener<T> listener) {
        switch (body.code){
            case SeverCode.SUCCESS://请求成功
                listener.onSuccess(body.data,"");

                if (body.data != null && "[]".equals(body.data) || "{}".equals(str)) {
                    myListener.onEmpty();

                } else {
                    myListener.onSuccess(response, str);
                }
                break;
            case 0://系统错误
                myListener.onError("code"+ code);
                break;
            case 5://未登录
                // myListener.onUnlogin();
                ApiTools.autoLogin(new MyNetListener() {
                    @Override
                    public void onSuccess(Object response, String optStr) {
                        postNewRequest(urlTail,tag,map,myListener);
                        MyToast.showDebugToast("重新登陆后，重新发送请求中。。。");
                    }
                });
                break;
            case 6://已删除
                myListener.onContentDeleted();
                break;
            case 2://表示没有申请过任何状态
                myListener.onNoneProposer("code"+ code);
                break;
            case 9://表示已经申请
                myListener.onRepeatPropser("code"+ code);
                break;
            default:
                myListener.onError(response.toString());
                break;
        }
    }
}
