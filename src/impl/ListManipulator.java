package impl;

import common.InvalidIndexException;
import common.InvalidListException;
import common.ListNode;
import interfaces.IFilterCondition;
import interfaces.IListManipulator;
import interfaces.IMapTransformation;
import interfaces.IReduceOperator;
import java.util.ArrayList;
public class ListManipulator implements IListManipulator {
    @Override
    public int size(ListNode head) {
        if (isEmpty(head)) {
            return 0;
        }

        ListNode temp = head;
        int count = 1;

        while (temp.next != head) {
            temp = temp.next;
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty(ListNode head) {
        return head == null;
    }

    @Override
    public boolean contains(ListNode head, Object element) {
        if (isEmpty(head)) {
            return false;
        }

        if (head.element.equals(element)){
            return true;
    }
        ListNode temp = head;
        while (temp.next != head) {
            temp = temp.next;
            if (temp.element.equals(element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int count(ListNode head, Object element) {
        if (isEmpty(head) || element == null) {
            return 0;
        }

        ListNode temp = head;
        int count = head.element.equals(element) ? 1 : 0;

        while (temp.next != head) {
            temp = temp.next;
            if (temp.element.equals(element)){
                count++;
            }
        }
        return count;
    }

    @Override
    public String convertToString(ListNode head) {
        if (isEmpty(head)) {
            return "";
        }

        ListNode temp = head;
        String result = temp.element + "";
        while (temp.next != head) {
            temp = temp.next;
            result += "," + temp.element;
        }

        return result;
    }

    @Override
    public Object getFromFront(ListNode head, int n) throws InvalidIndexException {
        if (n < 0 || isEmpty(head)) {
            throw new InvalidIndexException();
        }

        ListNode temp = head;
        while (temp.next != head && n > 0) {
            temp = temp.next;
            n--;
        }

        if (n != 0){
            throw new InvalidIndexException();
        }
        return temp.element;
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
        if (size(firstNode) != size(secondNode)) {
            return false;
        }

        try {
            for (int i = 0; i < size(firstNode); i++) {
                if (!getFromFront(firstNode, i).equals(getFromFront(secondNode, i))) {
                    return false;
                }
            }
        } catch (InvalidIndexException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean containsDuplicates(ListNode head) {
        if (head == null) {
            return false;
        }

        ArrayList<Object> uniqueItems = new ArrayList<>();
        ListNode temp = head;
        do {
            if (uniqueItems.contains(temp.element)) {
                return true;
            }
            uniqueItems.add(temp.element);
            temp = temp.next;
        } while (temp != head);

        return false;
    }

    @Override
    public ListNode addHead(ListNode head, ListNode node) {
        ListNode end = head.previous;
        end.next = node;
        node.previous = end;
        node.next = head;
        head.previous = node;
        return node;
    }

    @Override
    public ListNode append(ListNode firstListHead, ListNode secondListHead) {
        if (firstListHead == null && secondListHead == null) return null;
        if (firstListHead == null) return secondListHead;
        if (secondListHead == null) return firstListHead;

        ListNode firstend = firstListHead.previous;
        ListNode secondend = secondListHead.previous;
        firstend.next = secondListHead;
        secondend.next = firstListHead;
        secondListHead.previous = firstend;
        firstListHead.previous = secondend;

        return firstListHead;
    }

    public ListNode insert(ListNode head, ListNode node, int n) throws InvalidIndexException {
        if (n < 0 || n > size(head)) throw new InvalidIndexException();
        if (n == 0) return addHead(head, node);
        if (n == size(head)) return append(head, node);

        ListNode temp = head;
        while (n != 1) {
            temp = temp.next;
            n--;
        }

        node.next = temp.next;
        node.previous = temp;
        temp.next = node;

        return head;
    }

    public ListNode delete(ListNode head, Object elem) {
        if (head.element.equals(elem) && size(head) == 1) return null;
        if (!contains(head, elem)) return head;

        ListNode temp = head;
        while (!temp.element.equals(elem)) {
            temp = temp.next;
        }

        if (temp.equals(head)) {
            ListNode end = head.previous;
            ListNode newH = head.next;
            newH.previous = end;
            end.next = newH;
            return newH;
        } else {
            temp.previous.next = temp.next;
            temp.next.previous = temp.previous;
        }

        return head;
    }

    @Override
    public ListNode reverse(ListNode head) {
        if (isEmpty(head) || size(head) == 1) return head;

        ListNode newH = head.previous;
        ListNode temp = head;
        do {
            ListNode temp2 = temp.next;
            temp.next = temp.previous;
            temp.previous = temp2;
            temp = temp.next;
        } while (temp != head);

        return newH;
    }

    @Override
    public ListNode split(ListNode head, ListNode node) throws InvalidListException {
        if (isEmpty(head) || node == null || head.element.equals(node.element)) {
            throw new InvalidListException();
        }

        ListNode temp = head;
        do {
            if (temp.element.equals(node.element)) {
                ListNode end = head.previous;
                temp.previous.next = head;
                head.previous = temp.previous;
                temp.previous = end;
                end.next = temp;

                ListNode result = new ListNode(head);
                result.next = new ListNode(temp);
                result.previous = result.next;
                result.next.next = result;
                result.next.previous = result;

                return result;
            }
            temp = temp.next;
        } while (temp != head);

        return head;
    }

    @Override
    public ListNode map(ListNode head, IMapTransformation transformation) {
        if (isEmpty(head)) {
            return null;
        }

        ListNode temp = head;
        do {
            temp.element = transformation.transform(temp.element);
            temp = temp.next;
        } while (temp != head);

        return head;
    }

    @Override
    public Object reduce(ListNode head, IReduceOperator operator, Object initial) {
        if (head == null) {
            return initial;
        }

        ListNode temp = head;
        Object result = initial;
        do {
            result = operator.operate(result, temp.element);
            temp = temp.next;
        } while (temp != head);

        return result;
    }

    @Override
    public ListNode filter(ListNode head, IFilterCondition condition) {
        if (head == null){
            return null;
        }

        ListNode temp = head.previous;
        do {
            if (!condition.isSatisfied(temp.element)) {
                if (size(head) == 1) {
                    return null;

                }

                head = delete(head, temp.element);
            }
            temp = temp.previous;
        } while (temp != head || !condition.isSatisfied(temp.element));

        return head;
    }
}
