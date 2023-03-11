package com.example.vuquery.adapters;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vuquery.R;

public class newAdapter extends RecyclerView.Adapter<newAdapter.MyViewHolder> {

    Context context;
    String[] coursePdfName;
    String courseName;

    public newAdapter(Context context, String[] coursePdfName, String courseName) {
        this.context = context;
        this.coursePdfName = coursePdfName;
        this.courseName = courseName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_pdf_container, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pdfTitleTv.setText(coursePdfName[position]);
        holder.courseNameTv.setText(courseName);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.progressBar.setVisibility(View.INVISIBLE);
            }
        },5000);
    }

    @Override
    public int getItemCount() {

        return coursePdfName.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pdfTitleTv, courseNameTv;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            pdfTitleTv = itemView.findViewById(R.id.PdfTitleTextView);
            courseNameTv = itemView.findViewById(R.id.PdfCourseNameTextView);
            progressBar = itemView.findViewById(R.id.PdfLoadingProgressBar);
        }
    }
}
