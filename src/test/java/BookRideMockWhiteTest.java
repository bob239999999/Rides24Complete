import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;

public class BookRideMockWhiteTest {

	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected EntityManagerFactory entityManagerFactory; 
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;
	@Mock
	TypedQuery<User> typedQuery;
    
    
    @Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
    }
    
    
	@After
    public  void tearDown() {
		persistenceMock.close();
    }

	@Test
	public void testInvalidUser() {
		
	    String invalidUser = "invalidUser";  
	    Ride ride = new Ride("City A","City B", new Date(),1, 100.0,new Driver("drivername","AB01"));
	    Mockito.when(db.find(Traveler.class, invalidUser)).thenReturn(null);
	    
	    sut.open();
	    boolean result = sut.bookRide(invalidUser, ride,1, 0.0);
	    sut.close();

	    assertFalse(result);
	}

	@Test 
	public void testInsufficientSeats() {
		String validUser = "validUser";
		Ride ride = Mockito.mock(Ride.class);
		
		Traveler traveler = new Traveler(validUser, "admin");
		traveler.setMoney(150.0);
	
		Mockito.when(db.find(Traveler.class, validUser)).thenReturn(traveler);
		Mockito.when(ride.getnPlaces()).thenReturn(1);
		
	    sut.open();
    	boolean result = sut.bookRide(validUser, ride, 2, 0.0);
    	sut.close();

    	assertTrue(result);
	}
	
	@Test 
	public void testInsufficientBalance() {
		
		String validUser = "validUser";
		Ride ride = Mockito.mock(Ride.class);
		
		Traveler traveler = new Traveler(validUser, "admin");
		traveler.setMoney(50.0);
	
		Mockito.when(db.find(Traveler.class, validUser)).thenReturn(traveler);
		Mockito.when(ride.getnPlaces()).thenReturn(1);
		
	    sut.open();
    	boolean result = sut.bookRide(validUser, ride, 1, 0.0);
    	sut.close();

    	assertFalse(result);
	}
	
	

		@Test
		// Caso 2: El usuario existe y se realiza un depósito. El saldo del usuario debe aumentar.
		public void testDiscountGreaterThanPrice() {
			
			String validUser = "validUser"; 
			Ride ride = new Ride("City A", "City B", new Date(), 1, 100.0, new Driver("drivername", "AB01"));
		    
			Traveler traveler = new Traveler(validUser, "admin");
			traveler.setMoney(150.0);
			
			Mockito.when(db.find(Traveler.class, validUser)).thenReturn(traveler);
			
		    sut.open();
	    	boolean result = sut.bookRide(validUser, ride, 1, 200.0);
	    	sut.close();

	    	assertFalse(result);
	
		}
		
		@Test
		// Caso 2: El usuario existe y se realiza un depósito. El saldo del usuario debe aumentar.
		public void testNegativeSeats() {
			
			String validUser = "validUser"; 
			Ride ride = new Ride("City A", "City B", new Date(), 1, 100.0, new Driver("drivername", "AB01"));
		    
			Traveler traveler = new Traveler(validUser, "admin");
			traveler.setMoney(150.0);
			
			Mockito.when(db.find(Traveler.class, validUser)).thenReturn(traveler);
			
		    sut.open();
	    	boolean result = sut.bookRide(validUser, ride, -1, 0.0);
	    	sut.close();

	    	assertFalse(result);
	
		}
		
		@Test
		// Caso 2: El usuario existe y se realiza un depósito. El saldo del usuario debe aumentar.
		public void testSuccessfulBoking() {
			
			String validUser = "validUser"; 
		
			// Mock ride object
			Ride ride = Mockito.mock(Ride.class);
			
			
			// Create real Traveler object with 150.0 balance 
			Traveler traveler = new Traveler(validUser, "admin");
			traveler.setMoney(150.0);
			
			Mockito.when(db.find(Traveler.class, validUser)).thenReturn(traveler);
			
		    sut.open();
	    	boolean result = sut.bookRide(validUser, ride, 1, 0.0);
	    	sut.close();

	    	assertFalse(result);
	    	
	        // Verificar que el dinero del usuario se ha actualizado correctamente
	        assertEquals(50.0, traveler.getMoney(), 0.01);
	        
	        // Verificar que el número de asientos se ha reducido
	        Mockito.verify(ride).setnPlaces(0);
	    	
		}

}
