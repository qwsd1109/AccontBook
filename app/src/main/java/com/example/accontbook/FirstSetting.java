package com.example.accontbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FirstSetting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_setting);

        final Intent intent = new Intent(this, MainActivity.class);
        final EditText ed_won=(EditText)findViewById(R.id.ed_won);
        final EditText ed_day=(EditText)findViewById(R.id.editText);
        final TextView tv_won_err=(TextView)findViewById(R.id.tv_won_err);
        final TextView tv_day_err = (TextView)findViewById(R.id.tv_day_err);

        final SQLite dbHelper = new SQLite(getApplicationContext());

        ed_won.addTextChangedListener(new CustomTextWathcer(ed_won));

        findViewById(R.id.button).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        tv_day_err.setText("");
                        tv_won_err.setText("");
                        if ( ed_won.getText().toString().length() == 0 ) {
                            tv_won_err.setText("금액을 입력해주세요");
                        }else if(Long.parseLong(ed_won.getText().toString().replace(",",""))< 10000)
                        {
                            tv_won_err.setText("만원이상의 값을 입력해주세요.");
                        }else{

                            if ( ed_day.getText().toString().length() == 0 ) {
                                tv_day_err.setText("날짜를 입력해주세요");
                            }else if(Integer.parseInt(ed_day.getText().toString())>29){
                                tv_day_err.setText("1~28일 사이의 날짜 를 입력해주세요");
                            }else{
                                SharedPreferences pref = getSharedPreferences("setting", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("key","10");
                                editor.commit();
                                Integer day=Integer.parseInt(ed_day.getText().toString());
                                String won=ed_won.getText().toString();
                                dbHelper.set_insert(won,day);

                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                }
        );
    }

}