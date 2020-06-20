package com.example.cyberwatchdog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import android.app.ProgressDialog;

public class ChatActivity extends AppCompatActivity {
    EditText msgTxt;
    Button submit;
    String u_name = "none";
    public Uri imguri;
    private int count =0;
    DatabaseReference myRef;
    private ProgressDialog progressDialog2;
    private String status;

    StorageReference strRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Intent n = getIntent();
        progressDialog2 = new ProgressDialog(this);
        Button choose = findViewById(R.id.upload);
        Button upload = findViewById(R.id.choose);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Intent nn = getIntent();
        u_name = nn.getStringExtra("name");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileUploader();
            }
        });




        u_name = n.getStringExtra("name");
        Toast.makeText(getApplicationContext(), "Welcome " + u_name+" !", Toast.LENGTH_SHORT).show();
        ListView listView = (ListView) findViewById(R.id.mobile_list);


        myRef = database.getReference("messages");

        submit = findViewById(R.id.submit);
        msgTxt = findViewById(R.id.msg_txt);








        myRef.push().setValue(System.currentTimeMillis()+"  "+u_name+"  JUST LOGGED IN!");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = msgTxt.getText().toString();
                if(msg.isEmpty() || msg.contains("\n")){
                    Toast.makeText(getApplicationContext(), "Can't send blank message", Toast.LENGTH_SHORT).show();
                    return;
                }

                myRef.push().setValue( u_name +" :  "+ msg  );
                msgTxt.setText("");
            }
        });

        loadMessages();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sendOnChannel2("New Message Received" , u_name+" You have a new message");
                loadMessages();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }






    private void loadMessages() {
        count++;
        if(count == 1){

            progressDialog2.setMessage("Loading Messages");
            progressDialog2.show();}
        final ArrayList<String> messageArray = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, messageArray);



        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    messageArray.add(ds.getValue(String.class));
                }

                adapter.notifyDataSetChanged();
                progressDialog2.hide();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void sendOnChannel2(String title , String message){


    }







    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users");
        userRef.push().setValue(10);
    }



    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent ,1);
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode, Intent data){
        super.onActivityResult(requestCode , resultCode , data);
        if(resultCode == RESULT_OK && data.getData() != null){

            imguri = data.getData();
        }
    }



    private String getExtention(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }
    private StorageReference Ref;
    private void FileUploader(){


        Ref = strRef.child(System.currentTimeMillis()+"....."+"Image");
        Toast.makeText(getApplicationContext(),"Image Uploaded successfully ",Toast.LENGTH_LONG).show();

        Ref.putFile(imguri);



    }


    private void getFile(){

        try {
            File localFile = File.createTempFile("images", "jpg");
            Ref.getFile(localFile);
            Toast.makeText(getApplicationContext(),"Image Uploaded successfully ",Toast.LENGTH_LONG).show();

        }
        catch (Exception e){
            System.out.println(e);
        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
