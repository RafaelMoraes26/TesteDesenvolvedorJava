package br.com.hdiseguros.testedesenvolvedorjava.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrokerData {

    private String code;
    private Boolean active;
    private BigDecimal commissionRate;
}
