package insurance;

import java.util.Vector;

public class Client {

	int id;
	int age;
	int yearLicence;
	int yearLastAccident;
	boolean isGoldMember;
	int numberAccidents; // number of accidents for the calendar year
	int monthsInsured; // how long the client has been insured
	
	Vector<Car> cars;
	
	public Client () {
		cars = new Vector<Car>();
	}
	
}
