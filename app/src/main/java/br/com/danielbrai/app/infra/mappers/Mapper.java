package br.com.danielbrai.app.infra.mappers;

public interface Mapper<I, O> {

    O map (I input);
}
