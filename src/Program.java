import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
							+ "\n [5] Packed Flights.\n [6] Available Flights Betwen Toronto and New Yrok.\n [7] Booking Tickets.\n [8] Seat Status Upadte.\n [9] Canceled Bookings.\n [0] Exit.");
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
				boolean find = false;
				PreparedStatement check = null;
				String query = ("select  count(distinct flightid) from flight where flightid = ?");
				check = conn.prepareStatement(query);
				check.setString(1, flightId);
				ResultSet rs = check.executeQuery();
				if (rs.next()) {
					int count = rs.getInt(1);
					if (count > 0) {
						System.out.println("A Flight with the same ID already exists!");
						find = true;
					}
				}
				if (find) {
					continue;
				}

				System.out.println("Airline ID: ");

				String airlineId = sc.nextLine();
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
						break;
					}
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

				System.out.println("Flight departure city:");

				String flightDep = sc.nextLine();

				System.out.println("Flight arrival city: ");

				String flightArr = sc.nextLine();

				System.out.println("Flight time (Format HH:MM):");
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
						System.out.println("Please enter a valid time format (HH:MM):");
					}
				}
				int totalBusiness = 0;
				int takenBusiness = 0;
				if (businessAvailable) {
					System.out.println("Flight total Business Seats:");
					while (true) {
						number = sc.next();
						if (number.matches("[0-9]+")) {
							totalBusiness = Integer.parseInt(number);
							break;
						} else {
							System.out.println("Please enter a valid number of seats.");
						}
					}

					System.out.println("Flight taken Business Seats:");

					while (true) {
						number = sc.next();
						if (number.matches("[0-9]+") && totalBusiness >= Integer.parseInt(number)) {
							takenBusiness = Integer.parseInt(number);
							break;
						} else {
							System.out.println("Please enter a valid number of seats.");
						}
					}

				}

				System.out.println("Flight total Economy Seats:");
				int totalEco = 0;
				while (true) {
					number = sc.next();
					if (number.matches("[0-9]+")) {
						totalEco = Integer.parseInt(number);
						break;
					} else {
						System.out.println("Please enter a valid number of seats.");
					}
				}

				System.out.println("Flight taken Economy Seats:");
				int takenEco = 0;
				while (true) {
					number = sc.next();
					if (number.matches("[0-9]+") && totalEco >= Integer.parseInt(number)) {
						takenEco = Integer.parseInt(number);
						break;
					} else {
						System.out.println("Please enter a valid number of seats.");
					}
				}

				PreparedStatement prepStat = conn.prepareStatement(
						"INSERT INTO flight (flightid, airlineid, flighthasbusiness, flighthassmoking) VALUES (?, ?, ?, ?)");

				prepStat.setString(1, flightId);
				prepStat.setString(2, airlineId);
				prepStat.setBoolean(3, businessAvailable);
				prepStat.setBoolean(4, smokingAvailable);

				prepStat.executeUpdate();

				PreparedStatement createAvailInput = conn
						.prepareStatement("INSERT INTO avail (flightdeparture, flightarrival, "
								+ "flighttime, flighttotalbusinessseats, flighttakenbusinessseats, "
								+ "flighttotalecoseats, flighttakenecoseats, flight_flightid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

				createAvailInput.setString(1, flightDep);
				createAvailInput.setString(2, flightArr);
				createAvailInput.setString(3, flightTime);
				createAvailInput.setInt(4, totalBusiness);
				createAvailInput.setInt(5, takenBusiness);
				createAvailInput.setInt(6, totalEco);
				createAvailInput.setInt(7, takenEco);
				createAvailInput.setString(8, flightId);

				createAvailInput.executeUpdate();

				System.out.println("Added flight " + flightId + " successfully to the database.");

			} else if (userInput == 2) {

				System.out.println("\n--------------- NEW BOOKING ---------------");

				String bookingId = getRandomId();
				while(true) {
					PreparedStatement bExist = null;
					String query = ("select clientid=? from client");
					bExist = conn.prepareStatement(query);
					bExist.setString(1, bookingId);
					ResultSet rs = bExist.executeQuery();
					if(!rs.next()) {
						break;
					}
				}
				
				
				String cID=null;
				System.out.println("Is the Client registered? (Y/N)");
				String ans = sc.nextLine();
				while (true) {
					if (!(ans.trim().equalsIgnoreCase("y") || ans.trim().equalsIgnoreCase("n") )) {
						System.out.println("Please enter a valid answer (Y/N)");
						ans = sc.nextLine();
					} else {
						break;
					}
				}
				ans = ans.substring(0, 1);
				if (ans.trim().equalsIgnoreCase("N")) {
					while (true) {
						 cID = getRandomId();
						PreparedStatement cExist = null;
						String query = ("select clientid=? from client");
						cExist = conn.prepareStatement(query);
						cExist.setString(1, cID);
						ResultSet rs = cExist.executeQuery();
						if (!rs.next()) {
							System.out.println("Enter the client's name:");
							String name = sc.nextLine();
							System.out.println("Enter the client's surname:");
							String surname = sc.nextLine();
							System.out.println("Add client's email?(Y/N)");
							String email = null;
							ans = sc.nextLine();
							while (true) {
								if (!(ans.trim().equalsIgnoreCase("y") || ans.trim().equalsIgnoreCase("y") )) {
									System.out.println("Please enter a valid answer (Y/N)");
									ans = sc.nextLine();
								} else {
									System.out.println("Please enter the client's email:");
									email = sc.nextLine();
									PreparedStatement emailExist = null;
									String qy = ("select clientemail=? from client");
									emailExist = conn.prepareStatement(qy);
									emailExist.setString(1, email);
									rs = cExist.executeQuery();
										while(rs.next()) {
											System.out.println("This email is taken, please anter a different one: ");
											email = sc.nextLine();
											qy = ("select clientemail=? from client");
											emailExist = conn.prepareStatement(qy);
											emailExist.setString(1, email);
											rs = cExist.executeQuery();
											
										}
									
									
									break;
								}
							}
							PreparedStatement stat = conn.prepareStatement(
									"INSERT INTO client (clientid, clientname, clientsurname, clientemail, clientaddressid) VALUES (?, ?, ?, ?, ?)");

							stat.setString(1, cID);
							stat.setString(2, name);
							stat.setString(3, surname);
							stat.setString(4, email);
							stat.setString(5, null);

							stat.executeUpdate();
							// add
							while (true) {
								System.out.println("Add another email? (Y/N)");
								ans = sc.nextLine();
								if (!(ans.trim().equalsIgnoreCase("y") || ans.trim().equalsIgnoreCase("y") )) {
									System.out.println("Please enter a valid answer (Y/N)");
									ans = sc.nextLine();
								} else {
									if (ans.trim().equalsIgnoreCase("y")) {
										System.out.println("Enter the client's email:");
										email = sc.nextLine();
										stat = conn.prepareStatement(
												"INSERT INTO client (cliendid,clientmail) VALUES (?,?)");
										stat.setString(1, cID);
										stat.setString(2, email);
										stat.executeUpdate();

										// add
									} else {
										break;
									}

								}
							}

							System.out.println("Add client's fax?(Y/N)");
							ans = sc.nextLine();
							while (true) {
								if (!(ans.trim().equalsIgnoreCase("y") || ans.trim().equalsIgnoreCase("y") )) {
									System.out.println("Please enter a valid answer (Y/N)");
									ans = sc.nextLine();
								} else {
									break;
								}
							}
							System.out.println("Enter the client's country code:");
							String cCode = sc.nextLine();
							System.out.println("Enter the client's region code:");
							String rCode = sc.nextLine();
							System.out.println("Enter the client's local number:");
							String lNumber = sc.nextLine();
							String faxid = cCode + rCode + lNumber;
							PreparedStatement FAX = conn.prepareStatement(
									"INSERT INTO fax (faxid, faxcountrycode, faxregioncode, faxlocalnumber, client_clientid) VALUES (?, ?, ?, ?, ?)");

							FAX.setString(1, faxid);
							FAX.setString(2, cCode);
							FAX.setString(3, rCode);
							FAX.setString(4, lNumber);
							FAX.setString(5, cID);
							FAX.executeUpdate();
							// add
							while (true) {
								System.out.println("Add another fax? (Y/N)");
								ans = sc.nextLine();
								if (!(ans.trim().equalsIgnoreCase("y") || ans.trim().equalsIgnoreCase("y") )) {
									System.out.println("Please enter a valid answer (Y/N)");
									ans = sc.nextLine();
								} else {
									if (ans.trim().equalsIgnoreCase("y")) {
										System.out.println("Enter the client's country code:");
										cCode = sc.nextLine();
										System.out.println("Enter the client's region code:");
										rCode = sc.nextLine();
										System.out.println("Enter the client's local number:");
										lNumber = sc.nextLine();
										faxid = cCode + rCode + lNumber;
										FAX = conn.prepareStatement(
												"INSERT INTO fax (faxid, faxcountrycode, faxregioncode, faxlocalnumber, client_clientid) VALUES (?, ?, ?, ?, ?)");

										stat.setString(1, faxid);
										stat.setString(2, cCode);
										stat.setString(3, rCode);
										stat.setString(4, lNumber);
										stat.setString(5, cID);
										stat.executeUpdate();

										// add
									} else {
										break;
									}

								}
							}

							System.out.println("Add client's telephone?(Y/N)");
							ans = sc.nextLine();
							while (true) {
								if (!(ans.trim().equalsIgnoreCase("y") || ans.trim().equalsIgnoreCase("y") )) {
									System.out.println("Please enter a valid answer (Y/N)");
									ans = sc.nextLine();
								} else {
									break;
								}
							}
							System.out.println("Enter the client's country code:");
							String TcCode = sc.nextLine();
							System.out.println("Enter the client's region code:");
							String TrCode = sc.nextLine();
							System.out.println("Enter the client's local number:");
							String TlNumber = sc.nextLine();
							String telid = TcCode + TrCode + TlNumber;
							PreparedStatement TEL = conn.prepareStatement(
									"INSERT INTO telephone (telephoneid, countrycode, regioncode, localnumber, client_clientid) VALUES (?, ?, ?, ?, ?)");

							TEL.setString(1, TcCode);
							TEL.setString(2, TcCode);
							TEL.setString(3, TrCode);
							TEL.setString(4, TlNumber);
							TEL.setString(5, telid);
							TEL.executeUpdate();
							// add
							while (true) {
								System.out.println("Add another telephone? (Y/N)");
								ans = sc.nextLine();
								if (!(ans.trim().equalsIgnoreCase("y") || ans.trim().equalsIgnoreCase("y") )) {
									System.out.println("Please enter a valid answer (Y/N)");
									ans = sc.nextLine();
								} else {
									if (ans.trim().equalsIgnoreCase("y")) {
										System.out.println("Enter the client's country code:");
										TcCode = sc.nextLine();
										System.out.println("Enter the client's region code:");
										TrCode = sc.nextLine();
										System.out.println("Enter the client's local number:");
										TlNumber = sc.nextLine();
										faxid = TcCode + TrCode + TlNumber;
										TEL = conn.prepareStatement(
												"INSERT INTO telephone (telephoneid, countrycode, regioncode, localnumber, client_clientid) VALUES (?, ?, ?, ?, ?)");

										TEL.setString(1, TcCode);
										TEL.setString(2, TcCode);
										TEL.setString(3, TrCode);
										TEL.setString(4, TlNumber);
										TEL.setString(5, cID);
										TEL.executeUpdate();

										// add
									} else {
										break;
									}

								}
							}
							
							String addressid=null;
							while (true) {
								 addressid = getRandomId();
								PreparedStatement exist = null;
								String qu = ("select clientid=? from client");
								exist = conn.prepareStatement(qu);
								exist.setString(1, addressid);
								ResultSet res = exist.executeQuery();
								if(!rs.next()) {
									break;
								}
							}
							System.out.println("Enter the client's street");
							String cStreet=sc.nextLine();
							System.out.println("Enter the client's city");
							String cCity=sc.nextLine();
							System.out.println("Enter the client's Postal Code");
							String cPC=sc.nextLine();
							System.out.println("Enter the client's Country");
							String cCountry=sc.nextLine();
							PreparedStatement add = conn.prepareStatement(
									"INSERT INTO mailing_address (addressid, street, city, postalcode, country, client_clientid) VALUES (?, ?, ?, ?, ?, ?)");

							add.setString(1, addressid);
							add.setString(2, cStreet);
							add.setString(3, cCity);
							add.setString(4, cPC);
							add.setString(5, cCountry);
							add.setString(5, cID);
							
							add.executeUpdate();
							
						
					
				} else {
					break;// arxiko while(client id)
				}
						
			}
		}else {
		System.out.println("Enter the client's id:");
		 cID=sc.nextLine();
		 while(true) {
		    PreparedStatement cExist = null;
			String query = ("select clientid=? from client");
			cExist = conn.prepareStatement(query);
			cExist.setString(1, cID);
			ResultSet rs = cExist.executeQuery();
			if(!rs.next()) {
				break;
			}
		 }
		 
		}
		System.out.println("Enter the flight id: ");

		String flid = sc.nextLine();		
		System.out.println("Booking City: ");

		String bookingCity = sc.nextLine();

		System.out.println("Booking Date: ");

		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
		String bookingDate = localDate.format(formatter);

		System.out.println("Booking Seat Type: ");
		String bookingSeatType = sc.nextLine();
		while (true) {
			if (bookingSeatType.toLowerCase().equalsIgnoreCase("business")
					|| bookingSeatType.toLowerCase().equalsIgnoreCase("economy")) {
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
						"Please enter a valid Seat Condition(a = held-paid off, b = cancelled, c = held-payment in advance (10 days from the booking day to pay).");
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

		PreparedStatement prepStat = conn.prepareStatement("INSERT INTO booking (bookingid, bookingcity, bookingdate, "
				+ "bookingseattype, bookingtotalprice, bookingflightprice, "
				+ "bookingseatcondition, bookingprepayment, bookingremainder, client_clientid, flight_flightid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		prepStat.setString(1, bookingId);
		prepStat.setString(2, bookingCity);
		prepStat.setString(3, bookingDate);
		prepStat.setString(4, bookingSeatType);
		prepStat.setFloat(5, totalPrice);
		prepStat.setFloat(6, flightPrice);
		prepStat.setString(7, seatCondition);
		prepStat.setFloat(8, prePayment);
		prepStat.setFloat(9, paymentRemainder);
		prepStat.setString(10, cID);
		prepStat.setString(11, flid);
		

		prepStat.executeUpdate();

	}else if(userInput==3)

	{	
		System.out.println("All the registered flights:");

		PreparedStatement prepStat = null;
		PreparedStatement prepSeats = null;
		
		String query = ("select * from flight");
		prepStat = conn.prepareStatement(query);
		ResultSet rs = prepStat.executeQuery();
		while (rs.next()) {

			String fid = rs.getString("flightid");
			String airid = rs.getString("airlineid");
			String query2 = ("select count(*) from avail where flight_flightid=? and flighttotalbusinessseats=flighttotalbusinessseats and flighttotalecoseats=flighttakenecoseats");
			prepSeats = conn.prepareStatement(query2);
			prepSeats.setString(1, fid);
			ResultSet res = prepSeats.executeQuery();
			int count=res.getInt("count(*)");
			if(count>0) {
			int bs = res.getInt("flighttotalbusinessseats");
			int es = res.getInt("flighttotalecoseats");
			System.out.println("Flight" + fid +" from the " + airid + " airline" + "bs" + bs + " es" + es);
			}else {
				break;
			}
		} 
	}else if(userInput==4)
	{
		String toronto = "toronto";
		String newyork = "newyork";
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

	}else if(userInput==5)
	{
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
			} else {
				find = true;

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
	}else if(userInput==7)
	{
		System.out.println("Enter the Prices!");
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
