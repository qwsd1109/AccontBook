package com.example.accontbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsumptionFrament extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_consumption, container, false);
        long now = System.currentTimeMillis();
        // 출력될 포맷 설정
        long now_1 = System.currentTimeMillis();
        Date date = new Date(now_1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        final Context context = container.getContext();
        final String getTime = sdf.format(date);
        final SQLite dbHelper = new SQLite(getContext().getApplicationContext());
        final EditText ed_com = (EditText)view.findViewById(R.id.ed_con) ;
        final TextView tv_won_err=(TextView)view.findViewById(R.id.tv_won__err);

        final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.sh);


        ed_com.addTextChangedListener(new CustomTextWathcer(ed_com));

//        final Spinner spinner_field = (Spinner)view.findViewById(R.id.spinner);
//        //1번에서 생성한 field.xml의 item을 String 배열로 가져오기
//        String[] str={"  식비","  교통비","  주거비","  관리비","  수도비","  전기세","  문화생활","  충동구매","  기타"};
//        //2번에서 생성한 spinner_item.xml과 str을 인자로 어댑터 생성.
//        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(),R.layout.spinner_item,str);
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinner_field.setAdapter(adapter);
//        //spinner 이벤트 리스너
//        spinner_field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                ((TextView) adapterView.getChildAt(0)).setTextSize(20);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
        final EditText purpose = (EditText)view.findViewById(R.id.purpose);
        final TextView purpose_err = (TextView)view.findViewById(R.id.purpose_err);
        final int color = Color.parseColor("#47C297");
        final int pcolor = Color.parseColor("#ff0000");
        ed_com.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        purpose.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        view.findViewById(R.id.co_button).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if ( purpose.getText().toString().length() == 0 ) {
                            tv_won_err.setText("");
                            purpose_err.setText("");
                            purpose_err.setText("목적을 입력해주세요");

                            purpose.getBackground().setColorFilter(pcolor, PorterDuff.Mode.SRC_ATOP);
                            purpose.getBackground().setColorFilter(pcolor, PorterDuff.Mode.SRC_ATOP);
                            purpose.startAnimation(animation);
                            purpose.invalidate();
                        }else {
                            if ( ed_com.getText().toString().length() == 0 ) {
                                tv_won_err.setText("");
                                purpose_err.setText("");

                                purpose.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                                ed_com.getBackground().setColorFilter(pcolor, PorterDuff.Mode.SRC_ATOP);
                                ed_com.getBackground().setColorFilter(pcolor, PorterDuff.Mode.SRC_ATOP);
                                ed_com.startAnimation(animation);
                                ed_com.invalidate();
                            tv_won_err.setText("금액을 입력해주세요");
                            }else {
                                purpose.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                                ed_com.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                                tv_won_err.setText("");
                                purpose_err.setText("");
                                String text =purpose.getText().toString();
                                String num =ed_com.getText().toString();
                                String it = "지출";
                                dbHelper.con_insert(getTime,text,num,it);
                                ed_com.setText("");
                                purpose.setText("");
                                tv_won_err.setText("");
                                purpose_err.setText("");
                                Toast.makeText(context,"입력이 완료되었습니다.",Toast.LENGTH_LONG).show();
                        }
                        }
                    }
                });
        return view;
    }
}
