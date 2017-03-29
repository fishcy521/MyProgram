package com.feicuiedu.android.mvpdemo.view;

import com.feicuiedu.android.mvpdemo.model.User;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface ILoginView {

    String getUserName();
    String getPassword();

    void showSuccess(User user);
    void showFaileError();

}
