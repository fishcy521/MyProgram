package com.feicuiedu.android.mvpdemo.model;

/**
 * Created by Administrator on 2017/2/14.
 */

public class UserService implements IUserService {
    @Override
    public void login(String userName, String password, ILoginService loginService) {
        if (userName.equals("admin") && password.equals("password") ) {
            User user = new User(userName,password);
            loginService.loginSuccess(user);
        }
        else {
            loginService.loginFailed();
        }
    }
}
