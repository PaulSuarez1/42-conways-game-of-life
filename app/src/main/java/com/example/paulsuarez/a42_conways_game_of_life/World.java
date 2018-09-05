package com.example.paulsuarez.a42_conways_game_of_life;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {


    public static final Random RANDOM = new Random();
    public int width;
    public int height;
    private Cell[][] board;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Cell[width][height];
        init();
    }

    private void init() {
        for(int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                board[row][col] = new Cell(row, col, RANDOM.nextBoolean());
            }
        }
    }

    public Cell get(int row, int col) {
        return board[row][col];
    }

    public int nbNeighboursOf(int row, int col) {
        int nb = 0;
        for (int k = row -1; k <= row +1; k++) {
            for (int l = col - 1; l <= col +1; l++) {
                if ((k != row || l != col) && k >= 0
                        && k < width && l >=0 && l < height) {
                    Cell cell = board[k][l];
                    if (cell.on) {
                        nb++;
                    }
                }
            }
        }
        return nb;
    }

    public void nextGeneration() {
        List<Cell> liveCells = new ArrayList<Cell>();
        List<Cell> deadCells = new ArrayList<Cell>();
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                Cell cell = board[row][col];
                int nbNeighbours = nbNeighboursOf(cell.x, cell.y);
                if (cell.on &&
                        (nbNeighbours < 2 || nbNeighbours > 3)) {
                    deadCells.add(cell);
                }
                if ((cell.on && (nbNeighbours == 3 || nbNeighbours == 2))
                        ||
                        (!cell.on && nbNeighbours == 3)) {
                    liveCells.add(cell);
                }
            }
        }

        for (Cell cell : liveCells) {
            cell.reborn();
        }
        
        for (Cell cell : deadCells) {
            cell.die();
        }
    }

}
