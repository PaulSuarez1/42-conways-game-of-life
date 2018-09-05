package com.example.paulsuarez.a42_conways_game_of_life;

public class Cell {

        public int x;
        public int y;
        public boolean on;

        public Cell(int x, int y, boolean on) {
            this.x = x;
            this.y = y;
            this.on = on;
        }
        

        public void die() {
            on = false;
        }

        public void reborn() {
            on = true;
        }

        public void invert() {
            on = !on;
        }

}
