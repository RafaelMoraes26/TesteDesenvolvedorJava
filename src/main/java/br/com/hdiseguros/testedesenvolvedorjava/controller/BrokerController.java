package br.com.hdiseguros.testedesenvolvedorjava.controller;

import br.com.hdiseguros.testedesenvolvedorjava.model.Broker;
import br.com.hdiseguros.testedesenvolvedorjava.model.BrokerData;
import br.com.hdiseguros.testedesenvolvedorjava.model.CompleteBroker;
import br.com.hdiseguros.testedesenvolvedorjava.service.ValidarBroker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BrokerController {
    UriComponents uriBroker = UriComponentsBuilder.newInstance().scheme("https").host("607732991ed0ae0017d6a9b0.mockapi.io").path("insurance/v1/broker").build();
    ObjectMapper objectMapper = new ObjectMapper();
    RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/broker/{document}")
    public ResponseEntity<Broker> retornarBrokerPorDocumento(@PathVariable String document) throws Exception {
        ResponseEntity<Broker> resultado = restTemplate.getForEntity(uriBroker.toUriString() + "/" + document, Broker.class);
        return resultado;
    }

    @GetMapping("/broker")
    public List<Broker> retornarTodosOsBrokers() throws IOException {
        return objectMapper.readValue(new URL(uriBroker.toUriString()), new TypeReference<List<Broker>>(){});
    }

    @GetMapping("/brokerData/{code}")
    public ResponseEntity<BrokerData> retornaDadosDoBrokerPorCodigo(@PathVariable String code) {
        ResponseEntity<BrokerData> resultado = restTemplate.getForEntity(uriBroker.toUriString() + "Data/" + code, BrokerData.class);
        return resultado;
    }

    @GetMapping("/brokerData")
    public List<BrokerData> retornaTodosOsDadosDosBrokers()throws IOException {
        return objectMapper.readValue(new URL(uriBroker.toUriString() + "Data/"), new TypeReference<List<BrokerData>>(){});
    }

    @GetMapping("/validaBroker/{code}")
    public ResponseEntity<CompleteBroker> validarBroker(@PathVariable String code) throws IOException {
        return ValidarBroker.validarStatusBroker(code);
    }

    @PutMapping("/atualizaStatusBroker/{code}")
    public ResponseEntity<BrokerData> atualizarStatusBroker(@PathVariable String code, @RequestBody BrokerData brokerData) throws IOException {
        try {
            restTemplate.put(uriBroker.toUriString() + "Data/" + code, brokerData);
            return this.retornaDadosDoBrokerPorCodigo(code);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum broker foi encontrado com este código. Informe um código válido.");
        }
    }

}
