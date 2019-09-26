package com.example.scoredatabaseapp;

public class Scores {
    private String _name;
    private int _score;
    private int _id;

    public Scores(String name, int score) {
        this._name = name;
        this._score = score;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_score() {
        return _score;
    }

    public void set_score(int _score) {
        this._score = _score;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
