package cdt.services;

import java.sql.Timestamp;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cdt.dto.OrganizationDto;
import cdt.dto.PostResult;
import cdt.entities.AppUser;
import cdt.entities.Organization;

@Service
public class OrganizationService extends BaseService {

	@Transactional
	public PostResult create(OrganizationDto organtiationDto, UUID creatorId) {
		
		AppUser creator = appUserRepository.findById(creatorId);
		
		Organization organization = new Organization();
		
		organization.setName(organtiationDto.getName());
		organization.setCreator(creator);
		organization.setCreationDate(new Timestamp(System.currentTimeMillis()));
		
		organization = organizationRepository.save(organization);
		
		return new PostResult("success", "organization created", organization.getId().toString());
	}
	
}
