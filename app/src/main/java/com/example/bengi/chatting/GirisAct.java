package com.example.bengi.chatting;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisAct extends AppCompatActivity {

    EditText ad;
    Button girisbuton;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        tanimla();
    }

        public void tanimla(){
          ad= (EditText)findViewById(R.id.ad);
          girisbuton =(Button)findViewById(R.id.girisbuton);
          database = FirebaseDatabase.getInstance();
          reference = database.getReference();
          girisbuton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  String username = ad.getText().toString(); //kullanıcıadını edit texte yazılan olarak kaydetmek için yaıt ol
                  //fonksiyonuna yolladık
                  ad.setText("");//yolladıktan sonra edittexti boşalttık
                  kayitol(username);
              }
          });

        }

        public void kayitol(final String kadi){

           reference.child("Kullanicilar").child(kadi).child("Kullanıcıadı").setValue(kadi).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()) {
                       Toast.makeText(getApplicationContext(), "Başarılı giriş ", Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(GirisAct.this,MainActivity.class);
                       intent.putExtra("kadi",kadi);
                       startActivity(intent);

                   }
               }
           });


        }
}
