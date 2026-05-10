import java.util.ArrayList;
import java.util.List;

public class RouteService {

    private List<Schedule> schedules;

    public RouteService() {

        schedules = new ArrayList<>();
    }

    public void addSchedule(Schedule schedule) {

        schedules.add(schedule);
    }

    public List<Schedule> directRoutes(Station departure, Station destination, String date) {

        List<Schedule> matchingSchedules = new ArrayList<>();

        for (int i = 0; i < schedules.size(); i++) {

            Schedule schedule = schedules.get(i);

            Route route = schedule.getRoutes();

            if (route.orderOfStations(departure, destination)
                    && schedule.getDate().equals(date)) {

                matchingSchedules.add(schedule);
            }
        }

        return matchingSchedules;
    }

    public List<ChangeOver> changeOver(Station departure, Station destination, String date) {

        List<ChangeOver> matchingChangeOvers = new ArrayList<>();

        for (int i = 0; i < schedules.size(); i++) {

            Schedule firstSchedule = schedules.get(i);

            if (!firstSchedule.getDate().equals(date)) {
                continue;
            }

            Route firstRoute = firstSchedule.getRoutes();

            List<Station> firstRouteStations = firstRoute.getStations();

            for (int j = 0; j < firstRouteStations.size(); j++) {

                Station possibleChangeStation = firstRouteStations.get(j);

                boolean firstPartPossible = firstRoute.orderOfStations(
                        departure,
                        possibleChangeStation
                );

                if (firstPartPossible) {

                    for (int k = 0; k < schedules.size(); k++) {

                        Schedule secondSchedule = schedules.get(k);

                        if (!secondSchedule.getDate().equals(date)) {
                            continue;
                        }

                        if (firstSchedule.getId() == secondSchedule.getId()) {
                            continue;
                        }

                        Route secondRoute = secondSchedule.getRoutes();

                        boolean secondPartPossible = secondRoute.orderOfStations(
                                possibleChangeStation,
                                destination
                        );

                        if (secondPartPossible) {

                            boolean timeIsOk = firstSchedule.getArrival().compareTo(secondSchedule.getDeparture()) <= 0;

                            if (timeIsOk) {

                                ChangeOver changeOver = new ChangeOver( firstSchedule,secondSchedule,possibleChangeStation);

                                matchingChangeOvers.add(changeOver);
                            }
                        }
                    }
                }
            }
        }

        return matchingChangeOvers;
    }
}