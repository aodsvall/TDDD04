package insurance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InsuranceTest {
	
	
		private insurance.InsuranceCalculator insurance;
		private insurance.Client client;

		@BeforeEach
		public void setUp() {
			insurance = new insurance.InsuranceCalculator();
			client = new Client();								
		}

		@Test
		public void testException() throws InvalidClientData {		
			client.numberAccidents = -1;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.getClientDeductible(client));
		}
		
		
		@Test
		public void testAgeDiscountBoundary() throws InvalidClientData{
		    client.yearLicence = 1;
			client.age = 29;
			Assertions.assertEquals(insurance.getClientDeductible(client), 8000);
			client.age = 30; 
			Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
			client.age = 31;
			Assertions.assertEquals(insurance.getClientDeductible(client), 5000);

		}
		
		@Test
		public void testLicenseAgeDiscountBoundary() throws InvalidClientData{
		    client.age = 28;
		    client.yearLicence = 4;
			Assertions.assertEquals(insurance.getClientDeductible(client), 8000);
		    client.yearLicence = 5;
			Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
		    client.yearLicence = 6;
			Assertions.assertEquals(insurance.getClientDeductible(client), 5000);

		}
		@Test
		public void testNumberOfAccidentPrice() throws InvalidClientData {
			client.age = 35;
			client.numberAccidents = 0;
			client.isGoldMember = false;
			Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
			client.numberAccidents = 1;
			Assertions.assertEquals(insurance.getClientDeductible(client), 6000);
			client.numberAccidents = 2;
			Assertions.assertEquals(insurance.getClientDeductible(client), 7500);			
			client.numberAccidents = 3;
			Assertions.assertEquals(insurance.getClientDeductible(client), 9000);
			client.numberAccidents = 4;
			Assertions.assertEquals(insurance.getClientDeductible(client), 15000);		
			client.numberAccidents = 5;
			Assertions.assertEquals(insurance.getClientDeductible(client), 15000);			
		}
		
		@Test
		public void testNumberOfAccidentPriceIfGoldMember() throws InvalidClientData {
			client.age = 35;
			client.numberAccidents = 0;
			client.isGoldMember = true;
			Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
			client.numberAccidents = 1;
			Assertions.assertEquals(insurance.getClientDeductible(client), 5000);
			client.numberAccidents = 2;
			Assertions.assertEquals(insurance.getClientDeductible(client), 5000);			
			client.numberAccidents = 3;
			Assertions.assertEquals(insurance.getClientDeductible(client), 9000);
			client.numberAccidents = 4;
			Assertions.assertEquals(insurance.getClientDeductible(client), 15000);		
			client.numberAccidents = 5;
			Assertions.assertEquals(insurance.getClientDeductible(client), 15000);			
		}
		
		@Test
		public void testNumberOfAccidentInvalid() throws InvalidClientData {
			client.age = 35;
			client.numberAccidents = -2;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.getClientDeductible(client));
		}
		
		@Test
		public void invalidAge() throws InvalidClientData {
			client.age = 0;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.getClientDeductible(client));
			client.age = 150;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.getClientDeductible(client));
		}
		
		@Test
		public void invalidLicenseAge() throws InvalidClientData {
			client.age = 30;
			client.yearLicence = 35;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.getClientDeductible(client));
			client.yearLicence = -2;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.getClientDeductible(client));
		}
		
		@Test
		public void testAgeDiscountBoundaryPerMonth() throws InvalidClientData{
		    client.yearLicence = 1;
			client.age = 29;
			client.cars.add(new Car());
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 600);
			client.age = 30; 
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 500);
			client.age = 31;
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 500);

		}
		
		@Test
		public void NoCar() throws InvalidClientData {
			client.age = 29;
			client.yearLicence = 1;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.MonthlyInsuranceCost(client));
		}
		
		@Test
		public void ifCarIsRed() throws InvalidClientData {
			client.age = 30;
			client.yearLicence = 1;
			client.cars.add(new Car());
			client.cars.get(0).isRed = true;
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 600);
		}
		
		@Test
		public void multipleCars() throws InvalidClientData {
			client.age = 30;
			client.cars.add(new Car());
			client.cars.add(new Car());
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 700);
			client.cars.add(new Car());
			client.cars.get(2).isRed = true;
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 1000);
		}
		
		@Test
		public void monthlyDiscountOrNoDiscount() throws InvalidClientData {
			client.age = 30;
			client.monthsInsured = 9;
			client.isGoldMember = true;
			client.cars.add(new Car());
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 500);
			client.monthsInsured = 12;
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 450);
			client.isGoldMember = false;
			client.yearLastAccident = 0;
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 500);
			client.yearLastAccident = 1;
			Assertions.assertEquals(insurance.MonthlyInsuranceCost(client), 450);
		}
		
		@Test
		public void invalidMonthlyDiscount() throws InvalidClientData {
			client.age = 30;
			client.monthsInsured = -12;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.MonthlyInsuranceCost(client));
		}
		
		@Test
		public void invalidYearLastAccident() throws InvalidClientData {
			client.yearLastAccident = -1;
			Assertions.assertThrows(InvalidClientData.class, ()->insurance.MonthlyInsuranceCost(client));
		}
		
		
		
		
		
		
		
		
}
