package application;
import javafx.beans.property.SimpleStringProperty;

public class Badge {
	@Override
	public String toString() {
		return badgeNumber.get() + "\t" + badgeType.get() + "\t" + badgeNote.get()
				+ "\t" + badgeStatus.get();
	}
	private SimpleStringProperty badgeNumber;
	private SimpleStringProperty badgeType;
	private SimpleStringProperty badgeNote;
	private SimpleStringProperty badgeStatus;

	public Badge(String badgeNumber, String badgeType, String status,String badgeNotes) {
		super();
		this.badgeNumber = new SimpleStringProperty(badgeNumber);
		this.badgeType = new SimpleStringProperty(badgeType);
		this.badgeNote = new SimpleStringProperty(badgeNotes);
		this.badgeStatus = new SimpleStringProperty(status);
	}
	
	public String getBadgeStatus() {
		return this.badgeStatus.get();
	}
	public String getBadgeNumber() {
		return this.badgeNumber.get();
	}
	public String getBadgeType() {
		return this.badgeType.get();
	}
	public String getBadgeNote() {
		return this.badgeNote.get();
	}
	
	public void setBadgeStatus(String badgeStatus) {
		this.badgeStatus.set(badgeStatus);
	}
	public void setBadgeNumber(String badgeNumber) {
		this.badgeNumber.set(badgeNumber);
	}
	public void setBadgeType(String badgeType) {
		this.badgeType.set(badgeType);
	}
	public void setBadgeNote(String badgeNote) {
		this.badgeNote.set(badgeNote);
	}


	
}
