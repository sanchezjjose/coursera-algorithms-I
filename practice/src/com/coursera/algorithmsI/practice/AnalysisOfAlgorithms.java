package com.coursera.algorithmsI.practice;

public class AnalysisOfAlgorithms {

  public static void main(String args[]) {

    int N = 10;
    int sum = 0;

    for (int i = 0; i < N; i++) {  // i = 0,
      for (int j = i; j < N; j++) { // j = 0, 1..2
        for (int k = i; k <= j; k++) { // k = 0,
          sum++;
        }
      }
    }

    System.out.println(sum);
  }
}
