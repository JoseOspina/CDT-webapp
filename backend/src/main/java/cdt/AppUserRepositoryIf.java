package cdt;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepositoryIf extends CrudRepository<AppUser, UUID> {
	
	AppUser findById(UUID id);
	
	@Query("SELECT us FROM AppUser us JOIN us.auth0Ids auth WHERE auth = ?1")
	AppUser findByAuth0Id(String auth0Id);
	
	AppUser findByEmail(String email);	
	
	List<AppUser> findTop10ByProfile_NicknameLikeIgnoreCase(String q);
}
