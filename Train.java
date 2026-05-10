public class Train {

    private int id;
    private String trainNr;
    private int capacity;
    private Route route;
    private boolean delayed;
    private int delayMinutes;

    public Train(int id, String trainNr, int capacity, Route route) {

        this.id = id;
        this.trainNr = trainNr;
        this.capacity = capacity;
        this.route = route;

        this.delayed = false;
        this.delayMinutes = 0;
    }

    public int getId() {
        return id;
    }

    public String getTrainNr() {
        return trainNr;
    }

    public int getCapacity() {
        return capacity;
    }

    public Route getRoute() {
        return route;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }

    public void setTrainNr(String trainNr) {

        this.trainNr = trainNr;
    }

    public void setCapacity(int capacity) {

        this.capacity = capacity;
    }

    public void setRoute(Route route) {

        this.route = route;
    }

    public void setDelay(boolean delayed, int minutes) {

        this.delayed = delayed;
        this.delayMinutes = minutes;
    }
}