package lru.linmul;

public class Main {
    public static void main(String[] args) {
        CSV csv = new CSV("data.csv");
        System.out.println(csv.readAll().toString());
    }
}