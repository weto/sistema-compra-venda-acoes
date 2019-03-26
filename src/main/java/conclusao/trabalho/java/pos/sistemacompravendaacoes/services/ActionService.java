package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Action;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Sell;

public interface ActionService {
	
	Set<Action> getAll();
	
	Action getActionById(String id);
	
	Action createNewAction(Action action);
	
	void launch(Integer numberActions, String idCompany);

	Action saveAction(String id, Action action);

	void deleteActionById(String id);
	
	void sellActionByInvestor(Sell sell);

	void buyActionAllByInvestor(Sell sell);

	void sendMessageBuy(List<Company> companies);

	void sendMessageSell(List<Company> companies);
	
}
