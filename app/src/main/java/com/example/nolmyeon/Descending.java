package com.example.nolmyeon;

import java.util.Comparator;

public class Descending implements Comparator<Ranker> {
    public int compare(com.example.nolmyeon.Ranker a, com.example.nolmyeon.Ranker b)
    {
        return b.compareTo(a);
    }
}