package com.example.managestore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private UserDAO userDAO;
    private EditText editTextUserId, editTextUserName, editTextPassword, editTextAccountName, editTextAvatar, editTextAddress, editTextPhone, editTextUserStatus, editTextRoleId;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        userDAO = new UserDAO(this);
        userDAO.open();

        editTextUserId = findViewById(R.id.editTextUserId);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAccountName = findViewById(R.id.editTextAccountName);
        editTextAvatar = findViewById(R.id.editTextAvatar);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextUserStatus = findViewById(R.id.editTextUserStatus);
        editTextRoleId = findViewById(R.id.editTextRoleId);
        textViewResult = findViewById(R.id.textViewResult);

        Button buttonCreate = findViewById(R.id.buttonCreate);
        Button buttonRead = findViewById(R.id.buttonRead);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        Button buttonDelete = findViewById(R.id.buttonDelete);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUser();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }

    private void createUser() {
        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        String accountName = editTextAccountName.getText().toString();
        String avatar = editTextAvatar.getText().toString();
        String address = editTextAddress.getText().toString();
        String phone = editTextPhone.getText().toString();
        boolean userStatus = Integer.parseInt(editTextUserStatus.getText().toString()) == 1;
        int roleId = Integer.parseInt(editTextRoleId.getText().toString());

        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setAccountName(accountName);
        newUser.setAvatar(avatar);
        newUser.setAddress(address);
        newUser.setPhone(phone);
        newUser.setUserStatus(userStatus);
        newUser.setRoleID(roleId);

        long userId = userDAO.createUser(newUser);
        textViewResult.setText("New user created with ID: " + userId);
    }

    private void readUser() {
        int userId = Integer.parseInt(editTextUserId.getText().toString());
        User user = userDAO.getUserById(userId);
        if (user != null) {
            textViewResult.setText("User ID: " + user.getUserID() + "\n"
                    + "User Name: " + user.getUserName() + "\n"
                    + "Password: " + user.getPassword() + "\n"
                    + "Account Name: " + user.getAccountName() + "\n"
                    + "Avatar: " + user.getAvatar() + "\n"
                    + "Address: " + user.getAddress() + "\n"
                    + "Phone: " + user.getPhone() + "\n"
                    + "User Status: " + (user.isUserStatus() ? "Active" : "Inactive") + "\n"
                    + "Role ID: " + user.getRoleID());
        } else {
            textViewResult.setText("User not found");
        }
    }

    private void updateUser() {
        int userId = Integer.parseInt(editTextUserId.getText().toString());
        String userName = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();
        String accountName = editTextAccountName.getText().toString();
        String avatar = editTextAvatar.getText().toString();
        String address = editTextAddress.getText().toString();
        String phone = editTextPhone.getText().toString();
        boolean userStatus = Integer.parseInt(editTextUserStatus.getText().toString()) == 1;
        int roleId = Integer.parseInt(editTextRoleId.getText().toString());

        User user = new User();
        user.setUserID(userId);
        user.setUserName(userName);
        user.setPassword(password);
        user.setAccountName(accountName);
        user.setAvatar(avatar);
        user.setAddress(address);
        user.setPhone(phone);
        user.setUserStatus(userStatus);
        user.setRoleID(roleId);

        int rowsAffected = userDAO.updateUser(user);
        if (rowsAffected > 0) {
            textViewResult.setText("User updated successfully");
        } else {
            textViewResult.setText("User update failed");
        }
    }

    private void deleteUser() {
        int userId = Integer.parseInt(editTextUserId.getText().toString());
        int rowsAffected = userDAO.deleteUser(userId);
        if (rowsAffected > 0) {
            textViewResult.setText("User deleted successfully");
        } else {
            textViewResult.setText("User deletion failed");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}