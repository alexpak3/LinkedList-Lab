package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;

import java.util.ArrayList;

/**
 * This class represents the recursive implementation of the IListManipulator interface.
 */
public class RecursiveListManipulator implements IListManipulator {
    @Override
    public int size(ListNode head) {
        if (head == null) return 0;

        ListNode tail = head.previous;
        tail.next = null;
        int count = 1 + size(head.next);
        tail.next = head;

        return count;
    }

    @Override
    public boolean isEmpty(ListNode head) {
        return head == null;
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        if (head == null) return false;

        ListNode tail = head.previous;
        tail.next = null;
        boolean doesContain = head.element.equals(element) || contains(head.next, element);
        tail.next = head;

        return doesContain;
    }

    @Override
    public int count(ListNode head, Object element) {
        if (head == null) return 0;

        ListNode tail = head.previous;
        tail.next = null;
        int count = (head.element.equals(element) ? 1 : 0) + count(head.next, element);
        tail.next = head;

        return count;
    }

    @Override
    public String convertToString(ListNode head) {
        if (head == null) return "";
        if (head.next == null || head.next == head) return head.element + "";

        ListNode tail = head.previous;
        tail.next = null;
        String result = head.element + "," + convertToString(head.next);
        tail.next = head;

        return result;
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        if (n < 0 || head == null || size(head) < n) {
            throw new InvalidIndexException();
        }
        if (n == 0) {
            return head.element;
        }
        return getFromFront(head.next, n - 1);
    }

    @Override
    public Object getFromBack(ListNode head, int n) throws InvalidIndexException {
        return getFromFront(head, size(head) - n - 1);
    }

    @Override
    public boolean equals(ListNode firstNode, ListNode secondNode) {
        if (firstNode == null && secondNode == null) {
            return true;
        }
        if (firstNode == null || secondNode == null) {
            return false;
        }

        ListNode firstTail = firstNode.previous;
        ListNode secondTail = secondNode.previous;
        firstTail.next = null;
        secondTail.next = null;
        boolean result = firstNode.element.equals(secondNode.element) && equals(firstNode.next, secondNode.next);
        firstTail.next = firstNode;
        secondTail.next = secondNode;

        return result;
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        if (head == null) {
            return false;
        }
        return containsDuplicates(head, new ArrayList<>());
    }

    public boolean containsDuplicates(ListNode head, ArrayList<Object> uniqueItems) {
        if (head == null) {
            return false;
        }
        if (uniqueItems.contains(head)) {
            return true;
        }

        ListNode node = head.previous;
        node.next = null;
        uniqueItems.add(head);
        boolean result = containsDuplicates(head.next, uniqueItems);
        node.next = head;
        return result;
    }

    @Override
    public ListNode addHead(ListNode head, ListNode node) {
        ListNode tail = head.previous;
        tail.next = node;
        node.previous = tail;
        node.next = head;
        head.previous = node;
        return node;
    }

    @Override
    public ListNode append(ListNode firstListHead, ListNode secondListHead) {
        if (firstListHead == null && secondListHead == null) {
            return null;
        }
        if (firstListHead == null) {
            return secondListHead;
        }
        if (secondListHead == null) {
            return firstListHead;
        }

        ListNode firstTail = firstListHead.previous;
        ListNode secondTail = secondListHead.previous;
        firstTail.next = secondListHead;
        secondTail.next = firstListHead;
        secondListHead.previous = firstTail;
        firstListHead.previous = secondTail;

        return firstListHead;
    }

    @Override
    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        if (n < 0 || n > size(head)) {
            throw new InvalidIndexException();
        }
        if (n == 0) {
            return addHead(head, node);
        }
        if (n == size(head)) {
            return append(head, node);
        }

        ListNode currentNode = head;

        if (n == 1) {
            node.next = currentNode.next;
            node.previous = currentNode;
            currentNode.next = node;
            return head;
        }

        currentNode = currentNode.next;
        return insert(currentNode, node, n - 1);
    }

    @Override
    public ListNode delete(ListNode head, Object elem) {
        if (head.element.equals(elem) && size(head) == 1) {
            return null;
        }
        if (!contains(head, elem)) {
            return head;
        }

        if (head.element.equals(elem)) {
            ListNode tail = head.previous;
            ListNode newHead = head.next;
            newHead.previous = tail;
            tail.next = newHead;
            return newHead;
        }

        return delete(head.next, elem);
    }


    @Override
    public ListNode reverse(ListNode head) {
        if (isEmpty(head) || head.next == head.previous) {
            return head;
        }

        ListNode temp = head.next;
        head.next = head.previous;
        head.previous = temp;

        if (head.next.next == head) {
            return reverse(head.next);
        }

        return head.previous;
    }

    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        // TODO Auto-generated method stub
        return null;
    }
}