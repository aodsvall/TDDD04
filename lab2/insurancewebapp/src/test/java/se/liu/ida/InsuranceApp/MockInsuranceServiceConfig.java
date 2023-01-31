package se.liu.ida.InsuranceApp;



import java.util.HashMap;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import se.liu.ida.InsuranceApp.services.InsuranceService;

@Profile("mockInsuranceService")
@Configuration
public class MockInsuranceServiceConfig {
	
int nrOfClients = 0;
int nrOfCars = 0;
HashMap<Integer, Integer> carsForMember = new HashMap<>();

	@Bean
	@Primary 
	public InsuranceService insuranceService() {
		InsuranceService is =  Mockito.mock(InsuranceService.class);
		
	carsForMember.put(nrOfClients, nrOfCars);
		
		  Mockito.doAnswer(new Answer<Void>() {
		        @Override
		        public Void answer(InvocationOnMock invocation) throws Throwable {
		        	nrOfClients += 1;
		        	System.out.println(nrOfClients);
		            return null;
		        }
		    }).when(is).registerNewMember(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt());
		
		  
		  /** Add mocked behavior */
		  Mockito.doAnswer(new Answer<Integer>() {
		        @Override
		        public Integer answer(InvocationOnMock invocation) throws Throwable {
		        	System.out.println(nrOfClients);
		            return nrOfClients;
		        }
		    }).when(is).clientNumber();
		  
		  
		  
		  Mockito.doAnswer(new Answer<String>() {
		        @Override
		        public String answer(InvocationOnMock invocation) throws Throwable {
		        	nrOfCars += 1;
		        	return(String.valueOf(nrOfCars));
		        }
		    }).when(is).addNewCarToMember(Mockito.anyInt(), Mockito.anyString(), Mockito.anyInt());
		  
		
		  
		  
		 
	 
		 return is;
	}

}
