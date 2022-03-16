package com.cawstudios.clouddefence.helpers;

import jakarta.inject.Singleton;

import java.util.Scanner;

@Singleton
public class ConsoleIOIOHelperImpl implements ConsoleIOHelper {
    public void generateBlankLines(int count) {
        for (int i = 0; i < count; i++) {
            println("");
        }
    }

    public void println(String message) {
        System.out.println(message);
    }

    public String readString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
