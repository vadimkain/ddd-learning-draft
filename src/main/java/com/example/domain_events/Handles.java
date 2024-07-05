package com.example.domain_events;

public interface Handles<T> {
    void handle(T t);
}
