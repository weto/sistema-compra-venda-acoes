package conclusao.trabalho.java.pos.sistemacompravendaacoes.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.config.EmailConfig;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.config.RabbitMQConfig;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Message;

@Component
public class MessageListener {

	static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	@Value("${EMAIL_USER}")
	private String EMAIL_USER;

	@Value("${EMAIL_PASSWORD}")
	private String EMAIL_PASSWORD;
	
    @RabbitListener(queues = RabbitMQConfig.QUEUE_MESSAGES)
    public void processMessage(Message message) {
    	
    	final String fromEmail = message.getEmail();
		System.out.println("Initializing email send");
		EmailConfig config = new EmailConfig();
		config.sendEmail(fromEmail, this.EMAIL_PASSWORD, this.EMAIL_USER, message.getSubject(), message.getBody());
		
        logger.info("Message Received");
        logger.info("Sebject:" + message.getSubject());
        logger.info("Email:" + message.getEmail());
        logger.info("Body:" + message.getBody());
    }
}
