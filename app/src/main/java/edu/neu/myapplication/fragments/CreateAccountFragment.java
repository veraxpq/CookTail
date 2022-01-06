package edu.neu.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.neu.myapplication.LearningRecipeEntranceActivity;
import edu.neu.myapplication.LevelTestActivity;
import edu.neu.myapplication.R;
import edu.neu.myapplication.ReplacerActivity;

public class CreateAccountFragment extends Fragment {

    private EditText emailEt, nameEt, passwordEt, confirmPasswordEt;
    private TextView loginTv;
    private Button signUpBtn;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    public static final String EMAIL_REGEX =  "(.+)@(.+)$";

    //hardcode for leveltest page sitian
    private boolean tested = false;


    // TODO: Rename and change types and number of parameters
    public CreateAccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        clickListener();
    }

    private void init(View view) {

        emailEt = view.findViewById(R.id.emailET);
        passwordEt = view.findViewById(R.id.passwordET);
        confirmPasswordEt = view.findViewById(R.id.passwordRepeateET);
        loginTv = view.findViewById(R.id.loginTV);
        signUpBtn = view.findViewById(R.id.signUpBtn);
        progressBar = view.findViewById(R.id.progressBar);
        nameEt = view.findViewById(R.id.nameET);

        auth = FirebaseAuth.getInstance();

    }

    private void clickListener() {
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReplacerActivity) getActivity()).setFragment(new LoginFragment());
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String username = nameEt.getText().toString();
                String password = passwordEt.getText().toString();
                String confrimPassword = confirmPasswordEt.getText().toString();

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    emailEt.setError("please input valid email");
                    return;
                }

                if (username.isEmpty()) {
                    nameEt.setError("please input valid username");
                    return;
                }

                if (password.isEmpty() || password.length() < 6) {
                    passwordEt.setError("please input valid password");
                    return;
                }

                if (!password.equals(confrimPassword)) {
                    confirmPasswordEt.setError("Password not match");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                createAccount(email, password);
            }
        });
    }

    private void createAccount(String email, String password) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = auth.getCurrentUser();
                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Email verification link send", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    uploadUser(user, email, password);

                } else {
                    progressBar.setVisibility(View.GONE);
                    String exception = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error: " + exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadUser(FirebaseUser user, String email, String password) {

        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getDisplayName());
        map.put("email", email);
        map.put("profileImage", " ");
        map.put("uid", user.getUid());
        map.put("following", 0);
        map.put("followers", 0);
        map.put("status", "I love cooking!");
        map.put("level", 1);
        map.put("tested", false);
        map.put("recipes", 0);
        map.put("post", 0);
        map.put("learned",0);


        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
                .set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            assert getContext() != null;
                            progressBar.setVisibility(View.GONE);

                            //check user already test or not
                            if(!tested){
                                Intent intent = new Intent(getActivity(), LevelTestActivity.class);
                                startActivity(intent);
                            }else{
                                ((ReplacerActivity)getActivity()).setFragment(new LearningMainFragment());
                            }

//                            startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class));
//                            getActivity().finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}