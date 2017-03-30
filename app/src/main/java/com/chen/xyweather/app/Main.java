package com.chen.xyweather.app;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by chen on 17-3-30.
 */
public class Main {

    public int[] function(int n, int[] num) {

        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;

        int count = 0;
        int index = -1;
        int temp = 0;
        int min, max;
        for (int i = 1; i < n; i++) {
            temp = 0;
            min = max = -1;
            if (num[i] > num[i - 1] && num[i] >= num[i + 1]) {
                for (int j = i; j > 0; j--) {
                    if (num[j] > num[j - 1]) {
                        temp++;
                        min = j - 1;
                    } else {
                        break;
                    }
                }

                for (int j = i; j < n - 1; j++) {
                    if (num[j] > num[j + 1]) {
                        temp++;
                        max = j + 1;
                    } else {
                        break;
                    }
                }
            }
            if (count < temp) {
                count = temp;
                result[0] = min;
                result[1] = max;
            }
        }


        return result;


    }


    public static void function2(int x, int y, int[] A, int[] B) {

        int count = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= x && B[i] >= y) {
                count++;
            }
        }

        System.out.println("%d" + count);
    }

    public void te() {
        boolean first = true;
        boolean second = true;
        int n = 0;
        while (true) {

            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            if (s == null) {
                break;
            }
            String[] num = s.split(" ");
            if (first) {
                n = Integer.parseInt(num[0]);
                first = false;
            }

            if (second) {
                int[] numList = new int[n];
                for (int i = 0; i < num.length; i++) {
                    int j = Integer.parseInt(num[i]);
                    numList[i] = j;
                }
                second = false;

                function(n, numList);
            }
        }

    }

    public static void main() {


        int n = 0;
        int q = 0;
        boolean first = true;
        boolean second = true;
        boolean third = true;
        int[] A = new int[0];
        int[] B = new int[0];
        int count = 0;
        int x = 0;
        int y = 0;
        while (true) {

            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            count++;
            String[] num = s.split(" ");

            if (first) {
                n = Integer.parseInt(num[0]);
                q = Integer.parseInt(num[1]);
                first = false;
            } else if (second) {
                A = new int[n];
                for (int i = 0; i < num.length; i++) {
                    int j = Integer.parseInt(num[i]);
                    A[i] = j;
                }
                second = false;
            } else if (third) {
                B = new int[n];
                for (int i = 0; i < num.length; i++) {
                    int j = Integer.parseInt(num[i]);
                    B[i] = j;
                }
                third = false;
            } else {
                x = Integer.parseInt(num[0]);
                y = Integer.parseInt(num[1]);
                function2(x, y, A, B);
                count++;
                if (count == q)
                    break;
            }
        }
    }


}
