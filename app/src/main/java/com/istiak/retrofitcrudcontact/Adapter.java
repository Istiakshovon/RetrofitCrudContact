package com.istiak.retrofitcrudcontact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.istiak.retrofitcrudcontact.model.Contacts;
import com.istiak.retrofitcrudcontact.remote.ApiClient;
import com.istiak.retrofitcrudcontact.remote.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Contacts> contacts;
    Context context;
    private ApiInterface apiInterface;

    public Adapter(Context context, List<Contacts> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(contacts.get(position).getName());
        holder.contact.setText(contacts.get(position).getContact());
        holder.address.setText(contacts.get(position).getAddress());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteData(Integer.parseInt(contacts.get(position).getId()));
                return false;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailsContactActivity.class);
                intent.putExtra("id",contacts.get(position).getId());
                intent.putExtra("name",contacts.get(position).getName());
                intent.putExtra("contact",contacts.get(position).getContact());
                intent.putExtra("address",contacts.get(position).getAddress());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, contact, address;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name);
            contact = itemView.findViewById(R.id.txt_contact);
            address = itemView.findViewById(R.id.txt_address);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }


    public void deleteData(int id){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Contacts> call = apiInterface.deleteUser(id);
        call.enqueue(new Callback<Contacts>() {
            @Override
            public void onResponse(Call<Contacts> call, Response<Contacts> response) {
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("1")){
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Contacts> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }

        });
}
}