package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Action;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Message;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Sell;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories.ActionRepository;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories.CompanyRepository;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories.InvestorRepository;

@Service
public class ActionServiceImpl implements ActionService {
	
	private ActionRepository actionRepository;
	private CompanyRepository companyRepository;
	private InvestorRepository investorRepository;
	private MessageService messageService;
	private CompanyService companyService;
	
	public ActionServiceImpl(ActionRepository actionRepository,
							 CompanyRepository companyRepository,
							 InvestorRepository investorRepository,
							 MessageService messageService,
							 CompanyService companyService) {
		this.actionRepository = actionRepository ;
		this.companyRepository = companyRepository;
		this.investorRepository = investorRepository;
		this.messageService = messageService;
		this.companyService = companyService;
	}
	
	@Override
	public Set<Action> getAll() {
		Set<Action> actions = new HashSet<>();
		this.actionRepository.findAll().iterator().forEachRemaining(actions::add);
		return actions;
	}

	@Override
	public Action getActionById(String id) {
		return getById(id);
	}
	
	public Action getById(String id) {
		Optional<Action> actionOptional = actionRepository.findById(id);
		
		if(!actionOptional.isPresent()) {
			throw new IllegalArgumentException("Action Not Found For ID value" + id.toString());
		}
		return actionOptional.get();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Action createNewAction(Action action) {
		return actionRepository.save(action);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Action saveAction(String id, Action action) {
		action.setId(id);
		return actionRepository.save(action);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteActionById(String id) {
		actionRepository.deleteById(id);
	}

	@Override
	public void launch(Integer numberActions, String idCompany) {
		Optional<Company> optionalCompany = companyRepository.findById(idCompany);
		if(!optionalCompany.isPresent()) {
			throw new IllegalArgumentException("Not Found ID Company");
		}
		
		Company companyTemp = ((Company)optionalCompany.get());
		
		for (int i = 0; i < numberActions; i++) {
			Action action = new Action();
			action.setId(null);
			action.setOwner(companyTemp.getName());
			action.setSell("true");
			action.setValue(((Action) companyTemp.getActions().get(0)).getValue());
			companyTemp.getActions().add(action);
			companyRepository.save(companyTemp);
		}
	}

	@Override
	public void sellActionByInvestor(String id, Sell sellp) {
		this.validSendSell(sellp);
		messageService.sendSell(sellp);
	}

	@Override
	public void buyActionAllByInvestor(String id, Sell sellp) {
		this.validSendSell(sellp);
		this.sendMessageBuy(sellp);
		messageService.sendBuy(sellp);
	}

	@Override
	public void sendMessageSell(Sell sellp) {
		
		Optional<Investor> investorOptional = investorRepository.findById(sellp.getId());
		if(!investorOptional.isPresent()) {
			throw new IllegalArgumentException("Not Found ID Investor");
		}
		
		Optional<Company> companyOptional = companyRepository.findById(sellp.getIdCompany());
		if(!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Not Found ID Company");
		}

		List<Action> actions = new ArrayList<Action>();
		for (Action action : companyOptional.get().getActions()) {
			if(action.getId()!=null) {
				String email = action.getInvestor().getEmail();
				String value = action.getValue();

				action = new Action();
				action.setId(null);
				action.setSell("true");
				action.setValue(value);
				action.setOwner(companyOptional.get().getName());
				action.setInvestor(null);
				
				Message messageInvestor = new Message();
				messageInvestor.setBody("A ação foi vendida pelo valor de R$ " + value);
				messageInvestor.setEmail(email);
				messageInvestor.setSubject("Venda de Ação da Empresa " + companyOptional.get().getName());
				messageService.sendMessage(messageInvestor);

				Message messageCompany = new Message();
				messageCompany.setBody("A ação foi comprada pelo valor de R$ " + value);
				messageCompany.setEmail(email);
				messageCompany.setSubject("Compra de Ação da Empresa " + companyOptional.get().getName());
				messageService.sendMessage(messageCompany);
			}

			actions.add(action);
		};
		
		companyOptional.get().setActions(actions);
		companyRepository.save(companyOptional.get());
	}

	@Override
	public void sendMessageBuy(Sell sellp) {
		
		Optional<Investor> investorOptional = investorRepository.findById(sellp.getId());
		if(!investorOptional.isPresent()) {
			throw new IllegalArgumentException("Not Found ID Investor");
		}
		
		Optional<Company> companyOptional = companyRepository.findById(sellp.getIdCompany());
		if(!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Not Found ID Company");
		}
		
		boolean sell = false;
		for (Action action : companyOptional.get().getActions()) {
			if(action.getId()==null && !sell) {
				action.setSell("false");
				action.setOwner(investorOptional.get().getName());
				action.setInvestor(investorOptional.get());
				actionRepository.save(action);
				
				Message messageInvestor = new Message();
				messageInvestor.setBody("A ação foi comprada pelo valor de R$ " + action.getValue());
				messageInvestor.setEmail(investorOptional.get().getEmail());
				messageInvestor.setSubject("Compra de Ação da Empresa " + companyOptional.get().getName());
				messageService.sendMessage(messageInvestor);

				Message messageCompany = new Message();
				messageCompany.setBody("A ação foi vendida pelo valor de R$ " + action.getValue());
				messageCompany.setEmail(investorOptional.get().getEmail());
				messageCompany.setSubject("Venda de Ação da Empresa " + companyOptional.get().getName());
				messageService.sendMessage(messageCompany);
				sell = true;
			}
		}
		
		companyRepository.save(companyOptional.get());
	}

	@Override
	public boolean validSendSell(Sell sellp) {
		Optional<Investor> investorOptional = investorRepository.findById(sellp.getId());
		if(!investorOptional.isPresent()) {
			throw new IllegalArgumentException("Not Found ID Investor");
		}
		
		Optional<Company> companyOptional = companyRepository.findById(sellp.getIdCompany());
		if(!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Not Found ID Company");
		}

		return true;
	}
}
