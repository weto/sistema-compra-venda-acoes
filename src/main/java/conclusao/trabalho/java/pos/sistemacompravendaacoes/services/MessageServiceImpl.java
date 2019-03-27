package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.config.RabbitMQConfig;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Message;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.SellP;

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

	@Override
	public void sendBuy(SellP sellp) {
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_BUY_MESSAGES, sellp);
	}

	@Override
	public void sendSell(SellP sellp) {
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_SELL_MESSAGES, sellp);
	}
}
