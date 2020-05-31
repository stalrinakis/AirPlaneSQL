import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Random;

public class main {

	public static void main(String[] args) throws SQLException {
		System.out.println("MySQL Connect Example");
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "bookingdb";
		String driver = "com.mysql.jdbc.Driver";
		String userName = "root";
		String password = "";

		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to database");
		} catch (Exception e) {
			e.printStackTrace();

		}

		Scanner sc = new Scanner(System.in);

		while (true) {

			System.out.println("\n--------------- AIRLINE BOOKING SYSTEM ---------------");
			System.out.println(
					"Main menu: \n [1] Insert Flights.\n [2] Book Ticket.\n [3] Ticket Availability .\n [4] Flights via Airline."
							+ "\n [5] Packed Flights.\n [6] Available Flights Betwen Toronto and New Yrok.\n [7] Booking Tickets.\n [8] Seat Status Upadte.\n [9]Canceled Bookings.\n [0] Exit.");

			int userInput = sc.nextInt();
			sc.nextLine();
			if (userInput == 1) {

				System.out.println("\n--------------- ADD NEW FLIGHT ---------------");
				System.out.println("Flight ID: ");

				String flightId = sc.nextLine();

				System.out.println("Airline ID: ");

				String airlineId = sc.nextLine();

				if (!airlineId.toLowerCase().equalsIgnoreCase("aircan")
						&& !airlineId.toLowerCase().equalsIgnoreCase("usair")
						&& !airlineId.toLowerCase().equalsIgnoreCase("britair")
						&& !airlineId.toLowerCase().equalsIgnoreCase("airfrance")
						&& !airlineId.toLowerCase().equalsIgnoreCase("luftair")
						&& !airlineId.toLowerCase().equalsIgnoreCase("italair")) {

					System.out.println(
							"Please choose one of the following: AirCan, USAir, BritAir, AirFrance, LuftAir, ItalAir.");
					continue;
				}

				System.out.println("Business Available: ");

				boolean businessAvailable = sc.nextBoolean();

				System.out.println("Smoking Available: ");

				boolean smokingAvailable = sc.nextBoolean();
				sc.nextLine();

				System.out.println("flightdeparture");

				String flightDep = sc.nextLine();

				System.out.println("flightarrival");

				String flightArr = sc.nextLine();

				System.out.println("flighttime");

				String flightTime = sc.nextLine();

				System.out.println("flighttotalbusinessseats");

				int totalBusiness = sc.nextInt();

				System.out.println("flighttakenbusinessseats");

				int takenBusiness = sc.nextInt();

				System.out.println("flighttotalecoseats");

				int totalEco = sc.nextInt();

				System.out.println("flighttakenecoseats");

				int takenEco = sc.nextInt();

				PreparedStatement prepStat = conn.prepareStatement(
						"INSERT INTO flight (flightid, flightairlineid, flighthasbusiness, flighthassmoking, flightdeparture, "
								+ "flightarrival, flighttime, flighttotalbusinessseats, flighttakenbusinessseats, "
								+ "flighttotalecoseats, flighttakenecoseats) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

				prepStat.setString(1, flightId);
				prepStat.setString(2, airlineId);
				prepStat.setBoolean(3, businessAvailable);
				prepStat.setBoolean(4, smokingAvailable);
				prepStat.setString(5, flightDep);
				prepStat.setString(6, flightArr);
				prepStat.setString(7, flightTime);
				prepStat.setInt(8, totalBusiness);
				prepStat.setInt(9, takenBusiness);
				prepStat.setInt(10, totalEco);
				prepStat.setInt(11, takenEco);

				prepStat.executeUpdate();

			} else if (userInput == 2) {

				String bookingId = getRandomId();

				System.out.println("Booking City: ");

				String bookingCity = sc.nextLine();
				
				System.out.println("Booking Date: ");

				String bookingDate = sc.nextLine();
				
				System.out.println("Booking Seat Type: ");

				String bookingSeatType = sc.nextLine();
				
				System.out.println("Booking Total Price: ");

				float totalPrice = sc.nextFloat();
				sc.nextLine();
				
				System.out.println("Booking Flight Price: ");

				float flightPrice = sc.nextFloat();
				sc.nextLine();
				
				System.out.println("Booking Seat Condition (a, b, c): ");

				String seatCondition = sc.nextLine();
				
				System.out.println("Booking Pre-Payment: ");

				float prePayment = sc.nextFloat();
				sc.nextLine();
				
				System.out.println("Booking Payment Remainder: ");

				float paymentRemainder = sc.nextFloat();
				sc.nextLine();
				
				
				PreparedStatement prepStat = conn.prepareStatement(
						"INSERT INTO booking (bookingid, bookingcity, bookingdate, "
						+ "bookingseattype, bookingtotalprice, bookingflightprice, "
						+ "bookingseatcondition, bookingprepayment, bookingremainder) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

				prepStat.setString(1, bookingId);
				prepStat.setString(2, bookingCity);
				prepStat.setString(3, bookingDate);
				prepStat.setString(4, bookingSeatType);
				prepStat.setFloat(5, totalPrice);
				prepStat.setFloat(6, flightPrice);
				prepStat.setString(7, seatCondition);
				prepStat.setFloat(8, prePayment);
				prepStat.setFloat(9, paymentRemainder);


				prepStat.executeUpdate();
				
				
			}

		}
	}

	private static String getRandomId() {
		Random rand = new Random();
		String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		candidateChars.charAt(rand.nextInt(candidateChars.length()));

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 6; i++) {
			sb.append(candidateChars.charAt(rand.nextInt(candidateChars.length())));
		}

		return sb.toString();

	}

}
