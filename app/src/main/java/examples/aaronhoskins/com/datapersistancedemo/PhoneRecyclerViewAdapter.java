package examples.aaronhoskins.com.datapersistancedemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.database.PhoneDatabaseHelper;
import examples.aaronhoskins.com.datapersistancedemo.model.phone.Phone;

public class PhoneRecyclerViewAdapter extends RecyclerView.Adapter<PhoneRecyclerViewAdapter.ViewHolder> {
    ArrayList<Phone> phonesList = new ArrayList<>();
    PhoneRecyclerViewCallback callback;
    public PhoneRecyclerViewAdapter(ArrayList<Phone> phonesList, PhoneRecyclerViewCallback callback) {
        this.phonesList = phonesList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.phone_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Phone currentPhone = phonesList.get(position);
        holder.tvBrand.setText(currentPhone.getPhoneBrand());
        holder.tvModel.setText(currentPhone.getPhoneModel());
        holder.tvId.setText(currentPhone.getId());
        holder.setPhone(currentPhone);
    }

    public void onDatabaseChange(ArrayList<Phone> updatedList) {
        phonesList = updatedList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return phonesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvBrand;
        TextView tvModel;
        TextView tvId;
        Phone phone;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBrand = itemView.findViewById(R.id.tvPhoneBrand);
            tvModel = itemView.findViewById(R.id.tvPhoneModel);
            tvId = itemView.findViewById(R.id.tvPhoneId);
            itemView.setOnClickListener(this);
        }
        public Phone getPhone() {
            return phone;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        @Override
        public void onClick(View view) {
            callback.onPhoneSelected(phone);
        }
    }

    interface PhoneRecyclerViewCallback{
        void onPhoneSelected(Phone phoneSelected);
    }
}
