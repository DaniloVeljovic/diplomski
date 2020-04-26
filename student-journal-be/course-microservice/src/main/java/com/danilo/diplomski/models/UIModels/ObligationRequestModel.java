package com.danilo.diplomski.models.UIModels;

import java.time.LocalDateTime;

public class ObligationRequestModel {
	private LocalDateTime date;

	private String type;

	private String description;

	private String obligationID;

	public String getObligationID() {
		return obligationID;
	}

	public void setObligationID(String obligationID) {
		this.obligationID = obligationID;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
