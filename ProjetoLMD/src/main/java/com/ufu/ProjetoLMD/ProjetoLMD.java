package com.ufu.ProjetoLMD;

import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

public class ProjetoLMD implements ViewerListener {
	protected boolean loop = true;
	private Graph graph = new SingleGraph("Busca");

	public static void main(String args[]) {
		new ProjetoLMD();
	}
	
	public ProjetoLMD() {			
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		graph.setAttribute("ui.stylesheet", "url('https://projetolmd.s3.amazonaws.com/style.css')");
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");             

		graph.setAutoCreate(true);
		graph.setStrict(false);

		Viewer viewer = graph.display();

		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.EXIT);

		preBuiltGraph();
		//		randomGraph(4, 15);

		for (Node node : graph) {
			node.addAttribute("ui.label", node.getId());
		}

		ViewerPipe fromViewer = viewer.newViewerPipe();
		fromViewer.addViewerListener(this);
		fromViewer.addSink(graph);				

		while(loop) {
			fromViewer.pump(); 
		}
	}

	public void viewClosed(String id) {
		loop = false;
	}

	public void buttonPushed(String id) {
		clearGraph();
	}

	public void buttonReleased(String id) {		
		String ordemBusca;

		Object[] options = { "Busca em Largura", "Busca em Altura", "Cancelar" };

		JPanel panel = new JPanel();
		panel.add(new JLabel("Escolha o tipo de busca desejada"));

		int result = JOptionPane.showOptionDialog(
				null, panel, "Busca no grafo",
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.PLAIN_MESSAGE,
				null, options, null);

		switch (result) {
		case 0:
			ordemBusca = explore(graph.getNode(id), 'B');
			break;

		case 1:
			ordemBusca = explore(graph.getNode(id), 'D');
			break;

		default:
			return;
		}

		JOptionPane.showMessageDialog(
				null, "A ordem percorrida no grafo foi:\n" + ordemBusca,
				"Resultado", JOptionPane.PLAIN_MESSAGE);
		clearGraph();
	}

	public String explore(Node source, char operation) {	

		String ordemBusca = "";

		Iterator<? extends Node> nodeIterator;

		switch (operation) {
		case 'B':
			nodeIterator = source.getBreadthFirstIterator();
			break;

		case 'D': 
			nodeIterator = source.getDepthFirstIterator();
			break;

		default:
			return "";			
		}

		while (nodeIterator.hasNext()) {
			Node nextNode = nodeIterator.next();
			nextNode.setAttribute("ui.class", "marked");
			ordemBusca += nextNode.getId() + " ";
			sleep();
		}

		return ordemBusca;
	}

	protected void sleep() {
		try { Thread.sleep(1000); } catch (Exception e) {}
	}

	public void clearGraph() {
		for(Node node : graph) {
			node.setAttribute("ui.class", "");
		}
	}

	public void randomGraph(int averageDegree, int amountOfNodes) {
		Generator gen = new RandomGenerator(averageDegree);
		gen.addSink(graph);
		gen.begin();
		for(int i=0; i<amountOfNodes; i++)
			gen.nextEvents();
		gen.end();
	}

	public void preBuiltGraph() {
		graph.addEdge("AB", "A", "B");
		graph.addEdge("AC", "A", "C");
		graph.addEdge("AE", "A", "E");

		graph.addEdge("BC", "B", "C");
		graph.addEdge("BJ", "B", "J");

		graph.addEdge("CD", "C", "D");
		graph.addEdge("CI", "C", "I");

		graph.addEdge("DE", "D", "E");
		graph.addEdge("DG", "D", "G");
		graph.addEdge("DH", "D", "H");

		graph.addEdge("EF", "E", "F");

		graph.addEdge("FG", "F", "G");

		graph.addEdge("GH", "G", "H");
		graph.addEdge("GO", "G", "O");

		graph.addEdge("HI", "H", "I");
		graph.addEdge("HL", "H", "L");
		graph.addEdge("HN", "H", "N");
		graph.addEdge("HO", "H", "O");

		graph.addEdge("IJ", "I", "J");
		graph.addEdge("IL", "I", "L");

		graph.addEdge("JK", "J", "K");
		graph.addEdge("JL", "J", "L");

		graph.addEdge("KL", "K", "L");
		graph.addEdge("KM", "K", "M");

		graph.addEdge("LM", "L", "M");
		graph.addEdge("LN", "L", "N");

		graph.addEdge("MN", "M", "N");

		graph.addEdge("NO", "N", "O");
	}
}
