// Diogo Lourenzon Hatz - 10402406

package br.calebe.ticketmachine.core;

/**
 *
 * @author Calebe de Paula Bianchini
 */
public class PapelMoeda {

    protected int valor;
    protected int quantidade;

    public PapelMoeda(int valor, int quantidade) {
        this.valor = valor;                               // Defeito por omissão: não é realizado nenhum tratamento sobre o construtor, permitindo que a quantidade e valor 
        this.quantidade = quantidade;                     // sejam números negativos, por exemplo.
    }

    public int getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
