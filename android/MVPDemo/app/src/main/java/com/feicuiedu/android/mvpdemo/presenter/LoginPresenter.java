package com.feicuiedu.android.mvpdemo.presenter;

import android.os.Handler;

import com.feicuiedu.android.mvpdemo.model.ILoginService;
import com.feicuiedu.android.mvpdemo.model.IUserService;
import com.feicuiedu.android.mvpdemo.model.User;
import com.feicuiedu.android.mvpdemo.model.UserService;
import com.feicuiedu.android.mvpdemo.view.ILoginView;

/**
 * Created by Administrator on 2017/2/14.
 */

public class LoginPresenter {

    private IUserService userService;
    private ILoginView loginView;

    private Handler handler = new Handler();

    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;
        this.userService = new UserService();
    }

    public void login() {
        userService.login(loginView.getUserName(), loginView.getPassword(), new ILoginService() {
            @Override
            public void loginSuccess(final User user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showSuccess(user);

                    }
                });
            }

            @Override
            public void loginFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginView.showFaileError();

                    }
                });
            }
        });
    }


}
