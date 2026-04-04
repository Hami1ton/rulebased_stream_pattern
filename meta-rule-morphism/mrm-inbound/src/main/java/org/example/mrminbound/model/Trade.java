package org.example.mrminbound.model;

public class Trade {
    // 内部管理用ID
    private String tradeId;

    // 銘柄コード
    private String symbol;

    // 数量
    private Integer quantity;

    // Getter
    public String getTradeId() {
        return tradeId;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Setter
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setQuantity(Integer quantity) {
            this.quantity = quantity;
    }

}