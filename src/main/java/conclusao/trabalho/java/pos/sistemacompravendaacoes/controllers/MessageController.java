package conclusao.trabalho.java.pos.sistemacompravendaacoes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Message;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.services.MessageService;

@RestController
@RequestMapping(MessageController.BASE_URL)
public class MessageController {

	public static final String BASE_URL = "/api/v1/messages";
	
	private MessageService messageService;
	
	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createNewCompany(@RequestBody Message message){
		messageService.sendMessage(message);
        return "Message sent";
    }
}
