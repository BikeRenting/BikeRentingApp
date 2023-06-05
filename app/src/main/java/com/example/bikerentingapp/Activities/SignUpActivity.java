package com.example.bikerentingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bikerentingapp.Classes.DatabaseConnection;
import com.example.bikerentingapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SignUpActivity extends AppCompatActivity {
    private EditText username, e_mail, phone_number, password;

    private String pattern = "^[\\w\\.-]+@[\\w\\.-]+\\.[\\w]+$";
    String usernamePattern = "^[A-Za-z]\\w{5,29}$";

    private Pattern emailPattern;
    private Pattern nicknamePattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        username = findViewById(R.id.username);
        e_mail = findViewById(R.id.email);
        phone_number = findViewById(R.id.phonenumber);
        password = findViewById(R.id.password);
        emailPattern = Pattern.compile(pattern);
        nicknamePattern = Pattern.compile(usernamePattern);
    }

    public void registerAccount(View view){
        String mail = e_mail.getText().toString();
        String phone = phone_number.getText().toString();
        String passwd = password.getText().toString();
        String user = username.getText().toString();

        Matcher emailmatcher = emailPattern.matcher(mail);
        Matcher usernamematcher = nicknamePattern.matcher(user);


        if (mail.equals("") || phone.equals("") || passwd.equals("") || user.equals("")){
            Toast.makeText(this,"Uzupełnij wszystkie pola!",Toast.LENGTH_SHORT).show();
        } else if (phone.length() != 9) {
            Toast.makeText(this,"Number telefonu musi być 9 cyfrowy",Toast.LENGTH_SHORT).show();
        } else if (!emailmatcher.matches()) {
            Toast.makeText(this,"Niepoprawny adres e-mail!",Toast.LENGTH_SHORT).show();
        } else if (!usernamematcher.matches()) {
            Toast.makeText(this, "Niepoprawna nazwa użytkownika!", Toast.LENGTH_SHORT).show();
        } else if (DatabaseConnection.ifExist(user, mail, phone)) {
            Toast.makeText(this,"Konto o takich danych już istnieje!",Toast.LENGTH_SHORT).show();
        } else if(DatabaseConnection.createAccount(user, mail, phone, BCrypt.withDefaults().hashToString(4, passwd.toCharArray()))) {
            Toast.makeText(this,"Konto zostało utworzone!Zaloguj się!",Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this,"Coś poszło nie tak! ",Toast.LENGTH_SHORT).show();
        }
    }
}