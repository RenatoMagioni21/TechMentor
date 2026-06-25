package com.example.techmentor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("auth/login")
    Call<Usuario> login(@Body LoginRequest loginRequest);

    @GET("mentores")
    Call<List<Mentor>> getMentores(@Query("disciplina") String disciplina);

    @POST("agendamentos")
    Call<Agendamento> salvarAgendamento(@Body Agendamento agendamento);

    @GET("agendamentos")
    Call<List<Agendamento>> getAgendamentos();

    @GET("agendamentos/mentor/{id}")
    Call<List<Agendamento>> getAgendamentosPorMentor(@Path("id") Long mentorId);

    @GET("agendamentos/aluno/{alunoNome}")
    Call<List<Agendamento>> getAgendamentosPorAluno(@Path("alunoNome") String alunoNome);

    @DELETE("agendamentos/{id}")
    Call<Void> deletarAgendamento(@Path("id") Long agendamentoId);
}