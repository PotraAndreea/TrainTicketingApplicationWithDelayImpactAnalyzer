import java.util.ArrayList;
import java.util.List;

public class AdminService {

    private List<Route> routes;
    private List<Train> trains;
    private Services services;
    private Email emailService;

    public AdminService(Services services) {

        this.routes = new ArrayList<>();
        this.trains = new ArrayList<>();
        this.services = services;
        this.emailService = new Email();
    }

    // ================= ROUTE METHODS =================

    public void addRoute(Route route) {

        routes.add(route);

        System.out.println("Route added successfully.");
    }

    public void removeRoute(int routeId) {

        for (int i = 0; i < routes.size(); i++) {

            if (routes.get(i).getId() == routeId) {

                routes.remove(i);
                System.out.println("Route removed successfully.");
                return;
            }
        }

        System.out.println("Route not found.");
    }

    public void modifyRouteName(int routeId, String newRouteName) {

        Route route = findRouteById(routeId);

        if (route == null) {

            System.out.println("Route not found.");
            return;
        }

        route.setRouteName(newRouteName);

        System.out.println("Route modified successfully.");
    }

    public void addStationToRoute(int routeId, Station station) {

        Route route = findRouteById(routeId);

        if (route == null) {

            System.out.println("Route not found.");
            return;
        }

        route.addStation(station);

        System.out.println("Station added to route successfully.");
    }

    public void removeStationFromRoute(int routeId, Station station) {

        Route route = findRouteById(routeId);

        if (route == null) {

            System.out.println("Route not found.");
            return;
        }

        route.removeStation(station);

        System.out.println("Station removed from route successfully.");
    }

    public Route findRouteById(int routeId) {

        for (int i = 0; i < routes.size(); i++) {

            Route currentRoute = routes.get(i);

            if (currentRoute.getId() == routeId) {

                return currentRoute;
            }
        }

        return null;
    }

    public void showRoutes() {

        if (routes.size() == 0) {

            System.out.println("No routes available.");
            return;
        }

        for (int i = 0; i < routes.size(); i++) {

            Route route = routes.get(i);

            System.out.println("Route ID: " + route.getId());
            System.out.println("Route name: " + route.getRouteName());
            System.out.println("Stations:");

            List<Station> stations = route.getStations();

            for (int j = 0; j < stations.size(); j++) {

                System.out.println("- " + stations.get(j).getName());
            }

            System.out.println();
        }
    }


    public void addTrain(Train train) {

        trains.add(train);

        System.out.println("Train added successfully.");
    }

    public void removeTrain(int trainId) {

        for (int i = 0; i < trains.size(); i++) {

            if (trains.get(i).getId() == trainId) {

                trains.remove(i);
                System.out.println("Train removed successfully.");
                return;
            }
        }

        System.out.println("Train not found.");
    }

    public void modifyTrain(int trainId, String newTrainNr, int newCapacity, Route newRoute) {

        Train train = findTrainById(trainId);

        if (train == null) {

            System.out.println("Train not found.");
            return;
        }

        train.setTrainNr(newTrainNr);
        train.setCapacity(newCapacity);
        train.setRoute(newRoute);

        System.out.println("Train modified successfully.");
    }

    public Train findTrainById(int trainId) {

        for (int i = 0; i < trains.size(); i++) {

            Train currentTrain = trains.get(i);

            if (currentTrain.getId() == trainId) {

                return currentTrain;
            }
        }

        return null;
    }

    public void showTrains() {

        if (trains.size() == 0) {

            System.out.println("No trains available.");
            return;
        }

        for (int i = 0; i < trains.size(); i++) {

            Train train = trains.get(i);

            System.out.println("Train ID: " + train.getId());
            System.out.println("Train number: " + train.getTrainNr());
            System.out.println("Capacity: " + train.getCapacity());
            System.out.println("Route: " + train.getRoute().getRouteName());

            if (train.isDelayed()) {

                System.out.println("Status: delayed by " + train.getDelayMinutes() + " minutes");
            }
            else {

                System.out.println("Status: on time");
            }

            System.out.println();
        }
    }


    public void showBookingsForTrain(int trainId) {

        List<Booking> bookings = services.getBookings();

        boolean found = false;

        for (int i = 0; i < bookings.size(); i++) {

            Booking booking = bookings.get(i);

            List<Ticket> tickets = booking.getTickets();

            boolean bookingContainsTrain = false;

            for (int j = 0; j < tickets.size(); j++) {

                Ticket ticket = tickets.get(j);

                if (ticket.getTrain().getId() == trainId) {

                    bookingContainsTrain = true;
                }
            }

            if (bookingContainsTrain) {

                found = true;

                System.out.println("Booking ID: " + booking.getId());
                System.out.println("Customer name: " + booking.getUser().getName());
                System.out.println("Customer email: " + booking.getUser().getEmail());
                System.out.println("From: " + booking.getDeparture().getName());
                System.out.println("To: " + booking.getDestination().getName());
                System.out.println("Number of tickets: " + booking.getTickets().size());
                System.out.println();
            }
        }

        if (!found) {

            System.out.println("No bookings found for this train.");
        }
    }


    public void setTrainDelay(int trainId, int delayMinutes) {

        Train train = findTrainById(trainId);

        if (train == null) {

            System.out.println("Train not found.");
            return;
        }

        train.setDelay(true, delayMinutes);

        System.out.println("Train " + train.getTrainNr() + " was marked as delayed.");

        notifyCustomersAboutDelay(train);
    }

    private void notifyCustomersAboutDelay(Train train) {

        List<Booking> bookings = services.getBookings();

        for (int i = 0; i < bookings.size(); i++) {

            Booking booking = bookings.get(i);

            List<Ticket> tickets = booking.getTickets();

            boolean customerHasTicketForThisTrain = false;

            for (int j = 0; j < tickets.size(); j++) {

                Ticket ticket = tickets.get(j);

                if (ticket.getTrain().getId() == train.getId()) {

                    customerHasTicketForThisTrain = true;
                }
            }

            if (customerHasTicketForThisTrain) {

                emailService.delayEmail(booking.getUser(), train);
            }
        }
    }
}