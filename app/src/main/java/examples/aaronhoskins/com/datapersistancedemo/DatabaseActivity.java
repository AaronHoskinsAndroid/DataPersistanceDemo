package examples.aaronhoskins.com.datapersistancedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import examples.aaronhoskins.com.datapersistancedemo.model.datasource.local.database.PhoneDatabaseHelper;
import examples.aaronhoskins.com.datapersistancedemo.model.phone.Phone;

public class DatabaseActivity extends AppCompatActivity
        implements PhoneRecyclerViewAdapter.PhoneRecyclerViewCallback {
    RecyclerView rvPhoneList;
    PhoneRecyclerViewAdapter adapter;
    PhoneDatabaseHelper dbHelper;
    EditText etBrand;
    EditText etModel;
    EditText etSize;
    EditText etOsType;
    String selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        dbHelper = new PhoneDatabaseHelper(this);
        bindViews();
        initRecyclerView();
    }

    private void bindViews() {
        rvPhoneList = findViewById(R.id.rvPhoneList);
        etBrand = findViewById(R.id.etPhoneBrand);
        etModel = findViewById(R.id.etPhoneModel);
        etSize = findViewById(R.id.etPhoneSize);
        etOsType = findViewById(R.id.etPhoneOs);
    }

    private void populateView(Phone phone) {
        etBrand.setText(phone.getPhoneBrand());
        etModel.setText(phone.getPhoneModel());
        etSize.setText(phone.getPhoneSize());
        etOsType.setText(phone.getOsType());
        selectedId = phone.getId();
    }

    private void initRecyclerView() {
        ArrayList<Phone> phoneList = dbHelper.getAllPhones();
        adapter = new PhoneRecyclerViewAdapter(phoneList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPhoneList.setLayoutManager(layoutManager);
        rvPhoneList.setAdapter(adapter);
    }

    public void onDatabaseCrudClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddToDatabase:
                Phone phone = new Phone();
                final String brand = etBrand.getText().toString();
                final String model = etModel.getText().toString();
                final String size = etSize.getText().toString();
                final String os = etOsType.getText().toString();
                phone.setPhoneBrand(brand);
                phone.setPhoneModel(model);
                phone.setOsType(os);
                phone.setPhoneSize(size);
                dbHelper.insertPhoneIntoDB(phone);
                adapter.onDatabaseChange(dbHelper.getAllPhones());
                break;
            case R.id.btnDeleteFromData:
                dbHelper.deletePhoneInDB(selectedId);
                adapter.onDatabaseChange(dbHelper.getAllPhones());
                break;
            case R.id.btnFindPhone:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Enter Phone ID")
                        .setMessage("Please Enter Id of Phone")
                        .setView(taskEditText)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onPhoneSelected(dbHelper.getPhoneById(taskEditText.getText().toString()));
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                break;
            case R.id.btnUpdateDatabase:
                Phone phoneUpdate = new Phone();
                final String brandUpdate = etBrand.getText().toString();
                final String modelUpdate = etModel.getText().toString();
                final String sizeUpdate = etSize.getText().toString();
                final String osUpdate = etOsType.getText().toString();
                phoneUpdate.setPhoneBrand(brandUpdate);
                phoneUpdate.setPhoneModel(modelUpdate);
                phoneUpdate.setOsType(osUpdate);
                phoneUpdate.setPhoneSize(sizeUpdate);
                dbHelper.updatePhoneInDB(selectedId, phoneUpdate);
                adapter.onDatabaseChange(dbHelper.getAllPhones());
                break;
        }
    }

    @Override
    public void onPhoneSelected(Phone phoneSelected) {
        populateView(phoneSelected);
    }
}
