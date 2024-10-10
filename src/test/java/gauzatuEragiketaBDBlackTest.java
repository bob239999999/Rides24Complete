import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.User;
import testOperations.TestDataAccess;
/*Agregando build.yml*/
public class gauzatuEragiketaBDBlackTest {

    // sut: system under test
    static DataAccess sut = new DataAccess();

    // additional operations needed to execute the test 
    static TestDataAccess testDA = new TestDataAccess();

    @Test
    /*
     Caso 1: El usuario existe, se realiza un depósito válido.
     * Cubriendo clases 1,2,3,4
    */
    
    public void testValidDeposit() {
        String username = "testUser";
        double initialAmount = 100.0;
        double depositAmount = 20.0;
        boolean userCreated = false;
        User testUser = null;

        try {
            sut.open();
            testDA.open();
            
            // Verificamos si el usuario ya existe
            testUser = sut.getUser(username);
            
            // Si el usuario no existe, lo creamos
            if (testUser == null) {
                testUser = new User(username, "usrpass", "anytype");
                testUser.setMoney(initialAmount);
                testDA.createUser(testUser);
                userCreated = true;
            }

            // Ejecutar la operación de depósito
            boolean result = sut.gauzatuEragiketa(username, depositAmount, true);
            
            // Verificar que el depósito fue exitoso
            assertTrue(result);

            // Verificar que el saldo ha aumentado
            User user = sut.getUser(username);
            assertEquals(initialAmount + depositAmount, user.getMoney(), 0.01);
            sut.close();
            testDA.close();
        } finally {
            if (userCreated) {
                testDA.open();
                testDA.eliminarUsuarioDirecto(testUser);
                testDA.close();
            }
        }
    }

    @Test
    // Caso 2: El usuario existe y se realiza un retiro válido.
    public void testValidWithdraw() {
        String username = "testUser";
        double initialAmount = 100.0;
        double withdrawAmount = 60.0;
        boolean userCreated = false;
        User testUser = null;

        try {
            sut.open();
            testDA.open();

            // Verificamos si el usuario ya existe
            testUser = sut.getUser(username);

            // Si el usuario no existe, lo creamos
            if (testUser == null) {
                testUser = new User(username, "usrpass", "anytype");
                testUser.setMoney(initialAmount);
                testDA.createUser(testUser);
                userCreated = true;
            }

            // Ejecutar la operación de retiro
            boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);

            // Verificar que el retiro fue exitoso
            assertTrue(result);

            // Verificar que el saldo ha disminuido
            User user = sut.getUser(username);
            assertEquals(initialAmount - withdrawAmount, user.getMoney(), 0.01);
            sut.close();
            testDA.close();
        } finally {
            if (userCreated) {
                testDA.open();
                testDA.eliminarUsuarioDirecto(testUser);
                testDA.close();
            }
        }
    }

    @Test
    // Caso 3: El usuario existe, pero el retiro es mayor al saldo. El saldo debe ser 0.
    public void testWithdrawMoreThanBalance() {
        String username = "testUser";
        double initialAmount = 30.0;
        double withdrawAmount = 60.0;
        boolean userCreated = false;
        User testUser = null;

        try {
            sut.open();
            testDA.open();

            // Verificamos si el usuario ya existe
            testUser = sut.getUser(username);

            // Si el usuario no existe, lo creamos
            if (testUser == null) {
                testUser = new User(username, "usrpass", "anytype");
                testUser.setMoney(initialAmount);
                testDA.createUser(testUser);
                userCreated = true;
            }

            // Ejecutar la operación de retiro
            boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);

            // Verificar que el retiro fue exitoso
            assertTrue(result);

            // Verificar que el saldo ha disminuido a 0
            User user = sut.getUser(username);
            assertEquals(0.0, user.getMoney(), 0.01);
            sut.close();
            testDA.close();
        } finally {
            if (userCreated) {
                testDA.open();
                testDA.eliminarUsuarioDirecto(testUser);
                testDA.close();
            }
        }
    }

    @Test
    // Caso 4: El nombre de usuario es null. El método debe retornar false.
    public void testNullUsername() {
    	String username = null;
        double initialAmount = 30.0;
        double withdrawAmount = 60.0;
        boolean userCreated = false;
        User testUser = null;

        
            sut.open();
            testDA.open();

            /*No vale la pena crear el usuario en la base datos
            
            // Verificamos si el usuario ya existe
            testUser = sut.getUser(username);

            // Si el usuario no existe, lo creamos
            if (testUser == null) {
                testUser = new User(username, "usrpass", "anytype");
                testUser.setMoney(initialAmount);
                testDA.createUser(testUser);
                userCreated = true;
            }*/

            // Ejecutar la operación de retiro
            boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);

            // Verificar que el retiro fue exitoso
            assertFalse(result);

            /*Tendría que regresar false ya que ni siquiera podria encontrar el username
             * en la DB
             * Si la implementación de getUser es correcta */
            sut.close();
            testDA.close();
         
        
    }

    
    @Test
    // Caso 5: El monto es negativo. El método debe retornar false directamente
    //ya que no tiene sentido que intentes depositar un valor negativo, o depositar cuando
    //quieres retirar, esto por las leyes de los signos.
    
    
    /*Defecto encontrado no devuelve lo esperado*/
    public void testNegativeAmount() {
    	
    	
            String username = "testUser";
            double initialAmount = 30.0;
            double withdrawAmount = -60.0;
            boolean userCreated = false;
            User testUser = null;

            try {
                sut.open();
                testDA.open();

                // Verificamos si el usuario ya existe
                testUser = sut.getUser(username);

                // Si el usuario no existe, lo creamos
                if (testUser == null) {
                    testUser = new User(username, "usrpass", "anytype");
                    testUser.setMoney(initialAmount);
                    testDA.createUser(testUser);
                    userCreated = true;
                }

                // Ejecutar la operación de retiro o deposito
                boolean result = sut.gauzatuEragiketa(username, withdrawAmount, true);

                // Verificar que el retiro fue exitoso
                assertFalse(result);

                /*Aqui ya no vale verificar 
                 * el valor del dinero ya que tiene que ir a false directamente/*
                 */
                sut.close();
                testDA.close();
            } finally {
                if (userCreated) {
                    testDA.open();
                    testDA.eliminarUsuarioDirecto(testUser);
                    testDA.close();
                }
            }
    }
    
    

    @Test
    // Caso 6: El usuario no existe en la base de datos. El método debe retornar false.
    public void testUserNotInDatabase() {
        String nonExistentUsername = "nonExistentUser";
        double depositAmount = 20.0;
        sut.open();

        // Realizamos la operación para un usuario que no existe en la base de datos
        boolean result = sut.gauzatuEragiketa(nonExistentUsername, depositAmount, true);

        // Verificamos que el método retorne false
        assertFalse(result);

        sut.close();
    }
    
}
