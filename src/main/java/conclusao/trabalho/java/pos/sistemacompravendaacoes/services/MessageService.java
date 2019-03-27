package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Message;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.SellP;

public interface MessageService {

	void sendMessage(Message message);
	
	void sendBuy(SellP sellP);

	void sendSell(SellP sellP);
}
