package edu.bluejack22_2.nitip.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.bluejack22_2.nitip.Facade.ActivityChanger;
import edu.bluejack22_2.nitip.R;
import edu.bluejack22_2.nitip.ViewModel.BillViewModel;

public class BillDetailActivity extends AppCompatActivity {

    BillViewModel billViewModel;
    private Button btnChooseFile;
    private Button btnChangeStatus;
    private Button btnReject;
    private Button btnAccept;
    private Button btnCancel;
    private TextView tvImageName;
    private TextView tvLender;
    private TextView tvDebtor;
    private TextView tvAmount;
    private TextView tvTrDate;
    private Uri proofImageUri = null;
    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        proofImageUri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DISPLAY_NAME};
                        Cursor cursor = getContentResolver().query(proofImageUri, filePathColumn, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            tvImageName.setText(cursor.getString(columnIndex));
                            cursor.close();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        init();
        setValue();
        setAuthorization();
        setListener();
    }

    private void init() {
        billViewModel = new BillViewModel();
        btnChooseFile = findViewById(R.id.btnChooseFile);
        btnChangeStatus = findViewById(R.id.btnChangeStatus);
        btnReject = findViewById(R.id.btnReject);
        btnAccept = findViewById(R.id.btnAccept);
        btnCancel = findViewById(R.id.btnCancel);
        tvImageName = findViewById(R.id.tvImageName);
        tvLender = findViewById(R.id.tvLender);
        tvDebtor = findViewById(R.id.tvDebtor);
        tvAmount = findViewById(R.id.tvAmount);
        tvTrDate = findViewById(R.id.tvTrDate);
    }

    private void setValue() {
        tvTrDate.setText("Date : " + getIntent().getExtras().get("Date").toString());
        tvLender.setText("Lender : " + getIntent().getExtras().get("Lender").toString());
        tvDebtor.setText("Debtor : " + getIntent().getExtras().get("Debtor").toString());
        tvAmount.setText("Amount : " + getIntent().getExtras().get("Amount").toString());
    }

    private void setListener() {
        btnChooseFile.setOnClickListener(e -> {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();

            } else {
                requestStoragePermission();
            }
        });

        btnChangeStatus.setOnClickListener(e -> {
            if (proofImageUri == null) {
                Toast.makeText(this, "Send your proof!", Toast.LENGTH_SHORT).show();
            }
            else {
                billViewModel.changeBillStatus(getIntent().getExtras().get("Id").toString());
                ActivityChanger.changeActivity(this, HomeActivity.class);
            }
        });

        btnReject.setOnClickListener(e -> {
            billViewModel.rejectBill(getIntent().getExtras().get("Id").toString());
            ActivityChanger.changeActivity(this, HomeActivity.class);
        });

        btnAccept.setOnClickListener(e -> {
            billViewModel.acceptBill(getIntent().getExtras().get("Id").toString());
            ActivityChanger.changeActivity(this, HomeActivity.class);
        });

        btnCancel.setOnClickListener(e -> {
            billViewModel.cancelBill(getIntent().getExtras().get("Id").toString());
            ActivityChanger.changeActivity(this, HomeActivity.class);
        });

        tvImageName.setOnClickListener(e -> {
            if (proofImageUri != null) {
                openImage();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        someActivityResultLauncher.launch(intent);
    }
    private void requestStoragePermission() {

        new AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("You need to grant external storage permission to change profile picture")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(BillDetailActivity.this,
                                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    private void openImage() {
        // Create an Intent to open the image
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(proofImageUri, "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        PackageManager packageManager = getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No app available to open the image", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAuthorization() {
        if (getIntent().getExtras().get("Lender").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            btnReject.setVisibility(View.GONE);
            btnChangeStatus.setVisibility(View.GONE);
            btnChooseFile.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
            btnAccept.setVisibility(View.VISIBLE);
        }
        else {
            btnReject.setVisibility(View.VISIBLE);
            btnChangeStatus.setVisibility(View.VISIBLE);
            btnChooseFile.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
            btnAccept.setVisibility(View.GONE);
        }
    }

}