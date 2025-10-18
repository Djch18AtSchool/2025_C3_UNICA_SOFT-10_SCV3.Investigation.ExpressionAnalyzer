package Domain;

/**
 * Implementación de una Pila (Stack) desde cero.
 * Utiliza la lógica LIFO (Last-In, First-Out) y la clase Node.
 * Esta pila está diseñada para almacenar los operadores (char)
 * durante la conversión de infijo a postfijo.
 */
public class Stack <T> {

    protected Node <T> top;

    /**
     * Constructor de la Pila.
     * Inicialmente, la pila está vacía (top es null).
     */
    public Stack() {
        this.top = null;
    }

    /**
     * Verifica si la pila está vacía.
     * @return true si la pila no tiene elementos.
     */
    public boolean isEmpty() {
        return this.top == null;
    }

    /**
     * Operación Push (Apilar).
     * Crea un nuevo nodo con el valor y lo coloca en la cima.
     * @param value El carácter a apilar.
     */
    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.setNext(this.top);
        this.top = newNode;
    }

    /**
     * Operación Pop (Desapilar).
     * Quita el nodo de la cima y devuelve su valor.
     * @return El carácter que estaba en la cima.
     */
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Error: La pila está vacía (Stack Underflow)");
        }
        T value = this.top.getValue();
        this.top = this.top.getNext();
        return value;
    }

    /**
     * Operación Peek (Consultar Cima).
     * Devuelve el valor de la cima sin quitar el nodo.
     * @return El carácter que está en la cima.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Error: La pila está vacía");
        }
        return this.top.getValue();
    }
}