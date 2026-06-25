package com.example.techmentor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardMentorActivity extends AppCompatActivity {

    private RecyclerView rvAgendamentos;
    private Button btnDeslogar;
    private AgendamentoAdapter adapter;
    private List<Agendamento> listaAgendamentos = new ArrayList<>();
    private Long idMentorLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_mentor);

        idMentorLogado = getIntent().getLongExtra("ID_MENTOR", 1L);

        rvAgendamentos = findViewById(R.id.rvMinhasMentorias);
        btnDeslogar = findViewById(R.id.btnDeslogar);

        rvAgendamentos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AgendamentoAdapter(listaAgendamentos);
        rvAgendamentos.setAdapter(adapter);

        btnDeslogar.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardMentorActivity.this, LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish(); // Destrói a Dashboard atual

            Toast.makeText(this, "Sessão encerrada", Toast.LENGTH_SHORT).show();
        });

        buscarMeusAgendamentos();
    }

    private void buscarMeusAgendamentos() {
        RetrofitClient.getApiService().getAgendamentosPorMentor(idMentorLogado).enqueue(new Callback<List<Agendamento>>() {
            @Override
            public void onResponse(Call<List<Agendamento>> call, Response<List<Agendamento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaAgendamentos.clear();
                    listaAgendamentos.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Agendamento>> call, Throwable t) {
                Toast.makeText(DashboardMentorActivity.this, "Erro ao carregar sua agenda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}