package edu.neu.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.neu.myapplication.MainActivity;
import edu.neu.myapplication.R;
import edu.neu.myapplication.ReplacerActivity;

import static edu.neu.myapplication.fragments.CreateAccountFragment.EMAIL_REGEX;

 public class LoginFragment extends Fragment {

     private static final int RC_SIGN_IN = 1;
     private EditText emailEt, passwordEt;
     private TextView signupTv, forgotPasswordTv;
     private Button signInBtn, googleSignInBtn;
     private ProgressBar progressBar;
     private FirebaseAuth auth;
     private GoogleSignInClient mGoogleSignInClient;


     public LoginFragment() {
         // Required empty public constructor
     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_login, container, false);
     }


     @Override
     public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);

         init(view);

         clickListener();
     }


     private void clickListener() {

         forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ((ReplacerActivity)getActivity()).setFragment(new ForgotPassword());
             }
         });

         signInBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String email = emailEt.getText().toString();
                 String password = passwordEt.getText().toString();

                 if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                     emailEt.setError("Invalid email!");
                     return;
                 }
                 if (password.isEmpty() || password.length() < 6) {
                     passwordEt.setError("Please input 6 digit valid password!");
                 }
                 progressBar.setVisibility(View.VISIBLE);

                 auth.signInWithEmailAndPassword(email, password)
                         .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {

                                     FirebaseUser user = auth.getCurrentUser();

                                     if (!user.isEmailVerified()) {
                                         Toast.makeText(getContext(), "Please verify your email", Toast.LENGTH_SHORT).show();
                                     }

                                     sendUserToMainActivity();
                                 } else {
                                     String exception = "Error: " + task.getException().getMessage();
                                     Toast.makeText(getContext(), exception, Toast.LENGTH_SHORT).show();
                                     progressBar.setVisibility(View.GONE);
                                 }
                             }
                         });
             }
         });

         googleSignInBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 signIn();
             }
         });
         signupTv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ((ReplacerActivity)getActivity()).setFragment(new CreateAccountFragment());
             }
         });

         signupTv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ((ReplacerActivity) getActivity()).setFragment(new CreateAccountFragment());
             }
         });
     }

     private void sendUserToMainActivity() {

         if (getActivity() == null) {
             return;
         }
         progressBar.setVisibility(View.GONE);
         startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
         getActivity().finish();
     }
     private void init(View view) {

         emailEt = view.findViewById(R.id.emailET);
         passwordEt = view.findViewById(R.id.passwordET);
         signInBtn = view.findViewById(R.id.signInBtn);
         googleSignInBtn = view.findViewById(R.id.googleSignInBtn);
         signupTv = view.findViewById(R.id.signUpTV);
         forgotPasswordTv = view.findViewById(R.id.forgotTV);
         progressBar = view.findViewById(R.id.progressBar);

         auth = FirebaseAuth.getInstance();

         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.default_web_client_id))
                 .requestEmail()
                 .build();

         mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
     }

     private void signIn() {
         Intent signInIntent = mGoogleSignInClient.getSignInIntent();
         startActivityForResult(signInIntent, RC_SIGN_IN);
     }

     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
         if (requestCode == RC_SIGN_IN) {
             Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
             try {
                 // Google Sign In was successful, authenticate with Firebase
                 GoogleSignInAccount account = task.getResult(ApiException.class);
                 assert account != null;
                 firebaseAuthWithGoogle(account.getIdToken());
             } catch (ApiException e) {
//                 // Google Sign In failed, update UI appropriately

                 e.printStackTrace();
             }
         }
     }

     private void firebaseAuthWithGoogle(String idToken) {
         AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
         auth.signInWithCredential(credential)
                 .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                             // Sign in success, update UI with the signed-in user's information
                             FirebaseUser user = auth.getCurrentUser();
                             updateUi(user);
                         } else {
                             // If sign in fails, display a message to the user.
                             Log.w("TAG", "signInWithCredential:failure", task.getException());
                         }

                         // ...
                     }
                 });
     }

     private void updateUi(FirebaseUser user) {

         GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

         Map<String, Object> map = new HashMap<>();
         map.put("email", account.getEmail());
         map.put("name", account.getDisplayName());
         map.put("profileImage", String.valueOf(account.getPhotoUrl()));
         map.put("uid", user.getUid());
         map.put("following", 0);
         map.put("followers", 0);
         map.put("status", "I love cooking!");
         map.put("level", 1);
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
                             sendUserToMainActivity();
                         } else {
                             progressBar.setVisibility(View.GONE);
                             Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
     }
 }
