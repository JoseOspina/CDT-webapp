package cdt.controllers;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdt.dto.GetResult;
import cdt.dto.OrganizationDto;
import cdt.dto.PostResult;

@RestController
@RequestMapping("/1")
public class OrganizationsController extends BaseController {
	
	@RequestMapping(path = "/organization/create",  method = RequestMethod.POST)
    public PostResult createOrganization(@RequestBody OrganizationDto organizationDto) {
		
		if (getLoggedUser() == null) {
			return new PostResult("error", "endpoint enabled for users only", null);
		}
		
		return organizationService.create(organizationDto, getLoggedUserId());
	}
	
	@RequestMapping(path = "/organization/{organizationId}",  method = RequestMethod.GET)
    public GetResult<OrganizationDto> getOrganization(@PathVariable(name="organizationId") String orgIdStr) {
		
		if (getLoggedUser() == null) {
			return new GetResult<OrganizationDto>("error", "endpoint enabled for users only", null);
		}
		
		UUID orgId = UUID.fromString(orgIdStr);
		
		if (!organizationService.isAdmin(orgId, getLoggedUserId())) {
			return new GetResult<OrganizationDto>("error", "endpoint enabled for admins only", null);
		}
		
		return organizationService.get(orgId);
	}

}
