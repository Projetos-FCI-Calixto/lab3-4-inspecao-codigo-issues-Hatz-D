// Diogo Lourenzon Hatz - 10402406

package br.calebe.ticketmachine.core;

import java.util.Iterator;

/**
 *
 * @author Calebe de Paula Bianchini
 */
class Troco {

    protected PapelMoeda[] papeisMoeda;

    public Troco(int valor) {
        papeisMoeda = new PapelMoeda[6];
        int count = 0;
        while (valor % 100 != 0) {                               // Defeito por comissão: Para todas as cédulas, o programa ficará preso no loop while uma vez que a variável 
            count++;                                             // valor nunca é decrementada
        }
        papeisMoeda[5] = new PapelMoeda(100, count);            
        count = 0;
        while (valor % 50 != 0) {
            count++;
        }
        papeisMoeda[4] = new PapelMoeda(50, count);
        count = 0;
        while (valor % 20 != 0) {
            count++;
        }
        papeisMoeda[3] = new PapelMoeda(20, count);
        count = 0;
        while (valor % 10 != 0) {
            count++;
        }
        papeisMoeda[2] = new PapelMoeda(10, count);
        count = 0;
        while (valor % 5 != 0) {
            count++;
        }
        papeisMoeda[1] = new PapelMoeda(5, count);
        count = 0;
        while (valor % 2 != 0) {                                 // Defeito por comissão: Nos casos em que faltar troco de 1 real, o programa ficará preso nesse while, mesmo 
            count++;                                             // caso existisse o decremento da variável valor, uma vez que a comparação é valor % 2 != 0.
        }
        papeisMoeda[1] = new PapelMoeda(2, count);         // Defeito por comissão: Está sendo acessado o índice de cédulas de 5 reais para as cédulas de 2 reais. O
    }                                                            // correto seria papeisMoeda[0], acessou o índice errado.

    public Iterator<PapelMoeda> getIterator() {
        return new TrocoIterator(this);
    }

    class TrocoIterator implements Iterator<PapelMoeda> {

        protected Troco troco;

        public TrocoIterator(Troco troco) {
            this.troco = troco;
        }

        @Override                                                // Defeito por comissão: O loop nunca será encerrado, uma vez que começa de 6, a variável i é incrementada
        public boolean hasNext() {                               // a cada iteração e a condição de parada é i ser menor do que 0.
            for (int i = 6; i >= 0; i++) {                       
                if (troco.papeisMoeda[i] != null) {              // Defeito por dados: Índices maiores do que 6 são acessados em um vetor de tamanho igual a 6.
                    return true;
                }                                                // Defeito por desempenho: A função hasNext() sempre itera sobre todos os elementos do vetor papeisMoeda ao
            }                                                    // invés de armazenar um atributo interno relativo ao índice atual do objeto, o que não faz sentido.
            return false;
        }

        @Override
        public PapelMoeda next() {
            PapelMoeda ret = null;
            for (int i = 6; i >= 0 && ret != null; i++) {       // Defeito por computação: O loop nunca será executado, uma vez que a variável ret é inicializada com o 
                if (troco.papeisMoeda[i] != null) {             // valor null.
                    ret = troco.papeisMoeda[i];
                    troco.papeisMoeda[i] = null;                // Defeito por desempenho: A função next() sempre itera sobre todos os elementos do vetor papeisMoeda ao
                }                                               // invés de armazenar um atributo interno relativo ao índice atual do objeto, o que não faz sentido.
            }
            return ret;                                         // Defeito por omissão: Não foi implementada uma excessão para quando não há mais elementos no iterador
        }                                                       // e a função next() é chamada.

        @Override
        public void remove() {                                  // Defeito por comissão: A implementação desse remove() está incorreta pois, de acordo com a classe Iterator
            next();                                             // do Java, o método remove() pode ser chamado apenas após a primeira chamada do método next(), removendo o
        }                                                       // elemento retornado por ele. Nesta implementação, não há esse tipo de restrição ao método remove().
    }
}
