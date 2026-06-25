package com.example.techmentor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {

    private List<Mentor> mentorList;
    private Context context;

    public MentorAdapter(List<Mentor> mentorList, Context context) {
        this.mentorList = mentorList;
        this.context = context;
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mentor, parent, false);
        return new MentorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        Mentor mentor = mentorList.get(position);
        holder.tvNome.setText(mentor.getNome());
        holder.tvEspecialidade.setText(mentor.getEspecialidade());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AgendamentoActivity.class);
            intent.putExtra("MENTOR_ID", mentor.getId());
            intent.putExtra("MENTOR_NOME", mentor.getNome());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mentorList.size();
    }

    public static class MentorViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvEspecialidade;

        public MentorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNomeMentor);
            tvEspecialidade = itemView.findViewById(R.id.tvEspecialidade);
        }
    }
}