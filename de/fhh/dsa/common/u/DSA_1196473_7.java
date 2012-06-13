package de.fhh.dsa.common.u;

public class DSA_1196473_7 {
	static class BinarySearchTree {
		static class TreeNode {
			protected int value;
			protected TreeNode parent;
			protected TreeNode leftChild;
			protected TreeNode rightChild;
			protected boolean leftIsWire;
			protected boolean rightIsWire;
			
			public TreeNode(int value) {
				this.value = value;
				this.leftIsWire = false;
				this.rightIsWire = false;
			}
			
			public boolean hasChilds() {
				return hasLeftChild() || hasRightChild();
			}
			
			public boolean hasBothChilds() {
				return hasLeftChild() && hasRightChild();
			}
			
			public boolean hasLeftChild() {
				return this.leftChild != null && !this.leftIsWire;
			}
			
			public boolean hasRightChild() {
				return this.rightChild != null && !this.rightIsWire;
			}
			
			public boolean isRoot() {
				return this.parent == null;
			}
			
			public boolean isLeftChild() {
				return !isRoot() && !isRightChild();
			}
			
			public boolean isRightChild() {
				return !isRoot() && this.value > this.parent.value;
			}
			
			@Override
			public String toString() {
				return Integer.toString(this.value);
			}
		}
		
		private TreeNode root;
		
		public BinarySearchTree(int[] values) {
			for (int value : values) {
				this.insert(new TreeNode(value));
			}
		}
		
		public TreeNode search(int value) {
			TreeNode candidate = this.root;
			while (candidate != null) {
				if (candidate.value == value) {
					break;
				} else if (value > candidate.value) {
					candidate = candidate.rightChild;
				} else {
					candidate = candidate.leftChild;
				}
			}
			
			return candidate;
		}
		
		public void insert(TreeNode node) {
			if (initialized()) {
				TreeNode parent = this.root;
				while (true) {
					if (node.value > parent.value) { // rechts einfügen
						if (parent.hasRightChild()) {
							parent = parent.rightChild;
						} else {
							node.rightChild = parent.rightChild;
							node.rightIsWire = true;
							
							parent.rightChild = node;
							parent.rightIsWire = false;
							
							node.leftChild = parent;
							node.leftIsWire = true;
							
							break;
						}
					} else { // links einfügen
						if (parent.hasLeftChild()) {
							parent = parent.leftChild;
						} else {
							node.leftChild = parent.leftChild;
							node.leftIsWire = true;
							
							parent.leftChild = node;
							parent.leftIsWire = false;
							
							node.rightChild = parent;
							node.rightIsWire = true;
							
							break;
						}
					}
				}
				
				node.parent = parent;
			} else {
				this.root = node;
			}
		}
		
		private boolean initialized() {
			return this.root != null;
		}
		
		public void delete(TreeNode node) {
			if (node.hasBothChilds()) {
				TreeNode smallestRightChild = node.rightChild;
				while (smallestRightChild.hasLeftChild()) {
					smallestRightChild = smallestRightChild.leftChild;
				}
				
				node.value = smallestRightChild.value;
				deleteSingleChildNode(smallestRightChild);
			} else {
				deleteSingleChildNode(node);
			}
		}
		
		private void deleteSingleChildNode(TreeNode node) {
			TreeNode newChild = null;
			if (node.hasLeftChild()) {
				newChild = node.leftChild;
			} else if (node.hasRightChild()) {
				newChild = node.rightChild;
			}
			
			if (node.isRightChild()) {
				node.parent.rightChild = newChild;
				node.parent.rightIsWire = node.rightIsWire;
			} else {
				node.parent.leftChild = newChild;
				node.parent.leftIsWire = node.leftIsWire;
			}
			
			TreeNode current = node;
			TreeNode previous;
			do {
				while (!current.leftIsWire) {
					current = current.leftChild;
				}
				
				fixDeadReference(node, current);
				
				previous = current;
				current = current.rightChild;
				while (previous.rightIsWire && current != null) {
					fixDeadReference(node, current);
					
					previous = current;
					current = current.rightChild;
				}
			} while (current != null);
		}
		
		private void fixDeadReference(TreeNode referencedNode, TreeNode node) {
			if (node.leftIsWire && node.leftChild == referencedNode) {
				node.leftChild = referencedNode.parent;
			}
			
			if (node.rightIsWire && node.rightChild == referencedNode) {
				node.rightChild = referencedNode.parent;
			}
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			
			TreeNode current = this.root;
			TreeNode previous;
			do {
				while (!current.leftIsWire) {
					current = current.leftChild;
				}
				
				sb.append(current);
				sb.append(" ");
				
				previous = current;
				current = current.rightChild;
				while (previous.rightIsWire && current != null) {
					sb.append(current);
					sb.append(" ");
					previous = current;
					current = current.rightChild;
				}
			} while (current != null);
			
			return sb.toString();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("Baum:");
		BinarySearchTree tree = new BinarySearchTree(new int[]{186, 152, 203, 187, 290, 60, 130, 273, 66, 239, 187, 105, 9, 229, 245, 239, 20, 40, 33, 85, 300, 295, 302});//new BinarySearchTree(new int[]{3,57,83,2,45,323,1,2});
		System.out.println(tree);
		System.out.println("Suchergebniss nach 187:");
		System.out.println(tree.search(187));
		System.out.println("Baum nach einfügen von 23:");
		tree.insert(new BinarySearchTree.TreeNode(23));
		System.out.println(tree);
		System.out.println("Baum nach löschen von 152:");
		tree.delete(tree.search(152));
		System.out.println(tree);
	}
}
