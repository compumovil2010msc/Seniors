package com.example.astromedics.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.astromedics.App;
import com.example.astromedics.R;
import com.example.astromedics.model.Person;
import com.example.astromedics.util.SharedPreferencesUtils;
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

public class Login extends AppCompatActivity implements View.OnClickListener, DialogLogin.NoticeDialogListener{

    private EditText username, password;

    private static final int GOOGLE_INTENT_CODE=2;
    private GoogleSignInClient mGoogleSignInClient ;
    private GoogleSignInOptions gso;
    private Person personToCreateGoolge;
    private final String LOG_TAG="LOGIN";
    private FirebaseAuth mAuth;
    private Button loginWithFirebase;
    private Button loginWithGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        this.gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        this.mGoogleSignInClient= GoogleSignIn.getClient(this,this.gso);

        username = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        this.loginWithFirebase=findViewById(R.id.login_button);
        this.loginWithGoogle=findViewById(R.id.login_button_google);
        this.loginWithFirebase.setOnClickListener(this);
        this.loginWithGoogle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_google:
                signInGoogle();
                break;
            case R.id.login_button:
                loginFirebase();
                break;
        }
    }

    private void signInGoogle(){
        Intent intent =this.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,GOOGLE_INTENT_CODE);
    }

    private void loginFirebase() {
        String username=this.username.getText().toString();
        String password=this.password.getText().toString();
        if(username.isEmpty()&&password.isEmpty()) {
            Toast.makeText(this,"Invalid fields",Toast.LENGTH_LONG).show();return;
        }
        if(username.isEmpty()){
            Toast.makeText(this,"Username required",Toast.LENGTH_LONG).show();
        }
        if(password.isEmpty()){
            Toast.makeText(this,"Password required",Toast.LENGTH_LONG).show();
        }
        this.mAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(LOG_TAG,"User logged in with firebase auth correctly");
                            FirebaseUser user= mAuth.getCurrentUser();
                            Log.d(LOG_TAG,"Email logged in: "+user.getEmail());
                            getUserFromDbAndRedirect(user.getEmail());
                        }else{
                            Toast.makeText(Login.this,"Authentication failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GOOGLE_INTENT_CODE:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            homologateLoginFirebase(GoogleAuthProvider.getCredential(account.getIdToken(),null));
        }catch (ApiException e){
            Log.w(LOG_TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void homologateLoginFirebase(AuthCredential credential) {
        this.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i(LOG_TAG,"Credentials homologated");
                            Call<Person> call= App.get().getUserService().getUser(mAuth.getCurrentUser().getEmail());
                            call.enqueue(new Callback<Person>() {
                                @Override
                                public void onResponse(Call<Person> call, Response<Person> response) {
                                    personToCreateGoolge=response.body();
                                    if(personToCreateGoolge==null){
                                        personToCreateGoolge=new Person(mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getDisplayName());
                                        showDialogDoctorAndCreateUser();
                                    }else{
                                        redirectAndPersistLocal(personToCreateGoolge);
                                    }
                                }
                                @Override
                                public void onFailure(Call<Person> call, Throwable t) {
                                    Log.e(LOG_TAG,"Error fetching user: "+t.getMessage());
                                }
                            });
                        }else{
                            Log.i(LOG_TAG,"Fail Homologatig creds");
                        }
                    }
                });
    }

    private void showDialogDoctorAndCreateUser() {
        DialogLogin login=new DialogLogin();
        login.show(getSupportFragmentManager(),"loginDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        personToCreateGoolge.setDoctor(true);
        insertUser();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        personToCreateGoolge.setDoctor(false);
        insertUser();
    }

    private void insertUser() {
        Call<Void>call=App.get().getUserService().saveUser(personToCreateGoolge);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i(LOG_TAG,"Insert successfull");
                redirectAndPersistLocal(personToCreateGoolge);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.i(LOG_TAG,"Insert failure");
            }
        });
    }

    private void getUserFromDbAndRedirect(String email) {
        Call<Person> call= App.get().getUserService().getUser(email);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if(response.body()!=null){
                    Log.i(LOG_TAG,"User found: "+response.body().toString());
                    Person p =response.body();
                    redirectAndPersistLocal(p);
                }
            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e(LOG_TAG, "error getting user: "+t.getMessage());
            }
        });
    }

    private void redirectAndPersistLocal(Person personFromDataBase) {
        SharedPreferencesUtils.persistPref("userLoggedIn",personFromDataBase,this);
        if(personFromDataBase.isDoctor()){
            Intent intent=new Intent(this,HomeTherapist.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(this,HomeUser.class);
            startActivity(intent);
        }
    }

}