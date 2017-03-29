package com.feicuiedu.android.jisuanqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button btn7;
    Button btn8;
    Button btn9;

    Button btn6;
    Button btn5;
    Button btn4;

    Button btnAdd;
    Button btnSub;
    Button btnMup;
    Button btnDiv;
    Button btnEqu;

    Button btnPoint;

    EditText editText;
    private View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button tmp = (Button) v;
            String currentStr = tmp.getText() + "";
            String lastStr = editText.getText() + "";

            String result = lastStr + currentStr;


            if (tmp.getId() == R.id.btnEqu) {
                result = yunSuan(lastStr);
            }

            editText.setText(result);

            if (tmp.getId() == R.id.btnPoint) {
                editText.setText("");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        btn6 = (Button) findViewById(R.id.btn6);
        btn5 = (Button) findViewById(R.id.btn5);
        btn4 = (Button) findViewById(R.id.btn4);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMup = (Button) findViewById(R.id.btnMup);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        btnPoint = (Button) findViewById(R.id.btnPoint);

        btnEqu = (Button) findViewById(R.id.btnEqu);
        editText = (EditText) findViewById(R.id.editText);

        btn7.setOnClickListener(ocl);
        btn8.setOnClickListener(ocl);
        btn9.setOnClickListener(ocl);

        btn6.setOnClickListener(ocl);
        btn5.setOnClickListener(ocl);
        btn4.setOnClickListener(ocl);

        btnAdd.setOnClickListener(ocl);
        btnSub.setOnClickListener(ocl);
        btnMup.setOnClickListener(ocl);
        btnDiv.setOnClickListener(ocl);
        btnEqu.setOnClickListener(ocl);

        btnPoint.setOnClickListener(ocl);
    }

    //7/8  15
    public String yunSuan(String str) {
        String sign = "";
        String strReuslt = null;

        if (str.contains("+")) {
            sign= "\\+";
            String[] numbers = str.split(sign);
            String strNumber1 = numbers[0];
            String strNumber2 = numbers[1];
            int number1 = Integer.valueOf(strNumber1);
            int number2 = Integer.valueOf(strNumber2);

            int intResult = number1 + number2;

            strReuslt = String.valueOf(intResult);
        }

        if (str.contains("-")) {
            sign= "\\-";
            String[] numbers = str.split(sign);
            String strNumber1 = numbers[0];
            String strNumber2 = numbers[1];
            int number1 = Integer.valueOf(strNumber1);
            int number2 = Integer.valueOf(strNumber2);

            int intResult = number1 - number2;

            strReuslt = String.valueOf(intResult);
        }

        if (str.contains("*")) {
            sign= "\\*";
            String[] numbers = str.split(sign);
            String strNumber1 = numbers[0];
            String strNumber2 = numbers[1];
            int number1 = Integer.valueOf(strNumber1);
            int number2 = Integer.valueOf(strNumber2);

            int intResult = number1 * number2;

            strReuslt = String.valueOf(intResult);
        }
        if (str.contains("/")) {
            sign= "\\/";
            String[] numbers = str.split(sign);
            String strNumber1 = numbers[0];
            String strNumber2 = numbers[1];
            int number1 = Integer.valueOf(strNumber1);
            int number2 = Integer.valueOf(strNumber2);

            int intResult = number1 / number2;

            strReuslt = String.valueOf(intResult);
        }


        return strReuslt;
    }
}
