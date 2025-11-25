import java.util.*;

public class TwoWayLinkedList<E> implements MyList<E> {

    private Node<E> head, tail;
    private int size = 0;

    // Node class with previous pointer
    private static class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E e) {
            element = e;
        }
    }

    public TwoWayLinkedList() {
    }

    @Override
    public void add(int index, E e) {
        checkIndex(index);

        if (index == size) { // append at end
            addLast(e);
        } else if (index == 0) { // insert at head
            addFirst(e);
        } else {
            Node<E> current = getNode(index);
            Node<E> newNode = new Node<>(e);
            Node<E> prev = current.previous;

            prev.next = newNode;
            newNode.previous = prev;
            newNode.next = current;
            current.previous = newNode;
            size++;
        }
    }

    @Override
    public void add(E e) {
        addLast(e);
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> current = getNode(index);

        if (current.previous != null) {
            current.previous.next = current.next;
        } else {
            head = current.next;
        }

        if (current.next != null) {
            current.next.previous = current.previous;
        } else {
            tail = current.previous;
        }

        size--;
        return current.element;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return getNode(index).element;
    }

    @Override
    public int size() {
        return size;
    }

    private Node<E> getNode(int index) {
        Node<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // ListIterator from head
    public ListIterator<E> listIterator() {
        return new TwoWayListIterator(0);
    }

    // ListIterator from index
    public ListIterator<E> listIterator(int index) {
        return new TwoWayListIterator(index);
    }

    private class TwoWayListIterator implements ListIterator<E> {
        private Node<E> current;
        private int index;

        public TwoWayListIterator(int index) {
            this.index = index;
            current = (index == size) ? null : getNode(index);
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E e = current.element;
            current = current.next;
            index++;
            return e;
        }

        @Override
        public boolean hasPrevious() {
            return (current == null && tail != null) || (current != null && current.previous != null);
        }

        @Override
        public E previous() {
            if (current == null) {
                current = tail;
            } else {
                current = current.previous;
            }
            index--;
            return current.element;
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException();
        }
    }
}
