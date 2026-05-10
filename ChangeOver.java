
public class ChangeOver {
	
	private Schedule firstSchedule;
	private Schedule secondSchedule;
	private Station changeStation;
	
	public ChangeOver(Schedule firstSchedule, Schedule secondSchedule, Station changeStation ) {
		
		this.firstSchedule = firstSchedule;
		this.secondSchedule = secondSchedule;
		this.changeStation = changeStation;
	}
	
	public Schedule getFirst() {
		return firstSchedule;
	}
	
	public Schedule getSecond() {
		return secondSchedule;
	}
	
	public Station getChange() {
		return changeStation;
	}
}
