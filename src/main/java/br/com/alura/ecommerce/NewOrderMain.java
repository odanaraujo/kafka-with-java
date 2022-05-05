package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //produce produz a mensagem.Ele espera a chave e o tipo da mensagem
        //o kafka produce recebe propriedades (properties)
        var produce = new KafkaProducer<String, String>(properties());
        //value é a mensagem que queremos enviar (idDoPedido, idUsuario, valorCompra)
        var value = "122123, 67523, 790595085896";
        //record é um registro do produce. Parâmetros (o tópico, a chave e o valor)
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER",  value, value);

        //o send envia a mensagem. Um record, ou seja, um registro.
        //o método send devolve um Future (vai executar de forma assíncrona)
        //assim, executa com um get, pois, ele espera executar o serviço.
        //o callback retorna os dados de sucesso e a falha. Se falha, lanço um erro. Se sucesso, mostro informações.
        produce.send(record, (data, ex) -> {
            if(ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition" + data.partition() + "/ offset "
                    + data.offset() + "/ timestamp " + data.timestamp());
        }).get();
    }

    private static Properties properties() {
        var properties = new Properties();
        //onde estamos conectando - onde o kafka tá rodando
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //nos dois properties abaixo, transforma a chave e a mensagem de String para bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
