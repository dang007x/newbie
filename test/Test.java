package test;

public class Test {
   
    static class Obj {
        String label;
        int x;
        int y;

        public Obj() {
        }

        public Obj(String label, int x, int y) {
            this.label = label;
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString() {
            return x + " " + y;
        }
    }

    public static void main(String[] args) {
        Obj a = new Obj("ss", 1, 2);
        Obj b = a;
        a = new Obj("aa", 0, 0);

        System.out.println(b.toString());
        
    }
}