package br.com.josimar.kafka.nba;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

@RequestScoped
public class ReadStats {
    @Inject
    Logger log;

    public void reader() {
        try (CSVReader reader = new CSVReader(new FileReader("data/traditional.csv"))) {
            var gameCount = 0;
            reader.readNext();

            var stats = readLine(reader);
            int previousId = stats.getGameId();

            List<TraditionalStats> list = new ArrayList<>();
            list.add(stats);

            while ((stats = readLine(reader)) != null) {
                if (previousId == stats.getGameId()) {
                    list.add(stats);
                } else {
                    gameCount++;
                    sendToKafka(list, gameCount);
                    previousId = stats.getGameId();
                    list = new ArrayList<>();
                    list.add(stats);
                }

            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public TraditionalStats readLine(CSVReader reader) throws CsvValidationException, IOException {
        String[] line;
        line = reader.readNext();

        if (line == null) {
            return null;
        }

        TraditionalStats stats = new TraditionalStats();

        stats.setGameId(Integer.parseInt(line[0]));
        stats.setDate(line[1]);
        stats.setType(line[2]);
        stats.setPlayerId(Integer.parseInt(line[3]));
        stats.setPlayer(line[4]);
        stats.setTeam(line[5]);
        stats.setHome(line[6]);
        stats.setAway(line[7]);
        stats.setMin(Integer.parseInt(line[8]));
        stats.setPts(Integer.parseInt(line[9]));
        stats.setFgm(Integer.parseInt(line[10]));
        stats.setFga(Integer.parseInt(line[11]));
        stats.setFgPct(Double.parseDouble(line[12]));
        stats.setThreePm(Integer.parseInt(line[13]));
        stats.setThreePa(Integer.parseInt(line[14]));
        stats.setThreePct(Double.parseDouble(line[15]));
        stats.setFtm(Integer.parseInt(line[16]));
        stats.setFta(Integer.parseInt(line[17]));
        stats.setFtPct(Double.parseDouble(line[18]));

        return stats;
    }

    private void sendToKafka(List<TraditionalStats> list, int numberLine) {
        if (numberLine % 10000 == 0) {
            log.infov("Line {1} Sending gameid {0} to kafka", list.get(0).getGameId(), numberLine);
        }
    }
}
