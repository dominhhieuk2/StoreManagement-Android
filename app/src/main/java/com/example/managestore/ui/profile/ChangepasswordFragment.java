package com.example.managestore.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.managestore.R;
import com.example.managestore.dao.UserDAO;

public class ChangepasswordFragment extends Fragment {

    private EditText currentPassword;
    private EditText newPassword;
    private EditText confirmNewPassword;
    private Button changePasswordButton;
    private UserDAO userDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_changepassword, container, false);

        currentPassword = view.findViewById(R.id.currentPassword);
        newPassword = view.findViewById(R.id.newPassword);
        confirmNewPassword = view.findViewById(R.id.confirmNewPassword);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);

        userDAO = new UserDAO(getActivity());
        userDAO.open();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userSession", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPasswordStr = currentPassword.getText().toString();
                String newPasswordStr = newPassword.getText().toString();
                String confirmNewPasswordStr = confirmNewPassword.getText().toString();

                if (currentPasswordStr.isEmpty() || newPasswordStr.isEmpty() || confirmNewPasswordStr.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPasswordStr.equals(confirmNewPasswordStr)) {
                    Toast.makeText(getActivity(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userDAO.checkUser(username, currentPasswordStr)) {
                    boolean isUpdated = userDAO.updatePassword(username, newPasswordStr);
                    if (isUpdated) {
                        Toast.makeText(getActivity(), "Password changed successfully!", Toast.LENGTH_SHORT).show();
                        currentPassword.setText("");
                        newPassword.setText("");
                        confirmNewPassword.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Password change failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Current password is incorrect!", Toast.LENGTH_SHORT).show();
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
