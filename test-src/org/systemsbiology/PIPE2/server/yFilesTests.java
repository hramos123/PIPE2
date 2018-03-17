package org.systemsbiology.PIPE2.server;

import junit.framework.TestCase;
import y.algo.GraphChecker;
import y.base.*;
import y.io.*;
import y.layout.BufferedLayouter;
import y.layout.Layouter;
import y.layout.organic.OrganicLayouter;
import y.util.D;
import y.util.Maps;
import y.util.Timer;
import y.view.*;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class yFilesTests extends TestCase {
	public void testYfiles() {
		// 'graph' is of type y.base.Graph.
		Graph graph = new Graph();

		// Create 10 nodes.
		Node n[] = new Node[10];
		NodeMap nameNodeMap = graph.createNodeMap();
		for (int i = 0; i < 10; i++) {
			n[i] = graph.createNode();
			nameNodeMap.set(n[i], "Hello, I am node " + i);
		}

		// Create 5 edges. Each edge has "even" source node and "odd" target node.
		Edge e[] = new Edge[5];
		for (int i = 0, j = 0; i < 10; i += 2, j++)
			e[j] = graph.createEdge(n[i], n[i + 1]);

		// Get the number of nodes in the graph.
		// (Both methods are equivalent.)
		int nodeCount = graph.nodeCount();
		int N = graph.N();
		// Get the number of edges in the graph.
		// (Both methods are equivalent.)
		int edgeCount = graph.edgeCount();
		int E = graph.E();
		// Check if the graph is empty.
		boolean isEmpty = graph.isEmpty();
		// Check if the first node belongs to the graph.
		boolean containsNode = graph.contains(graph.firstNode());
		// Check if there is an edge between first and last node of the graph.
		boolean containsEdge = graph.containsEdge(graph.firstNode(), graph.lastNode());

		System.out.println("node count: " + nodeCount + "\nedgeCount: " + edgeCount);
		System.out.println("containsNode: " + containsNode + "\ncontainsEdge: " + containsEdge);

		// Get the first and last node of the node set from the graph.
		//graph.firstNode();
		//graph.lastNode();
		NodeCursor nc = graph.nodes();
		Node firstNode = (Node) nc.current();
		nc.cyclicPrev();
		Node lastNode = (Node) nc.current();

		// Exchange first and last node of the node set.
		graph.moveToLast(firstNode);
		graph.moveToFirst(lastNode);

		// Get the first and last edge of the edge set from the graph.
		//graph.firstEdge()
		EdgeCursor ec = graph.edges();
		Edge firstEdge = (Edge) ec.current();
		ec.cyclicPrev();
		Edge lastEdge = (Edge) ec.current();

		// Exchange first and last edge of the edge set.
		graph.moveToLast(firstEdge);
		graph.moveToFirst(lastEdge);

		// Get a cursor of all nodes from the graph.
//		NodeCursor nc = graph.nodes();
//
//		// Get a cursor of all edges from the graph.
//		EdgeCursor ec = graph.edges();

		// Get an array of all nodes from the graph.
		Node nodes[] = graph.getNodeArray();

		// Get an array of all edges from the graph.
		Edge edges[] = graph.getEdgeArray();

		// Check the positions of all nodes.
		for (int i = 0; i < graph.N(); i++) {
			if (nodes[i].index() != i)
				throw new RuntimeException("Mismatch at position " + i + ".");
			else {
//				System.out.println("All is well at position " + i + ".");
				System.out.println(nameNodeMap.get(n[i]));

			}
		}

		// Get the number of edges at a node.
		int degree = firstNode.degree();
		// Get the number of incoming edges at a node.
		int inDegree = firstNode.inDegree();
		// Get the number of outgoing edges at a node.
		int outDegree = firstNode.outDegree();

		System.out.println("degree: " + degree + "\ninDegree: " + inDegree + "\nout degree: " + outDegree);

		// Check whether there is an edge between first and last node of the graph.
		// First check if there is a connecting edge outgoing to 'lastNode'.
		Edge e1 = firstNode.getEdgeTo(lastNode);
		// If not, then check if there is a connecting edge incoming from 'lastNode'.
		if (e1 == null)
			e1 = firstNode.getEdgeFrom(lastNode);

		// Get a cursor to iterate over all edges at a node.
		EdgeCursor edges1 = firstNode.edges();
		// Get a cursor to iterate over all incoming edges at a node.
		EdgeCursor inEdges = firstNode.inEdges();
		// Get a cursor to iterate over all outgoing edges at a node.
		EdgeCursor outEdges = firstNode.outEdges();

		// Get a cursor to iterate over all neighbor nodes.
		NodeCursor neighbors = firstNode.neighbors();
		// Get a cursor to iterate over the source nodes of all incoming edges.
		// These nodes are called predecessors.
		NodeCursor predecessors = firstNode.predecessors();
		// Get a cursor to iterate over the target nodes of all outgoing edges.
		// These nodes are called successors.
		NodeCursor successors = firstNode.successors();

		// Get the two end nodes of an edge.
		Node source = firstEdge.source();
		Node target = firstEdge.target();
		// Getting the opposite when holding one of either source or target node.
		Node opposite = firstEdge.opposite(source);

		// Ask the edge whether it is a self-loop.
		boolean isSelfloop = firstEdge.isSelfLoop();

		// Forward iterate over all nodes of the node set from the graph.
		// edges is almost the same
		for (NodeCursor nc2 = graph.nodes(); nc.ok(); nc.next()) {
			Node n2 = nc2.node();
		}

		// Bind a label to the first node of the node set.
		// The bound data actually is of type java.lang.String.
		NodeMap labelNodeMap = graph.createNodeMap();
		NodeMap counterNodeMap = graph.createNodeMap();
		labelNodeMap.set(graph.firstNode(), "I am the first node!");
		// Increase the value stored in 'counterNodeMap' for the last node.
		// The bound data is an int.
		counterNodeMap.setInt(graph.lastNode(),
				counterNodeMap.getInt(graph.lastNode()) + 1);
		// Print out the label of the first node.
		System.out.print("The name of the first node is: ");
		System.out.println(labelNodeMap.get(graph.firstNode()));
		System.out.print("The counter of the last node is: ");
		System.out.println(counterNodeMap.get(graph.lastNode()));

		graph.disposeNodeMap(labelNodeMap);
		graph.disposeNodeMap(counterNodeMap);

		System.out.println("is bipartite: " + GraphChecker.isBipartite(graph));
	}

	public void testMapPerformances() {
		Timer t1 = new Timer(false);
		Timer t2 = new Timer(false);
		Timer t3 = new Timer(false);
		Timer t4 = new Timer(false);
		Timer t5 = new Timer(false);
		Graph graph = new Graph();
		for (int i = 0; i < 20000; i++)
			graph.createNode();

		for (int loop = 0; loop < 10; loop++) {
			D.bu(".");

			t1.start();
			NodeMap map = graph.createNodeMap();
			for (int i = 0; i < 10; i++) {
				for (NodeCursor nc = graph.nodes(); nc.ok(); nc.next()) {
					Node v = nc.node();
					map.setInt(v, i);
					i = map.getInt(v);
				}
			}
			graph.disposeNodeMap(map);
			t1.stop();


			t2.start();
			map = Maps.createIndexNodeMap(new int[graph.N()]);
			for (int i = 0; i < 10; i++) {
				for (NodeCursor nc = graph.nodes(); nc.ok(); nc.next()) {
					Node v = nc.node();
					map.setInt(v, i);
					map.getInt(v);
				}
			}
			t2.stop();


			t3.start();
			map = Maps.createHashedNodeMap();
			for (int i = 0; i < 10; i++) {
				for (NodeCursor nc = graph.nodes(); nc.ok(); nc.next()) {
					Node v = nc.node();
					map.setInt(v, i);
					i = map.getInt(v);
				}
			}
			t3.stop();

			t4.start();
			int[] array = new int[graph.N()];
			for (int i = 0; i < 10; i++) {
				for (NodeCursor nc = graph.nodes(); nc.ok(); nc.next()) {
					int vid = nc.node().index();
					array[vid] = i;
					i = array[vid];
				}
			}
			t4.stop();


			t5.start();
			Map jmap = new HashMap(2 * graph.N() + 1); //use hash map with good initial size
			for (int i = 0; i < 10; i++) {
				for (NodeCursor nc = graph.nodes(); nc.ok(); nc.next()) {
					Node v = nc.node();
					jmap.put(v, new Integer(i));
					i = ((Integer) jmap.get(v)).intValue();
				}
			}
			t5.stop();

		}

		D.bug("");
		D.bug("TIME:  standard NodeMap: " + t1);
		D.bug("TIME:  index    NodeMap: " + t2);
		D.bug("TIME:  hashed   NodeMap: " + t3);
		D.bug("TIME:  plain array     : " + t4);
		D.bug("TIME:  HashMap         : " + t5);
	}

	public void testLayoutStuff() {
		Random r = new Random();
//		DefaultLayoutGraph graph = new DefaultLayoutGraph();
		Graph2D graph = new Graph2D();
		Graph2DView gv = new Graph2DView();
		gv.setAntialiasedPainting(true);
		
//		gv.setBounds(200,200,200,200);
		graph.registerView(gv);
		NodeMap nameNodeMap = graph.createNodeMap();
		NodeMap intNodeMap = graph.createNodeMap();
		for (int i = 0; i < 26; i++) {
			Node n;
			//alternate nodeRealizers
			if (i % 2 == 0)
				n = graph.createNode();
			else{
				ShapeNodeRealizer nr = new ShapeNodeRealizer(ShapeNodeRealizer.ROUND_RECT);
				nr.setDropShadowOffsetX((byte)3);
				nr.setDropShadowOffsetY((byte)3);
				nr.setDropShadowColor(new Color(200,150,255));
				nr.setFillColor(new Color(255, 200,200));
				nr.setWidth(75);
				n = graph.createNode(nr);
			}
			graph.setLabelText(n, "node " + i);
			nameNodeMap.set(n, "I'm node " + i);
			intNodeMap.setInt(n, r.nextInt(10));
			// Getter methods for nodes.
			//YPoint getCenter(Node v)
			//YPoint getLocation(Node v)
			//double getHeight(Node v)
			//double getWidth(Node v)

			//// Setter methods for nodes.      from the graph object
			//void setCenter(Node v, YPoint position)
			//void setLocation(Node v, YPoint position)
			//void setSize(Node v, double w, double h)
		}
		Node[] nodes = graph.getNodeArray();
		EdgeMap weightEdgeMap = graph.createEdgeMap();
		for (int i = 0; i <25; i++) {
//			Edge e = graph.createEdge(nodes[i], nodes[i+1]);
			Edge e = graph.createEdge(nodes[r.nextInt(25)], nodes[r.nextInt(25)], getProteinInteractionEdgeRealizer());
			weightEdgeMap.setInt(e, r.nextInt(5));
			// Getter methods for edges.
			//YList getPathList(Edge edge)
			//YList getPointList(Edge edge)
			//YPoint getSourcePointAbs(Edge edge)
			//YPoint getTargetPointAbs(Edge edge)
			//// Setter methods for edges.
			//void setPath(Edge edge, YList path)
			//void setPoints(Edge edge, YList points)
			//void setSourcePointAbs(Edge edge, YPoint point)
			//void setTargetPointAbs(Edge edge, YPoint point)
		}

		// Run a hierarchical layout on the given graph.
//		Layouter hl = new HierarchicLayouter();
//		Layouter hl = new CircularLayouter();
//		Layouter hl = new TreeLayouter();
//		Layouter hl = new CircularLayouter();
		Layouter ol = new OrganicLayouter();
//		hl.doLayout(graph);
		//use the "safer" BufferLayouter
//		new BufferedLayouter(new HierarchicLayouter()).doLayout(graph);
		new BufferedLayouter(ol).doLayout(graph);
//		GraphLayout gl = new BufferedLayouter(new OrganicLayouter()).calcLayout(graph);

		//write the layout as a jpg file
		// Create the proper ImageOutputHandler for image generation.
		IOHandler ioh;
		boolean exportAsGIF = false;
		if (exportAsGIF)
			// Either GIF...
			ioh = new GIFIOHandler();
		else {
			// ... or JPG file format.
			ioh = new JPGIOHandler();
			// Set the image quality to 95%. This yields a good compromise between small
			// file size and high quality.
			((JPGIOHandler) ioh).setQuality(0.95f);
		}
		// Write the generated image to a file.
		String ext = ioh.getFileNameExtension();
		// Mind the dot! It is not part of the actual file name extension...
//		graph.updateViews();
		exportGraphToImageFileFormat(graph, (JPGIOHandler)ioh, "C:/Documents and Settings/hramos/my" + ext.toUpperCase() + "." + ext, 1000, 1000);
		//use the weight information:
//		graph.addDataProvider("PREFERRED_EDGE_LENGTH_DATA", weightEdgeMap);
//		// Invoke organic layout on the graph.
//		OrganicLayouter ol = new OrganicLayouter();
//		ol.doLayout(graph);
//		// Remove the data provider from the graph.
//		graph.removeDataProvider("PREFERRED_EDGE_LENGTH_DATA");
	}

	private void exportGraphToImageFileFormat(Graph2D graph, ImageOutputHandler ioh, String outFile, int height, int width) {
		Graph2DView originalView = replaceCurrentWithExportView(graph, ioh);
		configureExportView((Graph2DView) graph.getCurrentView(), height, width);
		writeGraphToFile(graph, ioh, outFile);
		restoreOriginalView(graph, originalView);
	}

	private Graph2DView replaceCurrentWithExportView(Graph2D graph, ImageOutputHandler ioh) {
		// Save the currently active view.
		Graph2DView originalView = (Graph2DView) graph.getCurrentView();
		// Create a new Graph2DView instance with the graph. This will be the
		// dedicated view for image export.
		Graph2DView exportView = ioh.createDefaultGraph2DView(graph);
		// Use the Graph2DRenderer instance of the currently active view. (Optional.)
		exportView.setGraph2DRenderer(originalView.getGraph2DRenderer());
		// Replace the currently active view containing the graph with the "export"
		// view.
		graph.setCurrentView(exportView);
		return originalView;
	}

	void restoreOriginalView(Graph2D graph, Graph2DView originalView) {
		// Remove the "export" view from the graph.
		graph.removeView(graph.getCurrentView());
		// Reset the current view to the originally active view.
		graph.setCurrentView(originalView);
	}

	void configureExportView(Graph2DView exportView, int height, int width) {
		ViewPortConfigurator vpc = new ViewPortConfigurator();
		// Register the graph to be exported with the configurator instance.
		// Depending on the other settings (see below) the graph will be used to
		// determine the image size, for example.
		vpc.setGraph2D(exportView.getGraph2D());
		// The complete graph should be exported, hence set the clipping type
		// accordingly.
		vpc.setClipType(ViewPortConfigurator.CLIP_GRAPH);
		// The graph's bounding box should determine the size of the image.
		vpc.setSizeType(ViewPortConfigurator.SIZE_USE_ORIGINAL);
//		vpc.setSizeType(ViewPortConfigurator.SIZE_USE_CUSTOM_HEIGHT);
		vpc.setCustomHeight(height);
		vpc.setCustomWidth(width);
		// Configure the export view using mainly default values, i.e., zoom level
		// 100%, and 15 pixel margin around the graph's bounding box.
		vpc.configure(exportView);
	}

	void writeGraphToFile(Graph2D graph, IOHandler ioh, String outFile) {
		try {
			// Writing out the graph using the given IOHandler.
			ioh.write(graph, outFile);
		} catch (IOException ioEx) {
			// Something went wrong. Complain.
			System.err.println("Cannot write graph to file '" + outFile + "'.");
		}
	}
	private EdgeRealizer getProteinInteractionEdgeRealizer(){
		EdgeRealizer retVal = new GenericEdgeRealizer();
//		EdgeRealizer retVal = new SplineEdgeRealizer();
//		retVal.setLineType(LineType.);
		retVal.setArrow(Arrow.STANDARD);
		return retVal;
	}
}
