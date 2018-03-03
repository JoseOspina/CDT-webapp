package cdt.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cdt.dto.AnswerDto;
import cdt.dto.AxisDto;
import cdt.dto.AxisResultsDto;
import cdt.dto.GetResult;
import cdt.dto.OrganizationDto;
import cdt.dto.PollDetailsDto;
import cdt.dto.PollDto;
import cdt.dto.PostResult;
import cdt.dto.QuestionDto;
import cdt.entities.Answer;
import cdt.entities.AnswerBatch;
import cdt.entities.AppUser;
import cdt.entities.Axis;
import cdt.entities.Organization;
import cdt.entities.Poll;
import cdt.entities.PollAudience;
import cdt.entities.PollConfig;
import cdt.entities.Question;
import cdt.entities.ResponderType;

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
	public PollDto getPollDto(UUID pollId) {
		Poll poll = pollRepository.findById(pollId);
		return poll.toDto();
	}
	
	@Transactional
	public GetResult<PollDto> getPoll(UUID pollId) {
		return new GetResult<PollDto>("success", "organization retrieved", getPollDto(pollId));
	}
	
	@Transactional
	public GetResult<PollDetailsDto> getPollDetails(UUID pollId) {
		PollDetailsDto pollDetailsDto = new PollDetailsDto();
		
		pollDetailsDto.setNumberOfAnswers(answerBatchRepository.countNAnswers(pollId));
		pollDetailsDto.setAxesResults(getAxesResults(pollId));
		
		return new GetResult<PollDetailsDto>("success", "organization retrieved", pollDetailsDto);
	}
	

	@Transactional
	public List<AxisResultsDto> getAxesResults(UUID pollId) {
		
		Poll poll = pollRepository.findById(pollId);
		
		for (Axis axis : poll.getAxes()) {
			for (Question question : axis.getQuestions()) {
				
				List<Integer> rates = answerBatchRepository.getQuestionRates(poll.getId(), question.getId());
				
				double average = 0;
				double min = 500;
				double max = -1;
				
				for (Integer rate : rates) {
					double rateDouble = (double) rate;
					
					average += rateDouble;
					
					if (rateDouble < min) {
						min = rateDouble;
					}
					
					if (rateDouble > max) {
						max = rateDouble;
					}
				}
				
				average = average/((double) rates.size());
			}
		}
		
		return new ArrayList<AxisResultsDto>();
	}
	
	@Transactional
	public PollAudience getPollAudience(UUID pollId) {
		return pollRepository.findById(pollId).getConfig().getAudience();
	}
	
	@Transactional
	public PostResult answerPoll(UUID pollId, List<AnswerDto> answersDto, ResponderType responderType) {
		
		Poll poll = pollRepository.findById(pollId);
		
		if (poll == null) {
			return new PostResult("error", "poll not found", null);
		}
		
		AnswerBatch batch = new AnswerBatch();
		
		batch.setResponderType(responderType);
		batch.setPoll(poll);
		batch = answerBatchRepository.save(batch);
		
		for (AnswerDto answerDto : answersDto) {
			
			Question question = questionRepository.findById(UUID.fromString(answerDto.getQuestionId()));
			if (question == null) {
				return new PostResult("error", "question not found", null);
			}
			
			Answer answer = new Answer();
			
			answer.setBatch(batch);
			answer.setQuestion(question);
			answer.setText(answerDto.getText());
			answer.setRate(answerDto.getRate());
			
			answer = answerRepository.save(answer);
			batch.getAnswers().add(answer);
		}
		
		return new PostResult("success", "poll answered", batch.getId().toString());
	}
}
