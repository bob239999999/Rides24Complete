/*
 * Autor: Maximiliano Araujo Galán
 * Descripción: Implementación de casos de prueba de caja Blanca.
 * 
 * */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.User;
import testOperations.TestDataAccess;

public class gauzatuEragiketaBDWhiteTest {

    // sut: system under test
    static DataAccess sut = new DataAccess();

    // additional operations needed to execute the test 
    static TestDataAccess testDA = new TestDataAccess();

    @Test
 // Caso 1: El usuario no existe. El método debe retornar false.
 public void testUserDoesNotExist() {
     String nonExistentUsername = "nonExistentUser";  // Nombre de usuario que no existe
     sut.open();
     
     // Realizamos la operación de depósito para un usuario que no existe
     boolean result = sut.gauzatuEragiketa(nonExistentUsername, 50.0, true);
     
     // Verificamos que el método retorne false, ya que el usuario no existe
     assertFalse(result);
     
     sut.close();
 }


    @Test
 // Caso 2: El usuario existe y se realiza un depósito. El saldo del usuario debe aumentar.
 public void testDepositToExistingUser() {
     String username = "testUser";
     double initialAmount = 100.0;
     double depositAmount = 50.0;
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
             testUser.setMoney(initialAmount);  // Establecemos el saldo inicial
             testDA.createUser(testUser);  // Creamos el usuario en la base de datos
             userCreated = true;  // Indicamos que el usuario fue creado
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
         // Limpiar el estado de la base de datos
         if (userCreated) {
             testDA.open();
             testDA.eliminarUsuarioDirecto(testUser);  // Eliminamos el usuario de prueba creado
             testDA.close();
         }
     }
 }



    @Test
    // Caso 3: El usuario existe y se realiza un retiro. El saldo del usuario debe disminuir.
    public void testWithdrawFromExistingUser() {
    	String username = "testUser";
        double initialAmount = 100.0;
        double withdrawAmount = 50.0;
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
                testUser.setMoney(initialAmount);  // Establecemos el saldo inicial
                testDA.createUser(testUser);  // Creamos el usuario en la base de datos
                userCreated = true;  // Indicamos que el usuario fue creado
            }

            // Ejecutar la operación de depósito
            boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);
            
            // Verificar que el depósito fue exitoso
            assertTrue(result);

            // Verificar que el saldo ha aumentado
            User user = sut.getUser(username);
            assertEquals(initialAmount - withdrawAmount, user.getMoney(), 0.01);
            sut.close();
            testDA.close();
        } finally {
            // Limpiar el estado de la base de datos
            if (userCreated) {
                testDA.open();
                testDA.eliminarUsuarioDirecto(testUser);  // Eliminamos el usuario de prueba creado
                testDA.close();
            }
        }
    }

    @Test
    // Caso 4: El usuario existe, pero el retiro es mayor al saldo. El saldo debe ser 0.
    public void testWithdrawMoreThanBalance() {
    	String username = "testUser";
        double initialAmount = 50.0;
        double withdrawAmount = 100.0;
        boolean userCreated = false;
        User testUser = null;  

        try {
            // crear un usuario con saldo inicial
        	   sut.open();
               testDA.open();
               
               // Verificamos si el usuario ya existe
               testUser = sut.getUser(username);
               
               // Si el usuario no existe, lo creamos
               if (testUser == null) {
                   testUser = new User(username, "usrpass", "anytype");
                   testUser.setMoney(initialAmount);  // Establecemos el saldo inicial
                   testDA.createUser(testUser);  // Creamos el usuario en la base de datos
                   userCreated = true;  // Indicamos que el usuario fue creado
               }

            // Ejecutar la operación de retiro
            sut.open();
            boolean result = sut.gauzatuEragiketa(username, withdrawAmount, false);
            sut.close();

            // Verificar que el retiro fue exitoso
            assertTrue(result);

            // Verificar que el saldo del usuario es 0, ya que no puede ser negativo
            sut.open();
            User user = sut.getUser(username);
            assertEquals(0.0, user.getMoney(), 0.01);
            sut.close();
        } finally {
        	
        	if (userCreated) {
                testDA.open();
                testDA.eliminarUsuarioDirecto(testUser);  // Eliminamos el usuario de prueba creado
                testDA.close();
            }
        }
    }
    
}

