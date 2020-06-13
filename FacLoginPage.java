package com.example.newtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FacLoginPage extends AppCompatActivity implements View.OnClickListener{
    FirebaseAuth mAuth;
    EditText facultyUN, facultyPass;
    ProgressBar pgb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fac_login_page);
        facultyUN = (EditText) findViewById(R.id.facultyUN);
        facultyPass = (EditText) findViewById(R.id.facultyPass);
        //progressBar = findViewById(R.id.progressbar);
        findViewById(R.id.textViewsignup).setOnClickListener(this);
        findViewById(R.id.FacultyEnter).setOnClickListener(this);
        pgb = findViewById(R.id.pgb);
        mAuth = FirebaseAuth.getInstance();
    }


    private void facultyLogin()
    {

        String mail = facultyUN.getText().toString().trim();
        String pass = facultyPass.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
        {
            facultyUN.setError("Please enter a valid Email");
            facultyUN.requestFocus();
            return;
        }

        if(mail.isEmpty())
        {
            facultyUN.setError("Email is required");
            facultyUN.requestFocus();
            return;
        }

        if(pass.isEmpty())
        {
            facultyPass.setError("Password is required");
            facultyPass.requestFocus();
            return;
        }

        if(pass.length()<6)
        {
            facultyPass.setError("Minimum password length should be 6");
            facultyPass.requestFocus();
            return;
        }

        pgb.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pgb.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(FacLoginPage.this, FacultyLogin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.textViewsignup:
                startActivity(new Intent(this, FacultySignUp.class));
                break;

            case R.id.FacultyEnter:
                facultyLogin();
                break;
        }

    }
}
