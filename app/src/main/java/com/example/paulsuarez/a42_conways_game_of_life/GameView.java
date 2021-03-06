package com.example.paulsuarez.a42_conways_game_of_life;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameView extends SurfaceView implements Runnable {

    public static final int DEFAULT_SIZE = 20;
    public static final int DEFAULT_ALIVE_COLOR = Color.BLUE;
    public static final int DEFAULT_DEAD_COLOR = Color.GRAY;
    private Thread thread;
    private boolean isRunning = false;
    private int columnWidth = 1;
    private int rowHeight = 1;
    private int nbColumns = 1;
    private int nbRows = 1;
    private World world;
    private Rect r = new Rect();
    private Paint p = new Paint();

    public GameView(Context context) {
        super(context);
        initWorld();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWorld();
    }

    @Override
    public void run() {
        while (isRunning) {
            if (!getHolder().getSurface().isValid())
                continue;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // I still don't fully understand how these try catches work
            }

            Canvas canvas = getHolder().lockCanvas();
            world.nextGeneration();
            drawCells(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isRunning = false;
        while (true) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // I still don't fully understand how these try catches work

            }
            break;
        }
    }

    private void initWorld() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        nbColumns = point.x / DEFAULT_SIZE;
        nbRows = point.y / DEFAULT_SIZE;
        columnWidth = point.x / nbColumns;
        rowHeight = point.y / nbRows;
        world = new World(nbColumns, nbRows);
    }

    private void drawCells(Canvas canvas) {
        for (int row = 0; row < nbColumns; row++) {
            for (int col = 0; col < nbRows; col++) {
                Cell cell = world.get(row, col);
                r.set((cell.x * columnWidth) - 1, (cell.y * rowHeight) - 1,
                        (cell.x * columnWidth + columnWidth) - 1,
                        (cell.y * rowHeight + rowHeight) - 1);
                p.setColor(cell.on ? DEFAULT_ALIVE_COLOR : DEFAULT_DEAD_COLOR);
                canvas.drawRect(r, p);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int row = (int) (event.getX() / columnWidth);
            int col = (int) (event.getY() / rowHeight);
            Cell cell = world.get(row, col);
            cell.invert();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
