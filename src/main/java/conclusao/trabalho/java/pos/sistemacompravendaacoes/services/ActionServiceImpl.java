package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	public void sellActionByInvestor(Sell sell) {
		List<Company> companies = companyService.getByCompanyInvestor(sell.getNameCompany(), sell.getNameInvestor());
		//messageService.sendSell(companies);
		this.sendMessageSell(companies);
	}

	@Override
	public void buyActionAllByInvestor(Sell sell) {
		
		List<Investor> investors = investorRepository.findByName(sell.getNameInvestor());
		List<Company> companies = companyService.getByActionCompany(sell.getNameCompany());
		
		if(companies.size() == 0) {
			throw new IllegalArgumentException("No action to buy from company");
		}
		
		for (Company company : companies) {
			for (Action action : company.getActions()) {
				if(action.getId()==null) {
					action.setSell("false");
					action.setOwner(investors.get(0).getName());
					action.setInvestor(investors.get(0));
					break;
				}
			};
		};
		
		this.sendMessageBuy(companies);
		//messageService.sendBuy(companies);
	}

	@Override
	public void sendMessageSell(List<Company> companies) {
		System.out.println("[####################################buySaveBySellBuy inicio##################################]");

		int positionCompany = 0;
		int positionAction = 0;

		for (Company company : companies) {
			for (Action action : company.getActions()) {
				if(action.getId()!=null) {
					String email = action.getInvestor().getEmail();
					String value = action.getValue();

					action = new Action();
					action.setId(null);
					action.setSell("true");
					action.setOwner(company.getName());
					action.setInvestor(null);
					action.setValue(value);
					companies.get(positionCompany).getActions().set(positionAction, action);

					Message messageInvestor = new Message();
					messageInvestor.setBody("A ação foi vendida pelo valor de R$ " + value);
					messageInvestor.setEmail(email);
					messageInvestor.setSubject("Venda de Ação da Empresa " + company.getName());
					//messageService.sendMessage(messageInvestor);

					Message messageCompany = new Message();
					messageCompany.setBody("A ação foi comprada pelo valor de R$ " + value);
					messageCompany.setEmail(email);
					messageCompany.setSubject("Compra de Ação da Empresa " + company.getName());
					//messageService.sendMessage(messageCompany);
				}
				positionAction++;
			}
			positionCompany++;
			companyRepository.save(company);
		}

		System.out.println("[####################################buySaveBySellBuy fim#####################################]");
	}

	@Override
	public void sendMessageBuy(List<Company> companies) {
		
		System.out.println("[sendMessageBuy]");
		for (Company company : companies) {
			for (Action action : company.getActions()) {
				if(action.getId()==null) {
					actionRepository.save(action);
					
					Message messageInvestor = new Message();
					messageInvestor.setBody("A ação foi comprada pelo valor de R$ " + action.getValue());
					messageInvestor.setEmail(company.getActions().get(0).getInvestor().getEmail());
					messageInvestor.setSubject("Compra de Ação da Empresa " + company.getName());
					//messageService.sendMessage(messageInvestor);

					Message messageCompany = new Message();
					messageCompany.setBody("A ação foi vendida pelo valor de R$ " + action.getValue());
					messageCompany.setEmail(company.getActions().get(0).getInvestor().getEmail());
					messageCompany.setSubject("Venda de Ação da Empresa " + company.getName());
					//messageService.sendMessage(messageCompany);
				}
			};
			
			companyRepository.save(company);
		}
	}
}
