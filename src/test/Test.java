package test;

import tensor.*;

public class Test {
    public static void main(String[] args) {
        // 1. create Scalar
        Scalar scalarA = Factory.createScalar("3.5");
        // 2. create Random Scalar
        Scalar scalarB = Factory.createRandomScalar("0", "10");

        // 3. create Vector
        Vector vectorA = Factory.createVector(3, "1");
        // 4. create Random Vector
        Vector vectorB = Factory.createRandomVector(3, "1", "3");
        // 5. create Vector from Array
        String[] vectorValues = {"1", "2", "3"};
        Vector vectorC = Factory.createVectorFromArray(vectorValues);

        // 6. create Matrix
        Matrix matrixA =  Factory.createMatrix(2, 3, "1");
        // 7. create Random Matrix
        Matrix matrixB = Factory.createRandomMatrix(2, 3, "1", "3");
        // 8. create Matrix from CSV
        Matrix matrixC = Factory.createMatrixFromCsv("./src/test/matrix1.csv");
        // 9. create Matrix from 2d Array
        String[][] matrixValues = {{"1", "2"}, {"3", "4"}, {"5", "6"}};
        Matrix matrixD = Factory.createMatrixFromArray(matrixValues);
        // 10. create Identity Matrix
        Matrix matrixE = Factory.createIdentityMatrix(3);

        // 11v. get Specific Value in Vector
        System.out.println("vectorC index 0 value : " + vectorC.getVectorElement(0)); // [1, 2, 3]
        // 11m. get Specific Value in Matrix
        System.out.println("matrixD 0, 1 value : " + matrixD.getMatrixElement(0, 1)); // [[1, 2], [3, 4], [5, 6]]
        // 12. set & get Value in Scalar
        scalarA.setValue("1");
        System.out.println("scalarA value : " + scalarA.getValue());

        // 13v. get Size of Vector
        System.out.println("vectorA size : " + vectorA.getVectorSize());
        // 13m. get Size of Matrix
        System.out.println("matrixB row size : " + matrixB.getMatrixRowCount());
        System.out.println("matrixB column size : " + matrixB.getMatrixColumnCount());

        // 14s. print toString object of Scalar
        System.out.println("scalarB toString : " + scalarB);
        // 14v. print toString object of Vector
        System.out.println("vectorB toString : " + vectorB);
        // 14m. print toString object of Matrix
        System.out.println("matrixB toString : " + matrixB);

        // 15. check equals
        // TODO
        System.out.println("scalarA.equals(scalarB): " + scalarA.equals(scalarB));
        System.out.println("vectorA.equals(vectorB): " + vectorA.equals(vectorB));
        System.out.println("matrixA.equals(matrixB): " + matrixA.equals(matrixB));

        // 16. compare Scalar
        try {
            int cmp = scalarA.compareTo(scalarB);
            System.out.println("scalarA.compareTo(scalarB): " + cmp);
        } catch (Exception e) {
            System.out.println("[예외] Scalar 비교 실패: " + e.getMessage());
        }

        // 17. clone
        // TODO
        Scalar scalarClone = scalarA.clone();
        Vector vectorClone = vectorA.clone();
        Matrix matrixClone = matrixA.clone();
        System.out.println("scalarClone: " + scalarClone);
        System.out.println("vectorClone: " + vectorClone);
        System.out.println("matrixClone: " + matrixClone);

        // 18. add Scalar (non-static)
        scalarA.add(scalarB);
        System.out.println("scalarA after add: " + scalarA);

        // 19. multiply Scalar (non-static)
        scalarA.multiply(scalarB);
        System.out.println("scalarA after multiply: " + scalarA);

        // 20. add Vector (non-static)
        try {
            vectorA.add(vectorB);
            System.out.println("vectorA after add: " + vectorA);
        } catch (Exception e) {
            System.out.println("[예외] Vector 덧셈 실패: " + e.getMessage());
        }

        // 21. multiply Vector (non-static)
        vectorA.multiply(scalarA);
        System.out.println("vectorA after multiply: " + vectorA);

        // 22. add Matrix (non-static)
        try {
            matrixA.add(matrixB);
            System.out.println("matrixA after add: " + matrixA);
        } catch (Exception e) {
            System.out.println("[예외] Matrix 덧셈 실패: " + e.getMessage());
        }

        // 23. multiply Matrix (non-static)
        try {
            Matrix matrixF = Factory.createMatrix(3, 2, "1"); // 2x3 * 3x2 → OK
            matrixA.multiply(matrixF);
            System.out.println("matrixA after multiply: " + matrixA);
        } catch (Exception e) {
            System.out.println("[예외] Matrix 곱셈 실패: " + e.getMessage());
        }

        // 24. static Add Scalar
        Scalar staticScalarAdd = Tensors.add(scalarA, scalarB);
        System.out.println("Tensors.add(scalarA, scalarB): " + staticScalarAdd);

        // 25. static Multiply Scalar
        Scalar staticScalarMul = Tensors.multiply(scalarA, scalarB);
        System.out.println("Tensors.multiply(scalarA, scalarB): " + staticScalarMul);

        // 26. static Add Vector
        Vector staticVectorAdd = Tensors.add(vectorB, vectorC); // 둘 다 size 3
        System.out.println("Tensors.add(vectorB, vectorC): " + staticVectorAdd);

        // 27. static Multiply Vector
        Vector staticVectorMul = Tensors.multiply(scalarB, vectorC);
        System.out.println("Tensors.multiply(scalarB, vectorC): " + staticVectorMul);

        // 28. static Add Matrix
        try {
            Matrix staticMatrixAdd = Tensors.add(matrixA, matrixB);
            System.out.println("Tensors.add(matrixA, matrixB): \n" + staticMatrixAdd);
        } catch (Exception e) {
            System.out.println("[예외] Tensors.add(matrixA, matrixB): " + e.getMessage());
        }

        // 29. static Multiply Matrix
        try {
            Matrix matrixG = Factory.createMatrix(3, 2, "2");
            Matrix staticMatrixMul = Tensors.multiply(matrixB, matrixG); // (2x3)*(3x2)
            System.out.println("Tensors.multiply(matrixB, matrixG): \n" + staticMatrixMul);
        } catch (Exception e) {
            System.out.println("[예외] Tensors.multiply(matrixB, matrixG): " + e.getMessage());
        }

        // 30. get Column Matrix from Vector
        Matrix columnMatrix = vectorC.toColumnMatrix();
        System.out.println("vectorC.toColumnMatrix():\n" + columnMatrix);

        // 31. get Row Matrix from Vector
        Matrix rowMatrix = vectorC.toRowMatrix();
        System.out.println("vectorC.toRowMatrix():\n" + rowMatrix);

        // 32. concat Columns two Matrix
        try {
            matrixA.concatColumns(matrixB);  // 둘 다 2행이면 가능
            System.out.println("matrixA.concatColumns(matrixB):\n" + matrixA);
        } catch (Exception e) {
            System.out.println("concatColumns fail: " + e.getMessage());
        }

        // 33. concat Rows two Matrix
        try {
            matrixA.concatRows(matrixB);  // 둘 다 2행이면 가능
            System.out.println("matrixA.concatRows(matrixB):\n" + matrixA);
        } catch (Exception e) {
            System.out.println("concatColumns fail: " + e.getMessage());
        }

        // 34. extract Row in Matrix
        try {
            Vector rowVec = matrixD.extractRow(1);
            System.out.println("matrixD.extractRow(1): " + rowVec);
        } catch (Exception e) {
            System.out.println("[예외] extractRow 실패: " + e.getMessage());
        }

        // 35. extract Column in Matrix
        try {
            Vector colVec = matrixD.extractColumn(0);
            System.out.println("matrixD.extractColumn(0): " + colVec);
        } catch (Exception e) {
            System.out.println("[예외] extractColumn 실패: " + e.getMessage());
        }

        // 36. get subMatrix
        try {
            Matrix sub = matrixD.subMatrix(0, 1, 0, 1);  // 상단 왼쪽 2x2
            System.out.println("matrixD.subMatrix(0,1,0,1):\n" + sub);
        } catch (Exception e) {
            System.out.println("[예외] subMatrix 실패: " + e.getMessage());
        }

        // 37. minor
        try {
            Matrix minor = matrixE.minor(1, 1);  // 3x3 단위행렬에서 (1,1) 제거
            System.out.println("matrixE.minor(1,1):\n" + minor);
        } catch (Exception e) {
            System.out.println("[예외] minor 실패: " + e.getMessage());
        }

        // 38. transpose
        Matrix transposed = matrixD.transpose();
        System.out.println("matrixD.transpose():\n" + transposed);

        // 39. trace
        try {
            Scalar tr = matrixE.trace();
            System.out.println("matrixE.trace(): " + tr);
        } catch (Exception e) {
            System.out.println("[예외] trace 실패: " + e.getMessage());
        }

        // 40 ~ 44.
        System.out.println("matrixE.isSquare(): " + matrixE.isSquare());
        System.out.println("matrixE.isIdentityMatrix(): " + matrixE.isIdentityMatrix());
        System.out.println("matrixA.isZeroMatrix(): " + matrixA.isZeroMatrix());
        System.out.println("matrixE.isUpperTriangular(): " + matrixE.isUpperTriangular());
        System.out.println("matrixE.isLowerTriangular(): " + matrixE.isLowerTriangular());

        // 45. swap two Row
        try {
            matrixA.swapRows(0, 1);
            System.out.println("matrixA.swapRows(0,1):\n" + matrixA);
        } catch (Exception e) {
            System.out.println("[예외] swapRows 실패: " + e.getMessage());
        }

        // 46. swap two Column
        try {
            matrixD.swapColumns(0, 1);
            System.out.println("matrixD.swapColumns(0,1):\n" + matrixD);
        } catch (Exception e) {
            System.out.println("[예외] swapColumns 실패: " + e.getMessage());
        }

        // 47. scaleRow
        try {
            matrixA.scaleRow(0, Factory.createScalar("2"));
            System.out.println("matrixA.scaleRow(0, 2):\n" + matrixA);
        } catch (Exception e) {
            System.out.println("[예외] scaleRow 실패: " + e.getMessage());
        }

        // 48. scaleColumn
        try {
            matrixA.scaleColumn(0, Factory.createScalar("0.5"));
            System.out.println("matrixA.scaleColumn(0, 0.5):\n" + matrixA);
        } catch (Exception e) {
            System.out.println("[예외] scaleColumn 실패: " + e.getMessage());
        }

        // 49. addScaledRow
        try {
            matrixA.addScaledRow(1, 0, Factory.createScalar("1"));
            System.out.println("matrixA.addScaledRow(1, 0, 1):\n" + matrixA);
        } catch (Exception e) {
            System.out.println("[예외] addScaledRow 실패: " + e.getMessage());
        }

        // 50. addScaledColumn
        try {
            matrixA.addScaledColumn(1, 0, Factory.createScalar("1"));
            System.out.println("matrixA.addScaledColumn(1, 0, 1):\n" + matrixA);
        } catch (Exception e) {
            System.out.println("[예외] addScaledColumn 실패: " + e.getMessage());
        }

        // 51. toRref
        try {
            Matrix rref = matrixD.toRref();
            System.out.println("matrixD.toRref():\n" + rref);
        } catch (Exception e) {
            System.out.println("[예외] toRref 실패: " + e.getMessage());
        }

        // 52. isRref
        try {
            boolean isRref = matrixD.toRref().isRref();
            System.out.println("matrixD.toRref().isRref(): " + isRref);
        } catch (Exception e) {
            System.out.println("[예외] isRref 실패: " + e.getMessage());
        }

        // 53. determinant
        try {
            Scalar det = matrixE.determinant();
            System.out.println("matrixE.determinant(): " + det);
        } catch (Exception e) {
            System.out.println("[예외] determinant 실패: " + e.getMessage());
        }

        // 54. inverse
        try {
            Matrix inv = matrixE.inverse();
            System.out.println("matrixE.inverse():\n" + inv);
        } catch (Exception e) {
            System.out.println("[예외] inverse 실패: " + e.getMessage());
        }
    }
}
