package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.config.RabbitMQConfig;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
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

	@Override
	public void sendBuy(List<Company> companies) {
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_BUY_MESSAGES, companies);
	}

	@Override
	public void sendSell(List<Company> companies) {
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_SELL_MESSAGES, companies);
	}
}
