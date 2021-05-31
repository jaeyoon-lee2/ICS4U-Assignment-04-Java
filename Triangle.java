/*
* This class get three sides and angles.
*
* @author  Jay Lee
* @version 1.0
* @since   2021-05-30
*/
// package ca.mths.assignment.assignment04.java.java.triangle;

public class Triangle {
    // Constant
    /** size of the data members. */
    private static final int HALF_DATA_SIZE = 3;
    /** sum of the data members index (0 + 1 + ... + 5). */
    private static final int SUM_OF_ANGLE_INDEX = 12;
    /** sum of the index of ASA triangle types. */
    private static final int SUM_OF_ASA_INDEX = 9;
    /** sum of the index of SAS triangle types. */
    private static final int SUM_OF_SAS_INDEX = 6;
    /** constant value to round to 2 decimal places. */
    private static final int ROUND = 100;

    /** Double array dataMembers - to contain sides and angles in radians. */
    private double[] dataMembers = new double[HALF_DATA_SIZE * 2];
    /**
    * string triangle type
    * SSS: 3 sides
    * SAS: 2 sides, 1 angle between them
    * SSA: 2 sides, 1 angle not between them
    * ASA: 1 side, 2 angles.
    */
    private String triangleType;

    /**
    * This method constructs the Triangle class.
    * @param side1 - first side of the triangle
    * @param side2 - second side of the triangle
    * @param side3 - third side of the triangle
    * @param angle1 - first angle of the triangle in degrees
    * @param angle2 - second angle of the triangle in degrees
    * @param angle3 - third angle of the triangle in degrees
    */
    public Triangle(final double side1, final double side2, final double side3,
                final double angle1, final double angle2, final double angle3) {
        this.dataMembers[0] = side1;
        this.dataMembers[1] = side2;
        this.dataMembers[2] = side3;
        this.dataMembers[HALF_DATA_SIZE] = Math.toRadians(angle1);
        this.dataMembers[HALF_DATA_SIZE + 1] = Math.toRadians(angle2);
        this.dataMembers[HALF_DATA_SIZE + 2] = Math.toRadians(angle3);
    }

    /**
    * This method check the triangle is valid and return boolean.
    * @return bool
    */
    protected boolean isTriangleValid() {
        if (dataMembers[0] != 0 || dataMembers[1] != 0 || dataMembers[2] != 0) {
            fillAllSides();
            // find the biggest side to check the triangle is valid.
            double biggestSide = dataMembers[0];
            for (int index = 1; index < HALF_DATA_SIZE; index++) {
                biggestSide = Math.max(biggestSide, dataMembers[index]);
            }
            // check if there is any sides,
            // sum of angles are pi (180°) or less(if not define),
            // and the biggest side is less than other two sides.
            if ((dataMembers[HALF_DATA_SIZE] + dataMembers[HALF_DATA_SIZE + 1]
                + dataMembers[HALF_DATA_SIZE + 2] <= Math.PI)
                && (biggestSide < getSemiperimeter())) {
                return true;
            }
        }
        return false;
    }

    /**
    * This method fills all sides of the triangle.
    */
    private void fillAllSides() {
        int[] validIndex = getValidValues();
        double side1 = dataMembers[validIndex[0]];
        double side2 = dataMembers[validIndex[1]];
        double side3;
        double angle1;
        double angle2;
        double angle3;
        if (triangleType.equals("SAS")) {
            angle3 = dataMembers[validIndex[2]];
            side3 = Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2)
                                - (2 * side1 * side2 * Math.cos(angle3)));
            this.dataMembers[validIndex[2] - HALF_DATA_SIZE] = side3;
        } else if (triangleType.equals("SSA")) {
            if (validIndex[2] - validIndex[0] == HALF_DATA_SIZE) {
                angle1 = dataMembers[validIndex[2]];
                angle2 = Math.asin(side2 * Math.sin(angle1) / side1);
            } else {
                angle2 = dataMembers[validIndex[2]];
                angle1 = Math.asin(side1 * Math.sin(angle2) / side2);
            }
            angle3 = Math.PI - angle1 - angle2;
            side3 = side1 * Math.sin(angle3) / Math.sin(angle1);
            this.dataMembers[HALF_DATA_SIZE - validIndex[0]
                             - validIndex[1]] = side3;
        } else if (triangleType.equals("ASA")) {
            side3 = dataMembers[validIndex[0]];
            int leftAngleIndex = SUM_OF_ANGLE_INDEX - validIndex[1]
                                                    - validIndex[2];
            if (validIndex[0] + validIndex[1]
                + validIndex[2] == SUM_OF_ASA_INDEX) {
                angle1 = dataMembers[validIndex[1]];
                angle2 = dataMembers[validIndex[2]];
                angle3 = Math.PI - angle1 - angle2;
            } else if (validIndex[0] + leftAngleIndex
                       + validIndex[1] == SUM_OF_ASA_INDEX) {
                angle2 = dataMembers[validIndex[1]];
                angle3 = dataMembers[validIndex[2]];
                angle1 = Math.PI - angle2 - angle3;
            } else {
                angle2 = dataMembers[validIndex[2]];
                angle3 = dataMembers[validIndex[1]];
                angle1 = Math.PI - angle2 - angle3;
            }
            side1 = side3 * Math.sin(angle1) / Math.sin(angle3);
            side2 = side3 * Math.sin(angle2) / Math.sin(angle3);
            this.dataMembers[validIndex[1] - HALF_DATA_SIZE] = side1;
            this.dataMembers[validIndex[2] - HALF_DATA_SIZE] = side2;
        }
    }

    /**
    * This method returns indexes of the valid sides or angles
    * and set the type of the triangle (SSS, SAS, SSA, ASA).
    * @return indexArray
    */
    private int[] getValidValues() {
        int sideCount = 0;
        int angleCount = 0;
        int sumOfIndex = 0;
        int counter = 0;
        int[] indexArray = new int[HALF_DATA_SIZE];

        for (int index = 0; index < dataMembers.length; index++) {
            if (dataMembers[index] != 0) {
                if (index <= 2) {
                    sideCount++;
                } else if (index > 2) {
                    angleCount++;
                }
                sumOfIndex += index;
                indexArray[counter] = index;
                counter++;
            }
        }
        if (sideCount == HALF_DATA_SIZE) {
            this.triangleType = "SSS";
        } else if (angleCount > 1) {
            this.triangleType = "ASA";
        } else if (sumOfIndex == SUM_OF_SAS_INDEX) {
            this.triangleType = "SAS";
        } else {
            this.triangleType = "SSA";
        }
        return indexArray;
    }

    /**
    * This method calculate the triangle area by using Heron's formula.
    * @return area of the triangle
    */
    public double getArea() {
        double semiperimeter = getSemiperimeter();
        double side1 = dataMembers[0];
        double side2 = dataMembers[1];
        double side3 = dataMembers[2];
        return Math.sqrt(semiperimeter * (semiperimeter - side1)
                      * (semiperimeter - side2) * (semiperimeter - side3));
    }

    /**
    * This method returns name of the triangle.
    * @return name of triangle
    */
    public String getName() {
        double side1 = Math.round(dataMembers[0] * ROUND) / ROUND;
        double side2 = Math.round(dataMembers[1] * ROUND) / ROUND;
        double side3 = Math.round(dataMembers[2] * ROUND) / ROUND;
        if (side1 == side2 && side2 == side3) {
            return "Equilateral";
        } else if (side1 == side2 || side2 == side3 || side1 == side3) {
            return "Isosceles";
        } else {
            return "Scalene";
        }
    }

    /**
    * This method returns the semiperimeter of the triangle.
    * @return semiperimeter
    */
    public double getPerimeter() {
        return dataMembers[0] + dataMembers[1] + dataMembers[2];
    }

    /**
    * This method returns the perimeter of the triangle.
    * @return perimeter
    */
    private double getSemiperimeter() {
        return getPerimeter() / 2;
    }

    /**
    * This method returns the radius of the largest inscribed circle.
    * @param triangleArea
    * @return radius of the largest inscribed circle
    */
    public double getRadiusOfLargestInscribedCircle(final double triangleArea) {
        return triangleArea / getSemiperimeter();
    }

    /**
    * This method returns circumcircle area.
    * @param triangleArea
    * @return circumcircle area
    */
    public double getCircumcircleArea(final double triangleArea) {
        double radius = dataMembers[0] * dataMembers[1] * dataMembers[2]
                            / (2 * 2 * triangleArea);
        return Math.PI * Math.pow(radius, 2);
    }

    /**
    * This method returns biggest height of the triangle.
    * @param area
    * @return height
    */
    public double getHeight(final double area) {
        double height = 0;
        for (int index = 0; index < HALF_DATA_SIZE; index++) {
            double newHeight = 2 * area / dataMembers[index];
            height = Math.max(height, newHeight);
        }
        return height;
    }
}
