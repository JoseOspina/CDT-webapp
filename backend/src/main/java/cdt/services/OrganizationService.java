package cdt.services;

import java.sql.Timestamp;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cdt.dto.GetResult;
import cdt.dto.OrganizationDto;
import cdt.dto.PostResult;
import cdt.entities.AppUser;
import cdt.entities.Organization;

@Service
public class OrganizationService extends BaseService {

	@Transactional
	public PostResult create(OrganizationDto organizationDto, UUID creatorId) {
		
		AppUser creator = appUserRepository.findById(creatorId);
		
		Organization organization = new Organization();
		
		organization.setName(organizationDto.getName());
		organization.setDescription(organizationDto.getDescription());
		organization.setCreator(creator);
		organization.getAdmins().add(creator);
		organization.setCreationDate(new Timestamp(System.currentTimeMillis()));
		
		organization = organizationRepository.save(organization);
		
		return new PostResult("success", "organization created", organization.getId().toString());
	}
	
	@Transactional
	public Boolean isAdmin(UUID orgId, UUID creatorId) {
		return organizationRepository.findByIdAndAdmins_Id(orgId, creatorId) != null; 
	}
	
	@Transactional
	public GetResult<OrganizationDto> get(UUID orgId) {
		Organization org = organizationRepository.findById(orgId);
		
		OrganizationDto orgDto = org.toDto();
		
		/* add polls */
		
		return new GetResult<OrganizationDto>("success", "organization retrieved", orgDto);
	}
	
}
