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

import org.junit.jupiter.api.BeforeEach;
import java.util.Date;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import testOperations.TestDataAccess;


public class BookRideBDWhiteTest {
    // sut: system under test
    static DataAccess sut = new DataAccess();
    // additional operations needed to execute the test 
    static TestDataAccess testDA = new TestDataAccess();

    private Traveler traveler;
    private Ride ride;
    private Driver driver;

    @BeforeEach
    void setUp() { 
        traveler = new Traveler("validUser", "password");
        traveler.setMoney(150.0);  // Assume this method exists for setting balance
        driver = new Driver("drivername", "AB01");
        // Initialize a ride with 1 available seat
        ride = new Ride("City A", "City B", new Date(), 1, 100.0, driver);
    }

    @Test
	 // Caso 1: El usuario introducido no existe en la base de datos 
	 public void testInvalidUser() {
	     String invalidUser = "invalidUser";  
	     // Nombre de usuario que no existe
	     sut.open();
	     // Realizamos la operación de depósito para un usuario que no existe
	     boolean result = sut.bookRide(invalidUser, ride ,1, 0.0);
	     // Verificamos que el método retorne false, ya que el usuario no existe
	     assertFalse(result);
	     sut.close();
	 }
    
    @Test 
    // Caso 2 : Plazas insuficientes .
     public void testInsuficientSeats() {
	      sut.open();
	      boolean result = sut.bookRide("validUser", ride, 2, 0.0);
	      assertFalse(result);
	      sut.close();
    }
    
    @Test 
    // Caso 3 : Descuento mayor que el precio
     public void testDiscountGreaterThanPrice() {
	      sut.open();
	      boolean result = sut.bookRide("validUser", ride, 2, 200.0);
	      assertFalse(result);
	      sut.close();
    }
    
    @Test 
    public void testSuccesfulBoking() {
   	      sut.open();
	      boolean result = sut.bookRide("validUser", ride, 1 ,0.0);
	      assertTrue(result);
	      assertEquals(50.0, traveler.getMoney(),0.01);
	      assertEquals(0, ride.getnPlaces());
	      
   }
    
    
    @Test 
    // Caso 3 : Negative number of seats
     public void testDiscountLowerThanPrice() {
	      sut.open();
	      boolean result = sut.bookRide("validUser", ride, -1 ,0.0);
	      assertFalse(result);
	      sut.close();
    }

}

