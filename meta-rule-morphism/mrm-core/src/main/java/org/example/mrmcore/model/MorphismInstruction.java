package org.example.mrmcore.model;

import java.util.Map;

/**
 * 変換の「射」を定義する完全な指示書
 */
public record MorphismInstruction(
    // 転記元の項目名
    String sourceField,

    // 転記先の項目名
    String targetField,

    // 適用する戦略ID
    String strategyId,

    // 戦略へのパラメータ
    Map<String, Object> options  
) {}