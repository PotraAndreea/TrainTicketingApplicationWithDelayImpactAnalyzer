public class ChangeOverBooking {

    private Booking firstBooking;
    private Booking secondBooking;
    private Schedule firstSchedule;
    private Schedule secondSchedule;
    private Station changeStation;

    public ChangeOverBooking(
            Booking firstBooking,
            Booking secondBooking,
            Schedule firstSchedule,
            Schedule secondSchedule,
            Station changeStation
    ) {

        this.firstBooking = firstBooking;
        this.secondBooking = secondBooking;
        this.firstSchedule = firstSchedule;
        this.secondSchedule = secondSchedule;
        this.changeStation = changeStation;
    }

    public Booking getFirstBooking() {
        return firstBooking;
    }

    public Booking getSecondBooking() {
        return secondBooking;
    }

    public Schedule getFirstSchedule() {
        return firstSchedule;
    }

    public Schedule getSecondSchedule() {
        return secondSchedule;
    }

    public Station getChangeStation() {
        return changeStation;
    }
}