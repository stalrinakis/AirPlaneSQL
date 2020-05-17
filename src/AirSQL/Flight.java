package AirSQL;
import java.time.LocalDate;
import java.time.LocalTime;

public class Flight {
	private String flightId;
	private String AirlineId;
	private LocalDate date;
	private LocalTime time;
	private String departureAirport, arrivalAirport;
	private int totalSeats,totalBSeats,totalSSeats,totalESeats, takenEcoSeats, takenBusSeats, takenSmoSeats;
	private double fliCost;
	private int tts;
	
	
	

	public String getFlightId() {
		return this.flightId;
	}

	public void setFlightId(String id) {
		this.flightId = id;
	}
	public String getAirlineId() {
		return this.AirlineId;
	}

	public void setAirlineId(String id) {
		this.AirlineId = id;
	}

	public String getDepartureAirport() {
		return this.departureAirport;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public String getArrivalAirport() {
		return this.arrivalAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	public int getTotalSeats() {
		return this.totalSeats;
	}

	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public int getTakenEcoSeats() {
		return this.takenEcoSeats;
	}

	public void setTakenEcoSeats(int takenEcoSeats) {
		this.takenEcoSeats = takenEcoSeats;
	}
	public int getTakenBusSeats() {
		return this.takenBusSeats;
	}

	public void setTakenBusSeats(int takenBusSeats) {
		this.takenBusSeats = takenBusSeats;
	}
	public int getTakenSmoSeats() {
		return this.takenSmoSeats;
	}

	public void setTakenSmoSeats(int takenSmoSeats) {
		this.takenSmoSeats = takenSmoSeats;
	}
	public int getTotalSmoSeats() {
		return this.totalSSeats;
	}
	public void setTotalSmoSeats(int totalSSeats) {
		this.totalSSeats=totalSSeats;
	}
	public int getTotalEcoSeats() {
		return this.totalESeats;
	}
	public void setTotalEcoSeats(int totalESeats) {
		this.totalESeats=totalESeats;
	}
	public int getTotalBusSeats() {
		return this.totalBSeats;
	}
	public void setTotalBusSeats(int totalBSeats) {
		this.totalBSeats=totalBSeats;
	}
	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(String brokenDate, String brokenDate2, String brokenDate3) {
		int year = Integer.parseInt(brokenDate);
		int month = Integer.parseInt(brokenDate2);
		int day = Integer.parseInt(brokenDate3);

		this.date = LocalDate.of(year, month, day);
	}

	public LocalTime getTime() {
		return this.time;
	}

	public void setTime(String brokenTime, String brokenTime2) {

		int hour = Integer.parseInt(brokenTime);
		int minute = Integer.parseInt(brokenTime2);

		this.time = LocalTime.of(hour, minute);
	}
	public LocalTime getFlightTime() {
		return this.time;
	}

	public void setFlightTime(String flibrokenTime, String flibrokenTime2) {

		int hour = Integer.parseInt(flibrokenTime);
		int minute = Integer.parseInt(flibrokenTime2);

		this.time = LocalTime.of(hour, minute);
	}
	public double getFlightCost() {
		return this.fliCost;
	}
	public void setFlightCost(double fliCost) {
		this.fliCost=fliCost;
	}
	public int getTotalTakenSeats() {
		return this.tts;
	}
	public void setTotalTakenSeats(int tts) {
		this.tts=tts;
	}
}
