# でんしゃかん字クイズ

小学1年生向けの漢字学習Androidアプリです。電車好きの子ども向けに、電車に関連した例文で80字の漢字の読み方を学習できるクイズアプリです。

## 技術スタック

- **言語**: Kotlin
- **IDE**: Android Studio
- **最小SDK**: API 24 (Android 7.0)
- **ターゲットSDK**: API 34 (Android 14)
- **アーキテクチャ**: MVVM
- **UI**: Jetpack Compose
- **データ保存**: DataStore (Preferences)
- **DI**: Hilt

## 主要機能

1. **スタート画面**
   - 通常モード（80字全てからランダム出題）
   - 苦手かん字モード（間違いが多い漢字のみ出題）
   - 学習履歴表示

2. **設定画面**
   - 問題数設定（10問/20問/全問）
   - 学習レポートへの遷移
   - 学習記録リセット

3. **学習レポート画面**
   - 全体の成績表示
   - 苦手な漢字一覧

4. **クイズ画面**
   - 進捗バー
   - 漢字表示
   - 4択問題（同カテゴリから優先的に選択肢を生成）
   - ヒント機能（例文表示）

5. **結果画面**
   - スコア表示
   - 間違えた漢字の一覧
   - 再挑戦機能

## プロジェクト構造

```
app/src/main/java/com/example/trainkanji/
├── data/
│   ├── KanjiCategory.kt          # 漢字のカテゴリ enum
│   ├── KanjiItem.kt              # 漢字データクラス
│   ├── KanjiData.kt              # 80字の漢字データ定義
│   ├── KanjiStats.kt             # 漢字別統計データ
│   ├── TotalStats.kt             # 全体統計データ
│   ├── QuestionCount.kt          # 問題数設定 enum
│   ├── QuizMode.kt               # クイズモード enum
│   └── QuizDataStore.kt          # DataStore 実装
├── ui/
│   ├── theme/
│   │   ├── Color.kt              # カラーパレット
│   │   ├── Type.kt               # タイポグラフィ
│   │   └── Shape.kt              # 形状・余白定義
│   ├── components/
│   │   ├── GradientBackground.kt # グラデーション背景
│   │   ├── KanjiBox.kt           # 漢字表示ボックス
│   │   ├── ChoiceButton.kt       # 選択肢ボタン
│   │   └── SentenceWithHighlight.kt # ハイライト付き例文
│   ├── screens/
│   │   ├── StartScreen.kt        # スタート画面
│   │   ├── SettingsScreen.kt     # 設定画面
│   │   ├── ReportScreen.kt       # レポート画面
│   │   ├── QuizScreen.kt         # クイズ画面
│   │   └── ResultScreen.kt       # 結果画面
│   └── navigation/
│       └── NavGraph.kt           # ナビゲーション設定
├── viewmodel/
│   ├── StartViewModel.kt         # スタート画面 ViewModel
│   ├── SettingsViewModel.kt      # 設定画面 ViewModel
│   ├── ReportViewModel.kt        # レポート画面 ViewModel
│   └── QuizViewModel.kt          # クイズ画面 ViewModel
├── TrainKanjiApplication.kt      # Hilt Application
└── MainActivity.kt               # メインアクティビティ
```

## ビルド方法

1. Android Studioでプロジェクトを開く
2. Gradle Syncを実行
3. エミュレータまたは実機でアプリを実行

## 注意事項

- すべてのUI文言は小学1年生が読めるよう、小1で習う漢字以外はひらがな表記
- 選択肢は同じカテゴリの漢字から優先的に生成（消去法で解けないように）
- 苦手判定は「間違い回数 > 正解回数」で判定
- アニメーションは控えめに（子どもが集中できるように）

## データ仕様

### 漢字カテゴリ

- NUMBERS: 数字（12字）
- DAYS: 曜日（7字）
- DIRECTIONS: 方向・位置（8字）
- NATURE: 自然・風景（13字）
- PEOPLE: 人・大きさ（12字）
- BODY: 体・動作（6字）
- COLORS: 色・形（6字）
- THINGS: もの・その他（16字）

合計80字の小学1年生の漢字を収録

## ライセンス

このプロジェクトは教育目的で作成されています。
