package cdt.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cdt.entities.AnswerBatch;

public interface AnswerBatchRepositoryIf extends CrudRepository<AnswerBatch, UUID> {
	
	@Query("SELECT COUNT(batch) FROM AnswerBatch batch WHERE batch.poll.id = ?1")
	public Integer countNAnswersInternal(UUID pollId);
	
	default Integer countNAnswers(UUID pollId) {
		Integer res = countNAnswersInternal(pollId);
		return res == null ? 0 : res.intValue();
	}
}
