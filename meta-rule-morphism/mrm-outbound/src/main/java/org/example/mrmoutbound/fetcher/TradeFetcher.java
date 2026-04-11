package org.example.mrmoutbound.fetcher;

import java.util.List;

import org.example.mrmoutbound.model.Trade;

import java.util.ArrayList;

public class TradeFetcher {

    public List<Trade> fetchAll() {
        // 本来はDBや外部APIから取得するが、ここではダミーデータを返す

        List<Trade> trades = new ArrayList<>();
        
        Trade t1 = new Trade();
        t1.setSymbol("8001");
        t1.setQuantity(100);
        trades.add(t1);
        
        Trade t2 = new Trade();
        t2.setSymbol("8002");
        t2.setQuantity(50);
        trades.add(t2);
        
        return trades;
    }
}