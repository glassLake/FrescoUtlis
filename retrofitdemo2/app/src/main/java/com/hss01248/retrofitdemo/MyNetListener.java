package com.hss01248.retrofitdemo;



/**
 * Created by Administrator on 2016/4/15 0015.
 */
public abstract class MyNetListener<T> {

    public    void onUnlogin(){}

    public  void onContentDeleted(){}


    /**
     * called when the request is success bug data is empty
     */
    public  void onEmpty(){}

    public void onPreExecute() {}

    /** Inform when {@link Request} execute is finish,
     * whatever success or error or cancel, this callback
     * method always invoke if request is done. */
    public void onFinish() {}

    /** Called when response success. */
    public abstract void onSuccess(T response,String optStr);

    /**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     */
    public void onError(String error) {}

    /** Inform when the {@link Request} is truly cancelled. */
    public void onCancel() {}

    /**
     * Inform When the {@link Request} cache non-exist or expired,
     * this callback method is opposite by the onUsedCache(),
     * means the http retrieving will happen soon.
     */
    public void onNetworking() {}

    /** Inform when the cache already use,
     * it means http networking won't execute. */
    public void onUsedCache() {}

    /** Inform when {@link Request} execute is going to retry. */
    public void onRetry() {}

    /**
     * 都是B作为单位
     */
    public void onProgressChange(long fileSize, long downloadedSize) {
    }


    public void onNoneProposer(String s) {
        onError(s);
    }

    public void onRepeatPropser(String s) {
        onError(s);
    }
}
