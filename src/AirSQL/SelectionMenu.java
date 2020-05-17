package AirSQL;

import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;

public class SelectionMenu {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Flight> flights = new ArrayList<Flight>();

		while (true) {
			System.out.println("\n--------------- AIRLINE MANAGMENT SYSTEM ---------------");
			System.out.println(
					"Main menu: \n [1] Insert Flights.\n [2] Update Clients Profiles.\n [3] Ticket Availability .\n [4] Flights via Airline."
							+ "\n [5] Packed Flights.\n [6] Available Flights Betwen Toronto and New Yrok.\n [7] Booking Tickets.\n [8] Seat Status Upadte.\n [9]Canceled Bookings.\n [0] Exit.");
			int userIn = sc.nextInt();
			sc.nextLine();
			if (userIn == 1) {
				System.out.print("Enter the airlines' ID: ");
				String airId = sc.nextLine();
				if (!"airfrance".equalsIgnoreCase(airId) || !"aircan".equalsIgnoreCase(airId)
						|| !"usair".equalsIgnoreCase(airId) || !"britair".equalsIgnoreCase(airId)
						|| !"luftair".equalsIgnoreCase(airId) || !"itair".equalsIgnoreCase(airId)) {

					do {
						System.out.println("Please enter a valid Airline ID! :");
						airId = sc.nextLine();
					} while (!"airfrance".equalsIgnoreCase(airId) && !"aircan".equalsIgnoreCase(airId)
							&& !"usair".equalsIgnoreCase(airId) && !"britair".equalsIgnoreCase(airId)
							&& !"luftair".equalsIgnoreCase(airId) && !"itair".equalsIgnoreCase(airId));
				}
				System.out.print("Enter the flights' ID: ");
				String fId = sc.nextLine();
				boolean foundFlight = false;

				for (int i = 0; i < flights.size(); i++) {
					if (fId.equalsIgnoreCase(flights.get(i).getFlightId())
							&& airId.equalsIgnoreCase(flights.get(i).getAirlineId())) {

						foundFlight = true;
						System.out.println("A flight with this ID in that airline already exists!");
						break;
					}

					if (foundFlight == true) {
						continue;
					}
				}
				String depAir;
				String arrAir;
				if ("aircan".equalsIgnoreCase(airId)) {
					do {
						System.out.print("Enter the departure airport ( Toronto Or Montreal) : ");
						depAir = sc.nextLine();

					} while (!"Toronto".equalsIgnoreCase(depAir) && !"Montreal".equalsIgnoreCase(depAir));
					System.out.print("Enter the arrival airport: ");
					arrAir = sc.nextLine();

				} else if ("usair".equalsIgnoreCase(airId)) {
					do {
						System.out.print("Enter the departure airport ( New York Or Chicago) : ");
						depAir = sc.nextLine();

					} while (!"New York".equalsIgnoreCase(depAir) && !"Chicago".equalsIgnoreCase(depAir));
					System.out.print("Enter the arrival airport: ");
					arrAir = sc.nextLine();

				} else if ("britair".equalsIgnoreCase(airId)) {
					do {
						System.out.print("Enter the departure airport ( London Or Edinburgh) : ");
						depAir = sc.nextLine();

					} while (!"London".equalsIgnoreCase(depAir) && !"Edinburgh".equalsIgnoreCase(depAir));
					System.out.print("Enter the arrival airport: ");
					arrAir = sc.nextLine();
				} else if ("airfrance".equalsIgnoreCase(airId)) {
					do {
						System.out.print("Enter the departure airport ( Paris Or Nice) : ");
						depAir = sc.nextLine();

					} while (!"Paris".equalsIgnoreCase(depAir) && !"Nice".equalsIgnoreCase(depAir));
					System.out.print("Enter the arrival airport: ");
					arrAir = sc.nextLine();
				} else if ("luftair".equalsIgnoreCase(airId)) {
					do {
						System.out.print("Enter the departure airport ( Berlin Or Bonn) : ");
						depAir = sc.nextLine();

					} while (!"Berlin".equalsIgnoreCase(depAir) && !"Bonn".equalsIgnoreCase(depAir));
					System.out.print("Enter the arrival airport: ");
					arrAir = sc.nextLine();

				} else {

					do {
						System.out.print("Enter the departure airport ( Rome Or Napoli) : ");
						depAir = sc.nextLine();

					} while (!"Rome".equalsIgnoreCase(depAir) && !"Napoli".equalsIgnoreCase(depAir));
					System.out.print("Enter the arrival airport: ");
					arrAir = sc.nextLine();
				}

				System.out.println("Enter the number of total seats: ");
				int allSeats = sc.nextInt();
				System.out.println("Enter the number of Bussiness seats: ");
				int bSeats = sc.nextInt();
				System.out.println("Enter the number of inflight smoking seats: ");
				int sSeats = sc.nextInt();
				int ecoSeats = allSeats - bSeats - sSeats;

				LocalDate dateNow = LocalDate.now();
				String[] brokenDate;
				String[] brokenTime;
				String[] flibrokenTime = null;

				System.out.print("Enter the date of the flight (format: DD/MM/YYYY): ");
				String date = sc.nextLine();
				brokenDate = date.split("/");

				boolean dt;

				do {
					dt = false;
					if (brokenDate.length == 3) {
						if (Integer.parseInt(brokenDate[1]) < 1 || Integer.parseInt(brokenDate[1]) > 12) {
							System.out.print("Wrong month, please try again: ");
							date = sc.nextLine();
							brokenDate = date.split("/");
							dt = true;
							continue;
						}
						if ((Integer.parseInt(brokenDate[1]) == 1) || (Integer.parseInt(brokenDate[1]) == 3)
								|| (Integer.parseInt(brokenDate[1]) == 5) || (Integer.parseInt(brokenDate[1]) == 7)
								|| (Integer.parseInt(brokenDate[1]) == 8) || (Integer.parseInt(brokenDate[1]) == 10)
								|| (Integer.parseInt(brokenDate[1]) == 12)) {
							if ((Integer.parseInt(brokenDate[0]) > 31) || (Integer.parseInt(brokenDate[0]) <= 0)) {
								System.out.print("Wrong day, please try again: ");
								date = sc.nextLine();
								brokenDate = date.split("/");
								dt = true;
								continue;
							}
						} else if (Integer.parseInt(brokenDate[1]) == 2) {
							if ((Integer.parseInt(brokenDate[0]) > 29) || (Integer.parseInt(brokenDate[0]) <= 0)) {
								System.out.print("Wrong day, please try again: ");
								date = sc.nextLine();
								brokenDate = date.split("/");
								dt = true;
								continue;
							}

						} else {
							if ((Integer.parseInt(brokenDate[0]) > 30) || (Integer.parseInt(brokenDate[0]) <= 0)) {
								System.out.print("Wrong day, please try again: ");
								date = sc.nextLine();
								brokenDate = date.split("/");
								dt = true;
								continue;
							}
						}
						LocalDate dateTemp = LocalDate.of(Integer.parseInt(brokenDate[2]),
								Integer.parseInt(brokenDate[1]), Integer.parseInt(brokenDate[0]));
						if (dateTemp.isBefore(dateNow)) {
							System.out.print("This date has already passed, please try again: ");
							date = sc.nextLine();
							brokenDate = date.split("/");
							dt = true;
							continue;
						}

					} else {
						System.out.print("Wrong date format, please try again: ");
						date = sc.nextLine();
						brokenDate = date.split("/");
						dt = true;
						continue;
					}

				} while (dt == true);

				System.out.print("Enter the time of departure for the flight (format: HH:MM): ");
				String time = sc.nextLine();
				brokenTime = time.split(":");
				while (Integer.parseInt(brokenTime[0]) >= 24 || Integer.parseInt(brokenTime[0]) < 0
						|| Integer.parseInt(brokenTime[1]) > 59 || Integer.parseInt(brokenTime[1]) < 0) {
					System.out.print("Wrong time, please try again: ");
					time = sc.nextLine();
					brokenTime = time.split(":");
				}
				System.out.print("Enter the total time of the flight (format: HH:MM): ");
				time = sc.nextLine();
				flibrokenTime = time.split(":");
				while (Integer.parseInt(flibrokenTime[0]) >= 24 || Integer.parseInt(flibrokenTime[0]) < 0
						|| Integer.parseInt(flibrokenTime[1]) > 59 || Integer.parseInt(flibrokenTime[1]) < 0) {
					System.out.print("Wrong time, please try again: ");
					time = sc.nextLine();
					flibrokenTime = time.split(":");
				}
				System.out.println("Enter the flights' cost :");
				double fcost = sc.nextDouble();

				Flight flight = new Flight();
				
				flight.setTotalTakenSeats(0);
				flight.setFlightCost(fcost);
				flight.setFlightId(fId);
				flight.setAirlineId(airId);
				flight.setDepartureAirport(depAir);
				flight.setArrivalAirport(arrAir);
				flight.setTotalSeats(allSeats);
				flight.setTotalEcoSeats(ecoSeats);
				flight.setTotalBusSeats(bSeats);
				flight.setTotalSmoSeats(sSeats);
				flight.setTakenBusSeats(0);
				flight.setTakenEcoSeats(0);
				flight.setTakenSmoSeats(0);
				flight.setDate(brokenDate[2], brokenDate[1], brokenDate[0]);
				flight.setTime(brokenTime[0], brokenTime[1]);
				flight.setFlightTime(flibrokenTime[0], flibrokenTime[1]);

				flights.add(flight);

			} else if (userIn == 2) {
				System.out.println("Please enter the Buyers' personal information. ");
				System.out.println("Please enter the Buyers' full name. ");
				String fullName = sc.nextLine();
				System.out.println("Please enter the Buyers' mailing address. ");
				String mail = sc.nextLine();
				System.out.println("Add phone number?( Yes/No). ");
				String ans = sc.nextLine();
				String phoneNum;
				if (ans.equalsIgnoreCase("Yes")) {
					System.out.println("Please enter the Buyers' phone number. ");
					do {
						phoneNum = sc.nextLine();
						System.out.println("Add another phone number?( Yes/No). ");
						ans = sc.nextLine();
					} while (!ans.equalsIgnoreCase("No"));
				}
				System.out.println("Add Fax number?( Yes/No). ");
				String faxCodes[] = null;
				ans = sc.nextLine();
				String faxNum;
				if (ans.equalsIgnoreCase("Yes")) {
					System.out.println("Please enter the Buyers' Fax number. ");
					do {
						faxNum = sc.nextLine();
						faxCodes[0] = faxNum.substring(0, 3);
						faxCodes[1] = faxNum.substring(3, 6);
						faxCodes[2] = faxNum.substring(6, 20);

						System.out.println("Add another Fax number?( Yes/No). ");
						ans = sc.nextLine();
					} while (!ans.equalsIgnoreCase("No"));
				}
				System.out.println("Add email address ?( Yes/No). ");
				ans = sc.nextLine();
				String eMail;
				if (ans.equalsIgnoreCase("Yes")) {
					System.out.println("Please enter the Buyers' email address. ");
					do {
						eMail = sc.nextLine();
						/*
						 * if (eMaillArr.contains(eMail)) {
						 * System.out.println("This email is already registered!"); break; }
						 */
						System.out.println("Add another email address?( Yes/No). ");
						ans = sc.nextLine();
					} while (!ans.equalsIgnoreCase("No"));
				}
			} else if (userIn == 3) {
				int i = 0;
				boolean exist = true;
				System.out.println("Please enter the flights' ID : ");
				String tfid = sc.nextLine();
				System.out.println("Please enter the airlines' ID : ");
				String airfid = sc.nextLine();
				do {
					for (i = 0; i < flights.size(); i++) {
						if (tfid.equalsIgnoreCase(flights.get(i).getFlightId())
								&& airfid.equalsIgnoreCase(flights.get(i).getAirlineId())) {
							System.out.println("Flights' ID : " + flights.get(i).getFlightId());
							System.out.println("Airlines' ID : " + flights.get(i).getAirlineId());
							System.out.println("Avaiable Business : "
									+ (flights.get(i).getTotalBusSeats() - flights.get(i).getTakenBusSeats()));
							System.out.println("Avaiable Economy : "
									+ (flights.get(i).getTakenEcoSeats() - flights.get(i).getTakenEcoSeats()));
							System.out.println("Avaiable Smoking : "
									+ (flights.get(i).getTotalSmoSeats() - flights.get(i).getTakenSmoSeats()));

						}
						if (exist == false) {
							System.out.println("This flights' ID is invalid, please try again :");
							continue;
						}
					}
				} while (exist);
			} else if (userIn == 4) {
				for (int j = 0; j < 6; j++) {
					if (j==0) {
						System.out.println("AirCan flights' IDs :");
					}else if(j==1) {
						System.out.println("USair flights' IDs :");
					}else if(j==2) {
						System.out.println("BritAir flights' IDs :");
					}else if(j==3) {
						System.out.println("AirFrance flights' IDs :");
					}else if(j==4) {
						System.out.println("LuftAir flights' IDs :");
					}else {
						System.out.println("ItalAir flights' IDs :");
					}
					for (int i = 0; i < flights.size(); i++) {
						if(flights.get(i).getAirlineId().equalsIgnoreCase("aircan")) {
							System.out.println(flights.get(i).getFlightId());
						}else if(flights.get(i).getAirlineId().equalsIgnoreCase("usair")) {
							System.out.println(flights.get(i).getFlightId());
						}else if(flights.get(i).getAirlineId().equalsIgnoreCase("britair")) {
							System.out.println(flights.get(i).getFlightId());
						}else if(flights.get(i).getAirlineId().equalsIgnoreCase("airfrance")) {
							System.out.println(flights.get(i).getFlightId());
						}else if(flights.get(i).getAirlineId().equalsIgnoreCase("luftair")) {
							System.out.println(flights.get(i).getFlightId());
						}else {
							System.out.println(flights.get(i).getFlightId());
						}
					}
				}
			} else if (userIn == 5) {
				boolean empty=true;
				for (int i = 0; i < flights.size(); i++) {
					if(flights.get(i).getTotalTakenSeats()==flights.get(i).getTotalSeats()) {
						if(empty) {
							System.out.println("List with Full Flights: ");
							empty=false;
						}
						System.out.println(" Airline's ID :" + flights.get(i).getAirlineId() + " Flight's ID : " + flights.get(i).getFlightId());
					}
					
				}
				if(empty) {
					System.out.println("There are no full flights.");
				}
			} else if (userIn == 6) {
				String fliAir[]=null;
				int ids[]=null;
				int k=0;
				int temp;
				String temp2;
				for (int i = 0; i < flights.size(); i++) {
					if((flights.get(i).getDepartureAirport().equalsIgnoreCase("toronto") && flights.get(i).getDepartureAirport().equalsIgnoreCase("New York")) || (flights.get(i).getArrivalAirport().equalsIgnoreCase("toronto") && flights.get(i).getArrivalAirport().equalsIgnoreCase("New York"))){
						ids[k]=Integer.parseInt(flights.get(i).getFlightId());
						fliAir[k]="From : "+ flights.get(i).getDepartureAirport() + " to " + flights.get(i).getArrivalAirport() + "With flight Id: " + flights.get(i).getFlightId();
						k++;
					}
					for(i=0; i<ids.length; i++) {
						if (ids[i]>ids[i+1]) {
							temp=ids[i];
							ids[i]=ids[i+1];
							ids[i+1]=temp;
							temp2=fliAir[i];
							fliAir[i]=fliAir[i+1];
							fliAir[i+1]=temp2;
						}
					}
				}
				for(int i=0; i<fliAir.length; i++) {
					System.out.println(fliAir[i]);
					
				}

			} else if (userIn == 7) {
				System.out.println("-----Booking Ticket Menu-----");
				if(flights.size()==0) {
					System.out.println("There are no avaible flights in any airline!");
					continue;
				}
				System.out.print("Enter the airlines' ID: ");
				String airId = sc.nextLine();
				if (!"airfrance".equalsIgnoreCase(airId) || !"aircan".equalsIgnoreCase(airId)
						|| !"usair".equalsIgnoreCase(airId) || !"britair".equalsIgnoreCase(airId)
						|| !"luftair".equalsIgnoreCase(airId) || !"itair".equalsIgnoreCase(airId)) {

					do {
						System.out.println("Please enter a valid Airline ID! :");
						airId = sc.nextLine();
					} while (!"airfrance".equalsIgnoreCase(airId) && !"aircan".equalsIgnoreCase(airId)
							&& !"usair".equalsIgnoreCase(airId) && !"britair".equalsIgnoreCase(airId)
							&& !"luftair".equalsIgnoreCase(airId) && !"itair".equalsIgnoreCase(airId));
				}
				System.out.println("Please enter the flight's ID.");
				String tiflid=sc.nextLine();
				boolean f=true;
				int i=0;
				do {
					for ( i=0; i<flights.size( ); i++) {
						if(airId.equalsIgnoreCase(flights.get(i).getAirlineId()) && tiflid.equalsIgnoreCase(flights.get(i).getFlightId())) {
							System.out.println("Flight Found!");
							
						}
					}
					
				}while( i <flights.size() && f);
				

			} else if (userIn == 8) {

			} else if (userIn == 9) {

			} else if (userIn == 0) {
				System.out.println("Exiting...");
				break;
			} else {
				System.out.println("Wrong input. Please try a number between 0 - 9.");
			}
		}
		sc.close();
	}
}
