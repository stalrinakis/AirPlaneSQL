import java.sql.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Random;

public class Program {

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
			String number = null;
			;
			int userInput = 0;
			boolean t = true;
			while (t) {
				number = sc.nextLine();
				if (number.matches("[0-9]+")) {
					userInput = Integer.parseInt(number);
					if (userInput < 10) {
						break;
					}
					System.out.println("Please enter a valid number (0-9).");
				} else {
					System.out.println("Please enter a valid number (0-9).");
				}
			}

			if (userInput == 1) {

				System.out.println("\n--------------- ADD NEW FLIGHT ---------------");
				System.out.println("Flight ID: ");

				String flightId = sc.nextLine();

				System.out.println("Airline ID: ");

				String airlineId = sc.nextLine();
				boolean find = false;
				while (true) {
					if (!airlineId.toLowerCase().equalsIgnoreCase("aircan")
							&& !airlineId.toLowerCase().equalsIgnoreCase("usair")
							&& !airlineId.toLowerCase().equalsIgnoreCase("britair")
							&& !airlineId.toLowerCase().equalsIgnoreCase("airfrance")
							&& !airlineId.toLowerCase().equalsIgnoreCase("luftair")
							&& !airlineId.toLowerCase().equalsIgnoreCase("italair")) {

						System.out.println(
								"Please choose one of the following: AirCan, USAir, BritAir, AirFrance, LuftAir, ItalAir.");
						airlineId = sc.nextLine();
					} else {
						PreparedStatement prepStat = null;
						String query = ("select  count(distinct flightid) from flight where flightairlineid = ? and flightid = ?");
						prepStat = conn.prepareStatement(query);
						prepStat.setString(1, airlineId);
						prepStat.setString(2, flightId);
						ResultSet rs = prepStat.executeQuery();
						if (rs.next()) {
							int count = rs.getInt(1);
							if (count > 0) {
								System.out.println("A Flight with the same ID in the same Airline already exists!");
								find = true;
							}
						}
						break;
					}

				}
				if (find) {
					break;
				}
				System.out.println("Business Available: ");
				boolean businessAvailable = false;
				String buav = null;
				while (true) {
					buav = sc.nextLine();
					if (buav.toLowerCase().equalsIgnoreCase("true") || buav.toLowerCase().equalsIgnoreCase("false")) {
						businessAvailable = Boolean.valueOf(buav);
						break;
					} else {
						System.out.println("Please enter True/False.");
					}
				}

				System.out.println("Smoking Available: ");
				boolean smokingAvailable = false;
				String smav = null;
				while (true) {
					smav = sc.nextLine();
					if (smav.toLowerCase().equalsIgnoreCase("true") || smav.toLowerCase().equalsIgnoreCase("false")) {
						smokingAvailable = Boolean.valueOf(smav);
						break;
					} else {
						System.out.println("Please enter True/False.");
					}
				}

				System.out.println("Flight departure city :");

				String flightDep = sc.nextLine();

				System.out.println("Flight arrival city : ");

				String flightArr = sc.nextLine();

				System.out.println("Flight time (Format HH:MM ) :");
				String flightTime = null;
				while (true) {
					String fTime = sc.next();
					if (fTime.contains(":")) {
						String arr[] = fTime.split(":");

						int hh = Integer.parseInt(arr[0]);
						int mm = Integer.parseInt(arr[1]);

						if ((arr[0].matches("[0-9]+") && hh < 24) && (arr[1].matches("[0-9]+") && mm < 60)) {
							flightTime = fTime;
							break;
						}
					} else {
						System.out.println("Please enter a valid time format (HH:MM ) :");
					}
				}
				int totalBusiness = 0;
				int takenBusiness = 0;
				if (businessAvailable) {
					System.out.println("Flight total businessseats");

					while (true) {
						number = sc.nextLine();
						if (number.matches("[0-9]+")) {
							totalBusiness = Integer.parseInt(number);
							break;
						} else {
							System.out.println("Please enter a valid number of seats.");
						}
					}

					System.out.println("Flight taken businessseats");

					while (true) {
						number = sc.nextLine();
						if (number.matches("[0-9]+") && totalBusiness >= Integer.parseInt(number)) {
							takenBusiness = Integer.parseInt(number);
							break;
						} else {
							System.out.println("Please enter a valid number of seats.");
						}
					}

				}

				System.out.println("Flight total ecoseats");
				int totalEco = 0;
				while (true) {
					number = sc.nextLine();
					if (number.matches("[0-9]+")) {
						totalEco = Integer.parseInt(number);
						break;
					} else {
						System.out.println("Please enter a valid number of seats.");
					}
				}

				System.out.println("Flight taken ecoseats");
				int takenEco = 0;
				while (true) {
					number = sc.nextLine();
					if (number.matches("[0-9]+") && totalEco >= Integer.parseInt(number)) {
						takenEco = Integer.parseInt(number);
						break;
					} else {
						System.out.println("Please enter a valid number of seats.");
					}
				}

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

				LocalDate localDate = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
				String bookingDate = localDate.format(formatter);

				System.out.println("Booking Seat Type: ");
				String bookingSeatType = sc.nextLine();
				while (true) {
					if (bookingSeatType.toLowerCase().equalsIgnoreCase("Business")
							|| bookingSeatType.toLowerCase().equalsIgnoreCase("Economy")) {
						break;
					} else {
						System.out.println("Please enter a valid seat type (Business or Economy):");
						bookingSeatType = sc.nextLine();
					}
				}

				System.out.println("Booking Total Price: ");
				float totalPrice = 0;
				while (true) {
					number = sc.nextLine();
					if (number.matches("[+]?([0-9]*[.])?[0-9]+")) {
						totalPrice = Float.parseFloat(number);
						break;
					} else {
						System.out.println("Please enter a valid price.");
					}
				}

				System.out.println("Booking Flight Price: ");

				float flightPrice = 0;
				while (true) {
					number = sc.nextLine();
					if (number.matches("[+]?([0-9]*[.])?[0-9]+")) {
						flightPrice = Float.parseFloat(number);
						break;
					} else {
						System.out.println("Please enter a valid price.");
					}
				}

				System.out.println("Booking Seat Condition (a, b, c): ");
				String seatCondition = null;
				while (true) {
					number = sc.nextLine();
					if (number.toLowerCase().contentEquals("a") || number.toLowerCase().contentEquals("b")
							|| number.toLowerCase().contentEquals("c")) {
						seatCondition = number;
						break;
					} else {
						System.out.println(
								"Please enter a valid Seat Condition( a=held-paid off , b=cancelled , c=held-payment in advance(10 days from the booking day to pay).");
					}
				}

				System.out.println("Booking Pre-Payment: ");

				float prePayment = 0;
				while (true) {
					number = sc.nextLine();
					if (number.matches("[+]?([0-9]*[.])?[0-9]+")) {
						prePayment = Float.parseFloat(number);
						break;
					} else {
						System.out.println("Please enter a valid price.");
					}
				}

				System.out.println("Booking Payment Remainder: ");

				float paymentRemainder = flightPrice - prePayment;

				PreparedStatement prepStat = conn
						.prepareStatement("INSERT INTO booking (bookingid, bookingcity, bookingdate, "
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

			} else if (userInput == 3) {

				PreparedStatement prepStat = null;
				String query = ("select * from flight where flighttotalbusinessseats=flighttakenbusinessseats and flighttotalecoseats=flighttakenecoseats");
				prepStat = conn.prepareStatement(query);
				ResultSet rs = prepStat.executeQuery();
				if (rs.next()) {
						System.out.println("All the full flights: ");
						String fid = rs.getString("flightid");
						String airid = rs.getString("flightairlineid");
						int bs = rs.getInt("flighttotalbusinessseats");
						int es = rs.getInt("flighttotalecoseats");
						System.out.println("The flight from the " + airid + " airline with the flightId of : " + fid +" is full with " +bs+" business seats and " + es+" economy seats." );
					
				}else {
					System.out.println("no flight found");
				}
			} else if (userInput == 4) {
				String toronto="toronto";
				String newyork="newyork";
				PreparedStatement prepStat = null;
				String query = ("select count(*) from flight where (flighttotalbusinessseats=? and flighttotalecoseats=?) or (flighttotalbusinessseats=? and flighttotalecoseats=?) ");
				prepStat = conn.prepareStatement(query);
				ResultSet rs = prepStat.executeQuery();
				prepStat.setString(1, toronto);
				prepStat.setString(2, newyork);
				if (rs.next()) {
					int count = rs.getInt(1);
					if (count == 0) {
						System.out.println("No flights have been registered yet!");

					} else {
						System.out.println("All the full flights: ");
						String fid = rs.getString("flightid");
						String airid = rs.getString("flightairlineid");
						int bs = rs.getInt("flighttotalbusinessseats");
						int es = rs.getInt("flighttotaleconomyseats");
						System.out.println("The flight from the" + airid + "airline with the flightId of : " + fid
								+ "is full with " + bs + "business seats and " + es + "economy seats.");
					}
				}
				
				
			}else if (userInput == 5) {
				System.out.println("---------------Booking ticket page---------------");
				System.out.println("Flight ID: ");
				String flightId = sc.nextLine();
				System.out.println("Airline ID: ");
				String airlineId = sc.nextLine();
				boolean find = false;
				while (true) {
					if (!airlineId.toLowerCase().equalsIgnoreCase("aircan")
							&& !airlineId.toLowerCase().equalsIgnoreCase("usair")
							&& !airlineId.toLowerCase().equalsIgnoreCase("britair")
							&& !airlineId.toLowerCase().equalsIgnoreCase("airfrance")
							&& !airlineId.toLowerCase().equalsIgnoreCase("luftair")
							&& !airlineId.toLowerCase().equalsIgnoreCase("italair")) {

						System.out.println(
								"Please choose one of the following: AirCan, USAir, BritAir, AirFrance, LuftAir, ItalAir.");
						airlineId = sc.nextLine();
					}else {
						find=true;
						
					
					}
					if (find) {
						PreparedStatement prepStat = null;
						String query = ("select flight=? and flightairlineid=? from flight");
						prepStat = conn.prepareStatement(query);
						ResultSet rs = prepStat.executeQuery();
						if (rs.next()) {
							int count = rs.getInt(1);
							if (count == 0) {
								System.out.println("No flights at this airline have been registered yet!");

							} else {
								System.out.println("All the full flights: ");
								String fid = rs.getString("flightid");
								String airid = rs.getString("flightairlineid");
								int bs = rs.getInt("flighttotalbusinessseats");
								int es = rs.getInt("flighttotaleconomyseats");
								System.out.println("The flight from the" + airid + "airline with the flightId of : " + fid
										+ "is full with " + bs + "business seats and " + es + "economy seats.");
							}
						
					}
				 }
				}
			}else if (userInput == 7) {
				System.out.println("Enter the Prices!");
			}
		}
		sc.close();
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