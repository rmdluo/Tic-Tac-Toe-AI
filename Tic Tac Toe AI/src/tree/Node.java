package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mcts.State;

public class Node {
	private State state;
	private Node parent;
	private List<Node> children;

	public Node() {
		this.state = new State();
		parent = null;
		children = new ArrayList<>();
	}

	public Node(State state) {
		this.state = state;
		parent = null;
		children = new ArrayList<>();
	}

	public Node(State state, Node parent, List<Node> children) {
		this.state = state;
		this.parent = parent;
		this.children = children;
	}

	public Node(Node node) {
		this.children = new ArrayList<>();
		this.state = new State(node.getState());
		if (node.getParent() != null)
			this.parent = node.getParent();
		List<Node> children = node.getChildren();
		for (Node child : children) {
			this.children.add(new Node(child));
		}
	}

	/**
	 * gets a random child node
	 * @return the random child
	 */
	public Node getRandomChildNode() {
		//gets a random child
		int numOfPossibleMoves = this.children.size();
		int selectRandom = (int) (Math.random() * numOfPossibleMoves);
		return this.children.get(selectRandom);
	}

	/**
	 * gets child that haves the best visit count
	 * @return the child with the best visit count
	 */
	public Node getChildWithMaxScore() {
		return Collections.max(this.children, Comparator.comparing(c -> {
			return c.getState().getVisitCount();
		}));
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
}
