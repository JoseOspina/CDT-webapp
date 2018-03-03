package cdt.dto;

public class AxisResultsDto {
	
	public String axisId;
	public String axisTitle;
	
	public double minCombined;
	public double meanCombined;
	public double maxCombined;
	
	public String getAxisId() {
		return axisId;
	}
	public void setAxisId(String axisId) {
		this.axisId = axisId;
	}
	public String getAxisTitle() {
		return axisTitle;
	}
	public void setAxisTitle(String axisTitle) {
		this.axisTitle = axisTitle;
	}
	public double getMinCombined() {
		return minCombined;
	}
	public void setMinCombined(double minCombined) {
		this.minCombined = minCombined;
	}
	public double getMeanCombined() {
		return meanCombined;
	}
	public void setMeanCombined(double meanCombined) {
		this.meanCombined = meanCombined;
	}
	public double getMaxCombined() {
		return maxCombined;
	}
	public void setMaxCombined(double maxCombined) {
		this.maxCombined = maxCombined;
	}
	
}
