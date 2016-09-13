import javafx.collections.transformation.SortedList;

import java.io.*;
import java.util.*;

/**
 * Created by Nikhil Thomas on 8/9/2016.
 */
class Tester {
    public void init(int stop) throws IOException {
        TwoCube solvedCube = new TwoCube();
        solvedCube.init();
        BufferedReader reader;
        BufferedWriter writer;
        FileReader fileReader = new FileReader("src/paths.txt");
        reader = new BufferedReader(fileReader);
        HashMap<String, Path> map = new HashMap<String, Path>();
        while(reader.ready()) {
            String line = reader.readLine();
            String[] split = line.split(" ");
            Path currentPath = new Path(split[1]);
            map.put(split[0], currentPath);
        }
        reader.close();
        FileWriter clear = new FileWriter("src/paths.txt");
        clear.close();
        FileWriter fileWriter = new FileWriter("src/paths.txt");
        map = search(stop, 0, solvedCube, map, "");
        System.out.println(map.size());
        writer = new BufferedWriter(fileWriter);
        for (Map.Entry<String, Path> cube: map.entrySet()) {
            if (cube.getValue().path.length() > 0) {
                writer.write(cube.getKey() + " " + cube.getValue().path);
                writer.newLine();
            }
        }
        writer.close();
    }
    public HashMap<String, Path> search(int stop, int current , TwoCube cube, HashMap<String, Path> map, String CurrentPath) {
        if(current >= stop) {
            return map;
        }
        if(!map.containsKey(cube)) {
            Path converter = new Path(CurrentPath);
            map.put(cube.CubeToString(), converter);
        } else {
            if(map.get(cube).path.length() > CurrentPath.length()) {
                Path converter = new Path(CurrentPath);
                map.put(cube.CubeToString(), converter);
            } else {
                return map;
            }
        }
        cube.move(Face.R, 1, true);
        map = search(stop, current+1, cube, map, CurrentPath + "R1");
        cube.move(Face.R, 2, true);
        map = search(stop, current+1, cube, map, CurrentPath + "R3");
        cube.move(Face.R, 1, true);
        cube.move(Face.U, 1, true);
        map = search(stop, current+1, cube, map, CurrentPath + "U1");
        cube.move(Face.U, 2, true);
        map = search(stop, current+1, cube, map, CurrentPath + "U3");
        cube.move(Face.U, 1, true);
        cube.move(Face.B, 1, true);
        map = search(stop, current+1, cube, map, CurrentPath + "B1");
        cube.move(Face.B, 2, true);
        map = search(stop, current+1, cube, map, CurrentPath + "B3");
        cube.move(Face.B, 1, true);
        cube.move(Face.R, 2, true);
        map = search(stop, current+1, cube, map, CurrentPath + "R2");
        cube.move(Face.R, 2, true);
        cube.move(Face.B, 2, true);
        map = search(stop, current+1, cube, map, CurrentPath + "B2");
        cube.move(Face.B, 2, true);
        cube.move(Face.U, 2, true);
        map = search(stop, current+1, cube, map, CurrentPath + "U2");
        cube.move(Face.U, 2, true);
        return map;
    }
}
public class LearningSolver {
    public static void main(String[] args) throws IOException {
//        Tester populate = new Tester();
//        populate.init(5);
        BufferedReader reader;
        BufferedWriter writer;
        FileReader fileReader = new FileReader("src/patterns.txt");
        reader = new BufferedReader(fileReader);
        HashMap<String, Path> map = new HashMap<String, Path>();
        while(reader.ready()) {
            String line = reader.readLine();
            String[] split = line.split(" ");
            Path currentPath = new Path(split[1]);
            map.put(split[0], currentPath);
        }
        reader.close();
//        FileWriter clear = new FileWriter("src/patterns.txt");
//        clear.close();
        ArrayList<String> empty = new ArrayList<>();
//        solve(scrambledCube, map, empty, "", false, empty, 0);
        for (int i = 0; i < 10; i++) {
            System.out.println((i + 1));
            TwoCube scrambledCube = new TwoCube();
            scrambledCube.init();
            scrambledCube.scramble(3, false);
            System.out.println("Done Scrambling");
            IDA(scrambledCube, map);
        }
        FileWriter fileWriter = new FileWriter("src/patterns.txt");
        writer = new BufferedWriter(fileWriter);
        for (Map.Entry<String, Path> cube: map.entrySet()) {
            if (cube.getValue().path.length() > 0) {
                writer.write(cube.getKey() + " " + cube.getValue().path);
                writer.newLine();
            }
        }
        writer.close();
    }

    private static HashMap<String, Path> IDA(TwoCube scrambledCube, HashMap<String, Path> map) {
        //call searchLayer until you find solved cube while updating map
        TwoCube copy = scrambledCube.clone();
        HashMap<TwoCube, Path> current = new HashMap<>();
        current.put(scrambledCube, new Path(""));
        breakLoop : while(true) {
            for (TwoCube cube : current.keySet()) {
                if(cube.CubeToString().equals("1121314151617181")) {
                    break breakLoop;
                }
            }
            current = searchLayer(current, map);
//            for (TwoCube cube : current.keySet()) {
//                System.out.print(cube.CubeToString() + "   ");
//            }
//            System.out.println();
        }
        String path = ((Path)current.values().toArray()[0]).path;
        path = cleanup(path);
        for (int i = 0; i < path.length() / 2; i++) {
            System.out.println("" + path.charAt(i * 2) + (path.charAt(((i * 2) + 1)) == '1' ? " " : path.charAt(((i * 2) + 1)) == '3' ? "'" : "2"));
        }
        System.out.println();
        map.put(copy.CubeToString(),new Path(path));
        return map;
    }

    private static String cleanup(String path) {
        System.out.println(path);
        for (int i = 0; i < (path.length() / 2) - 1; i++) {
            int amount1 = Integer.parseInt("" + (path.charAt((i * 2) + 1)));
            char face1 = path.charAt((i * 2));
            int amount2 = Integer.parseInt("" + (path.charAt((i * 2) + 3)));
            char face2 = path.charAt((i * 2) + 2);
            if(face1 == face2) {
                path = path.replaceAll("" + face1 + amount1 + face2 + amount2,((((amount1 + amount2) % 4) != 0) ? ("" + face2 + ((amount1 + amount2) % 4)) : ""));
                return cleanup(path);
            }
        }
        return path;
    }

    private static HashMap<TwoCube, Path> searchLayer(HashMap<TwoCube, Path> currentStates, HashMap<String, Path> map) {
        HashMap<TwoCube, Path> result = new HashMap<>();
        ArrayList<TwoCube> sortedResult = new ArrayList<>();
        for (TwoCube cube : currentStates.keySet()) {
            sortedResult.add(cube);
        }
//        for (TwoCube cube : sortedResult) {
//            System.out.print(dist(cube) + " ");
//        }
//        System.out.println();
        Collections.sort(sortedResult, new Comparator<TwoCube>() {
            @Override
            public int compare(TwoCube o1, TwoCube o2) {
                LearningSolver distance = new LearningSolver();
                int current = distance.dist(o1);
                int pattern = distance.dist(o2);
                if(current > pattern) {
                    return 1;
                } else if(current == pattern) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
//        for (TwoCube cube : sortedResult) {
//            System.out.print(dist(cube) + " ");
//        }
//        System.out.println();
//        int start = sortedResult.size() > 100 ? 0 : Integer.MIN_VALUE;
        for (TwoCube twoCube : sortedResult) {
//            start++;
//            if(start > sortedResult.size() / 2) {
//                continue;
//            }
            String input = currentStates.get(twoCube).path;
            TwoCube R1 = twoCube.clone();
            R1.move(Face.R, 1, true);
            result.put(R1, new Path(input + "R1"));
            TwoCube R2 = twoCube.clone();
            R2.move(Face.R, 2, true);
            result.put(R2, new Path(input + "R2"));
            TwoCube R3 = twoCube.clone();
            R3.move(Face.R, 3, true);
            result.put(R3, new Path(input + "R3"));

            TwoCube B1 = twoCube.clone();
            B1.move(Face.B, 1, true);
            result.put(B1, new Path(input + "B1"));
            TwoCube B2 = twoCube.clone();
            B2.move(Face.B, 2, true);
            result.put(B2, new Path(input + "B2"));
            TwoCube B3 = twoCube.clone();
            B3.move(Face.B, 3, true);
            result.put(B3, new Path(input + "B3"));

            TwoCube U1 = twoCube.clone();
            U1.move(Face.U, 1, true);
            result.put(U1, new Path(input + "U1"));
            TwoCube U2 = twoCube.clone();
            U2.move(Face.U, 2, true);
            result.put(U2, new Path(input + "U2"));
            TwoCube U3 = twoCube.clone();
            U3.move(Face.U, 3, true);
            result.put(U3, new Path(input + "U3"));
            if(map.containsKey(twoCube.CubeToString())) {
                String moves = map.get(twoCube.CubeToString()).path;
//                System.out.println("Recognized");
                TwoCube pattern = twoCube.clone();
                for (int i = 0; i < moves.length() / 2; i++) {
                    int amount = Integer.parseInt("" + (moves.charAt((i * 2) + 1)));
                    char face = moves.charAt((i * 2));
//                    System.out.println(i + " " + face + " " + amount);
                    switch (face) {
                        case 'R':
                            pattern.move(Face.R, amount, true);
                            break;
                        case 'B':
                            pattern.move(Face.B, amount, true);
                            break;
                        case 'U':
                            pattern.move(Face.U, amount, true);
                            break;
                    }
                }
                HashMap<TwoCube, Path> solved = new HashMap<>();
                solved.put(pattern, new Path(input + moves));
                return solved;
            }
            //do for all 9
            //add them all to result

            //check if it found a solved cube
            for (TwoCube cube : result.keySet()) {
                if(cube.CubeToString().equals("1121314151617181")) {
                    HashMap<TwoCube, Path> solved = new HashMap<>();
                    solved.put(cube, result.get(cube));
                    return solved;
                }
            }
        }
        return result;
    }

    public static int dist(TwoCube currentCube) {
        TwoCube patternCube = new TwoCube();
        patternCube.init();
        int cornerVal = manhattan(currentCube.findCorner(CubeColor.white, CubeColor.red, CubeColor.blue), patternCube.findCorner(CubeColor.white, CubeColor.red, CubeColor.blue));
        cornerVal += manhattan(currentCube.findCorner(CubeColor.white, CubeColor.blue, CubeColor.orange), patternCube.findCorner(CubeColor.white, CubeColor.blue, CubeColor.orange));
        cornerVal += manhattan(currentCube.findCorner(CubeColor.white, CubeColor.orange, CubeColor.green), patternCube.findCorner(CubeColor.white, CubeColor.orange, CubeColor.green));
        cornerVal += manhattan(currentCube.findCorner(CubeColor.white, CubeColor.green, CubeColor.red), patternCube.findCorner(CubeColor.white, CubeColor.green, CubeColor.red));
        cornerVal += manhattan(currentCube.findCorner(CubeColor.yellow, CubeColor.red, CubeColor.blue), patternCube.findCorner(CubeColor.yellow, CubeColor.red, CubeColor.blue));
        cornerVal += manhattan(currentCube.findCorner(CubeColor.yellow, CubeColor.blue, CubeColor.orange), patternCube.findCorner(CubeColor.yellow, CubeColor.blue, CubeColor.orange));
        cornerVal += manhattan(currentCube.findCorner(CubeColor.yellow, CubeColor.orange, CubeColor.green), patternCube.findCorner(CubeColor.yellow, CubeColor.orange, CubeColor.green));
        cornerVal += manhattan(currentCube.findCorner(CubeColor.yellow, CubeColor.green, CubeColor.red), patternCube.findCorner(CubeColor.yellow, CubeColor.green, CubeColor.red));
        return cornerVal / 4;
    }

    private static int manhattan(int[] currentCubeCorner, int[] patternCubeCorner) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            result += Math.abs(currentCubeCorner[i] - patternCubeCorner[i]);
        }
        return result;
    }

    private static HashMap<String, Path> solve(TwoCube scrambledCube, HashMap<String, Path> map, ArrayList<String> unsolved, String moves, boolean recursive, ArrayList<String> encountered, int recursiveDepth) {
        if(scrambledCube.CubeToString().equals("1121314151617181")) {
//            System.out.println("Solved");
            for (int i = 0; i < unsolved.size(); i++) {
                String path = unsolved.get(i);
                TwoCube temp = new TwoCube();
                temp.init();
                for (int j = 0; j < path.length() / 2; j++) {
                    char face = path.charAt(j*2);
                    int amount = Integer.parseInt("" + path.charAt((j*2)+1));
                    switch (face) {
                        case 'B':
                            temp.move(Face.B, 4-amount, true);
                            break;
                        case 'R':
                            temp.move(Face.R, 4-amount, true);
                            break;
                        case 'U':
                            temp.move(Face.U, 4-amount, true);
                            break;
                    }
                }
                map.put(temp.CubeToString(), new Path(path));
            }
            System.out.println("Done");
            System.out.println(moves);
            return map;
        }
        if(recursive) {
            ArrayList<String> recursiveTemp = new ArrayList<>();
            for (String s : unsolved) {
                if(s.length() <= 30) {
                    recursiveTemp.add(s + moves.charAt(moves.length() - 2) + moves.charAt(moves.length() - 1));
                }
            }
            unsolved = recursiveTemp;
        }
        String move = "";
        if(map.containsKey(scrambledCube.CubeToString())) {
            recursive = false;
            String path = map.get(scrambledCube.CubeToString()).path;
//            System.out.println(scrambledCube.CubeToString() + " " + path);
            System.out.println("Reverse Solving");
            for (int i = (path.length() / 2); i > 0; i--) {
                char face = path.charAt((i*2) - 2);
                int amount = Integer.parseInt("" + path.charAt((i*2) - 1));
                switch (face) {
                    case 'B':
                        move += "B" + (4-amount);
                        scrambledCube.move(Face.B, 4-amount, false);
                        break;
                    case 'R':
                        move += "R" + (4-amount);
                        scrambledCube.move(Face.R, 4-amount, false);
                        break;
                    case 'U':
                        move += "U" + (4-amount);
                        scrambledCube.move(Face.U, 4-amount, false);
                        break;
                }
            }
//            System.out.println("Solved: " + scrambledCube.CubeToString());
        } else {
//            if(encountered.contains(scrambledCube.CubeToString()) || recursiveDepth > 14) {
//                return map;
//            } else {
//                encountered.add(scrambledCube.CubeToString());
//            }
            recursive = true;
            unsolved.add("");
            scrambledCube.move(Face.R, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "R1", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.R, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "R2", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.R, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "R3", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.R, 1, true);

            scrambledCube.move(Face.B, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "B1", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.B, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "B2", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.B, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "B3", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.B, 1, true);

            scrambledCube.move(Face.U, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "U1", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.U, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "U2", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.U, 1, true);
            map = solve(scrambledCube, map, unsolved, moves + "U3", true, encountered, recursiveDepth + 1);
            scrambledCube.move(Face.U, 1, true);
        }
        if(!recursive) {
            moves += move;
            ArrayList<String> temp = new ArrayList<>();
            for (String s : unsolved) {
                if (s.length() <= 30) {
                    temp.add(s + move);
                }
            }
            map = solve(scrambledCube, map, temp, moves, recursive, encountered, recursiveDepth);
        }
        return map;
    }
}

class TwoCube implements Cloneable {
    private Cubie[][][] cubePieces = new Cubie[2][2][2];

    public TwoCube() {
    }

    public void init() {
        cubePieces[0][0][0] = new Cubie(CubeColor.white, CubeColor.red, CubeColor.blue);
        cubePieces[1][0][0] = new Cubie(CubeColor.white, CubeColor.green, CubeColor.red);
        cubePieces[1][1][0] = new Cubie(CubeColor.white, CubeColor.orange, CubeColor.green);
        cubePieces[0][1][0] = new Cubie(CubeColor.white, CubeColor.blue, CubeColor.orange);
        cubePieces[0][0][1] = new Cubie(CubeColor.yellow, CubeColor.red, CubeColor.blue);
        cubePieces[1][0][1] = new Cubie(CubeColor.yellow, CubeColor.green, CubeColor.red);
        cubePieces[1][1][1] = new Cubie(CubeColor.yellow, CubeColor.orange, CubeColor.green);
        cubePieces[0][1][1] = new Cubie(CubeColor.yellow, CubeColor.blue, CubeColor.orange);
    }

    @Override
    public TwoCube clone() {
        TwoCube newCube = new TwoCube();
        for (int x= 0; x < 2; x++) {
            for (int y= 0; y < 2; y++) {
                for (int z= 0; z < 2; z++) {
                    newCube.cubePieces[x][y][z] = cubePieces[x][y][z].clone();
                }
            }
        }
        return newCube;
    }

    public String scramble(int moves, boolean print) {
        Random generator = new Random();
        String lastMove = "";
        int lastface = 0;
        for (int i = 0; i < moves; i++) {
            int face = generator.nextInt(3) + 1;
            while(face == lastface) {
                face = generator.nextInt(3) + 1;
            }
            lastface = face;
            int amount = generator.nextInt(3) + 1;
            switch (face) {
                case 1:
                    move(Face.R, amount, print);
                    lastMove = "R" + amount;
                    break;
                case 2:
                    move(Face.B, amount, print);
                    lastMove = "B" + amount;
                    break;
                case 3:
                    move(Face.U, amount, print);
                    lastMove = "U" + amount;
                    break;
            }
        }
        return lastMove;
    }

    public void move(Face face, int amount, boolean scramble) {
        if(!scramble) {
            System.out.println(face.toString() + (amount == 3 ? "'" : amount == 2 ? "2"  : ""));
        }
        switch (face) {
            case U:
                for (int i = 0; i < amount; i++) {
                    int z = 1;
                    Cubie cornerTemp = cubePieces[0][0][z];
                    cubePieces[0][0][z] = cubePieces[1][0][z];
                    cubePieces[1][0][z] = cubePieces[1][1][z];
                    cubePieces[1][1][z] = cubePieces[0][1][z];
                    cubePieces[0][1][z] = cornerTemp;
                }
                break;
            case R:
                for (int i = 0; i < amount; i++) {
                    Cubie cornerTemp = cubePieces[1][0][0];
                    cubePieces[1][0][0] = swapCorner(cubePieces[1][1][0], 2, 3, 1);
                    cubePieces[1][1][0] = swapCorner(cubePieces[1][1][1], 2, 1, 3);
                    cubePieces[1][1][1] = swapCorner(cubePieces[1][0][1], 3, 1, 2);
                    cubePieces[1][0][1] = swapCorner(cornerTemp, 3, 2, 1);
                }
                break;
            case B:
                for (int i = 0; i < amount; i++) {
                    Cubie cornerTemp = cubePieces[0][1][0];
                    cubePieces[0][1][0] = swapCorner(cubePieces[0][1][1], 2, 1, 3);
                    cubePieces[0][1][1] = swapCorner(cubePieces[1][1][1], 3, 1, 2);
                    cubePieces[1][1][1] = swapCorner(cubePieces[1][1][0], 3, 2, 1);
                    cubePieces[1][1][0] = swapCorner(cornerTemp, 2, 3, 1);
                }
                break;
        }
    }

    public Cubie swapCorner(Cubie piece, int top, int right, int left) {
        CubeColor topcubeColor = top == 1 ? piece.topcubeColor : top == 2 ? piece.rightcubeColor : piece.leftcubeColor;
        CubeColor rightcubeColor = right == 1 ? piece.topcubeColor : right == 2 ? piece.rightcubeColor : piece.leftcubeColor;
        CubeColor leftcubeColor = left == 1 ? piece.topcubeColor : left == 2 ? piece.rightcubeColor : piece.leftcubeColor;
        return new Cubie(topcubeColor, rightcubeColor, leftcubeColor);
    }

    public String CubeToString() {
        String result = "";
        for (int i = 0; i < 8; i++) {
            int x = 0;
            int y = 0;
            int z = 0;
            switch (i) {
                case 1:
                    x = 1;
                    break;
                case 2:
                    x = 1;
                    y = 1;
                    break;
                case 3:
                    y = 1;
                    break;
                case 4:
                    z = 1;
                    break;
                case 5:
                    z = 1;
                    x = 1;
                    break;
                case 6:
                    z = 1;
                    x = 1;
                    y = 1;
                    break;
                case 7:
                    z = 1;
                    y = 1;
                    break;
            }
            result += CubietoNumber(x, y, z);
        }
        return result;
    }

    public String CubietoNumber(int x, int y, int z) {
        Cubie cubie = cubePieces[x][y][z];
        if(cubie.equalsColorIgnoreDirection(CubeColor.white, CubeColor.red, CubeColor.blue)) {
            return "1" +  (cubie.topcubeColor == CubeColor.white ? 1 : cubie.topcubeColor == CubeColor.red ? 2 : 3);
        } else if(cubie.equalsColorIgnoreDirection(CubeColor.white, CubeColor.green, CubeColor.red)) {
            return "2" +  (cubie.topcubeColor == CubeColor.white ? 1 : cubie.topcubeColor == CubeColor.green ? 2 : 3);
        } else if(cubie.equalsColorIgnoreDirection(CubeColor.white, CubeColor.orange, CubeColor.green)) {
            return "3" +  (cubie.topcubeColor == CubeColor.white ? 1 : cubie.topcubeColor == CubeColor.orange ? 2 : 3);
        } else if(cubie.equalsColorIgnoreDirection(CubeColor.white, CubeColor.blue, CubeColor.orange)) {
            return "4" +  (cubie.topcubeColor == CubeColor.white ? 1 : cubie.topcubeColor == CubeColor.blue ? 2 : 3);
        } else if(cubie.equalsColorIgnoreDirection(CubeColor.yellow, CubeColor.red, CubeColor.blue)) {
            return "5" +  (cubie.topcubeColor == CubeColor.yellow ? 1 : cubie.topcubeColor == CubeColor.red ? 2 : 3);
        } else if(cubie.equalsColorIgnoreDirection(CubeColor.yellow, CubeColor.green, CubeColor.red)) {
            return "6" +  (cubie.topcubeColor == CubeColor.yellow ? 1 : cubie.topcubeColor == CubeColor.green ? 2 : 3);
        } else if(cubie.equalsColorIgnoreDirection(CubeColor.yellow, CubeColor.orange, CubeColor.green)) {
            return "7" +  (cubie.topcubeColor == CubeColor.yellow ? 1 : cubie.topcubeColor == CubeColor.orange ? 2 : 3);
        } else {
            return "8" +  (cubie.topcubeColor == CubeColor.yellow ? 1 : cubie.topcubeColor == CubeColor.blue ? 2 : 3);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int[] findCorner(CubeColor top, CubeColor right, CubeColor left) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    if(cubePieces[i][j][k].equalsColorIgnoreDirection(top, right, left)) {
                        return new int[]{i, j, k};
                    }
                }
            }
        }
        throw new ArrayIndexOutOfBoundsException("No");
    }
}

class Cubie implements CubePiece, Cloneable {
    public CubeColor topcubeColor, rightcubeColor, leftcubeColor;

    public Cubie(CubeColor topcubeColor, CubeColor rightcubeColor, CubeColor leftcubeColor) {
        this.topcubeColor = topcubeColor;
        this.leftcubeColor = leftcubeColor;
        this.rightcubeColor = rightcubeColor;
    }

    @Override
    public Cubie clone() {
        return new Cubie(topcubeColor, rightcubeColor, leftcubeColor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cubie that = (Cubie) o;

        if (topcubeColor != that.topcubeColor) return false;
        if (rightcubeColor != that.rightcubeColor) return false;
        return leftcubeColor == that.leftcubeColor;

    }

    public boolean equalsIgnoreDirection(Cubie that) {
        return this == that || iscubeColorInPiece(that.topcubeColor) && iscubeColorInPiece(that.rightcubeColor) && iscubeColorInPiece(that.leftcubeColor);

    }

    public boolean equalsColorIgnoreDirection(CubeColor topcubeColor, CubeColor rightcubeColor, CubeColor leftcubeColor) {
        return iscubeColorInPiece(topcubeColor) && iscubeColorInPiece(rightcubeColor) && iscubeColorInPiece(leftcubeColor);
    }

    private boolean iscubeColorInPiece(CubeColor cubeColor) {
        return topcubeColor.equals(cubeColor) || rightcubeColor.equals(cubeColor) || leftcubeColor.equals(cubeColor);
    }

    @Override
    public int hashCode() {
        int result = topcubeColor != null ? topcubeColor.hashCode() : 0;
        result = 31 * result + (rightcubeColor != null ? rightcubeColor.hashCode() : 0);
        result = 31 * result + (leftcubeColor != null ? leftcubeColor.hashCode() : 0);
        return result;
    }
}

class Path {
    String path = "";
    public Path(String input) {
        path = input;
    }
}