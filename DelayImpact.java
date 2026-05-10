public class DelayImpact {

    private User user;
    private Train delayedTrain;
    private Train connectionTrain;
    private Station changeStation;
    private String originalArrival;
    private String newArrival;
    private String connectionDeparture;
    private boolean connectionMissed;

    public DelayImpact(
            User user,
            Train delayedTrain,
            Train connectionTrain,
            Station changeStation,
            String originalArrival,
            String newArrival,
            String connectionDeparture,
            boolean connectionMissed
    ) {

        this.user = user;
        this.delayedTrain = delayedTrain;
        this.connectionTrain = connectionTrain;
        this.changeStation = changeStation;
        this.originalArrival = originalArrival;
        this.newArrival = newArrival;
        this.connectionDeparture = connectionDeparture;
        this.connectionMissed = connectionMissed;
    }

    public User getUser() {
        return user;
    }

    public Train getDelayedTrain() {
        return delayedTrain;
    }

    public Train getConnectionTrain() {
        return connectionTrain;
    }

    public Station getChangeStation() {
        return changeStation;
    }

    public String getOriginalArrival() {
        return originalArrival;
    }

    public String getNewArrival() {
        return newArrival;
    }

    public String getConnectionDeparture() {
        return connectionDeparture;
    }

    public boolean isConnectionMissed() {
        return connectionMissed;
    }
}