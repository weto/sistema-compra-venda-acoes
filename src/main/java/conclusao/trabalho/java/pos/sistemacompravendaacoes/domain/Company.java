package conclusao.trabalho.java.pos.sistemacompravendaacoes.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Company {
	
	@Id
	private String id;
	
    private String name;

	private List<Action> actions = new ArrayList<>();
    
}
