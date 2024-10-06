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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.BeforeEach;
import java.util.Date;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import testOperations.TestDataAccess;


public class BookRideBDBlackTest {
    static DataAccess sut = new DataAccess();
    // additional operations needed to execute the test 
    static TestDataAccess testDA = new TestDataAccess();
    private Traveler validTraveler;
    private Traveler invalidTraveler;
    private Ride rideWithAvailableSeats;
    private Ride rideWithNoAvailableSeats;
    private Driver driver;

    @BeforeEach
    void setUp() {
    	validTraveler = new Traveler("userName", "password");
    	validTraveler.setMoney(200.0);  // Assume this method exists for setting balance
        driver = new Driver("drivername", "AB01");
        invalidTraveler = new Traveler("userName", "password");
        
        // Initialize a ride with 1 available seat
        rideWithAvailableSeats = new Ride("City A", "City B", new Date(), 1, 100.0, driver);
        rideWithNoAvailableSeats = new Ride("City A", "City B", new Date(), 1, 100.0, driver);
    }

    @Test 
    // Valid user booking with suficient seats 
    public void testValidBooking() {
    	boolean result = sut.bookRide("validUser", rideWithAvailableSeats, 1, 0.0); 
    	assertTrue(result);
    }
	
	@Test 
    public void testInvalidUserBooking() {
    	boolean result = sut.bookRide("invalidUser", rideWithAvailableSeats, 1, 0);
    	assertFalse(result);
    }

	@Test 
    public void testInsufficientSeatsBooking() {
    	boolean result = sut.bookRide("validUser", rideWithNoAvailableSeats, 1, 0);
    	assertFalse(result);
    }
    
    @Test 
    public void testInsuficientBalanceBooking() {
    	validTraveler.setMoney(50.0);
    	boolean result = sut.bookRide("validUser",rideWithAvailableSeats, 1, 0.0);
    }
    @Test     
    public void testValidDiscountBooking() {
    	boolean result = sut.bookRide("validUser",rideWithAvailableSeats, 1, 10.0);
    	assertTrue(result);
    	assertEquals(90.0, validTraveler.getMoney(), 0.01);
    }
    @Test     
    public void testDiscountGreaterThanPrice() {
    	boolean result = sut.bookRide("validUser",rideWithAvailableSeats, 1, 200.0);
    	assertFalse(result);
    }
    @Test     
    public void testNegativeSeatsBooking() {
    	boolean result = sut.bookRide("validUser",rideWithAvailableSeats, -1, 0.0);
    	assertFalse(result);
    }
    @Test 
    public void testZeroSeatsBooking() {
    	boolean result = sut.bookRide("validUser",rideWithAvailableSeats, 0, 0.0);
    	assertFalse(result);
    }
    @Test 
    public void testValidRequestedSeatsBooking() {
    	boolean result = sut.bookRide("validUser",rideWithAvailableSeats, 2, 0.0);
    	assertFalse(result);
    }
    @Test 
    public void testBookingAllAvailableSeats() {
    	boolean result = sut.bookRide("validUser",rideWithAvailableSeats, 5, 0.0);
    	assertFalse(result);
    }
    
    @After
    public void terDown() {
    	sut.close();
    }
}

