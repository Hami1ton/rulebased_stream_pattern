package org.example.mrmcore.model;

/**
 * 変換の文脈（Fact）を保持するレコード
 */
public record MappingContext(
    // 連携先ID（例: 取引先コードやシステムコードなど）
    String systemId,
    
    // INBOUND / OUTBOUND
    String direction,
    
    // ソース側の項目名
    String sourceField
) {}