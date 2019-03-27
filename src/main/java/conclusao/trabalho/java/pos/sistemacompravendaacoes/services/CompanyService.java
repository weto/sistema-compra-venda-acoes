package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.List;
import java.util.Set;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.CompanyDTO;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;

public interface CompanyService {
	
	Set<Company> getAll();
	
	Company getCompanyById(String id);
	
	Company createNewCompany(Company company);

	Company saveCompany(String id, CompanyDTO companyDTO);
	
	Company updateCompany(String id, Company company);
	
	boolean validData(Company company);

	void deleteCompanyById(String id);

	List<Company> getByCompanyInvestor(String nameCompany, String owner);

	List<Company> getByActionCompany(String nameCompany);
	
	List<Company> getByActionInvestor(Investor investor);

	List<Company> getUpdateByActionInvestor(Investor investoroOrigin, Investor investor);

}
