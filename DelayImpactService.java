import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DelayImpactService {

    private List<ChangeOverBooking> changeOverBookings;

    public DelayImpactService() {

        this.changeOverBookings = new ArrayList<>();
    }

    public void addChangeOverBooking(ChangeOverBooking changeOverBooking) {

        changeOverBookings.add(changeOverBooking);
    }

    public String addMinutesToTime(String time, int delayMinutes) {

        LocalTime localTime = LocalTime.parse(time);

        LocalTime newTime = localTime.plusMinutes(delayMinutes);

        return newTime.toString();
    }

    public boolean connectionIsMissed(String newArrival, String connectionDeparture) {

        LocalTime arrivalTime = LocalTime.parse(newArrival);
        LocalTime departureTime = LocalTime.parse(connectionDeparture);

        return arrivalTime.isAfter(departureTime);
    }

    public List<DelayImpact> analyzeDelayImpact(Train delayedTrain, int delayMinutes) {

        List<DelayImpact> impacts = new ArrayList<>();

        for (int i = 0; i < changeOverBookings.size(); i++) {

            ChangeOverBooking currentChangeOverBooking = changeOverBookings.get(i);

            Schedule firstSchedule = currentChangeOverBooking.getFirstSchedule();
            Schedule secondSchedule = currentChangeOverBooking.getSecondSchedule();

            Train firstTrain = firstSchedule.getTrain();

            if (firstTrain.getId() == delayedTrain.getId()) {

                String originalArrival = firstSchedule.getArrival();

                String newArrival = addMinutesToTime(
                        originalArrival,
                        delayMinutes
                );

                String connectionDeparture = secondSchedule.getDeparture();

                boolean missed = connectionIsMissed(
                        newArrival,
                        connectionDeparture
                );

                User user = currentChangeOverBooking
                        .getFirstBooking()
                        .getUser();

                DelayImpact impact = new DelayImpact(
                        user,
                        delayedTrain,
                        secondSchedule.getTrain(),
                        currentChangeOverBooking.getChangeStation(),
                        originalArrival,
                        newArrival,
                        connectionDeparture,
                        missed
                );

                impacts.add(impact);
            }
        }

        return impacts;
    }

    public void showDelayImpact(Train delayedTrain, int delayMinutes) {

        List<DelayImpact> impacts = analyzeDelayImpact(delayedTrain, delayMinutes);

        if (impacts.size() == 0) {

            System.out.println("No changeover passengers are affected by this delay.");
            return;
        }

        System.out.println("\n DELAY IMPACT ANALYSIS ");

        for (int i = 0; i < impacts.size(); i++) {

            DelayImpact impact = impacts.get(i);

            System.out.println("\nPassenger: " + impact.getUser().getName());
            System.out.println("Email: " + impact.getUser().getEmail());

            System.out.println("Delayed train: " + impact.getDelayedTrain().getTrainNr());
            System.out.println("Connection train: " + impact.getConnectionTrain().getTrainNr());
            System.out.println("Change station: " + impact.getChangeStation().getName());

            System.out.println("Original arrival: " + impact.getOriginalArrival());
            System.out.println("New arrival after delay: " + impact.getNewArrival());
            System.out.println("Connection departure: " + impact.getConnectionDeparture());

            if (impact.isConnectionMissed()) {

                System.out.println("Status: CONNECTION MISSED");

                System.out.println("\nEMAIL NOTIFICATION:");
                System.out.println("To: " + impact.getUser().getEmail());
                System.out.println("Hello " + impact.getUser().getName());
                System.out.println("Your first train " + impact.getDelayedTrain().getTrainNr()
                        + " is delayed.");
                System.out.println("Because of this delay, you may miss your connection train "
                        + impact.getConnectionTrain().getTrainNr()
                        + " at "
                        + impact.getChangeStation().getName()
                        + ".");
                System.out.println("==============================");

            }
            else {

                System.out.println("Status: CONNECTION STILL POSSIBLE");
            }
        }
    }
}