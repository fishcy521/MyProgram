package com.feicuiedu.android.mvpdemo.model;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface IUserService {

    void login(String userName, String password,ILoginService loginService);
}
