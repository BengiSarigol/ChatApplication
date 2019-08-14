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

import java.util.ArrayList;
import java.util.List;
//adapterlar listlerin view elemanlarında gösterilmesi için kullnaılır

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

      //listeleme işlemi yaparken layoutta listeleriz. o yüzden bunları kullanıyoruz

    Context context; //layoutlara erişebilmek için context kullanılır
    List<String> list;
    Activity activity;
    String username;


    public Adapter(Context context,List<String> list,Activity activity,String username) {
         this.context=context;
         this.activity=activity;
        this.list = list;
        this.username=username;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.adapter_layout,parent,false);//layouta eriştik
        return new ViewHolder(view);
        //return sayesinde alttaki viewholder classsına bu viewi gönderdik
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textview.setText(list.get(position).toString());//sırası gelen listenin textviewinin set edilmesinisağlar
        //listenin text kısmının yazılmasınjı sağladık

        holder.baslayout.setOnClickListener(new View.OnClickListener() { //tıklanma özelliğini xmlde önce açıyorum sonrada burada eger tıklanırsa diyorum

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ChatAct.class);
                intent.putExtra("username",username);
                intent.putExtra("othername",list.get(position).toString());//mesajlastıgımız kişinin adının alınması
                activity.startActivity(intent);//direk start activity yazamıyoruz çünkü activity nesnesinden extend edilmediği için



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();  //list size her seferinde kendi bir alta geçicek ozamanda üstteki classlar tekrar tetiklenicek
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textview ;
      LinearLayout baslayout;

        public ViewHolder(View itemView) {
            super(itemView); //super ile üst classtan alıyoruz0
            textview = itemView.findViewById(R.id.userName);
            baslayout = itemView.findViewById(R.id.baslayout); //kullanılıcak itemler burada tanımlanır

        }
    }
}
