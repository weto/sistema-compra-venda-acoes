package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.config.RabbitMQConfig;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Message;

@Service
public class MessageServiceImpl implements MessageService{

	private final RabbitTemplate rabbitTemplate;
	 
    public MessageServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void sendMessage(Message message) {
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_MESSAGES, message);
    }
}
