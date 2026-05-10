import java.util.List;


public class Booking {

		private int id;
		private User user;
		private Station departureStation;
		private Station destination;
		private List<Ticket> tickets;
		
		public Booking(int id ,User user, Station departureStation,Station destination ,List<Ticket> tickets) {
			
			this.id = id;
			this.user = user;
			this.departureStation= departureStation;
			this.destination = destination ;
			this.tickets = tickets;
		}
		
		public int getId() {
			return id;
		}
		
		public User getUser() {
			return user;
		}
		
		public Station getDeparture() {
			return departureStation;
		}
		
		public Station getDestination() {
			return destination;
		}
		
		public List<Ticket> getTickets() {
			return tickets;
		}
}
