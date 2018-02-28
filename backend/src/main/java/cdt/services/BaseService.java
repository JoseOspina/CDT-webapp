package cdt.services;

import org.springframework.beans.factory.annotation.Autowired;

import cdt.repositories.AppUserRepositoryIf;
import cdt.repositories.OrganizationRepositoryIf;
import cdt.repositories.PollRepositoryIf;

public class BaseService {
	
	@Autowired
	protected AppUserRepositoryIf appUserRepository;
	
	@Autowired
	protected OrganizationRepositoryIf organizationRepository;
	
	@Autowired
	protected PollRepositoryIf pollRepository;

}
