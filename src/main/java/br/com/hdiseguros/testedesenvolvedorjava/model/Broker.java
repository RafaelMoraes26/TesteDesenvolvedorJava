package br.com.hdiseguros.testedesenvolvedorjava.model;

import lombok.Data;

@Data
public class Broker {
    private String name;
    private String document;
    private String code;
    private String createDate;
}
