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
    private final int Images = 12;
    private final int Cell_size = 15;
    private final int Cover_for_cell = 10;
    private final int Mark_for_cell = 10;
    private final int Empty_cell = 0;
    private final int Mine_cell = 9;
    private final int Covered_mine_cell = Mine_cell + Cover_for_cell;
    private final int Marked_mine_cell = Covered_mine_cell + Mark_for_cell;
    private final int Draw_mine = 9;
    private final int Draw_cover = 10;
    private final int Draw_mark = 11;
    private final int Mines = 40;
    private final int N_Rows = 16;
    private final int N_Cols = 16;
    private final int Board_width = N_Cols * Cell_size + 1;
    private final int Board_height = N_Rows * Cell_size + 1;
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
        for(int i = 0; i < Images; i++) {
            var cesta = "src/images/" + i + ".png";
            img[i] = (new ImageIcon(cesta)).getImage();
        }
        newGame();
    }
    private void newGame() {
        int cell;
        var random = new Random();
        inGame = true;
        minesLeft = Mines;
        allCells = N_Rows * N_Cols;
        field = new int[allCells];
        for (int i = 0; i < allCells; i++) {

            field[i] = Cover_for_cell;
        }
        statusbar.setText(Integer.toString(minesLeft));
    }


}

