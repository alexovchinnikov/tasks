package com.company;

public class Main {
    private static final int START_YEAR = 1990;
    private static final int END_YEAR = 2020;

    public static void main(String[] args) {
        try {
            System.out.println("app v.1.13");

            for (int year = START_YEAR; year < END_YEAR; year++) {
                MyApp myApp = new MyApp(Integer.toString(year));

                double data = myApp.loadData();
                if (data > 0) {
                    System.out.println(year + " " + data);
                }
                myApp.saveData(year, (int) data);
            }
            System.out.println("ready");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
