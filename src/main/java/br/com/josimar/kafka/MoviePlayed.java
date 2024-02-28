package br.com.josimar.kafka;

public class MoviePlayed {
    public int id;
    public long duration;

    public MoviePlayed(int id, long duration) {
        this.id = id;
        this.duration = duration;
    }
}
