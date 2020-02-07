package com.istiak.retrofitcrudcontact;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.istiak.retrofitcrudcontact.model.Contacts;
import com.istiak.retrofitcrudcontact.remote.ApiClient;
import com.istiak.retrofitcrudcontact.remote.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText etxtUserName,etxtContact,etxtPassword;
    Button btnSignUp;
    String username,contact,password;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etxtUserName = findViewById(R.id.etxt_username);
        etxtContact = findViewById(R.id.etxt_contact);
        etxtPassword = findViewById(R.id.etxt_password);
        btnSignUp = findViewById(R.id.btn_registration);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etxtUserName.getText().toString();
                contact = etxtContact.getText().toString();
                password = etxtPassword.getText().toString();
                if(username.isEmpty()){
                    etxtUserName.setError("Please enter your username");
                    etxtUserName.requestFocus();
                }else if(contact.isEmpty()){
                    etxtContact.setError("Please enter your username");
                    etxtContact.requestFocus();
                }else if (password.isEmpty()){
                    etxtPassword.setError("Please enter your username");
                    etxtPassword.requestFocus();
                }else {
                    signUp(username, contact, password);
                }
            }
        });
    }

    private void signUp(String username,String contact,String password){
        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.signUp(username, contact, password);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

                else if (value.equals("exists"))
                {
                    loading.dismiss();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();


                }


                else {
                    loading.dismiss();
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {

                loading.dismiss();
                Toast.makeText(SignUpActivity.this, "Error! " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}