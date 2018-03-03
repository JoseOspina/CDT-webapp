package cdt.dto;

import java.util.List;

public class PollDetailsDto {
	
	public Integer numberOfAnswers;
	public List<AxisResultsDto> axesResults;

	public Integer getNumberOfAnswers() {
		return numberOfAnswers;
	}

	public void setNumberOfAnswers(Integer numberOfAnswers) {
		this.numberOfAnswers = numberOfAnswers;
	}

	public List<AxisResultsDto> getAxesResults() {
		return axesResults;
	}

	public void setAxesResults(List<AxisResultsDto> axesResults) {
		this.axesResults = axesResults;
	}
	
}
