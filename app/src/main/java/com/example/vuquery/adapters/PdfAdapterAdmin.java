/*
package com.example.vuquery.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vuquery.classes.MyApplication;
import com.example.vuquery.classes.PdfModelClass;
import com.example.vuquery.databinding.RowPdfContainerBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PdfAdapterAdmin extends RecyclerView.Adapter<PdfAdapterAdmin.HolderPdfAdmin>{

    private Context context;
    private ArrayList<PdfModelClass> pdfArrayList;

    private RowPdfContainerBinding rowPdfContainerBinding;

    private static final String TAG = "PDF_ADAPTER_TAG";

    public PdfAdapterAdmin(Context context, ArrayList<PdfModelClass> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
    }

    @NonNull
    @Override
    public HolderPdfAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rowPdfContainerBinding = RowPdfContainerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderPdfAdmin(rowPdfContainerBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdfAdmin holder, int position) {
        PdfModelClass model = pdfArrayList.get(position);
        String pdfTitle = model.getPdfName();
        String courseName = model.getCourseName();
        long timestamp = model.getTimestamp();
        String formattedDate = MyApplication.formatTimeStamp(timestamp);

        holder.pdfTitleTextView.setText(pdfTitle);
        holder.pdfCourseNameTextView.setText(courseName);
        holder.pdfUploadDateTextView.setText(formattedDate);

        LoadCourseName(model, holder);
        LoadPdfFromUrl(model, holder);
        LoadPdfSize(model, holder);
    }

    private void LoadPdfFromUrl(PdfModelClass model, HolderPdfAdmin holder) {
        String pdfUrl = model.getUrl();
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        storageReference.getBytes(500000000)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Log.d(TAG, "onSuccess: "+model.getPdfName()+" Successfully got the file");

                        holder.pdfView.fromBytes(bytes)
                                .pages(0)
                                .swipeVertical(false)
                                .enableSwipe(false)
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {
                                        holder.pdfLoadingProgressBar.setVisibility(View.INVISIBLE);
                                        Log.d(TAG, "onError : "+t.getMessage());
                                    }
                                })
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        holder.pdfLoadingProgressBar.setVisibility(View.INVISIBLE);
                                    }
                                })
                                .load();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        holder.pdfLoadingProgressBar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "onFailure: Can't get the file from url due to "+e.getMessage());
                    }
                });
    }

    private void LoadCourseName(PdfModelClass model, HolderPdfAdmin holder) {

        String coursePdfs = ""+model.getTimestamp();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        databaseReference.child(coursePdfs)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String courseName = ""+snapshot.child("CourseName").getValue();
                        holder.pdfCourseNameTextView.setText(courseName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void LoadPdfSize(PdfModelClass model, HolderPdfAdmin holder) {
        String pdfUrl = model.getUrl();
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl);
        storageReference.getMetadata()
                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        double bytes = storageMetadata.getSizeBytes();
                        Log.d(TAG, "onSuccess: "+model.getPdfName()+" "+bytes);

                        double kb = bytes/1024;
                        double mb = kb/1024;

                        if (mb>=1){
                            holder.pdfSizeTextView.setText(String.format("%.2f", mb)+" Mb");
                        }else if (kb>=1){
                            holder.pdfSizeTextView.setText(String.format("%.2f", kb)+" Kb");
                        }else {
                            holder.pdfSizeTextView.setText(String.format("%.2f", bytes)+" bytes");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.getMessage());
                        Toast.makeText(context.getApplicationContext(), "Can't Retrieve data due to "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return pdfArrayList.size();
    }

    class HolderPdfAdmin extends RecyclerView.ViewHolder{

        PDFView pdfView;
        ProgressBar pdfLoadingProgressBar;
        TextView pdfTitleTextView, pdfCourseNameTextView, pdfSizeTextView, pdfUploadDateTextView;
        ImageButton pdfDownloadImageBtn, moreAboutPdfImageBtn;

        public HolderPdfAdmin(@NonNull View itemView) {
            super(itemView);

            pdfView = rowPdfContainerBinding.pdfView;
            pdfLoadingProgressBar = rowPdfContainerBinding.PdfLoadingProgressBar;
            pdfTitleTextView = rowPdfContainerBinding.PdfTitleTextView;
            pdfCourseNameTextView = rowPdfContainerBinding.PdfCourseNameTextView;
            pdfSizeTextView = rowPdfContainerBinding.PdfSizeTextView;
            pdfUploadDateTextView = rowPdfContainerBinding.PdfUploadDateTextView;
            pdfDownloadImageBtn = rowPdfContainerBinding.PdfDownloadImageBtn;
            moreAboutPdfImageBtn = rowPdfContainerBinding.MoreAboutPdfImageBtn;
        }
    }
}
*/
