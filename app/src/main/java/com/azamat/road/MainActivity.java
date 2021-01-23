package com.azamat.road;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    /**
     * Ширина и высота экрана
     */

    private int screenWidth;
    private int screenHeight;

    private LinearLayout trafficXOne, trafficXTwo, trafficXThree;
    private LinearLayout trafficYOne, trafficYTwo, trafficYThree;
    int counter = 0;

    /**
     * Объявляем глобально наши переменные
     */
    private ImageView arrowUp;
    private ImageView arrowDown;
    private ImageView arrowRight;
    private ImageView arrowLeft;

    // Button
    private Button pauseBtn;

    /**
     * ПОЗИЦИЯ МАШИН
     */

    private float arrowUpY;
    private float arrowDownY;
    private float arrowRightX;
    private float arrowLeftX;

    /**
     * Инициализируем объекты
     */

    private Handler handler = new Handler();
    private Timer timer = new Timer();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        screen();
        timerAll();
        trafficTimer();

    }

    private void trafficTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        counter++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (counter){
                                    case 1:
                                        trafficXOne.setBackgroundColor(getResources().getColor(R.color.red));
                                        trafficXTwo.setBackgroundColor(getResources().getColor(R.color.grey));
                                        trafficXThree.setBackgroundColor(getResources().getColor(R.color.grey));

                                        trafficYOne.setBackgroundColor(getResources().getColor(R.color.grey));
                                        trafficYTwo.setBackgroundColor(getResources().getColor(R.color.grey));
                                        trafficYThree.setBackgroundColor(getResources().getColor(R.color.green));
                                        //TODO: Сюда попробовать дать условие стоп
                                        break;
                                    case 2:
                                        trafficXOne.setBackgroundColor(getResources().getColor(R.color.grey));
                                        trafficXTwo.setBackgroundColor(getResources().getColor(R.color.grey));
                                        trafficXThree.setBackgroundColor(getResources().getColor(R.color.green));

                                        trafficYOne.setBackgroundColor(getResources().getColor(R.color.grey));
                                        trafficYTwo.setBackgroundColor(getResources().getColor(R.color.yellow));
                                        trafficYThree.setBackgroundColor(getResources().getColor(R.color.grey));
//                                        counter = 0;
                                        break;
                                    case 3:
                                        trafficXOne.setBackgroundColor(getResources().getColor(R.color.grey));
                                        trafficXTwo.setBackgroundColor(getResources().getColor(R.color.yellow));
                                        trafficXThree.setBackgroundColor(getResources().getColor(R.color.grey));


                                        trafficYOne.setBackgroundColor(getResources().getColor(R.color.red));
                                        trafficYTwo.setBackgroundColor(getResources().getColor(R.color.grey));
                                        trafficYThree.setBackgroundColor(getResources().getColor(R.color.grey));
                                        //TODO: Сюда попробовать дать условие на старт

                                        counter = 0;
                                        break;
                                }
                            }

                        });

                    }
                }).start();

            }
        },0, 5000);
    }


    private void timerAll() {

        /**Включаем таймер*/

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos();
                    }
                });
            }
        }, 0, 30);

    }


    private void screen() {

        /**Получаем размеры экрана*/
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        /**Выход за пределы экрана, чтобы скрывались*/
        arrowUp.setY(-80.0f);
        arrowDown.setY(screenHeight + 10.0f);
        arrowRight.setX(screenWidth + 10.0f);
        arrowLeft.setX(-10.0f);

    }

    private void init() {

        /**Инициализируем наши view*/

        arrowUp = findViewById(R.id.upImg);
        arrowDown = findViewById(R.id.downImg);
        arrowRight = findViewById(R.id.rightImg);
        arrowLeft = findViewById(R.id.leftImg);

        trafficXOne = findViewById(R.id.trafficXOne);
        trafficXTwo = findViewById(R.id.trafficXTwo);
        trafficXThree = findViewById(R.id.trafficXThree);

        trafficYOne = findViewById(R.id.trafficYOne);
        trafficYTwo = findViewById(R.id.trafficYTwo);
        trafficYThree = findViewById(R.id.trafficYThree);
    }

    public void changePos() {

        /**Подключаем анимацию*/

        float scY = screenHeight/2.0f; //высота экрана делим на 2
        float arY = scY - arrowUp.getHeight(); //размер вюшки

        float scx=screenWidth/2.0f; //screen centerX
        float arx=scx-arrowRight.getWidth(); //arrowRightX

////         Up

        if ((counter == 2 || counter == 0) && (arrowUpY  >= scY && arrowUpY <= scY + 200.0f )){

        } else {
            arrowUpY -= 20.0f;
        }
        if (arrowUp.getY() + arrowUp.getHeight() < 0) {
            arrowUpY = screenHeight + 200.0f;
        }
        arrowUp.setY(arrowUpY);


       // Down

        if ((counter == 2 || counter == 0) && (arrowDownY + 220.0f  >= arY && arrowDownY <= arY) ){

        } else {
            arrowDownY += 25.0f;
        }
        if (arrowDown.getY() > screenHeight) {
            arrowDownY = -100.0f;
        }
        arrowDown.setY(arrowDownY);

        // Right

        //TODO:  svetoforColor.equals("red") && arrowRightX == 300.0f
        //TODO: arrowRightX == getResources().getColor(R.color.red) && arrowRightX == 300.0f



        if ((counter == 1 || counter==0) && (arrowRightX + 200.0f >=arx  && arrowRightX <= arx) ) {

        } else {
            arrowRightX += 30.0f;
        }
        if (arrowRight.getX() > screenWidth) {

            arrowRightX = -100.0f;
        }

       arrowRight.setX(arrowRightX);

        // Left
        if ((counter == 1 || counter== 0)  &&  (arrowLeftX <= scx+225.0f && arrowLeftX >= scx) ) {
            //arrowLeftX -= 0.0f;
        } else {
            arrowLeftX -= 25.0f;
        }
        if (arrowLeft.getX() + arrowLeft.getWidth() < 0) {
            arrowLeftX = screenWidth + 100.0f;
        }
        arrowLeft.setX(arrowLeftX);

    }


}




