package com.example.techmentor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendamentoActivity extends AppCompatActivity {

    private String dataSelecionada;
    private String nomeMentor;
    private Long mentorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento);

        nomeMentor = getIntent().getStringExtra("MENTOR_NOME");
        mentorId = getIntent().getLongExtra("MENTOR_ID", 0);

        TextView tvMentor = findViewById(R.id.tvNomeMentorAgendamento);
        CalendarView calendar = findViewById(R.id.calendarView);
        Button btnConfirmar = findViewById(R.id.btnConfirmarAgendamento);

        tvMentor.setText("Mentor: " + nomeMentor);

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            dataSelecionada = dayOfMonth + "/" + (month + 1) + "/" + year;
        });

        btnConfirmar.setOnClickListener(v -> {
            if (dataSelecionada == null) {
                Toast.makeText(this, "Por favor, selecione uma data no calendário", Toast.LENGTH_SHORT).show();
                return;
            }
            executarAgendamento();
        });
    }

    private void executarAgendamento() {
        Agendamento agendamento = new Agendamento();
        agendamento.setMentorId(mentorId);
        agendamento.setAlunoNome("Renato Mota");
        agendamento.setDataAgendamento(dataSelecionada);
        agendamento.setHoraAgendamento("14:00");

        RetrofitClient.getApiService().salvarAgendamento(agendamento).enqueue(new Callback<Agendamento>() {
            @Override
            public void onResponse(Call<Agendamento> call, Response<Agendamento> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AgendamentoActivity.this, "Agendamento realizado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AgendamentoActivity.this, "Erro do servidor: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Agendamento> call, Throwable t) {
                Toast.makeText(AgendamentoActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}