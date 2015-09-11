package com.example.android.bluetoothchat;

/**
 * Created by malharkulkarni on 12/09/15.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class MainGamePanel extends View{
    private int xMin = 0;
    private int xMax;
    private int yMin = 0;
    private int yMax;
    private float ballRadius = 30;
    private float ballX = 0;
    private float ballY = yMax;
    private float ballSpeedX = 10;
    private float ballSpeedY = 0;
    private float gravity = 0;
    private RectF ballBounds;
    private Paint paint;
    private int touchStarted;
    private int posx,posy;
    private String TAG = "Main Game panel";

    // Constructor
    public MainGamePanel(Context context)
    {
        super(context);
        ballBounds = new RectF();
        paint = new Paint();


    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas)
    {
        // Draw the ball
        ballBounds.set(ballX - ballRadius, ballY - ballRadius, ballX + ballRadius, ballY+ballRadius);
        paint.setColor(Color.GRAY);
        canvas.drawOval(ballBounds, paint);


        // Update the position of the ball, including collision detection and reaction.
        update();

        // Delay
        try
        {
            Thread.sleep(1);
        } catch (InterruptedException e) { }

        invalidate();  // Force a re-draw
    }

    // Detect collision and update the position of the ball.
    public void update()
    {
        //Log.d(TAG, ""+xMax+" "+yMax);
        ballX += ballSpeedX;
        //ballY += ballSpeedY;
        if (ballX - ballRadius > xMax)
        {
            //ballSpeedX=-ballSpeedX;
            ballX = 0;
        }
        else if (ballX - ballRadius < xMin)
        {
            ballSpeedX=-ballSpeedX;
            ballX = xMin+ballRadius;
        }

        ballY = ballY + ballSpeedY;
        if(ballY == yMax - ballRadius)
        {
            gravity = 0;
            ballSpeedY = 0;
        }
        else
        {
            gravity = 2;
        }
        ballSpeedY = ballSpeedY + gravity;

    }

    // Called back when the view is first created or its size changes.
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH)
    {
        // Set the movement bounds for the ball
        xMax = w-1;
        yMax = h-1;
        ballY = yMax - ballRadius;

    }
    public boolean onTouchEvent(MotionEvent event)
    {
        int eventaction = event.getAction();

        posx=(int)event.getX();
        posy=(int)event.getY();
        if(eventaction==event.ACTION_DOWN)
        {
            touchStarted=1;
            if(ballY == yMax - ballRadius)
            {
                ballSpeedY = -30;
            }

        }
        else if(eventaction==event.ACTION_UP)
        {
            touchStarted=0;
        }
        // tell the system that we handled the event and no further processing is required
        return true;
    }

}