package com.example.managestore.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.managestore.R;
import com.example.managestore.dao.UserDAO;
import com.example.managestore.models.User;

public class ProfileFragment extends Fragment {
    EditText username, password, accountName, avatar, address, phone;
    Button updateButton;
    UserDAO userDAO;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        accountName = view.findViewById(R.id.accountName);
        avatar = view.findViewById(R.id.avatar);
        address = view.findViewById(R.id.address);
        phone = view.findViewById(R.id.phone);
        updateButton = view.findViewById(R.id.updateButton);

        userDAO = new UserDAO(getContext());
        userDAO.open();

        sharedPreferences = getActivity().getSharedPreferences("userSession", getContext().MODE_PRIVATE);
        String usernameStr = sharedPreferences.getString("username", "");

        // Load user data from database
        User user = userDAO.getUserByUsername(usernameStr);
        if (user != null) {
            username.setText(user.getUserName());
            password.setText(user.getPassword());
            accountName.setText(user.getAccountName());
            avatar.setText(user.getAvatar());
            address.setText(user.getAddress());
            phone.setText(user.getPhone());
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameStr = username.getText().toString();
                String passwordStr = password.getText().toString();
                String accountNameStr = accountName.getText().toString();
                String avatarStr = avatar.getText().toString();
                String addressStr = address.getText().toString();
                String phoneStr = phone.getText().toString();

                if (usernameStr.isEmpty() || accountNameStr.isEmpty() || avatarStr.isEmpty() || addressStr.isEmpty() || phoneStr.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!phoneStr.matches("\\d{10}")) {
                    Toast.makeText(getContext(), "Phone number must be 10 digits!", Toast.LENGTH_SHORT).show();
                    return;
                }

                User updatedUser = new User(usernameStr, passwordStr, accountNameStr, avatarStr, addressStr, phoneStr, true, 1);

                boolean success = userDAO.updateUser(updatedUser);
                if (success) {
                    Toast.makeText(getContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Profile Update Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}