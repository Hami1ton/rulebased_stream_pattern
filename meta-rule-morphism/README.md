# meta-rule-morphism

## structure

```
meta-rule-morphism (Root / Parent POM)
├── pom.xml                      # 共通設定
├── mrm-core                     # 変換エンジンの核
│   ├── pom.xml
│   └── src/main/java/org/example/mrmcore
│       ├── engine               # 変換エンジン
│       ├── model                # MappingContext, MorphismInstruction
│       ├── strategy             # MorphismStrategy インフェースと具象クラス
│       └── util                 # todo
├── mrm-inbound                  # 外部ファイル → 内部データ変換
│   ├── pom.xml
│   ├── src/main/java/org/example/mrminbound
│   │   ├── watcher              # WatchService
│   │   └── service              # 集信フロー制御
│   └── src/main/resources
│       └── org/example/mrmcore/rules/inbound  # 変換DRL
└── mrm-outbound                 # 内部データ → 外部形式
    ├── pom.xml
    ├── src/main/java/org/example/mrmoutbound
    │   ├── fetcher              # DB等からのデータ抽出
    │   └── service              # 配信フロー制御
    └── src/main/resources
        └── org/example/mrmcore/rules/outbound # 変換DRL

```

## env


