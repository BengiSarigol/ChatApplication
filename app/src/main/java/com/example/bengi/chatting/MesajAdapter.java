package com.example.bengi.chatting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
//adapterlar listlerin view elemanlarında gösterilmesi için kullnaılır

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder> {

      //listeleme işlemi yaparken layoutta listeleriz. o yüzden bunları kullanıyoruz

    Context context; //layoutlara erişebilmek için context kullanılır
    List<MesajModel> list;
    Activity activity;
    String username;
    Boolean state;
    int view_send=1,view_received=2;



    public MesajAdapter(Context context, List<MesajModel> list, Activity activity, String username) {
         this.context=context;
         this.activity=activity;
        this.list = list;
        this.username=username;
        state = false;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view;
       if(viewType == view_send){

              view = LayoutInflater.from(context).inflate(R.layout.send_layout,parent,false);//layouta eriştik
        return new ViewHolder(view);
        //return sayesinde alttaki viewholder classsına bu viewi gönderdik
    }else{

           view = LayoutInflater.from(context).inflate(R.layout.received_layout,parent,false);//layouta eriştik
           return new ViewHolder(view);
       }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textview.setText(list.get(position).getText().toString());//sırası gelen listenin textviewinin set edilmesinisağlar
        //listenin text kısmının yazılmasınjı sağladık


    }

    @Override
    public int getItemCount() {
        return list.size();  //list size her seferinde kendi bir alta geçicek ozamanda üstteki classlar tekrar tetiklenicek
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textview ;


        public ViewHolder(View itemView) {
            super(itemView); //super ile üst classtan alıyoruz0
            if(state==true){

                textview = itemView.findViewById(R.id.sendtext);
            }
           else {
                textview = itemView.findViewById(R.id.receivedtext); //kullanılıcak itemler burada tanımlanır
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position).getFrom().equals(username)) { //kullanıcı yani username mesaj atonca olucaklar
            state = true;
            return  view_send;

        }
        else{
            state=false;
            return view_received;
        }
    }
}
