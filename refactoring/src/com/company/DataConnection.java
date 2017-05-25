package com.company;

import java.io.IOException;

interface DataConnection {
    double loadData() throws Exception;

    void saveData(int year, int data) throws IOException;
}