package com.ufu.ProjetoLMD;

import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");       
        graph.setAttribute("ui.stylesheet", "url('https://projetolmd.s3.amazonaws.com/style.css')");
        graph.setAutoCreate(true);
        graph.setStrict(false);
		Viewer viewer = graph.display();
		
		graph.addEdge("AB", "A", "B");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("AD", "A", "D");
        graph.addEdge("AE", "A", "E");
        
        graph.addEdge("BC", "B", "C");
        graph.addEdge("BE", "B", "E");
        
        graph.addEdge("CD", "C", "D");
        graph.addEdge("CH", "C", "H");
        graph.addEdge("CI", "C", "I");			               
   
        graph.addEdge("DE", "D", "E");			                
        graph.addEdge("DF", "D", "F");
        graph.addEdge("DG", "D", "G");
        graph.addEdge("DH", "D", "H");
        
        graph.addEdge("FE", "F", "E");			                
        graph.addEdge("FG", "F", "G");
        			                	                
        graph.addEdge("GH", "G", "H");
        graph.addEdge("GI", "G", "I");			                
        
        graph.addEdge("HI", "H", "I");	
        
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }

		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);

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
		System.out.println("Button pushed on node "+id);
		clearGraph();
	}

	public void buttonReleased(String id) {
		System.out.println("Button released on node "+id);
		
		String ordemBusca;
		
		Object[] options = { "Busca em Largura", "Busca em Altura", "Cancelar" };
		
		JPanel panel = new JPanel();
        panel.add(new JLabel("Escolha o tipo de busca desejada"));
        		
		int result = JOptionPane.showOptionDialog(
				null, panel, "Busca no grafo",
                JOptionPane.YES_NO_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE,
                null, options, null);
		
		System.out.println(result);
		
		switch (result) {
		case 0:
			ordemBusca = explore(graph.getNode(id), 0);
			break;
			
		case 1:
			ordemBusca = explore(graph.getNode(id), 1);
			break;
			
		default:
			return;
		}
		
		JOptionPane.showMessageDialog(null, "A ordem de busca no grafo foi:\n" + ordemBusca);
		clearGraph();
	}
	
	public String explore(Node source, int operation) {	
		
		String ordemBusca = "";
		
		Iterator<? extends Node> nodeIterator;
		
		switch (operation) {
		case 0:
			nodeIterator = source.getBreadthFirstIterator();
			break;

		case 1: 
			nodeIterator = source.getDepthFirstIterator();
			break;
			
		default:
			return "";			
		}
		
        while (nodeIterator.hasNext()) {
            Node nextNode = nodeIterator.next();
            nextNode.setAttribute("ui.class", "marked");
            ordemBusca += " " + nextNode.getId();
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
}