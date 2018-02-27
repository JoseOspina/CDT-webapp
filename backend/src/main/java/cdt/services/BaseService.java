package cdt.services;

import org.springframework.beans.factory.annotation.Autowired;

import cdt.repositories.AppUserRepositoryIf;
import cdt.repositories.OrganizationRepositoryIf;

public class BaseService {
	
	@Autowired
	protected AppUserRepositoryIf appUserRepository;
	
	@Autowired
	protected OrganizationRepositoryIf organizationRepository;

}
