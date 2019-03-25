package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Action;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	private CompanyRepository companyRepository;
	
	public CompanyServiceImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
	@Override
	public Set<Company> getAll() {
		Set<Company> companies = new HashSet<>();
		this.companyRepository.findAll().iterator().forEachRemaining(companies::add);
		return companies;
	}

	@Override
	public Company getCompanyById(String id) {
		return getById(id);
	}
	
	public Company getById(String id) {
		Optional<Company> companyOptional = companyRepository.findById(id);
		
		if(!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Company Not Found For ID value" + id.toString());
		}
		return companyOptional.get();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Company createNewCompany(Company company) {
		if(companyRepository.findByName(company.getName()).isEmpty()) {
			Company companySaved = companyRepository.save(company);
			return companySaved;
		} else {
			throw new IllegalArgumentException("Company alread Exists with name " + company.getName());
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Company saveCompany(String id, Company company) {
		company.setId(id);
		return companyRepository.save(company);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCompanyById(String id) {
		companyRepository.deleteById(id);
	}

	@Override
	public List<Company> getByCompanyInvestor(String nameCompany, String owner) {
		List<Company> companies = companyRepository.findByInvestorName(nameCompany, owner);
		companies.iterator().forEachRemaining(company -> {
			List<Action> actions = company.getActions().stream()
				.filter((action) -> action.getOwner().equals(owner))
				.collect(Collectors.toList());
			company.setActions(actions);
		});
		return companies;
	}

	@Override
	public List<Company> getByActionCompany(String nameCompany) {
		List<Company> companies = companyRepository.findByActionCompany(nameCompany);
		companies.iterator().forEachRemaining(company -> {
			List<Action> actions = company.getActions().stream()
				.filter((action) -> action.getId()==null)
				.collect(Collectors.toList());
			company.setActions(actions);
		});
		return companies;
	}

	@Override
	public List<Company> getByActionInvestor(Investor investor) {
		List<Company> companies = companyRepository.findByActionInvestor(investor.getName());
		companies.iterator().forEachRemaining(company -> {
			List<Action> actions = company.getActions().stream()
				.filter((action) -> action.getId()!=null)
				.collect(Collectors.toList());
			company.setActions(actions);
		});
		return companies;
	}

	@Override
	public List<Company> getUpdateByActionInvestor(Investor investoroOrigin, Investor investor) {
		List<Company> companies = companyRepository.findByActionInvestor(investoroOrigin.getName());
		companies.iterator().forEachRemaining(company -> {
			List<Action> actions = company.getActions().stream()
				.filter((action) -> {
					if(action.getId()!=null) {
						action.setOwner(investor.getName());
						action.setInvestor(investor);
					}
					return true;
				})
				.collect(Collectors.toList());
			company.setActions(actions);
		});
		return companies;
	}

}
