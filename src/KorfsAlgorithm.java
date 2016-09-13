import java.io.*;
import java.util.*;

/**
 * Created by Nikhil Thomas on 8/23/2016.
 */
public class KorfsAlgorithm {
    static HashMap<String, Integer> edgeMap;
    static HashMap<String, Integer> cornerMap;
    public static void main(String[] args) throws IOException {
//       Tester populate = new Tester();
//       populate.init(5);
        BufferedReader edgeReader;
        BufferedWriter edgeWriter;
        FileReader fileReaderEdge = new FileReader("src/edges.txt");
        edgeReader = new BufferedReader(fileReaderEdge);
        HashMap<String, Integer> edge = new HashMap<String, Integer>();
        while (edgeReader.ready()) {
            String line = edgeReader.readLine();
            String[] split = line.split(" ");
            int dist = Integer.parseInt(split[1]);
            edge.put(split[0], dist);
        }
        edgeReader.close();

        BufferedReader cornerReader;
        BufferedWriter cornerWriter;
        FileReader fileReaderCorner = new FileReader("src/corners.txt");
        cornerReader = new BufferedReader(fileReaderCorner);
        HashMap<String, Integer> corner = new HashMap<String, Integer>();
        while (cornerReader.ready()) {
            String line = cornerReader.readLine();
            String[] split = line.split(" ");
            int dist = Integer.parseInt(split[1]);
            corner.put(split[0], dist);
        }
        cornerReader.close();
        int scramble = 3;
        for (int i = 0; i < 10; i++) {
            System.out.println("" + (i + 1));
            KorfCube scrambledKorfCube = new KorfCube();
            scrambledKorfCube.init(true);
            scrambledKorfCube.scramble(scramble);
            System.out.println("Done Scrambling");
            long start = System.currentTimeMillis();
            IDA(scrambledKorfCube, edge, corner, scramble);
            long end = System.currentTimeMillis();
            long time = (end - start);
            System.out.println(time);
            System.out.println();
        }
        FileWriter fileWriterEdge = new FileWriter("src/edges.txt");
        edgeWriter = new BufferedWriter(fileWriterEdge);
        for (Map.Entry<String, Integer> KorfCube : edge.entrySet()) {
            if (KorfCube.getValue() > 0) {
                edgeWriter.write(KorfCube.getKey() + " " + KorfCube.getValue() + " ");
                edgeWriter.newLine();
            }
        }
        edgeWriter.close();

        FileWriter fileWriterCorner = new FileWriter("src/corners.txt");
        cornerWriter = new BufferedWriter(fileWriterCorner);
        for (Map.Entry<String, Integer> KorfCube : corner.entrySet()) {
            if (KorfCube.getValue() > 0) {
                cornerWriter.write(KorfCube.getKey() + " " + KorfCube.getValue() + " ");
                cornerWriter.newLine();
            }
        }
        cornerWriter.close();
    }

    private static void IDA(KorfCube scrambledKorfCube, HashMap<String, Integer> edge, HashMap<String, Integer> corner, int scramble) {
        //call searchLayer until you find solved KorfCube while updating map
        int layer = 1;
        RubiksCubeDisplay display = new RubiksCubeDisplay();
        display.display(scrambledKorfCube, "Calculating...");
        KorfCube copy = scrambledKorfCube.clone();
        HashMap<KorfCube, Path> current = new HashMap<>();
        current.put(scrambledKorfCube, new Path(""));
        breakLoop:
        while (true) {
            current = searchLayer(current, edge, corner, layer);
            if(current.size() == 1) {
                break;
            }
            layer++;
        }
        if(layer > scramble) {
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        }
        String path = ((Path) current.values().toArray()[0]).path;
        path = cleanup(path);
        display.display(scrambledKorfCube, "Calculating...");
        for (int i = 0; i < path.length() / 2; i++) {
            int amount = Integer.parseInt("" + path.charAt(((i * 2) + 1)));
            switch (path.charAt(i * 2)) {
                case 'R':
                    scrambledKorfCube.move(Face.R, amount, true, true);
                    break;
                case 'B':
                    scrambledKorfCube.move(Face.B, amount, true, true);
                    break;
                case 'U':
                    scrambledKorfCube.move(Face.U, amount, true, true);
                    break;
                case 'L':
                    scrambledKorfCube.move(Face.L, amount, true, true);
                    break;
                case 'F':
                    scrambledKorfCube.move(Face.F, amount, true, true);
                    break;
                case 'D':
                    scrambledKorfCube.move(Face.D, amount, true, true);
                    break;
            }
        }
        System.out.println();
    }

    private static String cleanup(String path) {
        for (int i = 0; i < (path.length() / 2) - 1; i++) {
            int amount1 = Integer.parseInt("" + (path.charAt((i * 2) + 1)));
            char face1 = path.charAt((i * 2));
            int amount2 = Integer.parseInt("" + (path.charAt((i * 2) + 3)));
            char face2 = path.charAt((i * 2) + 2);
            if (face1 == face2) {
                path = path.replaceAll("" + face1 + amount1 + face2 + amount2, ((((amount1 + amount2) % 4) != 0) ? ("" + face2 + ((amount1 + amount2) % 4)) : ""));
                return cleanup(path);
            }
        }
        return path;
    }

    private static HashMap<KorfCube, Path> searchLayer(HashMap<KorfCube, Path> currentStates, HashMap<String, Integer> edge, HashMap<String, Integer> corner, int iter) {
        HashMap<KorfCube, Path> result = new HashMap<>();
        List<KorfCube> sortedResult = new ArrayList<>();
        for (KorfCube KorfCube : currentStates.keySet()) {
            sortedResult.add(KorfCube);
        }
        cornerMap = corner;
        edgeMap = edge;
        Collections.sort(sortedResult, (o1, o2) -> {
            int current = dist(o1);
            int pattern = dist(o2);
            if (current > pattern) {
                return 1;
            } else if (current == pattern) {
                return 0;
            } else {
                return -1;
            }
        });
        corner = cornerMap;
        edge = edgeMap;
        if(sortedResult.size() > 324) {
            sortedResult = sortedResult.subList(0, (sortedResult.size() / 2));
        }
        for (KorfCube KorfCube : sortedResult) {
            String input = currentStates.get(KorfCube).path;
            String lastMove = "";
            if(input.length() >= 2) {
                lastMove = "" + input.charAt(input.length() - 2);
            }
            HashMap<KorfCube, Path> states = new HashMap<>();
            if (!lastMove.equals("R")) {
                KorfCube R1 = KorfCube.clone();
                R1.move(Face.R, 1, true, false);
                states.put(R1, new Path(input + "R1"));
                KorfCube R2 = KorfCube.clone();
                R2.move(Face.R, 2, true, false);
                states.put(R2, new Path(input + "R2"));
                KorfCube R3 = KorfCube.clone();
                R3.move(Face.R, 3, true, false);
                states.put(R3, new Path(input + "R3"));
            }

            if (!lastMove.equals("B")) {
                KorfCube B1 = KorfCube.clone();
                B1.move(Face.B, 1, true, false);
                states.put(B1, new Path(input + "B1"));
                KorfCube B2 = KorfCube.clone();
                B2.move(Face.B, 2, true, false);
                states.put(B2, new Path(input + "B2"));
                KorfCube B3 = KorfCube.clone();
                B3.move(Face.B, 3, true, false);
                states.put(B3, new Path(input + "B3"));
            }

            if (!lastMove.equals("U")) {
                KorfCube U1 = KorfCube.clone();
                U1.move(Face.U, 1, true, false);
                states.put(U1, new Path(input + "U1"));
                KorfCube U2 = KorfCube.clone();
                U2.move(Face.U, 2, true, false);
                states.put(U2, new Path(input + "U2"));
                KorfCube U3 = KorfCube.clone();
                U3.move(Face.U, 3, true, false);
                states.put(U3, new Path(input + "U3"));
            }

            if (!lastMove.equals("L")) {
                KorfCube L1 = KorfCube.clone();
                L1.move(Face.L, 1, true, false);
                states.put(L1, new Path(input + "L1"));
                KorfCube L2 = KorfCube.clone();
                L2.move(Face.L, 2, true, false);
                states.put(L2, new Path(input + "L2"));
                KorfCube L3 = KorfCube.clone();
                L3.move(Face.L, 3, true, false);
                states.put(L3, new Path(input + "L3"));
            }

            if (!lastMove.equals("F")) {
                KorfCube F1 = KorfCube.clone();
                F1.move(Face.F, 1, true, false);
                states.put(F1, new Path(input + "F1"));
                KorfCube F2 = KorfCube.clone();
                F2.move(Face.F, 2, true, false);
                states.put(F2, new Path(input + "F2"));
                KorfCube F3 = KorfCube.clone();
                F3.move(Face.F, 3, true, false);
                states.put(F3, new Path(input + "F3"));
            }

            if(!lastMove.equals("D")) {
                KorfCube D1 = KorfCube.clone();
                D1.move(Face.D, 1, true, false);
                states.put(D1, new Path(input + "D1"));
                KorfCube D2 = KorfCube.clone();
                D2.move(Face.D, 2, true, false);
                states.put(D2, new Path(input + "D2"));
                KorfCube D3 = KorfCube.clone();
                D3.move(Face.D, 3, true, false);
                states.put(D3, new Path(input + "D3"));
            }
            for (KorfCube keyKorfCube : states.keySet()) {
                if (keyKorfCube.CubeToString(true, true).equals("11c121e51c41e122e41c122e81c11e92e31e112e21c122e61c21e102e31c122e71c")) {
                    HashMap<KorfCube, Path> solved = new HashMap<>();
                    solved.put(keyKorfCube, states.get(keyKorfCube));
                    return solved;
                }
            }
            result.putAll(states);
        }
        return result;
    }

    public static int dist(KorfCube currentKorfCube) {
        int cornerVal = 0;
        int edgeVal = 0;
        if(edgeMap.containsKey(currentKorfCube.CubeToString(true, false)) && cornerMap.containsKey(currentKorfCube.CubeToString(false, true))) {
            cornerVal = cornerMap.get(currentKorfCube.CubeToString(false, true));
            edgeVal = edgeMap.get(currentKorfCube.CubeToString(true, false));
        } else {
            KorfCube patternKorfCube = new KorfCube();
            patternKorfCube.init(false);
            cornerVal = manhattan(currentKorfCube.findCorner(CubeColor.white, CubeColor.red, CubeColor.blue), patternKorfCube.findCorner(CubeColor.white, CubeColor.red, CubeColor.blue));
            cornerVal += manhattan(currentKorfCube.findCorner(CubeColor.white, CubeColor.blue, CubeColor.orange), patternKorfCube.findCorner(CubeColor.white, CubeColor.blue, CubeColor.orange));
            cornerVal += manhattan(currentKorfCube.findCorner(CubeColor.white, CubeColor.orange, CubeColor.green), patternKorfCube.findCorner(CubeColor.white, CubeColor.orange, CubeColor.green));
            cornerVal += manhattan(currentKorfCube.findCorner(CubeColor.white, CubeColor.green, CubeColor.red), patternKorfCube.findCorner(CubeColor.white, CubeColor.green, CubeColor.red));
            cornerVal += manhattan(currentKorfCube.findCorner(CubeColor.yellow, CubeColor.red, CubeColor.blue), patternKorfCube.findCorner(CubeColor.yellow, CubeColor.red, CubeColor.blue));
            cornerVal += manhattan(currentKorfCube.findCorner(CubeColor.yellow, CubeColor.blue, CubeColor.orange), patternKorfCube.findCorner(CubeColor.yellow, CubeColor.blue, CubeColor.orange));
            cornerVal += manhattan(currentKorfCube.findCorner(CubeColor.yellow, CubeColor.orange, CubeColor.green), patternKorfCube.findCorner(CubeColor.yellow, CubeColor.orange, CubeColor.green));
            cornerVal += manhattan(currentKorfCube.findCorner(CubeColor.yellow, CubeColor.green, CubeColor.red), patternKorfCube.findCorner(CubeColor.yellow, CubeColor.green, CubeColor.red));
            edgeVal = manhattan(currentKorfCube.findEdge(CubeColor.red, CubeColor.white), patternKorfCube.findEdge(CubeColor.red, CubeColor.white));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.green, CubeColor.white), patternKorfCube.findEdge(CubeColor.green, CubeColor.white));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.orange, CubeColor.white), patternKorfCube.findEdge(CubeColor.orange, CubeColor.white));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.blue, CubeColor.red), patternKorfCube.findEdge(CubeColor.blue, CubeColor.red));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.red, CubeColor.green), patternKorfCube.findEdge(CubeColor.red, CubeColor.green));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.green, CubeColor.orange), patternKorfCube.findEdge(CubeColor.green, CubeColor.orange));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.orange, CubeColor.blue), patternKorfCube.findEdge(CubeColor.orange, CubeColor.blue));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.blue, CubeColor.yellow), patternKorfCube.findEdge(CubeColor.blue, CubeColor.white));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.red, CubeColor.yellow), patternKorfCube.findEdge(CubeColor.red, CubeColor.yellow));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.green, CubeColor.yellow), patternKorfCube.findEdge(CubeColor.green, CubeColor.yellow));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.orange, CubeColor.yellow), patternKorfCube.findEdge(CubeColor.orange, CubeColor.yellow));
            edgeVal += manhattan(currentKorfCube.findEdge(CubeColor.blue, CubeColor.yellow), patternKorfCube.findEdge(CubeColor.blue, CubeColor.yellow));
            edgeMap.put(currentKorfCube.CubeToString(true, false), edgeVal);
            cornerMap.put(currentKorfCube.CubeToString(false, true), cornerVal);
        }
        return Math.max(cornerVal / 4, edgeVal / 4);
    }

    private static int manhattan(int[] currentKorfCubeCorner, int[] patternKorfCubeCorner) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            result += Math.abs(currentKorfCubeCorner[i] - patternKorfCubeCorner[i]);
        }
        return result;
    }
}

class KorfCube extends Cube {
    RubiksCubeDisplay display;
    String status = "";
    boolean debug = false;
    int moveCount = 0;
    CubePiece[][][] cubePieces = new CubePiece[3][3][3];
    public void init(boolean print) {
        debug = print;
        cubePieces[0][0][0] = new CornerPiece(CubeColor.white, CubeColor.red, CubeColor.blue);
        cubePieces[2][0][0] = new CornerPiece(CubeColor.white, CubeColor.green, CubeColor.red);
        cubePieces[2][2][0] = new CornerPiece(CubeColor.white, CubeColor.orange, CubeColor.green);
        cubePieces[0][2][0] = new CornerPiece(CubeColor.white, CubeColor.blue, CubeColor.orange);
        cubePieces[0][0][2] = new CornerPiece(CubeColor.yellow, CubeColor.red, CubeColor.blue);
        cubePieces[2][0][2] = new CornerPiece(CubeColor.yellow, CubeColor.green, CubeColor.red);
        cubePieces[2][2][2] = new CornerPiece(CubeColor.yellow, CubeColor.orange, CubeColor.green);
        cubePieces[0][2][2] = new CornerPiece(CubeColor.yellow, CubeColor.blue, CubeColor.orange);
        cubePieces[1][0][0] = new EdgePiece(CubeColor.red, CubeColor.white);
        cubePieces[2][1][0] = new EdgePiece(CubeColor.green, CubeColor.white);
        cubePieces[1][2][0] = new EdgePiece(CubeColor.orange, CubeColor.white);
        cubePieces[0][1][0] = new EdgePiece(CubeColor.blue, CubeColor.white);
        cubePieces[0][0][1] = new EdgePiece(CubeColor.blue, CubeColor.red);
        cubePieces[2][0][1] = new EdgePiece(CubeColor.red, CubeColor.green);
        cubePieces[2][2][1] = new EdgePiece(CubeColor.green, CubeColor.orange);
        cubePieces[0][2][1] = new EdgePiece(CubeColor.orange, CubeColor.blue);
        cubePieces[1][0][2] = new EdgePiece(CubeColor.yellow, CubeColor.red);
        cubePieces[2][1][2] = new EdgePiece(CubeColor.yellow, CubeColor.green);
        cubePieces[1][2][2] = new EdgePiece(CubeColor.yellow, CubeColor.orange);
        cubePieces[0][1][2] = new EdgePiece(CubeColor.yellow, CubeColor.blue);
        cubePieces[1][1][0] = new CenterPiece(CubeColor.white);
        cubePieces[1][0][1] = new CenterPiece(CubeColor.red);
        cubePieces[2][1][1] = new CenterPiece(CubeColor.green);
        cubePieces[1][2][1] = new CenterPiece(CubeColor.orange);
        cubePieces[0][1][1] = new CenterPiece(CubeColor.blue);
        cubePieces[1][1][2] = new CenterPiece(CubeColor.yellow);
        if(print) {
            display = new RubiksCubeDisplay();
            display.display(this, "");
        }
    }

    @Override
    public KorfCube clone() {
        KorfCube newCube = new KorfCube();
        newCube.init(false);
        for (int x= 0; x < 3; x++) {
            for (int y= 0; y < 3; y++) {
                for (int z= 0; z < 3; z++) {
                    if(!(x == 1 && y == 1 && z ==1) ) {
                        if(cubePieces[x][y][z].getClass().toString().equals("class CornerPiece")) {
                            newCube.cubePieces[x][y][z] = ((CornerPiece) cubePieces[x][y][z]).clone();
                        } else if(cubePieces[x][y][z].getClass().toString().equals("class EdgePiece")) {
                            newCube.cubePieces[x][y][z] = ((EdgePiece) cubePieces[x][y][z]).clone();
                        } else {
                            newCube.cubePieces[x][y][z] = ((CenterPiece) cubePieces[x][y][z]).clone();
                        }
                    }
                }
            }
        }
        return newCube;
    }

    public void scramble(int moves) {
        status = "Scrambling";
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Random generator = new Random();
        Face lastMoved = Face.F;
        int lastFace = 0;
        int lastAmount = 0;
        for (int i = 0; i < moves; i++) {
            int face = generator.nextInt(6) + 1;
            int amount = generator.nextInt(3) + 1;
            while(face == lastFace || (7 - face) == lastFace) {
                face = generator.nextInt(6) + 1;
            }
            lastFace = face;
            lastAmount = amount;
            switch (face) {
                case 1:
                    lastMoved = Face.R;
                    move(Face.R, amount, true, true);
                    break;
                case 6:
                    lastMoved = Face.L;
                    move(Face.L, amount, true, true);
                    break;
                case 3:
                    lastMoved = Face.U;
                    move(Face.U, amount, true, true);
                    break;
                case 4:
                    lastMoved = Face.D;
                    move(Face.D, amount, true, true);
                    break;
                case 5:
                    lastMoved = Face.F;
                    move(Face.F, amount, true, true);
                    break;
                case 2:
                    lastMoved = Face.B;
                    move(Face.B, amount, true, true);
                    break;
            }
        }
        status = "Done scrambling";
        display.display(this, lastMoved.toString() + (lastAmount == 3 ? "'" : lastAmount == 2 ? "2"  : ""));
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        moveCount -= moves;
    }
    public void move(Face face, int amount, boolean scramble, boolean print) {
        moveCount++;
        if(print) {
            System.out.println(face.toString() + (amount == 3 ? "'" : amount == 2 ? "2" : ""));
        }
        switch (face) {
            case U:
                for (int i = 0; i < amount; i++) {
                    int z = 2;
                    CubePiece cornerTemp = cubePieces[0][0][z];
                    CubePiece edgeTemp = cubePieces[1][0][z];
                    cubePieces[0][0][z] = cubePieces[2][0][z];
                    cubePieces[2][0][z] = cubePieces[2][2][z];
                    cubePieces[2][2][z] = cubePieces[0][2][z];
                    cubePieces[0][2][z] = cornerTemp;
                    cubePieces[1][0][z] = cubePieces[2][1][z];
                    cubePieces[2][1][z] = cubePieces[1][2][z];
                    cubePieces[1][2][z] = cubePieces[0][1][z];
                    cubePieces[0][1][z] = edgeTemp;
                }
                break;
            case D:
                for (int i = 0; i < amount; i++) {
                    int z = 0;
                    CubePiece cornerTemp = cubePieces[0][0][z];
                    CubePiece edgeTemp = cubePieces[1][0][z];
                    cubePieces[0][0][z] = cubePieces[0][2][z];
                    cubePieces[0][2][z] = cubePieces[2][2][z];
                    cubePieces[2][2][z] = cubePieces[2][0][z];
                    cubePieces[2][0][z] = cornerTemp;
                    cubePieces[1][0][z] = cubePieces[0][1][z];
                    cubePieces[0][1][z] = cubePieces[1][2][z];
                    cubePieces[1][2][z] = cubePieces[2][1][z];
                    cubePieces[2][1][z] = edgeTemp;
                }
                break;
            case R:
                for (int i = 0; i < amount; i++) {
                    CubePiece cornerTemp = cubePieces[2][0][0];
                    CubePiece edgeTemp = cubePieces[2][1][0];
                    cubePieces[2][0][0] = swapCorner(cubePieces[2][2][0], 2, 3, 1);
                    cubePieces[2][2][0] = swapCorner(cubePieces[2][2][2], 2, 1, 3);
                    cubePieces[2][2][2] = swapCorner(cubePieces[2][0][2], 3, 1, 2);
                    cubePieces[2][0][2] = swapCorner(cornerTemp, 3, 2, 1);
                    cubePieces[2][1][0] = cubePieces[2][2][1];
                    cubePieces[2][2][1] = swapEdge(cubePieces[2][1][2], 2, 1);
                    cubePieces[2][1][2] = cubePieces[2][0][1];
                    cubePieces[2][0][1] = swapEdge(edgeTemp, 2, 1);
                }
                break;
            case L:
                for (int i = 0; i < amount; i++) {
                    CubePiece cornerTemp = cubePieces[0][0][0];
                    CubePiece edgeTemp = cubePieces[0][1][0];
                    cubePieces[0][0][0] = swapCorner(cubePieces[0][0][2], 2, 1, 3);
                    cubePieces[0][0][2] = swapCorner(cubePieces[0][2][2], 3, 1, 2);
                    cubePieces[0][2][2] = swapCorner(cubePieces[0][2][0], 3, 2, 1);
                    cubePieces[0][2][0] = swapCorner(cornerTemp, 2, 3, 1);
                    cubePieces[0][1][0] = cubePieces[0][0][1];
                    cubePieces[0][0][1] = swapEdge(cubePieces[0][1][2], 2, 1);
                    cubePieces[0][1][2] = cubePieces[0][2][1];
                    cubePieces[0][2][1] = swapEdge(edgeTemp, 2, 1);
                }
                break;

            case F:
                for (int i = 0; i < amount; i++) {
                    CubePiece cornerTemp = cubePieces[0][0][0];
                    CubePiece edgeTemp = cubePieces[1][0][0];
                    cubePieces[0][0][0] = swapCorner(cubePieces[2][0][0], 2, 3, 1);
                    cubePieces[2][0][0] = swapCorner(cubePieces[2][0][2], 2, 1, 3);
                    cubePieces[2][0][2] = swapCorner(cubePieces[0][0][2], 3, 1, 2);
                    cubePieces[0][0][2] = swapCorner(cornerTemp, 3, 2, 1);
                    cubePieces[1][0][0] = cubePieces[2][0][1];
                    cubePieces[2][0][1] = swapEdge(cubePieces[1][0][2], 2, 1);
                    cubePieces[1][0][2] = cubePieces[0][0][1];
                    cubePieces[0][0][1] = swapEdge(edgeTemp, 2, 1);
                }
                break;
            case B:
                for (int i = 0; i < amount; i++) {
                    CubePiece cornerTemp = cubePieces[0][2][0];
                    CubePiece edgeTemp = cubePieces[1][2][0];
                    cubePieces[0][2][0] = swapCorner(cubePieces[0][2][2], 2, 1, 3);
                    cubePieces[0][2][2] = swapCorner(cubePieces[2][2][2], 3, 1, 2);
                    cubePieces[2][2][2] = swapCorner(cubePieces[2][2][0], 3, 2, 1);
                    cubePieces[2][2][0] = swapCorner(cornerTemp, 2, 3, 1);
                    cubePieces[1][2][0] = cubePieces[0][2][1];
                    cubePieces[0][2][1] = swapEdge(cubePieces[1][2][2], 2, 1);
                    cubePieces[1][2][2] = cubePieces[2][2][1];
                    cubePieces[2][2][1] = swapEdge(edgeTemp, 2, 1);
                }
                break;
            case E:
                for (int i = 0; i < amount; i++) {
                    CubePiece centerTemp = cubePieces[1][0][1];
                    CubePiece edgeTemp = cubePieces[0][0][1];
                    cubePieces[1][0][1] = cubePieces[2][1][1];
                    cubePieces[2][1][1] = cubePieces[1][2][1];
                    cubePieces[1][2][1] = cubePieces[0][1][1];
                    cubePieces[0][1][1] = centerTemp;
                    cubePieces[0][0][1] = cubePieces[2][0][1];
                    cubePieces[2][0][1] = cubePieces[2][2][1];
                    cubePieces[2][2][1] = cubePieces[0][2][1];
                    cubePieces[0][2][1] = edgeTemp;
                }
                break;
            case M:
                for (int i = 0; i < amount; i++) {
                    CubePiece centerTemp = cubePieces[1][0][1];
                    CubePiece edgeTemp = cubePieces[1][0][0];
                    cubePieces[1][0][1] = cubePieces[1][1][0];
                    cubePieces[1][1][0] = cubePieces[1][2][1];
                    cubePieces[1][2][1] = cubePieces[1][1][2];
                    cubePieces[1][1][2] = centerTemp;
                    cubePieces[1][0][0] = cubePieces[1][2][0];
                    cubePieces[1][2][0] = cubePieces[1][2][2];
                    cubePieces[1][2][2] = cubePieces[1][0][2];
                    cubePieces[1][0][2] = edgeTemp;
                }
                break;
            case d:
                for (int i = 0; i < amount; i++) {
                    CubePiece centerTemp = cubePieces[1][0][1];
                    CubePiece edgeTemp = cubePieces[1][0][0];
                    cubePieces[1][0][1] = cubePieces[1][1][0];
                    cubePieces[1][1][0] = cubePieces[1][2][1];
                    cubePieces[1][2][1] = cubePieces[1][1][2];
                    cubePieces[1][1][2] = centerTemp;
                    cubePieces[1][0][0] = cubePieces[1][2][0];
                    cubePieces[1][2][0] = cubePieces[1][2][2];
                    cubePieces[1][2][2] = cubePieces[1][0][2];
                    cubePieces[1][0][2] = edgeTemp;
                    CubePiece cornerTemp = cubePieces[2][0][0];
                    edgeTemp = cubePieces[2][1][0];
                    cubePieces[2][0][0] = swapCorner(cubePieces[2][2][0], 2, 3, 1);
                    cubePieces[2][2][0] = swapCorner(cubePieces[2][2][2], 2, 1, 3);
                    cubePieces[2][2][2] = swapCorner(cubePieces[2][0][2], 3, 1, 2);
                    cubePieces[2][0][2] = swapCorner(cornerTemp, 3, 2, 1);
                    cubePieces[2][1][0] = cubePieces[2][2][1];
                    cubePieces[2][2][1] = swapEdge(cubePieces[2][1][2], 2, 1);
                    cubePieces[2][1][2] = cubePieces[2][0][1];
                    cubePieces[2][0][1] = swapEdge(edgeTemp, 2, 1);
                }
                break;
        }
        if(print) {
            display.display(this, face.toString() + (amount == 3 ? "'" : amount == 2 ? "2"  : ""));
//            if (!scramble) {
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                try {
//                    Thread.sleep(150);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    public void print() {
        if(!debug) {
            return;
        }
        System.out.println("");
        System.out.println("         " +  getCorner(0, 0, 2, 2) + "  " +  getEdge(1, 0, 2, 2) + "  " +  getCorner(2, 0, 2, 3) + "  ");
        System.out.println("         " +  getEdge(0, 0, 1, 2) + "  " +  getCenter(1, 0, 1) + "  " +  getEdge(2, 0, 1, 1) + "  ");
        System.out.println("         " +  getCorner(0, 0, 0, 2) + "  " +  getEdge(1, 0, 0, 1) + "  " +  getCorner(2, 0, 0, 3) + "  ");
        System.out.println(getCorner(0, 0, 2, 3) + "  " +  getEdge(0, 0, 1, 1) + "  " +  getCorner(0, 0, 0, 3) + "  " +  getCorner(0, 0, 0, 1) + "  " +  getEdge(1, 0, 0, 2) + "  " +  getCorner(2, 0, 0, 1) + "  "+  getCorner(2, 0, 0, 2) + "  "+  getEdge(2, 0, 1, 2) + "  " +  getCorner(2, 0, 2, 2) + "  " +  getCorner(2, 0, 2, 1) + "  "+  getEdge(1, 0, 2, 1) + "  " +  getCorner(0, 0, 2, 1) + "  ");
        System.out.println(getEdge(0, 1, 2, 2) + "  " +  getCenter(0, 1, 1) + "  " +  getEdge(0, 1, 0, 1) + "  " +  getEdge(0, 1, 0, 2) + "  " +  getCenter(1, 1, 0) + "  " +  getEdge(2, 1, 0, 2) + "  "+  getEdge(2, 1, 0, 1) + "  "+  getCenter(2, 1, 1) + "  " +  getEdge(2, 1, 2, 2) + "  " +  getEdge(2, 1, 2, 1) + "  "+  getCenter(1, 1, 2) + "  " +  getEdge(0, 1, 2, 1) + "  ");
        System.out.println(getCorner(0, 2, 2, 2) + "  " +  getEdge(0, 2, 1, 2) + "  " +  getCorner(0, 2, 0, 2) + "  " +  getCorner(0, 2, 0, 1) + "  " +  getEdge(1, 2, 0, 2) + "  " +  getCorner(2, 2, 0, 1) + "  "+  getCorner(2, 2, 0, 3) + "  "+  getEdge(2, 2, 1, 1) + "  " +  getCorner(2, 2, 2, 3) + "  " +  getCorner(2, 2, 2, 1) + "  "+  getEdge(1, 2, 2, 1) + "  " +  getCorner(0, 2, 2, 1) + "  ");
        System.out.println("         " +  getCorner(0, 2, 0, 3) + "  " +  getEdge(1, 2, 0, 1) + "  " +  getCorner(2, 2, 0, 2) + "  ");
        System.out.println("         " +  getEdge(0, 2, 1, 1) + "  " +  getCenter(1, 2, 1) + "  " +  getEdge(2, 2, 1, 2) + "  ");
        System.out.println("         " +  getCorner(0, 2, 2, 3) + "  " +  getEdge(1, 2, 2, 2) + "  " +  getCorner(2, 2, 2, 2) + "  ");
        System.out.println("");
    }

    public char getCorner(int x, int y, int z, int location) {
        CornerPiece cornerPiece = (CornerPiece) cubePieces[x][y][z];
        return (location == 1 ? cornerPiece.topcubeColor : location == 2 ? cornerPiece.rightcubeColor : cornerPiece.leftcubeColor).toString().charAt(0);
    }

    public char getEdge(int x, int y, int z, int location) {
        EdgePiece cornerPiece = (EdgePiece) cubePieces[x][y][z];
        return (location == 1 ? cornerPiece.topcubeColor : cornerPiece.bottomcubeColor).toString().charAt(0);
    }

    public char getCenter(int x, int y, int z) {
        CenterPiece cornerPiece = (CenterPiece) cubePieces[x][y][z];
        return cornerPiece.cubeColor.toString().charAt(0);
    }

    public CubePiece swapCorner(CubePiece piece, int top, int right, int left) {
        CornerPiece corner = (CornerPiece) piece;
        CubeColor topcubeColor = top == 1 ? corner.topcubeColor : top == 2 ? corner.rightcubeColor : corner.leftcubeColor;
        CubeColor rightcubeColor = right == 1 ? corner.topcubeColor : right == 2 ? corner.rightcubeColor : corner.leftcubeColor;
        CubeColor leftcubeColor = left == 1 ? corner.topcubeColor : left == 2 ? corner.rightcubeColor : corner.leftcubeColor;
        return new CornerPiece(topcubeColor, rightcubeColor, leftcubeColor);
    }

    public CubePiece swapEdge(CubePiece piece, int top, int bottom) {
        EdgePiece edge = (EdgePiece) piece;
        CubeColor topcubeColor = top == 1 ? edge.topcubeColor : edge.bottomcubeColor;
        CubeColor bottomcubeColor = bottom == 1 ? edge.topcubeColor : edge.bottomcubeColor;
        return new EdgePiece(topcubeColor, bottomcubeColor);
    }
    public int[] findEdge(CubeColor topcubeColor, CubeColor bottomcubeColor) {
        EdgePiece edge = (EdgePiece) cubePieces[1][0][0];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{1, 0, 0};
        }
        edge = (EdgePiece) cubePieces[2][1][0];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{2, 1, 0};
        }
        edge = (EdgePiece) cubePieces[1][2][0];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{1, 2, 0};
        }
        edge = (EdgePiece) cubePieces[0][1][0];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{0, 1, 0};
        }
        edge = (EdgePiece) cubePieces[0][0][1];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{0, 0, 1};
        }
        edge = (EdgePiece) cubePieces[2][0][1];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{2, 0, 1};
        }
        edge = (EdgePiece) cubePieces[2][2][1];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{2, 2, 1};
        }
        edge = (EdgePiece) cubePieces[0][2][1];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{0, 2, 1};
        }
        edge = (EdgePiece) cubePieces[1][0][2];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{1, 0, 2};
        }
        edge = (EdgePiece) cubePieces[2][1][2];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{2, 1, 2};
        }
        edge = (EdgePiece) cubePieces[1][2][2];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{1, 2, 2};
        }
        edge = (EdgePiece) cubePieces[0][1][2];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{0, 1, 2};
        }
        return new int[]{0, 0, 0, 0};
    }
    public int[] findCorner(CubeColor topcubeColor, CubeColor rightcubeColor, CubeColor leftcubeColor) {
        CornerPiece that = new CornerPiece(topcubeColor, rightcubeColor, leftcubeColor);
        for (int x = 0; x < 3; x+=2) {
            for (int y = 0; y < 3; y+=2) {
                for (int z = 0; z < 3; z+=2) {
                    if (((CornerPiece)cubePieces[x][y][z]).equalsIgnoreDirection(that)) {
                        return new int[] {x, y, z};
                    }
                }
            }
        }
        throw new ArrayIndexOutOfBoundsException("No");
    }

    public void mainAlg() {
        move(Face.R, 1, false, false);
        move(Face.U, 1, false, false);
        move(Face.R, 3, false, false);
        move(Face.U, 3, false, false);
    }

    public void FRU(int n) {
        move(Face.F, 1, false, false);
        for (int i = 0; i < n; i++) {
            mainAlg();
        }
        move(Face.F, 3, false, false);
    }

    public void swapAlg(int i) {
        for (int j = 0; j < i; j++) {
            move(Face.L, 3, false, false);
            move(Face.U, 1, false, false);
            move(Face.R, 1, false, false);
            move(Face.U, 3, false, false);
            move(Face.L, 1, false, false);
            move(Face.U, 1, false, false);
            move(Face.R, 3, false, false);
            move(Face.U, 3, false, false);
        }
    }

    public void orientAlg() {
        move(Face.R, 3, false, false);
        move(Face.D, 3, false, false);
        move(Face.R, 1, false, false);
        move(Face.D, 1, false, false);
    }

    public String CornertoNumber(int x, int y, int z) {
        CornerPiece cubie = ((CornerPiece) cubePieces[x][y][z]);
        if(cubie.equalsIgnoreDirection(new CornerPiece(CubeColor.white, CubeColor.red, CubeColor.blue))) {
            return "1" +  (cubie.topcubeColor == CubeColor.white ? 1 : cubie.topcubeColor == CubeColor.red ? 2 : 3) + "c";
        } else if(cubie.equalsIgnoreDirection(new CornerPiece(CubeColor.white, CubeColor.green, CubeColor.red))) {
            return "2" +  (cubie.topcubeColor == CubeColor.white ? 1 : cubie.topcubeColor == CubeColor.green ? 2 : 3)+ "c";
        } else if(cubie.equalsIgnoreDirection(new CornerPiece(CubeColor.white, CubeColor.orange, CubeColor.green))) {
            return "3" +  (cubie.topcubeColor == CubeColor.white ? 1 : cubie.topcubeColor == CubeColor.orange ? 2 : 3)+ "c";
        } else if(cubie.equalsIgnoreDirection(new CornerPiece(CubeColor.white, CubeColor.blue, CubeColor.orange))) {
            return "4" +  (cubie.topcubeColor == CubeColor.white ? 1 : cubie.topcubeColor == CubeColor.blue ? 2 : 3)+ "c";
        } else if(cubie.equalsIgnoreDirection(new CornerPiece(CubeColor.yellow, CubeColor.red, CubeColor.blue))) {
            return "5" +  (cubie.topcubeColor == CubeColor.yellow ? 1 : cubie.topcubeColor == CubeColor.red ? 2 : 3)+ "c";
        } else if(cubie.equalsIgnoreDirection(new CornerPiece(CubeColor.yellow, CubeColor.green, CubeColor.red))) {
            return "6" +  (cubie.topcubeColor == CubeColor.yellow ? 1 : cubie.topcubeColor == CubeColor.green ? 2 : 3)+ "c";
        } else if(cubie.equalsIgnoreDirection(new CornerPiece(CubeColor.yellow, CubeColor.orange, CubeColor.green))) {
            return "7" +  (cubie.topcubeColor == CubeColor.yellow ? 1 : cubie.topcubeColor == CubeColor.orange ? 2 : 3)+ "c";
        } else {
            return "8" +  (cubie.topcubeColor == CubeColor.yellow ? 1 : cubie.topcubeColor == CubeColor.blue ? 2 : 3)+ "c";
        }
    }

    public String CubeToString(boolean edges, boolean corners) {
        String result = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if(!((i == 1) && (j == 1) && (k == 1))) {
                        if (cubePieces[i][j][k].getClass().toString().equals("class CornerPiece") && corners) {
                            result += CornertoNumber(i, j, k);
                        } else if(cubePieces[i][j][k].getClass().toString().equals("class EdgePiece") && edges) {
                            result += EdgetoNumber(i, j, k);
                        }
                    }
                }
            }
        }
        return result;
    }

    private String EdgetoNumber(int i, int j, int k) {
        EdgePiece cubie = ((EdgePiece) cubePieces[i][j][k]);
        if(cubie.equals(new EdgePiece(CubeColor.red, CubeColor.white))) {
            return "1" +  (cubie.topcubeColor == CubeColor.red ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.green, CubeColor.white))) {
            return "2" +  (cubie.topcubeColor == CubeColor.green ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.orange, CubeColor.white))) {
            return "3" +  (cubie.topcubeColor == CubeColor.orange ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.blue, CubeColor.white))) {
            return "4" +  (cubie.topcubeColor == CubeColor.blue ? 1 : 2) + "e";
        }else if(cubie.equals(new EdgePiece(CubeColor.blue, CubeColor.white))) {
            return "5" +  (cubie.topcubeColor == CubeColor.blue ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.blue, CubeColor.white))) {
            return "6" +  (cubie.topcubeColor == CubeColor.blue ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.blue, CubeColor.white))) {
            return "7" +  (cubie.topcubeColor == CubeColor.blue ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.blue, CubeColor.white))) {
            return "8" +  (cubie.topcubeColor == CubeColor.blue ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.red, CubeColor.yellow))) {
            return "9" +  (cubie.topcubeColor == CubeColor.red ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.green, CubeColor.yellow))) {
            return "10" +  (cubie.topcubeColor == CubeColor.green ? 1 : 2) + "e";
        } else if(cubie.equals(new EdgePiece(CubeColor.orange, CubeColor.yellow))) {
            return "11" +  (cubie.topcubeColor == CubeColor.orange ? 1 : 2) + "e";
        } else {
            return "12" +  (cubie.topcubeColor == CubeColor.blue ? 1 : 2) + "e";
        }
    }
}
