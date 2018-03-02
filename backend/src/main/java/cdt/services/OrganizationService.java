package cdt.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cdt.dto.AxisDto;
import cdt.dto.GetResult;
import cdt.dto.OrganizationDto;
import cdt.dto.PollDto;
import cdt.dto.PostResult;
import cdt.dto.QuestionDto;
import cdt.entities.AppUser;
import cdt.entities.Axis;
import cdt.entities.Organization;
import cdt.entities.Poll;
import cdt.entities.PollAudience;
import cdt.entities.PollConfig;
import cdt.entities.Question;

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
	
	@Transactional
	public Boolean hasTemplates(UUID orgId) {
		return pollRepository.hasTemplates(orgId);
	}
	
	@Transactional
	public PostResult createPoll(UUID orgId, PollDto pollDto, UUID creatorId) {
		
		Organization organization = organizationRepository.findById(orgId);
		
		Poll poll = new Poll();
		poll.setOrganization(organization);
		poll.setTitle(pollDto.getTitle());
		poll.setDescription(pollDto.getDescription());
		poll.setCreator(appUserRepository.findById(creatorId));
		poll.setCreationDate(new Timestamp(System.currentTimeMillis()));
		poll = pollRepository.save(poll);
		
		PollConfig config = new PollConfig();
		config.setAudience(PollAudience.valueOf(pollDto.getConfig().getAudience()));
		config.setPoll(poll);
		
		config = pollConfigRepository.save(config);
		
		
		for (AxisDto axisDto : pollDto.getAxes()) {
			Axis axis = new Axis();
			axis.setTitle(axisDto.getTitle());
			axis.setDescription(axisDto.getDescription());
			axis = axisRepository.save(axis);
			
			for (QuestionDto questionDto : axisDto.getQuestions()) {
				Question question = new Question();
				question.setText(questionDto.getText());
				
				question = questionRepository.save(question);
				axis.getQuestions().add(question);
			}
			poll.getAxes().add(axis);
		}

		return new PostResult("success", "poll created", poll.getId().toString());
	}
	
	@Transactional
	public GetResult<List<PollDto>> getPollsList(UUID orgId) {
		List<Poll> polls = pollRepository.findByOrganization_Id(orgId);
		
		List<PollDto> pollDtos = new ArrayList<PollDto>();
		
		for (Poll poll : polls) {
			pollDtos.add(poll.toDtoLight());
		}
		
		return new GetResult<List<PollDto>>("success", "organization retrieved", pollDtos);
	}
	
	@Transactional
	public UUID getOrganizationIdFromPollId(UUID pollId) {
		return pollRepository.getOrganizationIdFromPollId(pollId);
	}
	
	@Transactional
	public GetResult<PollDto> getPoll(UUID pollId) {
		Poll poll = pollRepository.findById(pollId);
		
		return new GetResult<PollDto>("success", "organization retrieved", poll.toDtoLight());
	}
	
	
}
