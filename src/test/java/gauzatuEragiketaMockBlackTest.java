import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class gauzatuEragiketaMockBlackTest {/*

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
    public void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
        persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
                      .thenReturn(entityManagerFactory);

        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
        Mockito.doReturn(et).when(db).getTransaction();
        sut = new DataAccess(db);
    }

    @After
    public void tearDown() {
        persistenceMock.close();
    }

    @Test
    public void testValidDeposit() {
        // Variables del caso de prueba
        String username = "testUser";
        double initialAmount = 100.0;
        double depositAmount = 20.0;
        User testUser = new User(username, "usrpass", "anytype");
        testUser.setMoney(initialAmount);

        // Simulamos la conexión con la base de datos y los queries
        sut.open();
        Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class)))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString()))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.getSingleResult()).thenReturn(testUser);

        // Ejecutar la operación de depósito
        boolean result = sut.gauzatuEragiketa(username, depositAmount, true);

        // Verificar resultados
        assertTrue(result);
        assertEquals(initialAmount + depositAmount, testUser.getMoney(), 0.01);

        sut.close();
    }

    @Test
    public void testValidWithdraw() {
        // Variables del caso de prueba
        String username = "testUser";
        double initialAmount = 100.0;
        double withdrawAmount = 60.0;
        User testUser = new User(username, "usrpass", "anytype");
        testUser.setMoney(initialAmount);

        // Simulamos la conexión con la base de datos y los queries
        sut.open();
        Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class)))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString()))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.getSingleResult()).thenReturn(testUser);

        // Ejecutar la operación de retiro
        boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);

        // Verificar resultados
        assertTrue(result);
        assertEquals(initialAmount - withdrawAmount, testUser.getMoney(), 0.01);

        sut.close();
    }

    @Test
    public void testWithdrawMoreThanBalance() {
        // Variables del caso de prueba
        String username = "testUser";
        double initialAmount = 30.0;
        double withdrawAmount = 60.0;
        User testUser = new User(username, "usrpass", "anytype");
        testUser.setMoney(initialAmount);

        // Simulamos la conexión con la base de datos y los queries
        sut.open();
        Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class)))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString()))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.getSingleResult()).thenReturn(testUser);

        // Ejecutar la operación de retiro
        boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);

        // Verificar resultados
        assertTrue(result);
        assertEquals(0.0, testUser.getMoney(), 0.01);

        sut.close();
    }

    @Test
    public void testNullUsername() {
        // Variables del caso de prueba
        String username = null;
        double withdrawAmount = 60.0;

        // Ejecutar la operación de retiro
        sut.open();
        boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);

        // Verificar resultados
        assertFalse(result);
        sut.close();
    }

    @Test
    public void testNegativeAmount() {
        // Variables del caso de prueba
        String username = "testUser";
        double initialAmount = 30.0;
        double withdrawAmount = -60.0;
        User testUser = new User(username, "usrpass", "anytype");
        testUser.setMoney(initialAmount);

        // Simulamos la conexión con la base de datos y los queries
        sut.open();
        Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class)))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString()))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.getSingleResult()).thenReturn(testUser);

        // Ejecutar la operación de retiro o depósito
        boolean result = sut.gauzatuEragiketa(username, withdrawAmount, true);

        // Verificar resultados
        assertFalse(result);

        sut.close();
    }

    @Test
    public void testUserNotInDatabase() {
        // Variables del caso de prueba
        String nonExistentUsername = "nonExistentUser";
        double depositAmount = 20.0;

        // Simulamos la conexión con la base de datos y los queries
        sut.open();
        Mockito.when(db.createQuery(Mockito.anyString(), Mockito.eq(User.class)))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.setParameter(Mockito.eq("username"), Mockito.anyString()))
               .thenReturn(typedQuery);
        Mockito.when(typedQuery.getSingleResult()).thenThrow(new javax.persistence.NoResultException());

        // Ejecutar la operación de depósito
        boolean result = sut.gauzatuEragiketa(nonExistentUsername, depositAmount, true);

        // Verificar resultados
        assertFalse(result);

        sut.close();
    }
    */
}

