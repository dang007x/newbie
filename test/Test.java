package test;

public class Test {
    public static void add(int i, int j){
        if(j > 10){
            System.out.println(j);
            return;
        }
        System.out.println("Done");
        add(i, j + 1);
    }

    public static void main(String[] args) {
        add(0, 1);
    }
}