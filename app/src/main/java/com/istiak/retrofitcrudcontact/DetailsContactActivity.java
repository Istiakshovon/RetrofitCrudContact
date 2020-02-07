package com.istiak.retrofitcrudcontact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

public class DetailsContactActivity extends AppCompatActivity {

    EditText etxtName,etxtContact,etxtAddress;
    Button btnUpdate;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_contact);

        etxtName = findViewById(R.id.etxt_name);
        etxtContact = findViewById(R.id.etxt_contact);
        etxtAddress = findViewById(R.id.etxt_address);

        btnUpdate = findViewById(R.id.button);

        final String id = getIntent().getExtras().getString("id");
        String name = getIntent().getExtras().getString("name");
        String contact = getIntent().getExtras().getString("contact");
        String address = getIntent().getExtras().getString("address");

        etxtName.setText(name);
        etxtContact.setText(contact);
        etxtAddress.setText(address);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact(id);
            }
        });
    }

    private void updateContact(String id){
        String name = etxtName.getText().toString();
        String contact = etxtContact.getText().toString();
        String address = etxtAddress.getText().toString();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.editUser(id, name, contact, address);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                Log.d("value",value);
                Log.d("msg",message);
                if (value.equals("1")){
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error! "+ t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",t.toString());
            }
        });
    }
}
