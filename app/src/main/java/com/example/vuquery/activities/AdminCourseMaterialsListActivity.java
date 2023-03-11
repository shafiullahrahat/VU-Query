package com.example.vuquery.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vuquery.R;
/*import com.example.vuquery.adapters.PdfAdapterAdmin;*/
import com.example.vuquery.adapters.newAdapter;
import com.example.vuquery.classes.PdfModelClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminCourseMaterialsListActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    TextView course_name;
    ImageButton backButton;
    Button upload_files, select_pdf, upload_pdf;
    EditText pdfTitleEditText;
    RecyclerView pdfShowRecyclerView;

    String[] coursePdfNames;
    newAdapter adapter;

    private static final String TAG = "ADD_PDF_TAG";
    private static final int PDF_PICK_CODE = 1000;
    private Uri pdfUri = null;
    private String pdfTitle = "", courseName = "", courseId="";

    /*private ArrayList<PdfModelClass> pdfArrayList;
    private PdfAdapterAdmin pdfAdapterAdmin;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_course_materials_list);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        backButton = findViewById(R.id.back_button);
        upload_files = findViewById(R.id.upload_files_btn);

        Intent intent = getIntent();
        courseName = intent.getStringExtra("course name");
        courseId = intent.getStringExtra("course id");
        course_name = findViewById(R.id.course_names_textView);
        course_name.setText(courseName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*pdfArrayList = new ArrayList<>();
        LoadPdfList();*/

        pdfShowRecyclerView = findViewById(R.id.pdfList_recyclerView);
        if (courseId.equals("11")){
            coursePdfNames = getResources().getStringArray(R.array.EnglishFundamentals);
        }else if (courseId .equals("12")) {
            coursePdfNames = getResources().getStringArray(R.array.DifferentialandIntegralCalculus);
        }else if (courseId.equals("13")) {
            coursePdfNames = getResources().getStringArray(R.array.Basic_physics);
        }else if (courseId.equals("14")) {
            coursePdfNames = getResources().getStringArray(R.array.Basic_physics_lab);
        }else if (courseId.equals("15")) {
            coursePdfNames = getResources().getStringArray(R.array.ComputerFundamentals);
        }else if (courseId.equals("16")) {
            coursePdfNames = getResources().getStringArray(R.array.ComputerFundamentals);
        }
        adapter = new newAdapter(this, coursePdfNames, courseName);
        pdfShowRecyclerView.setAdapter(adapter);
        pdfShowRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    /*private void LoadPdfList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        databaseReference.orderByChild("Course Name").equalTo(courseName)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pdfArrayList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            PdfModelClass modelClass = ds.getValue(PdfModelClass.class);
                            pdfArrayList.add(modelClass);
                        }
                        pdfAdapterAdmin = new PdfAdapterAdmin(getApplicationContext(), pdfArrayList);
                        pdfShowRecyclerView.setAdapter(pdfAdapterAdmin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }*/

    public void pdf_upload_dialog_box_open(View view) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminCourseMaterialsListActivity.this);
        View customDialogBoxView = getLayoutInflater().inflate(R.layout.custom_dialog_box, null);

        select_pdf = customDialogBoxView.findViewById(R.id.select_pdf_btn);
        upload_pdf = customDialogBoxView.findViewById(R.id.upload_pdf_btn);

        alertDialog.setView(customDialogBoxView);
        final AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.setCanceledOnTouchOutside(false );

        select_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPickIntent();
            }
        });

        upload_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfTitleEditText = customDialogBoxView.findViewById(R.id.pdf_title_editText);
                validationOfData();
            }
        });
        alertDialog1.show();
    }

    private void validationOfData() {

        pdfTitle = pdfTitleEditText.getText().toString().trim();
        if (TextUtils.isEmpty(pdfTitle)){
            Toast.makeText(getApplicationContext(), "Pdf Name required", Toast.LENGTH_SHORT).show();
        }else if (pdfUri==null){
            Toast.makeText(getApplicationContext(),"Please select a file", Toast.LENGTH_SHORT).show();
        }else{
            uploadPdfToStorage();
        }

    }

    private void uploadPdfToStorage() {
        Log.d(TAG, "uploadPdfToStorage : uploading pdf to storage");
        progressDialog.setMessage("Uploading Pdf...");
        progressDialog.show();

        long timestamp = System.currentTimeMillis();

        String filePathAndName = "Books/" + timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(pdfUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess : PDF uploaded to storage..");
                        Log.d(TAG, "onSuccess : getting pdf Url..");

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());

                        String uploadedPdfUrl = ""+uriTask.getResult();
                        uploadPdfInfoToFirebaseDatabase(uploadedPdfUrl, timestamp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure : pdf file failed to upload due to "+e.getMessage());
                        Toast.makeText(getApplicationContext(), "PDf files failed to upload due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadPdfInfoToFirebaseDatabase(String uploadedPdfUrl, long timestamp) {
        Log.d(TAG, "uploadPdfToStorage : uploading pdf Info to firebase Database");
        progressDialog.setMessage("Uploading Pdf Information..");

        String uid = firebaseAuth.getUid();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Uid", ""+uid);
        hashMap.put("Id", ""+timestamp);
        hashMap.put("PdfName", ""+pdfTitle);
        hashMap.put("CourseName", ""+courseName);
        hashMap.put("CourseId", courseId);
        hashMap.put("URl", ""+uploadedPdfUrl);
        hashMap.put("TimeStamp", timestamp);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        databaseReference.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onSuccess : PDF uploaded Successfully..");
                        Toast.makeText(getApplicationContext(), "PDf Files Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure : pdf file failed to upload due to "+e.getMessage());
                        Toast.makeText(getApplicationContext(), "PDf files failed to upload due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void pdfPickIntent() {
        Log.d(TAG, "pdfPickIntent : starting pdf pick intent");

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Files"), PDF_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if (requestCode == PDF_PICK_CODE){
                Log.d(TAG, "onActivityResult : PDF picked");

                pdfUri = data.getData();
                Log.d(TAG, "onActivityResult : URI :" +pdfUri);
            }
        }else{
            Log.d(TAG, "onActivityResult : cancelled PDF picking");
            Toast.makeText(getApplicationContext(), "cancelled PDF picking", Toast.LENGTH_SHORT).show();
        }
    }
}