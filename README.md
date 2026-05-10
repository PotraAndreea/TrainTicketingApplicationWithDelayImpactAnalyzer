# Train Ticketing Application with Delay Impact Analyzer

## Description

This project is a Java console application for managing train ticket bookings.

The application contains predefined stations, routes, trains, and schedules. It allows customers to search for available train connections and book one or multiple tickets. It also provides an administrator menu where routes, trains, bookings, and delays can be managed.

The application also includes a second functionality called **Train Delay Impact Analyzer**. This feature analyzes if a delayed train affects passengers who booked a route with a changeover. If the first train is delayed and the passenger may miss the second train, the application displays the affected passenger and prints a simulated delay impact notification email.

The application was developed using Java and can be run from an IDE such as Eclipse.

---

## Main Features

The application supports two types of users:

1. Customer
2. Administrator

---

## Customer Functionalities

A customer can:

- Search for a train connection by entering a departure station, destination station, and date
- View available direct routes
- View available changeover routes if no direct route exists
- Book one or multiple tickets
- Receive a booking confirmation email in the console
- See an error message if no connection exists
- See an error message if there are not enough available seats

---

## Administrator Functionalities

An administrator can:

- Show all available routes
- Add a new route
- Remove a route
- Modify the name of a route
- Add a station to an existing route
- Remove a station from a route
- Show all trains
- Add a new train
- Remove a train
- Modify train information
- Show bookings made for a specific train
- Set a train delay
- Notify customers about train delays through a console email message
- Analyze the impact of a train delay on changeover bookings
- Detect if a passenger may miss a connection because of a delay
- Display affected passengers and simulated delay impact emails

---

## Project Structure

The application contains the following classes:

### Station

Represents a train station.

Attributes:

- `id`
- `name`

Example:

```java
Station s1 = new Station(1, "Bucharest");
```

---

### Route

Represents a train route made of multiple stations.

Attributes:

- `id`
- `routeName`
- `stations`

Important methods:

- `addStation()`
- `removeStation()`
- `orderOfStations()`

The `orderOfStations()` method checks if the destination station appears after the departure station on the route.

Example route:

```text
Bucharest -> Brasov -> Cluj
```

---

### Train

Represents a train assigned to a route.

Attributes:

- `id`
- `trainNr`
- `capacity`
- `route`
- `delayed`
- `delayMinutes`

Example:

```java
Train train1 = new Train(1, "R1821", 3, route1);
```

---

### Schedule

Represents a train schedule.

Attributes:

- `id`
- `train`
- `route`
- `departure`
- `arrival`
- `date`

Example:

```java
Schedule schedule1 = new Schedule(
    1,
    train1,
    route1,
    "08:00",
    "12:00",
    "09.05.2026"
);
```

---

### User

Represents the customer who makes a booking.

Attributes:

- `id`
- `name`
- `email`

---

### Ticket

Represents one ticket for a train.

Attributes:

- `id`
- `train`
- `departureStation`
- `destinationStation`
- `seatNr`

---

### Booking

Represents a booking made by a user.

Attributes:

- `id`
- `user`
- `departureStation`
- `destination`
- `tickets`

---

### Services

Handles the booking logic.

Main responsibilities:

- Counts booked seats for a train
- Checks if enough seats are available
- Creates bookings
- Generates tickets
- Sends booking confirmation emails

Important methods:

```java
bookedSeats(Train train)
seatsAvailable(Train train, int requestedTickets)
createBooking(...)
getBookings()
```

---

### RouteService

Handles route searching.

Main responsibilities:

- Finds direct routes
- Finds changeover routes

Important methods:

```java
directRoutes(Station departure, Station destination, String date)
changeOver(Station departure, Station destination, String date)
```

---

### ChangeOver

Represents a route that requires changing trains.

Attributes:

- `firstSchedule`
- `secondSchedule`
- `changeStation`

---

### ChangeOverBooking

Represents a completed booking that contains two connected bookings: one for the first train and one for the second train.

This class is used for the delay impact analyzer.

Attributes:

- `firstBooking`
- `secondBooking`
- `firstSchedule`
- `secondSchedule`
- `changeStation`

Purpose:

The application needs to remember which passengers booked a route with a changeover. This is important because if the first train is delayed, the program can check if the passenger may miss the second train.

Example:

```text
First booking: Bucharest -> Brasov
Second booking: Brasov -> Sibiu
Change station: Brasov
```

---

### DelayImpact

Represents the result of a delay analysis for one passenger.

Attributes:

- `user`
- `delayedTrain`
- `connectionTrain`
- `changeStation`
- `originalArrival`
- `newArrival`
- `connectionDeparture`
- `connectionMissed`

Purpose:

This class stores the information needed to show whether a passenger is affected by a delay.

Example:

```text
Passenger: Andrei Ionescu
Delayed train: R1821
Connection train: R2020
Original arrival: 12:00
New arrival: 13:30
Connection departure: 13:00
Status: CONNECTION MISSED
```

---

### DelayImpactService

Handles the delay impact analysis logic.

Main responsibilities:

- Stores changeover bookings
- Adds delay minutes to the original arrival time
- Checks if the passenger can still catch the second train
- Displays affected passengers
- Prints simulated delay impact notification emails

Important methods:

```java
addChangeOverBooking(ChangeOverBooking changeOverBooking)
addMinutesToTime(String time, int delayMinutes)
connectionIsMissed(String newArrival, String connectionDeparture)
analyzeDelayImpact(Train delayedTrain, int delayMinutes)
showDelayImpact(Train delayedTrain, int delayMinutes)
```

Example logic:

```text
Original arrival: 12:00
Delay: 90 minutes
New arrival: 13:30
Connection departure: 13:00
Result: CONNECTION MISSED
```

---

### AdminService

Handles administrator operations.

Main responsibilities:

- Add, remove, and modify routes
- Add and remove stations from routes
- Add, remove, and modify trains
- Show bookings for a train
- Set train delays
- Notify customers about delays

---

### Email

Simulates email sending by printing email messages in the console.

Methods:

```java
confirmEmail(User user, Booking booking)
delayEmail(User user, Train train)
```

The application does not send real emails. It only displays the email content in the console.

---

## Predefined Data

The application starts with the following stations:

```text
1. Bucharest
2. Brasov
3. Cluj
4. Sibiu
```

The predefined routes are:

```text
Route 1: Bucharest - Brasov - Cluj
Route 2: Brasov - Sibiu
```

The predefined trains are:

```text
Train ID: 1
Train number: R1821
Capacity: 3
Route: Bucharest - Brasov - Cluj

Train ID: 2
Train number: R2020
Capacity: 80
Route: Brasov - Sibiu
```

The predefined schedules are:

```text
Schedule 1:
Train: R1821
Route: Bucharest - Brasov - Cluj
Departure: 08:00
Arrival: 12:00
Date: 09.05.2026

Schedule 2:
Train: R1821
Route: Bucharest - Brasov - Cluj
Departure: 14:00
Arrival: 18:00
Date: 09.05.2026

Schedule 3:
Train: R2020
Route: Brasov - Sibiu
Departure: 13:00
Arrival: 16:00
Date: 09.05.2026
```

---

## How to Run the Application

1. Open Eclipse.
2. Create a new Java project.
3. Create the Java classes used by the application:
   - `Main`
   - `Train`
   - `Station`
   - `Route`
   - `Schedule`
   - `User`
   - `Booking`
   - `Ticket`
   - `Services`
   - `RouteService`
   - `ChangeOver`
   - `ChangeOverBooking`
   - `DelayImpact`
   - `DelayImpactService`
   - `AdminService`
   - `Email`
4. Copy the corresponding code into each class.
5. Run the `Main` class.
6. Use the console menu to interact with the application.

---

## Console Menu

When the application starts, the user sees the main menu:

```text
===== TRAIN TICKETING APPLICATION =====
1. User
2. Administrator
0. Exit
Choose option:
```

---

# Input and Output Examples

---

## 1. Customer - Direct Route Booking

### Input Example

```text
===== TRAIN TICKETING APPLICATION =====
1. User
2. Administrator
0. Exit
Choose option: 1

===== CUSTOMER MENU =====
Enter departure station: Bucharest
Enter destination station: Cluj
Enter date (dd.mm.yyyy): 09.05.2026
```

### Output Example

```text
Available direct schedules:
1. Train R1821 | Route: Bucharest - Brasov - Cluj | Departure: 08:00 | Arrival: 12:00
2. Train R1821 | Route: Bucharest - Brasov - Cluj | Departure: 14:00 | Arrival: 18:00

Choose schedule number:
```

### Input Example

```text
Choose schedule number: 1
Enter your name: Maria Popescu
Enter your email: maria@email.com
Number of tickets: 2
```

### Output Example

```text
EMAIL CONFIRMATION:
To: maria@email.com
Hello Maria Popescu
Your booking was successful.
Booking ID: 1
Number of tickets: 2
==============================

Booking completed successfully.
```

---

## 2. Customer - Changeover Route Booking

A changeover route is used when there is no direct route between two stations, but the destination can still be reached by changing trains.

Example:

```text
Bucharest -> Brasov
Brasov -> Sibiu
```

The customer wants to travel from Bucharest to Sibiu. There is no direct route, but the customer can change trains at Brasov.

### Input Example

```text
===== TRAIN TICKETING APPLICATION =====
1. User
2. Administrator
0. Exit
Choose option: 1

===== CUSTOMER MENU =====
Enter departure station: Bucharest
Enter destination station: Sibiu
Enter date (dd.mm.yyyy): 09.05.2026
```

### Output Example

```text
No direct route found.
Available changeover schedules:
1. First train: R1821 | Route: Bucharest - Brasov - Cluj | Departure: 08:00 | Arrival: 12:00
   Change at: Brasov
   Second train: R2020 | Route: Brasov - Sibiu | Departure: 13:00 | Arrival: 16:00

Choose changeover option number:
```

### Input Example

```text
Choose changeover option number: 1
Enter your name: Andrei Ionescu
Enter your email: andrei@email.com
Number of tickets: 1
```

### Output Example

```text
EMAIL CONFIRMATION:
To: andrei@email.com
Hello Andrei Ionescu
Your booking was successful.
Booking ID: 1
Number of tickets: 1
==============================

EMAIL CONFIRMATION:
To: andrei@email.com
Hello Andrei Ionescu
Your booking was successful.
Booking ID: 2
Number of tickets: 1
==============================

Changeover booking completed successfully.
First train: R1821
Change station: Brasov
Second train: R2020
```

Because a changeover route uses two trains, the application creates two bookings: one for each train segment.

The application also stores this changeover booking in the delay impact system. This allows the program to later check if the passenger may miss the second train when the first train is delayed.

---

## 3. Customer - No Route Found

### Input Example

```text
Choose option: 1

Enter departure station: Sibiu
Enter destination station: Cluj
Enter date (dd.mm.yyyy): 09.05.2026
```

### Output Example

```text
No possible link between these stations.
```

This happens because the application cannot find either a direct route or a valid changeover route.

---

## 4. Customer - Invalid Station

### Input Example

```text
Choose option: 1

Enter departure station: Paris
Enter destination station: Cluj
Enter date (dd.mm.yyyy): 09.05.2026
```

### Output Example

```text
One of the stations does not exist.
```

---

## 5. Customer - Not Enough Available Seats

Train `R1821` has a capacity of `3`.

### Input Example

```text
Choose option: 1

Enter departure station: Bucharest
Enter destination station: Cluj
Enter date (dd.mm.yyyy): 09.05.2026

Choose schedule number: 1
Enter your name: Ana
Enter your email: ana@email.com
Number of tickets: 4
```

### Output Example

```text
Not enough available seats.

Booking failed.
```

The booking is rejected because the requested number of tickets is greater than the train capacity.

---

## 6. Customer - Invalid Number of Tickets

### Input Example

```text
Number of tickets: 0
```

### Output Example

```text
Number of tickets must be greater than 0.
```

---

# Administrator Examples

---

## 7. Show Routes

### Input Example

```text
===== TRAIN TICKETING APPLICATION =====
1. User
2. Administrator
0. Exit
Choose option: 2

===== ADMIN MENU =====
1. Show routes
2. Add route
3. Remove route
4. Modify route name
5. Add station to route
6. Remove station from route
7. Show trains
8. Add train
9. Remove train
10. Modify train
11. Show bookings for train
12. Set train delay
13. Analyze delay impact
0. Back to main menu
Choose option: 1
```

### Output Example

```text
Route ID: 1
Route name: Bucharest - Brasov - Cluj
Stations:
- Bucharest
- Brasov
- Cluj

Route ID: 2
Route name: Brasov - Sibiu
Stations:
- Brasov
- Sibiu
```

---

## 8. Add Route

### Input Example

```text
Choose option: 2
Enter route name: Cluj - Oradea
How many stations does this route have? 2
Enter station name: Cluj
Enter station name: Oradea
```

### Output Example

```text
Route added successfully.
```

If a station does not already exist, it is automatically created and added to the list of stations.

---

## 9. Remove Route

### Input Example

```text
Choose option: 3
Enter route ID to remove: 2
```

### Output Example

```text
Route removed successfully.
```

If the route ID does not exist, the output is:

```text
Route not found.
```

---

## 10. Modify Route Name

### Input Example

```text
Choose option: 4
Enter route ID: 1
Enter new route name: Bucharest - Brasov - Cluj-Napoca
```

### Output Example

```text
Route modified successfully.
```

If the route does not exist:

```text
Route not found.
```

---

## 11. Add Station to Route

### Input Example

```text
Choose option: 5
Enter route ID: 1
Enter station name to add: Alba Iulia
```

### Output Example

```text
Station added to route successfully.
```

If the route does not exist:

```text
Route not found.
```

---

## 12. Remove Station from Route

### Input Example

```text
Choose option: 6
Enter route ID: 1
Enter station name to remove: Brasov
```

### Output Example

```text
Station removed from route successfully.
```

If the station does not exist:

```text
Station not found.
```

If the route does not exist:

```text
Route not found.
```

---

## 13. Show Trains

### Input Example

```text
Choose option: 7
```

### Output Example

```text
Train ID: 1
Train number: R1821
Capacity: 3
Route: Bucharest - Brasov - Cluj
Status: on time

Train ID: 2
Train number: R2020
Capacity: 80
Route: Brasov - Sibiu
Status: on time
```

---

## 14. Add Train

### Input Example

```text
Choose option: 8
Enter train number: IR3000
Enter train capacity: 100
Enter route ID for this train: 1
```

### Output Example

```text
Train added successfully.
```

If the selected route does not exist:

```text
Route not found. Train was not added.
```

---

## 15. Remove Train

### Input Example

```text
Choose option: 9
Enter train ID to remove: 2
```

### Output Example

```text
Train removed successfully.
```

If the train does not exist:

```text
Train not found.
```

---

## 16. Modify Train

### Input Example

```text
Choose option: 10
Enter train ID: 1
Enter new train number: R9999
Enter new capacity: 120
Enter new route ID: 1
```

### Output Example

```text
Train modified successfully.
```

If the train does not exist:

```text
Train not found.
```

If the route does not exist:

```text
Route not found. Train was not modified.
```

---

## 17. Show Bookings for Train

After a customer books tickets for train `R1821`, the administrator can view the bookings.

### Input Example

```text
Choose option: 11
Enter train ID: 1
```

### Output Example

```text
Booking ID: 1
Customer name: Maria Popescu
Customer email: maria@email.com
From: Bucharest
To: Cluj
Number of tickets: 2
```

If there are no bookings for the selected train:

```text
No bookings found for this train.
```

---

## 18. Set Train Delay

The administrator can mark a train as delayed. Customers who booked tickets for that train are notified through a console email.

### Input Example

```text
Choose option: 12
Enter train ID: 1
Enter delay in minutes: 30
```

### Output Example

```text
Train R1821 was marked as delayed.

DELAY NOTIFICATION:
To: maria@email.com
Hello Maria Popescu
Train R1821 has a delay.
Delay: 30 minutes.
==============================
```

If the train does not exist:

```text
Train not found.
```

---

## 19. Analyze Delay Impact

This functionality is part of the second problem.

It checks if passengers who booked changeover routes are affected by a delay.

The program compares:

```text
new arrival time of the delayed train
connection departure time of the second train
```

If the new arrival time is after the second train departure time, the connection is considered missed.

Example situation:

```text
Passenger route: Bucharest -> Sibiu

First train:
R1821
Bucharest -> Brasov
Arrival at Brasov: 12:00

Second train:
R2020
Brasov -> Sibiu
Departure from Brasov: 13:00
```

If train `R1821` is delayed by 90 minutes:

```text
Original arrival: 12:00
New arrival: 13:30
Connection departure: 13:00
```

The passenger misses the connection.

### Input Example

First, a customer books a changeover route:

```text
Choose option: 1

Enter departure station: Bucharest
Enter destination station: Sibiu
Enter date (dd.mm.yyyy): 09.05.2026

Choose changeover option number: 1
Enter your name: Andrei Ionescu
Enter your email: andrei@email.com
Number of tickets: 1
```

Then the administrator analyzes the delay impact:

```text
Choose option: 2

===== ADMIN MENU =====
1. Show routes
2. Add route
3. Remove route
4. Modify route name
5. Add station to route
6. Remove station from route
7. Show trains
8. Add train
9. Remove train
10. Modify train
11. Show bookings for train
12. Set train delay
13. Analyze delay impact
0. Back to main menu
Choose option: 13

Enter delayed train ID: 1
Enter delay in minutes: 90
```

### Output Example

```text
===== DELAY IMPACT ANALYSIS =====

Passenger: Andrei Ionescu
Email: andrei@email.com
Delayed train: R1821
Connection train: R2020
Change station: Brasov
Original arrival: 12:00
New arrival after delay: 13:30
Connection departure: 13:00
Status: CONNECTION MISSED

EMAIL NOTIFICATION:
To: andrei@email.com
Hello Andrei Ionescu
Your first train R1821 is delayed.
Because of this delay, you may miss your connection train R2020 at Brasov.
==============================
```

---

## 20. Analyze Delay Impact When Connection Is Still Possible

If the delay is small enough, the passenger can still catch the second train.

Example:

```text
Original arrival: 12:00
Delay: 30 minutes
New arrival: 12:30
Connection departure: 13:00
```

Since `12:30` is before `13:00`, the connection is still possible.

### Input Example

```text
Choose option: 13
Enter delayed train ID: 1
Enter delay in minutes: 30
```

### Output Example

```text
===== DELAY IMPACT ANALYSIS =====

Passenger: Andrei Ionescu
Email: andrei@email.com
Delayed train: R1821
Connection train: R2020
Change station: Brasov
Original arrival: 12:00
New arrival after delay: 12:30
Connection departure: 13:00
Status: CONNECTION STILL POSSIBLE
```

---

## 21. Analyze Delay Impact When No Changeover Passengers Are Affected

If the delayed train does not have passengers with changeover bookings, the application displays a message.

### Input Example

```text
Choose option: 13
Enter delayed train ID: 2
Enter delay in minutes: 60
```

### Output Example

```text
No changeover passengers are affected by this delay.
```

---

## 22. Exit Application

### Input Example

```text
Choose option: 0
```

### Output Example

```text
Application closed.
```

---

## Validation and Error Handling

The application handles several invalid cases.

### Invalid main menu option

```text
Invalid option.
```

### Invalid administrator option

```text
Invalid admin option.
```

### Invalid schedule choice

```text
Invalid choice.
```

### Invalid changeover option

```text
Invalid choice.
```

### Invalid station name

```text
One of the stations does not exist.
```

### No direct or changeover route

```text
No possible link between these stations.
```

### Not enough available seats

```text
Not enough available seats.
```

### Invalid number of tickets

```text
Number of tickets must be greater than 0.
```

### Invalid train ID

```text
Train not found.
```

---

## Important Implementation Details

### Seat Availability

The application prevents overbooking by counting how many tickets have already been booked for a train.

The available seats are calculated as:

```text
available seats = train capacity - already booked seats
```

If the requested number of tickets is greater than the available seats, the booking is rejected.

---

### Direct Route Search

A direct route is found when:

- the departure station exists on the route
- the destination station exists on the same route
- the destination station appears after the departure station
- the schedule date matches the user input date

Example:

```text
Bucharest -> Brasov -> Cluj
```

A trip from `Bucharest` to `Cluj` is valid.

A trip from `Cluj` to `Bucharest` is not valid on this route because the order is wrong.

---

### Changeover Route Search

A changeover route is found when:

- the first train can go from the departure station to a change station
- the second train can go from the change station to the destination station
- both schedules are on the same date
- the second train leaves after the first train arrives

Example:

```text
Train 1: Bucharest -> Brasov -> Cluj
Train 2: Brasov -> Sibiu
```

A customer can travel:

```text
Bucharest -> Brasov -> Sibiu
```

with a changeover at:

```text
Brasov
```

---

### Delay Impact Analysis

The delay impact analyzer is used for passengers who booked a changeover route.

When a delay is analyzed, the program:

1. Finds all changeover bookings that use the delayed train as the first train
2. Adds the delay minutes to the original arrival time of the first train
3. Compares the new arrival time with the departure time of the second train
4. Displays whether the connection is still possible or missed
5. Prints a simulated email notification if the connection is missed

Example:

```text
Original first train arrival: 12:00
Delay: 90 minutes
New first train arrival: 13:30
Second train departure: 13:00
Result: CONNECTION MISSED
```

---

### Email Simulation

The application does not send real emails.

Instead, it simulates emails by printing messages in the console.

Booking confirmation example:

```text
EMAIL CONFIRMATION:
To: maria@email.com
Hello Maria Popescu
Your booking was successful.
Booking ID: 1
Number of tickets: 2
==============================
```

Delay notification example:

```text
DELAY NOTIFICATION:
To: maria@email.com
Hello Maria Popescu
Train R1821 has a delay.
Delay: 30 minutes.
==============================
```

Delay impact notification example:

```text
EMAIL NOTIFICATION:
To: andrei@email.com
Hello Andrei Ionescu
Your first train R1821 is delayed.
Because of this delay, you may miss your connection train R2020 at Brasov.
==============================
```

---

## Limitations

The application currently stores all data in memory. This means that all bookings, routes, trains, and delay impact data are lost when the program is closed.

The application does not use:

- a database
- real email sending
- user authentication
- graphical interface

All interaction happens through the console.

---

## Possible Future Improvements

Possible improvements for this project include:

- Saving data in files or a database
- Sending real emails using an email API
- Adding login for administrators
- Adding ticket cancellation
- Adding ticket prices
- Adding departure and arrival times for each station
- Suggesting alternative trains when a connection is missed
- Creating a graphical user interface
- Improving input validation
- Adding unit tests

---

## Conclusion

This Java application demonstrates a train ticketing system with an additional delay impact analysis feature.

The first part supports customer booking, direct route search, changeover route search, seat availability validation, administrator management operations, and basic delay notifications.

The second part extends the project by analyzing how train delays affect passengers with changeover bookings. It can detect missed connections and notify affected passengers through simulated console email messages.

The project is suitable as a console-based Java application for practicing object-oriented programming concepts such as classes, objects, lists, encapsulation, service classes, and time-based logic.
