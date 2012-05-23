package Uebungen;

public class DSA_1196473_5 {
	static class SinglyLinkedList {
		class Node {
			protected Object value;
			protected Node next;
			protected Node(Object value, Node nextNode) {
				this.value = value;
				this.next = nextNode;
			}
			
			public String toString() {
				return this.value.toString();
			}
		}
		
		private Node head;
		private Node tail;
		
		public SinglyLinkedList(){
			this.head = new Node(null, null);
			this.tail = new Node(null, this.head);
			this.head.next = this.tail; 
		}
		
		public Node locate(Object value) {
			if (value == null) {
				return this.head;
			} else {
				return locate(value, this.head);
			}
		}
		
		private Node locate(Object value, Node current) {
			if (current.next.value == value) {
				return current.next;
			} else if (current.next.next == current) {
				return null;
			} else {
				return this.locate(value, current.next);
			}
		}
		
		public Node insertAfter(Node after, Object insert) {
			if (after == null) {
				after = this.head;
			}
			Node insertNode = new Node(insert, after.next);
			if (after.next.next == after) {
				after.next.next = insertNode;
			}
			after.next = insertNode;
			return insertNode;
		}
		
		public Node insertBefore(Node before, Object insert) {
			Node preBeforeNode = locateBefore(before);
			Node insertNode = new Node(insert, preBeforeNode.next);
			
			if (preBeforeNode.next.next == preBeforeNode) {
				preBeforeNode.next.next = insertNode;
			}
			
			preBeforeNode.next = insertNode;
			return insertNode;
		}
		
		private Node locateBefore(Node node) {
			if (node == null) {
				return this.tail.next;
			}
			Node beforeNode = this.head;
			while (beforeNode.next != node) {
				beforeNode = beforeNode.next;
			}
			return beforeNode;
		}
		
		public void delete(Node toDelete) {
			if (toDelete.next.next == toDelete) {
				Node before = locateBefore(toDelete);
				before.next = toDelete.next;
				before.next.next = before;
			} else {
				toDelete.value = toDelete.next.value;
				toDelete.next = toDelete.next.next;
			}
		}
		
		public void concat(SinglyLinkedList otherList) {
			this.tail.next.next = otherList.head.next;
		}
		
		public void merge(SinglyLinkedList otherList) {
			Node otherNode = otherList.head;
			Node ownNode = this.head;
			while(!(otherNode.next.next == otherNode || ownNode.next.next == ownNode)) {
				
				otherNode = otherNode.next;
				ownNode = ownNode.next;
			}
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder("[");
			Node current = this.head;
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
		SinglyLinkedList list = new SinglyLinkedList();
		SinglyLinkedList.Node inserted = null;
		for (int i = 0; i < 20; i++) {
			inserted = list.insertAfter(inserted, i);//Math.random());
		}
		list.delete(list.locate(0));
		System.out.println(list);
		SinglyLinkedList list2 = new SinglyLinkedList();
		for (int i = 0; i < 20; i++) {
			list2.insertBefore(null, i);//Math.random());
		}
		list.concat(list2);
		System.out.println(list);
	}

}
