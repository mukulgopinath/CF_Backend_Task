package transactions.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import transactions.model.TransactionModel;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionModel, Long> {

	List<TransactionModel> findTransactionByUserId(@Param("userId") Long id);

}
