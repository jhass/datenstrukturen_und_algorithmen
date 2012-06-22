package de.fhh.dsa.common.u;

/** Binärer Suchbaum (AVL)
*
* @author Richard Pump <richard.pump@stud.fh-hannover.de> (1195429)
* @author Jonne Haß <jonne.hass@stud.fh-hannover.de> (1196473)
*
*/
public class DSA_1196473_7 {
	
	static class BinarySearchTree {
		interface TraverseCallback {
			public void action(TreeNode current);
		}
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
			
			public boolean hasNoLeftSide() {
				return this.leftChild == null;
			}
			
			public boolean hasNoRightSide() {
				return this.rightChild == null;
			}
			
			public int getHeight() {
				int leftHeight = 0, rightHeight = 0;
				if (this.hasLeftChild()) {
					System.out.println(this+" has left child: "+this.leftChild);
					leftHeight = this.leftChild.getHeight();
				}
				
				if (this.hasRightChild()) {
					System.out.println(this+" has right child: "+this.rightChild);
					rightHeight = this.rightChild.getHeight();
				}
				
				return Math.max(leftHeight, rightHeight)+1;
			}
			
			public int getBalanceFactor() {
				int leftHeight = 0, rightHeight = 0;
				
				if (this.hasLeftChild()) {
					leftHeight = this.leftChild.getHeight();
				}
				
				if (this.hasRightChild()) {
					rightHeight = this.rightChild.getHeight();
				}
				
				return rightHeight-leftHeight;
			}
			
			public void rotateRight() {
				TreeNode tmp = this.leftChild;
				this.leftChild = tmp.rightChild;
				tmp.rightChild = this;
				
				if (this.isLeftChild()) {
					this.parent.leftChild = tmp;
				} else if (this.isRightChild()) {
					this.parent.rightChild = tmp;
				}
				
				tmp.parent = this.parent;
				this.parent = tmp;
				this.fixChildParents();
				tmp.fixChildParents();
			}
			
			public void rotateLeft() { 
				TreeNode tmp = this.rightChild;
				this.rightChild = tmp.leftChild;
				tmp.leftChild = this;
				
				if (this.isLeftChild()) {
					this.parent.leftChild = tmp;
				} else if (this.isRightChild()) {
					this.parent.rightChild = tmp;
				}
				
				tmp.parent = this.parent;
				this.parent = tmp;
				this.fixChildParents();
				tmp.fixChildParents();
			}
			
			private void fixChildParents() {
				if (this.hasLeftChild()) {
					this.leftChild.parent = this;
				}
				if (this.hasRightChild()) {
					this.rightChild.parent = this;
				}
			}
			
			public void rotateLeftRight() {
				this.leftChild.rotateLeft();
				this.rotateRight();
			}
			
			public void rotateRightLeft() {
				this.rightChild.rotateRight();
				this.rotateLeft();
			}
			
			@Override
			public String toString() {
				return Integer.toString(this.value);
			}
		}
		
		private TreeNode root;
		private boolean prematureWiring;
		
		public BinarySearchTree(int[] values) {
			this(values, true);
		}
		
		public BinarySearchTree(int[] values, boolean prematureWiring) {
			this.prematureWiring = prematureWiring;
			
			for (int value : values) {
				this.insert(new TreeNode(value));
			}
			
		}
		
		public void disablePrematureWiring() {
			this.prematureWiring = false;
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
							if (this.prematureWiring) {
								node.rightChild = parent.rightChild;
								node.rightIsWire = true;
								
								parent.rightIsWire = false;
								
								node.leftChild = parent;
								node.leftIsWire = true;
							}
							
							parent.rightChild = node;
							break;
						}
					} else { // links einfügen
						if (parent.hasLeftChild()) {
							parent = parent.leftChild;
						} else {
							if (this.prematureWiring) {
								node.leftChild = parent.leftChild;
								node.leftIsWire = true;
								
								parent.leftIsWire = false;
								
								node.rightChild = parent;
								node.rightIsWire = true;
							}
							
							parent.leftChild = node;
							break;
						}
					}
				}
				
				node.parent = parent;
				this.balanceIfNecessary(parent);
			} else {
				this.root = node;
				node.parent = null;
			}
		}
		
		private boolean initialized() {
			return this.root != null;
		}
		
		private void balanceIfNecessary(TreeNode node) {
			if (node.getBalanceFactor() == 2) {
				if (node.hasRightChild() && node.rightChild.getBalanceFactor() == -2) {
					node.rotateRightLeft();
				} else {
					node.rotateLeft();
				}
			} else if (node.getBalanceFactor() == -2) {
				if (node.hasLeftChild() && node.leftChild.getBalanceFactor() == 2) {
					node.rotateLeftRight();
				} else {
					node.rotateRight();
				}
			}
			
			if (node.parent == null) {
				this.root = node;
			} else {
				balanceIfNecessary(node.parent);
			}
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
				if (this.prematureWiring)
					node.parent.rightIsWire = node.rightIsWire;
			} else {
				node.parent.leftChild = newChild;
				if (this.prematureWiring)
					node.parent.leftIsWire = node.leftIsWire;
			}
			
			if (this.prematureWiring) {
				final TreeNode refNode = node;
				traverse(node, new TraverseCallback() {
					public void action(TreeNode current) {
						fixDeadReference(refNode, current);
					}
				});
			}
		}
		
		private void traverse(TreeNode current, TraverseCallback callback) {
			TreeNode previous;
			do {
				while (!current.leftIsWire) {
					current = current.leftChild;
				}
				
				callback.action(current);
				
				previous = current;
				current = current.rightChild;
				while (previous.rightIsWire && current != null) {
					callback.action(current);
					
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
		
		
		public void generateInOrderWire() {
			recursiveTraverse(this.root, new TraverseCallback() {
				private TreeNode previous;
				public void action(TreeNode current) {
					if (current.hasNoLeftSide()) {
						if (previous != null) {
							current.leftChild = previous;
						}
						current.leftIsWire = true;
					}
					
					if (previous != null && previous.hasNoRightSide()) {
						previous.rightChild = current;
						previous.rightIsWire = true;
					}
					
					previous = current;
				}
			});
		}
		
		private void recursiveTraverse(TreeNode node, TraverseCallback callback) {
			if (node.hasLeftChild()) {
				recursiveTraverse(node.leftChild, callback);
			}
			
			callback.action(node);
			
			if (node.hasRightChild()) {
				recursiveTraverse(node.rightChild, callback);
			}
		}
		
		public void printInOrder() {
			System.out.print(this);
		}
		
		public void recursivePrintInOrder() {
			final StringBuilder sb = new StringBuilder();
			recursiveTraverse(this.root, new TraverseCallback() {
				public void action(TreeNode current) {
					sb.append(current);
					sb.append(" ");
				}
			});
			System.out.println(sb.toString());
		}
		
		@Override
		public String toString() {
			return toInOrderString();
		}
		
		private String toInOrderString() {
			final StringBuilder sb = new StringBuilder();
			
			traverse(this.root, new TraverseCallback() {
				public void action(TreeNode current) {
					sb.append(current);
					sb.append(" ");
				}
			});
			
			return sb.toString();
		}
		
		public int getHeight() {
			return this.root.getHeight();
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("Baum:");
		BinarySearchTree tree = new BinarySearchTree(
			new int[]{
				186, 152, 203, 187, 290, 60, 130, 273, 66, 239, 187, 105, 9,
				229, 245, 239, 20, 40, 33, 85, 300, 295, 302
			}, false);
		tree.recursivePrintInOrder();
		
		System.out.println("Suchergebniss nach 187:");
		System.out.println(tree.search(187));
		
		System.out.println("Baum nach einfügen von 23:");
		tree.insert(new BinarySearchTree.TreeNode(23));
		tree.recursivePrintInOrder();
		
		System.out.println("Baum nach löschen von 152:");
		tree.delete(tree.search(152));
		tree.recursivePrintInOrder();
		
		System.out.println("Höhe des aktuellen Baumes:");
		System.out.println(tree.getHeight());
		
		System.out.println("Baum nach 'InOrder-Wire'en iterativ ausgegeben:");
		tree.generateInOrderWire();
		tree.printInOrder();
	}
}