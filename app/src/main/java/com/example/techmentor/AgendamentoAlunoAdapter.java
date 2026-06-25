package com.example.techmentor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendamentoAlunoAdapter extends RecyclerView.Adapter<AgendamentoAlunoAdapter.ViewHolder> {

    private List<Agendamento> agendamentos;
    private Context context;

    public interface OnAgendamentoDeletedListener {
        void onDeleted();
    }

    private OnAgendamentoDeletedListener listener;

    public AgendamentoAlunoAdapter(List<Agendamento> agendamentos, Context context, OnAgendamentoDeletedListener listener) {
        this.agendamentos = agendamentos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_agendamento_aluno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Agendamento agendamento = agendamentos.get(position);

        holder.tvMentorNome.setText("Mentor ID: " + agendamento.getMentorId());
        holder.tvDataHora.setText("Data: " + agendamento.getDataAgendamento() + " às " + agendamento.getHoraAgendamento());

        holder.btnDeletar.setOnClickListener(v -> {
            Long idAgendamento = agendamento.getId();

            if (idAgendamento != null) {
                RetrofitClient.getApiService().deletarAgendamento(idAgendamento).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Agendamento cancelado!", Toast.LENGTH_SHORT).show();
                            if (listener != null) {
                                listener.onDeleted();
                            }
                        } else {
                            Toast.makeText(context, "Erro ao cancelar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(context, "Erro de rede ao cancelar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMentorNome, tvDataHora;
        Button btnDeletar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMentorNome = itemView.findViewById(R.id.tvMentorNome);
            tvDataHora = itemView.findViewById(R.id.tvDataHora);
            btnDeletar = itemView.findViewById(R.id.btnDeletar);
        }
    }
}