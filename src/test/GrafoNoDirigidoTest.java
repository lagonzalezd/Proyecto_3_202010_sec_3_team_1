package test;

import model.data_structures.GrafoNoDirigido;
import org.junit.Test;
import static org.junit.Assert.*;




class GrafoNoDirigidoTest {

    private GrafoNoDirigido grafo;

    private static int TAMANO=5;

    public void setUp1()
    {
        grafo = new GrafoNoDirigido(TAMANO);
        grafo.addEdge(1, 2, 3);
        grafo.addEdge(2, 3, 3);
        grafo.addEdge(3, 4, 3);
        grafo.addEdge(4, 5, 3);
        grafo.addEdge(5, 1, 3);
    }

    @Test
    public void v() {
        setUp1();
        assertEquals(TAMANO, grafo.V());
    }

    @Test
    public void e() {
        setUp1();
        assertEquals(5, grafo.E());
    }

    @Test
    public void addEdge() {
        grafo.addEdge(1, 5, 4);
        assertEquals(1, grafo.E());
    }

    @Test
    public void getInfoVertex() {

    }

    @Test
    public void setInfoVertex() {
    }

    @Test
    public void addVertex() {
    }

    @Test
    public void getCC() {
    }

    @Test
    public void cc() {
    }

    @Test
    public void unchecked() {
    }

    @Test
    public void dfs() {
    }

    @Test
    public void setCostArc() {
    }

    @Test
    public void adj() {
    }
}