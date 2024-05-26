package com.example.managestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.User;

public class SignupActivity extends AppCompatActivity {
    EditText username;
    EditText password, confirmpassword;
    TextView loginText;
    Button signupButton;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        signupButton = findViewById(R.id.signupButton);
        loginText = (TextView) findViewById(R.id.loginText);
        userDAO = new UserDAO(this);
        userDAO.open();

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();
                String confirmPasswordStr = confirmpassword.getText().toString();

                if (usernameStr.isEmpty() || passwordStr.isEmpty() || confirmPasswordStr.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!passwordStr.equals(confirmPasswordStr)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                User newUser = new User(usernameStr, passwordStr, "AccountName", "Avatar", "Address", "Phone", true, 1);

                long userId = userDAO.createUser(newUser);
                if (userId != -1) {
                    Toast.makeText(SignupActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Signup Failed! Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}