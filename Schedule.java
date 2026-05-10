
public class Schedule {

		private int id;
		private Train train;
		private Route route;
		private String departure;
		private String arrival;
		private String date;
		
		
		public Schedule(int id, Train train, Route routes, String departure, String arrival, String date ) {
			
			this.id = id;
			this.train = train;
			this.route = routes;
			this.departure = departure;
			this.arrival = arrival;
			this.date = date;
			
		}
		
		
		public int getId() {
			return id;
		}
		
		public Train getTrain() {
			return train;
		}
		
		public Route getRoutes() {
			return route;
		}
		
		public String getDeparture() {
			return departure;
		}
		
		public String getArrival() {
			return arrival;
		}
		
		public String getDate() {
			return date;
		}
		
}
