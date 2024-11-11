package iterator;

import businessLogic.BLFacade;
import businessLogic.BLFactory;

public class MainProgram {
    public static void main(String[] args) {
        // Usar la Factory para obtener BLFacade
        boolean isLocal = true;
        BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);
        
        // Obtener el iterador
        ExtendedIterator<String> i = blFacade.getDepartingCitiesIterator();
        String c;
        
        // Imprimir ciudades de última a primera
        System.out.println("_____________________");
        System.out.println("FROM LAST TO FIRST");
        i.goLast();
        while (i.hasPrevious()) {
            c = i.previous();
            System.out.println(c);
        }
        
        // Imprimir ciudades de primera a última
        System.out.println();
        System.out.println("_____________________");
        System.out.println("FROM FIRST TO LAST");
        i.goFirst();
        while (i.hasNext()) {
            c = i.next();
            System.out.println(c);
        }
    }
}
