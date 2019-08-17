package mcts;

import java.util.Collections;
import java.util.Comparator;

import tree.Node;

public class UCT {
	/**
	 * calculates UCT from given values
	 * @param totalVisit - The visit count of the parent node
	 * @param nodeWinScore - The win score of the node
	 * @param nodeVisit - The visit count of the node
	 * @return the UCT value
	 */
	public static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
		//if the node has not been visited, then give it a high value so that it will be visited
		if (nodeVisit == 0) {
			return Integer.MAX_VALUE;
		}
		
		//return the UCT value
		return (nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
	}

	/**
	 * finds node with best UCT
	 * @param node - the parent node
	 * @return the child node with the best UCT
	 */
	public static Node findBestNodeWithUCT(Node node) {
		//gets the parent node's visit count
		int parentVisit = node.getState().getVisitCount();
		
		//calculates and compares the UCT values
		return Collections.max(node.getChildren(), Comparator
				.comparing(c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount())));
	}
}
