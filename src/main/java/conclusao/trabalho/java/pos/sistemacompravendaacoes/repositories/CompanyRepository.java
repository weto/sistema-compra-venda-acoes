package conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Action;
import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Company;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

	List<Action> findByName(String nameInvestor);

	@Query(value = "{'$and': [ {'name':?0}, {'actions.investor.name':?1} ]}")
	List<Company> findByInvestorName(String nameCompany, String nameInvestor);

	@Query(value = "{'$and': [ {'name':?0}, {'actions.owner':?0} ]}")
	List<Company> findByActionCompany(String nameCompany);

	@Query(value = "{'actions.investor.name': ?0}}")
	List<Company> findByActionInvestor(String name);
	
}
