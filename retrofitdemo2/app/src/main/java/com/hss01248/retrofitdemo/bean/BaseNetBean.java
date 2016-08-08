package com.hss01248.retrofitdemo.bean;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public class BaseNetBean<T> {
    public boolean success;
    public int code;
    public String msg;
    public T data;
}
