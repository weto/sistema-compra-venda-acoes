package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestBody;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Action;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Sell;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.SellP;

public interface ActionService {
	
	Set<Action> getAll();
	
	Action getActionById(String id);
	
	Action createNewAction(Action action);
	
	void launch(Integer numberActions, String idCompany);

	Action saveAction(String id, Action action);

	void deleteActionById(String id);
	
	void sellActionByInvestor(String id, SellP sellp);

	void buyActionAllByInvestor(String id, SellP sellP);

	void sendMessageBuy(SellP sellp);

	void sendMessageSell(SellP sellp);
	
	boolean validSendSell(SellP sellp);
	
}
