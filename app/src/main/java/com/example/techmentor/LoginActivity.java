package com.example.techmentor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        btnEntrar = findViewById(R.id.btnLogin);

        btnEntrar.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String senha = etSenha.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                executarLogin(email, senha);
            }
        });
    }

    private void executarLogin(String email, String senha) {
        LoginRequest request = new LoginRequest(email, senha);

        RetrofitClient.getApiService().login(request).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuarioLogado = response.body();

                    Log.d("TECHMENTOR_LOGIN", "Objeto recebido: " + usuarioLogado.getNome() + " | Perfil: " + usuarioLogado.getPerfil());

                    if ("MENTOR".equalsIgnoreCase(usuarioLogado.getPerfil())) {
                        Intent intent = new Intent(LoginActivity.this, DashboardMentorActivity.class);

                        intent.putExtra("ID_MENTOR", usuarioLogado.getId());

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivityAluno.class);

                        intent.putExtra("ID_ALUNO", usuarioLogado.getId());
                        intent.putExtra("NOME_ALUNO", usuarioLogado.getNome());

                        startActivity(intent);
                    }
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("TECHMENTOR_LOGIN", "Falha na requisição", t);
            }
        });
    }
}