package Minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {
    private final int Images = 13;
    private final int Cell_size = 16;
    private final int Cover = 10;
    private final int Mark = 10;
    private final int Empty = 0;
    private final int Mine = 9;
    private final int Covered_mine = Mine + Cover;
    private final int Marked_mine = Covered_mine + Mark;
    private final int Draw_mine = 9;
    private final int Draw_cover = 10;
    private final int Draw_mark = 11;
    private final int Draw_wrong_mark = 12;
    private final int Mines = 60;
    private final int Rows = 20;
    private final int Cols = 20;
    private final int Board_width = Cols * Cell_size + 1;
    private final int Board_height = Rows * Cell_size + 1;
    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;
    private int allCells;
    private final JLabel statusbar;

    public Board(JLabel statusbar) {
        this.statusbar = statusbar;
        initBoard();
    }

    private void initBoard() {
        setPreferredSize(new Dimension(Board_width, Board_height));
        img = new Image[Images];
        for (int i = 0; i < Images; i++) {
            var cesta = "src/images/" + i + ".png";
            img[i] = (new ImageIcon(cesta)).getImage();
        }
        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void newGame() {
        int cell;
        var random = new Random();
        inGame = true;
        minesLeft = Mines;
        allCells = Rows * Cols;
        field = new int[allCells];
        for (int i = 0; i < allCells; i++) {
            field[i] = Cover;
        }
        statusbar.setText(Integer.toString(minesLeft));
        int i = 0;
        while (i < Mines) {
            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != Covered_mine)) {

                int current_col = position % Cols;
                field[position] = Covered_mine;
                i++;

                if (current_col > 0) {
                    cell = position - 1 - Cols;
                    if (cell >= 0) {
                        if (field[cell] != Covered_mine) {
                            field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != Covered_mine) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + Cols - 1;
                    if (cell < allCells) {
                        if (field[cell] != Covered_mine) {
                            field[cell] += 1;
                        }
                    }
                }

                cell = position - Cols;
                if (cell >= 0) {
                    if (field[cell] != Covered_mine) {
                        field[cell] += 1;
                    }
                }

                cell = position + Cols;
                if (cell < allCells) {
                    if (field[cell] != Covered_mine) {
                        field[cell] += 1;
                    }
                }

                if (current_col < (Cols - 1)) {
                    cell = position - Cols + 1;
                    if (cell >= 0) {
                        if (field[cell] != Covered_mine) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + Cols + 1;
                    if (cell < allCells) {
                        if (field[cell] != Covered_mine) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != Covered_mine) {
                            field[cell] += 1;
                        }
                    }
                }
            }
        }
    }

    private void find_empty_cells(int j) {
        int current_col = j % Cols;
        int cell;
        
        if (current_col > 0) {
            cell = j - Cols - 1;
            if (cell >= 0) {
                if (field[cell] > Mine) {
                    field[cell] -= Cover;
                    if (field[cell] == Empty) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j - 1;
            if (cell >= 0) {
                if (field[cell] > Mine) {
                    field[cell] -= Cover;
                    if (field[cell] == Empty) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + Cols - 1;
            if (cell < allCells) {
                if (field[cell] > Mine) {
                    field[cell] -= Cover;
                    if (field[cell] == Empty) {
                        find_empty_cells(cell);
                    }
                }
            }
        }

        cell = j - Cols;
        if (cell >= 0) {
            if (field[cell] > Mine) {
                field[cell] -= Cover;
                if (field[cell] == Empty) {
                    find_empty_cells(cell);
                }
            }
        }

        cell = j + Cols;
        if (cell < allCells) {
            if (field[cell] > Mine) {
                field[cell] -= Cover;
                if (field[cell] == Empty) {
                    find_empty_cells(cell);
                }
            }
        }

        if (current_col < (Cols - 1)) {
            cell = j - Cols + 1;
            if (cell >= 0) {
                if (field[cell] > Mine) {
                    field[cell] -= Cover;
                    if (field[cell] == Empty) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + Cols + 1;
            if (cell < allCells) {
                if (field[cell] > Mine) {
                    field[cell] -= Cover;
                    if (field[cell] == Empty) {
                        find_empty_cells(cell);
                    }
                }
            }

            cell = j + 1;
            if (cell < allCells) {
                if (field[cell] > Mine) {
                    field[cell] -= Cover;
                    if (field[cell] == Empty) {
                        find_empty_cells(cell);
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        int uncover = 0;
        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Cols; j++) {
                int cell = field[(i * Cols) + j];
                if (inGame && cell == Mine) {
                    inGame = false;
                }

                if (!inGame) {
                    if (cell == Covered_mine) {
                        cell = Draw_mine;
                    } 
                    else if (cell == Marked_mine) {
                        cell = Draw_mark;
                    } 
                    else if (cell > Covered_mine) {
                        cell = Draw_wrong_mark;
                    } 
                    else if (cell > Mine) {
                        cell = Draw_cover;
                    }
                }
                else {
                    if (cell > Covered_mine) {
                        cell = Draw_mark;
                    }
                    else if (cell > Mine) {
                        cell = Draw_cover;
                        uncover++;
                    }
                }
                g.drawImage(img[cell], (j * Cell_size),
                        (i * Cell_size), this);
            }
        }

        if (uncover == 0 && inGame) {
            inGame = false;
            statusbar.setText("You won");
        }
        else if (!inGame) {
            statusbar.setText("You lost");
        }
    }

    private class MinesAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int cCol = x / Cell_size;
            int cRow = y / Cell_size;
            boolean doRepaint = false;
            if (!inGame) {
                newGame();
                repaint();
            }

            if ((x < Cols * Cell_size) && (y < Rows * Cell_size)) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (field[(cRow * Cols) + cCol] > Mine) {
                        doRepaint = true;

                        if (field[(cRow * Cols) + cCol] <= Covered_mine) {
                            if (minesLeft > 0) {
                                field[(cRow * Cols) + cCol] += Mark;
                                minesLeft--;
                                String msg = Integer.toString(minesLeft);
                                statusbar.setText(msg);
                            }
                            else {
                                statusbar.setText("No marks left");
                            }
                        }
                        else {
                            field[(cRow * Cols) + cCol] -= Mark;
                            minesLeft++;
                            String msg = Integer.toString(minesLeft);
                            statusbar.setText(msg);
                        }
                    }
                }
                else {
                    if (field[(cRow * Cols) + cCol] > Covered_mine) {
                        return;
                    }
                    if ((field[(cRow * Cols) + cCol] > Mine) && (field[(cRow * Cols) + cCol] < Marked_mine)) {
                        field[(cRow * Cols) + cCol] -= Cover;
                        doRepaint = true;

                        if (field[(cRow * Cols) + cCol] == Mine) {
                            inGame = false;
                        }
                        if (field[(cRow * Cols) + cCol] == Empty) {
                            find_empty_cells((cRow * Cols) + cCol);
                        }
                    }
                }
                if (doRepaint) {
                    repaint();
                }
            }
        }
    }
}
