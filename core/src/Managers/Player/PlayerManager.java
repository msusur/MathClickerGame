package Managers.Player;


public class PlayerManager {

    private static int lvl = 1;
    private static int money = 0;
    private static int diamond = 5;

    public static int getLvl() {
        return lvl;
    }

    public static int getMoney() {
        return money;
    }

    public static int getDiamond() {
        return diamond;
    }

    public static void setMoney(int val) {
        money = val;
    }

    public static void addMoney(int val) {
        money += val;
    }

    public static void setDiamond(int val) {
        diamond = val;
    }

    public static void setLvl(int val) {
        lvl = val;
    }

}
