package conclusao.trabalho.java.pos.sistemacompravendaacoes.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Buy {
	
	List<Investor> investors = new ArrayList<Investor>();
	List<Company> companies = new ArrayList<Company>();

}
