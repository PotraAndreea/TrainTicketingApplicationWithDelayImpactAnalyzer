import java.util.ArrayList;
import java.util.List;

public class Route {

    private int id;
    private String routeName;
    private List<Station> stations;

    public Route(int id, String routeName) {

        this.id = id;
        this.routeName = routeName;
        this.stations = new ArrayList<>();
    }

    public void addStation(Station station) {

        stations.add(station);
    }

    public void removeStation(Station station) {

        for (int i = 0; i < stations.size(); i++) {

            if (stations.get(i).getId() == station.getId()) {

                stations.remove(i);
                return;
            }
        }
    }

    public void setRouteName(String routeName) {

        this.routeName = routeName;
    }

    public int getId() {
        return id;
    }

    public String getRouteName() {
        return routeName;
    }

    public List<Station> getStations() {
        return stations;
    }

    public boolean orderOfStations(Station departure, Station destination) {

        int departurePosition = -1;
        int destinationPosition = -1;

        for (int i = 0; i < stations.size(); i++) {

            if (stations.get(i).getName().equalsIgnoreCase(departure.getName())) {

                departurePosition = i;
            }

            if (stations.get(i).getName().equalsIgnoreCase(destination.getName())) {

                destinationPosition = i;
            }
        }

        if (departurePosition == -1 || destinationPosition == -1) {

            return false;
        }

        return destinationPosition > departurePosition;
    }
}