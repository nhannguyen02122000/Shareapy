package com.example.shareapy.homepage.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareapy.R;
import com.example.shareapy.models.ActivityInfo;
import com.example.shareapy.utils.SavedInstance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerviewHistory extends RecyclerView.Adapter<AdapterRecyclerviewHistory.ActivityViewHolder> {
    private List<ActivityInfo> listActivity = new ArrayList<>();
    private boolean isHistory;
    String pattern = "h:mm a dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    Dialog feedbackDialog = new Dialog(SavedInstance.homeActivity);
    ;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    AdapterRecyclerviewHistory(boolean isHistory) {
        this.isHistory = isHistory;
    }

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_activity, parent, false);
        customDialog();
        return new ActivityViewHolder(view);
    }

    private void customDialog() {
        feedbackDialog.setContentView(R.layout.dialog_feedback);
        feedbackDialog.setTitle("Rating and feedback");
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, final int position) {
        final ActivityInfo info = listActivity.get(position);
        final String id = user.getUid();
        holder.txtActivityCardTitle.setText(info.getTitle());
        holder.txtActivityCardTime.setText(simpleDateFormat.format(info.getTime()));
        if (info.getRatingList().containsKey(id)) {
            holder.rtbActivityCardRate.setRating(info.getRatingList().get(id));

        }
        if (!isHistory) {
            holder.rtbActivityCardRate.setVisibility(View.GONE);
        }
        holder.rtbActivityCardRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SavedInstance.homeActivity, "ok", Toast.LENGTH_SHORT).show();
            }

        });
        holder.imgCardType.setImageResource(getImageByType(info.getType()));

        holder.rtbActivityCardRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, final float v, boolean b) {
                RatingBar rtbDialogFeedback = feedbackDialog.findViewById(R.id.rtbDialogRate);
                final EditText edtDialogFeedback = feedbackDialog.findViewById(R.id.edtDialogFeedback);
                Button btnDialogSubmit = feedbackDialog.findViewById(R.id.btnDialogSubmit);
                rtbDialogFeedback.setRating(v);
                String feedback = "";
                if (info.getFeedbackList().containsKey(id)) {
                    feedback = info.getFeedbackList().get(id);
                }
                feedbackDialog.setCanceledOnTouchOutside(true);
                edtDialogFeedback.setText(feedback);
                btnDialogSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ratingBar.setRating(v);
                        final String feedbackText = edtDialogFeedback.getText().toString().trim();
                        final ProgressDialog progressDialog = new ProgressDialog(SavedInstance.homeActivity);
                        progressDialog.setTitle("Processing");
                        progressDialog.show();
                        db.collection("ActivityInfos")
                                .document(info.getId())
                                .update("ratingList." + id, v,
                                        "feedbackList." + id, feedbackText)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        feedbackDialog.dismiss();
                                        info.getRatingList().put(id, v);
                                        info.getFeedbackList().put(id, feedbackText);
                                        listActivity.set(position, info);
                                        progressDialog.dismiss();
                                        Toast.makeText(SavedInstance.homeActivity, "Thank for your feedback", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SavedInstance.homeActivity, "Set feedback failure", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        feedbackDialog.dismiss();
                                    }
                                });
                    }
                });
                if (!feedbackDialog.isShowing()) feedbackDialog.show();

            }
        });
    }

    private int getImageByType(String type) {
        switch (type) {
            case "Family":
                return R.drawable.image_family;
            case "School":
                return R.drawable.image_school;
            case "Work":
                return R.drawable.image_work;
            case "Relationship": return R.drawable.image_relationship;
            case "Lifestyle": return R.drawable.image_lifestyle;
            case "Other": return R.drawable.image_other;
            default: return R.drawable.image_other;
        }
    }

    public void setItem(List<ActivityInfo> listActivityNew) {
        this.listActivity.clear();
        this.listActivity.addAll(listActivityNew);
    }

    @Override
    public int getItemCount() {
        return listActivity.size();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {
        TextView txtActivityCardTitle, txtActivityCardTime;
        RatingBar rtbActivityCardRate;
        RoundedImageView imgCardType;

        ActivityViewHolder(View itemView) {
            super(itemView);
            txtActivityCardTitle = itemView.findViewById(R.id.txtActivityCardTitle);
            txtActivityCardTime = itemView.findViewById(R.id.txtActivityCardTime);
            rtbActivityCardRate = itemView.findViewById(R.id.rtbActivityCardRate);
            imgCardType = itemView.findViewById(R.id.imgCardType);
        }
    }
}
