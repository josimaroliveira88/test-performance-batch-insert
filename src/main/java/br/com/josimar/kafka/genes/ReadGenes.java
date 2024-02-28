package br.com.josimar.kafka.genes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

@RequestScoped
public class ReadGenes {
    @Inject
    Logger log;

    public void reader() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/humchr01.txt"))) {
            var id = 0;
            var header = reader.readLine();
            id++;
            log.infov("Header {0}", header);
            header = reader.readLine();
            id++;
            log.infov("Header {0}", header);
            header = reader.readLine();
            id++;
            log.infov("Header {0}", header);

            HumchrRecord gene;
            while ((gene = readLine(reader)) != null) {
                id++;
                sendToKafka(gene, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToKafka(HumchrRecord gene, int id) {
        log.infov("Id processed {0} - gene: {1}", id, gene);
    }

    private HumchrRecord readLine(BufferedReader reader) throws IOException {
        String line;
        line = reader.readLine();

        if (line == null || line.isEmpty()) {
            return null;
        }

        String geneName = line.substring(0, 16).trim();
        String chromosomalPosition = line.substring(17, 32).trim();
        String swissProtAccessionNumber = line.substring(33, 43).trim();
        String mimNumber = line.substring(44, 55).trim();
        String description = line.substring(56).trim();

        HumchrRecord record = new HumchrRecord(geneName, chromosomalPosition, swissProtAccessionNumber, mimNumber,
                description);

        return record;
    }
}
