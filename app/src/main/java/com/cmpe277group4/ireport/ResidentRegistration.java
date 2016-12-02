package com.cmpe277group4.ireport;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by 33843 on 12/1/2016.
 */
public class ResidentRegistration extends Fragment {

    private EditText emailText;
    private EditText passText;
    private Button registerButton;

    private ProgressDialog progressDialog;

    private final FactoryMethods factory = new FactoryMethods();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.resident_registration,container,false);
        emailText = (EditText) rootView.findViewById(R.id.residentemail);
        passText = (EditText) rootView.findViewById(R.id.residentpassword);
        registerButton = (Button) rootView.findViewById(R.id.residentregister);

        progressDialog = new ProgressDialog(getActivity());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailText.getText().toString().trim();
                String password = passText.getText().toString().trim();

                if(factory.isStringEmpty(email)){
                    Toast.makeText(getContext(),factory.NULL_EMAIL,Toast.LENGTH_SHORT).show();
                    return;
                }

                if(factory.isStringEmpty(password)){
                    Toast.makeText(getContext(),factory.NULL_PASSWORD,Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Registering resident");

                progressDialog.show();
                FirebaseActivity firebase = new FirebaseActivity();
                firebase.registerResidentUser(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isComplete()){
                                        if(task.isSuccessful()){
                                            Toast.makeText(getContext(),"User registered",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getContext(),"Unable to register",Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(getContext(),"Not Complete",Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.hide();
                                }
                            });
                }
        });
        return rootView;
    }
}
