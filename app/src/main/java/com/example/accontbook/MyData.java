package com.example.accontbook;

class MyData {
    public String tv_it;
    public String tv_item;
    public String tv_money;
    public String tv_time;


    public  String gettv_it(){
        return  tv_it;
    }
    public  String settv_it(String tv_it){
        return this.tv_it =tv_it;
    }
    public  String gettv_item(){
        return tv_item;
    }
    public  String settv_item(String tv_item){
        return this.tv_item=tv_item;
    }
    public  String gettv_money(){
        return tv_money;
    }
    public  String settv_money(String tv_money){
        return this.tv_money=tv_money;
    }
    public  String gettv_time(){
        return tv_time;
    }
    public  String settv_time(String tv_time){
        return this.tv_time= tv_time;
    }
}