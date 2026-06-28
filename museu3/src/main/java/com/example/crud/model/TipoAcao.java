package com.example.crud.model;
public enum TipoAcao {
    CRIAR("Criação"), EDITAR("Edição"), EXCLUIR("Exclusão");
    private final String label;
    TipoAcao(String label) { this.label = label; }
    public String getLabel() { return label; }
}
