package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Message;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Sell;

public interface MessageService {

	void sendMessage(Message message);
	
	void sendBuy(Sell sellP);

	void sendSell(Sell sellP);
}
