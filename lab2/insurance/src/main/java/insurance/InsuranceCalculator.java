package insurance;

public class InsuranceCalculator {
	/**
	 * Calculate the deductible for the client Base cost is 5000 SEK if the client
	 * is above 30 or has had a driving licence for more that 5 years and 8000 SEK
	 * otherwise With every accident for that calendar the deductible increases: 1
	 * accident : by 1000 SEK 2 accidents : by 2500 SEK 3 accidents : by 4000 SEK 4
	 * accidents and more by : 10000 SEK If the client if a gold member, then for
	 * the first 2 accidents, there is no increase but for 3 accidents and more
	 * normal rates apply
	 * 
	 * @param clientId
	 * @return the amount of the deductible
	 * @throws InvalidClientData
	 */
	
	
	
	int getClientDeductible(Client cl) throws InvalidClientData {
		int baseCost = 8000;
		if (cl.numberAccidents < 0)
			throw new InvalidClientData();
		if(cl.age < 18 || cl.age > 100)
			throw new InvalidClientData();
		if(cl.yearLicence > cl.age-18 || cl.yearLicence < 0)
			throw new InvalidClientData();

		if (cl.age >= 30 || cl.yearLicence >= 5)
			baseCost = 5000;


		switch (cl.numberAccidents) {
		case 0:
			return baseCost;

		case 1:
			if (cl.isGoldMember)
				return baseCost;
			return baseCost + 1000;

		case 2:
			if (cl.isGoldMember)
				return baseCost;
				return baseCost + 2500;

		case 3:
			return baseCost + 4000;

		default:
			return baseCost + 10000;

		}

	}

	/**
	 * Calculate the monthly cost for the service for the client First year rate is
	 * 500SEK if the client is above 30 or has had a driving licence for more that 5
	 * years and 600 SEK otherwise If the car is red the cost goes up by 100SEK Each
	 * additional car adds 200 SEK unless it is red then it adds 300SEK.
	 * After the first year, there is a 10% discount if there were 0 accidents that year or if
	 * the client is a gold member
	 * 
	 * @param clientId
	 * @return
	 */
	 
	
	int MonthlyInsuranceCost(Client cl)throws InvalidClientData  {
		int rate = 600;
		if (cl.age >= 30 || cl.yearLicence >= 5)
			rate = 500;
		if(cl.cars.isEmpty()) 
			throw new InvalidClientData();
		if (cl.cars.get(0).isRed)
			rate += 100;

		else {
			for (int i = 1; i < cl.cars.size(); i++) {
				if (cl.cars.get(i).isRed)
					rate += 300;
				else
					rate += 200;
			}
		}

		if(cl.monthsInsured >= 12)
			if(cl.isGoldMember || cl.yearLastAccident > 0)
				return (int) Math.round(rate * 0.9);
		
		return rate;
				

	

	}
}
