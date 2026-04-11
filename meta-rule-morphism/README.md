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
│
├── mrm-inbound              # 外部ファイル → 内部データ変換
│   ├── pom.xml
│   ├── src/main/java/org/example/mrminbound
│   │   ├── reader               # CSVファイルの読み込み・パース
│   │   ├── rule                 # Drools連携
│   │   ├── model                # 業務モデル（Tradeクラス等）
│   │   └── service
│   │
│   └── src/main/resources
│        └── org/example/mrminbound/ # 変換DRL
│
└── mrm-outbound                 # 内部データ → 外部形式
    ├── pom.xml
    ├── src/main/java/org/example/mrmoutbound
    │   ├── fetcher              # DB等からのデータ抽出
    │   ├── rule                 # Drools連携
    │   ├── model                # 業務モデル（Tradeクラス等）
    │   └── service              # 配信フロー制御
    │
    └── src/main/resources
        └── org/example/mrmoutbound/ # 変換DRL

```
