package com.feicuiedu.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btn7;
    Button btn8;
    Button btnEqu;
    Button btnAdd;
    EditText editText;
    private View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button btn = (Button) v;

            String strEditText = editText.getText()+"";
            String currentStr = btn.getText()+"";

            if (btn.getId() == R.id.btnEqu) {
                editText.setText(yunSun(strEditText));
            }
            else {
                editText.setText(strEditText + currentStr);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btnEqu = (Button) findViewById(R.id.btnEqu);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        editText = (EditText) findViewById(R.id.editText);

        btn7.setOnClickListener(ocl);
        btn8.setOnClickListener(ocl);
        btnAdd.setOnClickListener(ocl);
        btnEqu.setOnClickListener(ocl);

    }

    String yunSun(String str1) {

        String result = "";
        // 判断输入的字符串中是否含有 + 号
        if (str1.contains("+")) {

            // 以 + 作为分隔符 将 字符串+号两边的字符串分割到一个字符串数组中
            String[] arrays = str1.split("\\+");

            // 分别字符串数组中的元素
            String strNumber1 = arrays[0];
            String strNumber2 = arrays[1];

            // 执行字符串转整数类型
            int number1 = Integer.valueOf(strNumber1);
            int number2 = Integer.valueOf(strNumber2);

            int number = number1 + number2;

            // 把运算结果的值转换成整数并赋值给输出变量
            result = String.valueOf(number);
        }

        if (str1.contains("-")) {
            String[] arrays = str1.split("\\-");

            String strNumber1 = arrays[0];
            String strNumber2 = arrays[1];

            int number1 = Integer.valueOf(strNumber1);
            int number2 = Integer.valueOf(strNumber2);

            int number = number1 - number2;

            result = String.valueOf(number);
        }

        if (str1.contains("*")) {
            String[] arrays = str1.split("\\*");

            String strNumber1 = arrays[0];
            String strNumber2 = arrays[1];

            int number1 = Integer.valueOf(strNumber1);
            int number2 = Integer.valueOf(strNumber2);

            int number = number1 * number2;

            result = String.valueOf(number);
        }

        if (str1.contains("/")) {
            String[] arrays = str1.split("\\/");

            String strNumber1 = arrays[0];
            String strNumber2 = arrays[1];

            double number1 = Double.valueOf(strNumber1);
            double number2 = Double.valueOf(strNumber2);

            double number = number1 / number2;

            result = String.valueOf(number);
        }

        return result;
    }
}
