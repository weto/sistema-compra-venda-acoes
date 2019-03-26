package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.List;
import java.util.Map;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Message;

public interface MessageService {

	void sendMessage(Message message);
	
	void sendBuy(List<Company> companies);

	void sendSell(List<Company> companies);
}
