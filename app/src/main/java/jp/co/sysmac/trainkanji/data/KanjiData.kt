package jp.co.sysmac.trainkanji.data

object KanjiData {
    val allKanji: List<KanjiItem> = listOf(
        // NUMBERS (1-12)
        KanjiItem(1, "一", "いち", "一ばんホームにでん車がきます", KanjiCategory.NUMBERS),
        KanjiItem(2, "二", "に", "二りょう目にのろう", KanjiCategory.NUMBERS),
        KanjiItem(3, "三", "さん", "三ふんではっ車します", KanjiCategory.NUMBERS),
        KanjiItem(4, "四", "よん", "四ばんせんはかいそくでん車です", KanjiCategory.NUMBERS),
        KanjiItem(5, "五", "ご", "五じのでん車にのる", KanjiCategory.NUMBERS),
        KanjiItem(6, "六", "ろく", "六りょうへんせいのでん車だ", KanjiCategory.NUMBERS),
        KanjiItem(7, "七", "しち", "七じにえきにつく", KanjiCategory.NUMBERS),
        KanjiItem(8, "八", "はち", "八りょう目がせんとう車りょう", KanjiCategory.NUMBERS),
        KanjiItem(9, "九", "きゅう", "九しゅうしんかんせんにのりたい", KanjiCategory.NUMBERS),
        KanjiItem(10, "十", "じゅう", "十りょうへんせいのでん車", KanjiCategory.NUMBERS),
        KanjiItem(11, "百", "ひゃく", "きっぷは百円です", KanjiCategory.NUMBERS),
        KanjiItem(12, "千", "せん", "きっぷは千円です", KanjiCategory.NUMBERS),

        // DAYS (13-19)
        KanjiItem(13, "日", "にち", "日ようびはでん車がすいている", KanjiCategory.DAYS),
        KanjiItem(14, "月", "げつ", "月よう日はこんでいる", KanjiCategory.DAYS),
        KanjiItem(15, "火", "か", "火よう日にでかける", KanjiCategory.DAYS),
        KanjiItem(16, "水", "すい", "水よう日のダイヤ", KanjiCategory.DAYS),
        KanjiItem(17, "木", "もく", "木よう日のじこくひょう", KanjiCategory.DAYS),
        KanjiItem(18, "金", "きん", "金よう日のしゅうでん", KanjiCategory.DAYS),
        KanjiItem(19, "土", "ど", "土よう日はとくべつダイヤ", KanjiCategory.DAYS),

        // DIRECTIONS (20-27)
        KanjiItem(20, "上", "うえ", "つりかわが上にある", KanjiCategory.DIRECTIONS),
        KanjiItem(21, "下", "した", "ホームの下にせんろがある", KanjiCategory.DIRECTIONS),
        KanjiItem(22, "左", "ひだり", "左がわのドアがひらきます", KanjiCategory.DIRECTIONS),
        KanjiItem(23, "右", "みぎ", "右がわに気をつけて", KanjiCategory.DIRECTIONS),
        KanjiItem(24, "中", "なか", "でん車の中はすずしい", KanjiCategory.DIRECTIONS),
        KanjiItem(25, "入", "はい", "でん車に入る", KanjiCategory.DIRECTIONS),
        KanjiItem(26, "出", "で", "出口はこちらです", KanjiCategory.DIRECTIONS),
        KanjiItem(27, "立", "た", "つりかわにつかまって立つ", KanjiCategory.DIRECTIONS),

        // NATURE (28-40)
        KanjiItem(28, "山", "やま", "山が見えてきた", KanjiCategory.NATURE),
        KanjiItem(29, "川", "かわ", "川をわたるてっきょう", KanjiCategory.NATURE),
        KanjiItem(30, "森", "もり", "森の中をはしる", KanjiCategory.NATURE),
        KanjiItem(31, "林", "はやし", "林をとおりぬける", KanjiCategory.NATURE),
        KanjiItem(32, "花", "はな", "花がせんろぞいにさく", KanjiCategory.NATURE),
        KanjiItem(33, "草", "くさ", "草のうえをはしるでん車", KanjiCategory.NATURE),
        KanjiItem(34, "竹", "たけ", "竹やぶのよこをとおる", KanjiCategory.NATURE),
        KanjiItem(35, "空", "そら", "空がひろく見える", KanjiCategory.NATURE),
        KanjiItem(36, "雨", "あめ", "雨の日のでん車", KanjiCategory.NATURE),
        KanjiItem(37, "天", "てん", "天気がいいとよく見える", KanjiCategory.NATURE),
        KanjiItem(38, "気", "き", "気をつけてごじょう車ください", KanjiCategory.NATURE),
        KanjiItem(39, "石", "いし", "石でできたふるいえき", KanjiCategory.NATURE),
        KanjiItem(40, "田", "た", "田んぼのよこをとおる", KanjiCategory.NATURE),

        // PEOPLE (41-52)
        KanjiItem(41, "人", "ひと", "たくさんの人がのる", KanjiCategory.PEOPLE),
        KanjiItem(42, "男", "おとこ", "男の人がせきをゆずる", KanjiCategory.PEOPLE),
        KanjiItem(43, "女", "おんな", "女の人がのってきた", KanjiCategory.PEOPLE),
        KanjiItem(44, "子", "こ", "子どもがまどから見る", KanjiCategory.PEOPLE),
        KanjiItem(45, "大", "おお", "大きなえきにとまる", KanjiCategory.PEOPLE),
        KanjiItem(46, "小", "ちい", "小さなえきをつうかする", KanjiCategory.PEOPLE),
        KanjiItem(47, "先", "せん", "先とう車りょうにのる", KanjiCategory.PEOPLE),
        KanjiItem(48, "生", "せい", "学生がでん車でかよう", KanjiCategory.PEOPLE),
        KanjiItem(49, "名", "な", "えきの名まえをよむ", KanjiCategory.PEOPLE),
        KanjiItem(50, "学", "がく", "見学ででん車のしゃこに行く", KanjiCategory.PEOPLE),
        KanjiItem(51, "校", "こう", "学校のちかくのえき", KanjiCategory.PEOPLE),
        KanjiItem(52, "休", "やす", "休みの日にでん車たび", KanjiCategory.PEOPLE),

        // BODY (53-58)
        KanjiItem(53, "目", "め", "目でしんごうを見る", KanjiCategory.BODY),
        KanjiItem(54, "耳", "みみ", "耳でアナウンスをきく", KanjiCategory.BODY),
        KanjiItem(55, "口", "くち", "でん車の中で口をとじる", KanjiCategory.BODY),
        KanjiItem(56, "手", "て", "手すりにつかまる", KanjiCategory.BODY),
        KanjiItem(57, "足", "あし", "足もとにちゅうい", KanjiCategory.BODY),
        KanjiItem(58, "見", "み", "しゃそうからけしきを見る", KanjiCategory.BODY),

        // COLORS (59-64)
        KanjiItem(59, "赤", "あか", "赤いでん車がきた", KanjiCategory.COLORS),
        KanjiItem(60, "青", "あお", "青いでん車にのる", KanjiCategory.COLORS),
        KanjiItem(61, "白", "しろ", "白いしんかんせん", KanjiCategory.COLORS),
        KanjiItem(62, "玉", "たま", "玉のようなしゃりん", KanjiCategory.COLORS),
        KanjiItem(63, "王", "おう", "でん車の王さまはしんかんせん", KanjiCategory.COLORS),
        KanjiItem(64, "円", "えん", "まるい円のかたちのまど", KanjiCategory.COLORS),

        // THINGS (65-80)
        KanjiItem(65, "車", "しゃ", "車りょうの中をあるく", KanjiCategory.THINGS),
        KanjiItem(66, "音", "おと", "でん車の音がきこえる", KanjiCategory.THINGS),
        KanjiItem(67, "本", "ほん", "でん車で本をよむ", KanjiCategory.THINGS),
        KanjiItem(68, "文", "ぶん", "あんないの文をよむ", KanjiCategory.THINGS),
        KanjiItem(69, "字", "じ", "えきめいの字をよむ", KanjiCategory.THINGS),
        KanjiItem(70, "力", "ちから", "でん車は力づよくはしる", KanjiCategory.THINGS),
        KanjiItem(71, "虫", "むし", "虫がまどにとまった", KanjiCategory.THINGS),
        KanjiItem(72, "犬", "いぬ", "犬をつれてでん車にのる", KanjiCategory.THINGS),
        KanjiItem(73, "糸", "いと", "がせんは糸のよう", KanjiCategory.THINGS),
        KanjiItem(74, "貝", "かい", "貝かえきでおりる", KanjiCategory.THINGS),
        KanjiItem(75, "正", "ただ", "正しいホームに行く", KanjiCategory.THINGS),
        KanjiItem(76, "年", "ねん", "一年生がでん車にのる", KanjiCategory.THINGS),
        KanjiItem(77, "早", "はや", "早いでん車にのりたい", KanjiCategory.THINGS),
        KanjiItem(78, "夕", "ゆう", "夕がたのラッシュ", KanjiCategory.THINGS),
        KanjiItem(79, "町", "まち", "町のえきでおりる", KanjiCategory.THINGS),
        KanjiItem(80, "村", "むら", "村をとおるろせん", KanjiCategory.THINGS),
    )

    fun getByCategory(category: KanjiCategory): List<KanjiItem> {
        return allKanji.filter { it.category == category }
    }

    fun getById(id: Int): KanjiItem? {
        return allKanji.find { it.id == id }
    }

    /**
     * 選択肢を生成する（同じカテゴリから優先的に選択）
     */
    fun generateChoices(correctKanji: KanjiItem): List<String> {
        val choices = mutableListOf(correctKanji.reading)

        // 同じカテゴリから選択肢を取得
        val sameCategory = getByCategory(correctKanji.category)
            .filter { it.reading != correctKanji.reading }
            .map { it.reading }
            .shuffled()

        for (reading in sameCategory) {
            if (choices.size >= 4) break
            if (reading !in choices) choices.add(reading)
        }

        // 同カテゴリで足りない場合は他カテゴリから補充
        if (choices.size < 4) {
            val otherReadings = allKanji
                .filter { it.category != correctKanji.category }
                .map { it.reading }
                .shuffled()

            for (reading in otherReadings) {
                if (choices.size >= 4) break
                if (reading !in choices) choices.add(reading)
            }
        }

        return choices.shuffled()
    }
}
