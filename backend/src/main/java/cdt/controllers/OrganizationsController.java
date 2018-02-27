package cdt.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cdt.dto.OrganizationDto;
import cdt.dto.PostResult;

@RestController
@RequestMapping("/1")
public class OrganizationsController extends BaseController {
	
	@RequestMapping(path = "/organization/create",  method = RequestMethod.POST)
    public PostResult createOrganization(@RequestBody OrganizationDto organizationDto) {
		
		if (getLoggedUser() == null) {
			return new PostResult("error", "endpoint enabled users only", null);
		}
		
		return organizationService.create(organizationDto, getLoggedUserId());
	}

}
