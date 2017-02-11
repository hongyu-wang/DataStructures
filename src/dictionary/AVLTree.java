package dictionary;


import java.util.ArrayList;
import java.util.Collections;

public class AVLTree<T extends Comparable<T>> implements Dictionary<T> {
    private T root;
    private int balanceFactor;
    private int height;
    private AVLTree<T> parent;
    private AVLTree<T> left;
    private AVLTree<T> right;
    private int size;
    public AVLTree(){
        this(null, null, null);
    }

    private AVLTree(T root, AVLTree<T> left, AVLTree<T> right){
        this.root = root;
        this.left = left;
        this.right = right;
        this.parent = null;
        updateData();
    }

    @Override
    public void insert(T obj) {
        if (root == null) {
            root = obj;
            left = new AVLTree<>();
            right = new AVLTree<>();
        }
        else if (obj.compareTo(root) < 0){
            left.insert(obj);
        } else {
            right.insert(obj);
        }
        updateData();
        fixBalance();
    }

    public T getLargest(){
        if (root == null) return null;
        if (right.root == null){
            return root;
        }
        return right.getLargest();
    }

    private boolean fixBalance() {
        if (balanceFactor > 1){
            if (right.balanceFactor == -1){
                right.rightRotate();
            }leftRotate();
            return true;
        } else if (balanceFactor < -1){
            if (left.balanceFactor == 1){
                left.leftRotate();
            }rightRotate();
            return true;

        }
        return false;
    }

    private void rotate(T otherRoot, AVLTree<T> p1, AVLTree<T> p2, boolean rotate){
        T root = this.root;
        this.root = otherRoot;
        if (rotate){
            left = p1;
            left.parent = this;
            right = new AVLTree<>(root, p2, right);
            right.parent = this;
            p2.parent = right;
            right.right.parent = right;
        } else {
            right = p2;
            right.parent = this;
            left = new AVLTree<>(root, left, p1);
            left.parent = this;
            p1.parent =  left;
            left.left.parent =  left;
        }
        totalUpdate();
    }

    private void rightRotate() {

        rotate(left.root, left.left, left.right, true);
    }

    private void leftRotate() {
        rotate(right.root, right.left, right.right, false);
    }

    private void totalUpdate(){
        left.updateData();
        right.updateData();
        updateData();
    }

    private void updateData(){
        if (root == null){
            height = 0;
            size = 0;
            balanceFactor = 0;
        } else {
            height = Math.max(left.height, right.height) + 1;
            balanceFactor = right.height - left.height;
            size = left.size + right.size + 1;

        }
    }
    @Override
    public void delete(T obj) {
        if (root == null) return;

        if (root == obj){
            root = null;
            if (!isLeaf()){
                if (left.root == null){
                    root = right.root;
                    right.nullify();
                    checkUp();
                } else if (right.root == null){
                    root = left.root;
                    left.nullify();
                    checkUp();
                } else {
                    AVLTree<T> smallest = right.getSmallest();
                    root = smallest.root;
                    if (smallest.isLeaf()){
                        smallest.nullify();
                        if (smallest.parent != null)
                            smallest.parent.checkUp();
                    } else {
                        smallest.root = smallest.right.root;
                        smallest.right.nullify();
                        smallest.checkUp();
                    }

                }
            }

        } else{
            if (root.compareTo(obj) > 0) {
                left.delete(obj);
            } else {
                right.delete(obj);
            }
        }
        updateData();
    }

    public int getRank(T x){
        if (root == null){
            return -1;
        }
        int r = left.size + 1;
        if (x == root){
            return r;
        }
        if (root.compareTo(x) > 0){
            return r + right.getRank(x);
        } else {
            return left.getRank(x);
        }
    }

    public T select(int i){

        int val;
        if (i == (val = size + 1))
            return root;
        else if (i < val)
            return left.select(i);
        return right.select(i - val);
    }

    private void checkUp(){

        if (balanceFactor == 0){
            updateData();
        } else {
            updateData();
            if (balanceFactor == 0 && parent != null){
                parent.checkUp();
            } else {
                int height = this.height;
                fixBalance();
                if (this.height < height && parent != null)
                    parent.checkUp();
            }
        }
    }


    private void nullify(){
        root = null;
        left = null;
        right = null;
        updateData();
    }

    private AVLTree<T> getSmallest(){

        if (left.root == null){
            return this;
        }
        return left.getSmallest();
    }

    private boolean isLeaf(){
        return left.root == null && right.root == null;
    }

    public void inOrderTraversal(){

        if (root == null)
            return;
        left.inOrderTraversal();
        System.out.print(root + " ");

        right.inOrderTraversal();
    }



    @Override
    public boolean search(T obj) {
        return root != null && (root == obj || left.search(obj) || right.search(obj));

    }

    private static <T extends Comparable<T>> void getHeapRep(AVLTree<T> root, ArrayList<AVLTree<T>> arr, int index){
        if (root == null){

            return;
        }
        arr.set(index, root);

        getHeapRep(root.left, arr, index*2 + 1);
        getHeapRep(root.right, arr, index*2 + 2);
    }

    @Override
    public String toString() {
        return "["+balanceFactor + " " + root +"]";
    }

    public void printTree() {
        if (root == null){
            System.out.println("null");
            return;
        }
        ArrayList<AVLTree<T>> arr = new ArrayList<>(Collections.nCopies((int)Math.pow(2, height + 1) - 1, null));
        getHeapRep(this, arr, 0);
        StringBuilder stringBuilder = new StringBuilder();
        int index = 0;
        for (int i = 0; i < height; i ++){
            for (int j = 0; j < Math.pow(2, i); j ++){
                stringBuilder.append(arr.get(index ++)).append(" ");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }

    public int getSize(){
        return size;
    }


}