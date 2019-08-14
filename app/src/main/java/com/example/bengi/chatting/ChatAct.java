package com.example.bengi.chatting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAct extends AppCompatActivity {


     String username,othername;
     TextView chatusername;
     ImageView backimage,sendimage;
     EditText chatedittext;
     FirebaseDatabase firebasedatabase;
     DatabaseReference reference;
     RecyclerView chatrecycler;
     MesajAdapter mesajAdapter;
     List<MesajModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        tanimla();
        Mesajyukle();
    }

    public void tanimla(){

        list = new ArrayList<>();

       username = getIntent().getExtras().getString("username");
        othername =  getIntent().getExtras().getString("othername");

        Log.i("alınan değerler",username+ " -- "+othername);

        chatusername  = (TextView)findViewById(R.id.chatusername);
        backimage = (ImageView)findViewById(R.id.backimage);
        sendimage = (ImageView)findViewById(R.id.sendimage);
        chatedittext  =(EditText)findViewById(R.id.chatedittext);
        chatusername.setText(othername); //edittextte mesaj atıcağımız kişinin adı olsun

        backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatAct.this,MainActivity.class);
                intent.putExtra("kadi",username);//mainactivity bir kadi istiyor o olmadan mainactivitye giremeyiz
                //o yüğzden kadinı gönderiyoruz bizim username'mimizi
                startActivity(intent);
            }
        });

       firebasedatabase = FirebaseDatabase.getInstance();
       reference = firebasedatabase.getReference();
       sendimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String mesaj = chatedittext.getText().toString();
               chatedittext.setText("");//edittexti bosaltıyorum unutmaaaaaaaaaaa
               mesajgonder(mesaj);

           }
       });
       chatrecycler = (RecyclerView)findViewById(R.id.chatrecycler);
       RecyclerView.LayoutManager layoutmanager = new GridLayoutManager(ChatAct.this,1);
       chatrecycler.setLayoutManager(layoutmanager);
       mesajAdapter = new MesajAdapter(ChatAct.this, list,ChatAct.this,username);
       chatrecycler.setAdapter(mesajAdapter);


    }

    public void mesajgonder(String text){

        //her mesaj ayrı bir id ile database e kayıt olmalı

       final String key =  reference.child("Mesajlar").child(username).child(othername).push().getKey();

       final Map messagemap = new HashMap();
       messagemap.put("text",text); //message textimiz = parametreyele gelen mesaj oldu.
        messagemap.put("from",username); //kimden geldiğini belirtmemiz gerek
        reference.child("Mesajlar").child(username).child(othername).child(key).setValue(messagemap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    reference.child("Mesajlar").child(othername).child(username).setValue(messagemap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        //othername min altına username değerini yazmamızı sağlar
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });

                }
            }
        });


    }


    public  void Mesajyukle(){

        reference.child("Mesajlar").child(username).child(othername).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //bu fonksiyon herhn-angi güncellemede adapterinda güncellemesini sağlar

                MesajModel mesajmodel = dataSnapshot.getValue(MesajModel.class);

                list.add(mesajmodel);
                mesajAdapter.notifyDataSetChanged();
                chatrecycler.scrollToPosition(list.size()-1);//chatrecycler dolunca mesajla yukarı kaymasını sağlar otomatik



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
