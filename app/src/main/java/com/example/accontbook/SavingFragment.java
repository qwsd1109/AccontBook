package com.example.accontbook;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_saving, container, false);
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
        final String getTime = sdf.format(date);
        final SQLite dbHelper = new SQLite(getContext().getApplicationContext());
        final EditText ed_com = (EditText)view.findViewById(R.id.ed_con) ;
        final TextView tv_won_err=(TextView)view.findViewById(R.id.tv_won__err);


        ed_com.addTextChangedListener(new CustomTextWathcer(ed_com));

        final EditText purpose = (EditText)view.findViewById(R.id.purpose);
        final TextView purpose_err = (TextView)view.findViewById(R.id.purpose_err);
        int color = Color.parseColor("#0894A1");
        ed_com.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        purpose.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        view.findViewById(R.id.button).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if ( purpose.getText().toString().length() == 0 ) {
                            purpose_err.setText("목적을 입력해주세요");
                        }else {
                            if ( ed_com.getText().toString().length() == 0 ) {
                                tv_won_err.setText("금액을 입력해주세요");
                            }else {
                                String text =purpose.getText().toString();
                                String num =ed_com.getText().toString();
                                String it = "수입";
                                dbHelper.con_insert(getTime,text,num,it);
                                ed_com.setText("");
                                purpose.setText("");
                                tv_won_err.setText("");
                                purpose_err.setText("");
                            }
                        }
                    }
                });
        return view;
    }
}
