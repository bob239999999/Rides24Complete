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
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;

public class gauzatuEragiketaMockWhiteTest {

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
	// Caso 1: El usuario no existe. El método debe retornar false.
	public void testUserDoesNotExist() {
	    // Abrimos la conexión (mock) de la base de datos
	    sut.open();
	    
	    String nonExistentUsername = "nonExistentUser";  // Nombre de usuario que no existe
	    
	    // Simulamos la creación del TypedQuery y el setParameter
	    Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(typedQuery);
	    Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString())).thenReturn(typedQuery);

	    // Simulamos que getSingleResult() lanza una NoResultException porque el usuario no existe
	    Mockito.when(typedQuery.getSingleResult()).thenThrow(new javax.persistence.NoResultException());

	    // Llamada al método bajo prueba: intentamos realizar la operación para un usuario inexistente
	    boolean result = sut.gauzatuEragiketa(nonExistentUsername, 50.0, true);

	    // Cerramos la conexión
	    sut.close();

	    // Verificamos que el método retorne false, ya que el usuario no existe
	    assertFalse(result);
	}



	@Test
	// Caso 2: El usuario existe y se realiza un depósito. El saldo del usuario debe aumentar.
	public void testDepositToExistingUser() {
	    // Abrimos la conexión (mock) de la base de datos
	    sut.open();

	    String username = "testUser";
	    double initialAmount = 100.0;
	    double depositAmount = 50.0;

	    User testUser = new User(username, "usrpass", "anytype");
	    testUser.setMoney(initialAmount);

	    // Simulamos la creación del TypedQuery y el setParameter
	    Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(typedQuery);
	    Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString())).thenReturn(typedQuery);
	    
	    // Simulamos que getSingleResult() devuelve el usuario existente
	    Mockito.when(typedQuery.getSingleResult()).thenReturn(testUser);

	    // Llamada al método bajo prueba: intentamos realizar la operación para el usuario existente
	    boolean result = sut.gauzatuEragiketa(username, depositAmount, true);
	    
	    // Cerramos la conexión
	    sut.close();

	    // Verificamos que el depósito fue exitoso
	    assertTrue(result);
	    
	    // Verificar que el saldo ha aumentado
	    assertEquals(initialAmount + depositAmount, testUser.getMoney(), 0.01);
	}

    

	@Test
	// Caso 3: El usuario existe y se realiza un retiro. El saldo del usuario debe disminuir.
	public void testWithdrawFromExistingUser() {
	    String username = "testUser";
	    double initialAmount = 100.0;
	    double withdrawAmount = 50.0;

	    User testUser = new User(username, "usrpass", "anytype");
	    testUser.setMoney(initialAmount);
	    
	    // Abrimos la conexión (mock) de la base de datos
	    sut.open();

	    // Simulamos la creación del TypedQuery y el setParameter
	    Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(typedQuery);
	    Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString())).thenReturn(typedQuery);
	    
	    // Simulamos que getSingleResult() devuelve el usuario existente
	    Mockito.when(typedQuery.getSingleResult()).thenReturn(testUser);

	    // Llamada al método bajo prueba: intentamos realizar la operación de retiro
	    boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);
	    
	    // Cerramos la conexión
	    sut.close();

	    // Verificar que el retiro fue exitoso
	    assertTrue(result);
	    
	    // Verificar que el saldo ha disminuido
	    assertEquals(initialAmount - withdrawAmount, testUser.getMoney(), 0.01);
	}

	@Test
	// Caso 4: El usuario existe, pero el retiro es mayor al saldo. El saldo debe ser 0.
	public void testWithdrawMoreThanBalance() {
	    String username = "testUser";
	    double initialAmount = 50.0;
	    double withdrawAmount = 100.0;

	    User testUser = new User(username, "usrpass", "anytype");
	    testUser.setMoney(initialAmount);
	    
	    // Abrimos la conexión (mock) de la base de datos
	    sut.open();

	    // Simulamos la creación del TypedQuery y el setParameter
	    Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class))).thenReturn(typedQuery);
	    Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString())).thenReturn(typedQuery);
	    
	    // Simulamos que getSingleResult() devuelve el usuario existente
	    Mockito.when(typedQuery.getSingleResult()).thenReturn(testUser);

	    // Llamada al método bajo prueba: intentamos realizar la operación de retiro
	    boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);
	    
	    // Cerramos la conexión
	    sut.close();

	    // Verificar que el retiro fue exitoso
	    assertTrue(result);
	    
	    // Verificar que el saldo del usuario es 0
	    assertEquals(0.0, testUser.getMoney(), 0.01);
	}
	

}
