package conclusao.trabalho.java.pos.sistemacompravendaacoes.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String subject;
	private String email;
	private String body;
}
