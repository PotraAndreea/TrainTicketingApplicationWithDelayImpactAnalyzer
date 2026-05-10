
public class Ticket {

    private int id;

    private Train train;

    private Station departureStation;

    private Station destinationStation;

    private int seatNr;

    public Ticket(
            int id,
            Train train,
            Station departureStation,
            Station destinationStation,
            int seatNr
    ) {

        this.id = id;
        this.train = train;
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.seatNr = seatNr;
    }

    public int getId() {
        return id;
    }

    public Train getTrain() {
        return train;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public int getSeatNr() {
        return seatNr;
    }
}