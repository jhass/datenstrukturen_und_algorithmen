package de.fhh.dsa.common.u;

public class DSA_1196473_5 {
	static class SinglyLinkedList<NodeType extends Comparable<NodeType>> {
		class Node<ValueType extends Comparable<ValueType>> {
			protected ValueType value;
			protected Node<ValueType> next;
			protected Node(ValueType value, Node<ValueType> nextNode) {
				this.value = value;
				this.setNext(nextNode);
			}
			
			protected void setNext(Node<ValueType> node) {
				this.next = node;
			}
			
			@Override
			public String toString() {
				return this.value.toString();
			}
		}
		
		private Node<NodeType> head;
		private Node<NodeType> tail;
		
		public SinglyLinkedList(){
			this.head = new Node<NodeType>(null, null);
			this.tail = new Node<NodeType>(null, this.head);
			this.head.setNext(this.tail); 
		}
		
		public Node<NodeType> locate(NodeType value) {
			if (value == null) {
				return this.head;
			} else {
				return locate(value, this.head);
			}
		}
		
		private Node<NodeType> locate(NodeType value, Node<NodeType> current) {
			if (current.next.value.equals(value)) {
				return current.next;
			} else if (current.next.next == current) {
				return null;
			} else {
				return this.locate(value, current.next);
			}
		}
		
		public Node<NodeType> insertAfter(Node<NodeType> after, NodeType insert) {
			if (after == null) {
				after = this.head;
			}
			Node<NodeType> insertNode = new Node<NodeType>(insert, after.next);
			if (after.next.next == after) {
				after.next.setNext(insertNode);
			}
			after.setNext(insertNode);
			return insertNode;
		}
		
		public Node<NodeType> insertBefore(Node<NodeType> before, NodeType insert) {
			Node<NodeType> preBeforeNode = locatePrevious(before);
			Node<NodeType> insertNode = new Node<NodeType>(insert, preBeforeNode.next);
			
			if (preBeforeNode.next.next == preBeforeNode) {
				preBeforeNode.next.setNext(insertNode);
			}
			
			preBeforeNode.setNext(insertNode);
			return insertNode;
		}
		
		private Node<NodeType> locatePrevious(Node<NodeType> node) {
			if (node == null) {
				return this.tail.next;
			}
			Node<NodeType> beforeNode = this.head;
			while (beforeNode.next != node) {
				beforeNode = beforeNode.next;
			}
			return beforeNode;
		}
		
		public void delete(Node<NodeType> toDelete) {
			if (toDelete.next.next == toDelete) {
				Node<NodeType> before = locatePrevious(toDelete);
				before.setNext(toDelete.next);
				before.next.setNext(before);
			} else {
				toDelete.value = toDelete.next.value;
				toDelete.setNext(toDelete.next.next);
			}
		}
		
		public SinglyLinkedList<NodeType> concat(SinglyLinkedList<NodeType> otherList) {
			this.tail.next.setNext(otherList.head.next);
			return this;
		}
		
		public SinglyLinkedList<NodeType> merge(SinglyLinkedList<NodeType> otherList) {
			Node<NodeType> ownNode = this.head.next;
			Node<NodeType> otherNode = otherList.head.next;
			Node<NodeType> preOwnNode = this.head;
			Node<NodeType> nextOtherNode;
			
			if (ownNode == this.tail) {  // we are empty
				this.concat(otherList);
				return this;
			} else if (otherNode == otherList.tail) { // other list is empty
					return this; // noop
			}
			
			while(otherNode != otherList.tail) {
				while(ownNode != this.tail && (ownNode.value.compareTo(otherNode.value) < 0)) {
					preOwnNode = ownNode;
					ownNode = ownNode.next;
				}
				
				nextOtherNode = otherNode.next;
				preOwnNode.setNext(otherNode);
				otherNode.setNext(ownNode);
				
				if (ownNode.next == this.tail) {
					ownNode.setNext(otherNode);
					otherNode = nextOtherNode;
					break;
				}
				
				ownNode = otherNode;
				otherNode = nextOtherNode;
			}
			
			if (otherNode != otherList.tail) {
				this.tail.next.setNext(otherNode);
				this.tail = otherList.tail;
			}
			
			return this;
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder("[");
			Node<NodeType> current = this.head;
			do {
				sb.append(current.next+", ");
				current = current.next;
			} while (!(current.next.next == current));
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.deleteCharAt(sb.lastIndexOf(" "));
			sb.append("]");
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		SinglyLinkedList<Integer>.Node<Integer> inserted = null;
		for (int i = 0; i < 20; i += 2) {
			inserted = list.insertAfter(inserted, Integer.valueOf(i));//Math.random());
		}
		list.delete(list.locate(Integer.valueOf(0)));
		System.out.println(list);
		
		SinglyLinkedList<Integer> list2 = new SinglyLinkedList<Integer>();
		for (int i = -21; i < 22; i += 2) {
			list2.insertBefore(null, Integer.valueOf(i));//Math.random());
		}
		System.out.println(list2);
		
		
		//list.concat(list2);
		list.merge(list2);
		System.out.println(list);
		System.out.println(new SinglyLinkedList<Integer>().merge(list2));
	}

}
