package com.company;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger counter3 = new AtomicInteger(0);
    static AtomicInteger counter4 = new AtomicInteger(0);
    static AtomicInteger counter5 = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrom(text)) {
                    counter(text);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isSingleLetter(text)) {
                    counter(text);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isAscendingLetter(text)) {
                    counter(text);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + counter3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter5 + " шт");
    }

    private static boolean isPalindrom(String text) {
        return text.contentEquals(new StringBuilder(text).reverse());
    }

    private static boolean isSingleLetter(String text) {
        char[] charArray = text.toCharArray();
        char letter = charArray[0];
        for (int i = 1; i < charArray.length; i++) {
            if (letter != charArray[i]) return false;
        }
        return true;
    }

    private static boolean isAscendingLetter(String text) {
        char[] charArray = text.toCharArray();
        char letter = charArray[0];
        for (int i = 1; i < charArray.length; i++) {
            if (letter > charArray[i]) return false;
            letter = charArray[i];
        }
        return true;
    }

    public static void counter(String text) {
        switch (text.length()) {
            case 3:
                counter3.addAndGet(1);
                break;
            case 4:
                counter4.addAndGet(1);
                break;
            case 5:
                counter5.addAndGet(1);
                break;
            default:
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
