import java.util.ArrayList;
import java.util.List;

public class Services {

    private List<Booking> bookings;
    private Email emailService;
    private int nextTicketId;

    public Services() {

        bookings = new ArrayList<>();
        emailService = new Email();
        nextTicketId = 1;
    }

    public int bookedSeats(Train train) {

        int total = 0;

        for (int i = 0; i < bookings.size(); i++) {

            Booking currentBooking = bookings.get(i);

            List<Ticket> ticketsFromCurrentBooking = currentBooking.getTickets();

            for (int j = 0; j < ticketsFromCurrentBooking.size(); j++) {

                Ticket currentTicket = ticketsFromCurrentBooking.get(j);

                if (currentTicket.getTrain().getId() == train.getId()) {

                    total++;
                }
            }
        }

        return total;
    }

    public boolean seatsAvailable(Train train, int requestedTickets) {

        int booked = bookedSeats(train);

        int available = train.getCapacity() - booked;

        return requestedTickets <= available;
    }

    public Booking createBooking(
            int bookingId,
            User user,
            Train train,
            Station departure,
            Station destination,
            int numberOfTickets
    ) {

        if (!seatsAvailable(train, numberOfTickets)) {

            System.out.println("Not enough available seats.");
            return null;
        }

        List<Ticket> tickets = new ArrayList<>();

        int alreadyBooked = bookedSeats(train);

        for (int i = 0; i < numberOfTickets; i++) {

            int seatNr = alreadyBooked + i + 1;

            Ticket ticket = new Ticket(
                    nextTicketId,
                    train,
                    departure,
                    destination,
                    seatNr
            );

            tickets.add(ticket);

            nextTicketId++;
        }

        Booking booking = new Booking(
                bookingId,
                user,
                departure,
                destination,
                tickets
        );

        bookings.add(booking);

        emailService.confirmEmail(user, booking);

        return booking;
    }

    public List<Booking> getBookings() {

        return bookings;
    }
}