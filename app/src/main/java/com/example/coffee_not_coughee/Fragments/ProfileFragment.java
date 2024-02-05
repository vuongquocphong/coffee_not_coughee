package com.example.coffee_not_coughee.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.coffee_not_coughee.MainActivity;
import com.example.coffee_not_coughee.Model.UserModel;
import com.example.coffee_not_coughee.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileFragment extends Fragment {
    private MainActivity mainActivity;
    private UserModel currentUser;
    private EditText profileNameField;
    private EditText profilePhoneField;
    private EditText profileEmailField;
    private EditText profileAddressField;
    private ImageView profileNameEditButton;
    private ImageView profilePhoneEditButton;
    private ImageView profileEmailEditButton;
    private ImageView profileAddressEditButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
        }

        currentUser = mainActivity.getCurrentUser();

        profileNameField = view.findViewById(R.id.profileNameField);
        profilePhoneField = view.findViewById(R.id.profilePhoneField);
        profileEmailField = view.findViewById(R.id.profileEmailField);
        profileAddressField = view.findViewById(R.id.profileAddressField);
        profileNameEditButton = view.findViewById(R.id.profileNameEditButton);
        profilePhoneEditButton = view.findViewById(R.id.profilePhoneEditButton);
        profileEmailEditButton = view.findViewById(R.id.profileEmailEditButton);
        profileAddressEditButton = view.findViewById(R.id.profileAddressEditButton);

        profileNameField.setText(currentUser.full_name);
        profilePhoneField.setText(currentUser.phone);
        profileEmailField.setText(currentUser.email);
        profileAddressField.setText(currentUser.address);

        profileNameField.setEnabled(false);
        profilePhoneField.setEnabled(false);
        profileEmailField.setEnabled(false);
        profileAddressField.setEnabled(false);

        profileNameEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileNameField.setEnabled(!profileNameField.isEnabled());
            }
        });

        profilePhoneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profilePhoneField.isEnabled()) {
                    if(!checkValidPhone(profilePhoneField.getText().toString().trim())) {
                        profilePhoneField.setError("Invalid phone number!");
                        return;
                    }
                }
                profilePhoneField.setEnabled(!profilePhoneField.isEnabled());
            }
        });

        profileEmailEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileEmailField.isEnabled()) {
                    if(!checkValidEmail(profileEmailField.getText().toString().trim())) {
                        profileEmailField.setError("Invalid email!");
                        return;
                    }
                }
                profileEmailField.setEnabled(!profileEmailField.isEnabled());
            }
        });

        profileAddressEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileAddressField.setEnabled(!profileAddressField.isEnabled());
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private boolean checkValidPhone(String phone) {
        if (phone == null) {
            return false;
        }
        if (phone.charAt(0) != '0') {
            return false;
        }
        if (phone.length() != 10) {
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean checkValidEmail(String email) {
        if (email == null) {
            return false;
        }
        if (email.length() < 5) {
            return false;
        }
        if (email.charAt(0) == '@' || email.charAt(email.length() - 1) == '@') {
            return false;
        }
        if (email.charAt(0) == '.' || email.charAt(email.length() - 1) == '.') {
            return false;
        }
        if (email.contains("@.")) {
            return false;
        }
        if (email.contains(".@")) {
            return false;
        }
        if (email.contains("..")) {
            return false;
        }
        if (email.contains("@@")) {
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        // Update the current user data with the edited information
        currentUser.full_name = profileNameField.getText().toString().trim();
        currentUser.phone = profilePhoneField.getText().toString().trim();
        currentUser.email = profileEmailField.getText().toString().trim();
        currentUser.address = profileAddressField.getText().toString().trim();
        super.onDestroyView();
    }
}