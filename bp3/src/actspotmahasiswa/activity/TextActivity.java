package com.divakrishnam.actspotmahasiswa.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.divakrishnam.actspotmahasiswa.R;
import com.divakrishnam.actspotmahasiswa.api.APIClient;
import com.divakrishnam.actspotmahasiswa.api.APIService;
import com.divakrishnam.actspotmahasiswa.model.Login;
import com.divakrishnam.actspotmahasiswa.model.ResponseInfo;
import com.divakrishnam.actspotmahasiswa.util.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class TextActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_NAMA = "extra_nama";
    public static final String EXTRA_KONTAK = "extra_kontak";
    private TextView tvText;
    private EditText etText;
    private Button btnSave;
    private String user, nama, kontak;
    private SharedPrefManager pref;
    private APIService mApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        tvText = findViewById(R.id.tv_text);
        etText = findViewById(R.id.et_text);
        btnSave = findViewById(R.id.btn_savetext);
        pref = SharedPrefManager.getInstance(getApplicationContext());
        mApiInterface = APIClient.getClient().create(APIService.class);
        user = getIntent().getStringExtra(EXTRA_USER);
        nama = getIntent().getStringExtra(EXTRA_NAMA);
        kontak = getIntent().getStringExtra(EXTRA_KONTAK);
        if (user!=null){
            getSupportActionBar().setTitle("Change Username");
            tvText.setText("Username");
            etText.setText(user);
            etText.setSelection(user.length());
        }else if (nama!=null){
            getSupportActionBar().setTitle("Change Name");
            tvText.setText("Nama");
            etText.setText(nama);
            etText.setSelection(nama.length());
        }else if (kontak!=null){
            getSupportActionBar().setTitle("Change Contact");
            tvText.setText("Kontak");
            etText.setText(kontak);
            etText.setSelection(kontak.length());
        }
        btnSave.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view == btnSave){
            if (user!=null){
                saveUsername();
            }else if (nama!=null){
                saveNama();
            }else if (kontak!=null){
                saveKontak();
            }
        }
    }
    private void saveUsername(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String text = etText.getText().toString();
        String idMahasiswa = pref.getMahasiswaLogin().getIdMahasiswa();
        if (!text.isEmpty()){
            Call<ResponseInfo> call = mApiInterface.mahasiswaUbahUsername(idMahasiswa, text);
            call.enqueue(new Callback<ResponseInfo>() {
                @Override
                public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    if(response.body().getStatus().equals("200")) {
                        Login login = pref.getMahasiswaLogin();
                        login.setUsername(text);
                        pref.mahasiswaLogin(login);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<ResponseInfo> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error : "+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Field kosong", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
    private void saveNama(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String text = etText.getText().toString();
        String idMahasiswa = pref.getMahasiswaLogin().getIdMahasiswa();
        if (!text.isEmpty()){
            Call<ResponseInfo> call = mApiInterface.mahasiswaUbahNama(idMahasiswa, text);
            call.enqueue(new Callback<ResponseInfo>() {
                @Override
                public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    if(response.body().getStatus().equals("200")) {
                        Login login = pref.getMahasiswaLogin();
                        login.setNamaMahasiswa(text);
                        pref.mahasiswaLogin(login);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<ResponseInfo> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error : "+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Field kosong", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
    private void saveKontak(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Menyimpan data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String text = etText.getText().toString();
        String idMahasiswa = pref.getMahasiswaLogin().getIdMahasiswa();
        if (!text.isEmpty()){
            Call<ResponseInfo> call = mApiInterface.mahasiswaUbahKontak(idMahasiswa, text);
            call.enqueue(new Callback<ResponseInfo>() {
                @Override
                public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    if(response.body().getStatus().equals("200")) {
                        Login login = pref.getMahasiswaLogin();
                        login.setKontakMahasiswa(text);
                        pref.mahasiswaLogin(login);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<ResponseInfo> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error : "+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(getApplicationContext(), "Field kosong", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }
}