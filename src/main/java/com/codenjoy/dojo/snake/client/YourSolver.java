package com.codenjoy.dojo.snake.client;


import com.codenjoy.dojo.client.Direction;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.snake.model.Elements;

import java.util.List;

/**
 * User: your name
 */
public class YourSolver implements Solver<Board> {

    private static final String USER_NAME = "hang.glider.viru007@gmail.com";

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;

        Point point_app = board.getApples().get(0);
        Point point_snake = board.getHead();
        Point point_app_kek = board.getStones().get(0);
        List<Point> point_snake_body = board.getSnake();

        String result = "";
        char key = 0;

        int x_head = point_snake.getX();
        int y_head = point_snake.getY();
        int x_app = point_app.getX();
        int y_app = point_app.getY();
        char good_apple = Elements.GOOD_APPLE.ch();

        if (point_snake_body.size() == 25) {
            x_app = point_app_kek.getX();
            y_app = point_app_kek.getY();
            good_apple = Elements.BAD_APPLE.ch();
        }

        if (x_head > x_app) {
            key = 'l';
            if (board.getField()[x_head -1][y_head] == Elements.NONE.ch() ||
                    board.getField()[x_head -1][y_head] == good_apple) {
                result = Direction.LEFT.toString();
            }
        }

        if (x_head < x_app){
            key = 'r';
            if (board.getField()[x_head +1][y_head] == good_apple ||
                    board.getField()[x_head +1][y_head] == Elements.NONE.ch()) {
                result =  Direction.RIGHT.toString();
            }
        }

        if (y_head > y_app) {
            key = 'u';
            if (board.getField()[x_head][y_head -1] == Elements.NONE.ch()||
                    board.getField()[x_head][y_head -1] == good_apple) {
                result = Direction.UP.toString();
            }
        }

        if (y_head < y_app) {
            key = 'd';
            if (board.getField()[x_head][y_head +1] == Elements.NONE.ch()||
                    board.getField()[x_head][y_head +1] == good_apple) {
                result = Direction.DOWN.toString();
            }
        }

        if (result == "") {
            switch (key) {
                case 'l':{
                    if (board.getField()[x_head][y_head -1] == Elements.NONE.ch()||
                            board.getField()[x_head][y_head -1] == good_apple) {
                        result = Direction.UP.toString();
                    }else {
                        if (board.getField()[x_head][y_head + 1] == Elements.NONE.ch() ||
                                board.getField()[x_head][y_head + 1] == good_apple) {
                            result = Direction.DOWN.toString();
                        } else {
                            if (board.getField()[x_head +1][y_head] == good_apple ||
                                    board.getField()[x_head +1][y_head] == Elements.NONE.ch()) {
                                result =  Direction.RIGHT.toString();
                            }else {
                                //do nothing
                            }
                        }
                    }
                    break;
                }

                case 'r':{
                    if (board.getField()[x_head][y_head -1] == Elements.NONE.ch()||
                            board.getField()[x_head][y_head -1] == good_apple) {
                        result = Direction.UP.toString();
                    }else {
                        if (board.getField()[x_head][y_head + 1] == Elements.NONE.ch() ||
                                board.getField()[x_head][y_head + 1] == good_apple) {
                            result = Direction.DOWN.toString();
                        } else {
                            if (board.getField()[x_head -1][y_head] == Elements.NONE.ch() ||
                                    board.getField()[x_head -1][y_head] == good_apple) {
                                result = Direction.LEFT.toString();
                            }else {
                                //do nothing
                            }
                        }
                    }
                    break;
                }

                case 'u':{
                    if (board.getField()[x_head -1][y_head] == Elements.NONE.ch() ||
                            board.getField()[x_head -1][y_head] == good_apple) {
                        result = Direction.LEFT.toString();
                    }else {
                        if (board.getField()[x_head + 1][y_head] == good_apple ||
                                board.getField()[x_head + 1][y_head] == Elements.NONE.ch()) {
                            result = Direction.RIGHT.toString();
                        } else {
                            if (board.getField()[x_head][y_head + 1] == Elements.NONE.ch() ||
                                    board.getField()[x_head][y_head + 1] == good_apple) {
                                result = Direction.DOWN.toString();
                            } else {
                                //do nothing
                            }
                        }
                    }
                    break;
                }
                case  'd':{
                    if (board.getField()[x_head -1][y_head] == Elements.NONE.ch() ||
                            board.getField()[x_head -1][y_head] == good_apple) {
                        result = Direction.LEFT.toString();
                    }else {
                        if (board.getField()[x_head + 1][y_head] == good_apple ||
                                board.getField()[x_head + 1][y_head] == Elements.NONE.ch()) {
                            result = Direction.RIGHT.toString();
                        } else {
                            if (board.getField()[x_head][y_head - 1] == Elements.NONE.ch() ||
                                    board.getField()[x_head][y_head - 1] == good_apple) {
                                result = Direction.UP.toString();
                            } else {
                                //do nothing
                            }
                        }
                    }
                    break;
                }
            }
        }

        if (result == "") {
            result = Direction.UP.toString();
        }

        return result;
    }

    public static void main(String[] args) {
        start(USER_NAME, WebSocketRunner.Host.REMOTE);
    }

    public static void start(String name, WebSocketRunner.Host server) {
        try {
            WebSocketRunner.run(server, name,
                    new YourSolver(new RandomDice()),
                    new Board());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
