package com.example.juan.calculadora.Domain.DataStructures;


public class Stack<E> {

    private class Node {
        E element;
        Node next;
    }

    private Node top;
    private int numElements;

    public Stack() {
        top = null;
        numElements = 0;
    }

    public E getTop() {
        return top.element;
    }

    public void push(E element) {
        Node node = new Node();
        node.element = element;
        node.next = top;
        top = node;
        ++numElements;
    }

    public void pop() {
        if (top != null) {
            top = top.next;
            --numElements;
        }
    }

    public E getPop() {
        E element = null;
        if (top != null) {
            element = top.element;
            top = top.next;
            --numElements;
        }
        return element;
    }


    public boolean isEmpty() {
        return numElements == 0;
    }
}
