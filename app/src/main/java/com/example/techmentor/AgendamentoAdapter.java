package com.example.techmentor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.AgendamentoViewHolder> {

    private List<Agendamento> agendamentos;

    public AgendamentoAdapter(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    @NonNull
    @Override
    public AgendamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor, parent, false);
        return new AgendamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendamentoViewHolder holder, int position) {
        Agendamento agendamento = agendamentos.get(position);

        String nomeAluno = agendamento.getAlunoNome() != null ? agendamento.getAlunoNome() : "Não informado";
        String data = agendamento.getDataAgendamento() != null ? agendamento.getDataAgendamento() : "--/--/----";
        String hora = agendamento.getHoraAgendamento() != null ? agendamento.getHoraAgendamento() : "--:--";

        holder.tvAluno.setText("Aluno: " + nomeAluno);
        holder.tvData.setText("Data: " + data + " às " + hora);
    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }

    public static class AgendamentoViewHolder extends RecyclerView.ViewHolder {
        TextView tvAluno, tvData;

        public AgendamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAluno = itemView.findViewById(R.id.tvNomeMentor);
            tvData = itemView.findViewById(R.id.tvEspecialidade);
        }
    }
}