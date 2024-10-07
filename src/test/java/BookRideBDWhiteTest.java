/*
 * Autor: Britel Mohammed Yassine 
 * Descripción: Implementación de casos de prueba de caja Blanca.
 * 
 * */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.util.Date;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;


public class BookRideBDWhiteTest {
    static DataAccess sut = new DataAccess();
    static TestDataAccess testDA = new TestDataAccess();

    private Traveler traveler;
    private Ride ride;
    private Driver driver;

    @BeforeEach
    void setUp() { 
    	sut.open();
        traveler = new Traveler("validUser", "password");
        traveler.setMoney(150.0); 
        driver = new Driver("drivername", "AB01");
        ride = new Ride("City A", "City B", new Date(), 1, 100.0, driver);
        sut.addTraveler("validUser", "password");
        sut.addDriver("drivername", "AB01");
        try {
            sut.createRide("City A", "City B", new Date(), 1, 100.0f, "drivername");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exceptions as needed
        }
        
        sut.close();
    }

    @AfterEach
    void tearDown() {
        sut.open();
        sut.deleteUser(traveler); 
        sut.deleteUser(driver);
        sut.cancelRide(ride);
        sut.close();
    }
    
    @Test
	 public void testInvalidUser() {
	     String invalidUser = "invalidUser";  
	     sut.open();
	     boolean result = sut.bookRide(invalidUser, ride ,1, 0.0);
	     assertFalse(result);
	     sut.close();
	 } 
    
   
    
    @Test 
     public void testInsuficientSeats() {
	      sut.open();
	      boolean result = sut.bookRide("validUser", ride, 2, 0.0);
	      assertFalse(result);
	      sut.close();
    }
    
    @Test 
    public void testSuccesfulBoking() {
   	      sut.open();
	      boolean result = sut.bookRide("validUser", ride, 1 ,0.0);
	      sut.open();
	      assertTrue(result);
	      assertEquals(0, ride.getnPlaces());
   }
    
    

    @Test 
    public void testInsuficienBalance() {
    	Ride ride2 = new Ride("City A", "City B", new Date(), 1, 200.0, driver);
    	sut.open();
    	try {
            sut.createRide("City A", "City B", new Date(), 1, 200.0f, "drivername");
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exceptions as needed
        }
    	boolean result = sut.bookRide("validUser", ride2, 1, 0.0);
    	sut.cancelRide(ride2);
    	sut.close();
    	assertFalse(result);
    }
    
    @Test 
     public void testDiscountGreaterThanPrice() {
	      sut.open();
	      boolean result = sut.bookRide("validUser", ride, 2, 200.0);
	      assertFalse(result);
	      sut.close();
    }
    
    
    @Test 
     public void testNegatifSeats() {
	      sut.open();
	      boolean result = sut.bookRide("validUser", ride, -1 ,0.0);
	      assertFalse(result);
	      sut.close();
    }


}

