package com.example.bengi.chatting;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<String> list;
    String username;
    RecyclerView userecy;
    Adapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tanimla();
        listeleme();
    }

    public void tanimla(){
        username  =getIntent().getExtras().getString("kadi");
         firebaseDatabase = FirebaseDatabase.getInstance();
         reference = firebaseDatabase.getReference();
         list =  new ArrayList<>(); //list olusturduk ama eger bu sekilde bellekte yer olusturmasak hata alırız
        //yani tanımlamıs olduk

        userecy = (RecyclerView)findViewById(R.id.userecy) ; //recyclerviewin id si userrecy orada listeleme yapmak istiyoruz
        RecyclerView.LayoutManager layoutmanager = new GridLayoutManager(MainActivity.this,2);
        //bir satırda ikş kişininlistelenmesini istiyorum gridlayoutla o yüzden 2
        userecy.setLayoutManager(layoutmanager);
        //görevi yukarıda belirtip burada set ettik olmazsa çalısmaz set etmeyi unutma
        adapter = new Adapter(MainActivity.this,list,MainActivity.this,username);//constructor olusturdum adapter çalıssın diye
        //bu sayede de listi adaptera göndericek ve oradaki size kadar dönücek

        userecy.setAdapter(adapter);//????????????????????????????????????????????????????????????

    }

    public  void listeleme(){

        reference.child("Kullanicilar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals(username)) { //çetleşmek istediğimiz kişileri listelerken kendimizi görmemiz saçma olucagından bu şartı koyduk

                    list.add(dataSnapshot.getKey());
                    Log.i("Kullanicilar",dataSnapshot.getKey());//databasedeki keylerin logcatte listelenmesini sağşlar
                    adapter.notifyDataSetChanged(); //liste güncellendikçe adapterda güncellesin diye ekledik
                }
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
