package nombres;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.BiConsumer;

import static java.util.stream.IntStream.range;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Visu extends Canvas {
    List<Nombres.A> classe;
    int[] Valeurs;
    int WIDTH = 600;
    int HEIGHT = 600;
    Color NOIR = new Color(70, 69, 69);
    Color BLEU = new Color(0, 0, 255);
    Color ROUGE = new Color(255, 0, 0);
    Color VERT = new Color(0, 255, 0);
    Graphics graph;
    int zoom = 4;
    int dim_cel = 1;
    int N;
    int[][] tab;
    BiConsumer<Integer, Integer> F, G, H ,I;
    int a = 4;
    Integer xi, xf;
    Integer yi, yf;
    private boolean init;

    {
        G = new BiConsumer<>() {
            @Override
            public void accept(Integer x, Integer y) {

//                int X = zoom * x;
//                int Y = zoom * y;
                if (tab[x][y] == 15) {
                    graph.setColor(BLEU);
//                    g.drawRect(X, Y, taille, taille);
//                    g.fillRect(X, Y, taille, taille);

//                for (int d = 1; d <= N; d += a) {
//                    for (int k = 1; k <= N; k += a) {
                    for (int L = x; L < x + a; L++) {
                        for (int l = y; l < y + a; l++) {
                            int X = zoom * L;
                            int Y = zoom * l;
                            graph.drawRect(X, Y, dim_cel, dim_cel);
                            graph.fillRect(X, Y, dim_cel, dim_cel);

                        }
                    }
//                    }
//                }
                } else if (tab[x][y] == 30) {
                    graph.setColor(Color.RED);
//                    g.drawRect(X, Y, taille, taille);
//                    g.fillRect(X, Y, taille, taille);

//                for (int d = 1; d <= N; d += a) {
//                    for (int k = 1; k <= N; k += a) {
                    for (int L = x; L < x + a; L++) {
                        for (int l = y; l < y + a; l++) {
                            int X = zoom * L;
                            int Y = zoom * l;
                            graph.drawRect(X, Y, dim_cel, dim_cel);
                            graph.fillRect(X, Y, dim_cel, dim_cel);

                        }
                    }
//                    }
//                }
                }

            }
        };
        F = new BiConsumer<>() {

            @Override
            public void accept(Integer x, Integer y) {
                int X = zoom * x;
                int Y = zoom * y;
                // if (x <= y)

//                if (mat[x][y] == 1) {
//                    g.setColor(NOIR);
//                    g.drawRect(X, Y, taille, taille);
//                    g.fillRect(X, Y, taille, taille);
//                }

                //g.setColor(new Color((int)(pow(2,6))*mat[x][y]));
                int m = tab[x][y];
//                int c0=25,c1=50,c2=75;
//                g.setColor(new Color(m*255/c0,m*255/c1,m*255/c2));
                //g.drawRect(X, Y, taille, taille);
                //g.fillRect(X, Y, taille, taille);

//                if (m < 5) {
//                    //g.setColor(NOIR);
//                    graph.setColor(BLEU1);
//                    tracer(X, Y);
//                }
//                if (m > 5 && m < 10) {
//                    //g.setColor(ROUGE);
//                    graph.setColor(BLEU2);
//                    tracer(X, Y);
//                }
//                if (m > 10 && m < 15) {
//                    //g.setColor(BLEU);
//                    graph.setColor(BLEU3);
//                    tracer(X, Y);
//                }
//                if (m > 15) {
//                    // g.setColor(VERT);
//                    graph.setColor(BLEU4);
//                    tracer(X, Y);
//                }
//                switch (mat[x][y]) {
//
//                    case 1 -> {
//                        g.setColor(NOIR);
//                        tracer(X, Y);
//                    }
//                    case 4 -> {
//                        g.setColor(ROUGE);
//                        tracer(X, Y);
//                    }
//                    case 8 -> {
//                        g.setColor(VERT);
//                        tracer(X, Y);
//                    }
//                }
            }
        };
        H = new BiConsumer<>() {
            @Override
            public void accept(Integer x, Integer y) {
                graph.setColor(NOIR);
                if (!init) {
                    xi = zoom * x;
                    yi = zoom * y;
                    init = true;
                } else {
                    xf = zoom * x;
                    yf = zoom * y;
                    init = false;
                    graph.drawLine(xi, yi, xf, yf);

                }
                graph.drawLine(classe.get(classe.size() - 1).p().i()
                        , classe.get(classe.size() - 1).p().j()
                        , classe.get(0).p().i(), classe.get(0).p().j());
            }
        };
        I=new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer x, Integer y) {
                                int X = zoom * x;
                int Y = zoom * y;
                graph.setColor(Color.BLACK);
                tracer(X,Y);
            }
        };
    }

    public Visu(int[][] matrice, int n, int[] valeurs) {
        Valeurs = valeurs;
        N = n - 1;
        tab = matrice;
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.add(this);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Visu(List<Nombres.A> classe, int n) {
        this.classe = classe;
        this.N = n;
        JFrame frame = new JFrame();
        frame.setSize(WIDTH, HEIGHT);
        frame.add(this);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    void tracer(Integer x, Integer y) {
        graph.drawRect(x, y, dim_cel, dim_cel);
        graph.fillRect(x, y, dim_cel, dim_cel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.graph = g;
         //  range(1, N).forEach(i-> range(1, N).forEach(j-> F.accept(i, j)));
        // range(1, N).forEach(i -> rangeClosed(1, N).forEach(j -> G.accept(i, j)));
        for (Nombres.A a : classe) I.accept(a.p().i(), a.p().j());
    }

}