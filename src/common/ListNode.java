package common;

/**
 * This class represents nodes in a doubly linked list.
 *
 */
public class ListNode {

    /**
     * The element attribute represents the actual element stored in a linked list.
     */
    public Object element;

    /**
     * The previous attribute represents the reference to the previous node in the linked list.
     */
    public ListNode previous;

    /**
     * The next attribute represents the reference to the next node in the linked list.
     */
    public ListNode next;

    /**
     * Constructor to permit instantiation of a list node containing the specified element but without linking the node to any other node.
     * @param element the element to hold in this node
     */
    public ListNode(Object element) {
        this.element = element;
        this.previous = null;
        this.next = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ListNode) {
            ListNode other = (ListNode) o;
            return this.element.equals(other.element);
        }
        return false;
    }

    public ListNode getNext() {
        return next;
    }

    public void setPrevious( ListNode previous) {
        this.previous =  previous;
    }


    public Object getElement(){
        return element;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public ListNode getPrevious() {
        return previous;
    }
}
