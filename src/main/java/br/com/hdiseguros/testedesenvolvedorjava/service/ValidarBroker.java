package br.com.hdiseguros.testedesenvolvedorjava.service;

import br.com.hdiseguros.testedesenvolvedorjava.mapper.BrokerMapper;
import br.com.hdiseguros.testedesenvolvedorjava.model.Broker;
import br.com.hdiseguros.testedesenvolvedorjava.model.BrokerData;
import br.com.hdiseguros.testedesenvolvedorjava.model.CompleteBroker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ValidarBroker {

    public static ResponseEntity<CompleteBroker> validarStatusBroker(String code) throws IOException {
        UriComponents uriBroker = UriComponentsBuilder.newInstance().scheme("https").host("607732991ed0ae0017d6a9b0.mockapi.io").path("insurance/v1/broker").build();
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<BrokerData> brokerData = restTemplate.getForEntity(uriBroker.toUriString() + "Data/" + code, BrokerData.class);
            if(!brokerData.getBody().getActive()) throw new IOException("O Broker não se encontra ativo.");
            List<Broker> lsBrokers = objectMapper.readValue(new URL(uriBroker.toUriString()), new TypeReference<List<Broker>>() {
            });

            List<ResponseEntity<Broker>> lsBroker = lsBrokers.parallelStream().map(brokers -> {
                if (brokers.getCode().equals(code)) {
                    try {
                        ResponseEntity<Broker> corretor = restTemplate.getForEntity(uriBroker.toUriString() + "/" + brokers.getDocument(), Broker.class);
                        return corretor;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }).collect(Collectors.toList());

            Optional<ResponseEntity<Broker>> broker = lsBroker.stream().filter(Objects::nonNull).findFirst();

            CompleteBroker brokerCompleto = BrokerMapper.brokerCompletoMapper(broker.get(), brokerData);
            ResponseEntity<CompleteBroker> brokerResponseEntity = new ResponseEntity<>(brokerCompleto, HttpStatus.resolve(brokerData.getStatusCodeValue()));
            return brokerResponseEntity;
        } catch (Exception e) {
            if(e.getMessage().equals("O Broker não se encontra ativo.")) throw new ResponseStatusException(HttpStatus.OK, e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum broker foi encontrado com este código. Informe um código válido.");
        }
    }
}
