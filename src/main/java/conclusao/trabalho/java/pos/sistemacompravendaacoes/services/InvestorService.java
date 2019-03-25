package conclusao.trabalho.java.pos.sistemacompravendaacoes.services;

import java.util.List;
import java.util.Set;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;

public interface InvestorService {

	Set<Investor> getAll();
	
	Investor getInvestorById(String id);
	
	Investor createNewInvestor(Investor Investor);

	Investor saveInvestor(String id, Investor Investor);

	void deleteInvestorById(String id);

	List<Company> getActionByInvestor(Investor investor);

	List<Company> getActionCompanyByInvestor(String id, String nameCompany);
}
