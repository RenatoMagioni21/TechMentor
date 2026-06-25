package com.example.techmentor;

import com.google.gson.annotations.SerializedName;

public class Agendamento {
    private Long id;
    private Long mentorId;

    @SerializedName("alunoNome")
    private String alunoNome;

    @SerializedName("dataAgendamento")
    private String dataAgendamento;

    @SerializedName("horaAgendamento")
    private String horaAgendamento;

    public Agendamento() {
    }

    public Agendamento(Long id, Long mentorId, String alunoNome, String dataAgendamento, String horaAgendamento) {
        this.id = id;
        this.mentorId = mentorId;
        this.alunoNome = alunoNome;
        this.dataAgendamento = dataAgendamento;
        this.horaAgendamento = horaAgendamento;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMentorId() { return mentorId; }
    public void setMentorId(Long mentorId) { this.mentorId = mentorId; }

    public String getAlunoNome() { return alunoNome; }
    public void setAlunoNome(String alunoNome) { this.alunoNome = alunoNome; }

    public String getDataAgendamento() { return dataAgendamento; }
    public void setDataAgendamento(String dataAgendamento) { this.dataAgendamento = dataAgendamento; }

    public String getHoraAgendamento() { return horaAgendamento; }
    public void setHoraAgendamento(String horaAgendamento) { this.horaAgendamento = horaAgendamento; }
}