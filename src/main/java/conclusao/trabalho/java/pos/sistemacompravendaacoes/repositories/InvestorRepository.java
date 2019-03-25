package conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Investor;

@Repository
public interface InvestorRepository extends MongoRepository<Investor, String> {
	
	List<Investor> findByName(String name);
	
}
