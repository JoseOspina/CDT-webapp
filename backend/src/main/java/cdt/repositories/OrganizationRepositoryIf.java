package cdt.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import cdt.entities.Organization;

public interface OrganizationRepositoryIf extends CrudRepository<Organization, UUID> {
	
}
