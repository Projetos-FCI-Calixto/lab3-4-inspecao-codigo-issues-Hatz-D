// Diogo Lourenzon Hatz - 10402406

package br.calebe.ticketmachine.core;

import br.calebe.ticketmachine.exception.PapelMoedaInvalidaException;
import br.calebe.ticketmachine.exception.SaldoInsuficienteException;
import java.util.Iterator;

/**
 *
 * @author Calebe de Paula Bianchini
 */
public class TicketMachine {

    protected int valor;
    protected int saldo;
    protected int[] papelMoeda = {2, 5, 10, 20, 50, 100};

    public TicketMachine(int valor) {
        this.valor = valor;
        this.saldo = 0;
    }

    public void inserir(int quantia) throws PapelMoedaInvalidaException {
        boolean achou = false;
        for (int i = 0; i < papelMoeda.length && !achou; i++) {
            if (papelMoeda[1] == quantia) {          // Defeito por comissão: Da forma que está, a máquina somente aceita cédulas de 5 reais, uma vez que 
                achou = true;                        // em toda iteração, a comparação é feita com papelMoeda[1] (5) ao invés de papelMoeda[i].
            }
        }
        if (!achou) {
            throw new PapelMoedaInvalidaException();
        }
        this.saldo += quantia;
    }

    public int getSaldo() {
        return saldo;
    }
 
    public Iterator<Integer> getTroco() {            // Defeito por dados: A classe genérica do objeto retornado é Iterator<Integer> ao invés de Iterator<PapelMoeda>.
        return null;                                 
    }                                                // Defeito por comissão: Ao invés de retornar o troco, este método sempre retorna null. 

    public String imprimir() throws SaldoInsuficienteException {
        if (saldo < valor) {                         // Defeito por comissão: O correto será <=, uma vez que se o usuário tiver o saldo exatamente igual ao preço do ticket,
            throw new SaldoInsuficienteException();  // ele ainda deverá conseguir comprá-lo.
        }
        String result = "*****************\n";       // Defeito por comissão: Essa função não realiza a operação saldo -= valor para depois imprimir o saldo. Nesse sentido, 
        result += "*** R$ " + saldo + ",00 ****\n";  // é possível imprimir tickets infinitamente pois o saldo nunca é deduzido.
        result += "*****************\n";
        return result;
    }
}
