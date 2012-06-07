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

		public static Matrix vectorFactory(double[] elements) {
			Node current = null;
			for (int i = elements.length - 1; i >= 0; i--) {
				if (elements[i] != 0) {
					current = new Node(elements[i], current, i);
				}
			}
			Matrix vector = new Matrix(1, elements.length);
			vector.rows[0] = current;
			return vector;
		}

		private Node[] rows;
		private int numCols;
		
		public Matrix(int rows, int cols) {
			this.rows = new Node[rows];
			this.numCols = cols;
		}
		
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

		public Matrix multiplyWithVector(Matrix vector) {
			if (vector.rows.length != 1) {
				throw new IllegalArgumentException("given matrix is not a vector!");
			}
			
			if (vector.numCols != this.numCols) {
				throw new IllegalArgumentException("given vector doesn't have the right size!");
			}
			
			Node currentResultItem, vectorItem, resultingVectorStart = null, lastResultItem = null;
			int colIndex = 0;
			double sum;

			for (Node rowItem : this.rows) {
				sum = 0;
				vectorItem = vector.rows[0];
				while (rowItem != null && vectorItem != null) {
					while (vectorItem != null
							&& vectorItem.colIndex < rowItem.colIndex) {
						vectorItem = vectorItem.next;
					}
					
					if (vectorItem != null && rowItem.colIndex == vectorItem.colIndex) {
						sum += rowItem.value * vectorItem.value;
						vectorItem = vectorItem.next;
					}

					rowItem = rowItem.next;
				}

				if (sum != 0) {
					currentResultItem = new Node(sum, null, colIndex);
					if (lastResultItem == null) {
						resultingVectorStart = currentResultItem;
					} else {
						lastResultItem.setNext(currentResultItem);
					}

					lastResultItem = currentResultItem;
				}

				colIndex++;
			}
			
			Matrix resultingVector = new Matrix(1, colIndex);
			resultingVector.rows[0] = resultingVectorStart;
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

		public String asVectorString() {
			if (this.rows.length != 1) {
				throw new IllegalArgumentException("I'm not a vector!");
			}
			
			StringBuilder sb = new StringBuilder();
			Node current = this.rows[0];
			for (int colIndex = 0; colIndex < this.numCols; colIndex++) {
				if (current != null && colIndex == current.colIndex) {
					sb.append(current);
					current = current.next;
				} else {
					sb.append("\tN");
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

		Matrix vector = Matrix.vectorFactory(new double[] { 1, 2, 3, 4, 0, 6, 7, 0 });
		System.out.println("Vector:");
		System.out.println(vector.asVectorString());

		System.out.println("Multiplication Result:");
		System.out.println(matrix.multiplyWithVector(vector).asVectorString());
	}
}