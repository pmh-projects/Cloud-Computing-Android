package com.example.projekt_bazaDanych;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerView_Config {

    private Context mContext;
    private CustomerAdapter mCustomerAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Customer> customers, List<String> keys){

        mContext =context;
        mCustomerAdapter = new CustomerAdapter(customers, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mCustomerAdapter);
    }

//    public abstract CustomerItemView onCreateViewHolder(ViewGroup parent, int viewType);
//
//    public abstract void onBindViewHolder(RecyclerView_Config holder, int position);
//
//    public abstract int getItemCount();

    class CustomerItemView extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mSurnname;
        private TextView mAddress;
        private TextView mPhone;
        private TextView mEmail;

        private String key;

        public CustomerItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.customerslist, parent, false));

            mName = (TextView) itemView.findViewById(R.id.customerNameText);
            mSurnname = (TextView) itemView.findViewById(R.id.customerNameText2);
            mEmail = (TextView) itemView.findViewById(R.id.emailText);
            mAddress = (TextView) itemView.findViewById(R.id.addressText);
            mPhone = (TextView) itemView.findViewById(R.id.phoneText);

        }

        public void bind(Customer customer, String key) {
            mName.setText(customer.getUniqueName());
            mSurnname.setText(customer.getNameSurname());
            mEmail.setText(customer.getEmail());
            mAddress.setText(customer.getAddress());
            mPhone.setText(customer.getPhone());
            this.key = key;
        }
    }


        class CustomerAdapter extends RecyclerView.Adapter<CustomerItemView> {
        private List<Customer> mCustomerList;
        private List<String> mKeys;

        public CustomerAdapter(List<Customer> mCustomerList, List<String> mKeys) {
            this.mCustomerList = mCustomerList;
            this.mKeys = mKeys;
        }

            @NonNull
            @Override
            public CustomerItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CustomerItemView(parent);
            }

            @Override
            public void onBindViewHolder(@NonNull CustomerItemView holder, int position) {
               holder.bind(mCustomerList.get(position), mKeys.get(position));
            }

            @Override
            public int getItemCount() {
                return mCustomerList.size();
            }
        }
}
