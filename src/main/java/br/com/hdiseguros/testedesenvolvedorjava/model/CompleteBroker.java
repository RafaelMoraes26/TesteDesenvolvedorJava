package br.com.hdiseguros.testedesenvolvedorjava.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompleteBroker {
    private String code;
    private Boolean active;
    private BigDecimal comissionRate;
    private String name;
    private String document;
    private String createDate;
}
