package src.handler;

import java.util.ArrayList;


public class ExceptionHandler {

   
    public ExceptionHandler() {
    }

    public static boolean TypeMismatchHandler(Object o1, Object o2) {
        if (o1.getClass() == o2.getClass()) {
            return true;
        } else {
            System.out.println("Error! Unexpected input type!");
            System.out.println("System is expecting " + o1.getClass() + "type");
            return false;
        }
    }

   
    public static <E> boolean OutOfBoundHandler(int index, ArrayList<E> arrayList) {
        if (index < arrayList.size() && index >= 0) {
            return true;
        } else {
            System.out.println("Error! Index out of bound!");
            System.out.println("System expected input from 0 to " + arrayList.size() + "but got " + index + "instead");
            return false;
        }
    }

}