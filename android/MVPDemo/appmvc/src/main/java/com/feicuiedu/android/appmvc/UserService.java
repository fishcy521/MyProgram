package com.feicuiedu.android.appmvc;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/14.
 */

public class UserService implements IUserService {

    private IDatabaseHelper dh = new DatabaseHelper();

    @Override
    public void login(String userName, String password, Context context) {

        if (dh.verifyUserName(userName)) {
            User user = new User(userName,password);

            if (dh.verifyUser(user)){
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(context, "用户名密码错误", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(context, "用户名不存在", Toast.LENGTH_SHORT).show();
        }

    }
}
