package cdt.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import cdt.entities.Poll;

public interface PollRepositoryIf extends CrudRepository<Poll, UUID> {
	
	
	public Poll findById(UUID id);
	
	@Query("SELECT COUNT(po) FROM Poll po WHERE po.isTemplate = TRUE OR po.organization.id = ?1")
	public Integer countNTemplatesInternal(UUID orgId);
	
	public List<Poll> findByOrganization_Id(UUID orgId);
	
	default Boolean hasTemplates(UUID orgId) {
		Integer res = countNTemplatesInternal(orgId);
		return res == null ? false : res.intValue() > 0;
	}
	
}
