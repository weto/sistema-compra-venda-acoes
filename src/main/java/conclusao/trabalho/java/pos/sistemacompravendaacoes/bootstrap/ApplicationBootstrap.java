package conclusao.trabalho.java.pos.sistemacompravendaacoes.bootstrap;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Action;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.listeners.MessageListener;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories.ActionRepository;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories.CompanyRepository;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories.InvestorRepository;

@Component
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private CompanyRepository companyRepository;
	private ActionRepository actionRepository;
	private InvestorRepository investorRepository;

	static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
	
	public ApplicationBootstrap(CompanyRepository companyRespository, ActionRepository actionRepository, InvestorRepository investorRepository) {
		this.companyRepository = companyRespository;
		this.actionRepository = actionRepository;
		this.investorRepository = investorRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (investorRepository.count() == 0L) {
			investorRepository.deleteAll();
			loadInvestors();
		}

		if (actionRepository.count() == 0L) {
			actionRepository.deleteAll();
			loadActions();
		}

		if (companyRepository.count() == 0L) {
			companyRepository.deleteAll();
			loadCompanies("Google Corp");
			loadCompanies("Google Corp SA");
		}
		
	}
	
	private void loadCompanies(String name) {
		Action action = actionRepository.findByOwner("Wellington");
		List<Action> actions = new ArrayList<>();
		actions.add(action);
		
        Company cp1 = new Company();
        cp1.setName(name);
        cp1.setActions(actions);
        
        companyRepository.save(cp1);
    }
	
	private void loadActions() {
		List<Investor> investor = investorRepository.findByName("Wellington");

		Action action = new Action();
		action.setValue("45.00");
		action.setSell("false");
		action.setOwner(investor.get(0).getName());
		action.setInvestor(investor.get(0));
		actionRepository.save(action);
    }

	private void loadInvestors() {
		Investor investor = new Investor();
		investor.setName("Wellington");
		investor.setEmail("weto.jc@gmail.com");
		investorRepository.save(investor);
    }
}
