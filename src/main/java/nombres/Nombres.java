package nombres;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

import static java.lang.System.arraycopy;
import static java.util.stream.IntStream.range;

public class Nombres {
    private final int N = 128;
    private final int M = N + 1;
    private final int[][] tab = new int[M][M];
    private final List<A>[][] ltab = new ArrayList[M][M];
    private final List<A> X = new ArrayList<>();
    private final Map<Integer, Integer> hvaleurs = new HashMap<>();
    //  String chemin = "/home/tdc/IdeaProjects/LesNombres2/src/main/java/simple/";
    String chemin = "C:\\Users\\gille\\IdeaProjects\\LesNombres" +
            "\\src\\main\\java\\nombres\\";

    private Nombres() throws IOException {
        for (int i = 1; i < M; i++) for (int j = 1; j < M; j++) ltab[i][j] = new ArrayList<>();
        for (int i = 1; i < M; i++) for (int j = 1; j < M; j++) X.add(new A(new Paire(i, j), i * j));
        ltab();
        int[][] tab = tab();
        int[] valeurs = valeurs();
        System.out.println(Arrays.toString(valeurs));
        matriceToTextFile(tab, chemin, "tab_", N);

        int i = 6, j = 14;
        List<A> classe = classe(i, j);
        System.out.println(i * j + ": " + "NClasse=" + classe.size() + " " + classe);

        int val = 84;
        List<A> classe2 = classe(val);
        System.out.println(classe2);

        for (int v : valeurs) hvaleurs.put(v, classe(v).size());
        fonctionToTextFile(hvaleurs, chemin, "hval_", N);
    }

    public static void main(String[] args) throws IOException {
        new Nombres();
    }

    private List<A> classe(int i, int j) {
        return ltab[i][j];
    }

    private List<A> classe(int val) {
        List<A> L = new ArrayList<>();
        for (A a1 : X) L.addAll(ltab[a1.p.i][a1.p.j].stream().filter(a -> a.x == val).toList());
        return L.stream().distinct().toList();
    }

    private int[] valeurs() {
        return IntStream.of(to1Dtab(tab)).distinct().sorted().toArray();
    }

    private int[][] tab() {
        for (A a : X) tab[a.p.i][a.p.j] = ltab[a.p.i][a.p.j].size();
        return tab;
    }

    private List<A>[][] ltab() {
        X.forEach(a1 -> X.stream()
                .filter(a2 -> a1.x == a2.x)
                .forEach(a2 -> ltab[a1.p.i][a1.p.j].add(new A(new Paire(a2.p.i, a2.p.j), a2.x))));
        return ltab;
    }

    private int[] to1Dtab(int[][] tab2D) {
        int[] tab1D = new int[M * M];
        range(1, M).forEach(i -> arraycopy(tab2D[i], 0, tab1D, i * M, M));
        return tab1D;
    }

    private void matriceToTextFile(int[][] tab, String fileaddr, String filename, int n) throws IOException {
        StringBuilder sb = new StringBuilder();
        range(1, n).forEach(i -> {
            range(1, n).forEach(j -> sb.append(tab[i][j]).append(","));
            sb.append("\n");
        });

        FileWriter fw = new FileWriter(fileaddr + filename + n + ".txt", false);
        BufferedWriter output = new BufferedWriter(fw);
        output.write(sb.toString());
        output.flush();
        output.close();

    }

    private void fonctionToTextFile(Map<Integer, Integer> F, String fileaddr, String filename, int n) throws IOException {
        StringBuilder sb = new StringBuilder();
        F.forEach((key, value) -> sb.append(key).append(",").append(value).append("\n"));
        System.out.println(sb);
        FileWriter fw = new FileWriter(fileaddr + filename + n + ".txt", false);
        BufferedWriter output = new BufferedWriter(fw);
        output.write(sb.toString());
        output.flush();
        output.close();

    }

    record Paire(int i, int j) {
    }

    record A(Paire p, int x) {
        @Override
        public String toString() {
            return "(" + p.i + "," + p.j + ")";
        }
    }
}

