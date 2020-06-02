package model.data_structures;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.util.Iterator;
import java.util.Stack;

public class Dijkstra<Value, Adicional1, Adicional2>
{

    private GrafoNoDirigido grafo;

    private Arco[] edgeTo;

    private double[] distTo;

    private IndexMinPQ<Double> pq;

    public Dijkstra(GrafoNoDirigido pGrafo, Vertice s)
    {
        grafo = pGrafo;
        distTo = new double[grafo.V()];
        edgeTo = new Arco[grafo.V()];

        validateVertex(s);

        for (int v = 0; v < grafo.V(); v++)
        {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        distTo[(int)s.getId()] = 0.0;

        pq = new IndexMinPQ<Double>(grafo.V());
        pq.insert((int)s.getId(), distTo[(int)s.getId()]);

        while(!pq.isEmpty())
        {
            int v = pq.delMin();
            Vertice vertexActual = (Vertice) grafo.getInfoVertex(v);
            Iterator ady = (Iterator) grafo.adj(vertexActual.getId());

            while(ady.hasNext())
            {
                Arco<Integer> arcoActual = (Arco<Integer>) ady.next();
                relax(arcoActual);
            }

        }

    }


    private void relax(Arco arco)
    {
        int v =  arco.getInicio();
        int w =  arco.getFin();

        if (distTo[w] > distTo[v] + arco.getCosto()) {
            distTo[w] = distTo[v] + arco.getCosto();
            edgeTo[w] = arco;

            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }


    private void validateVertex(Vertice vertex)
    {
        int V = distTo.length;
        int v=(int) vertex.getId();

        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }


    public double distTo(Vertice  vertex) {
        validateVertex(vertex);
        return distTo[(int) vertex.getId()];
    }


    public boolean hasPathTo(Vertice  vertex)
    {
        validateVertex(vertex);
        return distTo[(int) vertex.getId()] < Double.POSITIVE_INFINITY;
    }


    public Iterator<Arco<Integer>> pathTo(Vertice vertex )
    {
        validateVertex(vertex);
        if (!hasPathTo(vertex)) return null;
        Queue<Arco<Integer>> path = new Queue<Arco<Integer>>();

        for (Arco<Integer> e = edgeTo[(int)vertex.getId()]; e != null; e = edgeTo[(int)e.getInicio()])
        {
            path.enqueue(e);
        }
        return path.iterator();
    }

}