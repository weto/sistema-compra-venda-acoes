package conclusao.trabalho.java.pos.sistemacompravendaacoes.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Action {
	
	@Id
	private String id;
    private String value;
    private String sell;
    private String owner;
    private Investor investor;

}
