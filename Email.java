public class Email {

    public void confirmEmail(User user, Booking booking) {

        System.out.println("\nEMAIL CONFIRMATION:");
        System.out.println("To: " + user.getEmail());
        System.out.println("Hello " + user.getName());
        System.out.println("Your booking was successful.");
        System.out.println("Booking ID: " + booking.getId());
        System.out.println("Number of tickets: " + booking.getTickets().size());
        System.out.println("==============================\n");
    }

    public void delayEmail(User user, Train train) {

        System.out.println("\nDELAY NOTIFICATION:");
        System.out.println("To: " + user.getEmail());
        System.out.println("Hello " + user.getName());
        System.out.println("Train " + train.getTrainNr() + " has a delay.");
        System.out.println("Delay: " + train.getDelayMinutes() + " minutes.");
        System.out.println("==============================\n");
    }
}