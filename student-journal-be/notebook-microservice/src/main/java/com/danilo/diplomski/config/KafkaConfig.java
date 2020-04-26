package com.danilo.diplomski.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.danilo.diplomski.models.kafka.RegistrationKafkaModel;
import com.danilo.diplomski.models.kafka.StudentKafkaModel;



@Configuration
@EnableKafka
public class KafkaConfig {

	@Bean
	public ProducerFactory<String, StudentKafkaModel> producerFactory()
	{
		Map<String, Object> config = new HashMap<>();
		
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return new DefaultKafkaProducerFactory<>(config);
		
	}
	
	@Bean
	public KafkaTemplate<String, StudentKafkaModel> kafkaTemplate()
	{
		return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	public ConsumerFactory<String, StudentKafkaModel> studentConsumerFactory()
	{
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "my--application");
		config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 2);
		config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "8000000");
		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(StudentKafkaModel.class));
	}
	
	@Bean(name = "studentKafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, StudentKafkaModel>  kafkaListenerContainerFactory()
	{
		ConcurrentKafkaListenerContainerFactory<String, StudentKafkaModel> 
		concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		
		concurrentKafkaListenerContainerFactory.setConsumerFactory(studentConsumerFactory());
		return concurrentKafkaListenerContainerFactory;
		
	}
	
	///SAGA consumer
		@Bean
		public ConsumerFactory<String, RegistrationKafkaModel> registrationConsumerFactory() {
			Map<String, Object> config = new HashMap<>();
			config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
			config.put(ConsumerConfig.GROUP_ID_CONFIG, "registration");
			config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 2);
			config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "8000000");
			config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
			
			return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
					new JsonDeserializer<>(RegistrationKafkaModel.class));
		}

		@Bean(name = "registrationKafkaListenerContainerFactory")
		public ConcurrentKafkaListenerContainerFactory<String, RegistrationKafkaModel> registrationKafkaListenerContainerFactory() {
			ConcurrentKafkaListenerContainerFactory<String, RegistrationKafkaModel> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();

			concurrentKafkaListenerContainerFactory.setConsumerFactory(registrationConsumerFactory());
			return concurrentKafkaListenerContainerFactory;

		}
		
		//SAGA producer
		@Bean
		public ProducerFactory<String, RegistrationKafkaModel> registrationProducerFactory() {
			Map<String, Object> config = new HashMap<>();

			config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

			return new DefaultKafkaProducerFactory<>(config);

		}

		@Bean
		public KafkaTemplate<String, RegistrationKafkaModel> registrationKafkaTemplate() {
			return new KafkaTemplate<>(registrationProducerFactory());
		}
		
	
}
