import com.sun.org.apache.xerces.internal.xni.XMLString;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by Nikhil Thomas on 7/24/2016.
 */
class RubiksCubeDisplay extends JFrame{
    static JFrame frame = new JFrame("Rubiks Cube Solver");
    public void display(Cube cube, String face) {
        JPanel layout = new JPanel(new GridLayout(3, 4));
        JButton status = new JButton(cube.status);
        layout.add(status);
        JPanel red = redFace(cube);
        layout.add(red);
        JButton moves = new JButton("Move Count: " + cube.moveCount);
        layout.add(moves);
        JButton move = new JButton("Face: " + face);
        layout.add(move);
        layout.add(moves);
        JPanel blue = blueFace(cube);
        layout.add(blue);
        JPanel white = whiteFace(cube);
        layout.add(white);
        JPanel green = greenFace(cube);
        layout.add(green);
        JPanel yellow = yellowFace(cube);
        layout.add(yellow);
        addBlank(layout);
        JPanel orange = orangeFace(cube);
        layout.add(orange);
        addBlank(layout);
        addBlank(layout);
        frame.getContentPane().add(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void addBlank(JPanel layout) {
        JButton blank1 = new JButton();
        layout.add(blank1);
    }

    private JPanel redFace(Cube cube) {
        JPanel red = new JPanel(new GridLayout(3, 3));
        red.add(getLabel(0, 0, 2, 2, 1, cube));
        red.add(getLabel(1, 0, 2, 0, 2, cube));
        red.add(getLabel(2, 0, 2, 3, 1, cube));
        red.add(getLabel(0, 0, 1, 0, 2, cube));
        red.add(getLabel(1, 0, 1, 0, 3, cube));
        red.add(getLabel(2, 0, 1, 1, 2, cube));
        red.add(getLabel(0, 0, 0, 2, 1, cube));
        red.add(getLabel(1, 0, 0, 1, 2, cube));
        red.add(getLabel(2, 0, 0, 3, 1, cube));
        return red;
    }

    private JPanel blueFace(Cube cube) {
        JPanel blue = new JPanel(new GridLayout(3, 3));
        blue.add(getLabel(0, 0, 2, 3, 1, cube));
        blue.add(getLabel(0, 0, 1, 1, 2, cube));
        blue.add(getLabel(0, 0, 0, 3, 1, cube));
        blue.add(getLabel(0, 1, 2, 0, 2, cube));
        blue.add(getLabel(0, 1, 1, 0, 3, cube));
        blue.add(getLabel(0, 1, 0, 1, 2, cube));
        blue.add(getLabel(0, 2, 2, 2, 1, cube));
        blue.add(getLabel(0, 2, 1, 0, 2, cube));
        blue.add(getLabel(0, 2, 0, 2, 1, cube));
        return blue;
    }

    private JPanel whiteFace(Cube cube) {
        JPanel white = new JPanel(new GridLayout(3, 3));
        white.add(getLabel(0, 0, 0, 1, 1, cube));
        white.add(getLabel(1, 0, 0, 0, 2, cube));
        white.add(getLabel(2, 0, 0, 1, 1, cube));
        white.add(getLabel(0, 1, 0, 0, 2, cube));
        white.add(getLabel(1, 1, 0, 1, 3, cube));
        white.add(getLabel(2, 1, 0, 0, 2, cube));
        white.add(getLabel(0, 2, 0, 1, 1, cube));
        white.add(getLabel(1, 2, 0, 0, 2, cube));
        white.add(getLabel(2, 2, 0, 1, 1, cube));
        return white;
    }

    private JPanel greenFace(Cube cube) {
        JPanel green = new JPanel(new GridLayout(3, 3));
        green.add(getLabel(2, 0, 0, 2, 1, cube));
        green.add(getLabel(2, 0, 1, 0, 2, cube));
        green.add(getLabel(2, 0, 2, 2, 1, cube));
        green.add(getLabel(2, 1, 0, 1, 2, cube));
        green.add(getLabel(2, 1, 1, 1, 3, cube));
        green.add(getLabel(2, 1, 2, 0, 2, cube));
        green.add(getLabel(2, 2, 0, 3, 1, cube));
        green.add(getLabel(2, 2, 1, 1, 2, cube));
        green.add(getLabel(2, 2, 2, 3, 1, cube));
        return green;
    }

    private JPanel yellowFace(Cube cube) {
        JPanel yellow = new JPanel(new GridLayout(3, 3));
        yellow.add(getLabel(2, 0, 2, 1, 1, cube));
        yellow.add(getLabel(1, 0, 2, 1, 2, cube));
        yellow.add(getLabel(0, 0, 2, 1, 1, cube));
        yellow.add(getLabel(2, 1, 2, 1, 2, cube));
        yellow.add(getLabel(1, 1, 2, 1, 3, cube));
        yellow.add(getLabel(0, 1, 2, 1, 2, cube));
        yellow.add(getLabel(2, 2, 2, 1, 1, cube));
        yellow.add(getLabel(1, 2, 2, 1, 2, cube));
        yellow.add(getLabel(0, 2, 2, 1, 1, cube));
        return yellow;
    }

    private JPanel orangeFace(Cube cube) {
        JPanel orange = new JPanel(new GridLayout(3, 3));
        orange.add(getLabel(0, 2, 0, 3, 1, cube));
        orange.add(getLabel(1, 2, 0, 1, 2, cube));
        orange.add(getLabel(2, 2, 0, 2, 1, cube));
        orange.add(getLabel(0, 2, 1, 1, 2, cube));
        orange.add(getLabel(1, 2, 1, 0, 3, cube));
        orange.add(getLabel(2, 2, 1, 0, 2, cube));
        orange.add(getLabel(0, 2, 2, 3, 1, cube));
        orange.add(getLabel(1, 2, 2, 0, 2, cube));
        orange.add(getLabel(2, 2, 2, 2, 1, cube));
        return orange;
    }

    private Component getLabel(int x, int y, int z, int location, int type, Cube cube) {
        char color = type == 1 ? cube.getCorner(x, y, z, location) : type == 2 ? cube.getEdge(x, y, z, location) : cube.getCenter(x, y, z);
        JButton result = new JButton(("" + color).toUpperCase());
        switch (color) {
            case 'r':
                result.setBackground(Color.RED);
                break;
            case 'o':
                result.setBackground(Color.ORANGE);
                break;
            case 'y':
                result.setBackground(Color.YELLOW);
                break;
            case 'w':
                result.setBackground(Color.WHITE);
                break;
            case 'b':
                result.setBackground(Color.BLUE);
                break;
            case 'g':
                result.setBackground(Color.GREEN);
                break;
        }
        int size = 47;
        result.setMinimumSize(new Dimension(size, size));
        result.setMaximumSize(new Dimension(size, size));
        result.setPreferredSize(new Dimension(size, size));
        return result;
    }
}
public class BeginnerSolver {
    public static void main(String[] args) {
        Cube cube = new Cube();
        cube.init(true);
        cube.scramble(50);
        cube.print();
        whiteCross(cube);
        whiteCorners(cube);
        midEdges(cube);
        solveYellowCross(cube);
        orientYellowCross(cube);
        solveYellowCorners(cube);
        orientYellowCorners(cube);
        cube.print();
        System.out.println(cube.moveCount);
    }

    private static void orientYellowCorners(Cube cube) {
        cube.status = "Orienting yellow corners";
        for (int i = 0; i < 4; i++) {
            while(cube.getCorner(2, 0, 2, 1) != 'y') {
                cube.orientAlg();
            }
            cube.move(Face.U, 1, false);
        }
    }

    private static void solveYellowCorners(Cube cube) {
        cube.status = "Moving yellow corners";
        int[] blueRed = cube.findCorner(CubeColor.yellow, CubeColor.red, CubeColor.blue, CubeColor.yellow);
        if(blueRed[1] == 0) {
            if(blueRed[0] == 2) {
                cube.move(Face.U, 1, false);
                cube.swapAlg(1);
                cube.move(Face.U, 3, false);
            }
        } else {
            if(blueRed[0] == 0) {
                cube.swapAlg(2);
            } else {
                cube.swapAlg(1);
            }
        }
        cube.move(Face.U, 3, false);
        CornerPiece result = new CornerPiece(CubeColor.yellow, CubeColor.blue, CubeColor.orange);
        while(!(result.equalsIgnoreDirection((CornerPiece)cube.cubePieces[0][0][2]))) {
            cube.swapAlg(1);
        }
        cube.move(Face.U, 1, false);
    }

    private static void orientYellowCross(Cube cube) {
        cube.status = "Positioning yellow edges";
        int[] edge = cube.findEdge(CubeColor.yellow, CubeColor.red);
        if(edge[1] == 1) {
            if(edge[0] == 0) {
                cube.move(Face.U, 3, false);
            } else {
                cube.move(Face.U, 1, false);
            }
        } else if(edge[1] == 2) {
            cube.move(Face.U, 2, false);
        }
        if((cube.getEdge(0, 1, 2, 0) == 'g' && cube.getEdge(2, 1, 2, 0) == 'b') || (cube.getEdge(0, 1, 2, 0) == 'o' && cube.getEdge(1, 2, 2, 0) == 'b') || (cube.getEdge(2, 1, 2, 0) == 'o' && cube.getEdge(1, 2, 2, 0) == 'g')) {
            cube.move(Face.M, 2, false);
            cube.move(Face.U, 1, false);
            cube.move(Face.M, 2, false);
            cube.move(Face.U, 1, false);
            cube.move(Face.M, 1, false);
            cube.move(Face.U, 2, false);
            cube.move(Face.M, 2, false);
            cube.move(Face.U, 2, false);
            cube.move(Face.M, 1, false);
            cube.move(Face.U, 2, false);
            edge = cube.findEdge(CubeColor.yellow, CubeColor.red);
            if(edge[1] == 1) {
                if(edge[0] == 0) {
                    cube.move(Face.U, 3, false);
                } else {
                    cube.move(Face.U, 1, false);
                }
            } else if(edge[1] == 2) {
                cube.move(Face.U, 2, false);
            }
        }
        while(!((cube.getEdge(0, 1, 2, 0) == 'b') && (cube.getEdge(2, 1, 2, 0) == 'g') && (cube.getEdge(1, 2, 2, 0) == 'o') && (cube.getEdge(1, 0, 2, 0) == 'r'))) {
            cube.move(Face.R, 1, false);
            cube.move(Face.U, 1, false);
            cube.move(Face.R, 3, false);
            cube.move(Face.U, 1, false);
            cube.move(Face.R, 1, false);
            cube.move(Face.U, 2, false);
            cube.move(Face.R, 3, false);
        }
    }

    private static void solveYellowCross(Cube cube) {
        cube.status = "Creating yellow cross";
        //Solve the edges
        int yellowCount = 0;
        boolean[] cross = new boolean[4];
        for (int i = 0; i < 4; i++) {
            int x = 1, y = 0, z = 2;
            switch(i) {
                case 1:
                    x = 2;
                    y = 1;
                    z = 2;
                    break;
                case 2:
                    x = 0;
                    y = 1;
                    z = 2;
                    break;
                case 3:
                    x = 1;
                    y = 2;
                    z = 2;
                    break;
            }
            cross[i] =  cube.getEdge(x, y, z, 1) == 'y';
            if(cross[i]) yellowCount++;
        }
        if(yellowCount == 0) {
            cube.FRU(1);
            cube.move(Face.U, 2, false);
            cube.FRU(2);
        } else if(yellowCount == 2) {
            if(cross[0]) {
                if(cross[1]) {
                    cube.move(Face.U, 2, false);
                    cube.FRU(1);
                } else if(cross[2]) {
                    cube.move(Face.U, 1, false);
                    cube.FRU(1);
                } else {
                    cube.move(Face.U, 1, false);
                }
            } else if(cross[3]) {
                if(cross[1]) {
                    cube.move(Face.U, 3, false);
                    cube.FRU(1);
                } else if(cross[2]) {
                    cube.FRU(1);
                }
            }
            cube.FRU(1);
        }
    }

    private static void midEdges(Cube cube) {
        cube.status = "Solving middle edges";
        for (int i = 0; i < 4; i++) {
            int[] edge = {1, 0, 0, 0};
            switch (i) {
                case 0:
                    edge = cube.findEdge(CubeColor.red, CubeColor.green);
                    break;
                case 1:
                    edge = cube.findEdge(CubeColor.green, CubeColor.orange);
                    break;
                case 2:
                    edge = cube.findEdge(CubeColor.orange, CubeColor.blue);
                    break;
                case 3:
                    edge = cube.findEdge(CubeColor.blue, CubeColor.red);
                    break;
            }
            boolean flipped = edge[3] == 1;
            if (edge[2] == 1) {
                if (edge[1] == 0) {
                    if (edge[0] == 0) {
                        if (flipped) {
                            cube.move(Face.F, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.F, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.F, 2, false);
                        } else {
                            cube.move(Face.F, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.L, 1, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.L, 3, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.R, 3, false);
                        }
                    } else if (edge[0] == 2) {
                        if (flipped) {
                            cube.mainAlg();
                            cube.mainAlg();
                            cube.mainAlg();
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.R, 1, false);
                        }
                    }
                } else if (edge[1] == 2) {
                    if (edge[0] == 0) {
                        if (flipped) {
                            cube.move(Face.L, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.L, 3, false);
                            cube.move(Face.B, 1, false);
                            cube.move(Face.L, 3, false);
                            cube.move(Face.B, 3, false);
                            cube.move(Face.L, 1, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.R, 1, false);
                        } else {
                            cube.move(Face.L, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.L, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.L, 2, false);
                            cube.move(Face.F, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.F, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.F, 2, false);
                            cube.move(Face.L, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.L, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.L, 2, false);
                        }
                    } else if (edge[0] == 2) {
                        if (flipped) {
                            cube.move(Face.R, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.R, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.R, 2, false);
                        } else {
                            cube.move(Face.R, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.R, 2, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.R, 2, false);
                            cube.mainAlg();
                            cube.mainAlg();
                            cube.mainAlg();
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.R, 1, false);
                        }
                    }
                }
            } else {
                if (edge[1] == 0) {
                    if (flipped) {
                        cube.move(Face.U, 1, false);
                        cube.move(Face.R, 1, false);
                        cube.move(Face.U, 3, false);
                        cube.move(Face.R, 3, false);
                        cube.move(Face.F, 1, false);
                        cube.move(Face.R, 3, false);
                        cube.move(Face.F, 3, false);
                        cube.move(Face.R, 1, false);
                    } else {
                        cube.move(Face.U, 2, false);
                        cube.move(Face.F, 3, false);
                        cube.move(Face.U, 1, false);
                        cube.move(Face.F, 1, false);
                        cube.move(Face.U, 1, false);
                        cube.move(Face.R, 1, false);
                        cube.move(Face.U, 3, false);
                        cube.move(Face.R, 3, false);
                    }
                } else if (edge[1] == 1) {
                    if (edge[0] == 0) {
                        if (flipped) {
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.R, 1, false);
                        } else {
                            cube.move(Face.U, 1, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.R, 3, false);
                        }
                    } else {
                        if (flipped) {
                            cube.move(Face.U, 2, false);
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.R, 1, false);
                        } else {
                            cube.move(Face.U, 3, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.R, 3, false);
                        }
                    }
                } else {
                    if (flipped) {
                        cube.move(Face.U, 3, false);
                        cube.move(Face.R, 1, false);
                        cube.move(Face.U, 3, false);
                        cube.move(Face.R, 3, false);
                        cube.move(Face.F, 1, false);
                        cube.move(Face.R, 3, false);
                        cube.move(Face.F, 3, false);
                        cube.move(Face.R, 1, false);
                    } else {
                        cube.move(Face.F, 3, false);
                        cube.move(Face.U, 1, false);
                        cube.move(Face.F, 1, false);
                        cube.move(Face.U, 1, false);
                        cube.move(Face.R, 1, false);
                        cube.move(Face.U, 3, false);
                        cube.move(Face.R, 3, false);
                    }

                }
            }
            cube.move(Face.E, 1, false);
        }
    }

    private static void whiteCorners(Cube cube) {
        cube.status = "Orienting white corners";
        for (int i = 0; i < 4; i++) {
            CornerPiece result;
            switch(i) {
                case 0:
                    result = new CornerPiece(CubeColor.white, CubeColor.green, CubeColor.red);
                    break;
                case 1:
                    result = new CornerPiece(CubeColor.white, CubeColor.orange, CubeColor.green);
                    break;
                case 2:
                    result = new CornerPiece(CubeColor.white, CubeColor.blue, CubeColor.orange);
                    break;
                case 3:
                default:
                    result = new CornerPiece(CubeColor.white, CubeColor.red, CubeColor.blue);
                    break;
            }
            moveToSolvableCorner(cube, cube.findCorner(result.topcubeColor, result.rightcubeColor, result.leftcubeColor, CubeColor.white));
            while(!cube.cubePieces[2][0][0].equals(result)) {
                cube.mainAlg();
            }
            cube.move(Face.D, 3, false);
        }
    }

    private static void moveToSolvableCorner(Cube cube, int[] corner) {
        cube.status = "Moving white corners";
        if(corner[2] == 0) {
            if(corner[1] == 0) {
                if(corner[0] == 0) {
                    cube.move(Face.L, 3, false);
                    cube.move(Face.U, 3, false);
                    cube.move(Face.L, 1, false);
                }
            } else {
                if(corner[0] == 0) {
                    cube.move(Face.L, 1, false);
                    cube.move(Face.U, 2, false);
                    cube.move(Face.L, 3, false);
                } else {
                    cube.move(Face.R, 3, false);
                    cube.move(Face.U, 2, false);
                    cube.move(Face.R, 1, false);
                    cube.move(Face.U, 3, false);
                }
            }
        } else {
            if(corner[1] == 0) {
                if(corner[0] == 0) {
                    cube.move(Face.U, 3, false);
                }
            } else {
                if(corner[0] == 0) {
                    cube.move(Face.U, 2, false);
                } else {
                    cube.move(Face.U, 1, false);
                }
            }
        }
    }

    private static void whiteCross(Cube cube) {
        cube.status = "Solving white cross";
        for (int i = 0; i < 4; i++) {
            int[] edge = {1, 0, 0, 0};
            switch (i) {
                case 0:
                    edge = cube.findEdge(CubeColor.red, CubeColor.white);
                    break;
                case 1:
                    edge = cube.findEdge(CubeColor.blue, CubeColor.white);
                    break;
                case 2:
                    edge = cube.findEdge(CubeColor.orange, CubeColor.white);
                    break;
                case 3:
                    edge = cube.findEdge(CubeColor.green, CubeColor.white);
                    break;
            }
            boolean flipped = edge[3] == 1;
            if(edge[2] == 0) {
                if(edge[1] == 0) {
                    if(flipped) {
                        cube.move(Face.F, 1, false);
                        cube.move(Face.D, 3, false);
                        cube.move(Face.L, 1, false);
                        cube.move(Face.D, 1, false);
                    }
                } else if(edge[1] == 1) {
                    if(edge[0] == 0) {
                        if(flipped) {
                            cube.move(Face.L, 3, false);
                            cube.move(Face.F, 3, false);
                        } else {
                            cube.move(Face.L, 2, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.F, 2, false);
                        }
                    }  else {
                        if(flipped) {
                            cube.move(Face.R, 1, false);
                            cube.move(Face.F, 1, false);
                        } else {
                            cube.move(Face.R, 2, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.F, 2, false);
                        }
                    }
                } else {
                    if(flipped) {
                        cube.move(Face.D, 3, false);
                        cube.move(Face.R, 1, false);
                        cube.move(Face.D, 1, false);
                        cube.move(Face.F, 1, false);
                    } else {
                        cube.move(Face.B, 2, false);
                        cube.move(Face.U, 2, false);
                        cube.move(Face.F, 2, false);
                    }

                }
            }  else if(edge[2] == 1) {
                if(edge[1] == 0) {
                    if(edge[0] == 0) {
                        if(flipped) {
                            cube.move(Face.F, 3, false);
                        } else {
                            cube.move(Face.L, 3, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.L, 1, false);
                            cube.move(Face.F, 2, false);
                        }
                    }  else if(edge[0] == 2) {
                        if(flipped) {
                            cube.move(Face.R, 1, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 2, false);
                        } else {
                            cube.move(Face.F, 1, false);
                        }
                    }
                } else if(edge[1] == 2) {
                    if(edge[0] == 0) {
                        if(flipped) {
                            cube.move(Face.L, 1, false);
                            cube.move(Face.U, 3, false);
                            cube.move(Face.L, 3, false);
                            cube.move(Face.F, 2, false);
                        } else {
                            cube.move(Face.B, 3, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.B, 1, false);
                            cube.move(Face.F, 2, false);
                        }
                    }  else if(edge[0] == 2) {
                        if(flipped) {
                            cube.move(Face.B, 1, false);
                            cube.move(Face.U, 2, false);
                            cube.move(Face.B, 3, false);
                            cube.move(Face.F, 2, false);
                        } else {
                            cube.move(Face.R, 3, false);
                            cube.move(Face.U, 1, false);
                            cube.move(Face.R, 1, false);
                            cube.move(Face.F, 2, false);
                        }
                    }
                }
            } else {
                if(edge[1] == 0) {
                    if(flipped) {
                        cube.move(Face.F, 2, false);
                    } else {
                        cube.move(Face.L, 3, false);
                        cube.move(Face.U, 1, false);
                        cube.move(Face.L, 1, false);
                        cube.move(Face.F, 3, false);
                    }
                } else if(edge[1] == 1) {
                    if(edge[0] == 0) {
                        if(flipped) {
                            cube.move(Face.U, 3, false);
                            cube.move(Face.F, 2, false);
                        } else {
                            cube.move(Face.L, 1, false);
                            cube.move(Face.F, 3, false);
                            cube.move(Face.L, 3, false);
                        }
                    }  else {
                        if(flipped) {
                            cube.move(Face.U, 1, false);
                            cube.move(Face.F, 2, false);
                        } else {
                            cube.move(Face.R, 3, false);
                            cube.move(Face.F, 1, false);
                            cube.move(Face.R, 1, false);
                        }
                    }
                } else {
                    if(flipped) {
                        cube.move(Face.U, 2, false);
                        cube.move(Face.F, 2, false);
                    } else {
                        cube.move(Face.U, 2, false);
                        cube.move(Face.L, 3, false);
                        cube.move(Face.U, 1, false);
                        cube.move(Face.L, 1, false);
                        cube.move(Face.F, 3, false);
                    }

                }
            }
//            cube.print();
            cube.move(Face.D, 1, false);
        }
    }
}

class Cube {
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
        display = new RubiksCubeDisplay();
        display.display(this, "");
    }

    @Override
    public Cube clone() {
        Cube newCube = new Cube();
        newCube.cubePieces[0][0][0] = cubePieces[0][0][0];
        newCube.cubePieces[2][0][0] = cubePieces[2][0][0];
        newCube.cubePieces[2][2][0] = cubePieces[2][2][0];
        newCube.cubePieces[0][2][0] = cubePieces[0][2][0];
        newCube.cubePieces[0][0][2] = cubePieces[0][0][2];
        newCube.cubePieces[2][0][2] = cubePieces[2][0][2];
        newCube.cubePieces[2][2][2] = cubePieces[2][2][2];
        newCube.cubePieces[0][2][2] = cubePieces[0][2][2];
        newCube.cubePieces[1][0][0] = cubePieces[1][0][0];
        newCube.cubePieces[2][1][0] = cubePieces[2][1][0];
        newCube.cubePieces[1][2][0] = cubePieces[1][2][0];
        newCube.cubePieces[0][1][0] = cubePieces[0][1][0];
        newCube.cubePieces[0][0][1] = cubePieces[0][0][1];
        newCube.cubePieces[2][0][1] = cubePieces[2][0][1];
        newCube.cubePieces[2][2][1] = cubePieces[2][2][1];
        newCube.cubePieces[0][2][1] = cubePieces[0][2][1];
        newCube.cubePieces[1][0][2] = cubePieces[1][0][2];
        newCube.cubePieces[2][1][2] = cubePieces[2][1][2];
        newCube.cubePieces[1][2][2] = cubePieces[1][2][2];
        newCube.cubePieces[0][1][2] = cubePieces[0][1][2];
        newCube.cubePieces[1][1][0] = cubePieces[1][1][0];
        newCube.cubePieces[1][0][1] = cubePieces[1][0][1];
        newCube.cubePieces[2][1][1] = cubePieces[2][1][1];
        newCube.cubePieces[1][2][1] = cubePieces[1][2][1];
        newCube.cubePieces[0][1][1] = cubePieces[0][1][1];
        newCube.cubePieces[1][1][2] = cubePieces[1][1][2];
        return newCube;
    }

    public void scramble(int moves) {
        status = "Scrambling";
        Random generator = new Random();
        Face lastMoved = Face.F;
        int lastAmount = 0;
        for (int i = 0; i < moves; i++) {
            int face = generator.nextInt(6) + 1;
            int amount = generator.nextInt(3) + 1;
            lastAmount = amount;
            switch (face) {
                case 1:
                    lastMoved = Face.R;
                    move(Face.R, amount, true);
                    break;
                case 2:
                    lastMoved = Face.L;
                    move(Face.L, amount, true);
                    break;
                case 3:
                    lastMoved = Face.U;
                    move(Face.U, amount, true);
                    break;
                case 4:
                    lastMoved = Face.D;
                    move(Face.D, amount, true);
                    break;
                case 5:
                    lastMoved = Face.F;
                    move(Face.F, amount, true);
                    break;
                case 6:
                    lastMoved = Face.B;
                    move(Face.B, amount, true);
                    break;
            }
        }
        status = "Done scrambling";
        display.display(this, lastMoved.toString() + (lastAmount == 3 ? "'" : lastAmount == 2 ? "2"  : ""));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        moveCount -= moves;
    }
    public void move(Face face, int amount, boolean scramble) {
        moveCount++;
        System.out.println(face.toString() + (amount == 3 ? "'" : amount == 2 ? "2"  : ""));
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
        display.display(this, face.toString() + (amount == 3 ? "'" : amount == 2 ? "2"  : ""));
        if(!scramble) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Thread.sleep(75);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
            return new int[]{1, 0, 0, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[2][1][0];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{2, 1, 0, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[1][2][0];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{1, 2, 0, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[0][1][0];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{0, 1, 0, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[0][0][1];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{0, 0, 1, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[2][0][1];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{2, 0, 1, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[2][2][1];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{2, 2, 1, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[0][2][1];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{0, 2, 1, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[1][0][2];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{1, 0, 2, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[2][1][2];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{2, 1, 2, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[1][2][2];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{1, 2, 2, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        edge = (EdgePiece) cubePieces[0][1][2];
        if((edge.topcubeColor == topcubeColor && edge.bottomcubeColor == bottomcubeColor) || (edge.topcubeColor == bottomcubeColor && edge.bottomcubeColor == topcubeColor)) {
            return new int[]{0, 1, 2, (edge.topcubeColor == bottomcubeColor) ? 1 : 0};
        }
        return new int[]{0, 0, 0, 0};
    }
    public int[] findCorner(CubeColor topcubeColor, CubeColor rightcubeColor, CubeColor leftcubeColor, CubeColor identifier) {
        CornerPiece that = new CornerPiece(topcubeColor, rightcubeColor, leftcubeColor);
        for (int x = 0; x < 3; x+=2) {
            for (int y = 0; y < 3; y+=2) {
                for (int z = 0; z < 3; z+=2) {
                    if (((CornerPiece)cubePieces[x][y][z]).equalsIgnoreDirection(that)) {
                        int direction = ((CornerPiece) cubePieces[x][y][z]).topcubeColor == identifier ? 1 : ((CornerPiece) cubePieces[x][y][z]).rightcubeColor == identifier ? 2 : 3;
                        return new int[] {x, y, z, direction};
                    }
                }
            }
        }
        throw new ArrayIndexOutOfBoundsException("No");
    }

    public void mainAlg() {
        move(Face.R, 1, false);
        move(Face.U, 1, false);
        move(Face.R, 3, false);
        move(Face.U, 3, false);
    }

    public void FRU(int n) {
        move(Face.F, 1, false);
        for (int i = 0; i < n; i++) {
            mainAlg();
        }
        move(Face.F, 3, false);
    }

    public void swapAlg(int i) {
        for (int j = 0; j < i; j++) {
            move(Face.L, 3, false);
            move(Face.U, 1, false);
            move(Face.R, 1, false);
            move(Face.U, 3, false);
            move(Face.L, 1, false);
            move(Face.U, 1, false);
            move(Face.R, 3, false);
            move(Face.U, 3, false);
        }
    }

    public void orientAlg() {
        move(Face.R, 3, false);
        move(Face.D, 3, false);
        move(Face.R, 1, false);
        move(Face.D, 1, false);
    }

    public String CubeToString() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    if(((i == 1) || (j == 1) || (k == 1)) && (((j != k) && (j != i) && (i != k)) || (j == 0 && k == 0) || (j == 0 && i == 0) || (i == 0 && k == 0))) {
                        System.out.println(cubePieces[i][j][k].getClass());
                    }
                }
            }
        }
        return null;
    }
}

interface CubePiece {

}

class CornerPiece implements CubePiece {
    public CubeColor topcubeColor, rightcubeColor, leftcubeColor;
    @Override
    public CornerPiece clone() {
        return new CornerPiece(topcubeColor, rightcubeColor, leftcubeColor);
    }
    public CornerPiece(CubeColor topcubeColor, CubeColor rightcubeColor, CubeColor leftcubeColor) {
        this.topcubeColor = topcubeColor;
        this.leftcubeColor = leftcubeColor;
        this.rightcubeColor = rightcubeColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CornerPiece that = (CornerPiece) o;

        if (topcubeColor != that.topcubeColor) return false;
        if (rightcubeColor != that.rightcubeColor) return false;
        return leftcubeColor == that.leftcubeColor;

    }

    public boolean equalsIgnoreDirection(CornerPiece that) {
        return this == that || iscubeColorInPiece(that.topcubeColor) && iscubeColorInPiece(that.rightcubeColor) && iscubeColorInPiece(that.leftcubeColor);

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

class EdgePiece implements CubePiece {
    public boolean equals(EdgePiece obj) {
        return ((topcubeColor.equals(obj.topcubeColor)) && (bottomcubeColor.equals(obj.bottomcubeColor))) || ((topcubeColor.equals(obj.bottomcubeColor)) && (bottomcubeColor.equals(obj.topcubeColor)));
    }

    @Override
    public EdgePiece clone() {
        return new EdgePiece(topcubeColor, bottomcubeColor);
    }
    public CubeColor topcubeColor, bottomcubeColor;
    public EdgePiece(CubeColor topcubeColor, CubeColor bottomcubeColor) {
        this.topcubeColor = topcubeColor;
        this.bottomcubeColor = bottomcubeColor;
    }
}

class CenterPiece implements CubePiece {
    public CubeColor cubeColor;
    @Override
    public CenterPiece clone() {
        return new CenterPiece(cubeColor);
    }
    public CenterPiece(CubeColor cubeColor) {
        this.cubeColor = cubeColor;
    }
}

enum CubeColor {
    white,
    green,
    blue,
    orange,
    red,
    yellow
}

enum Face {
    R,
    L,
    U,
    D,
    F,
    B,
    E,
    M,
    d
}