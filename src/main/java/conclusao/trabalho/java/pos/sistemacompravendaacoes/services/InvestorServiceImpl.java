package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories.InvestorRepository;

@Service
public class InvestorServiceImpl implements InvestorService {
	
	private InvestorRepository investorRepository;
	private CompanyService companyService;
	
	public InvestorServiceImpl(InvestorRepository investorRepository, CompanyService companyService) {
		this.investorRepository = investorRepository;
		this.companyService = companyService;
	}

	@Override
	public Set<Investor> getAll() {
		Set<Investor> investors = new HashSet<>();
		this.investorRepository.findAll().iterator().forEachRemaining(investors::add);
		return investors;
	}

	@Override
	public Investor getInvestorById(String id) {
		Optional<Investor> optionalInvestor = investorRepository.findById(id);
		
		if (!optionalInvestor.isPresent()) {
			throw new IllegalArgumentException("Investor Not Found ID");
		}
		
		return optionalInvestor.get();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Investor createNewInvestor(Investor investor) {
		if(investorRepository.findByName(investor.getName()) != null) {
			Investor investorSaved = investorRepository.save(investor);
			return investorSaved;
		} else {
			throw new IllegalArgumentException("Investor alread Exists with name " + investor.getName());
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Investor saveInvestor(String id, Investor investor) {
		investor.setId(id);
		
		Optional<Investor> investorOptional = investorRepository.findById(id);
		
		if(!investorOptional.isPresent()) {
			throw new IllegalArgumentException("Investor Not Found ID");
		}
		
		List<Company> companies = companyService.getUpdateByActionInvestor(investorOptional.get(), investor);
		
		companies.forEach(company -> {
			companyService.saveCompany(company.getId(), company);
		});
		
		return investorRepository.save(investor);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteInvestorById(String id) {
		Optional<Investor> investorOptional = investorRepository.findById(id);
		if(!investorOptional.isPresent()) {
			throw new IllegalArgumentException("Investor Not Found ID");
		} else {
			List<Company> companies = companyService.getByActionInvestor(investorOptional.get());
			if(companies.size() > 0) {
				throw new IllegalArgumentException("Investor can not be deleted");
			}
		}
		
		investorRepository.deleteById(id);
	}

	@Override
	public List<Company> getActionByInvestor(Investor investor) {
		 return companyService.getByActionInvestor(investor);
	}

	@Override
	public List<Company> getActionCompanyByInvestor(String id, String nameCompany) {
		Optional<Investor> investorOptional = investorRepository.findById(id);
		if(!investorOptional.isPresent()) {
			throw new IllegalArgumentException("Investor Not Found ID");
		}
		return companyService.getByCompanyInvestor(nameCompany, investorOptional.get().getName());
	}

}
