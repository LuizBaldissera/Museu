package com.example.crud.model;
public enum TipoObra {
    LIVRO("Livro"), REVISTA("Revista"), JORNAL("Jornal");
    private final String label;
    TipoObra(String label) { this.label = label; }
    public String getLabel() { return label; }
}
