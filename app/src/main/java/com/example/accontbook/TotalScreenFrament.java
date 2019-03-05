package com.example.accontbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.GridLayout.VERTICAL;

public class TotalScreenFrament extends Fragment {

    private RecyclerView recyclerView;
    private  SQLite sqLite;
    private RecyclerView.LayoutManager layoutManager;
    private  ArrayList<MyData>mydata = new ArrayList<MyData>();
    private MyAdapter myAdapter;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_screen, container, false);

        FitChart fitChart = (FitChart) view.findViewById(R.id.Chart);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        long val;
        float fl_tot;
        long fl_tot_2;
        float fl_tot_3;
        float fl_num;
        String num;

        final SQLite dbHelper = new SQLite(getContext().getApplicationContext());
        //final SQLite_set db = new SQLite_set(getContext().getApplicationContext());
        fl_tot = Float.parseFloat(dbHelper.set_getResult().replace(",",""));        //월급
        val=dbHelper.con_getResult();                           //지출
        fl_tot_2 = dbHelper.co_getResult();                     //수입
        fl_tot_3 = fl_tot_2+fl_tot;
        TextView textView = (TextView)view.findViewById(R.id.tv_won);
        fl_num = fl_tot_3 - val;
        DecimalFormat dc = new DecimalFormat("###,###,###,###,###,###,###,###");
        num = dc.format(fl_num);
        textView.setText(num + "원");
        fitChart.setMinValue(0f);
        fitChart.setMaxValue(fl_tot_3);
        fitChart.setValue(val);

        long now = System.currentTimeMillis();
        Date data = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String getTime = sdf.format(data);

        SharedPreferences pref = getActivity().getSharedPreferences("setting", MODE_PRIVATE);
        if(pref.getString("key_day", "").equals("20")){

        }
        else if(Integer.parseInt(getTime) == dbHelper.setday_getResult()){
            dbHelper.de();
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key_day","20");
            editor.commit();

        }
        if(Integer.parseInt(getTime) != dbHelper.setday_getResult()){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("key_day","40");
            editor.commit();
        }

        //loadDatabase();

        mydata.addAll(dbHelper.con_print());
        myAdapter = new MyAdapter(this.getActivity(),mydata);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration decoration = new DividerItemDecoration(this.getActivity(), VERTICAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(myAdapter);

//
//        myDataset = new ArrayList<>();
//        userAdapter = new MyAdapter(myDataset);
//        recyclerView.setAdapter(userAdapter);


        return view;
    }
    public  void loadDatabase(){
        sqLite = new SQLite(getActivity());
        sqLite.checkAndCopyData();
        sqLite.openDatabase();
        cursor=sqLite.QUeryData("select * from MONEYBOOK");
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    MyData myData = new MyData();
                    myData.settv_it(cursor.getString(3));
                    myData.settv_item(cursor.getString(1));
                    myData.settv_money(cursor.getString(2));
                    myData.settv_time(cursor.getString(4));
                    mydata.add(myData);
                }while(cursor.moveToNext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                myAdapter = new MyAdapter(getActivity(),mydata);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(myAdapter);
            }
        }
    }
}


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MyData> mDataset  = Collections.emptyList();
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_it;
        public TextView tv_item;
        public TextView tv_money;
        public TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_it  = (TextView) itemView.findViewById(R.id.tv_it);
            tv_item  = (TextView) itemView.findViewById(R.id.tv_item);
            tv_money  = (TextView) itemView.findViewById(R.id.tv_money);
            tv_time  = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
    public MyAdapter(Context context, List<MyData> myData){
        this.mDataset = myData;
        this.context = context;
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final  MyData myData = mDataset.get(position);
        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        holder.mTextView.setText(mDataset.get(position).text);
////        holder.mImageView.setImageResource(mDataset.get(position).img);
//        holder.tv_it.setText(mDataset.get(position).tv_it);
//        holder.tv_item.setText(mDataset.get(position).tv_item);
//        holder.tv_money.setText(mDataset.get(position).tv_money);
//        holder.tv_time.setText(mDataset.get(position).tv_time);
        holder.tv_it.setText(myData.gettv_it());
        holder.tv_item.setText(myData.gettv_item());
        holder.tv_money.setText(myData.gettv_money());
        holder.tv_time.setText(myData.gettv_time());
    }

    @Override
    public int getItemCount() {
        return this.mDataset.size();
    }
}



