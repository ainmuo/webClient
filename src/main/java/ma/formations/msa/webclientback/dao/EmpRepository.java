package ma.formations.msa.webclientback.dao;

import ma.formations.msa.webclientback.model.Emp;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
@Repository
public interface EmpRepository extends ReactiveCrudRepository<Emp, Long> {
    Flux<Emp> findByNameContaining(String name);
    Flux<Emp> findBySalaireBetween(Double min, Double max);
}