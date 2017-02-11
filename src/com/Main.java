package com;

import dictionary.AVLTree;

/**
 *
 * Created by Hongyu on 2/7/2017.
 */
public class Main {
    public static void main (String args []){
        AVLTree<Integer> avl = new AVLTree<>();
        int [] q1 = {17, 7, 8, 14, 19, 6, 10, 21, 15, 12, 9, 11};
        for (int i : q1){
            avl.insert(i);
        }
        avl.printTree();
        avl.delete(17);
        avl.printTree();


        //q4
        int [] q4 = {20, 15, 31, 6, 13, 24, -1, 10, 17, -1, 9, 16, 5, 11, -1, 14};
        int M = 3;
        algorithm(new AVLTree<>(), M, q4);

    }

    private static void algorithm(AVLTree<Integer> avl, int M, int[] a) {

        for (int i : a) {
            int size = avl.getSize();
            if (i > 0) {
                if (size < M)
                    avl.insert(i);
                else {
                    int val = avl.getLargest(); //logm
                    if (val > i) { //constant
                        avl.delete(val); //logm
                        avl.insert(i); //logm
                    }
                }

            } else {
                avl.inOrderTraversal();
                System.out.println();
            }


        }
    }
}
