package com.webconsole.harshmathur.multilock;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int RC_SIGN_IN = 0;
    FirebaseAuth auth;
    TextView name;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        name = (TextView) findViewById(R.id.user_name);
        email = (TextView) findViewById(R.id.user_email);

        //
        if(auth.getCurrentUser() != null){
            Log.d("AUTH",auth.getCurrentUser().getEmail());
            name.setText(auth.getCurrentUser().getDisplayName());
            email.setText(auth.getCurrentUser().getEmail());

        }else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setProviders(AuthUI.EMAIL_PROVIDER)
                    .build(),RC_SIGN_IN);
        }
        //findViewById(R.id.sign_out_button).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Log.d("AUTH", "User loggedin as : " + auth.getCurrentUser().getEmail());
                name.setText(auth.getCurrentUser().getDisplayName());
                email.setText(auth.getCurrentUser().getEmail());

            }
            else {
                Log.d("AUTH", "Not Authenticated");
            }
        }

    }

    public void ShowApp(View v){
        Intent i = new Intent(this, AppListActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                AuthUI.getInstance()
                        .signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("AUTH","User logged out");
                        finish();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
       // if(v.getId() == R.id.sign_out_button){

        //}
    }

}
