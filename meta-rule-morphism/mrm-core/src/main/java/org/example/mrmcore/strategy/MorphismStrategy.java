package org.example.mrmcore.strategy;

/**
 * 具体的な変換ロジック（射）を定義するインターフェース
 */
@FunctionalInterface
public interface MorphismStrategy {
    /**
     * @param sourceValue 元データ
     * @param options ルール側から渡されたパラメータ
     * @return 変換後のデータ
     */
    Object apply(Object sourceValue, java.util.Map<String, Object> options);
}