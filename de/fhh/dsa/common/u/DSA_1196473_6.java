package de.fhh.dsa.common.u;

public class DSA_1196473_6 {
	static class Matrix {
		static class Node {
			protected double value;
			protected Node next;
			protected int colIndex;

			protected Node(double value, Node next, int colIndex) {
				this.value = value;
				this.next = next;
				this.colIndex = colIndex;
			}

			protected void setNext(Node next) {
				this.next = next;
			}
			
			@Override
			public String toString() { 
				return String.format("\t%.2f", this.value); 
			}
			
			public String asString() {
				return String.format("<Node value=%f colIndex=%d>", this.value,
						this.colIndex);
			}
		}
		
		public static Node vectorFactory(double[] elements) {
			Node current = null;
			for (int i = elements.length-1; i >= 0; i--) {
				if (elements[i] != 0) {
					current = new Node(elements[i], current, i);
				}
			}
			
			return current;
		}

		private Node[] rows;
		private int numCols;

		public Matrix(int rows, int cols, int k) {
			this.rows = new Node[rows];
			this.numCols = cols;
			Node node;
			Node lastNode = null;
			double value;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					value = (j % k) * (i % Math.floor(Math.sqrt(j)));
					if (j != 0 && value != 0) {
						node = new Node(value, null, j);
						if (this.rows[i] == null) {
							this.rows[i] = node;
							lastNode = null;
						} else if (lastNode == null) {
							this.rows[i].setNext(node);
						} else {
							node.setNext(node);
						}
					}
				}
			}
		}
		
		public Node multiplyWithVector(Node vector) {			
			Node currentResultItem, vectorItem,
				 resultingVector = null, 
				 lastResultItem = null;
			int colIndex = 0;
			double sum;
			
			for (Node row : this.rows) {
				sum = 0;
				vectorItem = vector;
				while (row != null && vectorItem != null) {
					while (vectorItem != null && vectorItem.colIndex < row.colIndex) {
						vectorItem = vectorItem.next;
					}
					
					if (row.colIndex == vectorItem.colIndex) {
						sum += row.value*vectorItem.value; 
						vectorItem = vectorItem.next;
					}
					
					row = row.next;
				}
				
				if (sum != 0) {
					currentResultItem = new Node(sum, null, colIndex);
					if (lastResultItem == null) {
						resultingVector = currentResultItem;
					} else {
						lastResultItem.setNext(currentResultItem);
					}
					
					lastResultItem = currentResultItem;
				}
				
				colIndex++;
			}
			
			return resultingVector;
		}
		
		
		public void addMatrix(Matrix b) {
			addOrSubstractMatrix(b, false);
		}

		public void subMatrix(Matrix b) {
			addOrSubstractMatrix(b, true);
		}

		private void addOrSubstractMatrix(Matrix b, boolean negation) {
			if (this.rows.length != b.rows.length || this.numCols != b.numCols) {
				throw new IllegalArgumentException(
						"other matrix is not of the same type!");
			}
			
			Node currentOwnNode, currentOtherNode, previousOwnNode, nextOtherNode;
			
			for (int row = 0; row < this.rows.length; row++) {
				currentOwnNode = this.rows[row];
				if (currentOwnNode == null) {
					this.rows[row] = b.rows[row];
					continue;
				}
				
				previousOwnNode = null;
				currentOtherNode = b.rows[row];
				while (currentOtherNode != null) {
					if (negation) {
						currentOtherNode.value = -currentOtherNode.value;
					}
					
					while (currentOtherNode.colIndex > currentOwnNode.colIndex 
							|| currentOwnNode == null) {
						previousOwnNode = currentOwnNode;
						currentOwnNode = currentOwnNode.next;
					}
					
					nextOtherNode = currentOtherNode.next;
					if (currentOwnNode.colIndex == currentOtherNode.colIndex) {
						currentOwnNode.value += currentOtherNode.value;
						
						if (currentOwnNode.value == 0) {
							if (previousOwnNode == null) {
								this.rows[row] = currentOwnNode.next;
							} else {
								previousOwnNode.setNext(currentOwnNode.next);
							}
						}
						
						previousOwnNode = currentOwnNode;
						currentOwnNode = currentOwnNode.next;
					} else {
						if (currentOwnNode == this.rows[row]) {
							this.rows[row] = currentOtherNode;
							currentOtherNode.setNext(currentOwnNode);
						} else {
							previousOwnNode.setNext(currentOtherNode);
							currentOtherNode.setNext(currentOwnNode);
						}
						
					}
					currentOtherNode = nextOtherNode;
				}
			}
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (Node currentNode : this.rows) {
				for (int col = 0; col < this.numCols; col++) {
					if (currentNode != null && col == currentNode.colIndex) {
						sb.append(currentNode.toString());
						sb.append(" ");
						currentNode = currentNode.next;
					} else {
						sb.append("\tN");
					}
				}

				sb.append("\n");
			}

			return sb.toString();
		}
		
		public String vectorToString(Node vector) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < this.numCols; i++) {
				if (vector != null && vector.colIndex == i) {
					sb.append(vector.value);
					vector = vector.next;
				} else {
					sb.append("N");
				}
				sb.append("\n");
			}
			
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		Matrix matrix = new Matrix(4, 8, 5);
		Matrix matrixToAdd = new Matrix(4, 8, 2);
		Matrix matrixToSub = new Matrix(4, 8, 3);
		System.out.println("Base:");
		System.out.println(matrix);
		System.out.println("To Sub:");
		System.out.println(matrixToSub);
		System.out.println("After sub:");
		matrix.subMatrix(matrixToSub);
		System.out.println(matrix);
		System.out.println("To add:");
		System.out.println(matrixToAdd);
		System.out.println("After add:");
		matrix.addMatrix(matrixToAdd);
		System.out.println(matrix);
		
		Matrix.Node vector = Matrix.vectorFactory(new double[]{1, 2, 3, 4, 0, 6, 7, 8});
		System.out.println("Vector:");
		System.out.println(matrix.vectorToString(vector));
		
		System.out.println("Multiplication Result:");
		System.out.println(matrix.vectorToString(matrix.multiplyWithVector(vector)));
	}
}