/*
* This program get three sides or angles and shows information of the triangle.
*
* @author  Jay Lee
* @version 1.0
* @since   2021-05-30
*/
// package ca.mths.assignment.assignment04.java.triangleForm;

import java.util.Scanner;


public final class TriangleForm {
    private TriangleForm() {
        // Prevent instantiation
        // Optional: throw an exception e.g. AssertionError
        // if this ever *is* called
        throw new IllegalStateException("Cannot be instantiated");
    }
    /**
    * This method ...
    * @param args
    */
    public static void main(final String[] args) {
        Scanner myObj = new Scanner(System.in);

        System.out.println("\nTriangle△\n");
        System.out.println("All sides and angles are corresponding in order.");
        System.out.println("So the first side and angle face each other, not "
                           + "attached.");
        System.out.println("If you don’t know the side or angle, just skip it "
                           + "by entering nothing,\nbut you should enter at "
                           + "least three things without only three angles.");
        try {
            double side1 = 0;
            double side2 = 0;
            double side3 = 0;
            double angle1 = 0;
            double angle2 = 0;
            double angle3 = 0;
            System.out.print("\nEnter the first side: ");
            String strSide1 = myObj.nextLine();
            if (!strSide1.isEmpty()) {
                side1 = Double.parseDouble(strSide1);
            }
            System.out.print("\nEnter the second side: ");
            String strSide2 = myObj.nextLine();
            if (!strSide2.isEmpty()) {
                side2 = Double.parseDouble(strSide2);
            }
            System.out.print("\nEnter the third side: ");
            String strSide3 = myObj.nextLine();
            if (!strSide3.isEmpty()) {
                side3 = Double.parseDouble(strSide3);
            }
            System.out.print("\nEnter the first angle(°): ");
            String strAngle1 = myObj.nextLine();
            if (!strAngle1.isEmpty()) {
                angle1 = Double.parseDouble(strAngle1);
            }
            System.out.print("\nEnter the second angle: ");
            String strAngle2 = myObj.nextLine();
            if (!strAngle2.isEmpty()) {
                angle2 = Double.parseDouble(strAngle2);
            }
            System.out.print("\nEnter the third angle: ");
            String strAngle3 = myObj.nextLine();
            if (!strAngle3.isEmpty()) {
                angle3 = Double.parseDouble(strAngle3);
            }

            Triangle myTriangle = new Triangle(side1, side2, side3,
                                               angle1, angle2, angle3);

            if (myTriangle.isTriangleValid()) {
                double area = myTriangle.getArea();
                System.out.println("\nArea: " + area + " u^2");
                System.out.println("\nPerimeter: " + myTriangle.getPerimeter());
                System.out.println("\nType: " + myTriangle.getName());
                System.out.println("\nHighest height: "
                                   + myTriangle.getHeight(area));
                System.out.println("\nRadius of the largest inscribed circle: "
                          + myTriangle.getRadiusOfLargestInscribedCircle(area));
                System.out.println("\nCircumcircle Area: "
                               + myTriangle.getCircumcircleArea(area) + " u^2");
            } else {
                System.out.println("\nInvalid triangle.");
            }
        } catch (Exception ex) {
            System.err.print("\nInvalid input.");
        }

        System.out.println("\nDone.");
    }
}
