package br.com.hdiseguros.testedesenvolvedorjava.mapper;

import br.com.hdiseguros.testedesenvolvedorjava.model.Broker;
import br.com.hdiseguros.testedesenvolvedorjava.model.BrokerData;
import br.com.hdiseguros.testedesenvolvedorjava.model.CompleteBroker;
import org.springframework.http.ResponseEntity;

public class BrokerMapper {
    public static CompleteBroker brokerCompletoMapper(ResponseEntity<Broker> broker, ResponseEntity<BrokerData> brokerData) {
        Broker brokerBody = broker.getBody();
        BrokerData brokerDataBody = brokerData.getBody();
        CompleteBroker brokerCompleto = new CompleteBroker();
        brokerCompleto.setActive(brokerDataBody.getActive());
        brokerCompleto.setCode(brokerDataBody.getCode());
        brokerCompleto.setComissionRate(brokerDataBody.getCommissionRate());
        brokerCompleto.setDocument(brokerBody.getDocument());
        brokerCompleto.setName(brokerBody.getName());
        brokerCompleto.setCreateDate(brokerBody.getCreateDate());
        return brokerCompleto;
    }

    public static Broker brokerMapper(ResponseEntity<Broker> corretor) {
        Broker broker = new Broker();
        Broker brokerBody = corretor.getBody();
        broker.setCode(brokerBody.getCode());
        broker.setDocument(brokerBody.getDocument());
        broker.setName(brokerBody.getName());
        broker.setCreateDate(brokerBody.getCreateDate());
        return broker;
    }

    public static BrokerData brokerDataMapper(ResponseEntity<BrokerData> dadosCorretor) {
        BrokerData brokerData = new BrokerData();
        BrokerData brokerDataBody = dadosCorretor.getBody();
        brokerData.setCode(brokerDataBody.getCode());
        brokerData.setActive(brokerDataBody.getActive());
        brokerData.setCommissionRate(brokerDataBody.getCommissionRate());
        return brokerData;
    }
}
