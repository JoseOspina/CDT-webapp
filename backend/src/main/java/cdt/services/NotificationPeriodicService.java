package cdt.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cdt.entities.Poll;
import cdt.repositories.PollRepositoryIf;

@Service
public class NotificationPeriodicService {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PollRepositoryIf pollRepository;
	
	
	@Scheduled(fixedDelay = 30000)
	public void checkWantToContributeNow() throws IOException {
		List<Poll> polls = pollRepository.findNotSent();
		
		emailService.sendPollsNotifications(polls);
	}
	
}
