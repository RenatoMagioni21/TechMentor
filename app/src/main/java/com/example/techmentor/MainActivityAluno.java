package com.example.techmentor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityAluno extends AppCompatActivity {

    private RecyclerView rvMentores;
    private MentorAdapter mentorAdapter;
    private List<Mentor> listaMentores = new ArrayList<>();
    private EditText etFiltro;

    private RecyclerView rvMeusAgendamentos;
    private AgendamentoAlunoAdapter agendamentoAdapter;
    private List<Agendamento> listaMeusAgendamentos = new ArrayList<>();

    private Button btnDeslogar;
    private Long idAlunoLogado;
    private String nomeAlunoLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aluno);

        idAlunoLogado = getIntent().getLongExtra("ID_ALUNO", 1L);
        nomeAlunoLogado = getIntent().getStringExtra("NOME_ALUNO");

        rvMentores = findViewById(R.id.rvMentores);
        etFiltro = findViewById(R.id.etFiltroDisciplina);
        rvMeusAgendamentos = findViewById(R.id.rvMeusAgendamentos);
        btnDeslogar = findViewById(R.id.btnDeslogarAluno);

        rvMentores.setLayoutManager(new LinearLayoutManager(this));
        mentorAdapter = new MentorAdapter(listaMentores, this);
        rvMentores.setAdapter(mentorAdapter);

        rvMeusAgendamentos.setLayoutManager(new LinearLayoutManager(this));
        agendamentoAdapter = new AgendamentoAlunoAdapter(listaMeusAgendamentos, this, new AgendamentoAlunoAdapter.OnAgendamentoDeletedListener() {
            @Override
            public void onDeleted() {
                buscarMeusAgendamentos();
            }
        });
        rvMeusAgendamentos.setAdapter(agendamentoAdapter);

        if (nomeAlunoLogado != null && !nomeAlunoLogado.isEmpty()) {
            Toast.makeText(this, "Olá, " + nomeAlunoLogado + "!", Toast.LENGTH_SHORT).show();
        }

        btnDeslogar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivityAluno.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Sessão encerrada", Toast.LENGTH_SHORT).show();
        });

        buscarMentores("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        buscarMeusAgendamentos();
    }

    private void buscarMentores(String disciplina) {
        RetrofitClient.getApiService().getMentores(disciplina).enqueue(new Callback<List<Mentor>>() {
            @Override
            public void onResponse(Call<List<Mentor>> call, Response<List<Mentor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaMentores.clear();
                    listaMentores.addAll(response.body());
                    mentorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Mentor>> call, Throwable t) {
                Toast.makeText(MainActivityAluno.this, "Erro ao carregar mentores", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarMeusAgendamentos() {
        if (nomeAlunoLogado == null || nomeAlunoLogado.isEmpty()) return;

        RetrofitClient.getApiService().getAgendamentosPorAluno(nomeAlunoLogado).enqueue(new Callback<List<Agendamento>>() {
            @Override
            public void onResponse(Call<List<Agendamento>> call, Response<List<Agendamento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaMeusAgendamentos.clear();
                    listaMeusAgendamentos.addAll(response.body());
                    agendamentoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Agendamento>> call, Throwable t) {
                Toast.makeText(MainActivityAluno.this, "Erro ao carregar histórico", Toast.LENGTH_SHORT).show();
            }
        });
    }
}