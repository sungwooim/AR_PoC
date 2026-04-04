package com.example.ar_poc.data.heritage

import com.example.ar_poc.domain.model.HeritageChunk
import com.example.ar_poc.domain.model.HeritageContent
import com.example.ar_poc.domain.model.Poi
import com.example.ar_poc.domain.model.PoiType
import com.example.ar_poc.domain.model.SubElement

/**
 * Implementation of HeritageKnowledgeSource using local data.
 */
class LocalHeritageKnowledgeSource : HeritageKnowledgeSource {
    private val heritageList = listOf(
        HeritageContent(
            id = "geunjeongjeon",
            title = "근정전 (Geunjeongjeon)",
            titleMap = mapOf(
                "ko" to "근정전 (Geunjeongjeon)",
                "en" to "Geunjeongjeon Hall",
                "ja" to "勤政殿 (クンジョンジョン)",
                "zh" to "勤政殿"
            ),
            aliases = listOf("근정전", "Geunjeongjeon", "Geunjeongjeon Hall"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "main_court",
            shortDescription = "조선 왕조의 위엄을 상징하며 국가의 중차대한 의례가 거행되던 경복궁의 중심 정전입니다.",
            shortDescMap = mapOf(
                "ko" to "조선 왕조의 위엄을 상징하며 국가의 중차대한 의례가 거행되던 경복궁의 중심 정전입니다.",
                "en" to "The main hall of Gyeongbokgung Palace, symbolizing the dignity of the Joseon Dynasty, where important national state ceremonies were held.",
                "ja" to "朝鮮王朝の威厳を象徴し、国家の重要な儀式が行われた景福宮の中心となる正殿です。",
                "zh" to "勤政殿是景福宫的正殿，象征着朝鲜王朝的威严，是举行国家重大仪式的核心建筑。"
            ),
            latitude = 37.5796,
            longitude = 126.9770,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "geun_01",
                    section = "역사",
                    title = "조선 왕조의 위엄, 근정전",
                    titleMap = mapOf(
                        "ko" to "조선 왕조의 위엄, 근정전",
                        "en" to "The Dignity of the Joseon Dynasty, Geunjeongjeon",
                        "ja" to "朝鮮王朝の威厳、勤政殿",
                        "zh" to "朝鲜王朝的威严，勤政殿"
                    ),
                    keywords = listOf("국보", "정전", "왕의 공간", "중건"),
                    content = "근정전은 경복궁의 정전(正殿)이자 조선 왕조를 상징하는 가장 중심적인 건물입니다. '천하의 일을 부지런히 하여 잘 다스리다'라는 의미를 담고 있으며, 이곳에서 왕의 즉위식, 신하들의 하례, 외국 사신의 접대 등 국가의 중요한 공식 행사가 거행되었습니다. 1395년에 창건되었으나 임진왜란 때 소실되었고, 현재의 건물은 1867년 흥선대원군에 의해 중건된 모습입니다.",
                    contentMap = mapOf(
                        "ko" to "근정전은 경복궁의 정전(正殿)이자 조선 왕조를 상징하는 가장 중심적인 건물입니다. '천하의 일을 부지런히 하여 잘 다스리다'라는 의미를 담고 있으며, 이곳에서 왕의 즉위식, 신하들의 하례, 외국 사신의 접대 등 국가의 중요한 공식 행사가 거행되었습니다. 1395년에 창건되었으나 임진왜란 때 소실되었고, 현재의 건물은 1867년 흥선대원군에 의해 중건된 모습입니다.",
                        "en" to "Geunjeongjeon is the main hall of Gyeongbokgung Palace and the central building symbolizing the Joseon Dynasty. Meaning 'to govern diligently concerning the affairs of the world', it was the venue for important official state events such as the king's coronation, audiences with officials, and the reception of foreign envoys. Built in 1395, it was destroyed during the Imjin War (Japanese invasions), and the current building was reconstructed in 1867 by Heungseon Daewongun.",
                        "ja" to "勤政殿は景福宮の正殿であり、朝鮮王朝を象徴する最も中心的な建物です。「天下の事を勤勉に行ってよく治める」という意味が込められており、ここで王の即位式、家臣の参賀、外国使節の接待などの重要な国家の公式行事が執り行われました。1395年に創建されましたが、文禄・慶長の役で焼失し、現在の建物は1867年に興宣大院君によって再建されたものです。",
                        "zh" to "勤政殿是景福宫的正殿，也是象征朝鲜王朝的最核心建筑。其名意为“勤奋治理天下之事”，在此举行了国王登基仪式、百官朝贺、接待外国使臣等重要的国家官方活动。始建于1395年，在壬辰倭乱中被烧毁，现在的建筑是1867年兴宣大院君重建的面貌。"
                    )
                ),
                HeritageChunk(
                    chunkId = "geun_02",
                    section = "건축",
                    title = "한국 최대의 목조건축물",
                    titleMap = mapOf(
                        "ko" to "한국 최대의 목조건축물",
                        "en" to "Korea's Largest Wooden Structure",
                        "ja" to "韓国最大の木造建築物",
                        "zh" to "韩国最大的木造建筑"
                    ),
                    keywords = listOf("목조건축", "국보 제223호", "어좌", "일월오봉도"),
                    content = "국보 제223호인 근정전은 한국 목조 건축의 정수를 보여줍니다. 웅장한 이중 화강암 기단(월대) 위에 세워진 중층(2층) 구조의 건물로, 내부는 천장까지 탁 트인 통층 구조를 취하고 있습니다. 중앙 깊숙한 곳에는 왕의 자리인 어좌가 있으며, 그 뒤로는 해와 달, 다섯 봉우리를 그린 '일월오봉도' 병풍이 놓여 있어 왕의 위엄을 상징합니다.",
                    contentMap = mapOf(
                        "ko" to "국보 제223호인 근정전은 한국 목조 건축의 정수를 보여줍니다. 웅장한 이중 화강암 기단(월대) 위에 세워진 중층(2층) 구조의 건물로, 내부는 천장까지 탁 트인 통층 구조를 취하고 있습니다. 중앙 깊숙한 곳에는 왕의 자리인 어좌가 있으며, 그 뒤로는 해와 달, 다섯 봉우리를 그린 '일월오봉도' 병풍이 놓여 있어 왕의 위엄을 상징합니다.",
                        "en" to "Designated as National Treasure No. 223, Geunjeongjeon showcases the essence of Korean wooden architecture. An imposing double-tiered granite platform (Woldae) supports the two-story structure, giving it a spacious, open interior reaching up to the ceiling. Deep at the center lies the King's throne (Eojwa), behind which stands the 'Irworobongdo' (Sun, Moon, and Five Peaks) folding screen, symbolizing the king's supreme authority.",
                        "ja" to "国宝第223号である勤政殿は、韓国の木造建築の真髄を示しています。壮大な二重の花崗岩の基壇（月台）の上に建てられた重層（2階）構造の建物で、内部は天井まで吹き抜けになっています。中央の奥深い場所には王の席である御座があり、その後ろには太陽と月、5つの峰を描いた「日月五峰図」の屏風が置かれ、王の威厳を象徴しています。",
                        "zh" to "国宝第223号勤政殿展现了韩国木造建筑的精髓。这是建在宏伟的双层花岗岩基台（月台）上的重重（两层）结构建筑，内部是直达天花板的通层结构。中央深处是国王的宝座“御座”，其后放置着描绘日月和五座山峰的《日月五峰图》屏风，象征着国王的无上威严。"
                    )
                ),
                HeritageChunk(
                    chunkId = "geun_03",
                    section = "관람 포인트",
                    title = "바닥 돌과 쇠고리의 비밀",
                    titleMap = mapOf(
                        "ko" to "바닥 돌과 쇠고리의 비밀",
                        "en" to "The Secret of the Floor Stones and Iron Rings",
                        "ja" to "床石と鉄の輪の秘密",
                        "zh" to "地石与铁环的秘密"
                    ),
                    keywords = listOf("박석", "품계석", "차일"),
                    content = "마당 바닥에 깔린 울퉁불퉁한 돌 '박석'은 햇빛이 눈에 반사되는 것을 막고 신하들의 가죽신이 미끄러지지 않게 하는 선조들의 지혜가 담겨 있습니다. 박석 사이사이에 박힌 무거운 쇠고리는 행사 때 햇빛을 가리는 천막인 '차일'을 고정하던 장치입니다.",
                    contentMap = mapOf(
                        "ko" to "마당 바닥에 깔린 울퉁불퉁한 돌 '박석'은 햇빛이 눈에 반사되는 것을 막고 신하들의 가죽신이 미끄러지지 않게 하는 선조들의 지혜가 담겨 있습니다. 박석 사이사이에 박힌 무거운 쇠고리는 행사 때 햇빛을 가리는 천막인 '차일'을 고정하던 장치입니다.",
                        "en" to "The uneven stones called 'Bakseok', which pave the courtyard floor, contain the wisdom of ancestors: they prevent the sunlight from reflecting directly into the eyes and keeping officials' leather shoes from slipping. The heavy iron rings embedded between the Bakseok were devices used to set up 'Chail', a tent to block out the sunlight during large-scale events.",
                        "ja" to "中庭の床に敷かれているでこぼこした石「薄石（パクソク）」には、日光の反射を防ぎ、臣下たちの革靴がすべらないようにする先人たちの知恵が込められています。薄石の間に埋め込まれた重い鉄の輪は、行事の際に日差しを遮るテントである「遮日（チャイル）」を固定するための装置です。",
                        "zh" to "铺在庭院地上的凹凸不平的石头叫做“薄石”，它能防止阳光反射刺眼，还能防止大臣们的皮鞋打滑，蕴含着先人的智慧。薄石之间镶嵌的沉重铁环，是在举行活动时用来固定遮阳帐篷“遮日”的装置。"
                    )
                ),
                HeritageChunk(
                    chunkId = "geun_04",
                    section = "트리비아",
                    title = "천장에 숨겨진 칠조룡",
                    titleMap = mapOf(
                        "ko" to "천장에 숨겨진 칠조룡",
                        "en" to "The Seven-Clawed Dragon Hidden in the Ceiling",
                        "ja" to "天井に隠された七爪竜",
                        "zh" to "隐藏在天花板上的七爪龙"
                    ),
                    keywords = listOf("황룡", "발톱", "왕권"),
                    content = "근정전 내부 천장 중앙을 올려다보면 황금색 용 두 마리가 조각되어 있습니다. 보통 용은 발톱이 5개인 오조룡이지만, 이곳의 용은 발톱이 7개인 칠조룡입니다. 이는 중건 당시 조선이 강력한 자주국임을 상징하기 위해 특별히 제작된 것입니다.",
                    contentMap = mapOf(
                        "ko" to "근정전 내부 천장 중앙을 올려다보면 황금색 용 두 마리가 조각되어 있습니다. 보통 용은 발톱이 5개인 오조룡이지만, 이곳의 용은 발톱이 7개인 칠조룡입니다. 이는 중건 당시 조선이 강력한 자주국임을 상징하기 위해 특별히 제작된 것입니다.",
                        "en" to "If you look up at the center of the Geunjeongjeon ceiling, you will see two sculpted golden dragons. While typical dragons have five claws, these distinct ones boast seven. They were specially crafted during the reconstruction to symbolize Joseon's status as a powerful, independent nation.",
                        "ja" to "勤政殿内部の天井中央を見上げると、黄金色の龍が二匹彫刻されています。通常の龍は爪が5つですが、ここの龍は爪が7つあります。これは再建当時、朝鮮が強力な自主国であることを象徴するために特別に製作されたものです。",
                        "zh" to "抬头仰望勤政殿内部的天花板中央，会看到雕刻着两条金黄色的龙。普通的龙通常有5个爪子，但这里的龙却有7个爪子。这是在重建时，为了象征朝鲜是强大的自主国家而特别制作的。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "geun_signboard",
                    displayName = mapOf("ko" to "근정전 현판", "en" to "Geunjeongjeon Signboard", "ja" to "勤政殿の扁額", "zh" to "勤政殿匾额"),
                    visualHints = listOf("large wooden sign with korean characters on building", "palace hall name plaque", "rectangular nameplate mounted on top of main hall"),
                    linkedChunkId = "geun_01"
                ),
                SubElement(
                    id = "geun_iron_ring",
                    displayName = mapOf("ko" to "차일고리 (마당 쇠고리)", "en" to "Chail Iron Ring", "ja" to "遮日の鉄輪", "zh" to "遮日铁环"),
                    visualHints = listOf("metal iron ring embedded in stone floor", "rusty metal loop on stone pavement", "iron ring for chail on bakseok"),
                    linkedChunkId = "geun_03"
                ),
                SubElement(
                    id = "geun_rank_stone",
                    displayName = mapOf("ko" to "품계석 (직급 표시돌)", "en" to "Rank Stone", "ja" to "品階石", "zh" to "品阶石"),
                    visualHints = listOf("small stone pillar with korean characters", "stone marker lined up in courtyard", "carved stone post in palace courtyard"),
                    linkedChunkId = "geun_03"
                ),
                SubElement(
                    id = "geun_zodiac_statue",
                    displayName = mapOf("ko" to "월대 십이지신상", "en" to "Zodiac Animal Statue", "ja" to "月台 十二支神像", "zh" to "月台 十二生肖像"),
                    visualHints = listOf("stone carved animal statue on balustrade", "zodiac animal stone sculpture on stone fence", "stone guardian animal figure on stairs"),
                    linkedChunkId = "geun_02"
                ),
                SubElement(
                    id = "geun_chiljolyong",
                    displayName = mapOf("ko" to "칠조룡 (천장 7발톱 용)", "en" to "Seven-clawed Dragon", "ja" to "七爪龍", "zh" to "七爪龙"),
                    visualHints = listOf("golden dragon sculpture on ceiling", "dragon with seven claws on ceiling center", "carved golden dragon looking down from ceiling"),
                    linkedChunkId = "geun_04"
                ),
                SubElement(
                    id = "geun_deumu",
                    displayName = mapOf("ko" to "드무 (화재맥이 청동솥)", "en" to "Deumu (Bronze Water Vat)", "ja" to "ドゥム (防火用青銅釜)", "zh" to "防火青铜鼎"),
                    visualHints = listOf("large bronze cauldron in palace courtyard", "massive metal water vat near building", "wide iron pot on stone base for fire prevention"),
                    linkedChunkId = "geun_02"
                )
            )
        ),
        HeritageContent(
            id = "gyeonghoeru",
            title = "경회루 (Gyeonghoeru)",
            titleMap = mapOf(
                "ko" to "경회루 (Gyeonghoeru)",
                "en" to "Gyeonghoeru Pavilion",
                "ja" to "慶会楼 (キョンフェル)",
                "zh" to "庆会楼"
            ),
            aliases = listOf("경회루", "Gyeonghoeru", "Gyeonghoeru Pavilion"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "pavilion_area",
            shortDescription = "연못 위에 떠 있는 듯한 아름다움을 자랑하며 외교 사신 접대와 연회가 열리던 조선 최대의 누각입니다.",
            shortDescMap = mapOf(
                "ko" to "연못 위에 떠 있는 듯한 아름다움을 자랑하며 외교 사신 접대와 연회가 열리던 조선 최대의 누각입니다.",
                "en" to "The largest pavilion of the Joseon Dynasty, celebrated for its ethereal beauty rising above an artificial pond, where banquets and diplomatic receptions were held.",
                "ja" to "人工池の上に浮かぶかのような美しさを誇り、外交使節の接待や宴会が開かれた朝鮮最大の楼閣です。",
                "zh" to "庆会楼是朝鲜时代最大的楼阁，宛如漂浮在人工池上，是举行宴会和接待外交使节的场所。"
            ),
            latitude = 37.5800,
            longitude = 126.9749,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "gy_01",
                    section = "역사",
                    title = "왕실의 연회장, 경회루",
                    titleMap = mapOf(
                        "ko" to "왕실의 연회장, 경회루",
                        "en" to "The Royal Banquet Hall, Gyeonghoeru",
                        "ja" to "王室の宴会場、慶会楼",
                        "zh" to "王室的宴会厅，庆会楼"
                    ),
                    keywords = listOf("국보", "연회", "사신 접대"),
                    content = "경회루는 인공 연못 위에 세워진 2층 누각으로, 나라에 경사가 있을 때 연회를 베풀기 위해 지어진 곳입니다. 이름 그대로 '임금과 신하가 덕으로써 만나는 즐거운 모임'을 위해 지어졌습니다. 평상시에는 출입이 엄격히 금지되었으나, 나라에 경사가 있거나 외국 사신이 방문했을 때 임금이 성대한 연회를 베풀던 공식적인 외교 공간이었습니다.",
                    contentMap = mapOf(
                        "ko" to "경회루는 인공 연못 위에 세워진 2층 누각으로, 나라에 경사가 있을 때 연회를 베풀기 위해 지어진 곳입니다. 이름 그대로 '임금과 신하가 덕으로써 만나는 즐거운 모임'을 위해 지어졌습니다. 평상시에는 출입이 엄격히 금지되었으나, 나라에 경사가 있거나 외국 사신이 방문했을 때 임금이 성대한 연회를 베풀던 공식적인 외교 공간이었습니다.",
                        "en" to "Gyeonghoeru is a two-story pavilion built on an artificial pond, constructed for royal banquets during times of national celebration. True to its name, meaning 'the joyful gathering of the king and officials through virtue,' access was strictly forbidden ordinarily. It served as an official diplomatic venue for grand feasts hosted by the king when the nation celebrated or welcomed foreign envoys.",
                        "ja" to "慶会楼は人工の池の上に建てられた2階建ての楼閣で、国の慶事があった際に宴会を催すために建てられた場所です。その名の通り「王と臣下が徳をもって出会う喜ばしい集い」のために建てられました。普段は出入りが厳しく禁じられていましたが、国の慶事や外国使節の訪問時に王が盛大な宴会を開いた公式の外交空間でした。",
                        "zh" to "庆会楼是建在人工池上的二层楼阁，是为国家有喜事时举办宴会而建造的地方。正如其名，意为'国王与臣子以德相会的欢乐聚会'。平时严禁出入，但当国家有喜事或外国使节来访时，国王会在此举办盛大宴会，是正式的外交场所。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gy_02",
                    section = "건축",
                    title = "하늘과 땅을 품은 48개의 돌기둥",
                    titleMap = mapOf(
                        "ko" to "하늘과 땅을 품은 48개의 돌기둥",
                        "en" to "48 Stone Pillars Embodying Heaven and Earth",
                        "ja" to "天と地を象徴する48本の石柱",
                        "zh" to "包含天地之义的48根石柱"
                    ),
                    keywords = listOf("누각", "돌기둥", "연못", "차경"),
                    content = "인공 연못 위에 세워진 경회루는 48개의 거대한 돌기둥이 지탱하고 있습니다. 바깥쪽 기둥은 사각형(땅)으로, 안쪽 기둥은 원형(하늘)으로 만들어 동양의 천원지방 사상을 반영했습니다. 2층 누각에 오르면 창틀이 마치 액자가 되어 주변 산과 연못의 풍경을 담아내는 '차경(경치를 빌려옴)의 미학'을 감상할 수 있습니다.",
                    contentMap = mapOf(
                        "ko" to "인공 연못 위에 세워진 경회루는 48개의 거대한 돌기둥이 지탱하고 있습니다. 바깥쪽 기둥은 사각형(땅)으로, 안쪽 기둥은 원형(하늘)으로 만들어 동양의 천원지방 사상을 반영했습니다. 2층 누각에 오르면 창틀이 마치 액자가 되어 주변 산과 연못의 풍경을 담아내는 '차경(경치를 빌려옴)의 미학'을 감상할 수 있습니다.",
                        "en" to "Gyeonghoeru stands on an artificial pond, supported by 48 massive stone pillars. The outer pillars are square (representing Earth) and the inner pillars are round (representing Heaven), reflecting the Eastern concept of 'round heaven, square earth' (Cheonwonjibang). Ascending to the second floor, the window frames act as picture frames capturing the surrounding landscape — the aesthetic of 'Chaegyeong' (borrowing scenery).",
                        "ja" to "人工の池の上に建てられた慶会楼は、48本の巨大な石柱が支えています。外側の柱は四角形（地）で、内側の柱は円形（天）に作られており、東洋の天円地方思想を反映しています。2階に上ると、窓枠が額縁のようになって、周囲の山と池の風景を取り込む「借景の美学」を楽しむことができます。",
                        "zh" to "建于人工池上的庆会楼由48根巨大的石柱支撑。外侧柱子为四方形（象征地），内侧柱子为圆形（象征天），体现了东方的'天圆地方'思想。登上二层楼阁，窗框犹如画框，将周围的山川和池塘的景色纳入其中，可以欣赏到'借景'的美学。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gy_03",
                    section = "관람 포인트",
                    title = "연못 건너편의 반영",
                    titleMap = mapOf(
                        "ko" to "연못 건너편의 반영",
                        "en" to "The Reflection Across the Pond",
                        "ja" to "池の対岸の映り込み",
                        "zh" to "池塘对岸的倒影"
                    ),
                    keywords = listOf("사진명소", "반영", "인왕산"),
                    content = "경회루의 가장 아름다운 모습은 연못 건너편 담장에서 바라볼 때입니다. 바람이 없는 날, 잔잔한 수면에 거울처럼 비치는 경회루의 반영과 그 뒤로 펼쳐지는 인왕산의 실루엣은 경복궁 최고의 사진 명소로 손꼽힙니다.",
                    contentMap = mapOf(
                        "ko" to "경회루의 가장 아름다운 모습은 연못 건너편 담장에서 바라볼 때입니다. 바람이 없는 날, 잔잔한 수면에 거울처럼 비치는 경회루의 반영과 그 뒤로 펼쳐지는 인왕산의 실루엣은 경복궁 최고의 사진 명소로 손꼽힙니다.",
                        "en" to "The most beautiful view of Gyeonghoeru is seen from the wall across the pond. On a calm, windless day, the mirror-like reflection of the pavilion on the still water, with the silhouette of Inwangsan Mountain behind it, is considered one of Gyeongbokgung's best photography spots.",
                        "ja" to "慶会楼の最も美しい姿は、池の対岸の塀から眺めるときです。風のない日、穏やかな水面に鏡のように映る慶会楼の映り込みと、その後ろに広がる仁王山のシルエットは、景福宮最高の写真スポットとして知られています。",
                        "zh" to "庆会楼最美的景色是从池塘对面的围墙处眺望时。在无风的日子里，平静的水面上如镜子般倒映着庆会楼，其后方展开仁王山的轮廓，被称为景福宫最佳的拍照胜地。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gy_04",
                    section = "트리비아",
                    title = "연못 속의 구룡(九龍)",
                    titleMap = mapOf(
                        "ko" to "연못 속의 구룡(九龍)",
                        "en" to "The Bronze Dragons Hidden in the Pond",
                        "ja" to "池の中の銅龍",
                        "zh" to "池塘里的铜龙"
                    ),
                    keywords = listOf("화재예방", "청동룡", "전설"),
                    content = "목조 건물인 경회루를 화재로부터 보호하기 위해 선조들은 연못 속에 두 마리의 청동 용을 넣었다는 기록이 전해집니다. 실제로 1997년 연못 준설 작업 도중 청동 용 한 마리가 발견되어 현재 국립고궁박물관에 전시되어 있으며, 이는 조상들이 화재 예방을 위해 얼마나 정성을 들였는지 보여줍니다.",
                    contentMap = mapOf(
                        "ko" to "목조 건물인 경회루를 화재로부터 보호하기 위해 선조들은 연못 속에 두 마리의 청동 용을 넣었다는 기록이 전해집니다. 실제로 1997년 연못 준설 작업 도중 청동 용 한 마리가 발견되어 현재 국립고궁박물관에 전시되어 있으며, 이는 조상들이 화재 예방을 위해 얼마나 정성을 들였는지 보여줍니다.",
                        "en" to "Records indicate that ancestors placed two bronze dragons in the pond to protect the wooden Gyeonghoeru from fire. In fact, during dredging work in 1997, one bronze dragon was discovered and is now displayed at the National Palace Museum of Korea, demonstrating the great care ancestors took for fire prevention.",
                        "ja" to "木造建築である慶会楼を火災から守るために、先人たちは池の中に2匹の青銅の龍を入れたという記録が伝わっています。実際に1997年の池の浚渫工事中に青銅の龍が1匹発見され、現在は国立古宮博物館に展示されており、火災予防のために先祖がどれほど心を尽くしたかを示しています。",
                        "zh" to "据记载，为了保护木造建筑庆会楼免遭火灾，先人们在池塘中放入了两条铜龙。1997年池塘疏浚工程中，发现了一条铜龙，目前在国立故宫博物馆展出，展示了先祖们为防火所付出的精心努力。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "gy_pond",
                    displayName = mapOf("ko" to "경회루 연못", "en" to "Gyeonghoeru Pond", "ja" to "慶会楼の池", "zh" to "庆会楼池塘"),
                    visualHints = listOf("large rectangular palace pond with pavilion reflection", "still water reflecting wooden pavilion", "lotus pond with stone embankment"),
                    linkedChunkId = "gy_03"
                ),
                SubElement(
                    id = "gy_japsang",
                    displayName = mapOf("ko" to "잡상 (지붕 위 수호신)", "en" to "Japsang (Roof Figures)", "ja" to "雑像 (屋根の守り神)", "zh" to "杂像 (屋顶守护神)"),
                    visualHints = listOf("small clay animal figures lining roof ridge", "ceramic monkey and monk statues on palace roof edge", "roof edge guardian figures"),
                    linkedChunkId = "gy_01"
                ),
                SubElement(
                    id = "gy_square_pillar",
                    displayName = mapOf("ko" to "외곽 사각 기둥", "en" to "Outer Square Pillar", "ja" to "外郭の四角柱", "zh" to "外侧方柱"),
                    visualHints = listOf("square shaped stone column supporting pavilion", "huge rectangular stone pillars standing in water", "flat angled stone columns"),
                    linkedChunkId = "gy_02"
                ),
                SubElement(
                    id = "gy_round_pillar",
                    displayName = mapOf("ko" to "내부 원형 기둥", "en" to "Inner Round Pillar", "ja" to "内部の円柱", "zh" to "内侧圆柱"),
                    visualHints = listOf("cylindrical round stone column supporting pavilion", "smooth circular stone pillars under pavilion", "round column standing on stone base"),
                    linkedChunkId = "gy_02"
                )
            )
        ),
        HeritageContent(
            id = "gwanghwamun",
            title = "광화문 (Gwanghwamun)",
            titleMap = mapOf(
                "ko" to "광화문 (Gwanghwamun)",
                "en" to "Gwanghwamun Gate",
                "ja" to "光化門 (クァンファムン)",
                "zh" to "光化门"
            ),
            aliases = listOf("광화문", "Gwanghwamun", "Gwanghwamun Gate"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "entrance",
            shortDescription = "경복궁의 남쪽 정문이자 조선의 수도 한양의 중심적인 관문으로, 민족의 역사를 함께해 온 상징입니다.",
            shortDescMap = mapOf(
                "ko" to "경복궁의 남쪽 정문이자 조선의 수도 한양의 중심적인 관문으로, 민족의 역사를 함께해 온 상징입니다.",
                "en" to "The southern main gate of Gyeongbokgung Palace and the central gateway of Hanyang, Joseon's capital, serving as a symbol that has shared the nation's history.",
                "ja" to "景福宮の南側正門であり、朝鮮の都漢陽の中心的な関門として、民族の歴史を共に歩んできた象徴です。",
                "zh" to "景福宫南侧正门，也是朝鲜都城汉阳的核心关口，是与民族历史共同前行的象征。"
            ),
            latitude = 37.5759,
            longitude = 126.9769,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "gw_01",
                    section = "역사",
                    title = "수난과 복원의 역사",
                    keywords = listOf("정문", "수난", "복원", "상징"),
                    content = "광화문은 경복궁의 정문으로, '빛이 사방을 덮고 교화가 만방에 미친다'는 뜻을 가지고 있습니다. 임진왜란 때 소실된 후 고종 때 중건되었으나, 일제강점기에 조선총독부 건물을 짓기 위해 강제로 위치가 옮겨지고 한국전쟁 때는 문루가 파괴되는 등 수많은 시련을 겪었습니다. 현재의 모습은 2010년, 잘못된 위치와 재질을 바로잡아 원래의 자리에 전통 방식대로 복원한 것입니다."
                ),
                HeritageChunk(
                    chunkId = "gw_02",
                    section = "건축",
                    title = "웅장한 세 개의 무지개문",
                    keywords = listOf("홍예문", "문루", "석축"),
                    content = "광화문은 높은 석축 기단 위에 2층 규모의 문루가 세워진 웅장한 구조입니다. 기단부에는 세 개의 홍예문(무지개 모양 문)이 있는데, 가운데 문은 왕이 다니는 문이었으며 좌우 문은 신하들이 출입하던 문이었습니다. 기단부의 거대한 돌들은 조선 시대 석공 기술의 견고함을 잘 보여줍니다."
                ),
                HeritageChunk(
                    chunkId = "gw_03",
                    section = "관람 가이드",
                    title = "수문장 교대 의식",
                    keywords = listOf("수문장", "교대식", "전통문화"),
                    content = "매일 정해진 시간(오전 10시, 오후 2시)에 광화문 앞에서 열리는 수문장 교대 의식은 놓치지 말아야 할 볼거리입니다. 조선 시대 복식과 무기를 그대로 재현한 수문군들의 절도 있는 동작을 통해 당시의 성문 파수 문화를 생생하게 체험할 수 있습니다."
                ),
                HeritageChunk(
                    chunkId = "gw_04",
                    section = "트리비아",
                    title = "불을 먹는 상상의 동물, 해태",
                    keywords = listOf("해태", "화기", "정의"),
                    content = "광화문 앞 좌우에는 한 쌍의 해태상이 자리하고 있습니다. 해태는 시비곡직을 가리는 정의로운 동물이기도 하지만, 불을 먹는 능력이 있다고 믿어졌습니다. 이는 풍수지리상 화기가 강한 관악산의 기운으로부터 경복궁을 지키기 위한 조상들의 비보(裨補)책이었습니다."
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "gw_arches",
                    displayName = mapOf("ko" to "세 개의 홍예문 (무지개 아치)", "en" to "Three Rainbow Arches", "ja" to "3つの虹霓門", "zh" to "三个彩虹拱门"),
                    visualHints = listOf("three arched stone gate passages", "large stone gate with three arch openings", "stone wall with three rounded archways"),
                    linkedChunkId = "gw_02"
                ),
                SubElement(
                    id = "gw_haetae",
                    displayName = mapOf("ko" to "해태 석상", "en" to "Haetae Stone Statue", "ja" to "ヘテ石像", "zh" to "獬豸石像"),
                    visualHints = listOf("stone mythical lion-like creature statue", "seated stone animal sculpture at gate entrance", "carved stone haechi guardian statue"),
                    linkedChunkId = "gw_04"
                ),
                SubElement(
                    id = "gw_hongyemun_painting",
                    displayName = mapOf("ko" to "홍예문 천장 그림 (주작)", "en" to "Archway Ceiling Painting", "ja" to "虹霓門 天井画 (朱雀)", "zh" to "虹霓门 天花板壁画 (朱雀)"),
                    visualHints = listOf("colorful mythical bird painting on archway ceiling", "painted Vermilion Bird inside stone arch", "ceiling mural inside palace gate"),
                    linkedChunkId = "gw_02"
                ),
                SubElement(
                    id = "gw_signboard",
                    displayName = mapOf("ko" to "광화문 현판", "en" to "Gwanghwamun Signboard", "ja" to "光化門の扁額", "zh" to "光化门匾额"),
                    visualHints = listOf("large wooden sign with Korean characters on gate", "gate name plaque with black calligraphy", "rectangular nameplate mounted on top of gate"),
                    linkedChunkId = "gw_01"
                ),
                SubElement(
                    id = "gw_sumunjang",
                    displayName = mapOf("ko" to "수문장 교대식", "en" to "Royal Guard Changing Ceremony", "ja" to "守門将交代式", "zh" to "守门将换岗仪式"),
                    visualHints = listOf("royal guard ceremony in traditional costume", "soldiers in colorful Joseon dynasty uniforms", "guard changing ceremony with flags and weapons"),
                    linkedChunkId = "gw_03"
                )
            )
        )
    )

    override fun getHeritageById(id: String): HeritageContent? =
        heritageList.find { it.id.equals(id, ignoreCase = true) }

    override fun getHeritageList(): List<HeritageContent> =
        heritageList

    // ─────────────────────────────────────────────────────────────────────
    // 유산 외 일반 POI 샘플 데이터 (카페, 화장실, 전망 포인트, 매표소 등)
    // ─────────────────────────────────────────────────────────────────────
    private val extraPoiList: List<Poi> = listOf(

        // ── 카페 / 편의시설 ───────────────────────────────────
        Poi(
            id = "cafe_hyangweonjeong",
            type = PoiType.CAFE,
            title = "향원정 카페",
            titleMap = mapOf(
                "ko" to "향원정 카페",
                "en" to "Hyangwonjeong Café",
                "ja" to "香遠亭カフェ",
                "zh" to "香远亭咖啡厅"
            ),
            latitude = 37.5808,
            longitude = 126.9757
        ),
        Poi(
            id = "cafe_yuseonjae",
            type = PoiType.CAFE,
            title = "유선재 찻집",
            titleMap = mapOf(
                "ko" to "유선재 찻집",
                "en" to "Yuseonjae Tea House",
                "ja" to "流線齋 茶室",
                "zh" to "流线斋茶室"
            ),
            latitude = 37.5812,
            longitude = 126.9762
        ),

        // ── 기념품 / 매표소 ──────────────────────────────────
        Poi(
            id = "shop_main",
            type = PoiType.SHOP,
            title = "경복궁 기념품점",
            titleMap = mapOf(
                "ko" to "경복궁 기념품점",
                "en" to "Gyeongbokgung Souvenir Shop",
                "ja" to "景福宮 お土産店",
                "zh" to "景福宫纪念品店"
            ),
            latitude = 37.5763,
            longitude = 126.9770
        ),

        // ── 관람 안내소 ──────────────────────────────────────
        Poi(
            id = "info_main",
            type = PoiType.INFO,
            title = "관람 안내소",
            titleMap = mapOf(
                "ko" to "관람 안내소",
                "en" to "Visitor Information Center",
                "ja" to "観覧案内所",
                "zh" to "参观咨询中心"
            ),
            latitude = 37.5762,
            longitude = 126.9775
        ),

        // ── 전망 포인트 ──────────────────────────────────────
        Poi(
            id = "viewpoint_gyeonghoeru",
            type = PoiType.VIEWPOINT,
            title = "경회루 포토 스팟",
            titleMap = mapOf(
                "ko" to "경회루 포토 스팟",
                "en" to "Gyeonghoeru Photo Spot",
                "ja" to "慶会楼 フォトスポット",
                "zh" to "庆会楼最佳拍照点"
            ),
            latitude = 37.5803,
            longitude = 126.9743
        ),

        // ── 북쪽 후문 (경무대 방면) ─────────────────────────
        Poi(
            id = "gate_north",
            type = PoiType.GATE,
            title = "신무문 (북문)",
            titleMap = mapOf(
                "ko" to "신무문 (북문)",
                "en" to "Sinmumun (North Gate)",
                "ja" to "神武門（北門）",
                "zh" to "神武门（北门）"
            ),
            latitude = 37.5830,
            longitude = 126.9766
        )
    )

    override fun getExtraPoiList(): List<Poi> = extraPoiList
}
