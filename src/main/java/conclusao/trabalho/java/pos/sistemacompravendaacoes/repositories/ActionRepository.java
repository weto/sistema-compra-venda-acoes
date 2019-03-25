package conclusao.trabalho.java.pos.sistemacompravendaacoes.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import conclusao.trabalho.java.pos.sistemacompravendaacoes.domain.Action;

@Repository
public interface ActionRepository extends MongoRepository<Action, String> {
	
	Action findByOwner(String name);
	
}
