package com.example.nolmyeon;

import java.util.Comparator;

public class Ranker implements Comparable<com.example.nolmyeon.Ranker>{
    private Integer count;
    private Long number;
    private String name;

    public Ranker(Integer count, Long number, String name) {
        this.count = count;
        this.number = number;
        this.name = name;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ranker{" +
                "count=" + count +
                ", number=" + number +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(com.example.nolmyeon.Ranker ranker) {
        return this.count.compareTo(ranker.count);
    }

}
