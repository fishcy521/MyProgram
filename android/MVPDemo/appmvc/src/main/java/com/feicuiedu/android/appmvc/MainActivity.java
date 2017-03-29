package com.feicuiedu.android.appmvc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText password;
    private Button btnLogin;

    private IUserService userService;
    private View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String strUserName = userName.getText()+"";
            String strPassword = password.getText()+ "";

            userService.login(strUserName,strPassword,MainActivity.this);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(ocl);

        userService = new UserService();
    }
}
