package edu.utexas.cs.nn.evolution.genotypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.utexas.cs.nn.MMNEAT.MMNEAT;
import edu.utexas.cs.nn.networks.NetworkUtil;
import edu.utexas.cs.nn.networks.TWEANN;
import edu.utexas.cs.nn.networks.hyperneat.HyperNEATTask;
import edu.utexas.cs.nn.networks.hyperneat.Substrate;
import edu.utexas.cs.nn.parameters.CommonConstants;
import edu.utexas.cs.nn.util.datastructures.Pair;
import edu.utexas.cs.nn.util.util2D.ILocated2D;
import edu.utexas.cs.nn.util.util2D.Tuple2D;

/**
 * genotype for a hyperNEAT CPPN network
 *
 * @author gillespl
 *
 */
public class HyperNEATCPPNGenotype extends TWEANNGenotype {

	public static int numCPPNOutputsPerLayerPair = 1;
	public static final int LINK_INDEX = 0;
	public static int biasIndex = 0;
	public static int leoIndex = 0;
	public static boolean constructingNetwork = false;
	public static final double BIAS = 1.0;// Necessary for most CPPN networks
	public int innovationID = 0;// provides unique innovation numbers for links and genes

	/**
	 * Default constructor
	 */
	public HyperNEATCPPNGenotype() {
		super();
	}

	/**
	 * Used by TWEANNCrossover
	 * 
	 * @param nodes new node genes
	 * @param links new link genes
	 * @param neuronsPerModule effectively the number of output neurons
	 * @param archetypeIndex archetype to use
	 */
	public HyperNEATCPPNGenotype(ArrayList<NodeGene> nodes, ArrayList<LinkGene> links, int neuronsPerModule, int archetypeIndex) {
		super(nodes, links, neuronsPerModule, false, false, archetypeIndex);
	}	

	/**
	 * Constructor for hyperNEATCPPNGenotype. Uses super constructor from
	 * TWEANNGenotype
	 * 
	 * @param links
	 *            list of links between genes
	 * @param genes
	 *            list of nodes in genotype
	 * @param outputNeurons
	 *            number of output neurons
	 */
	public HyperNEATCPPNGenotype(ArrayList<LinkGene> links, ArrayList<NodeGene> genes, int outputNeurons) {
		super(genes, links, outputNeurons, false, false, 0);
	}

	/**
	 * Constructor for random hyperNEATCPPNGenotype.
	 * 
	 * @param networkInputs
	 *            number of network inputs
	 * @param networkOutputs
	 *            number of network outputs
	 * @param archetypeIndex
	 *            index of genotype in archetype
	 */
	public HyperNEATCPPNGenotype(int networkInputs, int networkOutputs, int archetypeIndex) {
		// Construct new CPPN with random weights
		super(networkInputs, networkOutputs, archetypeIndex); 
	}

	public TWEANN getCPPN() {
		return super.getPhenotype();
	}

	/**
	 * Uses another CPPN to create a TWEANN controller for the domain. This
	 * created TWEANN is unique only to the instance in which it is used. In a
	 * sense, it's a one-and-done network, which explains the lax use of
	 * innovation numbers
	 *
	 * @return TWEANN generated by CPPN
	 */
	@Override
	public TWEANN getPhenotype() {
		TWEANNGenotype tg = getSubstrateGenotype((HyperNEATTask) MMNEAT.task) ;
		return tg.getPhenotype();//return call to substrate genotype
	}

        /**
         * Use the CPPN to construct a genotype that encodes the substrate
         * network, and return that genotype. This genotype can be used to
         * get the substrate network phenotype, which is actually evaluated
         * in any given domain.
         * 
         * Having the genotype of the substrate genotype allows access to 
         * network components using methods of the genotype, which are sometimes
         * more flexible than the methods of the network itself.
         * 
         * @return genotype that encodes a substrate network generated by a CPPN
         */
	public TWEANNGenotype getSubstrateGenotype(HyperNEATTask hnt) {
		constructingNetwork = true; // prevent displaying of substrates
		//long time = System.currentTimeMillis(); // for timing
		TWEANN cppn = getCPPN();// CPPN used to create TWEANN network
		List<Substrate> subs = hnt.getSubstrateInformation();// extract substrate information from domain
		List<Pair<String, String>> connections = hnt.getSubstrateConnectivity();// extract substrate connectivity from domain
		ArrayList<NodeGene> newNodes = null;
		ArrayList<LinkGene> newLinks = null;
		innovationID = 0;// reset each time a phenotype is generated
		int phenotypeOutputs = 0;

		newNodes = createSubstrateNodes(cppn, subs);
		// Will map substrate names to index in subs List
		// needs to be switched
		HashMap<String, Integer> substrateIndexMapping = new HashMap<String, Integer>();
		for (int i = 0; i < subs.size(); i++) {
			substrateIndexMapping.put(subs.get(i).getName(), i);
		}
		// loop through connections and add links, based on contents of subs
		newLinks = createNodeLinks(cppn, connections, subs, substrateIndexMapping);
		// Figure out number of output neurons
		for (Substrate s : subs) {
			if (s.getStype() == Substrate.OUTPUT_SUBSTRATE) {
				phenotypeOutputs += s.size.t1 * s.size.t2;
			}
		}		
		constructingNetwork = false;
		
		// the instantiation of the TWEANNgenotype in question

		// Hard coded to have a single neural output module.
		// May need to fix this down the line.
		// An archetype index of -1 is used. Hopefully this won't cause
		// problems, since the archetype is only needed for mutations and crossover.
		TWEANNGenotype tg = new TWEANNGenotype(newNodes,newLinks, phenotypeOutputs, false, false, -1);
                return tg;
	}
	
	public TWEANNGenotype getSubstrateGenotypeForEvolution(HyperNEATTask hnt) {
		TWEANNGenotype tg = getSubstrateGenotype(hnt);
		tg.archetypeIndex = 0;
		//doesn't seem we need this code but need to evolve a few generations to see if it crashes when crossover and mutation occurs
		//if crash does occur, need to change link node innovation numbers as well
//		long counter = -1;//is this right???
//		for(int i = 0; i < tg.nodes.size(); i++) {
//			if(tg.nodes.get(i).ntype == Node.NTYPE_INPUT || tg.nodes.get(i).ntype == Node.NTYPE_OUTPUT) {
//				tg.nodes.get(i).innovation = counter--;
//			}
//		}
		return tg;
	}
	
	/**
	 * Copies given genotype
	 * 
	 * @return Copy of the CPPN genotype
	 */
	@Override
	public Genotype<TWEANN> copy() {
		int[] temp = moduleUsage; // Schrum: Not sure if keeping moduleUsage is appropriate
		ArrayList<LinkGene> linksCopy = new ArrayList<LinkGene>(this.links.size());
		for (LinkGene lg : this.links) {// needed for a deep copy
			linksCopy.add(new LinkGene(lg.sourceInnovation, lg.targetInnovation, lg.weight, lg.innovation, false));
		}

		ArrayList<NodeGene> genes = new ArrayList<NodeGene>(this.nodes.size());
		for (NodeGene ng : this.nodes) {// needed for a deep copy
			genes.add(new NodeGene(ng.ftype, ng.ntype, ng.innovation, false, ng.bias));
		}
		HyperNEATCPPNGenotype result = new HyperNEATCPPNGenotype(linksCopy, genes, MMNEAT.networkOutputs);

		// Schrum: Not sure if keeping moduleUsage is appropriate
		moduleUsage = temp;
		result.moduleUsage = new int[temp.length];
		System.arraycopy(this.moduleUsage, 0, result.moduleUsage, 0, moduleUsage.length);
		return result;
	}

	/**
	 * creates an array list containing all the nodes from all the substrates
	 *
	 * @param cppn
	 *             CPPN that produces phenotype network
	 * @param subs
	 *            list of substrates extracted from domain
	 * @return array list of NodeGenes from substrates
	 */
	public ArrayList<NodeGene> createSubstrateNodes(TWEANN cppn, List<Substrate> subs) {
		ArrayList<NodeGene> newNodes = new ArrayList<NodeGene>();
		// loops through substrate list
		for (int i = 0; i < subs.size(); i++) {
			for (int y = 0; y < subs.get(i).size.t2; y++) {
				for (int x = 0; x < subs.get(i).size.t1; x++) {
					// Substrate types and Neuron types match and use same values
					double bias = 0.0; // default
					// Non-input substrates can have a bias if desired
					if(CommonConstants.evolveHyperNEATBias && subs.get(i).stype != Substrate.INPUT_SUBSTRATE) {
						// Ask CPPN to generate a bias for each neuron
						bias = cppn.process(new double[]{0, 0, x, y, BIAS})[biasIndex];
					}
					newNodes.add(new NodeGene(CommonConstants.ftype, subs.get(i).getStype(), innovationID++, false, bias));
				}
			}
		}
		return newNodes;
	}

	/**
	 * creates an array list of links between substrates as dictated by
	 * connections parameter
	 *
	 * @param cppn
	 *            used to evolve link weight
	 * @param connections
	 *            list of different connections between substrates
	 * @param subs
	 *            list of substrates in question
	 * @param sIMap
	 *            hashmap that maps the substrate in question to its index in
	 *            the substrate list
	 *
	 * @return array list containing all the links between substrates
	 */
	public ArrayList<LinkGene> createNodeLinks(TWEANN cppn, List<Pair<String, String>> connections, List<Substrate> subs, HashMap<String, Integer> sIMap) {
		ArrayList<LinkGene> result = new ArrayList<LinkGene>();
		for (int i = 0; i < connections.size(); i++) {
			int sourceSubstrateIndex = sIMap.get(connections.get(i).t1);
			int targetSubstrateIndex = sIMap.get(connections.get(i).t2);
			Substrate sourceSubstrate = subs.get(sourceSubstrateIndex);
			Substrate targetSubstrate = subs.get(targetSubstrateIndex);
			// adds links from between two substrates to whole list of links
			loopThroughLinks(result, cppn, i, sourceSubstrate, targetSubstrate, sourceSubstrateIndex, targetSubstrateIndex, subs);
		}
		return result;
	}

	/**
	 * a method for looping through all nodes of two substrates to be linked
	 * Link is only created if CPPN output reaches a certain threshold that is
	 * dictated via command line parameter.
	 *
	 * @param linksSoFar
	 * 			  All added links are accumulated in this list
	 * @param cppn
	 *            used to evolve link weight
	 * @param outputIndex
	 *            index from cppn outputs to be used as weight in creating link
	 * @param s1
	 *            first substrate to be linked
	 * @param s2
	 *            second substrate to be linked
	 * @param s1Index
	 *            index of first substrate in substrate list
	 * @param s2Index
	 *            index of second substrate in substrate list
	 * @param subs
	 *            list of substrates
	 *
	 */
	public void loopThroughLinks(ArrayList<LinkGene> linksSoFar, TWEANN cppn, int outputIndex, Substrate s1, Substrate s2, int s1Index, int s2Index, List<Substrate> subs) {
		// searches through width of first substrate
		for (int X1 = 0; X1 < s1.size.t1; X1++) {
			// searches through height of first substrate
			for (int Y1 = 0; Y1 < s1.size.t2; Y1++) {
				if(!s1.isNeuronDead(X1, Y1)) {
					// searches through width of second substrate
					for (int X2 = 0; X2 < s2.size.t1; X2++) {
						// searches through height of second substrate
						for (int Y2 = 0; Y2 < s2.size.t2; Y2++) {
							if(!s2.isNeuronDead(X2, Y2)) {
								// CPPN inputs need to be centered and scaled
								ILocated2D scaledSourceCoordinates = MMNEAT.substrateMapping.transformCoordinates(new Tuple2D(X1, Y1), s1.size.t1, s1.size.t2);
								ILocated2D scaledTargetCoordinates = MMNEAT.substrateMapping.transformCoordinates(new Tuple2D(X2, Y2), s2.size.t1, s2.size.t2);
								// inputs to CPPN 
								double[] inputs = {scaledSourceCoordinates.getX(), scaledSourceCoordinates.getY(), scaledTargetCoordinates.getX(), scaledTargetCoordinates.getY(), BIAS};
								double[] outputs = cppn.process(inputs);
								boolean expressLink = CommonConstants.leo
										// Specific network output determines link expression
										? outputs[(numCPPNOutputsPerLayerPair * outputIndex) + leoIndex] > CommonConstants.linkExpressionThreshold
										// Output magnitude determines link expression
										: Math.abs(outputs[(numCPPNOutputsPerLayerPair * outputIndex) + LINK_INDEX]) > CommonConstants.linkExpressionThreshold;
								if (expressLink) {
									long sourceID = getInnovationID(X1, Y1, s1Index, subs);
									long targetID = getInnovationID(X2, Y2, s2Index, subs);
									double weight = CommonConstants.leo
													// LEO takes its weight directly from the designated network output
													? outputs[(numCPPNOutputsPerLayerPair * outputIndex) + LINK_INDEX]
													// Standard HyperNEAT must scale the weight
													: NetworkUtil.calculateWeight(outputs[(numCPPNOutputsPerLayerPair * outputIndex) + LINK_INDEX]);
									linksSoFar.add(new LinkGene(sourceID, targetID, weight, innovationID++, false));
								}

							}
						}
					}
				}
			}
		}
	}


	/**
	 * Given the substrate coordinates and sizes that a particular link is supposed to connect,
	 * determine the index it should be located at in the cached link gene list.
	 * @param X1 x coordinate in first substrate
	 * @param Y1 y coordinate in first substrate
	 * @param X2 x coordinate in second substrate
	 * @param Y2 y coordinate in second substrate
	 * @param height1 height (y dimension) of first substrate
	 * @param width2 width (x dimension) of second substrate
	 * @param height2 height (y dimension) of second substrate
	 * @return index in cachedPhenotypeLinks of link gene
	 */
	// This method was only used in a failed attempt to speed up HyperNEAT by caching link gene information,
	// and replacing the weights when needed. This method helped figure out which weight to replace, but only
	// works if normally unexpressed links actually are expressed, but with a weight of 0.0
	//	public static int linkGeneIndex(int X1, int Y1, int X2, int Y2, int height1, int width2, int height2) {
	//		return X1*height1*height2*width2 + Y1*height2*width2 + X2*height2 + Y2;
	//	}

	/**
	 * returns the innovation id of the node in question
	 *
	 * @param x
	 *            x-coordinate of node
	 * @param y
	 *            y-coordinate of node
	 * @param sIndex
	 *            index of substrate in question
	 * @param subs
	 *            list of substrates available
	 *
	 * @return innovationID of link in question
	 */
	public long getInnovationID(int x, int y, int sIndex, List<Substrate> subs) {
		long innovationIDAccumulator = 0;
		for (int i = 0; i < sIndex; i++) {
			Substrate s = subs.get(i);
			innovationIDAccumulator += s.size.t1 * s.size.t2;
		}
		innovationIDAccumulator += (subs.get(sIndex).size.t1 * y) + x;
		return innovationIDAccumulator;
	}



	/**
	 * Creates a new random instance of the hyperNEATCPPNGenotype
	 * @return New genotype for CPPN
	 */
	@Override
	public Genotype<TWEANN> newInstance() {
		HyperNEATCPPNGenotype result = new HyperNEATCPPNGenotype(MMNEAT.networkInputs, MMNEAT.networkOutputs, this.archetypeIndex);
		result.moduleUsage = new int[result.numModules];
		return result;
	}

}
