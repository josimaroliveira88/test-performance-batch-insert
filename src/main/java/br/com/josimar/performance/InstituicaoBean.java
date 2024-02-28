package br.com.josimar.performance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.jboss.logging.Logger;

import io.quarkus.runtime.Startup;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;

@ApplicationScoped
public class InstituicaoBean {
    Logger LOG = Logger.getLogger(InstituicaoBean.class);

    private AtomicInteger counter = new AtomicInteger();

    private static final String NOME_ARQUIVO = "arquivo.txt";

    @Inject
    ContaUtil contaUtil;

    @Inject
    InstituicaoService instituicaoService;

    public int getCounter() {
        return counter.get();
    }

    @Scheduled(every = "20s")
    void incrementCounter() {
        counter.incrementAndGet();
        instituicaoService.createInstituicoes();
        System.out.println("Counter incremented: " + counter);
    }

    @Scheduled(cron = "0 39 23 * * ?")
    void cronJob(ScheduledExecution execution) {
        counter.incrementAndGet();
        System.out.println(execution.getScheduledFireTime());
    }

    @Scheduled(cron = "{cron.expr}")
    void cronJobWithExpressionInConfig(ScheduledExecution execution) {
        counter.incrementAndGet();
        LOG.info("Passaram-se 30 segundos, limpando a lista de contas");
        contaUtil.reset();
    }

    // @Startup
    void createFile() {
        LOG.info("Application started, creating file with data");

        File arquivo = new File(NOME_ARQUIVO);
        if (arquivo.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
                String linha;
                int index = 0;
                String linhaAcumulada = "";
                while ((linha = br.readLine()) != null) {
                    index++;
                    if (index <= 10) {
                        linhaAcumulada = linhaAcumulada + linha;
                    } else {
                        processarLinha(linhaAcumulada);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        var instituicao = instituicaoService.createInstituicoes();
        Jsonb jsonb = JsonbBuilder.create();
        var json = jsonb.toJson(instituicao);
        try (FileWriter fileWriter = new FileWriter("instituicoes.json")) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processarLinha(String linha) {
        linha = linha.trim();
    }
}
