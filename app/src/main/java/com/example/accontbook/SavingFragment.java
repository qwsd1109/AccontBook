package com.example.accontbook;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        final Context context = container.getContext();
        final Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.sh);

        ed_com.addTextChangedListener(new CustomTextWathcer(ed_com));

        final EditText purpose = (EditText)view.findViewById(R.id.purpose);
        final TextView purpose_err = (TextView)view.findViewById(R.id.purpose_err);
        final int color = Color.parseColor("#0894A1");
        final int pcolor = Color.parseColor("#ff0000");
        ed_com.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        purpose.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        view.findViewById(R.id.button);
        view.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (purpose.getText().toString().length() == 0) {
                            tv_won_err.setText("");
                            purpose_err.setText("");
                            purpose_err.setText("목적을 입력해주세요");

                            purpose.getBackground().setColorFilter(pcolor, PorterDuff.Mode.SRC_ATOP);
                            purpose.getBackground().setColorFilter(pcolor, PorterDuff.Mode.SRC_ATOP);
                            purpose.startAnimation(animation);
                            purpose.invalidate();
                        } else {
                            if (ed_com.getText().toString().length() == 0) {
                                tv_won_err.setText("");
                                purpose_err.setText("");
                                tv_won_err.setText("금액을 입력해주세요");


                                purpose.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                                ed_com.getBackground().setColorFilter(pcolor, PorterDuff.Mode.SRC_ATOP);
                                ed_com.getBackground().setColorFilter(pcolor, PorterDuff.Mode.SRC_ATOP);
                                ed_com.startAnimation(animation);
                                ed_com.invalidate();
                            } else {
                                purpose.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                                ed_com.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                                tv_won_err.setText("");
                                purpose_err.setText("");
                                String text = purpose.getText().toString();
                                String num = ed_com.getText().toString();
                                String it = "수입";
                                dbHelper.con_insert(getTime, text, num, it);
                                ed_com.setText("");
                                purpose.setText("");
                                tv_won_err.setText("");
                                purpose_err.setText("");
                                Toast toast = Toast.makeText(context, "입력되었습니다.", Toast.LENGTH_LONG);
                            }
                        }
                    }
                });
        return view;
    }
}
