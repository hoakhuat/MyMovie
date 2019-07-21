package com.example.mymovie.model.movie_cast;

import java.util.List;

public class CastAndCrew {
    private int id;
    private List<Cast> cast;
    private List<Crew> crew;

    public CastAndCrew() {
    }

    public CastAndCrew(int id, List<Cast> cast, List<Crew> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
