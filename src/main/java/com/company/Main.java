package com.company;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting test.");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        //props.put("metadata.broker.list","localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",   "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       // props.put("producer.type", "sync");

        System.out.println("Start sending.");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 2; i++) {
            System.out.println("in loop");
            try {
                producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i))).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Done sending.");

        producer.close();

        System.out.println("Ending test.");
    }
}