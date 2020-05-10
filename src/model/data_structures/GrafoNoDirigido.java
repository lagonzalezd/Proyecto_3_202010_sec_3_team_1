package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;
import model.data_structures.EstacionVertice;

import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.SeparateChainingHashST;

public class GrafoNoDirigido<K, V> {
	
	//Código tomado de: https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/Graph.java
	//https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/DepthFirstPaths.java
	//Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.

	private EdgeWeightedGraph grafo;
	private CC cc;
	private SeparateChainingHashST<K, Integer> llaveAEntero;
	private SeparateChainingHashST<Integer, K> enteroALlave;
	private SeparateChainingHashST<K, V> llaveAInfoVertex;
	private Haversine hav;
	
	private boolean[] marked;
	private int[] edgeTo;

	private int V = 0;
	
	public int V(){
		return V;
	}
	
	public int E(){
		return grafo.E();
	}
	
	public GrafoNoDirigido(int tamaNo) {
		grafo = new EdgeWeightedGraph(tamaNo);
		llaveAEntero = new SeparateChainingHashST<K, Integer>();
		llaveAInfoVertex = new SeparateChainingHashST<K, V>();
		enteroALlave = new SeparateChainingHashST<Integer, K>();
		hav = new Haversine();
		cc = new CC(grafo);
	}
	
	public void addEdge(K from, K to, double peso) {
		
		if(!llaveAEntero.contains(from)) {
			llaveAEntero.put(from, V);
			enteroALlave.put(V, from);
			V++;
		}
		int fromEntero = llaveAEntero.get(from);
		
		if(!llaveAEntero.contains(to)) {
			llaveAEntero.put(to, V);
			enteroALlave.put(V, to);
			V++;
		}
		int toEntero = llaveAEntero.get(to);

		grafo.addEdge(new Edge(fromEntero, toEntero, peso));
	}
	
	public V getInfoVertex(K key) {
		return llaveAInfoVertex.get(key);
	}
	
	public void setInfoVertex(K idVertex, V infoVertex){
		llaveAInfoVertex.put(idVertex, infoVertex);;
	}
	
	public void addVertex(K key, V info) {
		llaveAInfoVertex.put(key, info);
		if(!llaveAEntero.contains(key)) {
			llaveAEntero.put(key, V);
			enteroALlave.put(V, key);
			V++;
		}
	}
	
	public Iterable<K> getCC(K key) {
		CC cc = new CC (grafo);
		ArrayList<K> lista = new ArrayList<K>();
		
		for(int v = 0; v < grafo.V(); v++) {
			if(cc.id(v) == cc.id(llaveAEntero.get(key))) {
				lista.add(enteroALlave.get(v));
			}
		}
		
		return lista;
	}
	
	public int cc(){
		return cc.count();
	}
	
	public void unchecked(){
		for (int i = 0; i<marked.length; i++){
			marked[i] = false;
		}
	}
	
	public void dfs(K s){
		int v = llaveAEntero.get(s);
        marked[v] = true;
        for (K w : adj(s)) {
        	int k = llaveAEntero.get(w);
            if (!marked[k]) {
                edgeTo[k] = v;
                dfs(w);
            }
        }
	}
	
	public void setCostArc(K idVertexIni, K idVertexFin, double cost){
		Iterator<K> ini = adj(idVertexIni).iterator();
		boolean encontro = false;
		int fin = llaveAEntero.get(idVertexFin);
		while (ini.hasNext() && !encontro){
			EstacionArco ar = (EstacionArco) ini.next();
			if(fin == ar.getFin()){
				EstacionVertice vinit = (EstacionVertice) llaveAInfoVertex.get(idVertexIni);
				EstacionVertice vfin = (EstacionVertice) llaveAInfoVertex.get(idVertexFin);
				double costo = hav.distance(vinit.getLatitud(), vinit.getLongitud(), vfin.getLatitud(), vfin.getLongitud());
				ar.setCost(costo);
			}
		}
	}
	
	public Iterable<K> adj(K key) {
		Iterable<Edge> a = grafo.adj(llaveAEntero.get(key));
		
		ArrayList<K> resultado = new ArrayList<K>();
		for(Edge e: a) {
			int v = e.other(llaveAEntero.get(key));
			resultado.add(enteroALlave.get(v));
		}
		return resultado;
	}
	
	
	
}
