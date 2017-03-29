package com.feicuiedu.android.mvpdemo.model;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface ILoginService {

    void loginSuccess(User user);
    void loginFailed();
}
