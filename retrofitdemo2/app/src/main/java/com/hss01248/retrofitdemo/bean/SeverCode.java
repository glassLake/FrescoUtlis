package com.hss01248.retrofitdemo.bean;

/**
 * Created by Administrator on 2016/8/7.
 */
public interface SeverCode {
    int SUCCESS = 1;//获取成功
    int EMPTY = 2;//内容为空
    int UNLOGIN = 3;//没有登录
    int LOGIN_INVALID = 4;//登录已经失效,需要重新登录
    int CONTENT_UNFOUND = 5;//没有找到对应的内容,可能是key错了,也可能是该内容已经删除
    //其他待补充

}
