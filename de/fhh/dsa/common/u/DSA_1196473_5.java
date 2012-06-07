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
			
			public String asString() {
				return String.format("<Node value=%s>", this.value); 
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
			} else if (current.next == this.tail) {
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
			Node<NodeType> prevBeforeNode = locatePrevious(before);
			Node<NodeType> insertNode = new Node<NodeType>(insert, prevBeforeNode.next);
			
			if (prevBeforeNode.next.next == prevBeforeNode) {
				prevBeforeNode.next.setNext(insertNode);
			}
			
			prevBeforeNode.setNext(insertNode);
			return insertNode;
		}
		
		private Node<NodeType> locatePrevious(Node<NodeType> node) {
			if (node == null) {
				return this.tail.next;
			}
			Node<NodeType> prevNode = this.head;
			while (prevNode.next != node) {
				prevNode = prevNode.next;
			}
			return prevNode;
		}
		
		public void delete(Node<NodeType> toDelete) {
			if (toDelete.next.next == toDelete) {
				Node<NodeType> previous = locatePrevious(toDelete);
				previous.setNext(toDelete.next);
				previous.next.setNext(previous);
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
			Node<NodeType> prevOwnNode = this.head;
			Node<NodeType> nextOtherNode;
			
			if (ownNode == this.tail) {  // we are empty
				this.concat(otherList);
				return this;
			} else if (otherNode == otherList.tail) { // other list is empty
					return this; // noop
			}
			
			while(otherNode != otherList.tail) {
				while(ownNode != this.tail && (ownNode.value.compareTo(otherNode.value) < 0)) {
					prevOwnNode = ownNode;
					ownNode = ownNode.next;
				}
				
				nextOtherNode = otherNode.next;
				prevOwnNode.setNext(otherNode);
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
			if (this.tail.next == this.head) {
				return "[]";
			}
			StringBuilder sb = new StringBuilder("[");
			Node<NodeType> current = this.head;
			do {
				sb.append(current.next+", ");
				current = current.next;
			} while (current.next.next != current);
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.deleteCharAt(sb.lastIndexOf(" "));
			sb.append("]");
			return sb.toString();
		}
	}

	public static void main(String[] args) {
		testTaskA();
		testTaskB();
		testTaskC();
		testTaskD(20);
		testTaskE();
		testTaskF();
		testTaskG();
		testTaskH();
	}
	
	private static void testTaskA() {
		System.out.println("Aufgabe a)");
		System.out.println("Leere Liste:"+new SinglyLinkedList<Integer>());
		System.out.println("");
	}
	
	private static void testTaskB() {
		System.out.println("Aufgabe b)");
		SinglyLinkedList<Integer> list = getOrderedList();
		System.out.println("Liste: "+list);
		System.out.println("Suche nach 5: "+list.locate(5).asString());
		System.out.println();
	}
	
	private static void testTaskC() {
		System.out.println("Aufgabe b)");
		SinglyLinkedList<Integer> list = getOrderedList();
		System.out.println("Liste: "+list);
		SinglyLinkedList<Integer>.Node<Integer> node = list.locate(20);
		System.out.println("Suche nach 20: "+node.asString());
		list.insertAfter(node, -10);
		System.out.println("Einfügen von -10 nach 20: "+list);
		list.insertAfter(node, 50);
		System.out.println("Einfügen von 50 nach 20: "+list);
		System.out.println();
	}
	
	private static void testTaskD(int length) {
		System.out.println("Aufgabe d)");
		System.out.println("Länge: "+length);
		SinglyLinkedList<Long> list = new SinglyLinkedList<Long>();
		for (int i=0; i < length; i++) {
			list.insertAfter(null, Math.round(Math.random()*1000));
		}
		System.out.println("Liste: "+list);
		System.out.println();
	}
	
	private static void testTaskE() {
		System.out.println("Aufgabe e)");
		SinglyLinkedList<Integer> list = getOrderedList();
		System.out.println("Liste: "+list);
		SinglyLinkedList<Integer>.Node<Integer> node = list.locate(20);
		System.out.println("Suche nach 20: "+node.asString());
		list.insertBefore(node, -10);
		System.out.println("Einfügen von -10 vor 20: "+list);
		list.insertBefore(null, 50);
		System.out.println("Einfügen von 50 vor tail: "+list);
		System.out.println();
	}
	
	private static void testTaskF() {
		System.out.println("Aufgabe f)");
		SinglyLinkedList<Integer> list = getOrderedList();
		System.out.println("Liste: "+list);
		SinglyLinkedList<Integer>.Node<Integer> node = list.locate(15);
		System.out.println("Suche nach 15: "+node.asString());
		list.delete(node);
		System.out.println("Löschen von 15: "+list);
		System.out.println();
	}
	
	private static void testTaskG() {
		System.out.println("Aufgabe f)");
		SinglyLinkedList<Integer> listOne = getOrderedList();
		System.out.println("Liste 1: "+listOne);
		SinglyLinkedList<Integer> listTwo = getOrderedListFromToWithStep(30, 40, 1);
		System.out.println("Liste 2: "+listTwo);
		listOne.concat(listTwo);
		System.out.println("Liste 1 mit Liste 2 angehängt: "+listOne);
		System.out.println();
	}
	
	private static void testTaskH() {
		System.out.println("Aufgabe h)");
		SinglyLinkedList<Integer> listOne = getOrderedListFromToWithStep(-10, 10, 2);
		System.out.println("Liste 1: "+listOne);
		SinglyLinkedList<Integer> listTwo = getOrderedListFromToWithStep(1, 21, 3);
		System.out.println("Liste 2: "+listTwo);
		listOne.merge(listTwo);
		System.out.println("Liste 1 mit Liste 2 vereinigt: "+listOne);
	}
	
	private static SinglyLinkedList<Integer> getOrderedList() {
		return getOrderedListFromToWithStep(0, 20, 1);
	}
	
	private static SinglyLinkedList<Integer> getOrderedListFromToWithStep(int from, int to, int step) {
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		SinglyLinkedList<Integer>.Node<Integer> inserted = null;
		for (; from <= to; from += step) {
			inserted = list.insertAfter(inserted, Integer.valueOf(from));
		}
		return list;
	}

}
