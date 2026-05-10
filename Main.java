import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ================= STATIONS =================

        List<Station> allStations = new ArrayList<>();

        Station s1 = new Station(1, "Bucharest");
        Station s2 = new Station(2, "Brasov");
        Station s3 = new Station(3, "Cluj");
        Station s4 = new Station(4, "Sibiu");

        allStations.add(s1);
        allStations.add(s2);
        allStations.add(s3);
        allStations.add(s4);

        int nextStationId = 5;

        // ================= ROUTES =================

        Route route1 = new Route(1, "Bucharest - Brasov - Cluj");

        route1.addStation(s1);
        route1.addStation(s2);
        route1.addStation(s3);

        Route route2 = new Route(2, "Brasov - Sibiu");

        route2.addStation(s2);
        route2.addStation(s4);

        int nextRouteId = 3;

        // ================= TRAINS =================

        Train train1 = new Train(1, "R1821", 3, route1);
        Train train2 = new Train(2, "R2020", 80, route2);

        int nextTrainId = 3;

        // ================= SCHEDULES =================

        Schedule schedule1 = new Schedule(
                1,
                train1,
                route1,
                "08:00",
                "12:00",
                "09.05.2026"
        );

        Schedule schedule2 = new Schedule(
                2,
                train1,
                route1,
                "14:00",
                "18:00",
                "09.05.2026"
        );

        Schedule schedule3 = new Schedule(
                3,
                train2,
                route2,
                "13:00",
                "16:00",
                "09.05.2026"
        );

        // ================= SERVICES =================

        Services services = new Services();

        RouteService routeService = new RouteService();

        routeService.addSchedule(schedule1);
        routeService.addSchedule(schedule2);
        routeService.addSchedule(schedule3);

        AdminService adminService = new AdminService(services);

        DelayImpactService delayImpactService = new DelayImpactService();

        adminService.addRoute(route1);
        adminService.addRoute(route2);

        adminService.addTrain(train1);
        adminService.addTrain(train2);

        int nextUserId = 1;
        int nextBookingId = 1;

        // ================= MAIN MENU =================

        boolean running = true;

        while (running) {

            System.out.println("\n===== TRAIN TICKETING APPLICATION =====");
            System.out.println("1. User");
            System.out.println("2. Administrator");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int mainChoice = sc.nextInt();
            sc.nextLine();

            if (mainChoice == 1) {

                // ================= CUSTOMER MENU =================

                System.out.println("\n===== CUSTOMER MENU =====");

                System.out.print("Enter departure station: ");
                String departureName = sc.nextLine();

                System.out.print("Enter destination station: ");
                String destinationName = sc.nextLine();

                System.out.print("Enter date (dd.mm.yyyy): ");
                String date = sc.nextLine();

                Station departureStation = findStationByName(departureName, allStations);
                Station destinationStation = findStationByName(destinationName, allStations);

                if (departureStation == null || destinationStation == null) {

                    System.out.println("One of the stations does not exist.");
                }
                else {

                    // ================= SEARCH DIRECT ROUTES =================

                    List<Schedule> matchingSchedules = routeService.directRoutes(
                            departureStation,
                            destinationStation,
                            date
                    );

                    if (matchingSchedules.size() > 0) {

                        System.out.println("\nAvailable direct schedules:");

                        for (int i = 0; i < matchingSchedules.size(); i++) {

                            Schedule currentSchedule = matchingSchedules.get(i);

                            System.out.println(
                                    (i + 1)
                                            + ". Train "
                                            + currentSchedule.getTrain().getTrainNr()
                                            + " | Route: "
                                            + currentSchedule.getRoutes().getRouteName()
                                            + " | Departure: "
                                            + currentSchedule.getDeparture()
                                            + " | Arrival: "
                                            + currentSchedule.getArrival()
                            );
                        }

                        System.out.print("\nChoose schedule number: ");
                        int choice = sc.nextInt();
                        sc.nextLine();

                        if (choice < 1 || choice > matchingSchedules.size()) {

                            System.out.println("Invalid choice.");
                        }
                        else {

                            Schedule chosenSchedule = matchingSchedules.get(choice - 1);

                            System.out.print("Enter your name: ");
                            String name = sc.nextLine();

                            System.out.print("Enter your email: ");
                            String email = sc.nextLine();

                            System.out.print("Number of tickets: ");
                            int nrTickets = sc.nextInt();
                            sc.nextLine();

                            if (nrTickets <= 0) {

                                System.out.println("Number of tickets must be greater than 0.");
                            }
                            else {

                                User user = new User(nextUserId, name, email);
                                nextUserId++;

                                Booking booking = services.createBooking(
                                        nextBookingId,
                                        user,
                                        chosenSchedule.getTrain(),
                                        departureStation,
                                        destinationStation,
                                        nrTickets
                                );

                                nextBookingId++;

                                if (booking != null) {

                                    System.out.println("\nBooking completed successfully.");
                                }
                                else {

                                    System.out.println("\nBooking failed.");
                                }
                            }
                        }
                    }
                    else {

                        // ================= SEARCH CHANGEOVER ROUTES =================

                        List<ChangeOver> matchingChangeOvers = routeService.changeOver(
                                departureStation,
                                destinationStation,
                                date
                        );

                        if (matchingChangeOvers.size() == 0) {

                            System.out.println("No possible link between these stations.");
                        }
                        else {

                            System.out.println("\nNo direct route found.");
                            System.out.println("Available changeover schedules:");

                            for (int i = 0; i < matchingChangeOvers.size(); i++) {

                                ChangeOver currentChangeOver = matchingChangeOvers.get(i);

                                Schedule firstSchedule = currentChangeOver.getFirst();
                                Schedule secondSchedule = currentChangeOver.getSecond();
                                Station changeStation = currentChangeOver.getChange();

                                System.out.println(
                                        (i + 1)
                                                + ". First train: "
                                                + firstSchedule.getTrain().getTrainNr()
                                                + " | Route: "
                                                + firstSchedule.getRoutes().getRouteName()
                                                + " | Departure: "
                                                + firstSchedule.getDeparture()
                                                + " | Arrival: "
                                                + firstSchedule.getArrival()
                                );

                                System.out.println(
                                        "   Change at: "
                                                + changeStation.getName()
                                );

                                System.out.println(
                                        "   Second train: "
                                                + secondSchedule.getTrain().getTrainNr()
                                                + " | Route: "
                                                + secondSchedule.getRoutes().getRouteName()
                                                + " | Departure: "
                                                + secondSchedule.getDeparture()
                                                + " | Arrival: "
                                                + secondSchedule.getArrival()
                                );
                            }

                            System.out.print("\nChoose changeover option number: ");
                            int changeOverChoice = sc.nextInt();
                            sc.nextLine();

                            if (changeOverChoice < 1 || changeOverChoice > matchingChangeOvers.size()) {

                                System.out.println("Invalid choice.");
                            }
                            else {

                                ChangeOver chosenChangeOver = matchingChangeOvers.get(changeOverChoice - 1);

                                Schedule firstChosenSchedule = chosenChangeOver.getFirst();
                                Schedule secondChosenSchedule = chosenChangeOver.getSecond();
                                Station changeStation = chosenChangeOver.getChange();

                                System.out.print("Enter your name: ");
                                String name = sc.nextLine();

                                System.out.print("Enter your email: ");
                                String email = sc.nextLine();

                                System.out.print("Number of tickets: ");
                                int nrTickets = sc.nextInt();
                                sc.nextLine();

                                if (nrTickets <= 0) {

                                    System.out.println("Number of tickets must be greater than 0.");
                                }
                                else {

                                    boolean seatsAvailableFirstTrain = services.seatsAvailable(
                                            firstChosenSchedule.getTrain(),
                                            nrTickets
                                    );

                                    boolean seatsAvailableSecondTrain = services.seatsAvailable(
                                            secondChosenSchedule.getTrain(),
                                            nrTickets
                                    );

                                    if (!seatsAvailableFirstTrain || !seatsAvailableSecondTrain) {

                                        System.out.println("Not enough available seats for the complete changeover route.");
                                    }
                                    else {

                                        User user = new User(nextUserId, name, email);
                                        nextUserId++;

                                        Booking firstBooking = services.createBooking(
                                                nextBookingId,
                                                user,
                                                firstChosenSchedule.getTrain(),
                                                departureStation,
                                                changeStation,
                                                nrTickets
                                        );

                                        nextBookingId++;

                                        Booking secondBooking = services.createBooking(
                                                nextBookingId,
                                                user,
                                                secondChosenSchedule.getTrain(),
                                                changeStation,
                                                destinationStation,
                                                nrTickets
                                        );

                                        nextBookingId++;

                                        if (firstBooking != null && secondBooking != null) {

                                            ChangeOverBooking changeOverBooking = new ChangeOverBooking(
                                                    firstBooking,
                                                    secondBooking,
                                                    firstChosenSchedule,
                                                    secondChosenSchedule,
                                                    changeStation
                                            );

                                            delayImpactService.addChangeOverBooking(changeOverBooking);

                                            System.out.println("\nChangeover booking completed successfully.");
                                            System.out.println("First train: " + firstChosenSchedule.getTrain().getTrainNr());
                                            System.out.println("Change station: " + changeStation.getName());
                                            System.out.println("Second train: " + secondChosenSchedule.getTrain().getTrainNr());
                                        }
                                        else {

                                            System.out.println("\nChangeover booking failed.");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if (mainChoice == 2) {

                // ================= ADMIN MENU =================

                boolean adminRunning = true;

                while (adminRunning) {

                    System.out.println("\n===== ADMIN MENU =====");
                    System.out.println("1. Show routes");
                    System.out.println("2. Add route");
                    System.out.println("3. Remove route");
                    System.out.println("4. Modify route name");
                    System.out.println("5. Add station to route");
                    System.out.println("6. Remove station from route");
                    System.out.println("7. Show trains");
                    System.out.println("8. Add train");
                    System.out.println("9. Remove train");
                    System.out.println("10. Modify train");
                    System.out.println("11. Show bookings for train");
                    System.out.println("12. Set train delay");
                    System.out.println("13. Analyze delay impact");
                    System.out.println("0. Back to main menu");
                    System.out.print("Choose option: ");

                    int adminChoice = sc.nextInt();
                    sc.nextLine();

                    if (adminChoice == 1) {

                        adminService.showRoutes();
                    }
                    else if (adminChoice == 2) {

                        System.out.print("Enter route name: ");
                        String routeName = sc.nextLine();

                        Route newRoute = new Route(nextRouteId, routeName);
                        nextRouteId++;

                        System.out.print("How many stations does this route have? ");
                        int numberOfStations = sc.nextInt();
                        sc.nextLine();

                        for (int i = 0; i < numberOfStations; i++) {

                            System.out.print("Enter station name: ");
                            String stationName = sc.nextLine();

                            Station station = findStationByName(stationName, allStations);

                            if (station == null) {

                                station = new Station(nextStationId, stationName);
                                nextStationId++;

                                allStations.add(station);
                            }

                            newRoute.addStation(station);
                        }

                        adminService.addRoute(newRoute);
                    }
                    else if (adminChoice == 3) {

                        System.out.print("Enter route ID to remove: ");
                        int routeId = sc.nextInt();
                        sc.nextLine();

                        adminService.removeRoute(routeId);
                    }
                    else if (adminChoice == 4) {

                        System.out.print("Enter route ID: ");
                        int routeId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new route name: ");
                        String newRouteName = sc.nextLine();

                        adminService.modifyRouteName(routeId, newRouteName);
                    }
                    else if (adminChoice == 5) {

                        System.out.print("Enter route ID: ");
                        int routeId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter station name to add: ");
                        String stationName = sc.nextLine();

                        Station station = findStationByName(stationName, allStations);

                        if (station == null) {

                            station = new Station(nextStationId, stationName);
                            nextStationId++;

                            allStations.add(station);
                        }

                        adminService.addStationToRoute(routeId, station);
                    }
                    else if (adminChoice == 6) {

                        System.out.print("Enter route ID: ");
                        int routeId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter station name to remove: ");
                        String stationName = sc.nextLine();

                        Station station = findStationByName(stationName, allStations);

                        if (station == null) {

                            System.out.println("Station not found.");
                        }
                        else {

                            adminService.removeStationFromRoute(routeId, station);
                        }
                    }
                    else if (adminChoice == 7) {

                        adminService.showTrains();
                    }
                    else if (adminChoice == 8) {

                        System.out.print("Enter train number: ");
                        String trainNr = sc.nextLine();

                        System.out.print("Enter train capacity: ");
                        int capacity = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter route ID for this train: ");
                        int routeId = sc.nextInt();
                        sc.nextLine();

                        Route route = adminService.findRouteById(routeId);

                        if (route == null) {

                            System.out.println("Route not found. Train was not added.");
                        }
                        else {

                            Train newTrain = new Train(nextTrainId, trainNr, capacity, route);
                            nextTrainId++;

                            adminService.addTrain(newTrain);
                        }
                    }
                    else if (adminChoice == 9) {

                        System.out.print("Enter train ID to remove: ");
                        int trainId = sc.nextInt();
                        sc.nextLine();

                        adminService.removeTrain(trainId);
                    }
                    else if (adminChoice == 10) {

                        System.out.print("Enter train ID: ");
                        int trainId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new train number: ");
                        String newTrainNr = sc.nextLine();

                        System.out.print("Enter new capacity: ");
                        int newCapacity = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter new route ID: ");
                        int newRouteId = sc.nextInt();
                        sc.nextLine();

                        Route newRoute = adminService.findRouteById(newRouteId);

                        if (newRoute == null) {

                            System.out.println("Route not found. Train was not modified.");
                        }
                        else {

                            adminService.modifyTrain(
                                    trainId,
                                    newTrainNr,
                                    newCapacity,
                                    newRoute
                            );
                        }
                    }
                    else if (adminChoice == 11) {

                        System.out.print("Enter train ID: ");
                        int trainId = sc.nextInt();
                        sc.nextLine();

                        adminService.showBookingsForTrain(trainId);
                    }
                    else if (adminChoice == 12) {

                        System.out.print("Enter train ID: ");
                        int trainId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter delay in minutes: ");
                        int delayMinutes = sc.nextInt();
                        sc.nextLine();

                        adminService.setTrainDelay(trainId, delayMinutes);
                    }
                    else if (adminChoice == 13) {

                        System.out.print("Enter delayed train ID: ");
                        int trainId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter delay in minutes: ");
                        int delayMinutes = sc.nextInt();
                        sc.nextLine();

                        Train delayedTrain = adminService.findTrainById(trainId);

                        if (delayedTrain == null) {

                            System.out.println("Train not found.");
                        }
                        else {

                            delayedTrain.setDelay(true, delayMinutes);

                            delayImpactService.showDelayImpact(
                                    delayedTrain,
                                    delayMinutes
                            );
                        }
                    }
                    else if (adminChoice == 0) {

                        adminRunning = false;
                    }
                    else {

                        System.out.println("Invalid admin option.");
                    }
                }
            }
            else if (mainChoice == 0) {

                running = false;
            }
            else {

                System.out.println("Invalid option.");
            }
        }

        System.out.println("Application closed.");

        sc.close();
    }

    // ================= HELPER METHOD =================

    public static Station findStationByName(String name, List<Station> stations) {

        for (int i = 0; i < stations.size(); i++) {

            Station station = stations.get(i);

            if (station.getName().equalsIgnoreCase(name)) {

                return station;
            }
        }

        return null;
    }
}