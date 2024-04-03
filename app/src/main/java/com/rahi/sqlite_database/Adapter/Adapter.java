package com.rahi.sqlite_database.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.rahi.sqlite_database.Database.DatabaseHelper;
import com.rahi.sqlite_database.MainActivity;
import com.rahi.sqlite_database.MainActivity2;
import com.rahi.sqlite_database.POJO_Classes.GetData;
import com.rahi.sqlite_database.R;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurAlgorithm;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import jp.wasabeef.blurry.Blurry;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList<GetData> getData;
    Context context;

    public Adapter(ArrayList<GetData> getData, Context context) {
        this.getData = getData;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyc_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

         GetData getdata = getData.get(position);

        holder.cardView.setOnClickListener(view -> {
            final Dialog dialogBuilder = new Dialog(context, R.style.MyNewDialog1);

            View dialogView = LayoutInflater.from(context).inflate(R.layout.update_dialog,null);
            dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogBuilder.setContentView(dialogView);
            EditText et_name = dialogView.findViewById(R.id.et_name);
            EditText et_email = dialogView.findViewById(R.id.et_email);
            EditText et_pass = dialogView.findViewById(R.id.et_pass);
            EditText et_prof = dialogView.findViewById(R.id.et_prof);
            EditText et_age = dialogView.findViewById(R.id.et_age);
            Button btn_update = dialogView.findViewById(R.id.btn_update);

            et_name.setText(getdata.getName());
            et_email.setText(getdata.getEmail());
            et_pass.setText(getdata.getPass());
            et_prof.setText(getdata.getProf());
            et_age.setText(Integer.toString(getdata.getAge()));

            btn_update.setOnClickListener(view1 -> {
                getdata.setName(et_name.getText().toString());
                getdata.setEmail(et_email.getText().toString());
                getdata.setPass(et_pass.getText().toString());
                getdata.setProf(et_prof.getText().toString());
                getdata.setAge(Integer.parseInt(et_age.getText().toString()));

                DatabaseHelper db = new DatabaseHelper(context);
                Boolean checkInsertedData = db.UpdateUser(et_name.getText().toString(), et_email.getText().toString(),et_pass.getText().toString(),et_prof.getText().toString(),Integer.parseInt(et_age.getText().toString()));
                if(checkInsertedData == true) {
                    dialogBuilder.dismiss();
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(position);
                    notifyDataSetChanged();
                }
                else{
                    Toast.makeText(context, "Not Done", Toast.LENGTH_SHORT).show();
                    dialogBuilder.dismiss();
//                    notifyItemChanged(position);
                }
            });
            dialogBuilder.show();
//            notifyDataSetChanged();
        });

        holder.cardView.setOnLongClickListener(view -> {
            deletedata(getdata.getEmail().toString(),position);
//            notifyDataSetChanged();
            return true;
        });
        holder.name.setText(getdata.getName());
        holder.email.setText(getdata.getEmail());
        holder.pass.setText(getdata.getPass());
        holder.prof.setText(getdata.getProf());
        holder.age.setText(Integer.toString(getdata.getAge()));

//        notifyDataSetChanged();
    }

    private void deletedata(String email,int position) {
        DatabaseHelper db = new DatabaseHelper(context);
        int deleterows = db.DeleteUser(email);
        if(deleterows>0){
            getData.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
            Toast.makeText(context, email+"Data Deleted", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, email+"Data Not Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return getData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,pass,prof,age;
        CardView cardView;
//        BlurView blurView;
        ConstraintLayout cons,cons2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cons = itemView.findViewById(R.id.root);
            cons2 = itemView.findViewById(R.id.cons2);
            cardView = itemView.findViewById(R.id.cardview);
            name = itemView.findViewById(R.id.post_name);
            email = itemView.findViewById(R.id.post_email);
            pass = itemView.findViewById(R.id.post_pass);
            prof = itemView.findViewById(R.id.post_prof);
            age = itemView.findViewById(R.id.post_age);
//            cons2.post(() -> Blurry.with(context).radius(5).sampling(1).onto(cardView));
//            Blurry.with(context).radius(20).sampling(2).onto(cardView);
//            blurView.setupWith(cardView).setBlurAlgorithm(new RenderScriptBlur(context)).setBlurRadius(25f).setHasFixedTransformationMatrix(true);
        }
    }
}
