package edu.utexas.cs.nn.networks.hyperneat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.utexas.cs.nn.MMNEAT.MMNEAT;
import edu.utexas.cs.nn.evolution.genotypes.TWEANNGenotype;
import edu.utexas.cs.nn.graphics.DrawingPanel;
import edu.utexas.cs.nn.networks.ActivationFunctions;
import edu.utexas.cs.nn.networks.TWEANN;
import edu.utexas.cs.nn.networks.TWEANN.Node;
import edu.utexas.cs.nn.parameters.Parameters;
import edu.utexas.cs.nn.util.datastructures.Pair;

/**
 * Util class containing methods used by hyperNEAT and its tasks
 * 
 * @author Lauren Gillespie
 *
 */
public class HyperNEATUtil {

	//may change later to just use collections.sort with a special comparator instead of creating new data type
	public static class VisualNode implements Comparable<VisualNode> {
		public double activation;
		public int xCoord, yCoord;
		public Color c;
		public boolean dead;

		public VisualNode(double activation, int xCoord, int yCoord) {
			this(activation, xCoord, yCoord, Color.white);
		}

		public VisualNode(double activation, int xCoord, int yCoord, Color c) {
			this(activation, xCoord, yCoord, c, false);
		}

		public VisualNode(double activation, int xCoord, int yCoord, Color c, boolean dead) {
			this.activation = activation;
			this.xCoord = xCoord*SUBS_GRID_SIZE;
			this.yCoord = yCoord*SUBS_GRID_SIZE;
			this.c = c;
			this.dead = dead;
		}

		public void setColor(Color c) {
			this.c = c;
		}
		@Override
		public int compareTo(VisualNode o) {
			if(o.dead && this.dead) return 0; // dead neurons are alike
			else if(o.dead) return 1; // this is not dead
			else if(this.dead) return -1; // o is not dead
			else return (int) Math.signum(this.activation - o.activation);
		} 

		public void drawNode(DrawingPanel p) {
			p.getGraphics().setColor(c);
			p.getGraphics().fillRect(xCoord, yCoord, SUBS_GRID_SIZE, SUBS_GRID_SIZE);
		}

		@Override
		public String toString() {
			String s = "Visual Node: (";
			s += "activation: " + activation;
			s+= ", X-coord: " + xCoord;
			s += " Y-coord: " + yCoord;
			s += ", Color: " + c;
			s += ") + \n";
			return s;
		}
	}

	//size of grid in substrate drawing. 
	public final static int LINK_WINDOW_SPACING = 5;
	public final static int SUBS_GRID_SIZE = Parameters.parameters.integerParameter("substrateGridSize");
	public final static int WEIGHT_GRID_SIZE = Parameters.parameters.integerParameter("substrateWeightSize");
	private static List<DrawingPanel> substratePanels = null;
	private static ArrayList<DrawingPanel> weightPanels = null;
	private static HyperNEATTask hyperNEATTask;
	private static TWEANNGenotype tg;
	private static List<Substrate> substrates;
	private static List<Pair<String, String>> connections;
	private static List<TWEANNGenotype.NodeGene> nodes;

	public static void resetSubstrates() {
		if(hyperNEATTask != null) {
			substrates = hyperNEATTask.getSubstrateInformation();
		}
	}

	public static List<DrawingPanel> drawSubstrates(ArrayList<Node> nodes) {
		if(substratePanels == null) {
			hyperNEATTask = (HyperNEATTask) MMNEAT.task;
			substrates = hyperNEATTask.getSubstrateInformation();
			substratePanels = new ArrayList<DrawingPanel>();
			int nodeIndexStart = 0;
			int substratePlacing = 0;
			for(int i = 0; i < substrates.size(); i++) {
				Substrate s = substrates.get(i);
				substratePanels.add(drawSubstrate(s, nodes, nodeIndexStart));
				substratePanels.get(i).setLocation(substratePlacing, 0);
				nodeIndexStart += s.size.t1 * s.size.t2;
				substratePlacing += substratePanels.get(i).getFrame().getWidth();
			}
		} else {
			int nodeIndexStart = 0;
			for(int i = 0; i < substrates.size(); i++) {
				Substrate s = substrates.get(i);
				drawSubstrate(substratePanels.get(i), s, nodes, nodeIndexStart);
				nodeIndexStart += s.size.t1 * s.size.t2;
			}
		}
		return substratePanels;
	}

	/**
	 * Draws substrate as a grid with squares representing each substrate coordinate
	 * @param s substrate
	 * @param nodes list of substrate nodes
	 * @param nodeIndexStart where the relevant nodes start
	 * @return drawing panel containing substrate drawing
	 */
	public static DrawingPanel drawSubstrate(Substrate s, ArrayList<Node> nodes, int nodeIndexStart) { 
		DrawingPanel p = new DrawingPanel(s.size.t1 * SUBS_GRID_SIZE, s.size.t2 * SUBS_GRID_SIZE, s.name);
		return drawSubstrate(p, s, nodes, nodeIndexStart); // updates existing panel
	}

	/**
	 * Overloaded. Draws substrate as a grid with squares representing each substrate coordinate
	 *  
	 * @param dp drawing panel which substrate is going to be drawn onto
	 * @param s substrate in question
	 * @param nodes list of substrate nodes 
	 * @param nodeIndexStart where the relevant nodes start
	 * @return drawing panel containing drawing of substrate
	 */
	public static DrawingPanel drawSubstrate(DrawingPanel dp, Substrate s, ArrayList<Node> nodes, int nodeIndexStart) { 
		drawCoord(dp, s, nodes, nodeIndexStart);
		drawGrid(dp, s.size);
		return dp;
	}

	/**
	 * Draws grid around squares so they are more easily distinguishable
	 * 'Not used currently b/c slows down computation time
	 * @param p drawingPanel
	 * @param size size of substrate
	 */
	private static void drawGrid(DrawingPanel p, Pair<Integer, Integer> size) {
		// Loop through columns and rows to draw black lines
		p.getGraphics().setBackground(Color.gray);
		p.getGraphics().setColor(Color.black);
		for(int i = 0; i <= size.t1; i++) {
			for(int j = 0; j <= size.t2; j++) {
				p.getGraphics().drawRect(i * SUBS_GRID_SIZE, j * SUBS_GRID_SIZE, (i + 1) * SUBS_GRID_SIZE, SUBS_GRID_SIZE * (j + 1));
			}
		}	
	}

	/**
	 * Draws squares representing a coordinate location from substrate
	 * @param p drawing panel
	 * @param size size of substrate
	 * @param c color of square
	 */
	private static void drawCoord(DrawingPanel p, Substrate s, ArrayList<Node> nodes, int nodeIndex) {
		p.getGraphics().setBackground(Color.gray);
		boolean sort = Parameters.parameters.booleanParameter("sortOutputActivations") && s.stype == Substrate.OUTPUT_SUBSTRATE;
		boolean biggest = Parameters.parameters.booleanParameter("showHighestActivatedOutput") && s.stype == Substrate.OUTPUT_SUBSTRATE;
		ArrayList<VisualNode> activations = new ArrayList<VisualNode>(); 
		for(int j = 0; j < s.size.t2; j++) {
			for(int i = 0; i < s.size.t1; i++) {
				Node node = nodes.get(nodeIndex++);
				Color c = Color.gray;
				double activation = node.output();
				if(node.ntype == TWEANN.Node.NTYPE_OUTPUT || !node.outputs.isEmpty()) {
					if(!s.isNeuronDead(i, j)) c = HyperNEATUtil.regularVisualization(activation);

				}
				activations.add(new VisualNode(activation, i, j, c, s.isNeuronDead(i, j)));//dead neurons
			}
		}
		if(sort) {
			Collections.sort(activations);
			float scale = activations.size();
			for(int i = activations.size() - 1; i >= 0 && !activations.get(i).dead; i--) {
				VisualNode vNode = activations.get(i);
				if(!vNode.dead) {
					vNode.setColor(new Color(0, i / scale, 0));
				}
			}
		}
		if(biggest) {//only shows biggest neuron 
			Collections.sort(activations);
			int currentIndex = activations.size() - 1;
			while(activations.get(activations.size() - 1).activation == activations.get(currentIndex).activation) {
				activations.get(currentIndex).setColor(Color.MAGENTA);
				currentIndex--;
			}
		}  




		for(VisualNode vn : activations) {
			vn.drawNode(p);
		}
	}

	/**
	 * Compresses values from -1 to 1 and then returns a color where -1 is blue, 0 is black and 1 is red.
	 * 
	 * @param activation value to convert
	 * @return color of value
	 */
	public static Color regularVisualization(double activation) { 
		activation = Math.max(-1, Math.min(activation, 1.0));// For unusual activation functions that go outside of the [-1,1] range
		return new Color(activation > 0 ? (int)(activation*255) : 0, 0, activation < 0 ? (int)(-activation*255) : 0);

	}


	/**
	 * Draws the weights of the links between nodes in substrate layers to a series of drawing panels, with
	 * one panel per connection of substrates
	 * @param genotype genotype of network to be drawn
	 * @param hnt hyperNEAT task
	 * @return the weight panels
	 */
	public static ArrayList<DrawingPanel> drawWeight(TWEANNGenotype genotype, HyperNEATTask hnt) {
		
		//gets all relevant information needed to draw link weights
		tg = (TWEANNGenotype)genotype.copy();
		connections = hnt.getSubstrateConnectivity();
		nodes = tg.nodes;
		//disposes of weight panels if already instantiated to clean up old panels
		if(weightPanels != null) {
			for(int i =0; i < weightPanels.size(); i++) {
				weightPanels.get(i).dispose();
			}
		}
		//instantiates panel array
		weightPanels = new ArrayList<DrawingPanel>();
		substrates = hnt.getSubstrateInformation();
		
		//used to instantiate drawing panels not on top of one another
		int weightPanelsWidth = 0;
		int weightPanelsHeight = 0;

		//creates each a panel for each connection between substrates
		for(int i = 0; i < connections.size(); i++) {
			String sub1 = connections.get(i).t1;
			String sub2 = connections.get(i).t2;
			Substrate s1 = getSubstrate(sub1);
			Substrate s2 = getSubstrate(sub2);
			//this block of code gets the substrates and their nodes and indices
			assert s1 != null && s2 != null;
			int s1StartingIndex = getSubstrateNodeStartingIndex(s1);
			int s2StartingIndex = getSubstrateNodeStartingIndex(s2);
			//actually creates panel with weights
			weightPanels.add(drawWeight(s1, s2, s1StartingIndex, s2StartingIndex));
			//sets locations of panels so they're not right on top of one another
			weightPanels.get(i).setLocation(weightPanelsWidth, weightPanelsHeight);
			weightPanelsWidth += weightPanels.get(i).getFrame().getWidth() + LINK_WINDOW_SPACING;
		}
		return weightPanels;

	}

	/**
	 * Gets the index of the first node in substrate
	 * @param sub substrate 
	 * @return index of first node in substrate
	 */
	private static int getSubstrateNodeStartingIndex(Substrate sub) {
		int nodeIndex = 0;
		for(int i = 0; i < substrates.size(); i++) {
			if(substrates.get(i).getName().equals(sub.getName())){ break;}
			
			nodeIndex += substrates.get(i).size.t1 * substrates.get(i).size.t2;
		}
		return nodeIndex;
	}

	/**
	 * gets the substrate from the list of substrates using the name
	 * @param name name of given substrate
	 * @return substrate with given name
	 */
	private static Substrate getSubstrate(String name) {
		Substrate s = null;
		for(int i = 0; i < substrates.size(); i++) {
			if(substrates.get(i).name.equals(name)) { 
				s =  substrates.get(i);
			}
		}
		return s;
	}

	/**
	 * draws the weights of the connections between s1 and s2	
	 * @param s1 substrate 1
	 * @param s2 substrate 2
	 * @param s1Index starting index of nodes in s1
	 * @param s2Index starting index of nodes in s2
	 * @return drawingPanel with drawn weights
	 */
	static DrawingPanel drawWeight(Substrate s1, Substrate s2, int s1Index, int s2Index) {
		//create new panel here
		int xCoord = 0;
		int yCoord = 0;
		int nodeVisWidth = WEIGHT_GRID_SIZE* s1.size.t1;
		int nodeVisHeight = WEIGHT_GRID_SIZE * s1.size.t2;
		int panelWidth = s2.size.t1 * nodeVisWidth  + s2.size.t1 - 1;
		int panelHeight  = s2.size.t2 * nodeVisHeight  + s2.size.t2 - 1;
		
		//instantiates panel
		DrawingPanel wPanel = new DrawingPanel(panelWidth, panelHeight, s1.getName() + "->" + s2.getName());
		wPanel.getGraphics().setBackground(Color.white);
		//for every node in s1, draws all links from it to s2
		for(int i = s2Index; i < (s2Index + (s2.size.t1 * s2.size.t2)); i++) {//goes through every node in target substrate
			//drawBorder(wPanel, xCoord, yCoord, nodeVisWidth + 2, nodeVisHeight + 2);
			drawNodeWeight(wPanel, nodes.get(i), xCoord , yCoord , s1Index, s1Index + (s1.size.t1 * s1.size.t2), nodeVisWidth, nodeVisHeight);
                        xCoord += nodeVisWidth + 1;
			if(xCoord >= panelWidth) {
				xCoord = 0;
				yCoord += nodeVisHeight + 1;
			}
   		}
		return wPanel;
	}
		
	/**
	 * get all connections of node to next substrate nodes and get all those links and then link weights
		and then paint color to drawing panel corresponding to link weight
		use same color scheme as substrate visualizer except dead links are gray
		
	 * @param dPanel drawing panel
	 * @param targetNode node to draw links from
	 * @param xCoord x coordinate to start from in drawing panel
	 * @param yCoord y coordinate to start from in drawing panel
	 * @param startingNodeIndex index of first node in second substrate
	 * @param endingNodeIndex ending index of first node in second substrate
	 * @param nodeWidth width of node 1
	 * @param nodeHeight height of node 1
	 */
	private  static void  drawNodeWeight(DrawingPanel dPanel, TWEANNGenotype.NodeGene targetNode, int xCoord, int yCoord, int startingNodeIndex, int endingNodeIndex, int nodeWidth, int nodeHeight) {
		int xLeftEdge = xCoord;
		for(int j = startingNodeIndex; j < endingNodeIndex; j++) {//goes through every node in second substrate
			Color c = Color.gray;
			TWEANNGenotype.NodeGene node = nodes.get(j);
			TWEANNGenotype.LinkGene link = tg.getLinkBetween(node.innovation, targetNode.innovation);
			if(link != null) {
                                double weight = link.weight;
				c = regularVisualization(ActivationFunctions.activation(ActivationFunctions.FTYPE_TANH, weight));
			}
			dPanel.getGraphics().setColor(c);
			dPanel.getGraphics().fillRect(xCoord, yCoord, WEIGHT_GRID_SIZE, WEIGHT_GRID_SIZE);
			xCoord += WEIGHT_GRID_SIZE;
			if(xCoord >= xLeftEdge + nodeWidth ) {
			xCoord = xLeftEdge;
			yCoord += WEIGHT_GRID_SIZE;
		}
            }
	}

}
