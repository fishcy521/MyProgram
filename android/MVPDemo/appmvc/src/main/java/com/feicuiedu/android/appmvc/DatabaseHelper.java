package com.feicuiedu.android.appmvc;

/**
 * Created by Administrator on 2017/2/14.
 */

public class DatabaseHelper implements IDatabaseHelper {

    @Override
    public boolean verifyUserName(String userName) {

        /*
         *如果根据用户名 userName 从数据库查询 能够返回资料那么应该返回true,否则返回false
         */
        return true;
    }

    @Override
    public boolean verifyUser(User user) {

        /*
            如果根据user中的username 和password 查询数据库，能返回资料，说明用户存在，那么方法返回true
            否则返回false
         */
        return true;
    }
}
