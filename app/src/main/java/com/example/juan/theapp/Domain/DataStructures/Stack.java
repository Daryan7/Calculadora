package com.example.juan.theapp.Domain.DataStructures;


public class Stack<E> {

    private class Node {
        E element;
        Node next;
    }

    private Node top;

    public Stack() {
        top = null;
    }

    public E getTop() {
        return top.element;
    }

    public void push(E element) {
        Node node = new Node();
        node.element = element;
        node.next = top;
        top = node;
    }

    public void pop() {
        top = top.next;
    }

    public E getPop() {
        E element = top.element;
        top = top.next;
        return element;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
