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
    String chemin = "/home/tdc/IdeaProjects/LesNombres/src/main/java/nombres/";
//    String chemin = "C:\\Users\\gille\\IdeaProjects\\LesNombres" +
//            "\\src\\main\\java\\nombres\\";

    private Nombres() throws IOException {
        for (int i = 1; i < M; i++) for (int j = 1; j < M; j++) ltab[i][j] = new ArrayList<>();
        for (int i = 1; i < M; i++) for (int j = 1; j < M; j++) X.add(new A(new Paire(i, j), i * j));
        boolean iInfj = true;
        ltab(iInfj);
        int[][] tab = tab();
        int[] valeurs = valeurs();
        System.out.println(Arrays.toString(valeurs));
        matriceToTextFile(tab, chemin, "tab_", N);

        int i = 18, j = 32;
        List<A> classe = classe(i, j);
        System.out.println(i * j + ": " + "NClasse=" + classe.size() + " " + classe);

        int val = i * j;
        List<A> classe2 = classe(val);
        System.out.println(classe2);

        for (int v : valeurs) hvaleurs.put(v, classe(v).size());
        fonctionToTextFile(hvaleurs, chemin, "hval_", N);
        int[][] tabd = d();
        int[][] tabdd = dd();
        final int[] erreur = {0};
        range(1, M).forEach(x -> range(1, M).forEach(y -> {

            if (coprime(x, y)) {
                if (tabd[x][y] != tabdd[x][y]) {
                    erreur[0]++;
                }
            }
        }));
        System.out.println("erreurs : "+ erreur[0]);

        new Visu(classe, N);

    }

    public static void main(String[] args) throws IOException {
        new Nombres();
    }

    private int __gcd(int a, int b) {
        return a == b ? a : (a > b ? __gcd(a - b, b) : __gcd(a, b - a));
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

    private List<A>[][] ltab(boolean iInfj) {
        X.forEach(a1 -> X.stream().filter(a -> a1.p.i <= a1.p.j && iInfj).filter(a2 -> a1.x == a2.x)
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

    int d(int n) {
        final int[] nbdiv = {0};
        range(1, M).forEach(i -> range(1, M).forEach(j -> {
            if (i * j == n) nbdiv[0]++;
        }));
        return nbdiv[0];
    }

    int[][] d() {
        int[][] mat = new int[M][M];
        range(1, M).forEach(i -> range(1, M).forEach(j -> mat[i][j] = d(i * j)));
        return mat;
    }

    int[][] dd() {
        int[][] mat = new int[M][M];
        range(1, M).forEach(i -> range(1, M).forEach(j -> mat[i][j] = d(i) * d(j)));
        return mat;
    }

    int nbOfDiv(int n) {
        return (int) range(1, n + 1).filter(i -> n % i == 0).count();
    }

    int[][] nbOfDiv() {
        int[][] mat = new int[N + 1][N + 1];
        range(1, N).forEach(i -> range(1, N).forEach(j -> mat[i][j] = nbOfDiv(i * j)));
        return mat;
    }

    private boolean coprime(int a, int b) {
        return __gcd(a, b) == 1;
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

