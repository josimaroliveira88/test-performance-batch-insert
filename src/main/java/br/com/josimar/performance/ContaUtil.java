package br.com.josimar.performance;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

@ApplicationScoped
public class ContaUtil {
    private static final int QUANTIDADE_CONTAS = 10000000;

    Logger LOG = Logger.getLogger(ContaUtil.class);

    private Map<ContaId, Conta> contas;

    public Map<ContaId, Conta> getContas() {
        if (contas == null) {
            LOG.info("Criando contas");
            var initTime = System.currentTimeMillis();
            contas = createContas();
            var endTime = System.currentTimeMillis();
            LOG.info("Duration of create: " + (endTime - initTime));
        } else {
            LOG.info("Contas ja existem");
        }
        return contas;
    }

    public void reset() {
        contas = null;
    }

    private Map<ContaId, Conta> createContas() {
        Map<ContaId, Conta> contas = new HashMap<>();
        // create a random number between 1 and 10000
        var random = new Random();
        int numeroAleatorio = random.nextInt(QUANTIDADE_CONTAS) + 1;
        for (int i = 1; i < QUANTIDADE_CONTAS + 1; i++) {
            var conta = new Conta();
            conta.setCodigoInstituicao(i);
            conta.setCodigoTipoPlano(i);
            if (i == numeroAleatorio) {
                conta.setNumeroConta(42);
                conta.setCodigoInstituicao(42+QUANTIDADE_CONTAS);
                conta.setCodigoTipoPlano(42+QUANTIDADE_CONTAS);
                conta.setNomeConta("Conta foi encontrada");
            } else {
                var numeroConta = random.nextInt(QUANTIDADE_CONTAS) + 1;
                if (numeroConta == 42) {
                    conta.setNumeroConta(numeroConta+1);
                    conta.setNomeConta("nome" + conta.getNumeroConta());
                } else {
                    conta.setNumeroConta(numeroConta);
                    conta.setNomeConta("nome" + conta.getNumeroConta());
                }
            }
            contas.put(new ContaId(conta.getCodigoInstituicao(), conta.getCodigoTipoPlano(), conta.getNumeroConta()), conta);
            if (i == numeroAleatorio) {
                LOG.info("Conta a ser encontrada no index " + i + ": " + conta);
            }

            if (i == 0) {
                LOG.info("First conta created: " + conta);
            }
        }

        return contas;
    }
}
