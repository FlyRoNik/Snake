package com.codenjoy.dojo.snake.client;


import com.codenjoy.dojo.client.Direction;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.snake.model.Elements;

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

    public static void main(String[] args) {
        start(USER_NAME, WebSocketRunner.Host.REMOTE);
    }

    Direction prior_X = null;
    Direction prior_Y = null;
    Point point_app = null;
    Point point_snake = null;

    Point point_snake_r = null;

    @Override
    public String get(Board board) {
        this.board = board;

        point_app = board.getApples().get(0);
        point_snake = board.getHead();
        point_snake_r = board.getHead();

        //TODO если 2 варианта пути то стоит ли выбирать лучший??
        return rec(Direction.STOP).toString();
    }

    private Direction rec(Direction direct) {
        if (direct == Direction.STOP) {
            direct = searchLook(board.getField()[point_snake.getX()][point_snake.getY()]);//при первом вхождении
        }else {
            setCharInDirect(direct, getLookForDirect(direct));
            point_snake.move(point_snake.getX() + direct.changeX(0), point_snake.getY() + direct.changeY(0));
            direct = direct.inverted(); // для исключения направления откуда пришда змейка
        }

        outMass();

        boolean flag = false;
        if (point_snake.getX() == point_snake_r.getX() && point_snake.getY() == point_snake_r.getY()) {
            flag = true;
        }

        searchPrior();

        boolean found_prior = false;
        Direction[] arr_direct = {direct.clockwise(), direct.clockwise().clockwise(),
                direct.clockwise().clockwise().clockwise()}; //заполняем массив напрвлениями кроме того откуда пришла

        Direction[] arr_PriorDirect = new Direction[0];
        Direction[] arr_NotPriorDirect = new Direction[0];

        for (int i = 0; i < arr_direct.length; i++) {                         //ищем приоритетные направления
            if (arr_direct[i].equals(prior_X) || arr_direct[i].equals(prior_Y)) {
                arr_PriorDirect = addToArray(arr_PriorDirect, arr_direct[i]);
            } else {
                arr_NotPriorDirect = addToArray(arr_NotPriorDirect, arr_direct[i]);
            }
        }

        getProcessArrNotPriorDirect(arr_PriorDirect);

        if (!point_snake.itsMe(point_app.copy())) {
            for (int i = 0; i <arr_PriorDirect.length; i++) {                         //ищем приоритетные направления
                char ch = getCharInDirect(arr_PriorDirect[i]);
                if (ch == Elements.NONE.ch() || ch == Elements.GOOD_APPLE.ch() || ch == '♣') {         //если можно туда двигаться
                    if (ch == '♣'){
                        return null;
                    }

                    boolean back = setAnchor(arr_direct,arr_PriorDirect[i]);   //установка ♣, вернет true если есть хоть одно направление
                    found_prior = true;
                    outMass();
                    direct = rec(arr_PriorDirect[i]);
                    outMass();
                    if (direct == Direction.ACT) {
                        if (flag){
                            outMass();
                            return arr_PriorDirect[i];
                        }
                        return Direction.ACT;
                    }

                    if (direct == Direction.STOP) {
                        point_snake.move(point_snake.getX() + arr_PriorDirect[i].inverted().changeX(0), point_snake.getY() + arr_PriorDirect[i].inverted().changeY(0));
                        setCharInDirectQ(arr_PriorDirect[i], '☼');
                        if (!back){
                            return Direction.STOP;
                        }
                        found_prior = false;
                        wipeoffAnchor(arr_direct, arr_PriorDirect[i]); //стереть ♣
                    }

                    if (direct == null) {
                        point_snake.move(point_snake.getX() + arr_PriorDirect[i].inverted().changeX(0), point_snake.getY() + arr_PriorDirect[i].inverted().changeY(0));
                        //setCharInDirect(arr_direct[i],'☼');
                        wipeoffLook(arr_PriorDirect[i]);
                        outMass();
                        if (!back){
                            return Direction.STOP;
                        }
                        found_prior = false;
                        wipeoffAnchor(arr_direct, arr_PriorDirect[i]); //стереть ♣
                        outMass();
                    }
                }
            }

            getProcessArrNotPriorDirect(arr_NotPriorDirect);

            if (!found_prior) {                                      //если не нашли приор направления ???
                for (int i = 0; i < arr_NotPriorDirect.length; i++) {       //ищем куда можно пойти
                    char ch = getCharInDirect(arr_NotPriorDirect[i]);
                    if (ch == Elements.NONE.ch() || ch == Elements.GOOD_APPLE.ch() || ch == '♣') {   //если можно туда двигаться
                        if (ch == '♣') {
                            return null;
                        }

                          if (i == arr_NotPriorDirect.length - 1 || getCharInDirect(arr_NotPriorDirect[i + 1]) != '♣') {
                            boolean back = setAnchor(arr_NotPriorDirect, arr_NotPriorDirect[i]);   //установка ♣, вернет true если есть хоть одно направление
                            outMass();
                            direct = rec(arr_NotPriorDirect[i]);
                            outMass();
                            if (direct == Direction.ACT) {
                                if (flag) {
                                    outMass();
                                    return arr_NotPriorDirect[i];
                                }
                                return Direction.ACT;
                            }

                            if (direct == Direction.STOP) {
                                point_snake.move(point_snake.getX() + arr_NotPriorDirect[i].inverted().changeX(0), point_snake.getY() + arr_NotPriorDirect[i].inverted().changeY(0));
                                setCharInDirectQ(arr_NotPriorDirect[i], '☼');
                                if (!back) {
                                    return Direction.STOP;
                                }
                                wipeoffAnchor(arr_NotPriorDirect, arr_NotPriorDirect[i]); //стереть ♣
                            }

                            if (direct == null) {
                                point_snake.move(point_snake.getX() + arr_NotPriorDirect[i].inverted().changeX(0), point_snake.getY() + arr_NotPriorDirect[i].inverted().changeY(0));
                                //setCharInDirect(arr_NotPriorDirect[i], '☼');
                                wipeoffLook(arr_NotPriorDirect[i]);
                                outMass();
                                if (!back) {
                                    return Direction.STOP;
                                }
                                wipeoffAnchor(arr_NotPriorDirect, arr_NotPriorDirect[i]); //стереть ♣
                            }
                            outMass();
                        }
                    }
                }
            }

        }else {
            return Direction.ACT;
        }

        return Direction.STOP;
    }

    private void getProcessArrNotPriorDirect(Direction[] arr_NotPriorDirect) {
        if (arr_NotPriorDirect.length < 3) {
            for (int i = 1; i < arr_NotPriorDirect.length; i++) {
                if (arr_NotPriorDirect[i] == searchLook(getCharInDirect(Direction.STOP)).inverted()) {
                    Direction direct = arr_NotPriorDirect[i];
                    arr_NotPriorDirect[i] = arr_NotPriorDirect[0];
                    arr_NotPriorDirect[0] = direct;
                }
            }
        }else {
            for (int i = 0; i < arr_NotPriorDirect.length - 1; i++) {
                if (arr_NotPriorDirect[i] == searchLook(getCharInDirect(Direction.STOP)).inverted()) {
                    Direction direct = arr_NotPriorDirect[i];
                    arr_NotPriorDirect[i] = arr_NotPriorDirect[2];
                    arr_NotPriorDirect[2] = direct;
                }
            }
        }
    }

    private void outMass(){
        for (int i = 0; i < board.getField().length; i++) {
            for (int j = 0; j < board.getField().length; j++) {
                System.out.print(board.getField()[j][i]);
            }
            System.out.println("");
        }
    }

    private boolean setAnchor(Direction []arr_direct, Direction direct) {
        boolean flag = false;
        for (Direction d : arr_direct) {
            if (!d.equals(direct)) {
                if (setCharInDirect(d, '♣')) {
                    flag = true;
                }
            }
        }
        return flag;
    }

        private void wipeoffAnchor(Direction []arr_direct, Direction direct) {
        for (Direction d : arr_direct) {
            if (!d.equals(direct)) {
                setCharInDirectA(d, ' ');
            }
        }
    }

    private void wipeoffLook(Direction direct) {
        board.set(point_snake.getX() + direct.changeX(0), point_snake.getY() + direct.changeY(0), ' ');
    }

    private void searchPrior() {
        prior_X = Direction.STOP;
        prior_Y = Direction.STOP;

        int dx = point_snake.getX() - point_app.getX();
        int dy = point_snake.getY() - point_app.getY();

        if (dx < 0) {
            prior_X = Direction.RIGHT;
        }
        if (dx > 0) {
            prior_X = Direction.LEFT;
        }
        if (dy < 0) {
            prior_Y = Direction.DOWN;
        }
        if (dy > 0) {
            prior_Y = Direction.UP;
        }
    }

    private Direction searchLook(char c) {
        switch (c) {
            case '►':{
                return Direction.RIGHT.inverted();
            }
            case '◄':{
                return Direction.LEFT.inverted();
            }
            case '▲':{
                return Direction.UP.inverted();
            }
            case '▼':{
                return Direction.DOWN.inverted();
            }
            default: return Direction.STOP;
        }
    }

    private char getLookForDirect(Direction direct) {
        if (Direction.RIGHT == direct) {
            return '►';
        }
        if (Direction.LEFT == direct) {
            return '◄';
        }
        if (Direction.DOWN == direct) {
            return '▼';
        }
        if (Direction.UP == direct) {
            return '▲';
        }
        return ' ';
    }

    private char getCharInDirect(Direction direct) {
        return board.getField()[point_snake.getX() +
                direct.changeX(0)][point_snake.getY() + direct.changeY(0)];
    }

    private Direction[] addToArray(Direction[] array, Direction s) {
        Direction[] ans = new Direction[array.length+1];
        System.arraycopy(array, 0, ans, 0, array.length);
        ans[ans.length - 1] = s;
        return ans;
    }

    private boolean setCharInDirect(Direction direct, char symbol) {
        char ch = getCharInDirect(direct);
        if (ch == Elements.NONE.ch()) {
            board.set(point_snake.getX() + direct.changeX(0), point_snake.getY() + direct.changeY(0), symbol);
            return true;
        }
        return false;
    }

    private boolean setCharInDirectQ(Direction direct, char symbol) {
        char ch = getCharInDirect(direct);
        if (ch == Elements.NONE.ch() || ch == '►' || ch == '◄' || ch == '▲' || ch == '▼') {
            board.set(point_snake.getX() + direct.changeX(0), point_snake.getY() + direct.changeY(0), symbol);
            return true;
        }
        return false;
    }

    private boolean setCharInDirectA(Direction direct, char symbol) {
        char ch = getCharInDirect(direct);
        if (ch == Elements.NONE.ch() || ch == '♣') {
            board.set(point_snake.getX() + direct.changeX(0), point_snake.getY() + direct.changeY(0), symbol);
            return true;
        }
        return false;
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
