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
                    chunkId = "geunjeongjeon_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "근정전(勤政殿)은 경복궁의 정전으로 왕의 즉위식, 신하들의 하례, 외국 사신의 접견, 궁중연회 등 중요한 국가행사를 치르던 곳이다. 근정전의 ‘근정’은 천하의 일을 부지런히 잘 다스리다‘라는 뜻으로, 궁궐 내에서도 가장 규모가 크고 격식을 갖춘 건물로 면적도 가장 넓게 차지하고 있다. 근정전은 2단의 월대 위에 다시 낮은 기단을 두고 그 위로 중층 올린 건물로 안에서 보면 층 구분이 없는 통층(通層)이다. 근정전 앞마당, 즉 조정(朝廷)은 다른 궁궐의 정전과 같이 박석이 깔려있고, 중앙에는 삼도(三道)를 두어 궁궐의 격식을 갖추었으며 조정에는 정1품부터 정9품까지의 품계석을 다. 월대의 귀퉁이나 계단 주위 난간 기둥에는 4신상과 12지신상을 포함하여 28수 별자리상 등을 간결하지만 재치있게 조각하였다. 내부 바닥은 전돌을 깔았고, 북쪽 가운데에 근정전 전체 전경 26. 4. 19. 오후 4:34 자리인 어좌를 설치하였다. 어좌 뒤에는 왕권을 상징하는 해와 달, 다섯 봉우리의 산이 그려진 ‘일월오봉도’를 놓았고 천장에는 칠조룡을 조각하여 장식하였다. 근정전에서는 정 세종, 세조, 중종, 선조가 왕위에 올랐으며, 1985년 국보로 지정되었다. 근정문(勤政門)은 근정전의 정문으로 앞면 3칸, 옆면 2칸의 우진각지붕의 형태로, 궁궐 정전의 정문 중 유일하게 2층 규모로 지었다. 근정문은 왕의 장례(국장)가 있을 때 다음 즉위식을 치렀던 곳으로 이곳에서 단종, 성종, 명종이 왕위에 올랐다. 근정문은 행각을 포함하여 1985년 보물로 지정되었다. ⊙ 근정전 행랑 주련(柱聯) 내용 (위치 : 근정문 동쪽 일화문 안) ① 立愛敦親 敎民以睦(입애돈친 교민이목) : 사랑을 확립하고 친족끼리 돈독하게 함으로써 백성들을 화목하게 하고, ② 好學樂善 爲世所宗(호학낙선 위세소종) : 배움을 좋아하고 선을 즐김으로써 세상 사람들이 받드는 바가 된다. ③ 序昭六親 殷道隆盛(서소육친 은도융성) : 질서가 육친(六親)에 밝으니 은나라의 도가 융성하고, ④ 德推九睦 治堯旪龢(덕추구목 치요협화) : 덕이 구족(九族)에 미치니 요임금의 정치가 화목하도다. ⑤ 列卿尙書 落花底春酒(열경상서 낙화저춘주) : 구경(九卿)과 상서(尙書)들은 떨어지는 꽃 아래에서 봄 술을 마시고, ⑥ 王孫公子 芳樹下淸歌(왕손공자 방수하청가) : 왕손과 공자들은 아름다운 나무 아래에서 청아한 노래를 부르도다. ⑦ 捍禦宗邦 維城維翰(한어종방 유성유한) : 나라를 막고 지키는 것",
                    contentMap = mapOf("ko" to "근정전(勤政殿)은 경복궁의 정전으로 왕의 즉위식, 신하들의 하례, 외국 사신의 접견, 궁중연회 등 중요한 국가행사를 치르던 곳이다. 근정전의 ‘근정’은 천하의 일을 부지런히 잘 다스리다‘라는 뜻으로, 궁궐 내에서도 가장 규모가 크고 격식을 갖춘 건물로 면적도 가장 넓게 차지하고 있다. 근정전은 2단의 월대 위에 다시 낮은 기단을 두고 그 위로 중층 올린 건물로 안에서 보면 층 구분이 없는 통층(通層)이다. 근정전 앞마당, 즉 조정(朝廷)은 다른 궁궐의 정전과 같이 박석이 깔려있고, 중앙에는 삼도(三道)를 두어 궁궐의 격식을 갖추었으며 조정에는 정1품부터 정9품까지의 품계석을 다. 월대의 귀퉁이나 계단 주위 난간 기둥에는 4신상과 12지신상을 포함하여 28수 별자리상 등을 간결하지만 재치있게 조각하였다. 내부 바닥은 전돌을 깔았고, 북쪽 가운데에 근정전 전체 전경 26. 4. 19. 오후 4:34 자리인 어좌를 설치하였다. 어좌 뒤에는 왕권을 상징하는 해와 달, 다섯 봉우리의 산이 그려진 ‘일월오봉도’를 놓았고 천장에는 칠조룡을 조각하여 장식하였다. 근정전에서는 정 세종, 세조, 중종, 선조가 왕위에 올랐으며, 1985년 국보로 지정되었다. 근정문(勤政門)은 근정전의 정문으로 앞면 3칸, 옆면 2칸의 우진각지붕의 형태로, 궁궐 정전의 정문 중 유일하게 2층 규모로 지었다. 근정문은 왕의 장례(국장)가 있을 때 다음 즉위식을 치렀던 곳으로 이곳에서 단종, 성종, 명종이 왕위에 올랐다. 근정문은 행각을 포함하여 1985년 보물로 지정되었다. ⊙ 근정전 행랑 주련(柱聯) 내용 (위치 : 근정문 동쪽 일화문 안) ① 立愛敦親 敎民以睦(입애돈친 교민이목) : 사랑을 확립하고 친족끼리 돈독하게 함으로써 백성들을 화목하게 하고, ② 好學樂善 爲世所宗(호학낙선 위세소종) : 배움을 좋아하고 선을 즐김으로써 세상 사람들이 받드는 바가 된다. ③ 序昭六親 殷道隆盛(서소육친 은도융성) : 질서가 육친(六親)에 밝으니 은나라의 도가 융성하고, ④ 德推九睦 治堯旪龢(덕추구목 치요협화) : 덕이 구족(九族)에 미치니 요임금의 정치가 화목하도다. ⑤ 列卿尙書 落花底春酒(열경상서 낙화저춘주) : 구경(九卿)과 상서(尙書)들은 떨어지는 꽃 아래에서 봄 술을 마시고, ⑥ 王孫公子 芳樹下淸歌(왕손공자 방수하청가) : 왕손과 공자들은 아름다운 나무 아래에서 청아한 노래를 부르도다. ⑦ 捍禦宗邦 維城維翰(한어종방 유성유한) : 나라를 막고 지키는 것")
                ),
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
                    linkedChunkId = "geun_01",
                    locationHint = mapOf("ko" to "근정전 정면 2층 지붕 밑 중앙, 검은 바탕에 금색 한자로 쓰인 현판을 올려다보세요.", "en" to "Look up at the center beneath the upper roof of Geunjeongjeon.", "ja" to "勤政殿正面の2階屋根の下、中央を見上げてください。", "zh" to "抬头看勤政殿正面二层屋檐下中央。")
                ),
                SubElement(
                    id = "geun_iron_ring",
                    displayName = mapOf("ko" to "차일고리 (마당 쇠고리)", "en" to "Chail Iron Ring", "ja" to "遮日の鉄輪", "zh" to "遮日铁环"),
                    visualHints = listOf("metal iron ring embedded in stone floor", "rusty metal loop on stone pavement", "iron ring for chail on bakseok"),
                    linkedChunkId = "geun_03",
                    locationHint = mapOf("ko" to "근정전 앞마당(조정) 박석 사이를 찾아 보세요.", "en" to "Scan the Bakseok pavement in front of Geunjeongjeon.", "ja" to "勤政殿前庭の薄石の間を探してみてください。", "zh" to "在勤政殿前院的薄石之间寻找。")
                ),
                SubElement(
                    id = "geun_rank_stone",
                    displayName = mapOf("ko" to "품계석 (직급 표시돌)", "en" to "Rank Stone", "ja" to "品階石", "zh" to "品阶石"),
                    visualHints = listOf("small stone pillar with korean characters", "stone marker lined up in courtyard", "carved stone post in palace courtyard"),
                    linkedChunkId = "geun_03",
                    locationHint = mapOf("ko" to "근정전 앞마당 중앙 삼도 양옆을 따라 늘어선 작은 돌기둥들.", "en" to "Along the sides of the three central paths in front of Geunjeongjeon.", "ja" to "勤政殿前庭の中央三道の両側に並んだ小さな石柱。", "zh" to "沿着勤政殿前院中央三道的两侧寻找小石柱。")
                ),
                SubElement(
                    id = "geun_zodiac_statue",
                    displayName = mapOf("ko" to "월대 십이지신상", "en" to "Zodiac Animal Statue", "ja" to "月台 十二支神像", "zh" to "月台 十二生肖像"),
                    visualHints = listOf("stone carved animal statue on balustrade", "zodiac animal stone sculpture on stone fence", "stone guardian animal figure on stairs"),
                    linkedChunkId = "geun_02",
                    locationHint = mapOf("ko" to "근정전 2단 월대 난간 귀퉁이와 계단 주위의 작은 동물 석상.", "en" to "Small animal statues at corners and stair railings of Geunjeongjeon's Woldae.", "ja" to "勤政殿の月台の欄干の角と階段周囲の小さな動物石像。", "zh" to "勤政殿月台栏杆角落和台阶周围的小动物石像。")
                ),
                SubElement(
                    id = "geun_chiljolyong",
                    displayName = mapOf("ko" to "칠조룡 (천장 7발톱 용)", "en" to "Seven-clawed Dragon", "ja" to "七爪龍", "zh" to "七爪龙"),
                    visualHints = listOf("golden dragon sculpture on ceiling", "dragon with seven claws on ceiling center", "carved golden dragon looking down from ceiling"),
                    linkedChunkId = "geun_04",
                    locationHint = mapOf("ko" to "근정전 내부 천장 한가운데를 올려다보세요. 황금빛 용 두 마리.", "en" to "Look up at the center of Geunjeongjeon's interior ceiling — two golden dragons.", "ja" to "勤政殿内部の天井中央を見上げてください。金色の龍。", "zh" to "抬头看勤政殿内部天花板中央——两条金龙。")
                ),
                SubElement(
                    id = "geun_deumu",
                    displayName = mapOf("ko" to "드무 (화재맥이 청동솥)", "en" to "Deumu (Bronze Water Vat)", "ja" to "ドゥム (防火用青銅釜)", "zh" to "防火青铜鼎"),
                    visualHints = listOf("large bronze cauldron in palace courtyard", "massive metal water vat near building", "wide iron pot on stone base for fire prevention"),
                    linkedChunkId = "geun_02",
                    locationHint = mapOf("ko" to "근정전 앞 월대 모서리의 큰 청동 방화수 항아리.", "en" to "Large bronze cauldron-shaped fire-prevention vats at Geunjeongjeon's Woldae corners.", "ja" to "勤政殿前の月台の角に置かれた大きな青銅釜形の防火水壺。", "zh" to "勤政殿前月台角落的大青铜釜形防火水壶。")
                ),
                SubElement(
                    id = "geun_throne",
                    displayName = mapOf("ko" to "어좌 (왕의 자리)", "en" to "Eojwa (Royal Throne)", "ja" to "御座 (王の座)", "zh" to "御座（王座）"),
                    visualHints = listOf("royal wooden throne inside traditional palace hall", "elaborately carved throne seat on raised platform", "ornate golden-red throne with folding screen behind"),
                    linkedChunkId = "geun_02",
                    locationHint = mapOf(
                        "ko" to "근정전 내부 북쪽 가운데에 설치된 왕의 자리(어좌)를 창호 너머로 찾아보세요.",
                        "en" to "Look through the window lattice at Geunjeongjeon's interior — the royal throne stands at the north center.",
                        "ja" to "勤政殿内部の北側中央に設置された王の御座を、格子戸越しに探してください。",
                        "zh" to "透过勤政殿的窗棂，寻找设置在内部北侧中央的王座（御座）。"
                    )
                ),
                SubElement(
                    id = "geun_ilworobongdo",
                    displayName = mapOf("ko" to "일월오봉도 병풍", "en" to "Irworobongdo (Sun-Moon-Five Peaks Screen)", "ja" to "日月五峰図 屏風", "zh" to "日月五峰图 屏风"),
                    visualHints = listOf("folding screen painting with sun moon and five mountain peaks", "traditional Korean painting of red sun and white moon with five mountains", "royal screen with ocean waves and peaks behind throne"),
                    linkedChunkId = "geun_02",
                    locationHint = mapOf(
                        "ko" to "근정전 어좌 바로 뒤에 해, 달, 다섯 봉우리가 그려진 병풍이 있습니다.",
                        "en" to "Directly behind the throne in Geunjeongjeon stands the folding screen painted with sun, moon, and five peaks.",
                        "ja" to "勤政殿の御座のすぐ後ろに、太陽・月・五つの峰が描かれた屏風があります。",
                        "zh" to "勤政殿御座正后方有一扇画着日、月、五峰的屏风。"
                    )
                ),
                SubElement(
                    id = "geun_samdo",
                    displayName = mapOf("ko" to "삼도 (조정의 세 갈래 길)", "en" to "Samdo (Three Ceremonial Paths)", "ja" to "三道 (朝廷の三つの道)", "zh" to "三道（朝廷三条道）"),
                    visualHints = listOf("three stone paths leading across palace courtyard", "raised central stone road flanked by two lower paths", "granite ceremonial road divided into three lanes"),
                    linkedChunkId = "geun_03",
                    locationHint = mapOf(
                        "ko" to "근정문을 지나 근정전으로 가는 앞마당 중앙에 세 갈래 돌길이 있습니다. 가운데가 어도(왕의 길).",
                        "en" to "Walking from Geunjeongmun to Geunjeongjeon, three stone paths run through the courtyard — the middle (Eodo) was for the king.",
                        "ja" to "勤政門から勤政殿へ向かう前庭の中央に三道があります。中央は御道（王の道）。",
                        "zh" to "从勤政门通往勤政殿的前庭中央有三道——中间的御道是国王专用。"
                    )
                )
            ),
            coverImageAsset = "heritage/geunjeongjeon_cover.jpg",
            galleryImageAssets = listOf("heritage/geunjeongjeon_1.jpg", "heritage/geunjeongjeon_2.jpg", "heritage/geunjeongjeon_3.jpg")
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
                    chunkId = "gyeonghoeru_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "경회루(慶會樓)의 ‘경회’는 ‘경사스러운 연회’라는 뜻으로, 경복궁 침전영역 서쪽에 위치한 연못 안에 조성된 누각이다. 경회루는 왕이 신하들과 규모가 큰 연회를 열거나 외국 을 접대하던 곳이다. 연못에서 뱃놀이를 즐기고 경회루에 올라 인왕산과 궁궐의 장엄한 경관을 감상하는 왕실 정원으로 꾸몄다. 경복궁 창건 당시에는 작은 누각이었으나 1412년 종 12)에 크게 연못을 파고 지금과 같은 규모로 다시 만들었다. 성종과 연산군 대에 수리하였다가 임진왜란 때 소실된 것을 1867년(고종 4) 경복궁을 다시 지을 때 중건하였다. 경회루의 1층은 48개(둥근 기둥과 네모난 기둥 각 24개)의 높은 돌기둥들만 세웠으며, 2층에 마루를 깔아 연회장으로 이용했다. 추녀마루에는 우리나라 건물 가운데 가장 많은 의 잡상(雜像, 지붕 위 네 귀에 여러 가지 신상神像의 모습으로 만들어 얹은 장식 기와)이 있다. 경회루는 1985년 국보로 지정되었다. 경회루 전체 전경 26. 4. 19. 오후 4:36 26. 4. 19. 오후 4:36",
                    contentMap = mapOf("ko" to "경회루(慶會樓)의 ‘경회’는 ‘경사스러운 연회’라는 뜻으로, 경복궁 침전영역 서쪽에 위치한 연못 안에 조성된 누각이다. 경회루는 왕이 신하들과 규모가 큰 연회를 열거나 외국 을 접대하던 곳이다. 연못에서 뱃놀이를 즐기고 경회루에 올라 인왕산과 궁궐의 장엄한 경관을 감상하는 왕실 정원으로 꾸몄다. 경복궁 창건 당시에는 작은 누각이었으나 1412년 종 12)에 크게 연못을 파고 지금과 같은 규모로 다시 만들었다. 성종과 연산군 대에 수리하였다가 임진왜란 때 소실된 것을 1867년(고종 4) 경복궁을 다시 지을 때 중건하였다. 경회루의 1층은 48개(둥근 기둥과 네모난 기둥 각 24개)의 높은 돌기둥들만 세웠으며, 2층에 마루를 깔아 연회장으로 이용했다. 추녀마루에는 우리나라 건물 가운데 가장 많은 의 잡상(雜像, 지붕 위 네 귀에 여러 가지 신상神像의 모습으로 만들어 얹은 장식 기와)이 있다. 경회루는 1985년 국보로 지정되었다. 경회루 전체 전경 26. 4. 19. 오후 4:36 26. 4. 19. 오후 4:36")
                ),
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
                    linkedChunkId = "gy_03",
                    locationHint = mapOf("ko" to "경회루 주변 직사각형 연못과 수면 반영.", "en" to "The rectangular pond around Gyeonghoeru and its reflection.", "ja" to "慶会楼周辺の長方形の池と水面に映る姿。", "zh" to "庆会楼周围的长方形池塘和水中倒影。")
                ),
                SubElement(
                    id = "gy_japsang",
                    displayName = mapOf("ko" to "잡상 (지붕 위 수호신)", "en" to "Japsang (Roof Figures)", "ja" to "雑像 (屋根の守り神)", "zh" to "杂像 (屋顶守护神)"),
                    visualHints = listOf("small clay animal figures lining roof ridge", "ceramic monkey and monk statues on palace roof edge", "roof edge guardian figures"),
                    linkedChunkId = "gy_01",
                    locationHint = mapOf("ko" to "경회루 지붕 네 귀퉁이 추녀마루의 작은 토우 장식.", "en" to "Clay figurines on the four roof edges of Gyeonghoeru.", "ja" to "慶会楼の屋根の四隅の棟の小さな土偶飾り。", "zh" to "庆会楼屋顶四角屋脊上的小陶偶装饰。")
                ),
                SubElement(
                    id = "gy_square_pillar",
                    displayName = mapOf("ko" to "외곽 사각 기둥", "en" to "Outer Square Pillar", "ja" to "外郭の四角柱", "zh" to "外侧方柱"),
                    visualHints = listOf("square shaped stone column supporting pavilion", "huge rectangular stone pillars standing in water", "flat angled stone columns"),
                    linkedChunkId = "gy_02",
                    locationHint = mapOf("ko" to "경회루를 받치는 외곽 사각형 돌기둥 (24개, 땅 상징).", "en" to "Outer square stone pillars supporting Gyeonghoeru (24, Earth).", "ja" to "慶会楼を支える外側の四角柱（24本、大地）。", "zh" to "支撑庆会楼的外侧方形柱（24根，象征大地）。")
                ),
                SubElement(
                    id = "gy_round_pillar",
                    displayName = mapOf("ko" to "내부 원형 기둥", "en" to "Inner Round Pillar", "ja" to "内部の円柱", "zh" to "内侧圆柱"),
                    visualHints = listOf("cylindrical round stone column supporting pavilion", "smooth circular stone pillars under pavilion", "round column standing on stone base"),
                    linkedChunkId = "gy_02",
                    locationHint = mapOf("ko" to "경회루 안쪽 둥근 돌기둥 (24개, 하늘 상징).", "en" to "Inner round stone pillars under Gyeonghoeru (24, Heaven).", "ja" to "慶会楼の内側の丸い石柱（24本、天）。", "zh" to "庆会楼内侧的圆形柱（24根，象征天）。")
                ),
                SubElement(
                    id = "gy_stone_bridges",
                    displayName = mapOf("ko" to "경회루 돌다리 3개", "en" to "Three Stone Bridges of Gyeonghoeru", "ja" to "慶会楼の3つの石橋", "zh" to "庆会楼三座石桥"),
                    visualHints = listOf("three stone bridges crossing palace pond to pavilion", "short stone bridges over rectangular palace pond", "multiple parallel stone paths across water"),
                    linkedChunkId = "gy_01",
                    locationHint = mapOf(
                        "ko" to "연못 동쪽에서 경회루로 이어지는 돌다리 3개를 찾으세요. 가운데 다리는 왕이 사용했습니다.",
                        "en" to "Find the three stone bridges from the east of the pond to Gyeonghoeru — the middle one was for the king.",
                        "ja" to "池の東側から慶会楼へ続く3つの石橋を探してください。中央の橋は王が使いました。",
                        "zh" to "寻找从池塘东侧通向庆会楼的三座石桥——中间的桥是国王专用。"
                    )
                )
            ),
            coverImageAsset = "heritage/gyeonghoeru_cover.jpg",
            galleryImageAssets = listOf("heritage/gyeonghoeru_1.jpg", "heritage/gyeonghoeru_2.jpg", "heritage/gyeonghoeru_3.jpg")
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
                    chunkId = "gwanghwamun_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "광화문(光化門)은 경복궁의 정문으로 ‘광화’는 ‘군주에 의한 덕화(德化)’라는 뜻이다. 광화문은 다른 궁궐들의 정문과는 달리 돌로 높은 석축을 쌓고 그 위에 중층구조의 누각을 서 성곽의 성문과 같은 격식으로 장대하게 지어졌다. 광화문은 세 개의 홍예문으로 이루어져 있는데, 중앙의 홍예문은 왕이, 좌우의 홍예문은 왕세자와 신하들이 각각 출입하였다. 또한 문루(門樓)에는 종을 걸어 두어 시각을 알리 사용하였다. 광화문 월대 해치 26. 4. 19. 오후 4:32 광화문은 일제강점기에 조선총독부 청사를 지으면서 건춘문 북쪽으로 옮겼다가 한국전쟁 때 폭격을 맞아 문루가 모두 소실되었다. 이후 1968년 경복궁 정문의 위치로 다시 옮 나 나무를 사용하지 않고 철근콘크리트 구조로 복원하는 과정에서 위치 또한 제자리를 찾지 못하였다. 현재의 광화문은 2010년에 원래의 모습으로 제자리를 찾아서 다시 복원 다. 26. 4. 19. 오후 4:32",
                    contentMap = mapOf("ko" to "광화문(光化門)은 경복궁의 정문으로 ‘광화’는 ‘군주에 의한 덕화(德化)’라는 뜻이다. 광화문은 다른 궁궐들의 정문과는 달리 돌로 높은 석축을 쌓고 그 위에 중층구조의 누각을 서 성곽의 성문과 같은 격식으로 장대하게 지어졌다. 광화문은 세 개의 홍예문으로 이루어져 있는데, 중앙의 홍예문은 왕이, 좌우의 홍예문은 왕세자와 신하들이 각각 출입하였다. 또한 문루(門樓)에는 종을 걸어 두어 시각을 알리 사용하였다. 광화문 월대 해치 26. 4. 19. 오후 4:32 광화문은 일제강점기에 조선총독부 청사를 지으면서 건춘문 북쪽으로 옮겼다가 한국전쟁 때 폭격을 맞아 문루가 모두 소실되었다. 이후 1968년 경복궁 정문의 위치로 다시 옮 나 나무를 사용하지 않고 철근콘크리트 구조로 복원하는 과정에서 위치 또한 제자리를 찾지 못하였다. 현재의 광화문은 2010년에 원래의 모습으로 제자리를 찾아서 다시 복원 다. 26. 4. 19. 오후 4:32")
                ),
                HeritageChunk(
                    chunkId = "gw_01",
                    section = "역사",
                    title = "수난과 복원의 역사",
                    titleMap = mapOf(
                        "ko" to "수난과 복원의 역사",
                        "en" to "A History of Hardship and Restoration",
                        "ja" to "苦難と復元の歴史",
                        "zh" to "苦难与复原的历史"
                    ),
                    keywords = listOf("정문", "수난", "복원", "상징"),
                    content = "광화문은 경복궁의 정문으로, '빛이 사방을 덮고 교화가 만방에 미친다'는 뜻을 가지고 있습니다. 임진왜란 때 소실된 후 고종 때 중건되었으나, 일제강점기에 조선총독부 건물을 짓기 위해 강제로 위치가 옮겨지고 한국전쟁 때는 문루가 파괴되는 등 수많은 시련을 겪었습니다. 현재의 모습은 2010년, 잘못된 위치와 재질을 바로잡아 원래의 자리에 전통 방식대로 복원한 것입니다.",
                    contentMap = mapOf(
                        "ko" to "광화문은 경복궁의 정문으로, '빛이 사방을 덮고 교화가 만방에 미친다'는 뜻을 가지고 있습니다. 임진왜란 때 소실된 후 고종 때 중건되었으나, 일제강점기에 조선총독부 건물을 짓기 위해 강제로 위치가 옮겨지고 한국전쟁 때는 문루가 파괴되는 등 수많은 시련을 겪었습니다. 현재의 모습은 2010년, 잘못된 위치와 재질을 바로잡아 원래의 자리에 전통 방식대로 복원한 것입니다.",
                        "en" to "Gwanghwamun is the main gate of Gyeongbokgung Palace, meaning 'light covers all directions and enlightenment reaches all lands.' Destroyed during the Imjin War and rebuilt during King Gojong's reign, it endured many tribulations: forcibly relocated during the Japanese colonial period to make way for the Government-General Building, and its gate tower destroyed during the Korean War. Its current form was restored in 2010 to its original location using traditional methods, correcting the misplaced position and incorrect materials.",
                        "ja" to "光化門は景福宮の正門で、「光が四方を覆い、教化が万邦に及ぶ」という意味を持っています。文禄・慶長の役で焼失した後、高宗の時代に再建されましたが、日本統治時代には朝鮮総督府の建物を建てるために強制的に位置が移され、朝鮮戦争の際には門楼が破壊されるなど、数多くの試練を経験しました。現在の姿は2010年に、誤った位置と材質を正し、元の場所に伝統的な方式で復元されたものです。",
                        "zh" to "光化门是景福宫的正门，寓意“光照四方，教化万邦”。壬辰倭乱时被焚毁，高宗时期重建，但在日据时期为建造朝鲜总督府建筑而被强行迁移，朝鲜战争时门楼又遭破坏，历经诸多磨难。目前的面貌是2010年纠正了错误的位置和材质，按照传统方式在原址上复原的结果。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gw_02",
                    section = "건축",
                    title = "웅장한 세 개의 무지개문",
                    titleMap = mapOf(
                        "ko" to "웅장한 세 개의 무지개문",
                        "en" to "Three Majestic Rainbow Arches",
                        "ja" to "雄大な3つの虹霓門",
                        "zh" to "雄伟的三座彩虹拱门"
                    ),
                    keywords = listOf("홍예문", "문루", "석축"),
                    content = "광화문은 높은 석축 기단 위에 2층 규모의 문루가 세워진 웅장한 구조입니다. 기단부에는 세 개의 홍예문(무지개 모양 문)이 있는데, 가운데 문은 왕이 다니는 문이었으며 좌우 문은 신하들이 출입하던 문이었습니다. 기단부의 거대한 돌들은 조선 시대 석공 기술의 견고함을 잘 보여줍니다.",
                    contentMap = mapOf(
                        "ko" to "광화문은 높은 석축 기단 위에 2층 규모의 문루가 세워진 웅장한 구조입니다. 기단부에는 세 개의 홍예문(무지개 모양 문)이 있는데, 가운데 문은 왕이 다니는 문이었으며 좌우 문은 신하들이 출입하던 문이었습니다. 기단부의 거대한 돌들은 조선 시대 석공 기술의 견고함을 잘 보여줍니다.",
                        "en" to "Gwanghwamun has a grand structure with a two-story gate tower built atop a high stone foundation. The base features three Hongyemun (rainbow-shaped arches) — the central gate was for the king, while the left and right gates were used by officials. The massive stones of the foundation showcase the solidity of Joseon-era stonemasonry.",
                        "ja" to "光化門は高い石積み基壇の上に2階建ての門楼が建てられた壮大な構造です。基壇部には3つの虹霓門（虹の形の門）があり、中央の門は王が通る門で、左右の門は臣下が出入りする門でした。基壇部の巨大な石は朝鮮時代の石工技術の堅固さをよく表しています。",
                        "zh" to "光化门是在高石砌基台上建有两层门楼的雄伟建筑。基台部有三座虹霓门（彩虹形门），中间门为国王通行之门，左右门为大臣出入之门。基台部的巨石展现了朝鲜时代石工技艺的坚固性。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gw_03",
                    section = "관람 가이드",
                    title = "수문장 교대 의식",
                    titleMap = mapOf(
                        "ko" to "수문장 교대 의식",
                        "en" to "The Royal Guard Changing Ceremony",
                        "ja" to "守門将交代儀式",
                        "zh" to "守门将换岗仪式"
                    ),
                    keywords = listOf("수문장", "교대식", "전통문화"),
                    content = "매일 정해진 시간(오전 10시, 오후 2시)에 광화문 앞에서 열리는 수문장 교대 의식은 놓치지 말아야 할 볼거리입니다. 조선 시대 복식과 무기를 그대로 재현한 수문군들의 절도 있는 동작을 통해 당시의 성문 파수 문화를 생생하게 체험할 수 있습니다.",
                    contentMap = mapOf(
                        "ko" to "매일 정해진 시간(오전 10시, 오후 2시)에 광화문 앞에서 열리는 수문장 교대 의식은 놓치지 말아야 할 볼거리입니다. 조선 시대 복식과 무기를 그대로 재현한 수문군들의 절도 있는 동작을 통해 당시의 성문 파수 문화를 생생하게 체험할 수 있습니다.",
                        "en" to "The Royal Guard Changing Ceremony, held daily at set times (10:00 AM and 2:00 PM) in front of Gwanghwamun, is a must-see attraction. Through the disciplined movements of the guards, authentically reproducing Joseon-era costumes and weapons, visitors can vividly experience the palace gate sentry culture of that era.",
                        "ja" to "毎日決まった時間（午前10時、午後2時）に光化門の前で行われる守門将交代儀式は見逃せない見どころです。朝鮮時代の服装と武器をそのまま再現した守門軍たちの節度ある動作を通して、当時の城門警護文化を生き生きと体験することができます。",
                        "zh" to "每天在固定时间（上午10时、下午2时）于光化门前举行的守门将换岗仪式是不容错过的看点。守门军们完美再现朝鲜时代的服饰和武器，通过他们整齐划一的动作，可以生动体验当时的城门守卫文化。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gw_04",
                    section = "트리비아",
                    title = "불을 먹는 상상의 동물, 해태",
                    titleMap = mapOf(
                        "ko" to "불을 먹는 상상의 동물, 해태",
                        "en" to "Haetae: The Mythical Fire-Eating Creature",
                        "ja" to "火を食べる想像の動物、ヘテ",
                        "zh" to "能吞火的神兽——獬豸"
                    ),
                    keywords = listOf("해태", "화기", "정의"),
                    content = "광화문 앞 좌우에는 한 쌍의 해태상이 자리하고 있습니다. 해태는 시비곡직을 가리는 정의로운 동물이기도 하지만, 불을 먹는 능력이 있다고 믿어졌습니다. 이는 풍수지리상 화기가 강한 관악산의 기운으로부터 경복궁을 지키기 위한 조상들의 비보(裨補)책이었습니다.",
                    contentMap = mapOf(
                        "ko" to "광화문 앞 좌우에는 한 쌍의 해태상이 자리하고 있습니다. 해태는 시비곡직을 가리는 정의로운 동물이기도 하지만, 불을 먹는 능력이 있다고 믿어졌습니다. 이는 풍수지리상 화기가 강한 관악산의 기운으로부터 경복궁을 지키기 위한 조상들의 비보(裨補)책이었습니다.",
                        "en" to "A pair of Haetae statues stand on the left and right in front of Gwanghwamun. Haetae is a righteous creature that distinguishes right from wrong, and was also believed to have the ability to devour fire. This was an ancestral feng shui countermeasure (bibo) to protect Gyeongbokgung from the strong fire energy of Gwanaksan Mountain.",
                        "ja" to "光化門の前の左右には一対の獬豸（ヘテ）像が配置されています。獬豸は是非曲直を見分ける正義の動物であるとともに、火を食べる能力を持つと信じられていました。これは風水地理上、火気が強い冠岳山の気から景福宮を守るための先祖たちの裨補策でした。",
                        "zh" to "光化门前左右设有一对獬豸石像。獬豸是明辨是非曲直的正义神兽，同时被认为具有吞火的能力。这是先祖们为了抵挡风水上火气旺盛的冠岳山气运、守护景福宫而采取的裨补（风水调节）之策。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "gw_arches",
                    displayName = mapOf("ko" to "세 개의 홍예문 (무지개 아치)", "en" to "Three Rainbow Arches", "ja" to "3つの虹霓門", "zh" to "三个彩虹拱门"),
                    visualHints = listOf("three arched stone gate passages", "large stone gate with three arch openings", "stone wall with three rounded archways"),
                    linkedChunkId = "gw_02",
                    locationHint = mapOf("ko" to "광화문 석축 기단 아래의 세 개 아치형 통로.", "en" to "Three arched passages beneath Gwanghwamun's stone foundation.", "ja" to "光化門の石積み基壇下の3つのアーチ状通路。", "zh" to "光化门石砌基台下的三个拱形通道。")
                ),
                SubElement(
                    id = "gw_haetae",
                    displayName = mapOf("ko" to "해태 석상", "en" to "Haetae Stone Statue", "ja" to "ヘテ石像", "zh" to "獬豸石像"),
                    visualHints = listOf("stone mythical lion-like creature statue", "seated stone animal sculpture at gate entrance", "carved stone haechi guardian statue"),
                    linkedChunkId = "gw_04",
                    locationHint = mapOf("ko" to "광화문 앞 좌우의 해태 석상 한 쌍.", "en" to "The pair of Haetae statues flanking the front of Gwanghwamun.", "ja" to "光化門前の左右に座る獬豸石像。", "zh" to "光化门前左右对立的獬豸石像。")
                ),
                SubElement(
                    id = "gw_hongyemun_painting",
                    displayName = mapOf("ko" to "홍예문 천장 그림 (주작)", "en" to "Archway Ceiling Painting", "ja" to "虹霓門 天井画 (朱雀)", "zh" to "虹霓门 天花板壁画 (朱雀)"),
                    visualHints = listOf("colorful mythical bird painting on archway ceiling", "painted Vermilion Bird inside stone arch", "ceiling mural inside palace gate"),
                    linkedChunkId = "gw_02",
                    locationHint = mapOf("ko" to "광화문 아치 천장의 화려한 그림.", "en" to "The painted ceiling inside one of Gwanghwamun's arches.", "ja" to "光化門のアーチの天井の華やかな絵。", "zh" to "光化门拱门天花板上的华丽图案。")
                ),
                SubElement(
                    id = "gw_signboard",
                    displayName = mapOf("ko" to "광화문 현판", "en" to "Gwanghwamun Signboard", "ja" to "光化門の扁額", "zh" to "光化门匾额"),
                    visualHints = listOf("large wooden sign with Korean characters on gate", "gate name plaque with black calligraphy", "rectangular nameplate mounted on top of gate"),
                    linkedChunkId = "gw_01",
                    locationHint = mapOf("ko" to "광화문 2층 누각 정면 중앙의 대형 현판.", "en" to "The large signboard at the center front of Gwanghwamun's upper tower.", "ja" to "光化門の2階門楼正面中央の大型扁額。", "zh" to "光化门二层门楼正面中央的大型匾额。")
                ),
                SubElement(
                    id = "gw_sumunjang",
                    displayName = mapOf("ko" to "수문장 교대식", "en" to "Royal Guard Changing Ceremony", "ja" to "守門将交代式", "zh" to "守门将换岗仪式"),
                    visualHints = listOf("royal guard ceremony in traditional costume", "soldiers in colorful Joseon dynasty uniforms", "guard changing ceremony with flags and weapons"),
                    linkedChunkId = "gw_03",
                    locationHint = mapOf("ko" to "오전 10시, 오후 2시 광화문 앞 수문장 교대 의식.", "en" to "Royal Guard Changing Ceremony at 10:00 and 14:00 daily.", "ja" to "午前10時と午後2時の守門将交代儀式。", "zh" to "每天上午10点和下午2点的守门将换岗仪式。")
                )
            ),
            coverImageAsset = "heritage/gwanghwamun_cover.jpg",
            galleryImageAssets = listOf("heritage/gwanghwamun_1.jpg", "heritage/gwanghwamun_2.jpg", "heritage/gwanghwamun_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 사정전 (Sajeongjeon) — 왕의 편전 (집무실)
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "sajeongjeon",
            title = "사정전 (Sajeongjeon)",
            titleMap = mapOf(
                "ko" to "사정전 (Sajeongjeon)",
                "en" to "Sajeongjeon Hall",
                "ja" to "思政殿 (サジョンジョン)",
                "zh" to "思政殿"
            ),
            aliases = listOf("사정전", "Sajeongjeon", "Sajeongjeon Hall", "思政殿"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "inner_court",
            shortDescription = "왕이 매일 신하들과 국사를 논의하던 편전으로, '깊이 생각하여 정사를 돌본다'는 뜻을 담고 있습니다.",
            shortDescMap = mapOf(
                "ko" to "왕이 매일 신하들과 국사를 논의하던 편전으로, '깊이 생각하여 정사를 돌본다'는 뜻을 담고 있습니다.",
                "en" to "The king's daily office hall where he deliberated state affairs with officials, meaning 'to govern by thinking deeply.'",
                "ja" to "王が毎日臣下たちと国事を議論した便殿で、「深く考えて政事を行う」という意味が込められています。",
                "zh" to "国王每天与大臣讨论国事的便殿，寓意“深思熟虑地治理政事”。"
            ),
            latitude = 37.5802,
            longitude = 126.9770,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "sajeongjeon_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "사정전(思政殿)의 ‘사정’은 ‘선정을 깊이 생각하다’라는 뜻으로, 왕이 신하들과 함께 일상 업무를 보던 공식 집무실인 편전(便殿)이다. 이곳에서 매일 아침 업무 보고와 회의, 경연 이 이루어졌다. 내부에는 근정전과 같이 왕의 자리인 어좌가 있고, 그 뒤로 왕권을 상징하는 해와 달, 다섯 봉우리의 산이 그려진 ‘일월오봉도’를 놓았다. 사정전 좌우에는 만춘전(萬春殿, ‘만춘’ : 만년의 봄)과 천추전(千秋殿, ‘천추’ : 천년의 가을)은 사정전의 부속건물로 사정전에 없는 온돌시설이 갖추어져 있어 사계절로 이용이 하였던 것으로 보인다. 경복궁 창건 당시에는 세 건물이 복도로 연결되어 있었으나 고종 대에 다시 지으면서 독립된 건물로 지어졌다. 사정전 전체 전경 26. 4. 19. 오후 4:35 사정전 상참의 관련 자료 26. 4. 19. 오후 4:35",
                    contentMap = mapOf("ko" to "사정전(思政殿)의 ‘사정’은 ‘선정을 깊이 생각하다’라는 뜻으로, 왕이 신하들과 함께 일상 업무를 보던 공식 집무실인 편전(便殿)이다. 이곳에서 매일 아침 업무 보고와 회의, 경연 이 이루어졌다. 내부에는 근정전과 같이 왕의 자리인 어좌가 있고, 그 뒤로 왕권을 상징하는 해와 달, 다섯 봉우리의 산이 그려진 ‘일월오봉도’를 놓았다. 사정전 좌우에는 만춘전(萬春殿, ‘만춘’ : 만년의 봄)과 천추전(千秋殿, ‘천추’ : 천년의 가을)은 사정전의 부속건물로 사정전에 없는 온돌시설이 갖추어져 있어 사계절로 이용이 하였던 것으로 보인다. 경복궁 창건 당시에는 세 건물이 복도로 연결되어 있었으나 고종 대에 다시 지으면서 독립된 건물로 지어졌다. 사정전 전체 전경 26. 4. 19. 오후 4:35 사정전 상참의 관련 자료 26. 4. 19. 오후 4:35")
                ),
                HeritageChunk(
                    chunkId = "saj_01",
                    section = "역사",
                    title = "왕의 일상 집무실, 사정전",
                    titleMap = mapOf(
                        "ko" to "왕의 일상 집무실, 사정전",
                        "en" to "The King's Daily Office, Sajeongjeon",
                        "ja" to "王の日常の執務室、思政殿",
                        "zh" to "国王的日常办公厅，思政殿"
                    ),
                    keywords = listOf("편전", "경연", "어전회의", "집무"),
                    content = "사정전은 근정전 바로 뒤에 위치한 편전으로, 왕이 매일 신하들과 국정을 논의하던 실질적인 업무 공간이었습니다. '사정(思政)'이란 '깊이 생각하여 정사를 돌본다'는 뜻입니다. 근정전이 공식 의례의 공간이라면, 사정전은 왕의 일상적인 정치 활동이 이루어지던 곳입니다. 경연(經筵)이라 불리는 학문 토론회도 이곳에서 자주 열렸으며, 세종대왕은 이곳에서 한글 창제를 구상했다고 전해집니다.",
                    contentMap = mapOf(
                        "ko" to "사정전은 근정전 바로 뒤에 위치한 편전으로, 왕이 매일 신하들과 국정을 논의하던 실질적인 업무 공간이었습니다. '사정(思政)'이란 '깊이 생각하여 정사를 돌본다'는 뜻입니다. 근정전이 공식 의례의 공간이라면, 사정전은 왕의 일상적인 정치 활동이 이루어지던 곳입니다. 경연(經筵)이라 불리는 학문 토론회도 이곳에서 자주 열렸으며, 세종대왕은 이곳에서 한글 창제를 구상했다고 전해집니다.",
                        "en" to "Sajeongjeon is the executive office hall located directly behind Geunjeongjeon, serving as the practical workspace where the king discussed state affairs with officials daily. 'Sajeong' means 'to govern by thinking deeply.' While Geunjeongjeon was for formal ceremonies, Sajeongjeon was where the king's routine political activities took place. Academic debates called 'Gyeongyeon' were frequently held here, and it is said that King Sejong conceived the creation of Hangul in this very hall.",
                        "ja" to "思政殿は勤政殿のすぐ後ろに位置する便殿で、王が毎日臣下と国政を議論する実質的な業務空間でした。「思政」とは「深く考えて政事を行う」という意味です。勤政殿が公式の儀礼空間であるのに対し、思政殿は王の日常的な政治活動が行われた場所です。「経筵」と呼ばれる学問討論会もここで頻繁に開かれ、世宗大王はこの場所でハングル創製を構想したと伝えられています。",
                        "zh" to "思政殿位于勤政殿正后方，是国王每天与臣子讨论国事的实际办公场所。“思政”意为“深思熟虑地治理政事”。如果说勤政殿是正式仪式的空间，那么思政殿就是国王日常政治活动的场所。这里还经常举办名为“经筵”的学术讨论会，据说世宗大王就是在此构思了韩文（训民正音）的创制。"
                    )
                ),
                HeritageChunk(
                    chunkId = "saj_02",
                    section = "건축",
                    title = "소박하지만 격조 높은 단층 전각",
                    titleMap = mapOf(
                        "ko" to "소박하지만 격조 높은 단층 전각",
                        "en" to "A Simple Yet Dignified Single-Story Hall",
                        "ja" to "質素ながら格調高い単層の殿閣",
                        "zh" to "朴素却格调高雅的单层殿阁"
                    ),
                    keywords = listOf("단층", "팔작지붕", "온돌", "실용"),
                    content = "사정전은 근정전과 달리 단층 구조의 건물입니다. 이는 화려함보다 실용성을 중시한 결과로, 왕이 매일 업무를 보는 공간답게 효율적으로 설계되었습니다. 팔작지붕 양식을 갖추고 있으며, 내부에는 온돌이 설치되어 겨울에도 따뜻하게 집무를 볼 수 있었습니다. 좌우에는 만춘전(萬春殿)과 천추전(千秋殿)이 날개처럼 배치되어 있어 보좌 업무를 위한 공간으로 활용되었습니다.",
                    contentMap = mapOf(
                        "ko" to "사정전은 근정전과 달리 단층 구조의 건물입니다. 이는 화려함보다 실용성을 중시한 결과로, 왕이 매일 업무를 보는 공간답게 효율적으로 설계되었습니다. 팔작지붕 양식을 갖추고 있으며, 내부에는 온돌이 설치되어 겨울에도 따뜻하게 집무를 볼 수 있었습니다. 좌우에는 만춘전(萬春殿)과 천추전(千秋殿)이 날개처럼 배치되어 있어 보좌 업무를 위한 공간으로 활용되었습니다.",
                        "en" to "Unlike Geunjeongjeon, Sajeongjeon is a single-story building, designed for practicality over grandeur as a space for the king's daily work. It features a hip-and-gable roof (Paljak) style, and ondol (underfloor heating) was installed so the king could work comfortably even in winter. Manchunjeon (Hall of Ten Thousand Springs) and Cheonchujeon (Hall of a Thousand Autumns) flank it on either side, serving as auxiliary workspaces.",
                        "ja" to "思政殿は勤政殿とは異なり、単層構造の建物です。華やかさよりも実用性を重視した結果で、王が毎日執務を行う空間らしく効率的に設計されています。八作屋根の様式を備え、内部にはオンドル（床暖房）が設置され、冬でも暖かく執務を行うことができました。左右には萬春殿と千秋殿が翼のように配置され、補佐業務のための空間として活用されました。",
                        "zh" to "思政殿与勤政殿不同，是单层结构的建筑。这是注重实用性而非华丽的结果，作为国王每日办公的场所，设计十分高效。采用八作屋顶样式，内部设有暖炕（地暖），即使在冬天也能温暖地处理政务。左右分别配置了万春殿和千秋殿，如同双翼一般，用作辅助办公空间。"
                    )
                ),
                HeritageChunk(
                    chunkId = "saj_03",
                    section = "관람 포인트",
                    title = "어좌와 일월오봉도",
                    titleMap = mapOf(
                        "ko" to "어좌와 일월오봉도",
                        "en" to "The Royal Throne and Sun-Moon-Five Peaks Screen",
                        "ja" to "御座と日月五峰図",
                        "zh" to "御座与日月五峰图"
                    ),
                    keywords = listOf("어좌", "일월오봉도", "내부", "왕권"),
                    content = "사정전 내부 중앙에는 왕의 자리인 어좌가 놓여 있고, 그 뒤에는 일월오봉도 병풍이 배치되어 있습니다. 이 일월오봉도는 근정전의 것과 유사하지만 크기가 다소 작으며, 편전의 위격에 맞게 제작되었습니다. 현재 내부 관람은 제한적이지만, 정면에서 창호를 통해 어좌와 일월오봉도를 볼 수 있습니다.",
                    contentMap = mapOf(
                        "ko" to "사정전 내부 중앙에는 왕의 자리인 어좌가 놓여 있고, 그 뒤에는 일월오봉도 병풍이 배치되어 있습니다. 이 일월오봉도는 근정전의 것과 유사하지만 크기가 다소 작으며, 편전의 위격에 맞게 제작되었습니다. 현재 내부 관람은 제한적이지만, 정면에서 창호를 통해 어좌와 일월오봉도를 볼 수 있습니다.",
                        "en" to "At the center of Sajeongjeon's interior sits the royal throne (Eojwa), with the Irworobongdo (Sun, Moon, and Five Peaks) folding screen behind it. This screen is similar to the one in Geunjeongjeon but slightly smaller, made to suit the status of a daily office hall. While interior access is currently limited, visitors can view the throne and screen through the front windows.",
                        "ja" to "思政殿内部の中央には王の席である御座が置かれ、その後ろには日月五峰図の屏風が配置されています。この日月五峰図は勤政殿のものと似ていますが、やや小さく、便殿の格に合わせて制作されています。現在、内部の見学は制限されていますが、正面から格子戸を通して御座と日月五峰図を見ることができます。",
                        "zh" to "思政殿内部中央放置着国王的御座，其后方设有日月五峰图屏风。此屏风与勤政殿的类似但稍小，是按照便殿的规格制作的。虽然目前内部参观受到限制，但可以从正面通过窗棂看到御座和日月五峰图。"
                    )
                ),
                HeritageChunk(
                    chunkId = "saj_04",
                    section = "트리비아",
                    title = "세종대왕과 사정전의 경연",
                    titleMap = mapOf(
                        "ko" to "세종대왕과 사정전의 경연",
                        "en" to "King Sejong and the Royal Lectures at Sajeongjeon",
                        "ja" to "世宗大王と思政殿の経筵",
                        "zh" to "世宗大王与思政殿的经筵"
                    ),
                    keywords = listOf("세종", "경연", "한글", "학문"),
                    content = "세종대왕은 재위 32년 동안 경연을 1,898회나 열었다고 기록되어 있습니다. 하루에 3번씩 경연을 열기도 했으며, 대부분이 이 사정전에서 열렸습니다. 세종은 이곳에서 집현전 학사들과 학문을 토론하고, 한글(훈민정음) 창제의 밑그림을 그렸습니다. 사정전은 단순한 건물이 아니라, 조선의 학문과 정치가 만나는 지식의 산실이었습니다.",
                    contentMap = mapOf(
                        "ko" to "세종대왕은 재위 32년 동안 경연을 1,898회나 열었다고 기록되어 있습니다. 하루에 3번씩 경연을 열기도 했으며, 대부분이 이 사정전에서 열렸습니다. 세종은 이곳에서 집현전 학사들과 학문을 토론하고, 한글(훈민정음) 창제의 밑그림을 그렸습니다. 사정전은 단순한 건물이 아니라, 조선의 학문과 정치가 만나는 지식의 산실이었습니다.",
                        "en" to "Records indicate that King Sejong held royal lectures (Gyeongyeon) 1,898 times during his 32-year reign, sometimes holding three sessions a day, most of them in Sajeongjeon. Here, Sejong debated scholarship with scholars of Jiphyeonjeon (Hall of Worthies) and drafted the blueprint for creating Hangul (Hunminjeongeum). Sajeongjeon was not merely a building but the cradle of knowledge where Joseon's scholarship and politics converged.",
                        "ja" to "世宗大王は在位32年の間に経筵を1,898回も開いたと記録されています。1日に3回経筵を開くこともあり、そのほとんどがこの思政殿で行われました。世宗はここで集賢殿の学者たちと学問を議論し、ハングル（訓民正音）創製の構想を練りました。思政殿は単なる建物ではなく、朝鮮の学問と政治が交わる知識の揺りかごでした。",
                        "zh" to "据记载，世宗大王在位32年间共举行了1898次经筵，有时一天举行三次，其中大部分在思政殿举行。世宗在此与集贤殿的学者们讨论学问，并构思了韩文（训民正音）的创制蓝图。思政殿不仅仅是一座建筑，更是朝鲜学问与政治交汇的知识摇篮。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "saj_throne",
                    displayName = mapOf("ko" to "사정전 어좌", "en" to "Sajeongjeon Royal Throne", "ja" to "思政殿の御座", "zh" to "思政殿御座"),
                    visualHints = listOf("royal throne visible through traditional wooden lattice window", "king's seat with folding screen behind it inside single-story hall", "ornate wooden chair with sun-moon-peaks painting"),
                    linkedChunkId = "saj_03",
                    locationHint = mapOf("ko" to "사정전 창호 너머로 내부 중앙의 어좌 확인.", "en" to "Through Sajeongjeon's windows — royal throne at the center.", "ja" to "思政殿の格子戸越しに中央の御座を確認。", "zh" to "透过思政殿的窗棂查看中央的王座。")
                ),
                SubElement(
                    id = "saj_signboard",
                    displayName = mapOf("ko" to "사정전 현판", "en" to "Sajeongjeon Signboard", "ja" to "思政殿の扁額", "zh" to "思政殿匾额"),
                    visualHints = listOf("large wooden sign with korean characters on single-story hall", "palace hall name plaque on simple hip-and-gable roof building", "rectangular nameplate on smaller palace hall"),
                    linkedChunkId = "saj_01",
                    locationHint = mapOf("ko" to "사정전 정면 지붕 밑 중앙의 현판.", "en" to "The signboard at center beneath Sajeongjeon's front roof.", "ja" to "思政殿正面の屋根下中央の扁額。", "zh" to "思政殿正面屋檐下中央的匾额。")
                ),
                SubElement(
                    id = "saj_manchunjeon",
                    displayName = mapOf("ko" to "만춘전 (동쪽 부속전)", "en" to "Manchunjeon (East Annex)", "ja" to "万春殿（東側の付属殿）", "zh" to "万春殿（东侧附属殿）"),
                    visualHints = listOf("smaller palace building flanking east side of main hall", "auxiliary wooden hall with traditional roof", "side pavilion connected to main hall by corridor"),
                    linkedChunkId = "saj_02",
                    locationHint = mapOf("ko" to "사정전 동쪽 부속 전각 만춘전 — 온돌 있음.", "en" to "Manchunjeon east of Sajeongjeon — with ondol heating.", "ja" to "思政殿東側の萬春殿 — オンドル有り。", "zh" to "思政殿东侧的万春殿——设有暖炕。")
                ),
                SubElement(
                    id = "saj_cheonchujeon",
                    displayName = mapOf("ko" to "천추전 (서쪽 부속전)", "en" to "Cheonchujeon (West Annex)", "ja" to "千秋殿（西側の付属殿）", "zh" to "千秋殿（西侧附属殿）"),
                    visualHints = listOf("western annex hall next to main executive office", "smaller palace building with ondol flooring", "auxiliary wing on the west side of executive hall"),
                    linkedChunkId = "saj_02",
                    locationHint = mapOf(
                        "ko" to "사정전 서쪽 부속 전각 천추전(千秋殿) — '천년의 가을'이라는 뜻. 온돌 시설 있음.",
                        "en" to "Cheonchujeon west of Sajeongjeon — meaning 'thousand autumns.' Has ondol heating.",
                        "ja" to "思政殿西側の千秋殿 — 「千年の秋」の意味。オンドル設備あり。",
                        "zh" to "思政殿西侧的千秋殿——意为\"千年之秋\"，设有暖炕。"
                    )
                ),
                SubElement(
                    id = "saj_ilworobongdo",
                    displayName = mapOf("ko" to "사정전 일월오봉도", "en" to "Irworobongdo at Sajeongjeon", "ja" to "思政殿の日月五峰図", "zh" to "思政殿日月五峰图"),
                    visualHints = listOf("folding screen with sun moon five peaks behind throne", "traditional royal painting of red sun and white moon", "symbolic royal screen placed behind the king's seat"),
                    linkedChunkId = "saj_03",
                    locationHint = mapOf(
                        "ko" to "사정전 내부 어좌 뒤편의 일월오봉도 병풍 — 근정전과 같은 도안입니다.",
                        "en" to "The Irworobongdo screen behind Sajeongjeon's throne — same motif as Geunjeongjeon.",
                        "ja" to "思政殿の御座の後ろの日月五峰図屏風 — 勤政殿と同じ図案。",
                        "zh" to "思政殿御座后方的日月五峰图屏风——与勤政殿图案相同。"
                    )
                )
            ),
            coverImageAsset = "heritage/sajeongjeon_cover.jpg",
            galleryImageAssets = listOf("heritage/sajeongjeon_1.jpg", "heritage/sajeongjeon_2.jpg", "heritage/sajeongjeon_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 수정전 (Sujeongjeon) — 집현전 / 세종의 학당
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "sujeongjeon",
            title = "수정전 (Sujeongjeon)",
            titleMap = mapOf(
                "ko" to "수정전 (Sujeongjeon)",
                "en" to "Sujeongjeon Hall",
                "ja" to "修政殿 (スジョンジョン)",
                "zh" to "修政殿"
            ),
            aliases = listOf("수정전", "Sujeongjeon", "Sujeongjeon Hall", "修政殿", "집현전"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "inner_court",
            shortDescription = "세종대왕의 집현전이 있던 곳으로, 한글 창제의 산실이자 조선 최고의 학문 기관이 자리했던 전각입니다.",
            shortDescMap = mapOf(
                "ko" to "세종대왕의 집현전이 있던 곳으로, 한글 창제의 산실이자 조선 최고의 학문 기관이 자리했던 전각입니다.",
                "en" to "The site of King Sejong's Jiphyeonjeon (Hall of Worthies), the birthplace of Hangul and the premier scholarly institution of Joseon.",
                "ja" to "世宗大王の集賢殿があった場所で、ハングル創製の発祥地であり、朝鮮最高の学問機関が置かれた殿閣です。",
                "zh" to "世宗大王集贤殿所在之处，是韩文创制的发源地，也是朝鲜最高学术机构所在的殿阁。"
            ),
            latitude = 37.5801,
            longitude = 126.9760,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "sujeongjeon_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "수정전(修政殿)의 ‘수정’은 ‘정치를 잘 수행하다’라는 뜻으로, 고종 대 편전으로 사용했던 건물이다. 경복궁 창건 당시에는 없었으나 고종 대 경복궁을 다시 지을 때 지은 건물이다 94년(고종 31) 갑오개혁 때 군국기무처가 들어섰고, 이후 의정부(議政府)가 내각(內閣)으로 바뀌면서 내각의 청사로 사용되었다. 특히 조선 초기 수정전 일대에는 세종 대에 훈민정음 창제의 산실인 집현전이 있었다. 수정전은 2012년 보물로 지정되었다. 수정전 전체 전경 26. 4. 19. 오후 4:35",
                    contentMap = mapOf("ko" to "수정전(修政殿)의 ‘수정’은 ‘정치를 잘 수행하다’라는 뜻으로, 고종 대 편전으로 사용했던 건물이다. 경복궁 창건 당시에는 없었으나 고종 대 경복궁을 다시 지을 때 지은 건물이다 94년(고종 31) 갑오개혁 때 군국기무처가 들어섰고, 이후 의정부(議政府)가 내각(內閣)으로 바뀌면서 내각의 청사로 사용되었다. 특히 조선 초기 수정전 일대에는 세종 대에 훈민정음 창제의 산실인 집현전이 있었다. 수정전은 2012년 보물로 지정되었다. 수정전 전체 전경 26. 4. 19. 오후 4:35")
                ),
                HeritageChunk(
                    chunkId = "suj_01",
                    section = "역사",
                    title = "한글 탄생의 산실, 집현전",
                    titleMap = mapOf(
                        "ko" to "한글 탄생의 산실, 집현전",
                        "en" to "Birthplace of Hangul: The Hall of Worthies",
                        "ja" to "ハングル誕生の揺りかご、集賢殿",
                        "zh" to "韩文诞生的摇篮——集贤殿"
                    ),
                    keywords = listOf("집현전", "한글", "세종", "학자"),
                    content = "수정전 일대는 조선 시대 집현전(集賢殿)이 있던 곳으로 알려져 있습니다. 세종대왕은 1420년 집현전을 설치하고 당대 최고의 학자들을 모아 학문 연구에 전념하게 했습니다. 이곳에서 한글(훈민정음)이 연구·창제되었으며, 《칠정산》《농사직설》 등 수많은 학술 서적이 편찬되었습니다. 현재의 수정전 건물은 고종 때 중건된 것으로, 내각의 업무 공간으로 사용되었습니다.",
                    contentMap = mapOf(
                        "ko" to "수정전 일대는 조선 시대 집현전(集賢殿)이 있던 곳으로 알려져 있습니다. 세종대왕은 1420년 집현전을 설치하고 당대 최고의 학자들을 모아 학문 연구에 전념하게 했습니다. 이곳에서 한글(훈민정음)이 연구·창제되었으며, 《칠정산》《농사직설》 등 수많은 학술 서적이 편찬되었습니다. 현재의 수정전 건물은 고종 때 중건된 것으로, 내각의 업무 공간으로 사용되었습니다.",
                        "en" to "The Sujeongjeon area is known as the former site of Jiphyeonjeon (Hall of Worthies) during the Joseon Dynasty. In 1420, King Sejong established Jiphyeonjeon and gathered the era's finest scholars to devote themselves to academic research. Hangul (Hunminjeongeum) was researched and created here, along with numerous academic works such as 'Chiljeongsan' (astronomical calculations) and 'Nongsajikseol' (guide to farming). The current building was reconstructed during King Gojong's reign and served as a cabinet office.",
                        "ja" to "修政殿一帯は、朝鮮時代に集賢殿（ジッピョンジョン）があった場所として知られています。世宗大王は1420年に集賢殿を設置し、当代最高の学者たちを集めて学問研究に専念させました。ここでハングル（訓民正音）が研究・創製され、《七政算》《農事直説》など数多くの学術書が編纂されました。現在の修政殿の建物は高宗の時代に再建されたもので、内閣の業務空間として使用されました。",
                        "zh" to "修政殿一带据知是朝鲜时代集贤殿所在地。世宗大王于1420年设立集贤殿，汇聚当时最优秀的学者专心研究学问。韩文（训民正音）在此研究创制，《七政算》《农事直说》等大量学术著作也在此编纂。现在的修政殿建筑是高宗时期重建的，曾用作内阁办公场所。"
                    )
                ),
                HeritageChunk(
                    chunkId = "suj_02",
                    section = "건축",
                    title = "경복궁에서 가장 긴 전각",
                    titleMap = mapOf(
                        "ko" to "경복궁에서 가장 긴 전각",
                        "en" to "The Longest Hall in Gyeongbokgung",
                        "ja" to "景福宮で最も長い殿閣",
                        "zh" to "景福宫最长的殿阁"
                    ),
                    keywords = listOf("장방형", "내각", "근대건축", "복도"),
                    content = "수정전은 정면 10칸의 긴 장방형 건물로, 경복궁의 전각 중 가장 긴 건물 중 하나입니다. 이 독특한 형태는 많은 관리들이 동시에 업무를 볼 수 있는 사무 공간으로 설계되었기 때문입니다. 근정전 서쪽에 위치하여 서편 행각의 일부를 이루며, 조선 말기 군국기무처와 내각이 이곳에서 국가 개혁을 논의하기도 했습니다.",
                    contentMap = mapOf(
                        "ko" to "수정전은 정면 10칸의 긴 장방형 건물로, 경복궁의 전각 중 가장 긴 건물 중 하나입니다. 이 독특한 형태는 많은 관리들이 동시에 업무를 볼 수 있는 사무 공간으로 설계되었기 때문입니다. 근정전 서쪽에 위치하여 서편 행각의 일부를 이루며, 조선 말기 군국기무처와 내각이 이곳에서 국가 개혁을 논의하기도 했습니다.",
                        "en" to "Sujeongjeon is a long rectangular building with ten bays at the front, making it one of the longest halls in Gyeongbokgung. This unique shape was designed as an office space accommodating many officials working simultaneously. Located west of Geunjeongjeon as part of the western corridor, the late Joseon Gunguk Gimucheo (Grand Council for State Affairs) and cabinet held discussions on national reform here.",
                        "ja" to "修政殿は正面10間の長い長方形の建物で、景福宮の殿閣の中で最も長い建物の一つです。この独特な形状は、多くの官吏が同時に業務を行えるオフィス空間として設計されたためです。勤政殿の西側に位置し、西側行閣の一部をなし、朝鮮末期には軍国機務処と内閣がここで国家改革を議論しました。",
                        "zh" to "修政殿是正面十间的长方形建筑，是景福宫殿阁中最长的建筑之一。这种独特的形态是为了让众多官吏能同时办公而设计的。位于勤政殿西侧，是西侧行廊的一部分，朝鲜末期军国机务处和内阁曾在此讨论国家改革。"
                    )
                ),
                HeritageChunk(
                    chunkId = "suj_03",
                    section = "관람 포인트",
                    title = "사정전 마당에서 바라보는 전경",
                    titleMap = mapOf(
                        "ko" to "사정전 마당에서 바라보는 전경",
                        "en" to "Panoramic View from Sajeongjeon Courtyard",
                        "ja" to "思政殿の庭から眺める全景",
                        "zh" to "从思政殿庭院眺望的全景"
                    ),
                    keywords = listOf("전경", "북악산", "위치", "서쪽"),
                    content = "수정전은 사정전 마당의 서편에 위치해 있어, 사정전 앞마당에서 수정전을 바라보면 뒤편으로 인왕산의 능선이 펼쳐지는 아름다운 풍경을 감상할 수 있습니다. 이 위치는 경복궁의 핵심 축선을 벗어나 비교적 조용한 관람이 가능한 지점으로, 많은 방문객이 지나치지만 경복궁의 학문적 역사를 떠올리기에 좋은 장소입니다.",
                    contentMap = mapOf(
                        "ko" to "수정전은 사정전 마당의 서편에 위치해 있어, 사정전 앞마당에서 수정전을 바라보면 뒤편으로 인왕산의 능선이 펼쳐지는 아름다운 풍경을 감상할 수 있습니다. 이 위치는 경복궁의 핵심 축선을 벗어나 비교적 조용한 관람이 가능한 지점으로, 많은 방문객이 지나치지만 경복궁의 학문적 역사를 떠올리기에 좋은 장소입니다.",
                        "en" to "Located on the western side of Sajeongjeon courtyard, viewing Sujeongjeon from the courtyard reveals the beautiful Inwangsan ridge unfolding behind it. This location, off the main palace axis, offers relatively quiet viewing — often passed by many visitors, yet an ideal spot for reflecting on Gyeongbokgung's scholarly heritage.",
                        "ja" to "修政殿は思政殿の庭の西側に位置しており、庭から修政殿を眺めると、背後に仁王山の稜線が広がる美しい風景を楽しむことができます。景福宮の中心軸線から外れた比較的静かな場所で、多くの訪問者が通り過ぎますが、景福宮の学問的歴史を思い起こすのにふさわしい場所です。",
                        "zh" to "修政殿位于思政殿庭院的西侧，从庭院眺望修政殿，背后是仁王山的山脊线，风景优美。这里偏离景福宫的中心轴线，参观相对安静，虽然许多游客会经过此处，却是回味景福宫学术历史的好地方。"
                    )
                ),
                HeritageChunk(
                    chunkId = "suj_04",
                    section = "트리비아",
                    title = "갑오개혁의 무대",
                    titleMap = mapOf(
                        "ko" to "갑오개혁의 무대",
                        "en" to "The Stage of the Gabo Reform",
                        "ja" to "甲午改革の舞台",
                        "zh" to "甲午改革的舞台"
                    ),
                    keywords = listOf("갑오개혁", "군국기무처", "근대화", "개혁"),
                    content = "1894년 갑오개혁 당시, 수정전은 군국기무처(軍國機務處)가 설치된 곳이었습니다. 이곳에서 신분제 폐지, 과거제 혁파, 근대적 내각제 도입 등 조선 사회를 근본적으로 바꾸는 역사적 결정들이 내려졌습니다. 수정전은 조선의 전통 학문에서 근대 개혁까지, 한국 지성사의 변화를 모두 품고 있는 특별한 공간입니다.",
                    contentMap = mapOf(
                        "ko" to "1894년 갑오개혁 당시, 수정전은 군국기무처(軍國機務處)가 설치된 곳이었습니다. 이곳에서 신분제 폐지, 과거제 혁파, 근대적 내각제 도입 등 조선 사회를 근본적으로 바꾸는 역사적 결정들이 내려졌습니다. 수정전은 조선의 전통 학문에서 근대 개혁까지, 한국 지성사의 변화를 모두 품고 있는 특별한 공간입니다.",
                        "en" to "During the Gabo Reform of 1894, Sujeongjeon housed the Gunguk Gimucheo (Grand Council for State Affairs). Here, historically transformative decisions were made — the abolition of the caste system, the dissolution of the civil service examination, and the introduction of a modern cabinet system. Sujeongjeon is a unique space encompassing all of Korea's intellectual history, from traditional Joseon scholarship to modern reform.",
                        "ja" to "1894年の甲午改革の際、修政殿には軍国機務処が設置されました。ここで身分制の廃止、科挙制の改革、近代的な内閣制の導入など、朝鮮社会を根本的に変える歴史的決定が下されました。修政殿は朝鮮の伝統学問から近代改革まで、韓国の知性史の変遷をすべて抱く特別な空間です。",
                        "zh" to "1894年甲午改革期间，修政殿设立了军国机务处。在此做出了废除身份制度、革除科举制度、引入近代内阁制等从根本上改变朝鲜社会的历史性决定。修政殿是一个特殊的空间，承载了从朝鲜传统学问到近代改革的整个韩国知识史。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "suj_signboard",
                    displayName = mapOf("ko" to "수정전 현판", "en" to "Sujeongjeon Signboard", "ja" to "修政殿の扁額", "zh" to "修政殿匾额"),
                    visualHints = listOf("large wooden sign on long rectangular hall", "palace hall name plaque on single-story elongated building", "nameplate on longest hall in the palace"),
                    linkedChunkId = "suj_01",
                    locationHint = mapOf("ko" to "긴 장방형 수정전 정면 중앙 현판.", "en" to "Center front signboard of long rectangular Sujeongjeon.", "ja" to "長方形の修政殿正面中央の扁額。", "zh" to "长方形修政殿正面中央的匾额。")
                ),
                SubElement(
                    id = "suj_corridor",
                    displayName = mapOf("ko" to "수정전 행각 (복도)", "en" to "Sujeongjeon Corridor", "ja" to "修政殿の行閣（回廊）", "zh" to "修政殿行廊（走廊）"),
                    visualHints = listOf("long covered wooden corridor connecting palace buildings", "traditional korean walkway with tiled roof connecting halls", "wooden corridor along the western side of palace courtyard"),
                    linkedChunkId = "suj_02",
                    locationHint = mapOf("ko" to "수정전 서쪽 행각(복도) 목조 기둥.", "en" to "Sujeongjeon's western corridor — wooden pillars.", "ja" to "修政殿西側の行閣（回廊）の木柱。", "zh" to "修政殿西侧行廊（走廊）的木柱。")
                )
            ),
            coverImageAsset = "heritage/sujeongjeon_cover.jpg",
            galleryImageAssets = listOf("heritage/sujeongjeon_1.jpg", "heritage/sujeongjeon_2.jpg", "heritage/sujeongjeon_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 강녕전 (Gangnyeongjeon) — 왕의 침전
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "gangnyeongjeon",
            title = "강녕전 (Gangnyeongjeon)",
            titleMap = mapOf(
                "ko" to "강녕전 (Gangnyeongjeon)",
                "en" to "Gangnyeongjeon Hall",
                "ja" to "康寧殿 (カンニョンジョン)",
                "zh" to "康宁殿"
            ),
            aliases = listOf("강녕전", "Gangnyeongjeon", "Gangnyeongjeon Hall", "康寧殿"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "royal_residence",
            shortDescription = "왕의 일상생활과 수면이 이루어지던 침전으로, '건강하고 편안함'이라는 뜻의 왕실 사적 공간입니다.",
            shortDescMap = mapOf(
                "ko" to "왕의 일상생활과 수면이 이루어지던 침전으로, '건강하고 편안함'이라는 뜻의 왕실 사적 공간입니다.",
                "en" to "The king's private bedchamber where daily life and sleep took place, meaning 'health and tranquility.'",
                "ja" to "王の日常生活と睡眠が行われた寝殿で、「健康で穏やか」という意味の王室の私的空間です。",
                "zh" to "国王日常生活与就寝的寝殿，寓意“健康安宁”，是王室的私人空间。"
            ),
            latitude = 37.5808,
            longitude = 126.9770,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "gangnyeongjeon_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "강녕전(康寧殿)은 교태전과 함께 왕과 왕비가 일상생활을 하던 침전이다. 강녕전은 왕의 침전으로 ‘강녕’은 ‘편안하고 건강하다’라는 뜻이다. 왕은 이곳에서 독서와 휴식 등 일상 뿐 아니라 신하들과 은밀한 정무를 보기도 하였다. 강녕전은 ‘정(井)’자 모양으로 9개의 방을 구성하여 가운데 방은 왕이 사용하고, 주위의 방에서는 상궁이 숙직하였다. 건물 앞 넓은 월대가 있고, 지붕 위에 용마루가 없는 건물이다. 그러나 1917년 창덕궁에 대화재가 나면서 창덕궁의 침전(대조전과 희정당 등)이 소실되자 강녕전을 옮겨다가 희정당 복원에 사용되었고, 지금의 강녕전은 1995년에 복원한 것이 강녕전 전체 전경 26. 4. 19. 오후 4:37 강녕전 주변으로는 경성전(慶成殿, ‘경성’ : 완성함을 기뻐함), 연생전(延生殿, ‘연생’ : 생기를 맞이함), 응지당(膺祉堂, ‘응지’ : 복을 받음), 연길당(延吉堂, ‘연길’ : 복을 맞아들임 강녕전 부속건물이 있다. 26. 4. 19. 오후 4:37",
                    contentMap = mapOf("ko" to "강녕전(康寧殿)은 교태전과 함께 왕과 왕비가 일상생활을 하던 침전이다. 강녕전은 왕의 침전으로 ‘강녕’은 ‘편안하고 건강하다’라는 뜻이다. 왕은 이곳에서 독서와 휴식 등 일상 뿐 아니라 신하들과 은밀한 정무를 보기도 하였다. 강녕전은 ‘정(井)’자 모양으로 9개의 방을 구성하여 가운데 방은 왕이 사용하고, 주위의 방에서는 상궁이 숙직하였다. 건물 앞 넓은 월대가 있고, 지붕 위에 용마루가 없는 건물이다. 그러나 1917년 창덕궁에 대화재가 나면서 창덕궁의 침전(대조전과 희정당 등)이 소실되자 강녕전을 옮겨다가 희정당 복원에 사용되었고, 지금의 강녕전은 1995년에 복원한 것이 강녕전 전체 전경 26. 4. 19. 오후 4:37 강녕전 주변으로는 경성전(慶成殿, ‘경성’ : 완성함을 기뻐함), 연생전(延生殿, ‘연생’ : 생기를 맞이함), 응지당(膺祉堂, ‘응지’ : 복을 받음), 연길당(延吉堂, ‘연길’ : 복을 맞아들임 강녕전 부속건물이 있다. 26. 4. 19. 오후 4:37")
                ),
                HeritageChunk(
                    chunkId = "gang_01",
                    section = "역사",
                    title = "왕의 사적 공간, 강녕전",
                    titleMap = mapOf(
                        "ko" to "왕의 사적 공간, 강녕전",
                        "en" to "The King's Private Quarters, Gangnyeongjeon",
                        "ja" to "王の私的空間、康寧殿",
                        "zh" to "国王的私人空间，康宁殿"
                    ),
                    keywords = listOf("침전", "왕의 생활", "내전", "사적"),
                    content = "강녕전은 경복궁의 내전 영역에 위치한 왕의 침전입니다. '강녕(康寧)'이란 '건강하고 편안하다'는 뜻으로, 오복(五福) 중 하나를 의미합니다. 이곳은 왕이 공식 업무를 마치고 돌아와 쉬고, 잠을 자며, 때로는 가까운 신하와 비공식적인 대화를 나누던 사적 공간이었습니다. 사정전(편전)의 바로 북쪽에 위치하여, 공적 영역에서 사적 영역으로의 전환이 자연스럽게 이루어지는 구조입니다.",
                    contentMap = mapOf(
                        "ko" to "강녕전은 경복궁의 내전 영역에 위치한 왕의 침전입니다. '강녕(康寧)'이란 '건강하고 편안하다'는 뜻으로, 오복(五福) 중 하나를 의미합니다. 이곳은 왕이 공식 업무를 마치고 돌아와 쉬고, 잠을 자며, 때로는 가까운 신하와 비공식적인 대화를 나누던 사적 공간이었습니다. 사정전(편전)의 바로 북쪽에 위치하여, 공적 영역에서 사적 영역으로의 전환이 자연스럽게 이루어지는 구조입니다.",
                        "en" to "Gangnyeongjeon is the king's bedchamber located in the inner court area of Gyeongbokgung. 'Gangnyeong' means 'health and tranquility,' representing one of the Five Blessings (Obok). This was the private space where the king returned after official duties to rest and sleep, sometimes having informal conversations with close officials. Located directly north of Sajeongjeon (the executive office), the transition from public to private domain flows naturally.",
                        "ja" to "康寧殿は景福宮の内殿エリアに位置する王の寝殿です。「康寧」とは「健康で穏やか」という意味で、五福の一つを意味します。ここは王が公式業務を終えて戻り、休息し、眠り、時には親しい臣下と非公式な会話を交わした私的空間でした。思政殿（便殿）のすぐ北に位置し、公的領域から私的領域への移行が自然に行われる構造です。",
                        "zh" to "康宁殿位于景福宫内殿区域，是国王的寝殿。“康宁”意为“健康安宁”，是五福之一。这里是国王结束公务后回来休息、睡觉，有时与亲近的臣子进行非正式交谈的私人空间。位于思政殿（便殿）正北方，从公共区域到私人区域的过渡自然流畅。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gang_02",
                    section = "건축",
                    title = "용마루가 없는 특별한 지붕",
                    titleMap = mapOf(
                        "ko" to "용마루가 없는 특별한 지붕",
                        "en" to "The Special Roof Without a Ridge",
                        "ja" to "棟がない特別な屋根",
                        "zh" to "没有正脊的特殊屋顶"
                    ),
                    keywords = listOf("용마루", "지붕", "온돌", "구들"),
                    content = "강녕전의 가장 독특한 건축적 특징은 지붕에 용마루(가장 윗부분의 수평 마루)가 없다는 점입니다. 이는 왕이 곧 '용'이므로 용 위에 또 다른 용마루를 올릴 수 없다는 상징적 이유 때문입니다. 또한 왕의 침전답게 온돌(구들)이 설치되어 있어 겨울에도 따뜻하게 지낼 수 있었으며, 중앙의 대청마루는 여름철 통풍을 위한 개방 공간이었습니다.",
                    contentMap = mapOf(
                        "ko" to "강녕전의 가장 독특한 건축적 특징은 지붕에 용마루(가장 윗부분의 수평 마루)가 없다는 점입니다. 이는 왕이 곧 '용'이므로 용 위에 또 다른 용마루를 올릴 수 없다는 상징적 이유 때문입니다. 또한 왕의 침전답게 온돌(구들)이 설치되어 있어 겨울에도 따뜻하게 지낼 수 있었으며, 중앙의 대청마루는 여름철 통풍을 위한 개방 공간이었습니다.",
                        "en" to "Gangnyeongjeon's most distinctive architectural feature is the absence of a yongmaru (the horizontal ridge at the very top of the roof). This is because the king was symbolically a 'dragon,' and placing another dragon ridge above a dragon was considered inappropriate. As a royal bedchamber, ondol underfloor heating was installed for warmth in winter, while the central daecheongmaru (wooden-floored hall) served as an open ventilation space during summer.",
                        "ja" to "康寧殿の最も独特な建築的特徴は、屋根に龍棟（最上部の水平な棟）がないことです。これは王がすなわち「龍」であるため、龍の上にさらに龍棟を載せることはできないという象徴的な理由からです。また、王の寝殿らしくオンドル（床暖房）が設置されており、冬でも暖かく過ごすことができました。中央の大庁の間は夏の通風のための開放空間でした。",
                        "zh" to "康宁殿最独特的建筑特征是屋顶没有正脊（屋顶最顶部的水平脊）。这是因为国王就是“龙”，不能在龙之上再放一条龙脊的象征性原因。作为王的寝殿，设有暖炕（地暖），冬天也能温暖居住。中央的大厅是夏季通风的开放空间。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gang_03",
                    section = "관람 포인트",
                    title = "내전의 중심축과 행각",
                    titleMap = mapOf(
                        "ko" to "내전의 중심축과 행각",
                        "en" to "The Central Axis and Corridors of the Inner Palace",
                        "ja" to "内殿の中心軸と行閣",
                        "zh" to "内殿的中心轴线与行廊"
                    ),
                    keywords = listOf("축선", "행각", "연생전", "경성전"),
                    content = "강녕전은 경복궁의 중심축 위에 위치하며, 근정전-사정전-강녕전-교태전으로 이어지는 남북 축선의 핵심 요소입니다. 좌우에는 연생전(延生殿)과 경성전(慶成殿)이 부속 건물로 배치되어 있으며, 이 건물들은 행각(복도)으로 연결되어 하나의 폐쇄적인 사적 영역을 형성합니다. 이 구조를 통해 외전(공적 영역)과 내전(사적 영역)이 자연스럽게 구분됩니다.",
                    contentMap = mapOf(
                        "ko" to "강녕전은 경복궁의 중심축 위에 위치하며, 근정전-사정전-강녕전-교태전으로 이어지는 남북 축선의 핵심 요소입니다. 좌우에는 연생전(延生殿)과 경성전(慶成殿)이 부속 건물로 배치되어 있으며, 이 건물들은 행각(복도)으로 연결되어 하나의 폐쇄적인 사적 영역을 형성합니다. 이 구조를 통해 외전(공적 영역)과 내전(사적 영역)이 자연스럽게 구분됩니다.",
                        "en" to "Gangnyeongjeon sits on the central axis of Gyeongbokgung, forming a key element of the north-south axis running from Geunjeongjeon to Sajeongjeon to Gangnyeongjeon to Gyotaejeon. Yeongsaengjeon and Gyeongseongjeon flank it as annexes, connected by haenggak (corridors) to form a closed private domain. This structure naturally separates the outer court (public) from the inner court (private).",
                        "ja" to "康寧殿は景福宮の中心軸上に位置し、勤政殿-思政殿-康寧殿-交泰殿とつながる南北軸線の核心要素です。左右には延生殿と慶成殿が付属建物として配置され、行閣（回廊）で結ばれて一つの閉鎖的な私的領域を形成しています。この構造によって外殿（公的領域）と内殿（私的領域）が自然に区分されます。",
                        "zh" to "康宁殿位于景福宫的中心轴线上，是从勤政殿到思政殿、康宁殿、交泰殿南北轴线的核心要素。左右配置有延生殿和庆成殿作为附属建筑，通过行廊（走廊）相连，形成一个封闭的私人区域。通过这种结构，外殿（公共区域）与内殿（私人区域）自然区分。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gang_04",
                    section = "트리비아",
                    title = "세 번이나 불탄 왕의 침전",
                    titleMap = mapOf(
                        "ko" to "세 번이나 불탄 왕의 침전",
                        "en" to "The King's Bedchamber That Burned Three Times",
                        "ja" to "三度も焼失した王の寝殿",
                        "zh" to "三度被烧的国王寝殿"
                    ),
                    keywords = listOf("화재", "중건", "복원", "풍수"),
                    content = "강녕전은 경복궁 역사상 가장 많이 화재를 겪은 건물 중 하나입니다. 1553년(명종 8년), 1876년(고종 13년), 그리고 일제강점기 때까지 세 차례 이상 소실과 복원을 반복했습니다. 특히 1553년 화재 때는 왕이 급히 피신해야 했으며, 당시 사람들은 풍수적으로 '화기(火氣)'가 강한 위치라 해석하기도 했습니다. 현재의 건물은 1995년에 복원된 것입니다.",
                    contentMap = mapOf(
                        "ko" to "강녕전은 경복궁 역사상 가장 많이 화재를 겪은 건물 중 하나입니다. 1553년(명종 8년), 1876년(고종 13년), 그리고 일제강점기 때까지 세 차례 이상 소실과 복원을 반복했습니다. 특히 1553년 화재 때는 왕이 급히 피신해야 했으며, 당시 사람들은 풍수적으로 '화기(火氣)'가 강한 위치라 해석하기도 했습니다. 현재의 건물은 1995년에 복원된 것입니다.",
                        "en" to "Gangnyeongjeon is one of the buildings with the most fire damage in Gyeongbokgung's history. It was destroyed and rebuilt more than three times — in 1553 (King Myeongjong's 8th year), 1876 (King Gojong's 13th year), and during the Japanese colonial period. During the 1553 fire, the king had to evacuate urgently, and people interpreted the location as having strong 'fire energy' in feng shui terms. The current building was restored in 1995.",
                        "ja" to "康寧殿は景福宮の歴史上最も多く火災に遭った建物の一つです。1553年（明宗8年）、1876年（高宗13年）、そして日本統治時代まで3回以上の焼失と復元を繰り返しました。特に1553年の火災の際には王が急いで避難しなければならず、当時の人々は風水的に「火気」が強い場所と解釈しました。現在の建物は1995年に復元されたものです。",
                        "zh" to "康宁殿是景福宫历史上遭受火灾最多的建筑之一。1553年（明宗8年）、1876年（高宗13年）以及日据时期，反复经历了三次以上的焚毁与重建。特别是1553年的火灾中，国王不得不紧急避难，当时人们从风水角度解读此处“火气”旺盛。现在的建筑是1995年复原的。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "gang_no_ridge",
                    displayName = mapOf("ko" to "용마루 없는 지붕", "en" to "Ridgeless Roof", "ja" to "棟のない屋根", "zh" to "无正脊屋顶"),
                    visualHints = listOf("traditional palace roof without horizontal ridge on top", "smooth roof top line without the usual ridge", "palace building with uniquely flat roof peak"),
                    linkedChunkId = "gang_02",
                    locationHint = mapOf("ko" to "강녕전 지붕 꼭대기 — 용마루가 없습니다.", "en" to "Gangnyeongjeon's roof top — no central ridge.", "ja" to "康寧殿の屋根の頂上 — 龍棟なし。", "zh" to "康宁殿屋顶顶部——没有正脊。")
                ),
                SubElement(
                    id = "gang_signboard",
                    displayName = mapOf("ko" to "강녕전 현판", "en" to "Gangnyeongjeon Signboard", "ja" to "康寧殿の扁額", "zh" to "康宁殿匾额"),
                    visualHints = listOf("large wooden sign with korean characters on palace bedchamber", "nameplate on ridgeless roof palace hall", "rectangular palace name plaque"),
                    linkedChunkId = "gang_01",
                    locationHint = mapOf("ko" to "강녕전 정면 지붕 밑 중앙 현판.", "en" to "Signboard at center beneath Gangnyeongjeon's front roof.", "ja" to "康寧殿正面の屋根下中央の扁額。", "zh" to "康宁殿正面屋檐下中央的匾额。")
                ),
                SubElement(
                    id = "gang_ondol",
                    displayName = mapOf("ko" to "온돌 아궁이", "en" to "Ondol Furnace Opening", "ja" to "オンドルの焚き口", "zh" to "暖炕灶口"),
                    visualHints = listOf("stone furnace opening at base of palace wall", "traditional underfloor heating hole in stone foundation", "small arched opening in building foundation for fire"),
                    linkedChunkId = "gang_02",
                    locationHint = mapOf("ko" to "강녕전 돌 기단 하부의 온돌 아궁이 입구.", "en" to "Ondol furnace openings at Gangnyeongjeon's foundation base.", "ja" to "康寧殿の石基壇下部のオンドルの焚き口。", "zh" to "康宁殿石基台下部的暖炕灶口。")
                )
            ),
            coverImageAsset = "heritage/gangnyeongjeon_cover.jpg",
            galleryImageAssets = listOf("heritage/gangnyeongjeon_1.jpg", "heritage/gangnyeongjeon_2.jpg", "heritage/gangnyeongjeon_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 교태전 (Gyotaejeon) — 왕비의 침전
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "gyotaejeon",
            title = "교태전 (Gyotaejeon)",
            titleMap = mapOf(
                "ko" to "교태전 (Gyotaejeon)",
                "en" to "Gyotaejeon Hall",
                "ja" to "交泰殿 (キョテジョン)",
                "zh" to "交泰殿"
            ),
            aliases = listOf("교태전", "Gyotaejeon", "Gyotaejeon Hall", "交泰殿"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "royal_residence",
            shortDescription = "왕비의 침전이자 내명부의 중심으로, 뒤뜰의 아미산 굴뚝이 아름답기로 유명한 전각입니다.",
            shortDescMap = mapOf(
                "ko" to "왕비의 침전이자 내명부의 중심으로, 뒤뜰의 아미산 굴뚝이 아름답기로 유명한 전각입니다.",
                "en" to "The queen's bedchamber and center of the inner court, famous for the beautiful Amisan chimneys in its rear garden.",
                "ja" to "王妃の寝殿であり内命婦の中心で、裏庭の峨嵋山の煙突が美しいことで有名な殿閣です。",
                "zh" to "王妃的寝殿，也是内命妇的中心，因后花园峨眉山烟囱的精美而闻名的殿阁。"
            ),
            latitude = 37.5813,
            longitude = 126.9770,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "gyotaejeon_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "교태전(交泰殿)은 강녕전과 함께 왕과 왕비가 일상생활을 하던 침전이자, 왕비의 생활공간이다. ‘교태’는 ‘천지, 음양이 잘 어울려 태평을 이루다’라는 뜻이다. 교태전은 궁궐의 가운데에 위치하고 있어, 왕비의 생활공간이기 때문에 중궁전이라고도 부른다. 교태전은 경복궁 창건 당시에는 없었으나 1440년(세종 22)에 세워진 것으로 추정된다. 강녕전과 찬가지로 지붕 위에 용마루가 없고 내부 모습은 비슷하나, 건물 앞에 월대는 없다. 1917년 창덕궁에 대화재가 나면서 창덕궁의 침전(대조전과 희정당 등)이 소실되자 교태전을 옮겨다가 대조전 복원에 사용되었고, 지금의 교태전은 1995년에 복원하였다. 교태전 쪽에는 천문관측 기구를 모아두고 연구하였던 흠경각(欽敬閣, ‘흠경’ : 하늘을 공경하여 공손히 사람에게 필요한 시간을 줌)과 교태전의 부속건물로 불교 행사를 행하였던 함원전 교태전 전체 전경 26. 4. 19. 오후 4:37 元殿, ‘함원’ : 원기를 간직함) 등이 있다. 교태전 뒤로는 아미산(峨嵋山)이라는 왕비를 위한 후원을 조성하였다. 이곳은 계단식 화단과 땅 밑으로 연기 길을 내어 후원으로 연결된 굴뚝이 아름답다. 굴뚝은 4개가 있는데 형의 모양으로 사군자, 십장생 등의 무늬를 새겨 악귀를 막고 장수를 기원하는 의미를 담았다. 아미산 굴뚝은 1985년 보물로 지정되었다. 26. 4. 19. 오후 4:37",
                    contentMap = mapOf("ko" to "교태전(交泰殿)은 강녕전과 함께 왕과 왕비가 일상생활을 하던 침전이자, 왕비의 생활공간이다. ‘교태’는 ‘천지, 음양이 잘 어울려 태평을 이루다’라는 뜻이다. 교태전은 궁궐의 가운데에 위치하고 있어, 왕비의 생활공간이기 때문에 중궁전이라고도 부른다. 교태전은 경복궁 창건 당시에는 없었으나 1440년(세종 22)에 세워진 것으로 추정된다. 강녕전과 찬가지로 지붕 위에 용마루가 없고 내부 모습은 비슷하나, 건물 앞에 월대는 없다. 1917년 창덕궁에 대화재가 나면서 창덕궁의 침전(대조전과 희정당 등)이 소실되자 교태전을 옮겨다가 대조전 복원에 사용되었고, 지금의 교태전은 1995년에 복원하였다. 교태전 쪽에는 천문관측 기구를 모아두고 연구하였던 흠경각(欽敬閣, ‘흠경’ : 하늘을 공경하여 공손히 사람에게 필요한 시간을 줌)과 교태전의 부속건물로 불교 행사를 행하였던 함원전 교태전 전체 전경 26. 4. 19. 오후 4:37 元殿, ‘함원’ : 원기를 간직함) 등이 있다. 교태전 뒤로는 아미산(峨嵋山)이라는 왕비를 위한 후원을 조성하였다. 이곳은 계단식 화단과 땅 밑으로 연기 길을 내어 후원으로 연결된 굴뚝이 아름답다. 굴뚝은 4개가 있는데 형의 모양으로 사군자, 십장생 등의 무늬를 새겨 악귀를 막고 장수를 기원하는 의미를 담았다. 아미산 굴뚝은 1985년 보물로 지정되었다. 26. 4. 19. 오후 4:37")
                ),
                HeritageChunk(
                    chunkId = "gyo_01",
                    section = "역사",
                    title = "왕비의 거처, 교태전",
                    titleMap = mapOf(
                        "ko" to "왕비의 거처, 교태전",
                        "en" to "The Queen's Residence, Gyotaejeon",
                        "ja" to "王妃の住まい、交泰殿",
                        "zh" to "王妃的居所，交泰殿"
                    ),
                    keywords = listOf("왕비", "중궁전", "내명부", "내전"),
                    content = "교태전은 경복궁 왕비의 공식 거처인 중궁전(中宮殿)입니다. '교태(交泰)'란 주역(周易)에서 따온 말로, '천지가 어울려 만물이 소통한다'는 뜻이며, 왕(하늘)과 왕비(땅)의 조화를 상징합니다. 강녕전 바로 뒤에 위치하여, 왕과 왕비의 거처가 전후로 이어지는 궁궐 내전의 핵심 구조를 이룹니다. 왕비는 이곳에서 내명부(궁녀와 후궁)를 관리하고, 궁중 살림을 총괄했습니다.",
                    contentMap = mapOf(
                        "ko" to "교태전은 경복궁 왕비의 공식 거처인 중궁전(中宮殿)입니다. '교태(交泰)'란 주역(周易)에서 따온 말로, '천지가 어울려 만물이 소통한다'는 뜻이며, 왕(하늘)과 왕비(땅)의 조화를 상징합니다. 강녕전 바로 뒤에 위치하여, 왕과 왕비의 거처가 전후로 이어지는 궁궐 내전의 핵심 구조를 이룹니다. 왕비는 이곳에서 내명부(궁녀와 후궁)를 관리하고, 궁중 살림을 총괄했습니다.",
                        "en" to "Gyotaejeon is the queen's official residence, the Junggung-jeon (Central Palace Hall). 'Gyotae' is derived from the I Ching (Book of Changes), meaning 'heaven and earth harmonize and all things communicate,' symbolizing the harmony between king (heaven) and queen (earth). Located directly behind Gangnyeongjeon, it forms the core structure where the king's and queen's residences connect front to back. Here, the queen managed the naemyeongbu (palace ladies and royal concubines) and oversaw palace household affairs.",
                        "ja" to "交泰殿は景福宮の王妃の公式住居である中宮殿です。「交泰」とは周易から取った言葉で、「天地が交わって万物が通じる」という意味であり、王（天）と王妃（地）の調和を象徴しています。康寧殿のすぐ後ろに位置し、王と王妃の住居が前後に連なる宮殿内殿の中核構造を形成しています。王妃はここで内命婦（宮女と後宮）を管理し、宮中の家政を総括しました。",
                        "zh" to "交泰殿是景福宫王妃的正式居所——中宫殿。“交泰”源自《周易》，意为“天地交合，万物通泰”，象征国王（天）与王妃（地）的和谐。位于康宁殿正后方，形成国王与王妃居所前后相连的宫殿内殿核心结构。王妃在此管理内命妇（宫女和嫔妃），统管宫中家务。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gyo_02",
                    section = "건축",
                    title = "용마루 없는 왕비의 전각",
                    titleMap = mapOf(
                        "ko" to "용마루 없는 왕비의 전각",
                        "en" to "The Queen's Hall Without a Roof Ridge",
                        "ja" to "棟のない王妃の殿閣",
                        "zh" to "没有正脊的王妃殿阁"
                    ),
                    keywords = listOf("용마루", "지붕", "내전", "후원"),
                    content = "교태전도 강녕전처럼 용마루가 없는 것이 특징입니다. 왕비 역시 '용'의 반려이므로 용마루를 얹지 않았다는 해석이 있습니다. 건물 뒤편에는 작은 후원(뒤뜰)이 꾸며져 있어, 왕비가 사적인 시간을 보내며 자연을 즐길 수 있도록 배려했습니다. 이 후원이 바로 유명한 아미산 정원입니다.",
                    contentMap = mapOf(
                        "ko" to "교태전도 강녕전처럼 용마루가 없는 것이 특징입니다. 왕비 역시 '용'의 반려이므로 용마루를 얹지 않았다는 해석이 있습니다. 건물 뒤편에는 작은 후원(뒤뜰)이 꾸며져 있어, 왕비가 사적인 시간을 보내며 자연을 즐길 수 있도록 배려했습니다. 이 후원이 바로 유명한 아미산 정원입니다.",
                        "en" to "Like Gangnyeongjeon, Gyotaejeon also lacks a yongmaru roof ridge. One interpretation is that the queen, as the dragon's (king's) consort, similarly could not have a dragon ridge. Behind the building lies a small rear garden (huwon), designed for the queen to enjoy nature during private moments. This garden is the famous Amisan Garden.",
                        "ja" to "交泰殿も康寧殿と同様に龍棟がないのが特徴です。王妃も「龍」の伴侶であるため龍棟を載せなかったという解釈があります。建物の裏側には小さな後苑（裏庭）が設けられ、王妃が私的な時間を過ごしながら自然を楽しめるよう配慮されています。この後苑が有名な峨嵋山庭園です。",
                        "zh" to "交泰殿与康宁殿一样，也没有正脊。有一种解释认为，王妃作为“龙”（国王）的伴侣，同样不能设置龙脊。建筑后方设有小后花园（后苑），让王妃在私人时间里享受自然。这个后花园就是著名的峨眉山花园。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gyo_03",
                    section = "관람 포인트",
                    title = "아미산 굴뚝 — 보물 제811호",
                    titleMap = mapOf(
                        "ko" to "아미산 굴뚝 — 보물 제811호",
                        "en" to "Amisan Chimneys — Treasure No. 811",
                        "ja" to "峨嵋山の煙突 — 宝物第811号",
                        "zh" to "峨眉山烟囱 — 宝物第811号"
                    ),
                    keywords = listOf("아미산", "굴뚝", "보물", "꽃담"),
                    content = "교태전 뒤편 아미산 정원에는 보물 제811호로 지정된 4기의 굴뚝이 있습니다. 이 굴뚝은 교태전 온돌의 연기를 배출하는 기능적 역할을 하면서도, 육각형 벽돌에 덩굴·나비·꽃 등 아름다운 문양을 새겨 장식 예술의 극치를 보여줍니다. 굴뚝 주변에는 단계적으로 쌓은 화단(花壇)과 괴석이 어우러져 작은 산수 정원을 이루고 있으며, '아미산'이라는 이름도 중국 쓰촨성의 명산 아미산에서 따온 것입니다.",
                    contentMap = mapOf(
                        "ko" to "교태전 뒤편 아미산 정원에는 보물 제811호로 지정된 4기의 굴뚝이 있습니다. 이 굴뚝은 교태전 온돌의 연기를 배출하는 기능적 역할을 하면서도, 육각형 벽돌에 덩굴·나비·꽃 등 아름다운 문양을 새겨 장식 예술의 극치를 보여줍니다. 굴뚝 주변에는 단계적으로 쌓은 화단(花壇)과 괴석이 어우러져 작은 산수 정원을 이루고 있으며, '아미산'이라는 이름도 중국 쓰촨성의 명산 아미산에서 따온 것입니다.",
                        "en" to "In the Amisan Garden behind Gyotaejeon stand four chimneys designated as Treasure No. 811. These chimneys serve the functional role of venting smoke from Gyotaejeon's ondol heating, yet showcase the pinnacle of decorative art with hexagonal bricks carved with vines, butterflies, and floral patterns. Surrounding the chimneys, tiered flower beds (hwadan) and ornamental rocks create a miniature landscape garden. The name 'Amisan' is borrowed from the famous Mount Emei in China's Sichuan province.",
                        "ja" to "交泰殿の裏の峨嵋山庭園には、宝物第811号に指定された4基の煙突があります。この煙突は交泰殿のオンドルの煙を排出する機能的な役割を果たしながらも、六角形のレンガに蔓・蝶・花などの美しい文様を彫り込み、装飾芸術の極致を見せています。煙突の周囲には段階的に積んだ花壇と奇石が調和して小さな山水庭園を形成しており、「峨嵋山」という名前は中国四川省の名山、峨眉山から取られたものです。",
                        "zh" to "交泰殿后方的峨眉山花园中矗立着四座被指定为宝物第811号的烟囱。这些烟囱在发挥排放交泰殿暖炕烟气功能的同时，在六角形砖上雕刻了藤蔓、蝴蝶、花卉等精美纹样，展现了装饰艺术的巅峰。烟囱周围层叠的花坛和怪石相映成趣，构成了小型山水花园。“峨眉山”之名取自中国四川省的名山峨眉山。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gyo_04",
                    section = "트리비아",
                    title = "경복궁에서 가장 아름다운 뒤뜰",
                    titleMap = mapOf(
                        "ko" to "경복궁에서 가장 아름다운 뒤뜰",
                        "en" to "The Most Beautiful Rear Garden in Gyeongbokgung",
                        "ja" to "景福宮で最も美しい裏庭",
                        "zh" to "景福宫最美的后花园"
                    ),
                    keywords = listOf("후원", "사계절", "조경", "여성공간"),
                    content = "교태전 아미산 정원은 경복궁 안에서 유일하게 왕비만을 위해 조성된 정원입니다. 매화·모란·국화·동백 등 사계절 꽃이 피도록 설계되어, 왕비가 계절의 변화를 느끼며 마음의 안정을 찾을 수 있도록 했습니다. 이 공간은 조선 시대 여성의 사적 공간이 얼마나 세심하게 배려되었는지를 보여주는 좋은 예입니다.",
                    contentMap = mapOf(
                        "ko" to "교태전 아미산 정원은 경복궁 안에서 유일하게 왕비만을 위해 조성된 정원입니다. 매화·모란·국화·동백 등 사계절 꽃이 피도록 설계되어, 왕비가 계절의 변화를 느끼며 마음의 안정을 찾을 수 있도록 했습니다. 이 공간은 조선 시대 여성의 사적 공간이 얼마나 세심하게 배려되었는지를 보여주는 좋은 예입니다.",
                        "en" to "The Amisan Garden of Gyotaejeon is the only garden in Gyeongbokgung designed exclusively for the queen. It was planned to bloom with flowers in all four seasons — plum blossoms, peonies, chrysanthemums, and camellias — allowing the queen to sense the changing seasons and find peace of mind. This space is a fine example of how thoughtfully private spaces for women were designed in the Joseon Dynasty.",
                        "ja" to "交泰殿の峨嵋山庭園は、景福宮の中で唯一、王妃のためだけに造られた庭園です。梅・牡丹・菊・椿など四季の花が咲くように設計され、王妃が季節の移ろいを感じながら心の安らぎを得られるよう配慮されています。この空間は、朝鮮時代の女性の私的空間がいかに細やかに配慮されていたかを示す好例です。",
                        "zh" to "交泰殿峨眉山花园是景福宫内唯一为王妃专门打造的花园。设计为四季有花——梅花、牡丹、菊花、山茶花等，让王妃感受季节的变化，获得心灵的平静。这个空间是展示朝鲜时代女性私人空间设计之细致周到的绝佳范例。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "gyo_amisan_chimney",
                    displayName = mapOf("ko" to "아미산 굴뚝 (보물)", "en" to "Amisan Chimney (Treasure)", "ja" to "峨嵋山の煙突（宝物）", "zh" to "峨眉山烟囱（宝物）"),
                    visualHints = listOf("ornate hexagonal brick chimney with carved floral patterns", "decorative palace chimney with vine and butterfly motifs", "heritage chimney structure with tiered flower beds"),
                    linkedChunkId = "gyo_03",
                    locationHint = mapOf("ko" to "교태전 뒤편 아미산 정원의 육각형 굴뚝 4기 (보물 제811호).", "en" to "Four hexagonal chimneys in Amisan garden behind Gyotaejeon (Treasure No. 811).", "ja" to "交泰殿の裏の峨嵋山庭園の六角形煙突4基（宝物第811号）。", "zh" to "交泰殿后峨眉山花园的四座六角形烟囱（宝物第811号）。")
                ),
                SubElement(
                    id = "gyo_amisan_garden",
                    displayName = mapOf("ko" to "아미산 화계 (계단식 화단)", "en" to "Amisan Terraced Garden", "ja" to "峨嵋山の花壇（段々花壇）", "zh" to "峨眉山阶梯花坛"),
                    visualHints = listOf("terraced stone flower beds behind palace building", "tiered garden with ornamental rocks and seasonal flowers", "stepped garden arrangement with stone walls behind royal hall"),
                    linkedChunkId = "gyo_04",
                    locationHint = mapOf("ko" to "교태전 바로 뒤 계단식 화단이 있는 작은 정원.", "en" to "Small garden with tiered flower terraces directly behind Gyotaejeon.", "ja" to "交泰殿のすぐ裏、段々花壇のある庭園。", "zh" to "交泰殿正后方带有阶梯式花坛的小花园。")
                ),
                SubElement(
                    id = "gyo_signboard",
                    displayName = mapOf("ko" to "교태전 현판", "en" to "Gyotaejeon Signboard", "ja" to "交泰殿の扁額", "zh" to "交泰殿匾额"),
                    visualHints = listOf("large wooden sign with korean characters on queen's palace", "palace name plaque on ridgeless roof hall", "royal nameplate on queen consort's residence"),
                    linkedChunkId = "gyo_01",
                    locationHint = mapOf("ko" to "교태전 정면 중앙 현판.", "en" to "Center front signboard of Gyotaejeon.", "ja" to "交泰殿正面中央の扁額。", "zh" to "交泰殿正面中央的匾额。")
                ),
                SubElement(
                    id = "gyo_ggotdam",
                    displayName = mapOf("ko" to "교태전 꽃담 (꽃무늬 담장)", "en" to "Gyotaejeon Flower Wall", "ja" to "交泰殿の花塀", "zh" to "交泰殿花墙"),
                    visualHints = listOf("palace wall decorated with floral brick patterns", "traditional korean wall with embedded flower designs", "ornamental wall with plum blossom motifs near chimney"),
                    linkedChunkId = "gyo_03",
                    locationHint = mapOf("ko" to "교태전 주변 담장의 꽃무늬 벽돌.", "en" to "Floral-patterned bricks on walls around Gyotaejeon.", "ja" to "交泰殿周辺の塀の花柄レンガ。", "zh" to "交泰殿周围围墙上的花纹砖。")
                ),
                SubElement(
                    id = "gyo_no_ridge",
                    displayName = mapOf("ko" to "용마루 없는 교태전 지붕", "en" to "Gyotaejeon's Ridgeless Roof", "ja" to "棟のない交泰殿の屋根", "zh" to "无正脊的交泰殿屋顶"),
                    visualHints = listOf("traditional palace roof without horizontal ridge", "smooth roof without ridge tile on top", "palace building with uniquely flat roof peak"),
                    linkedChunkId = "gyo_02",
                    locationHint = mapOf(
                        "ko" to "교태전 지붕을 멀리서 보세요. 일반 전각과 달리 용마루가 없습니다. 왕비도 '용'의 반려라는 상징.",
                        "en" to "Look at Gyotaejeon's roof from afar — no yongmaru ridge, as the queen was consort of the dragon.",
                        "ja" to "交泰殿の屋根を遠くから見てください。龍棟がない — 王妃も「龍」の伴侶であるという象徴。",
                        "zh" to "远观交泰殿屋顶——没有正脊，象征王妃是\"龙\"的伴侣。"
                    )
                )
            ),
            coverImageAsset = "heritage/gyotaejeon_cover.jpg",
            galleryImageAssets = listOf("heritage/gyotaejeon_1.jpg", "heritage/gyotaejeon_2.jpg", "heritage/gyotaejeon_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 자경전 (Jagyeongjeon) — 대왕대비 거처, 십장생 굴뚝
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "jagyeongjeon",
            title = "자경전 (Jagyeongjeon)",
            titleMap = mapOf(
                "ko" to "자경전 (Jagyeongjeon)",
                "en" to "Jagyeongjeon Hall",
                "ja" to "慈慶殿 (チャギョンジョン)",
                "zh" to "慈庆殿"
            ),
            aliases = listOf("자경전", "Jagyeongjeon", "Jagyeongjeon Hall", "慈慶殿"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "east_court",
            shortDescription = "대왕대비(왕의 할머니)의 거처로, 보물 제810호인 십장생 굴뚝과 화려한 꽃담이 유명합니다.",
            shortDescMap = mapOf(
                "ko" to "대왕대비(왕의 할머니)의 거처로, 보물 제810호인 십장생 굴뚝과 화려한 꽃담이 유명합니다.",
                "en" to "The residence of the Queen Dowager (king's grandmother), famous for the Ten Longevity Symbols chimney (Treasure No. 810) and its elaborate flower walls.",
                "ja" to "大王大妃（王の祖母）の居所で、宝物第810号の十長生の煙突と華やかな花塀で有名です。",
                "zh" to "大王大妃（国王的祖母）的居所，以宝物第810号十长生烟囱和华丽的花墙闻名。"
            ),
            latitude = 37.5812,
            longitude = 126.9779,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "jagyeongjeon_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "자경전(慈慶殿)의 ‘자경’은 ‘어머니의 복을 누린다’라는 뜻으로, 정조가 왕위에 오른 후 어머니 혜경궁(헌경황후) 홍씨를 위해 창경궁에 지은 ‘자경당’의 유래가 된다. 자경전은 경 창건 당시에는 없었으나 1867년(고종 4) 경복궁을 다시 지을 때 신정황후 조씨(24대 헌종의 어머니이자 26대 고종의 양어머니, 대한제국 선포 후 황후로 추존됨)를 위해 지은 이다. 그러나 지은 지 얼마 지나지 않아 화재로 소실된 것을 1888년(고종 25)에 다시 지어 지금까지 남아 있는 건물이다. 자경전 주변으로는 복안당(福安堂, ‘복안’ : 복되고 편안 과 청연루(淸讌樓, ‘청연’ : 조촐한 연회), 협경당(協慶堂, ‘협경’ : 함께 경사를 누림) 등 부속건물을 따로 두었는데 모두 연결되어 있다. 자경전은 1985년 보물로 지정되었다. 자경전 전체 전경 26. 4. 19. 오후 4:37 자경전에는 온돌방을 많이 마련했는데, 각 방들과 연결된 8개의 연도를 모아 북쪽 담장에 하나의 큰 굴뚝을 만들었다. 굴뚝 정면 가운데는 해, 산, 물, 돌, 구름, 학, 소나무, 사슴 북, 불로초의 십장생 무늬를 넣었고, 그 위아래로는 학, 나티, 불가사리를 배치하여 악귀를 막고 장수를 기원하는 의미를 담았다. 굴뚝으로서의 실용적인 기능에 충실하면서도 미가 빼어나 조선시대 궁궐 굴뚝 가운데 가장 뛰어나다는 평가를 받고 있다. 자경전 십장생굴뚝은 1985년 보물로 지정되었다. 26. 4. 19. 오후 4:37",
                    contentMap = mapOf("ko" to "자경전(慈慶殿)의 ‘자경’은 ‘어머니의 복을 누린다’라는 뜻으로, 정조가 왕위에 오른 후 어머니 혜경궁(헌경황후) 홍씨를 위해 창경궁에 지은 ‘자경당’의 유래가 된다. 자경전은 경 창건 당시에는 없었으나 1867년(고종 4) 경복궁을 다시 지을 때 신정황후 조씨(24대 헌종의 어머니이자 26대 고종의 양어머니, 대한제국 선포 후 황후로 추존됨)를 위해 지은 이다. 그러나 지은 지 얼마 지나지 않아 화재로 소실된 것을 1888년(고종 25)에 다시 지어 지금까지 남아 있는 건물이다. 자경전 주변으로는 복안당(福安堂, ‘복안’ : 복되고 편안 과 청연루(淸讌樓, ‘청연’ : 조촐한 연회), 협경당(協慶堂, ‘협경’ : 함께 경사를 누림) 등 부속건물을 따로 두었는데 모두 연결되어 있다. 자경전은 1985년 보물로 지정되었다. 자경전 전체 전경 26. 4. 19. 오후 4:37 자경전에는 온돌방을 많이 마련했는데, 각 방들과 연결된 8개의 연도를 모아 북쪽 담장에 하나의 큰 굴뚝을 만들었다. 굴뚝 정면 가운데는 해, 산, 물, 돌, 구름, 학, 소나무, 사슴 북, 불로초의 십장생 무늬를 넣었고, 그 위아래로는 학, 나티, 불가사리를 배치하여 악귀를 막고 장수를 기원하는 의미를 담았다. 굴뚝으로서의 실용적인 기능에 충실하면서도 미가 빼어나 조선시대 궁궐 굴뚝 가운데 가장 뛰어나다는 평가를 받고 있다. 자경전 십장생굴뚝은 1985년 보물로 지정되었다. 26. 4. 19. 오후 4:37")
                ),
                HeritageChunk(
                    chunkId = "jag_01",
                    section = "역사",
                    title = "왕실 어른을 위한 효의 건축",
                    titleMap = mapOf(
                        "ko" to "왕실 어른을 위한 효의 건축",
                        "en" to "Architecture of Filial Piety for the Royal Elder",
                        "ja" to "王室の年長者のための孝の建築",
                        "zh" to "为王室长辈而建的孝之建筑"
                    ),
                    keywords = listOf("대왕대비", "효", "고종", "조대비"),
                    content = "자경전은 1865년 고종 즉위 후 흥선대원군이 경복궁을 중건하면서 대왕대비 조씨(신정왕후)를 위해 지은 전각입니다. '자경(慈慶)'은 '자애로움과 경사스러움'이라는 뜻으로, 왕실의 가장 어른인 대왕대비에 대한 효심을 담고 있습니다. 경복궁의 동쪽에 별도의 영역으로 조성되어 독립적이면서도 존귀한 공간을 형성합니다.",
                    contentMap = mapOf(
                        "ko" to "자경전은 1865년 고종 즉위 후 흥선대원군이 경복궁을 중건하면서 대왕대비 조씨(신정왕후)를 위해 지은 전각입니다. '자경(慈慶)'은 '자애로움과 경사스러움'이라는 뜻으로, 왕실의 가장 어른인 대왕대비에 대한 효심을 담고 있습니다. 경복궁의 동쪽에 별도의 영역으로 조성되어 독립적이면서도 존귀한 공간을 형성합니다.",
                        "en" to "Jagyeongjeon was built when Heungseon Daewongun reconstructed Gyeongbokgung after King Gojong's accession in 1865, for the Queen Dowager Jo (Queen Sinjeong). 'Jagyeong' means 'benevolence and celebration,' embodying filial piety toward the royal family's most senior member. It was established as a separate domain on the eastern side of Gyeongbokgung, forming an independent yet honored space.",
                        "ja" to "慈慶殿は、1865年に高宗即位後、興宣大院君が景福宮を再建する際に大王大妃の趙氏（神貞王后）のために建てた殿閣です。「慈慶」は「慈愛と慶び」という意味で、王室の最年長者である大王大妃への孝心が込められています。景福宮の東側に別の領域として造成され、独立しながらも尊い空間を形成しています。",
                        "zh" to "慈庆殿是1865年高宗即位后，兴宣大院君重建景福宫时为大王大妃赵氏（神贞王后）所建的殿阁。“慈庆”意为“慈爱与喜庆”，寄托了对王室最年长者大王大妃的孝心。建于景福宫东侧的独立区域，形成了独立而尊贵的空间。"
                    )
                ),
                HeritageChunk(
                    chunkId = "jag_02",
                    section = "건축",
                    title = "경복궁 유일의 현존 침전",
                    titleMap = mapOf(
                        "ko" to "경복궁 유일의 현존 침전",
                        "en" to "The Only Surviving Bedchamber in Gyeongbokgung",
                        "ja" to "景福宮唯一の現存する寝殿",
                        "zh" to "景福宫唯一现存的寝殿"
                    ),
                    keywords = listOf("현존", "원형", "ㄱ자", "행각"),
                    content = "자경전은 경복궁에서 일제 강점기의 훼손을 면하고 원형을 가장 잘 보존한 침전입니다. ㄱ자 형태의 독특한 평면 구조를 갖추고 있어, 다른 정방형 전각과 차별됩니다. 이는 대왕대비의 생활 편의를 고려한 설계로, 취사·난방·거주 공간이 하나의 건물 안에서 유기적으로 연결되어 있습니다.",
                    contentMap = mapOf(
                        "ko" to "자경전은 경복궁에서 일제 강점기의 훼손을 면하고 원형을 가장 잘 보존한 침전입니다. ㄱ자 형태의 독특한 평면 구조를 갖추고 있어, 다른 정방형 전각과 차별됩니다. 이는 대왕대비의 생활 편의를 고려한 설계로, 취사·난방·거주 공간이 하나의 건물 안에서 유기적으로 연결되어 있습니다.",
                        "en" to "Jagyeongjeon is the bedchamber that best preserves its original form in Gyeongbokgung, having avoided destruction during the Japanese colonial period. It features a distinctive L-shaped floor plan, differentiating it from other rectangular halls. This design considered the Queen Dowager's living convenience, organically connecting cooking, heating, and living spaces within a single structure.",
                        "ja" to "慈慶殿は景福宮の中で日本統治時代の破壊を免れ、原形を最もよく保存している寝殿です。ㄱ字形の独特な平面構造を持ち、他の四角形の殿閣と差別化されています。これは大王大妃の生活の利便性を考慮した設計で、炊事・暖房・居住空間が一つの建物の中で有機的に繋がっています。",
                        "zh" to "慈庆殿是景福宫中逃过日据时期破坏、原貌保存最好的寝殿。拥有独特的L形平面结构，与其他方形殿阁有所不同。这一设计考虑了大王大妃的生活便利，将烹饪、取暖、居住空间在一栋建筑内有机连接。"
                    )
                ),
                HeritageChunk(
                    chunkId = "jag_03",
                    section = "관람 포인트",
                    title = "십장생 굴뚝 — 보물 제810호",
                    titleMap = mapOf(
                        "ko" to "십장생 굴뚝 — 보물 제810호",
                        "en" to "Ten Longevity Symbols Chimney — Treasure No. 810",
                        "ja" to "十長生の煙突 — 宝物第810号",
                        "zh" to "十长生烟囱 — 宝物第810号"
                    ),
                    keywords = listOf("십장생", "굴뚝", "보물", "장수"),
                    content = "자경전 서쪽 담장에 있는 십장생 굴뚝은 보물 제810호로 지정된 걸작입니다. 해·산·물·구름·소나무·불로초·거북·학·사슴·대나무 등 십장생(十長生) 문양이 벽돌에 정교하게 조각되어 있으며, 장수와 복을 기원하는 의미를 담고 있습니다. 기능적으로는 온돌 연기를 배출하면서도, 예술적으로는 조선 최고의 장식 공예품 중 하나로 평가됩니다.",
                    contentMap = mapOf(
                        "ko" to "자경전 서쪽 담장에 있는 십장생 굴뚝은 보물 제810호로 지정된 걸작입니다. 해·산·물·구름·소나무·불로초·거북·학·사슴·대나무 등 십장생(十長生) 문양이 벽돌에 정교하게 조각되어 있으며, 장수와 복을 기원하는 의미를 담고 있습니다. 기능적으로는 온돌 연기를 배출하면서도, 예술적으로는 조선 최고의 장식 공예품 중 하나로 평가됩니다.",
                        "en" to "The Ten Longevity Symbols chimney on Jagyeongjeon's western wall is a masterpiece designated as Treasure No. 810. The ten symbols of longevity — sun, mountains, water, clouds, pine trees, herb of immortality, turtles, cranes, deer, and bamboo — are intricately carved into the bricks, conveying wishes for long life and blessings. While functionally venting ondol smoke, it is artistically regarded as one of Joseon's finest decorative crafts.",
                        "ja" to "慈慶殿の西側の塀にある十長生の煙突は、宝物第810号に指定された傑作です。太陽・山・水・雲・松・不老草・亀・鶴・鹿・竹など十長生の文様がレンガに精巧に彫刻されており、長寿と福を祈る意味が込められています。機能的にはオンドルの煙を排出しながら、芸術的には朝鮮最高の装飾工芸品の一つとして評価されています。",
                        "zh" to "慈庆殿西侧围墙上的十长生烟囱是被指定为宝物第810号的杰作。日、山、水、云、松、灵芝、龟、鹤、鹿、竹等十长生纹样精巧地雕刻在砖上，寓意祈求长寿和幸福。在功能上排放暖炕烟气的同时，在艺术上被评价为朝鲜最高的装饰工艺品之一。"
                    )
                ),
                HeritageChunk(
                    chunkId = "jag_04",
                    section = "트리비아",
                    title = "꽃담에 숨겨진 장수의 기원",
                    titleMap = mapOf(
                        "ko" to "꽃담에 숨겨진 장수의 기원",
                        "en" to "Wishes for Longevity Hidden in the Flower Walls",
                        "ja" to "花塀に隠された長寿への祈り",
                        "zh" to "花墙中隐藏的长寿祈愿"
                    ),
                    keywords = listOf("꽃담", "만자문", "박쥐", "수복"),
                    content = "자경전 주변 담장에는 '꽃담'이라 불리는 다양한 장식 벽이 있습니다. 매화·모란·연꽃·국화 등의 사계절 꽃, 나비, 만자문(卍字紋), 박쥐(복을 상징) 등이 벽돌과 기와 조각으로 표현되어 있습니다. 이 모든 문양은 대왕대비의 장수와 행복을 기원하는 효심의 표현이며, 경복궁에서 가장 화려한 담장 장식으로 꼽힙니다.",
                    contentMap = mapOf(
                        "ko" to "자경전 주변 담장에는 '꽃담'이라 불리는 다양한 장식 벽이 있습니다. 매화·모란·연꽃·국화 등의 사계절 꽃, 나비, 만자문(卍字紋), 박쥐(복을 상징) 등이 벽돌과 기와 조각으로 표현되어 있습니다. 이 모든 문양은 대왕대비의 장수와 행복을 기원하는 효심의 표현이며, 경복궁에서 가장 화려한 담장 장식으로 꼽힙니다.",
                        "en" to "Around Jagyeongjeon are various decorative walls called 'ggotdam' (flower walls). Plum blossoms, peonies, lotuses, chrysanthemums, butterflies, swastika patterns (卍), and bats (symbolizing good fortune) are expressed in brick and tile. All these motifs are expressions of filial piety wishing for the Queen Dowager's longevity and happiness, and they are considered the most elaborate wall decorations in Gyeongbokgung.",
                        "ja" to "慈慶殿の周囲の塀には「花塀」と呼ばれる様々な装飾壁があります。梅・牡丹・蓮・菊などの四季の花、蝶、卍字紋、コウモリ（福の象徴）などがレンガと瓦の彫刻で表現されています。これらすべての文様は大王大妃の長寿と幸福を祈る孝心の表現であり、景福宮で最も華やかな塀の装飾とされています。",
                        "zh" to "慈庆殿周围的围墙上有各种被称为“花墙”的装饰墙。梅花、牡丹、莲花、菊花等四季花卉，蝴蝶、万字纹、蝙蝠（象征福气）等以砖瓦雕刻表现。这些纹样都是祈愿大王大妃长寿幸福的孝心表达，被评为景福宫最华丽的围墙装饰。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "jag_sipjangsaeng",
                    displayName = mapOf("ko" to "십장생 굴뚝 (보물)", "en" to "Ten Longevity Chimney (Treasure)", "ja" to "十長生の煙突（宝物）", "zh" to "十长生烟囱（宝物）"),
                    visualHints = listOf("ornate brick chimney with carved longevity symbols", "chimney decorated with sun mountain crane deer turtle carvings", "intricately carved palace chimney on western wall"),
                    linkedChunkId = "jag_03",
                    locationHint = mapOf("ko" to "자경전 북쪽 담장의 십장생 굴뚝 (보물 제810호).", "en" to "Ten Longevity chimney on Jagyeongjeon's north wall (Treasure No. 810).", "ja" to "慈慶殿の北側の塀の十長生煙突（宝物第810号）。", "zh" to "慈庆殿北侧围墙上的十长生烟囱（宝物第810号）。")
                ),
                SubElement(
                    id = "jag_ggotdam",
                    displayName = mapOf("ko" to "자경전 꽃담", "en" to "Jagyeongjeon Flower Wall", "ja" to "慈慶殿の花塀", "zh" to "慈庆殿花墙"),
                    visualHints = listOf("palace wall with colorful floral brick patterns", "decorative wall with plum blossom and butterfly motifs", "traditional korean flower wall with bat and swastika carvings"),
                    linkedChunkId = "jag_04",
                    locationHint = mapOf("ko" to "자경전 주변의 화려한 꽃담 장식.", "en" to "Elaborate flower wall decorations around Jagyeongjeon.", "ja" to "慈慶殿周辺の華やかな花塀装飾。", "zh" to "慈庆殿周围华丽的花墙装饰。")
                ),
                SubElement(
                    id = "jag_signboard",
                    displayName = mapOf("ko" to "자경전 현판", "en" to "Jagyeongjeon Signboard", "ja" to "慈慶殿の扁額", "zh" to "慈庆殿匾额"),
                    visualHints = listOf("large wooden sign with korean characters on L-shaped palace hall", "palace name plaque on queen dowager's residence", "nameplate on traditional korean palace building"),
                    linkedChunkId = "jag_01",
                    locationHint = mapOf("ko" to "자경전 정면 중앙의 현판.", "en" to "Jagyeongjeon's center front signboard.", "ja" to "慈慶殿正面中央の扁額。", "zh" to "慈庆殿正面中央的匾额。")
                )
            ),
            coverImageAsset = "heritage/jagyeongjeon_cover.jpg",
            galleryImageAssets = listOf("heritage/jagyeongjeon_1.jpg", "heritage/jagyeongjeon_2.jpg", "heritage/jagyeongjeon_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 향원정 (Hyangwonjeong) — 연못 위 육각 정자
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "hyangwonjeong",
            title = "향원정 (Hyangwonjeong)",
            titleMap = mapOf(
                "ko" to "향원정 (Hyangwonjeong)",
                "en" to "Hyangwonjeong Pavilion",
                "ja" to "香遠亭 (ヒャンウォンジョン)",
                "zh" to "香远亭"
            ),
            aliases = listOf("향원정", "Hyangwonjeong", "Hyangwonjeong Pavilion", "香遠亭"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "rear_garden",
            shortDescription = "경복궁 후원 연못 위에 세워진 아름다운 육각 정자로, 취향교와 함께 경복궁 최고의 포토 스팟입니다.",
            shortDescMap = mapOf(
                "ko" to "경복궁 후원 연못 위에 세워진 아름다운 육각 정자로, 취향교와 함께 경복궁 최고의 포토 스팟입니다.",
                "en" to "A beautiful hexagonal pavilion built on a pond in the rear garden of Gyeongbokgung, together with Chwihyanggyo bridge, one of the palace's best photo spots.",
                "ja" to "景福宮の後苑の池の上に建てられた美しい六角形の東屋で、醉香橋と共に景福宮最高のフォトスポットです。",
                "zh" to "建在景福宫后花园池塘上的美丽六角亭，与醉香桥一起是景福宫最佳拍照地点。"
            ),
            latitude = 37.5822,
            longitude = 126.9768,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "hyangwonjeong_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "향원정(香遠亭)의 ‘향원’은 ‘향이 멀리 간다’라는 뜻으로, 원래 이곳에는 세조 대에 세운 취로정이 있었다. 그러다가 1873년(고종 10) 고종이 건청궁을 지을 때 그 앞에 연못(향원 을 파서 연못 가운데 섬을 만들고 2층의 육모지붕의 형태로 지었다. 향원정을 가기 위해 지은 다리는 ‘향기에 취한다’라는 뜻에 취향교(醉香橋)라고 불렀다. 원래 취향교는 무지개 양의 흰색 다리로 조성되었으나 한국전쟁 때 파괴된 것을 1953년에 복원하였는데 향원정 남쪽으로 복원되었다. 2017년부터 2020년까지 향원정 보수공사 때 취향교는 원래의 리로 복원하였다. 그리고 향원지 서북쪽에는 향원지의 근원이 되는 열상진원(洌上眞源)샘은 향원지를 시작으로 경회루 연못을 거쳐 경복궁 밖으로 흘러 나갔다. 향원정은 2012년 보물로 지정되었 향원정 전체 전경 26. 4. 19. 오후 4:38 ⊙ 향원정 주련(柱聯) 내용 (위치 : 향원정 외부 기둥) ① 玉池龍躍舞(옥지용약무) : 아름다운 연못에 용이 뛰쳐 오르며 춤춘다. ② 千山華月逈(천산화월형) : 천산에는 빛나는 달이 멀리까지 비추고, ③ 萬里衆星明(만리중성명) : 만리에는 뭇 별들이 밝게 빛나네. ④ 崑閬雲霞積(곤랑운하적) : 곤륜산 꼭대기에는 구름 노을 쌓였고, ⑤ 蓬壺日月長(봉호일월장) : 신선 사는 봉래에는 세월이 길도다. 26. 4. 19. 오후 4:38",
                    contentMap = mapOf("ko" to "향원정(香遠亭)의 ‘향원’은 ‘향이 멀리 간다’라는 뜻으로, 원래 이곳에는 세조 대에 세운 취로정이 있었다. 그러다가 1873년(고종 10) 고종이 건청궁을 지을 때 그 앞에 연못(향원 을 파서 연못 가운데 섬을 만들고 2층의 육모지붕의 형태로 지었다. 향원정을 가기 위해 지은 다리는 ‘향기에 취한다’라는 뜻에 취향교(醉香橋)라고 불렀다. 원래 취향교는 무지개 양의 흰색 다리로 조성되었으나 한국전쟁 때 파괴된 것을 1953년에 복원하였는데 향원정 남쪽으로 복원되었다. 2017년부터 2020년까지 향원정 보수공사 때 취향교는 원래의 리로 복원하였다. 그리고 향원지 서북쪽에는 향원지의 근원이 되는 열상진원(洌上眞源)샘은 향원지를 시작으로 경회루 연못을 거쳐 경복궁 밖으로 흘러 나갔다. 향원정은 2012년 보물로 지정되었 향원정 전체 전경 26. 4. 19. 오후 4:38 ⊙ 향원정 주련(柱聯) 내용 (위치 : 향원정 외부 기둥) ① 玉池龍躍舞(옥지용약무) : 아름다운 연못에 용이 뛰쳐 오르며 춤춘다. ② 千山華月逈(천산화월형) : 천산에는 빛나는 달이 멀리까지 비추고, ③ 萬里衆星明(만리중성명) : 만리에는 뭇 별들이 밝게 빛나네. ④ 崑閬雲霞積(곤랑운하적) : 곤륜산 꼭대기에는 구름 노을 쌓였고, ⑤ 蓬壺日月長(봉호일월장) : 신선 사는 봉래에는 세월이 길도다. 26. 4. 19. 오후 4:38")
                ),
                HeritageChunk(
                    chunkId = "hyang_01",
                    section = "역사",
                    title = "고종의 휴식처, 향원정",
                    titleMap = mapOf(
                        "ko" to "고종의 휴식처, 향원정",
                        "en" to "King Gojong's Retreat, Hyangwonjeong",
                        "ja" to "高宗の憩いの場、香遠亭",
                        "zh" to "高宗的休憩之所，香远亭"
                    ),
                    keywords = listOf("고종", "후원", "연못", "건청궁"),
                    content = "향원정은 1873년 고종이 건청궁을 지으면서 함께 조성한 정자입니다. '향원(香遠)'은 주돈이의 《애련설》에서 따온 말로, '향기가 멀리 퍼진다'는 뜻입니다. 건청궁에서 일상을 보내던 고종이 이곳에서 연못을 바라보며 쉬었다고 전해집니다. 주변의 향원지(연못)는 조선 전기부터 존재하던 것을 고종이 확장·정비한 것입니다.",
                    contentMap = mapOf(
                        "ko" to "향원정은 1873년 고종이 건청궁을 지으면서 함께 조성한 정자입니다. '향원(香遠)'은 주돈이의 《애련설》에서 따온 말로, '향기가 멀리 퍼진다'는 뜻입니다. 건청궁에서 일상을 보내던 고종이 이곳에서 연못을 바라보며 쉬었다고 전해집니다. 주변의 향원지(연못)는 조선 전기부터 존재하던 것을 고종이 확장·정비한 것입니다.",
                        "en" to "Hyangwonjeong was built in 1873 when King Gojong constructed Geoncheongung Palace. 'Hyangwon' derives from Zhou Dunyi's essay 'On Loving the Lotus,' meaning 'fragrance travels far.' It is said that King Gojong, who spent his daily life at Geoncheongung, would rest here gazing at the pond. The surrounding Hyangwonji (pond) existed from the early Joseon period and was expanded and maintained by King Gojong.",
                        "ja" to "香遠亭は1873年に高宗が乾清宮を建てる際に一緒に造営した東屋です。「香遠」は周敦頤の《愛蓮説》から取った言葉で、「香りが遠くまで広がる」という意味です。乾清宮で日常を過ごしていた高宗がここで池を眺めながら休んだと伝えられています。周囲の香遠池（池）は朝鮮前期から存在していたものを高宗が拡張・整備したものです。",
                        "zh" to "香远亭是1873年高宗建造乾清宫时一同修建的亭子。“香远”取自周敦颐《爱莲说》，意为“香气远播”。据说在乾清宫生活的高宗常在此眺望池塘休息。周围的香远池从朝鲜前期就已存在，后由高宗扩建整修。"
                    )
                ),
                HeritageChunk(
                    chunkId = "hyang_02",
                    section = "건축",
                    title = "연못 위 2층 육각 정자",
                    titleMap = mapOf(
                        "ko" to "연못 위 2층 육각 정자",
                        "en" to "A Two-Story Hexagonal Pavilion on the Pond",
                        "ja" to "池の上の二階建て六角亭",
                        "zh" to "池上的两层六角亭"
                    ),
                    keywords = listOf("육각정", "누각", "취향교", "목조"),
                    content = "향원정은 연못 가운데 인공 섬 위에 세워진 2층 육각형 정자입니다. 6개의 기둥이 만드는 개방적인 구조 덕분에 어느 방향에서든 주변 경관을 감상할 수 있습니다. 연못의 북쪽에서 나무다리인 취향교(醉香橋)를 건너 정자에 이를 수 있으며, '향기에 취하는 다리'라는 이름처럼 연꽃 향기 속을 걷는 느낌을 줍니다.",
                    contentMap = mapOf(
                        "ko" to "향원정은 연못 가운데 인공 섬 위에 세워진 2층 육각형 정자입니다. 6개의 기둥이 만드는 개방적인 구조 덕분에 어느 방향에서든 주변 경관을 감상할 수 있습니다. 연못의 북쪽에서 나무다리인 취향교(醉香橋)를 건너 정자에 이를 수 있으며, '향기에 취하는 다리'라는 이름처럼 연꽃 향기 속을 걷는 느낌을 줍니다.",
                        "en" to "Hyangwonjeong is a two-story hexagonal pavilion built on an artificial island in the middle of the pond. Its open structure with six pillars allows visitors to admire the surrounding scenery from any direction. Chwihyanggyo (Bridge of Intoxicating Fragrance) — a wooden bridge from the north side of the pond — leads to the pavilion, evoking the feeling of walking through lotus fragrance, as its name suggests.",
                        "ja" to "香遠亭は池の中央の人工島の上に建てられた2階建ての六角形の東屋です。6本の柱が作る開放的な構造のおかげで、どの方向からでも周囲の景観を楽しむことができます。池の北側から木造の橋である醉香橋を渡って東屋に行くことができ、「香りに酔う橋」という名前のように蓮の香りの中を歩く感覚を与えてくれます。",
                        "zh" to "香远亭是建在池塘中央人工岛上的两层六角形亭子。6根柱子构成的开放式结构使得从任何方向都能欣赏周围的景色。从池塘北侧经过木桥醉香桥可以到达亭子，正如“沉醉于花香的桥”之名，让人有漫步在莲花香气中的感觉。"
                    )
                ),
                HeritageChunk(
                    chunkId = "hyang_03",
                    section = "관람 포인트",
                    title = "사계절 최고의 포토 스팟",
                    titleMap = mapOf(
                        "ko" to "사계절 최고의 포토 스팟",
                        "en" to "The Best Photo Spot in All Seasons",
                        "ja" to "四季を通じた最高のフォトスポット",
                        "zh" to "四季最佳拍照地点"
                    ),
                    keywords = listOf("사진", "반영", "연꽃", "단풍"),
                    content = "향원정은 경복궁에서 가장 인기 있는 사진 촬영 명소 중 하나입니다. 봄에는 벚꽃, 여름에는 연꽃, 가을에는 단풍, 겨울에는 눈 덮인 정자와 얼어붙은 연못이 각각의 절경을 선사합니다. 특히 바람 없는 날 수면에 비치는 정자의 반영(리플렉션)은 사진작가들 사이에서 손꼽히는 장면입니다.",
                    contentMap = mapOf(
                        "ko" to "향원정은 경복궁에서 가장 인기 있는 사진 촬영 명소 중 하나입니다. 봄에는 벚꽃, 여름에는 연꽃, 가을에는 단풍, 겨울에는 눈 덮인 정자와 얼어붙은 연못이 각각의 절경을 선사합니다. 특히 바람 없는 날 수면에 비치는 정자의 반영(리플렉션)은 사진작가들 사이에서 손꼽히는 장면입니다.",
                        "en" to "Hyangwonjeong is one of the most popular photography spots in Gyeongbokgung. Each season offers its own spectacular scenery: cherry blossoms in spring, lotus flowers in summer, autumn foliage, and the snow-covered pavilion on a frozen pond in winter. The reflection of the pavilion on calm water on a windless day is especially prized among photographers.",
                        "ja" to "香遠亭は景福宮で最も人気のある撮影スポットの一つです。春は桜、夏は蓮の花、秋は紅葉、冬は雪に覆われた東屋と凍った池がそれぞれの絶景を見せてくれます。特に風のない日に水面に映る東屋のリフレクションは、写真家たちの間で屈指の名場面です。",
                        "zh" to "香远亭是景福宫最受欢迎的拍照胜地之一。春天的樱花、夏天的莲花、秋天的红叶、冬天的雪景亭台和结冰的池塘，每个季节都呈现出不同的绝景。尤其是无风之日水面上亭子的倒影，被摄影师们视为最上镜的画面。"
                    )
                ),
                HeritageChunk(
                    chunkId = "hyang_04",
                    section = "트리비아",
                    title = "취향교의 위치가 바뀐 이유",
                    titleMap = mapOf(
                        "ko" to "취향교의 위치가 바뀐 이유",
                        "en" to "Why Chwihyanggyo Bridge Changed Its Location",
                        "ja" to "醉香橋の位置が変わった理由",
                        "zh" to "醉香桥位置变更的原因"
                    ),
                    keywords = listOf("취향교", "다리", "복원", "남쪽"),
                    content = "원래 취향교는 건청궁 방향인 북쪽에 놓여 있어, 왕이 건청궁에서 바로 건너올 수 있었습니다. 그러나 일제강점기에 다리가 남쪽으로 옮겨졌고, 오랫동안 남쪽 다리로 알려져 왔습니다. 2021년 문화재청은 고증을 거쳐 다리를 원래 위치인 북쪽으로 복원하였습니다. 현재의 취향교는 전통 방식으로 제작된 목조 다리이며, 복원 후 방문객들은 조선 시대 왕과 같은 동선으로 정자를 방문할 수 있게 되었습니다.",
                    contentMap = mapOf(
                        "ko" to "원래 취향교는 건청궁 방향인 북쪽에 놓여 있어, 왕이 건청궁에서 바로 건너올 수 있었습니다. 그러나 일제강점기에 다리가 남쪽으로 옮겨졌고, 오랫동안 남쪽 다리로 알려져 왔습니다. 2021년 문화재청은 고증을 거쳐 다리를 원래 위치인 북쪽으로 복원하였습니다. 현재의 취향교는 전통 방식으로 제작된 목조 다리이며, 복원 후 방문객들은 조선 시대 왕과 같은 동선으로 정자를 방문할 수 있게 되었습니다.",
                        "en" to "Originally, Chwihyanggyo was located on the north side facing Geoncheongung, allowing the king to cross directly from his residence. However, during the Japanese colonial period, the bridge was relocated to the south and was long known in that position. In 2021, the Cultural Heritage Administration restored the bridge to its original northern location based on historical research. The current Chwihyanggyo is a wooden bridge built using traditional methods, and visitors can now follow the same path the Joseon kings once took.",
                        "ja" to "元々、醉香橋は乾清宮の方向である北側に架けられており、王が乾清宮から直接渡ることができました。しかし日本統治時代に橋は南側に移され、長い間南側の橋として知られてきました。2021年に文化財庁は考証を経て、橋を元の位置である北側に復元しました。現在の醉香橋は伝統的な方法で製作された木造の橋で、復元後、訪問者は朝鮮時代の王と同じ動線で東屋を訪れることができるようになりました。",
                        "zh" to "醉香桥原来位于通向乾清宫的北侧，国王可以直接从乾清宫走过来。但在日据时期，桥被移到了南侧，长期以来一直被认为在南侧。2021年文化遗产厅经过考证，将桥恢复到原来的北侧位置。现在的醉香桥是用传统工法制作的木桥，复原后游客可以按照朝鲜时代国王同样的路线参观亭子。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "hyang_chwihyanggyo",
                    displayName = mapOf("ko" to "취향교 (나무 다리)", "en" to "Chwihyanggyo Bridge", "ja" to "醉香橋（木の橋）", "zh" to "醉香桥（木桥）"),
                    visualHints = listOf("wooden bridge over pond leading to hexagonal pavilion", "traditional korean wooden bridge crossing lotus pond", "curved wooden walkway over water to island pavilion"),
                    linkedChunkId = "hyang_02",
                    locationHint = mapOf("ko" to "향원지 북쪽에서 향원정으로 가는 나무 다리.", "en" to "Wooden bridge from north side of Hyangwonji to the pavilion.", "ja" to "香遠池の北側から香遠亭への木造橋。", "zh" to "香远池北侧通往香远亭的木桥。")
                ),
                SubElement(
                    id = "hyang_pond",
                    displayName = mapOf("ko" to "향원지 (연못)", "en" to "Hyangwonji Pond", "ja" to "香遠池", "zh" to "香远池"),
                    visualHints = listOf("large palace pond with hexagonal pavilion in center", "lotus pond reflecting pavilion and sky", "rectangular pond surrounding island with pavilion"),
                    linkedChunkId = "hyang_03",
                    locationHint = mapOf("ko" to "향원정이 떠 있는 사각형 연못 주변을 걷기.", "en" to "Walk around the rectangular pond surrounding Hyangwonjeong.", "ja" to "香遠亭が浮かぶ四角い池の周りを散策。", "zh" to "环绕香远亭所在的方形池塘漫步。")
                )
            ),
            coverImageAsset = "heritage/hyangwonjeong_cover.jpg",
            galleryImageAssets = listOf("heritage/hyangwonjeong_1.jpg", "heritage/hyangwonjeong_2.jpg", "heritage/hyangwonjeong_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 건청궁 (Geoncheongung) — 고종의 거처, 을미사변 현장
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "geoncheongung",
            title = "건청궁 (Geoncheongung)",
            titleMap = mapOf(
                "ko" to "건청궁 (Geoncheongung)",
                "en" to "Geoncheongung Palace",
                "ja" to "乾清宮 (コンチョングン)",
                "zh" to "乾清宫"
            ),
            aliases = listOf("건청궁", "Geoncheongung", "Geoncheongung Palace", "乾清宮"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "rear_garden",
            shortDescription = "고종이 일상 거처로 삼았던 궁으로, 1895년 을미사변(명성황후 시해)의 비극적 현장이기도 합니다.",
            shortDescMap = mapOf(
                "ko" to "고종이 일상 거처로 삼았던 궁으로, 1895년 을미사변(명성황후 시해)의 비극적 현장이기도 합니다.",
                "en" to "The palace where King Gojong resided daily, also the tragic site of the 1895 Eulmi Incident (assassination of Empress Myeongseong).",
                "ja" to "高宗が日常の居所としていた宮で、1895年の乙未事変（明成皇后暗殺）の悲劇的な現場でもあります。",
                "zh" to "高宗日常居住的宫殿，也是1895年乙未事变（明成皇后被害）的悲剧现场。"
            ),
            latitude = 37.5826,
            longitude = 126.9770,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "geoncheongung_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "건청궁(乾淸宮)은 1873년(고종 10)에 왕과 왕비의 생활공간으로 지어진 궁 안의 궁이다. ‘건청’은 ‘하늘은 맑다’라는 뜻으로, 경복궁에서 가장 북쪽에 위치하고 있다. 건물은 민간 대부 집의 형태를 따르면서 화려하고 섬세한 치장을 가미하여 지었다. 건청궁의 왕의 생활공간인 장안당(長安堂, ‘장안’ : 오랫동안 평안하게 지냄)과 왕비의 생활공간인 곤녕합( 閤, ‘곤녕’ : 땅이 편안함) 등으로 구성되어 있는데, 장안당과 곤녕합은 복도로 이어져 있다. 이곳에서 고종과 명성황후는 10년 정도 생활하였다. 그러나 1895년(고종 32) 곤녕합 호루(玉壺樓, ‘옥호’ : 옥으로 만든 호리병)에서 명성황후가 시해되는 을미사변이 일어나 고종은 이듬해 러시아공사관으로 거처를 옮기게 되었다. 이후 1909년(융희 3)에 철거 고 일제강점기 때 미술관이 들어섰다가 철거된 후, 2007년에 지금의 모습으로 복원하였다. 건청궁 전체 전경 26. 4. 19. 오후 4:38",
                    contentMap = mapOf("ko" to "건청궁(乾淸宮)은 1873년(고종 10)에 왕과 왕비의 생활공간으로 지어진 궁 안의 궁이다. ‘건청’은 ‘하늘은 맑다’라는 뜻으로, 경복궁에서 가장 북쪽에 위치하고 있다. 건물은 민간 대부 집의 형태를 따르면서 화려하고 섬세한 치장을 가미하여 지었다. 건청궁의 왕의 생활공간인 장안당(長安堂, ‘장안’ : 오랫동안 평안하게 지냄)과 왕비의 생활공간인 곤녕합( 閤, ‘곤녕’ : 땅이 편안함) 등으로 구성되어 있는데, 장안당과 곤녕합은 복도로 이어져 있다. 이곳에서 고종과 명성황후는 10년 정도 생활하였다. 그러나 1895년(고종 32) 곤녕합 호루(玉壺樓, ‘옥호’ : 옥으로 만든 호리병)에서 명성황후가 시해되는 을미사변이 일어나 고종은 이듬해 러시아공사관으로 거처를 옮기게 되었다. 이후 1909년(융희 3)에 철거 고 일제강점기 때 미술관이 들어섰다가 철거된 후, 2007년에 지금의 모습으로 복원하였다. 건청궁 전체 전경 26. 4. 19. 오후 4:38")
                ),
                HeritageChunk(
                    chunkId = "geon_01",
                    section = "역사",
                    title = "고종의 근대화 거점",
                    titleMap = mapOf(
                        "ko" to "고종의 근대화 거점",
                        "en" to "King Gojong's Base for Modernization",
                        "ja" to "高宗の近代化の拠点",
                        "zh" to "高宗的近代化基地"
                    ),
                    keywords = listOf("고종", "근대화", "전기", "을미사변"),
                    content = "건청궁은 1873년 고종이 아버지 흥선대원군의 섭정에서 벗어나 친정을 시작하면서 직접 지은 왕의 사저입니다. 경복궁 북쪽 후원에 위치하며, 기존 궁궐 전각과 다른 자유로운 배치를 보여줍니다. 고종은 이곳에서 전기 조명을 도입(경복궁에 한국 최초의 전등 설치)하는 등 근대화를 추진했습니다. 그러나 1895년 이곳에서 명성황후가 일본 낭인들에 의해 시해되는 을미사변이 일어나 한국 근대사의 가장 비극적인 장소가 되었습니다.",
                    contentMap = mapOf(
                        "ko" to "건청궁은 1873년 고종이 아버지 흥선대원군의 섭정에서 벗어나 친정을 시작하면서 직접 지은 왕의 사저입니다. 경복궁 북쪽 후원에 위치하며, 기존 궁궐 전각과 다른 자유로운 배치를 보여줍니다. 고종은 이곳에서 전기 조명을 도입(경복궁에 한국 최초의 전등 설치)하는 등 근대화를 추진했습니다. 그러나 1895년 이곳에서 명성황후가 일본 낭인들에 의해 시해되는 을미사변이 일어나 한국 근대사의 가장 비극적인 장소가 되었습니다.",
                        "en" to "Geoncheongung was built in 1873 by King Gojong himself when he began direct rule, free from his father Heungseon Daewongun's regency. Located in the northern rear garden of Gyeongbokgung, it shows a free-form layout different from traditional palace halls. Gojong introduced electric lighting here (Korea's first electric lights installed at Gyeongbokgung) as part of modernization efforts. However, in 1895, the Eulmi Incident occurred here — the assassination of Empress Myeongseong by Japanese agents — making it one of the most tragic sites in modern Korean history.",
                        "ja" to "乾清宮は1873年に高宗が父・興宣大院君の摂政から離れ、親政を始める際に自ら建てた王の私邸です。景福宮の北側の後苑に位置し、既存の宮殿殿閣とは異なる自由な配置を見せています。高宗はここで電気照明を導入（景福宮に韓国初の電灯を設置）するなど近代化を推進しました。しかし1895年、ここで明成皇后が日本の浪人によって暗殺される乙未事変が起き、韓国近代史で最も悲劇的な場所となりました。",
                        "zh" to "乾清宫是1873年高宗摆脱父亲兴宣大院君的摄政、开始亲政时亲自建造的国王私邸。位于景福宫北侧后花园，展现出与传统宫殿不同的自由布局。高宗在此引入电灯照明（在景福宫安装韩国首批电灯）等推进近代化。然而1895年，明成皇后在此被日本浪人杀害的乙未事变发生，使之成为韩国近代史上最悲惨的场所。"
                    )
                ),
                HeritageChunk(
                    chunkId = "geon_02",
                    section = "건축",
                    title = "궁궐 속의 작은 궁궐",
                    titleMap = mapOf(
                        "ko" to "궁궐 속의 작은 궁궐",
                        "en" to "A Small Palace Within the Palace",
                        "ja" to "宮殿の中の小さな宮殿",
                        "zh" to "宫殿中的小宫殿"
                    ),
                    keywords = listOf("장안당", "곤녕합", "녹산", "사저"),
                    content = "건청궁은 경복궁 안에 있지만 독립적인 궁궐 형태를 갖추고 있습니다. 고종의 거처인 장안당(長安堂), 명성황후의 거처인 곤녕합(坤寧閤), 그리고 주변 행각과 정원으로 구성되어 있습니다. 기존 경복궁의 엄격한 좌우대칭 배치와 달리 자연 지형을 살린 자유로운 배치가 특징이며, 이는 고종의 근대적 감각을 반영합니다. 2007년에 복원되어 현재의 모습을 갖추었습니다.",
                    contentMap = mapOf(
                        "ko" to "건청궁은 경복궁 안에 있지만 독립적인 궁궐 형태를 갖추고 있습니다. 고종의 거처인 장안당(長安堂), 명성황후의 거처인 곤녕합(坤寧閤), 그리고 주변 행각과 정원으로 구성되어 있습니다. 기존 경복궁의 엄격한 좌우대칭 배치와 달리 자연 지형을 살린 자유로운 배치가 특징이며, 이는 고종의 근대적 감각을 반영합니다. 2007년에 복원되어 현재의 모습을 갖추었습니다.",
                        "en" to "Geoncheongung, while inside Gyeongbokgung, takes the form of an independent palace. It comprises Jangandang (King Gojong's quarters), Gonnyeonghap (Empress Myeongseong's quarters), and surrounding corridors and gardens. Unlike Gyeongbokgung's strict bilateral symmetry, it features a free-form layout utilizing natural terrain, reflecting Gojong's modern sensibility. It was restored in 2007 to its current appearance.",
                        "ja" to "乾清宮は景福宮の中にありながら、独立した宮殿の形態を備えています。高宗の居所である長安堂、明成皇后の居所である坤寧閤、そして周囲の行閣と庭園で構成されています。景福宮の厳格な左右対称の配置とは異なり、自然の地形を活かした自由な配置が特徴で、高宗の近代的な感覚を反映しています。2007年に復元され、現在の姿になりました。",
                        "zh" to "乾清宫虽然在景福宫内，但具有独立宫殿的形态。由高宗居所长安堂、明成皇后居所坤宁阁以及周围行廊和花园组成。与景福宫严格的左右对称布局不同，其特点是利用自然地形的自由布局，反映了高宗的近代审美。2007年复原，呈现出目前的面貌。"
                    )
                ),
                HeritageChunk(
                    chunkId = "geon_03",
                    section = "관람 포인트",
                    title = "을미사변 현장, 곤녕합",
                    titleMap = mapOf(
                        "ko" to "을미사변 현장, 곤녕합",
                        "en" to "Site of the Eulmi Incident: Gonnyeonghap",
                        "ja" to "乙未事変の現場、坤寧閤",
                        "zh" to "乙未事变现场——坤宁阁"
                    ),
                    keywords = listOf("을미사변", "명성황후", "곤녕합", "추모"),
                    content = "곤녕합은 명성황후의 거처이자 1895년 을미사변이 일어난 비극적인 현장입니다. 새벽에 일본 낭인들이 이곳에 침입하여 명성황후를 시해했으며, 이 사건은 한국 근대사에서 가장 충격적인 사건 중 하나입니다. 현재 복원된 곤녕합 앞에는 추모의 의미를 담은 안내판이 설치되어 있으며, 역사 교육의 현장으로 많은 방문객이 찾습니다.",
                    contentMap = mapOf(
                        "ko" to "곤녕합은 명성황후의 거처이자 1895년 을미사변이 일어난 비극적인 현장입니다. 새벽에 일본 낭인들이 이곳에 침입하여 명성황후를 시해했으며, 이 사건은 한국 근대사에서 가장 충격적인 사건 중 하나입니다. 현재 복원된 곤녕합 앞에는 추모의 의미를 담은 안내판이 설치되어 있으며, 역사 교육의 현장으로 많은 방문객이 찾습니다.",
                        "en" to "Gonnyeonghap was Empress Myeongseong's residence and the tragic site of the 1895 Eulmi Incident. Japanese agents broke in at dawn and assassinated the empress, making it one of the most shocking events in modern Korean history. A memorial information board now stands before the restored Gonnyeonghap, and many visitors come as a site of historical education.",
                        "ja" to "坤寧閤は明成皇后の居所であり、1895年に乙未事変が起きた悲劇的な現場です。未明に日本の浪人が侵入して明成皇后を暗殺し、この事件は韓国近代史で最も衝撃的な事件の一つです。現在復元された坤寧閤の前には追悼の意味を込めた案内板が設置されており、歴史教育の現場として多くの訪問者が訪れます。",
                        "zh" to "坤宁阁是明成皇后的居所，也是1895年乙未事变的悲剧现场。黎明时分日本浪人闯入此处杀害了明成皇后，这是韩国近代史上最震惊的事件之一。现在复原的坤宁阁前设有纪念说明牌，作为历史教育的现场，吸引了众多访客。"
                    )
                ),
                HeritageChunk(
                    chunkId = "geon_04",
                    section = "트리비아",
                    title = "한국 최초의 전기 조명",
                    titleMap = mapOf(
                        "ko" to "한국 최초의 전기 조명",
                        "en" to "Korea's First Electric Lighting",
                        "ja" to "韓国初の電気照明",
                        "zh" to "韩国最早的电灯照明"
                    ),
                    keywords = listOf("전기", "에디슨", "근대화", "전등"),
                    content = "1887년 고종은 건청궁에 한국 최초의 전등을 설치했습니다. 에디슨이 백열전구를 상용화한 지 불과 8년 만의 일로, 당시 동아시아에서도 매우 빠른 전기 도입이었습니다. 고종은 서양 문물에 큰 관심을 보였으며, 건청궁은 조선이 근대 국가로 전환하려던 노력을 상징하는 공간입니다. 다만 전등 가동에 필요한 증기기관의 소음이 상당했다는 기록도 전해집니다.",
                    contentMap = mapOf(
                        "ko" to "1887년 고종은 건청궁에 한국 최초의 전등을 설치했습니다. 에디슨이 백열전구를 상용화한 지 불과 8년 만의 일로, 당시 동아시아에서도 매우 빠른 전기 도입이었습니다. 고종은 서양 문물에 큰 관심을 보였으며, 건청궁은 조선이 근대 국가로 전환하려던 노력을 상징하는 공간입니다. 다만 전등 가동에 필요한 증기기관의 소음이 상당했다는 기록도 전해집니다.",
                        "en" to "In 1887, King Gojong installed Korea's first electric lights at Geoncheongung. This was just eight years after Edison commercialized the incandescent bulb — remarkably early even by East Asian standards. Gojong showed great interest in Western civilization, and Geoncheongung symbolizes Joseon's efforts to transform into a modern state. Records also mention that the steam engine needed to power the lights was quite noisy.",
                        "ja" to "1887年、高宗は乾清宮に韓国初の電灯を設置しました。エジソンが白熱電球を商用化してからわずか8年後のことで、当時の東アジアでも非常に早い電気の導入でした。高宗は西洋文物に大きな関心を示し、乾清宮は朝鮮が近代国家に転換しようとした努力を象徴する空間です。ただし、電灯の稼働に必要な蒸気機関の騒音がかなりあったという記録も伝わっています。",
                        "zh" to "1887年，高宗在乾清宫安装了韩国首批电灯。这距爱迪生将白炽灯泡商业化仅8年，即使在当时的东亚也是非常早的电气化引入。高宗对西方文明表现出浓厚兴趣，乾清宫象征着朝鲜转型为近代国家的努力。不过据记载，电灯运行所需的蒸汽机噪音相当大。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "geon_jangandang",
                    displayName = mapOf("ko" to "장안당 (고종 거처)", "en" to "Jangandang (King's Quarters)", "ja" to "長安堂（高宗の居所）", "zh" to "长安堂（高宗居所）"),
                    visualHints = listOf("traditional korean palace building in rear garden area", "king's private residence hall with open courtyard", "wooden palace hall with simple elegant design"),
                    linkedChunkId = "geon_02",
                    locationHint = mapOf("ko" to "건청궁 내 고종 거처 장안당, 민가풍 건축.", "en" to "Jangandang — King Gojong's residence in private-house style.", "ja" to "乾清宮の長安堂 — 高宗の居所、民家風。", "zh" to "乾清宫内高宗居所长安堂，民居风建筑。")
                ),
                SubElement(
                    id = "geon_gonnyeonghap",
                    displayName = mapOf("ko" to "곤녕합 (명성황후 거처)", "en" to "Gonnyeonghap (Empress's Quarters)", "ja" to "坤寧閤（明成皇后の居所）", "zh" to "坤宁阁（明成皇后居所）"),
                    visualHints = listOf("empress residence building with memorial sign", "palace hall with historical significance memorial", "traditional korean building with somber memorial plaque"),
                    linkedChunkId = "geon_03",
                    locationHint = mapOf("ko" to "건청궁 내 곤녕합 — 명성황후 거처, 을미사변 현장.", "en" to "Gonnyeonghap — Empress Myeongseong's residence / Eulmi Incident site.", "ja" to "坤寧閤 — 明成皇后の居所、乙未事変の現場。", "zh" to "坤宁阁——明成皇后居所，乙未事变现场。")
                ),
                SubElement(
                    id = "geon_signboard",
                    displayName = mapOf("ko" to "건청궁 현판", "en" to "Geoncheongung Signboard", "ja" to "乾清宮の扁額", "zh" to "乾清宫匾额"),
                    visualHints = listOf("palace name plaque on rear garden palace complex", "wooden sign with Korean characters on king's private palace", "nameplate on palace within palace compound"),
                    linkedChunkId = "geon_01",
                    locationHint = mapOf("ko" to "건청궁 정문 위 현판.", "en" to "Signboard above Geoncheongung's main gate.", "ja" to "乾清宮正門上の扁額。", "zh" to "乾清宫正门上的匾额。")
                ),
                SubElement(
                    id = "geon_electric_light",
                    displayName = mapOf("ko" to "최초의 전등 (기념 표지)", "en" to "First Electric Light Marker", "ja" to "韓国初の電灯（記念標）", "zh" to "韩国最早电灯纪念碑"),
                    visualHints = listOf("information marker about electric lighting", "historical plaque about 1887 Korea's first electric light", "memorial sign explaining early electrification"),
                    linkedChunkId = "geon_04",
                    locationHint = mapOf(
                        "ko" to "건청궁 경내에서 1887년 한국 최초 전등 설치를 기념하는 표지판을 찾아보세요.",
                        "en" to "Find the marker in Geoncheongung grounds commemorating Korea's first electric light installation in 1887.",
                        "ja" to "乾清宮の敷地内で1887年の韓国初の電灯設置を記念する案内板を探してください。",
                        "zh" to "在乾清宫院内寻找纪念1887年韩国首盏电灯安装的标识牌。"
                    )
                )
            ),
            coverImageAsset = "heritage/geoncheongung_cover.jpg",
            galleryImageAssets = listOf("heritage/geoncheongung_1.jpg", "heritage/geoncheongung_2.jpg", "heritage/geoncheongung_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 집옥재 (Jibokjae) — 고종의 서재, 중국식 건축
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "jibokjae",
            title = "집옥재 (Jibokjae)",
            titleMap = mapOf(
                "ko" to "집옥재 (Jibokjae)",
                "en" to "Jibokjae Library",
                "ja" to "集玉斎 (チボクチェ)",
                "zh" to "集玉斋"
            ),
            aliases = listOf("집옥재", "Jibokjae", "Jibokjae Library", "集玉斎", "集玉斋"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "rear_garden",
            shortDescription = "고종의 개인 서재이자 외국 사신 접견실로, 경복궁 안 유일한 중국식 건축 양식의 전각입니다.",
            shortDescMap = mapOf(
                "ko" to "고종의 개인 서재이자 외국 사신 접견실로, 경복궁 안 유일한 중국식 건축 양식의 전각입니다.",
                "en" to "King Gojong's private library and reception hall for foreign envoys, the only Chinese-style architecture within Gyeongbokgung.",
                "ja" to "高宗の個人書斎であり外国使臣の接見室で、景福宮内唯一の中国式建築様式の殿閣です。",
                "zh" to "高宗的私人书房兼外国使臣接见室，是景福宫内唯一的中国式建筑风格殿阁。"
            ),
            latitude = 37.5829,
            longitude = 126.9775,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "jibokjae_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "집옥재(集玉齋)의 ‘집옥’은 ‘옥처럼 귀한 보배를 모으다’라는 뜻으로, 집옥재를 중심으로 왼쪽에 팔우정(八隅亭), 오른쪽에 협길당(協吉堂, ‘협길’ : 함께 복을 누림)이 있다. 이곳은 91년(고종 28) 창덕궁 함녕전의 별당이었던 집옥재와 협길당 등을 건청궁 서쪽으로 옮겨 지은 것으로, 고종의 서재와 외국 사신을 접견하던 장소로 사용되었다. 집옥재는 경복궁의 다른 전각과 달리 청나라 양식으로 지어진 건물로, 밖에서 보면 단층으로 보이나 내부는 2층으로 되어있다. 팔우정은 팔각 누각으로 기둥 상부에 꽃과 넝쿨 를 화려하게 조각하였고, 유리로 창을 만들었다. 협길당은 고유한 조선식 건물로 온돌방을 두어 휴식 장소로 사용하였다. 세 건물은 복도를 통해 연결되어 있으며, 특히 집옥재는 복궁의 전각 중 유일하게 현판이 세로형으로 되어있다. 집옥재 전체 전경 26. 4. 19. 오후 4:39 ⊙ 집옥재 주련(柱聯) 내용 (위치 : 집옥재 외부 기둥) ① 灑潤含膏 雲氣多壽(쇄윤함고 운기다수) : 촉촉이 젖어 기름지니 운기(雲氣)는 장수하게 해주고, ② 稱物納照 鏡心彌光(칭물납조 경심미광) : 만나는 사물마다 비추어주니 거울은 더욱 밝도다. ③ 玉樹淩霄雲煙煥采(옥수능소 운연환채) : 아름다운 나무가 하늘에 솟으니 안개구름 찬란히 빛나고, ④ 寶花留硏 筆墨生香(보화류연 필묵생향) : 귀한 꽃이 벼룻가에 머무니 필묵(筆墨)에 향기가 나도다. ⑤ 西山朝來 致有爽氣(서산조래 치유상기) : 서산에 아침이 되니 상쾌한 기운이 이르고, ⑥ 太華夜碧 人聞淸鐘(태화야벽 인문청종) : 태화산(太華山)에 밤 깊으니 맑은 종소리를 듣도다. 26. 4. 19. 오후 4:39",
                    contentMap = mapOf("ko" to "집옥재(集玉齋)의 ‘집옥’은 ‘옥처럼 귀한 보배를 모으다’라는 뜻으로, 집옥재를 중심으로 왼쪽에 팔우정(八隅亭), 오른쪽에 협길당(協吉堂, ‘협길’ : 함께 복을 누림)이 있다. 이곳은 91년(고종 28) 창덕궁 함녕전의 별당이었던 집옥재와 협길당 등을 건청궁 서쪽으로 옮겨 지은 것으로, 고종의 서재와 외국 사신을 접견하던 장소로 사용되었다. 집옥재는 경복궁의 다른 전각과 달리 청나라 양식으로 지어진 건물로, 밖에서 보면 단층으로 보이나 내부는 2층으로 되어있다. 팔우정은 팔각 누각으로 기둥 상부에 꽃과 넝쿨 를 화려하게 조각하였고, 유리로 창을 만들었다. 협길당은 고유한 조선식 건물로 온돌방을 두어 휴식 장소로 사용하였다. 세 건물은 복도를 통해 연결되어 있으며, 특히 집옥재는 복궁의 전각 중 유일하게 현판이 세로형으로 되어있다. 집옥재 전체 전경 26. 4. 19. 오후 4:39 ⊙ 집옥재 주련(柱聯) 내용 (위치 : 집옥재 외부 기둥) ① 灑潤含膏 雲氣多壽(쇄윤함고 운기다수) : 촉촉이 젖어 기름지니 운기(雲氣)는 장수하게 해주고, ② 稱物納照 鏡心彌光(칭물납조 경심미광) : 만나는 사물마다 비추어주니 거울은 더욱 밝도다. ③ 玉樹淩霄雲煙煥采(옥수능소 운연환채) : 아름다운 나무가 하늘에 솟으니 안개구름 찬란히 빛나고, ④ 寶花留硏 筆墨生香(보화류연 필묵생향) : 귀한 꽃이 벼룻가에 머무니 필묵(筆墨)에 향기가 나도다. ⑤ 西山朝來 致有爽氣(서산조래 치유상기) : 서산에 아침이 되니 상쾌한 기운이 이르고, ⑥ 太華夜碧 人聞淸鐘(태화야벽 인문청종) : 태화산(太華山)에 밤 깊으니 맑은 종소리를 듣도다. 26. 4. 19. 오후 4:39")
                ),
                HeritageChunk(
                    chunkId = "jib_01",
                    section = "역사",
                    title = "고종의 지적 호기심의 공간",
                    titleMap = mapOf(
                        "ko" to "고종의 지적 호기심의 공간",
                        "en" to "A Space for King Gojong's Intellectual Curiosity",
                        "ja" to "高宗の知的好奇心の空間",
                        "zh" to "高宗求知欲的空间"
                    ),
                    keywords = listOf("서재", "고종", "외교", "서양서적"),
                    content = "집옥재는 1891년 고종이 건청궁 옆에 지은 개인 서재 겸 외교 접견실입니다. '집옥(集玉)'은 '옥(보배로운 지식)을 모으는 곳'이라는 뜻입니다. 고종은 이곳에 수천 권의 서적을 소장하고, 서양의 법률·과학·외교 서적을 열독했습니다. 또한 외국 공사와 비공식 접견을 나누는 장소로도 사용되어, 조선의 근대 외교가 이루어진 현장이기도 합니다.",
                    contentMap = mapOf(
                        "ko" to "집옥재는 1891년 고종이 건청궁 옆에 지은 개인 서재 겸 외교 접견실입니다. '집옥(集玉)'은 '옥(보배로운 지식)을 모으는 곳'이라는 뜻입니다. 고종은 이곳에 수천 권의 서적을 소장하고, 서양의 법률·과학·외교 서적을 열독했습니다. 또한 외국 공사와 비공식 접견을 나누는 장소로도 사용되어, 조선의 근대 외교가 이루어진 현장이기도 합니다.",
                        "en" to "Jibokjae was built in 1891 by King Gojong next to Geoncheongung as a private library and diplomatic reception room. 'Jibok' means 'a place to collect jade (precious knowledge).' Gojong housed thousands of books here, avidly reading Western works on law, science, and diplomacy. It also served as a venue for informal meetings with foreign ministers, making it a site where Joseon's modern diplomacy took place.",
                        "ja" to "集玉斎は1891年に高宗が乾清宮の隣に建てた個人書斎兼外交接見室です。「集玉」は「玉（貴重な知識）を集める場所」という意味です。高宗はここに数千冊の書籍を所蔵し、西洋の法律・科学・外交の書籍を熱心に読みました。また外国公使との非公式な接見の場としても使用され、朝鮮の近代外交が行われた現場でもあります。",
                        "zh" to "集玉斋是1891年高宗在乾清宫旁建造的私人书房兼外交接见室。“集玉”意为“收集玉石（珍贵知识）之所”。高宗在此收藏了数千册书籍，热切阅读西方的法律、科学、外交著作。同时也作为与外国公使非正式会面的场所，是朝鲜近代外交的历史现场。"
                    )
                ),
                HeritageChunk(
                    chunkId = "jib_02",
                    section = "건축",
                    title = "경복궁 유일의 중국식 건축",
                    titleMap = mapOf(
                        "ko" to "경복궁 유일의 중국식 건축",
                        "en" to "The Only Chinese-Style Building in Gyeongbokgung",
                        "ja" to "景福宮唯一の中国式建築",
                        "zh" to "景福宫唯一的中国式建筑"
                    ),
                    keywords = listOf("중국식", "벽돌", "유리", "복도"),
                    content = "집옥재는 경복궁 안에서 유일하게 중국식 건축 양식으로 지어진 건물입니다. 전통 한옥과 달리 벽돌을 사용하고, 유리창을 설치하며, 회랑(복도)으로 좌우 건물(팔우정·협길당)을 연결하는 독특한 구조입니다. 고종의 서구 문물에 대한 관심과 당시 청나라 건축의 영향이 결합된 결과물로, 경복궁의 다른 전각과 확연히 다른 이국적 분위기를 풍깁니다.",
                    contentMap = mapOf(
                        "ko" to "집옥재는 경복궁 안에서 유일하게 중국식 건축 양식으로 지어진 건물입니다. 전통 한옥과 달리 벽돌을 사용하고, 유리창을 설치하며, 회랑(복도)으로 좌우 건물(팔우정·협길당)을 연결하는 독특한 구조입니다. 고종의 서구 문물에 대한 관심과 당시 청나라 건축의 영향이 결합된 결과물로, 경복궁의 다른 전각과 확연히 다른 이국적 분위기를 풍깁니다.",
                        "en" to "Jibokjae is the only building within Gyeongbokgung constructed in Chinese architectural style. Unlike traditional hanok, it uses brick, features glass windows, and connects to flanking buildings (Parujeong and Hyeopgildang) through covered corridors — a unique structure. Combining Gojong's interest in Western civilization with Qing Dynasty architectural influence, it exudes an exotic atmosphere distinctly different from other palace halls.",
                        "ja" to "集玉斎は景福宮の中で唯一、中国式建築様式で建てられた建物です。伝統的な韓屋とは異なりレンガを使用し、ガラス窓を設置し、回廊（廊下）で左右の建物（八隅亭・協吉堂）を繋ぐ独特な構造です。高宗の西洋文物への関心と当時の清朝建築の影響が結合した結果物で、景福宮の他の殿閣とは明らかに異なる異国的な雰囲気を醸しています。",
                        "zh" to "集玉斋是景福宫内唯一以中国式建筑风格建造的建筑。与传统韩屋不同，使用砖砌，安装玻璃窗，通过回廊（走廊）连接左右建筑（八隅亭和协吉堂），结构独特。是高宗对西方文明的兴趣与当时清朝建筑影响相结合的产物，散发出与景福宫其他殿阁截然不同的异域氛围。"
                    )
                ),
                HeritageChunk(
                    chunkId = "jib_03",
                    section = "관람 포인트",
                    title = "2층 목조 구조의 내부",
                    titleMap = mapOf(
                        "ko" to "2층 목조 구조의 내부",
                        "en" to "Interior of the Two-Story Wooden Structure",
                        "ja" to "二階建て木造構造の内部",
                        "zh" to "两层木结构的内部"
                    ),
                    keywords = listOf("2층", "서가", "유리창", "내부"),
                    content = "집옥재의 내부는 1층과 2층으로 구성되어 있으며, 벽면에는 서적을 보관하기 위한 서가(책장)가 빼곡합니다. 유리 창문을 통해 자연광이 들어와 당시로서는 매우 현대적인 독서 환경을 제공했습니다. 외부에서는 목조 건물의 정교한 장식과 채색이 눈에 띄며, 용·봉황·화초 등의 문양이 기둥과 처마에 화려하게 그려져 있습니다.",
                    contentMap = mapOf(
                        "ko" to "집옥재의 내부는 1층과 2층으로 구성되어 있으며, 벽면에는 서적을 보관하기 위한 서가(책장)가 빼곡합니다. 유리 창문을 통해 자연광이 들어와 당시로서는 매우 현대적인 독서 환경을 제공했습니다. 외부에서는 목조 건물의 정교한 장식과 채색이 눈에 띄며, 용·봉황·화초 등의 문양이 기둥과 처마에 화려하게 그려져 있습니다.",
                        "en" to "The interior of Jibokjae spans two floors, with bookshelves lining the walls for storing the king's vast collection. Glass windows allowed natural light to enter, providing a remarkably modern reading environment for its time. On the exterior, the elaborate decorations and paintings on the wooden structure are notable, with dragons, phoenixes, and floral motifs vividly painted on pillars and eaves.",
                        "ja" to "集玉斎の内部は1階と2階で構成されており、壁面には書籍を保管するための書架（本棚）がびっしりと並んでいます。ガラスの窓から自然光が入り、当時としては非常に近代的な読書環境を提供しました。外観では木造建物の精巧な装飾と彩色が目を引き、龍・鳳凰・花草などの文様が柱と軒に華やかに描かれています。",
                        "zh" to "集玉斋内部分为一层和二层，墙面上密密麻麻地排列着用于存放书籍的书架。玻璃窗引入自然光，在当时提供了非常现代化的阅读环境。外观上，木结构建筑精巧的装饰和彩绘引人注目，龙、凤凰、花草等纹样华丽地绘制在柱子和屋檐上。"
                    )
                ),
                HeritageChunk(
                    chunkId = "jib_04",
                    section = "트리비아",
                    title = "팔우정과 협길당",
                    titleMap = mapOf(
                        "ko" to "팔우정과 협길당",
                        "en" to "Parujeong and Hyeopgildang",
                        "ja" to "八隅亭と協吉堂",
                        "zh" to "八隅亭与协吉堂"
                    ),
                    keywords = listOf("팔우정", "협길당", "팔각", "회랑"),
                    content = "집옥재의 왼쪽에는 팔각형의 팔우정(八隅亭)이, 오른쪽에는 협길당(協吉堂)이 연결되어 있습니다. 팔우정은 '여덟 모서리의 정자'라는 뜻으로, 중국식 2층 팔각정 건물이며 특별한 행사나 차를 즐기는 공간으로 사용되었습니다. 협길당은 보조 업무 공간으로 활용되었습니다. 이 세 건물이 회랑으로 연결된 모습은 경복궁에서 가장 독특한 건축 경관 중 하나입니다.",
                    contentMap = mapOf(
                        "ko" to "집옥재의 왼쪽에는 팔각형의 팔우정(八隅亭)이, 오른쪽에는 협길당(協吉堂)이 연결되어 있습니다. 팔우정은 '여덟 모서리의 정자'라는 뜻으로, 중국식 2층 팔각정 건물이며 특별한 행사나 차를 즐기는 공간으로 사용되었습니다. 협길당은 보조 업무 공간으로 활용되었습니다. 이 세 건물이 회랑으로 연결된 모습은 경복궁에서 가장 독특한 건축 경관 중 하나입니다.",
                        "en" to "To the left of Jibokjae stands the octagonal Parujeong (Pavilion of Eight Corners), and to the right is Hyeopgildang. Parujeong is a Chinese-style two-story octagonal pavilion used for special events and tea enjoyment. Hyeopgildang served as an auxiliary workspace. The three buildings connected by covered corridors form one of the most distinctive architectural landscapes in Gyeongbokgung.",
                        "ja" to "集玉斎の左側には八角形の八隅亭が、右側には協吉堂が繋がっています。八隅亭は「八つの角の亭」という意味で、中国式の2階建て八角亭の建物で、特別な行事や茶を楽しむ空間として使用されました。協吉堂は補助業務空間として活用されました。この3つの建物が回廊で繋がった姿は、景福宮で最も独特な建築景観の一つです。",
                        "zh" to "集玉斋左侧连接着八角形的八隅亭，右侧连接着协吉堂。八隅亭意为“八角亭”，是中国式两层八角亭建筑，用于特殊活动和品茶。协吉堂用作辅助办公空间。这三座建筑通过回廊相连的景观是景福宫最独特的建筑景观之一。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "jib_parujeong",
                    displayName = mapOf("ko" to "팔우정 (팔각정)", "en" to "Parujeong (Octagonal Pavilion)", "ja" to "八隅亭（八角亭）", "zh" to "八隅亭（八角亭）"),
                    visualHints = listOf("octagonal two-story Chinese-style pavilion connected to library", "eight-sided wooden tower building with ornate decoration", "unique octagonal structure next to palace library"),
                    linkedChunkId = "jib_04",
                    locationHint = mapOf("ko" to "집옥재 왼쪽 2층 팔각형 팔우정.", "en" to "Two-story octagonal Parujeong connected to the left of Jibokjae.", "ja" to "集玉斎の左の2階建て八角形八隅亭。", "zh" to "集玉斋左侧的两层八角形八隅亭。")
                ),
                SubElement(
                    id = "jib_signboard",
                    displayName = mapOf("ko" to "집옥재 현판", "en" to "Jibokjae Signboard", "ja" to "集玉斎の扁額", "zh" to "集玉斋匾额"),
                    visualHints = listOf("Chinese-style palace name plaque with ornate decoration", "nameplate on brick-and-wood Chinese-style palace building", "wooden sign on unique Chinese-architecture palace hall"),
                    linkedChunkId = "jib_01",
                    locationHint = mapOf("ko" to "중국식 집옥재 정면 중앙 현판.", "en" to "Chinese-style Jibokjae's center front signboard.", "ja" to "中国式集玉斎正面中央の扁額。", "zh" to "中国式集玉斋正面中央的匾额。")
                ),
                SubElement(
                    id = "jib_glass_window",
                    displayName = mapOf("ko" to "집옥재 유리창", "en" to "Jibokjae Glass Windows", "ja" to "集玉斎のガラス窓", "zh" to "集玉斋玻璃窗"),
                    visualHints = listOf("glass windows on traditional wooden palace building", "modern glass panes in Chinese-style palace structure", "transparent windows unusual for traditional palace architecture"),
                    linkedChunkId = "jib_03",
                    locationHint = mapOf("ko" to "집옥재 벽의 유리창 (한옥에 드문 요소).", "en" to "Glass windows on Jibokjae — rare for traditional hanok.", "ja" to "集玉斎のガラス窓（韓屋では珍しい）。", "zh" to "集玉斋的玻璃窗（传统韩屋中罕见）。")
                )
            ),
            coverImageAsset = "heritage/jibokjae_cover.jpg",
            galleryImageAssets = listOf("heritage/jibokjae_1.jpg", "heritage/jibokjae_2.jpg", "heritage/jibokjae_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 동궁 (Donggung) — 세자의 거처
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "donggung",
            title = "동궁 (Donggung)",
            titleMap = mapOf(
                "ko" to "동궁 (Donggung)",
                "en" to "Donggung (Crown Prince's Palace)",
                "ja" to "東宮 (トングン)",
                "zh" to "东宫"
            ),
            aliases = listOf("동궁", "Donggung", "Donggung Palace", "東宮", "세자궁"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "east_court",
            shortDescription = "조선 왕세자의 거처이자 학습 공간으로, 미래의 왕을 교육하던 경복궁 동쪽 영역입니다.",
            shortDescMap = mapOf(
                "ko" to "조선 왕세자의 거처이자 학습 공간으로, 미래의 왕을 교육하던 경복궁 동쪽 영역입니다.",
                "en" to "The residence and study of the Joseon crown prince, the eastern area of Gyeongbokgung where the future king was educated.",
                "ja" to "朝鮮の王世子の住まいであり学習空間で、未来の王を教育した景福宮の東側エリアです。",
                "zh" to "朝鲜王世子的居所兼学习空间，是教育未来国王的景福宫东侧区域。"
            ),
            latitude = 37.5803,
            longitude = 126.9782,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "donggung_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "동궁(東宮) 영역은 왕세자와 왕세자빈의 교육공간이자 생활공간으로 궁궐의 동쪽에 있어 동궁, 또는 세자궁이라고 부른다. 현재 동궁 영역에는 왕세자와 왕세자빈의 생활공간인 자선당(資善堂)과 왕세자의 교육과 정무를 보던 비현각(丕顯閣), 그리고 동궁의 정당(正堂)인 계조당(繼照堂)이 있다. 자 의 ‘자선’은 ‘착한 성품을 기른다’, 비현각의 ‘비현’은 ‘덕을 크게 밝히다’, 계조당의 ‘계조’는 ‘계승하여 비춘다’라는 뜻으로 모두 왕세자와 관련된 뜻으로 건물 이름을 지었다. 동궁은 경복궁 창건 당시에는 없었으나 1427년(세종 9)부터 동궁을 지었고, 몇 차례의 소실과 재건을 거쳐 임진왜란 때 소실되었다가 1867년(고종 4)에 다시 지었다. 그러나 일 점기 때 동궁 일대가 모두 철거되었으며, 자선당과 비현각은 1999년에, 계조당은 2023년에 복원하였다. 동궁 영역 전경 26. 4. 19. 오후 4:37 26. 4. 19. 오후 4:37",
                    contentMap = mapOf("ko" to "동궁(東宮) 영역은 왕세자와 왕세자빈의 교육공간이자 생활공간으로 궁궐의 동쪽에 있어 동궁, 또는 세자궁이라고 부른다. 현재 동궁 영역에는 왕세자와 왕세자빈의 생활공간인 자선당(資善堂)과 왕세자의 교육과 정무를 보던 비현각(丕顯閣), 그리고 동궁의 정당(正堂)인 계조당(繼照堂)이 있다. 자 의 ‘자선’은 ‘착한 성품을 기른다’, 비현각의 ‘비현’은 ‘덕을 크게 밝히다’, 계조당의 ‘계조’는 ‘계승하여 비춘다’라는 뜻으로 모두 왕세자와 관련된 뜻으로 건물 이름을 지었다. 동궁은 경복궁 창건 당시에는 없었으나 1427년(세종 9)부터 동궁을 지었고, 몇 차례의 소실과 재건을 거쳐 임진왜란 때 소실되었다가 1867년(고종 4)에 다시 지었다. 그러나 일 점기 때 동궁 일대가 모두 철거되었으며, 자선당과 비현각은 1999년에, 계조당은 2023년에 복원하였다. 동궁 영역 전경 26. 4. 19. 오후 4:37 26. 4. 19. 오후 4:37")
                ),
                HeritageChunk(
                    chunkId = "dong_01",
                    section = "역사",
                    title = "미래의 왕을 키우는 공간",
                    titleMap = mapOf(
                        "ko" to "미래의 왕을 키우는 공간",
                        "en" to "A Space for Nurturing the Future King",
                        "ja" to "未来の王を育てる空間",
                        "zh" to "培养未来国王的空间"
                    ),
                    keywords = listOf("세자", "교육", "동쪽", "서연"),
                    content = "동궁은 '동쪽의 궁궐'이라는 뜻으로, 전통적으로 세자(왕세자)의 거처는 궁궐의 동쪽에 배치되었습니다. 이는 동쪽이 해가 뜨는 방향으로, 떠오르는 태양처럼 성장하는 미래의 왕을 상징하기 때문입니다. 경복궁 동궁 영역에서 세자는 학문과 예절을 배우는 '서연(書筵)'에 참석하고, 왕위를 물려받기 위한 제왕학을 익혔습니다.",
                    contentMap = mapOf(
                        "ko" to "동궁은 '동쪽의 궁궐'이라는 뜻으로, 전통적으로 세자(왕세자)의 거처는 궁궐의 동쪽에 배치되었습니다. 이는 동쪽이 해가 뜨는 방향으로, 떠오르는 태양처럼 성장하는 미래의 왕을 상징하기 때문입니다. 경복궁 동궁 영역에서 세자는 학문과 예절을 배우는 '서연(書筵)'에 참석하고, 왕위를 물려받기 위한 제왕학을 익혔습니다.",
                        "en" to "Donggung means 'Eastern Palace,' as the crown prince's residence was traditionally placed on the eastern side of the palace. This is because east is the direction of sunrise, symbolizing the future king growing like the rising sun. In Gyeongbokgung's Donggung area, the crown prince attended 'Seoyeon' (royal lectures on scholarship and etiquette) and studied the art of kingship to prepare for succession.",
                        "ja" to "東宮は「東の宮殿」という意味で、伝統的に世子（王世子）の住まいは宮殿の東側に配置されていました。これは東が日の出の方角であり、昇る太陽のように成長する未来の王を象徴するためです。景福宮の東宮エリアで世子は学問と礼節を学ぶ「書筵」に参加し、王位を継承するための帝王学を身につけました。",
                        "zh" to "东宫意为“东边的宫殿”，传统上世子（王世子）的居所设在宫殿东侧。因为东方是日出的方向，象征着如朝阳般成长的未来国王。在景福宫东宫区域，世子参加学习学问和礼仪的“书筵”，修习继承王位所需的帝王之学。"
                    )
                ),
                HeritageChunk(
                    chunkId = "dong_02",
                    section = "건축",
                    title = "비현각과 자선당의 구조",
                    titleMap = mapOf(
                        "ko" to "비현각과 자선당의 구조",
                        "en" to "The Structure of Bihyeongak and Jaseondang",
                        "ja" to "丕顕閣と資善堂の構造",
                        "zh" to "丕显阁与资善堂的结构"
                    ),
                    keywords = listOf("비현각", "자선당", "학습", "침전"),
                    content = "동궁 영역의 핵심 건물은 비현각(丕顯閣)과 자선당(資善堂)입니다. 비현각은 세자가 학문을 배우고 서연을 여는 공부방 역할의 전각이며, 자선당은 세자의 일상 거처(침전)였습니다. 두 건물은 행각으로 연결되어 학습과 생활이 하나의 영역에서 이루어질 수 있도록 설계되었습니다. 현재는 복원 과정에 있으며, 일부 기단과 초석이 남아 옛 규모를 짐작하게 합니다.",
                    contentMap = mapOf(
                        "ko" to "동궁 영역의 핵심 건물은 비현각(丕顯閣)과 자선당(資善堂)입니다. 비현각은 세자가 학문을 배우고 서연을 여는 공부방 역할의 전각이며, 자선당은 세자의 일상 거처(침전)였습니다. 두 건물은 행각으로 연결되어 학습과 생활이 하나의 영역에서 이루어질 수 있도록 설계되었습니다. 현재는 복원 과정에 있으며, 일부 기단과 초석이 남아 옛 규모를 짐작하게 합니다.",
                        "en" to "The key buildings in the Donggung area are Bihyeongak and Jaseondang. Bihyeongak served as the study hall where the crown prince learned and held Seoyeon (royal lectures), while Jaseondang was the prince's daily residence (bedchamber). The two buildings were connected by corridors, designed so study and daily life could take place within a single domain. Currently under restoration, some foundation stones and cornerstones remain, hinting at the former scale.",
                        "ja" to "東宮エリアの核心的な建物は丕顕閣と資善堂です。丕顕閣は世子が学問を学び書筵を開く勉強部屋の役割を果たす殿閣で、資善堂は世子の日常の住まい（寝殿）でした。2つの建物は行閣で繋がれ、学習と生活が一つのエリアで行えるよう設計されていました。現在は復元過程にあり、一部の基壇と礎石が残って昔の規模を推測させます。",
                        "zh" to "东宫区域的核心建筑是丕显阁和资善堂。丕显阁是世子学习学问、举行书筵的学习殿阁，资善堂是世子的日常居所（寝殿）。两座建筑通过行廊相连，设计使学习和生活能在同一区域内进行。目前正在复原过程中，部分基台和柱础石仍在，可推测昔日的规模。"
                    )
                ),
                HeritageChunk(
                    chunkId = "dong_03",
                    section = "관람 포인트",
                    title = "동궁 터에서 바라보는 경복궁",
                    titleMap = mapOf(
                        "ko" to "동궁 터에서 바라보는 경복궁",
                        "en" to "Viewing Gyeongbokgung from the Donggung Site",
                        "ja" to "東宮の跡地から眺める景福宮",
                        "zh" to "从东宫遗址眺望景福宫"
                    ),
                    keywords = listOf("동궁터", "전경", "북악산", "조망"),
                    content = "동궁 터는 경복궁의 동쪽 높은 지대에 위치해 있어, 이곳에서 서쪽을 바라보면 근정전과 경회루의 지붕선이 한눈에 들어옵니다. 뒤편으로는 북악산의 능선이 펼쳐지며, 경복궁의 풍수적 배치를 가장 잘 이해할 수 있는 조망 포인트 중 하나입니다. 복원이 완료되면 세자의 시각으로 궁궐을 바라보는 특별한 체험이 가능해질 것입니다.",
                    contentMap = mapOf(
                        "ko" to "동궁 터는 경복궁의 동쪽 높은 지대에 위치해 있어, 이곳에서 서쪽을 바라보면 근정전과 경회루의 지붕선이 한눈에 들어옵니다. 뒤편으로는 북악산의 능선이 펼쳐지며, 경복궁의 풍수적 배치를 가장 잘 이해할 수 있는 조망 포인트 중 하나입니다. 복원이 완료되면 세자의 시각으로 궁궐을 바라보는 특별한 체험이 가능해질 것입니다.",
                        "en" to "The Donggung site is located on elevated ground on the eastern side of Gyeongbokgung, offering a panoramic view of Geunjeongjeon and Gyeonghoeru rooflines to the west. Bugaksan Mountain's ridge unfolds behind, making it one of the best vantage points for understanding the palace's feng shui layout. Once restoration is complete, visitors will experience viewing the palace from the crown prince's perspective.",
                        "ja" to "東宮の跡地は景福宮の東側の高台に位置しており、ここから西を見ると勤政殿と慶会楼の屋根のラインが一望できます。背後には北岳山の稜線が広がり、景福宮の風水的な配置を最もよく理解できる眺望ポイントの一つです。復元が完了すれば、世子の視点で宮殿を眺める特別な体験が可能になるでしょう。",
                        "zh" to "东宫遗址位于景福宫东侧的高地上，从这里向西望去，勤政殿和庆会楼的屋顶线条尽收眼底。后方是北岳山的山脊线，是理解景福宫风水布局的最佳观景点之一。复原完成后，游客将能以世子的视角眺望宫殿，获得特殊的体验。"
                    )
                ),
                HeritageChunk(
                    chunkId = "dong_04",
                    section = "트리비아",
                    title = "세자의 하루 일과",
                    titleMap = mapOf(
                        "ko" to "세자의 하루 일과",
                        "en" to "A Day in the Crown Prince's Life",
                        "ja" to "世子の一日の日課",
                        "zh" to "世子的一日行程"
                    ),
                    keywords = listOf("서연", "일과", "교육", "제왕학"),
                    content = "조선의 세자는 새벽 5시에 일어나 아침 서연(朝講)을 시작으로, 오전 강의(書筵), 오후 무예 훈련, 저녁 자습(夕講)까지 빡빡한 일과를 보냈습니다. 서연에서는 유교 경전(사서오경)을 중심으로 학습하되, 역사·법률·외교·군사 등 통치에 필요한 모든 분야를 다루었습니다. 동궁은 이런 세자 교육의 모든 과정이 이루어지던 '미래의 왕을 위한 학교'였습니다.",
                    contentMap = mapOf(
                        "ko" to "조선의 세자는 새벽 5시에 일어나 아침 서연(朝講)을 시작으로, 오전 강의(書筵), 오후 무예 훈련, 저녁 자습(夕講)까지 빡빡한 일과를 보냈습니다. 서연에서는 유교 경전(사서오경)을 중심으로 학습하되, 역사·법률·외교·군사 등 통치에 필요한 모든 분야를 다루었습니다. 동궁은 이런 세자 교육의 모든 과정이 이루어지던 '미래의 왕을 위한 학교'였습니다.",
                        "en" to "The Joseon crown prince woke at 5 AM and followed a rigorous daily schedule: morning royal lectures (Jogang), forenoon study sessions (Seoyeon), afternoon martial arts training, and evening self-study (Seokgang). Studies centered on Confucian classics (Four Books and Five Classics) but covered all governance-related fields including history, law, diplomacy, and military affairs. Donggung was the 'school for the future king' where this entire education took place.",
                        "ja" to "朝鮮の世子は朝5時に起き、朝の書筵（朝講）から始まり、午前の講義（書筵）、午後の武芸訓練、夕方の自習（夕講）まで、厳しい一日を過ごしました。書筵では儒教の経典（四書五経）を中心に学びつつ、歴史・法律・外交・軍事など統治に必要なすべての分野を網羅しました。東宮はこのような世子教育のすべての過程が行われた「未来の王のための学校」でした。",
                        "zh" to "朝鲜世子清晨五点起床，从早朝书筵（朝讲）开始，上午课程（书筵）、下午武艺训练、晚间自习（夕讲），日程安排紧凑。书筵以儒学经典（四书五经）为核心，同时涵盖历史、法律、外交、军事等治国所需的各个领域。东宫就是进行世子全部教育过程的“为未来国王开设的学校”。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "dong_foundation",
                    displayName = mapOf("ko" to "동궁 기단 유구", "en" to "Donggung Foundation Remains", "ja" to "東宮の基壇遺構", "zh" to "东宫基台遗址"),
                    visualHints = listOf("stone foundation remains of ancient palace building", "archaeological foundation stones in palace grounds", "rectangular stone base remnants in east palace area"),
                    linkedChunkId = "dong_02",
                    locationHint = mapOf("ko" to "동궁 영역의 비현각·자선당 기단 유구.", "en" to "Foundation remains of Bihyeongak and Jaseondang in Donggung.", "ja" to "東宮エリアの丕顕閣・資善堂の基壇遺構。", "zh" to "东宫区域的丕显阁、资善堂基台遗迹。")
                ),
                SubElement(
                    id = "dong_signboard",
                    displayName = mapOf("ko" to "동궁 안내판", "en" to "Donggung Information Sign", "ja" to "東宮の案内板", "zh" to "东宫指示牌"),
                    visualHints = listOf("informational sign describing crown prince palace area", "heritage explanation board with historical photos", "cultural heritage information panel in palace grounds"),
                    linkedChunkId = "dong_01",
                    locationHint = mapOf("ko" to "동궁 영역의 안내판에서 세자 교육 설명 읽기.", "en" to "Read the Donggung signpost about crown prince education.", "ja" to "東宮エリアの案内板で世子教育の説明を読む。", "zh" to "阅读东宫区域说明牌上的世子教育内容。")
                )
            ),
            coverImageAsset = "heritage/donggung_cover.jpg",
            galleryImageAssets = listOf("heritage/donggung_1.jpg", "heritage/donggung_2.jpg", "heritage/donggung_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 태원전 (Taeweonjeon) — 선왕 영정 봉안
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "taeweonjeon",
            title = "태원전 (Taeweonjeon)",
            titleMap = mapOf(
                "ko" to "태원전 (Taeweonjeon)",
                "en" to "Taeweonjeon Hall",
                "ja" to "泰元殿 (テウォンジョン)",
                "zh" to "泰元殿"
            ),
            aliases = listOf("태원전", "Taeweonjeon", "Taeweonjeon Hall", "泰元殿"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "west_court",
            shortDescription = "선왕의 어진(초상화)을 모시고 제사를 올리던 전각으로, 왕실의 조상 숭배 공간입니다.",
            shortDescMap = mapOf(
                "ko" to "선왕의 어진(초상화)을 모시고 제사를 올리던 전각으로, 왕실의 조상 숭배 공간입니다.",
                "en" to "A hall for enshrining the portraits of former kings and performing ancestral rites, a space for royal ancestor worship.",
                "ja" to "先王の御真（肖像画）を祀り祭祀を行った殿閣で、王室の祖先崇拝の空間です。",
                "zh" to "供奉先王御真（肖像画）并举行祭祀的殿阁，是王室祖先崇拜的空间。"
            ),
            latitude = 37.5815,
            longitude = 126.9755,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "taeweonjeon_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "태원전(泰元殿)의 ‘태원’은 ‘하늘’을 뜻하는데, 이곳은 1868년(고종 5)에 지어진 것으로 추정된다. 이곳에 태조의 어진을 모셨고, 이후에는 신정황후 조씨와 명성황후 민씨가 세 떠난 후 빈전(殯殿, 왕과 왕비가 세상을 떠난 후 발인하기 전까지 재궁(관)을 모셔둔 건물)으로 사용되었다. 태원전 주변에는 부속 건물인 문경전(文慶殿)과 공묵재(恭默齋, ‘공묵’ : 공손히 침묵함), 영사재(永思齋, ‘영사’ : 오래 생각하여 가슴에 새김) 등 의례용 건물이 있다. 문경전은 (魂殿, 왕과 왕비의 신주를 종묘로 모시기 전까지 임시로 신주를 모시는 건물)으로 사용되기도 하였다. 태원전 전체 전경 26. 4. 19. 오후 4:39 태원전은 일제강점기 때 철거되었다가, 1960년대 청와대와 인접하여 육군 수도경비사령부와 청와대 경호부대가 있었으나, 1993년 모두 이전한 후 2006년에 현재 모습으로 하였다. 26. 4. 19. 오후 4:39",
                    contentMap = mapOf("ko" to "태원전(泰元殿)의 ‘태원’은 ‘하늘’을 뜻하는데, 이곳은 1868년(고종 5)에 지어진 것으로 추정된다. 이곳에 태조의 어진을 모셨고, 이후에는 신정황후 조씨와 명성황후 민씨가 세 떠난 후 빈전(殯殿, 왕과 왕비가 세상을 떠난 후 발인하기 전까지 재궁(관)을 모셔둔 건물)으로 사용되었다. 태원전 주변에는 부속 건물인 문경전(文慶殿)과 공묵재(恭默齋, ‘공묵’ : 공손히 침묵함), 영사재(永思齋, ‘영사’ : 오래 생각하여 가슴에 새김) 등 의례용 건물이 있다. 문경전은 (魂殿, 왕과 왕비의 신주를 종묘로 모시기 전까지 임시로 신주를 모시는 건물)으로 사용되기도 하였다. 태원전 전체 전경 26. 4. 19. 오후 4:39 태원전은 일제강점기 때 철거되었다가, 1960년대 청와대와 인접하여 육군 수도경비사령부와 청와대 경호부대가 있었으나, 1993년 모두 이전한 후 2006년에 현재 모습으로 하였다. 26. 4. 19. 오후 4:39")
                ),
                HeritageChunk(
                    chunkId = "tae_01",
                    section = "역사",
                    title = "선왕을 기리는 신성한 공간",
                    titleMap = mapOf(
                        "ko" to "선왕을 기리는 신성한 공간",
                        "en" to "A Sacred Space Honoring Former Kings",
                        "ja" to "先王を偲ぶ神聖な空間",
                        "zh" to "缅怀先王的神圣空间"
                    ),
                    keywords = listOf("어진", "제사", "혼전", "선왕"),
                    content = "태원전은 경복궁 서쪽에 위치한 전각으로, 돌아가신 선왕의 어진(초상화)을 모시는 혼전(魂殿)의 역할을 했습니다. 왕이 승하하면 3년간 이곳에서 제사를 올린 후 종묘(宗廟)로 옮겼습니다. 고종 때 경복궁 중건 시 함께 지어졌으며, 조선 왕실의 유교적 조상 숭배 전통을 보여주는 중요한 공간입니다.",
                    contentMap = mapOf(
                        "ko" to "태원전은 경복궁 서쪽에 위치한 전각으로, 돌아가신 선왕의 어진(초상화)을 모시는 혼전(魂殿)의 역할을 했습니다. 왕이 승하하면 3년간 이곳에서 제사를 올린 후 종묘(宗廟)로 옮겼습니다. 고종 때 경복궁 중건 시 함께 지어졌으며, 조선 왕실의 유교적 조상 숭배 전통을 보여주는 중요한 공간입니다.",
                        "en" to "Taeweonjeon is a hall on the western side of Gyeongbokgung that served as a honjeon (spirit hall) enshrining the deceased king's portrait. After a king passed away, ancestral rites were performed here for three years before the spirit tablet was moved to Jongmyo Shrine. Built during King Gojong's reconstruction, it represents the Confucian ancestor worship tradition of the Joseon royal family.",
                        "ja" to "泰元殿は景福宮の西側に位置する殿閣で、亡くなった先王の御真（肖像画）を祀る魂殿の役割を果たしました。王が崩御すると3年間ここで祭祀を行った後、宗廟に移しました。高宗の景福宮再建時に建てられ、朝鮮王室の儒教的な祖先崇拝の伝統を示す重要な空間です。",
                        "zh" to "泰元殿位于景福宫西侧，用作供奉已故先王御真（肖像画）的魂殿。国王驾崩后在此举行三年祭祀，之后移至宗庙。高宗重建景福宫时一同修建，是展示朝鲜王室儒家祖先崇拜传统的重要空间。"
                    )
                ),
                HeritageChunk(
                    chunkId = "tae_02",
                    section = "건축",
                    title = "엄숙한 제례 공간의 설계",
                    titleMap = mapOf(
                        "ko" to "엄숙한 제례 공간의 설계",
                        "en" to "Design of a Solemn Ceremonial Space",
                        "ja" to "厳粛な祭礼空間の設計",
                        "zh" to "肃穆祭礼空间的设计"
                    ),
                    keywords = listOf("혼전", "제례", "행각", "서쪽"),
                    content = "태원전은 제례 공간답게 장식이 절제되고 엄숙한 분위기를 풍깁니다. 주변을 행각(복도)이 감싸고 있어 외부와 차단된 독립적인 공간을 형성하며, 내부에는 어진을 모시기 위한 감실이 설치되었습니다. 경복궁의 서쪽 영역은 제의적 성격이 강한 구역으로, 태원전은 그 중심에 위치합니다.",
                    contentMap = mapOf(
                        "ko" to "태원전은 제례 공간답게 장식이 절제되고 엄숙한 분위기를 풍깁니다. 주변을 행각(복도)이 감싸고 있어 외부와 차단된 독립적인 공간을 형성하며, 내부에는 어진을 모시기 위한 감실이 설치되었습니다. 경복궁의 서쪽 영역은 제의적 성격이 강한 구역으로, 태원전은 그 중심에 위치합니다.",
                        "en" to "Taeweonjeon features restrained decoration and a solemn atmosphere befitting a ceremonial space. Corridors (haenggak) surround it, forming an independent space shielded from the outside, with a niche (gamsil) inside for enshrining portraits. The western area of Gyeongbokgung has a strong ritual character, and Taeweonjeon stands at its center.",
                        "ja" to "泰元殿は祭礼空間らしく装飾が抑えられ、厳粛な雰囲気を醸しています。周囲を行閣（回廊）が囲んで外部と遮断された独立した空間を形成し、内部には御真を祀るための龕室が設置されました。景福宮の西側エリアは祭祀的な性格が強い区域で、泰元殿はその中心に位置しています。",
                        "zh" to "泰元殿作为祭礼空间，装饰克制，氛围肃穆。周围由行廊（走廊）环绕，形成与外界隔绝的独立空间，内部设有供奉御真的龛室。景福宫的西侧区域具有强烈的祭祀性质，泰元殿位于其中心。"
                    )
                ),
                HeritageChunk(
                    chunkId = "tae_03",
                    section = "트리비아",
                    title = "어진의 보관과 이동",
                    titleMap = mapOf(
                        "ko" to "어진의 보관과 이동",
                        "en" to "Storage and Transfer of Royal Portraits",
                        "ja" to "御真の保管と移動",
                        "zh" to "御真的保管与迁移"
                    ),
                    keywords = listOf("어진", "보관", "종묘", "이동"),
                    content = "조선 시대 왕의 초상화인 어진(御真)은 왕의 생전에 그려져 여러 본이 제작되었습니다. 왕이 승하하면 태원전 같은 혼전에 봉안했다가, 3년상이 끝나면 종묘의 정전 또는 영녕전으로 신주(위패)를 이동했습니다. 어진은 전란 시에도 가장 먼저 피난시켰을 정도로 소중히 여겨졌으며, 현재 남아 있는 조선 시대 어진은 극소수에 불과합니다.",
                    contentMap = mapOf(
                        "ko" to "조선 시대 왕의 초상화인 어진(御真)은 왕의 생전에 그려져 여러 본이 제작되었습니다. 왕이 승하하면 태원전 같은 혼전에 봉안했다가, 3년상이 끝나면 종묘의 정전 또는 영녕전으로 신주(위패)를 이동했습니다. 어진은 전란 시에도 가장 먼저 피난시켰을 정도로 소중히 여겨졌으며, 현재 남아 있는 조선 시대 어진은 극소수에 불과합니다.",
                        "en" to "Royal portraits (Eojin) were painted during the king's lifetime, with multiple copies made. After the king's death, they were enshrined in spirit halls like Taeweonjeon, and after the three-year mourning period, the spirit tablets were moved to Jongmyo Shrine. Royal portraits were so precious that they were among the first items evacuated during wartime, and only a very few Joseon-era royal portraits survive today.",
                        "ja" to "朝鮮時代の王の肖像画である御真は、王の生前に描かれ、複数の本が制作されました。王が崩御すると泰元殿のような魂殿に奉安し、3年の喪が明けると宗廟の正殿または永寧殿に神主（位牌）を移しました。御真は戦乱の際にも最初に避難させるほど大切にされ、現在残っている朝鮮時代の御真はごくわずかです。",
                        "zh" to "朝鲜时代国王的肖像画“御真”在国王生前绘制，制作多份。国王驾崩后供奉在泰元殿等魂殿中，三年丧期结束后将神主（牌位）移至宗庙的正殿或永宁殿。御真在战乱中也是最先转移保护的物品，现存的朝鲜时代御真极为稀少。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "tae_signboard",
                    displayName = mapOf("ko" to "태원전 현판", "en" to "Taeweonjeon Signboard", "ja" to "泰元殿の扁額", "zh" to "泰元殿匾额"),
                    visualHints = listOf("palace name plaque on solemn ceremonial hall", "wooden sign on restrained decoration palace building", "nameplate on western palace hall"),
                    linkedChunkId = "tae_01",
                    locationHint = mapOf("ko" to "경복궁 서쪽 태원전 현판 — 제례 공간.", "en" to "Taeweonjeon signboard in west Gyeongbokgung — ceremonial space.", "ja" to "景福宮西側の泰元殿の扁額 — 祭礼空間。", "zh" to "景福宫西侧泰元殿的匾额——祭礼空间。")
                )
            ),
            coverImageAsset = "heritage/taeweonjeon_cover.jpg",
            galleryImageAssets = listOf("heritage/taeweonjeon_1.jpg", "heritage/taeweonjeon_2.jpg", "heritage/taeweonjeon_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 소주방 (Sojubang) — 궁중 주방
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "sojubang",
            title = "소주방 (Sojubang)",
            titleMap = mapOf(
                "ko" to "소주방 (Sojubang)",
                "en" to "Sojubang (Royal Kitchen)",
                "ja" to "焼厨房 (ソジュバン)",
                "zh" to "烧厨房"
            ),
            aliases = listOf("소주방", "Sojubang", "Royal Kitchen", "焼厨房", "烧厨房"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "royal_residence",
            shortDescription = "왕과 왕비의 식사를 준비하던 궁중 주방으로, 궁중 음식 문화를 체험할 수 있는 공간입니다.",
            shortDescMap = mapOf(
                "ko" to "왕과 왕비의 식사를 준비하던 궁중 주방으로, 궁중 음식 문화를 체험할 수 있는 공간입니다.",
                "en" to "The royal kitchen where meals for the king and queen were prepared, a space for experiencing court cuisine culture.",
                "ja" to "王と王妃の食事を準備した宮中の厨房で、宮中の食文化を体験できる空間です。",
                "zh" to "为国王和王妃准备膳食的宫廷厨房，是体验宫廷饮食文化的空间。"
            ),
            latitude = 37.5810,
            longitude = 126.9775,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "so_01",
                    section = "역사",
                    title = "왕의 수라를 준비하던 곳",
                    titleMap = mapOf(
                        "ko" to "왕의 수라를 준비하던 곳",
                        "en" to "Where the King's Royal Meals Were Prepared",
                        "ja" to "王の水刺を準備した場所",
                        "zh" to "准备国王膳食的地方"
                    ),
                    keywords = listOf("수라", "궁중음식", "주방", "상궁"),
                    content = "소주방은 왕과 왕비의 일상 식사인 '수라(水刺)'를 준비하던 궁중 주방입니다. '소주방(燒廚房)'이란 '불을 때어 요리하는 주방'이라는 뜻입니다. 강녕전과 교태전 사이에 위치하여 왕과 왕비에게 신속하게 음식을 올릴 수 있었습니다. 최고의 솜씨를 가진 숙수(조리사)와 상궁들이 하루 다섯 번의 수라상을 차렸습니다.",
                    contentMap = mapOf(
                        "ko" to "소주방은 왕과 왕비의 일상 식사인 '수라(水刺)'를 준비하던 궁중 주방입니다. '소주방(燒廚房)'이란 '불을 때어 요리하는 주방'이라는 뜻입니다. 강녕전과 교태전 사이에 위치하여 왕과 왕비에게 신속하게 음식을 올릴 수 있었습니다. 최고의 솜씨를 가진 숙수(조리사)와 상궁들이 하루 다섯 번의 수라상을 차렸습니다.",
                        "en" to "Sojubang was the royal kitchen where 'Sura' (the king and queen's daily meals) were prepared. 'Sojubang' literally means 'kitchen where fire is used for cooking.' Located between Gangnyeongjeon and Gyotaejeon, meals could be served promptly to the royal couple. The most skilled cooks (suksu) and court ladies prepared five royal meal services daily.",
                        "ja" to "焼厨房は王と王妃の日常の食事「水刺（スラ）」を準備した宮中の厨房です。「焼厨房」とは「火を焚いて料理する厨房」という意味です。康寧殿と交泰殿の間に位置し、王と王妃に迅速に食事を提供できました。最高の腕前を持つ熟手（料理人）と尚宮たちが1日5回の水刺膳を準備しました。",
                        "zh" to "烧厨房是准备国王和王妃日常膳食“水刺”的宫廷厨房。“烧厨房”意为“生火做饭的厨房”。位于康宁殿和交泰殿之间，可以迅速为国王和王妃送餐。技艺最精湛的熟手（厨师）和尚宫们每天准备五次膳食。"
                    )
                ),
                HeritageChunk(
                    chunkId = "so_02",
                    section = "건축",
                    title = "기능적으로 설계된 주방 건축",
                    titleMap = mapOf(
                        "ko" to "기능적으로 설계된 주방 건축",
                        "en" to "Functionally Designed Kitchen Architecture",
                        "ja" to "機能的に設計された厨房建築",
                        "zh" to "功能性设计的厨房建筑"
                    ),
                    keywords = listOf("아궁이", "우물", "환기", "동선"),
                    content = "소주방은 조리 기능에 최적화된 실용적 건축물입니다. 여러 개의 아궁이가 나란히 설치되어 동시에 여러 음식을 조리할 수 있었고, 근처에 우물이 있어 물 공급이 용이했습니다. 연기를 배출하기 위한 환기 구조가 잘 갖추어져 있으며, 음식을 빠르게 운반할 수 있도록 강녕전·교태전과의 동선이 효율적으로 설계되었습니다.",
                    contentMap = mapOf(
                        "ko" to "소주방은 조리 기능에 최적화된 실용적 건축물입니다. 여러 개의 아궁이가 나란히 설치되어 동시에 여러 음식을 조리할 수 있었고, 근처에 우물이 있어 물 공급이 용이했습니다. 연기를 배출하기 위한 환기 구조가 잘 갖추어져 있으며, 음식을 빠르게 운반할 수 있도록 강녕전·교태전과의 동선이 효율적으로 설계되었습니다.",
                        "en" to "Sojubang is a practical structure optimized for cooking. Multiple furnaces (agungi) were installed side by side for simultaneous cooking, with a nearby well for easy water supply. Ventilation structures for smoke exhaust were well-designed, and the circulation routes to Gangnyeongjeon and Gyotaejeon were efficiently planned for quick food delivery.",
                        "ja" to "焼厨房は調理機能に最適化された実用的な建築物です。複数のかまどが並んで設置され、同時に複数の料理を調理でき、近くに井戸があって水の供給が容易でした。煙を排出するための換気構造がよく整えられており、康寧殿・交泰殿への食事の迅速な運搬ができるよう動線が効率的に設計されています。",
                        "zh" to "烧厨房是为烹饪功能优化的实用建筑。多个灶口并排设置，可以同时烹制多种食物，附近有水井便于取水。排烟通风结构完善，与康宁殿、交泰殿的动线设计高效，便于快速送餐。"
                    )
                ),
                HeritageChunk(
                    chunkId = "so_03",
                    section = "트리비아",
                    title = "왕의 하루 다섯 끼 수라상",
                    titleMap = mapOf(
                        "ko" to "왕의 하루 다섯 끼 수라상",
                        "en" to "The King's Five Daily Royal Meals",
                        "ja" to "王の一日五食の水刺膳",
                        "zh" to "国王每日五餐的膳食"
                    ),
                    keywords = listOf("수라상", "반찬", "12첩", "궁중요리"),
                    content = "조선 왕의 하루 식사는 이른 아침 초조반상, 아침 수라, 점심 낮것상, 저녁 수라, 야참(밤참)까지 총 5번이었습니다. 정식 수라상에는 밥·국·찌개를 비롯해 12가지 반찬(12첩 반상)이 차려졌으며, 독이 없는지 확인하는 '기미상궁'이 왕보다 먼저 음식을 맛보았습니다. 궁중음식은 현재 무형문화재로 그 전통이 이어지고 있습니다.",
                    contentMap = mapOf(
                        "ko" to "조선 왕의 하루 식사는 이른 아침 초조반상, 아침 수라, 점심 낮것상, 저녁 수라, 야참(밤참)까지 총 5번이었습니다. 정식 수라상에는 밥·국·찌개를 비롯해 12가지 반찬(12첩 반상)이 차려졌으며, 독이 없는지 확인하는 '기미상궁'이 왕보다 먼저 음식을 맛보았습니다. 궁중음식은 현재 무형문화재로 그 전통이 이어지고 있습니다.",
                        "en" to "The Joseon king ate five times daily: early morning light meal, morning Sura, midday meal, evening Sura, and a late-night snack. A formal Sura table featured rice, soup, stew, and 12 side dishes (12-cheop bansang). A 'taste-testing court lady' (Gimi Sanggung) always sampled the food before the king to check for poison. Royal court cuisine continues today as Intangible Cultural Heritage.",
                        "ja" to "朝鮮の王の1日の食事は、早朝の軽食、朝の水刺、昼食、夕方の水刺、夜食まで計5回でした。正式な水刺膳には、ご飯・汁・チゲをはじめ12種類のおかず（12チョプ飯床）が並べられ、毒がないか確認する「箕尾尚宮」が王より先に食事を味見しました。宮中料理は現在、無形文化財としてその伝統が受け継がれています。",
                        "zh" to "朝鲜国王每天用膳五次：清晨轻食、早朝水刺、午膳、晚朝水刺和夜宵。正式的水刺膳上有米饭、汤、炖菜及12种小菜（十二碟饭桌）。“尝膳尚宫”会在国王用膳前先品尝食物以检查是否有毒。宫廷饮食至今作为非物质文化遗产传承着。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "so_agungi",
                    displayName = mapOf("ko" to "소주방 아궁이", "en" to "Royal Kitchen Furnace", "ja" to "焼厨房のかまど", "zh" to "烧厨房灶口"),
                    visualHints = listOf("traditional korean kitchen furnace with stone opening", "multiple cooking hearths side by side in palace kitchen", "stone and brick cooking stations in royal kitchen"),
                    linkedChunkId = "so_02",
                    locationHint = mapOf("ko" to "강녕전/교태전 사이 소주방의 아궁이들.", "en" to "Multiple furnaces at Sojubang between Gangnyeongjeon and Gyotaejeon.", "ja" to "康寧殿/交泰殿間の焼厨房のかまど。", "zh" to "康宁殿/交泰殿之间烧厨房的灶口。")
                )
            )
        ),

        // ═══════════════════════════════════════════════════════════════
        // 흥복전 (Heungbokjeon) — 근정전 동쪽 보조 전각
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "heungbokjeon",
            title = "흥복전 (Heungbokjeon)",
            titleMap = mapOf(
                "ko" to "흥복전 (Heungbokjeon)",
                "en" to "Heungbokjeon Hall",
                "ja" to "興福殿 (フンボクジョン)",
                "zh" to "兴福殿"
            ),
            aliases = listOf("흥복전", "Heungbokjeon", "Heungbokjeon Hall", "興福殿"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "east_court",
            shortDescription = "근정전 동쪽에 위치한 보조 전각으로, 현재 국립고궁박물관 부지에 그 터가 남아 있습니다.",
            shortDescMap = mapOf(
                "ko" to "근정전 동쪽에 위치한 보조 전각으로, 현재 국립고궁박물관 부지에 그 터가 남아 있습니다.",
                "en" to "An auxiliary hall east of Geunjeongjeon, its former site now occupied by the National Palace Museum of Korea.",
                "ja" to "勤政殿の東側に位置する補助殿閣で、現在は国立古宮博物館の敷地にその跡が残っています。",
                "zh" to "位于勤政殿东侧的辅助殿阁，其旧址现为国立故宫博物馆所在地。"
            ),
            latitude = 37.5793,
            longitude = 126.9778,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "heungbokjeon_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "흥복전(興福殿)은 고종 대 경복궁을 다시 지을 때 처음 지어졌으며, ‘흥복’의 뜻은 ‘복을 일으키다’라는 뜻이다. 이곳은 고종 연간에 독일, 일본, 이탈리아, 프랑스 등 외국 사신을 했다는 기록이 있다. 이후 신정황후 조씨(24대 헌종의 어머니이자 26대 고종의 양어머니, 대한제국 선포 후 황후로 추존됨)가 세상을 떠난 곳이기도 하다. 흥복전은 일제강점기 철거되었다가 2020년에 복원하였다. 흥복전 전체 전경 26. 4. 19. 오후 4:38",
                    contentMap = mapOf("ko" to "흥복전(興福殿)은 고종 대 경복궁을 다시 지을 때 처음 지어졌으며, ‘흥복’의 뜻은 ‘복을 일으키다’라는 뜻이다. 이곳은 고종 연간에 독일, 일본, 이탈리아, 프랑스 등 외국 사신을 했다는 기록이 있다. 이후 신정황후 조씨(24대 헌종의 어머니이자 26대 고종의 양어머니, 대한제국 선포 후 황후로 추존됨)가 세상을 떠난 곳이기도 하다. 흥복전은 일제강점기 철거되었다가 2020년에 복원하였다. 흥복전 전체 전경 26. 4. 19. 오후 4:38")
                ),
                HeritageChunk(
                    chunkId = "heung_01",
                    section = "역사",
                    title = "복을 일으키는 전각",
                    titleMap = mapOf(
                        "ko" to "복을 일으키는 전각",
                        "en" to "The Hall That Brings Fortune",
                        "ja" to "福を興す殿閣",
                        "zh" to "兴福的殿阁"
                    ),
                    keywords = listOf("보조", "동쪽", "박물관", "편전"),
                    content = "흥복전은 '복을 일으킨다'는 뜻의 전각으로, 근정전 동쪽 영역에 위치했습니다. 왕실의 각종 행사를 보조하고, 때로는 임시 편전이나 대기 공간으로 사용되었습니다. 일제강점기에 경복궁 전각 대부분이 훼손되면서 흥복전도 철거되었으며, 그 자리에 현재 국립고궁박물관이 들어서 있습니다. 박물관 내에서 조선 왕실 유물을 관람하는 것으로 흥복전의 역사적 의미를 되새길 수 있습니다.",
                    contentMap = mapOf(
                        "ko" to "흥복전은 '복을 일으킨다'는 뜻의 전각으로, 근정전 동쪽 영역에 위치했습니다. 왕실의 각종 행사를 보조하고, 때로는 임시 편전이나 대기 공간으로 사용되었습니다. 일제강점기에 경복궁 전각 대부분이 훼손되면서 흥복전도 철거되었으며, 그 자리에 현재 국립고궁박물관이 들어서 있습니다. 박물관 내에서 조선 왕실 유물을 관람하는 것으로 흥복전의 역사적 의미를 되새길 수 있습니다.",
                        "en" to "Heungbokjeon means 'Hall That Raises Fortune' and was located in the eastern area of Geunjeongjeon. It assisted with various royal events and was sometimes used as a temporary office or waiting area. Demolished during the Japanese colonial period along with most palace halls, the National Palace Museum of Korea now stands on its site. Viewing Joseon royal artifacts in the museum helps reflect on Heungbokjeon's historical significance.",
                        "ja" to "興福殿は「福を興す」という意味の殿閣で、勤政殿の東側エリアに位置していました。王室の各種行事を補助し、時には臨時の便殿や待機場所として使用されました。日本統治時代に景福宮の殿閣のほとんどが破壊される中で興福殿も撤去され、その場所に現在の国立古宮博物館が建っています。博物館で朝鮮王室の遺物を観覧することで興福殿の歴史的意義を偲ぶことができます。",
                        "zh" to "兴福殿意为“兴起福气的殿阁”，位于勤政殿东侧区域。辅助王室各种活动，有时用作临时便殿或等候空间。日据时期景福宫大部分殿阁被毁，兴福殿也被拆除，现在国立故宫博物馆建在其旧址上。在博物馆内观赏朝鲜王室文物，可以重新认识兴福殿的历史意义。"
                    )
                ),
                HeritageChunk(
                    chunkId = "heung_02",
                    section = "관람 가이드",
                    title = "국립고궁박물관과 함께 보기",
                    titleMap = mapOf(
                        "ko" to "국립고궁박물관과 함께 보기",
                        "en" to "Visit Along with the National Palace Museum",
                        "ja" to "国立古宮博物館と一緒に見る",
                        "zh" to "与国立故宫博物馆一同参观"
                    ),
                    keywords = listOf("박물관", "유물", "관람", "무료"),
                    content = "흥복전 터에 자리한 국립고궁박물관은 무료로 관람할 수 있으며, 조선 왕실의 의례 용품, 어보(왕의 도장), 복식, 과학 기구 등 4만여 점의 유물을 소장하고 있습니다. 경복궁 관람과 함께 방문하면 궁궐 건축의 맥락에서 왕실 문화를 더 깊이 이해할 수 있습니다.",
                    contentMap = mapOf(
                        "ko" to "흥복전 터에 자리한 국립고궁박물관은 무료로 관람할 수 있으며, 조선 왕실의 의례 용품, 어보(왕의 도장), 복식, 과학 기구 등 4만여 점의 유물을 소장하고 있습니다. 경복궁 관람과 함께 방문하면 궁궐 건축의 맥락에서 왕실 문화를 더 깊이 이해할 수 있습니다.",
                        "en" to "The National Palace Museum of Korea on Heungbokjeon's former site offers free admission and houses over 40,000 artifacts including royal ritual items, royal seals (Eobo), costumes, and scientific instruments. Combined with a Gyeongbokgung visit, it provides deeper understanding of royal culture within the context of palace architecture.",
                        "ja" to "興福殿の跡地にある国立古宮博物館は無料で見学でき、朝鮮王室の儀礼用品、御宝（王の印章）、服飾、科学器具など4万点以上の遺物を所蔵しています。景福宮の見学と合わせて訪れると、宮殿建築の文脈で王室文化をより深く理解することができます。",
                        "zh" to "位于兴福殿旧址的国立故宫博物馆免费参观，收藏有朝鲜王室仪式用品、御宝（王印）、服饰、科学仪器等4万余件文物。与景福宫参观结合，可以在宫殿建筑的背景下更深入地了解王室文化。"
                    )
                )
            ),
            subElements = emptyList(),
            coverImageAsset = "heritage/heungbokjeon_cover.jpg",
            galleryImageAssets = listOf("heritage/heungbokjeon_1.jpg", "heritage/heungbokjeon_2.jpg", "heritage/heungbokjeon_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 근정문 (Geunjeongmun) — 근정전 정문
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "geunjeongmun",
            title = "근정문 (Geunjeongmun)",
            titleMap = mapOf(
                "ko" to "근정문 (Geunjeongmun)",
                "en" to "Geunjeongmun Gate",
                "ja" to "勤政門 (クンジョンムン)",
                "zh" to "勤政门"
            ),
            aliases = listOf("근정문", "Geunjeongmun", "Geunjeongmun Gate", "勤政門"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "main_court",
            shortDescription = "근정전으로 들어가는 정문으로, 문을 지나며 의례의 시작을 알리던 상징적 관문입니다.",
            shortDescMap = mapOf(
                "ko" to "근정전으로 들어가는 정문으로, 문을 지나며 의례의 시작을 알리던 상징적 관문입니다.",
                "en" to "The main gate leading to Geunjeongjeon, a symbolic gateway that marked the beginning of state ceremonies.",
                "ja" to "勤政殿への正門で、門をくぐることで儀礼の始まりを告げる象徴的な関門です。",
                "zh" to "通往勤政殿的正门，穿过此门标志着国家仪式的开始，是象征性的关口。"
            ),
            latitude = 37.5788,
            longitude = 126.9770,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "geunjeongmun_pdf_official",
                    section = "공식 소개",
                    title = "궁능유적본부 공식 소개",
                    titleMap = mapOf(
                        "ko" to "궁능유적본부 공식 소개",
                        "en" to "Official Introduction (Royal Palaces and Tombs Center)",
                        "ja" to "宮陵遺跡本部 公式紹介",
                        "zh" to "宫陵遗迹本部 官方介绍"
                    ),
                    keywords = listOf("공식", "소개", "원문"),
                    content = "근정전(勤政殿)은 경복궁의 정전으로 왕의 즉위식, 신하들의 하례, 외국 사신의 접견, 궁중연회 등 중요한 국가행사를 치르던 곳이다. 근정전의 ‘근정’은 천하의 일을 부지런히 잘 다스리다‘라는 뜻으로, 궁궐 내에서도 가장 규모가 크고 격식을 갖춘 건물로 면적도 가장 넓게 차지하고 있다. 근정전은 2단의 월대 위에 다시 낮은 기단을 두고 그 위로 중층 올린 건물로 안에서 보면 층 구분이 없는 통층(通層)이다. 근정전 앞마당, 즉 조정(朝廷)은 다른 궁궐의 정전과 같이 박석이 깔려있고, 중앙에는 삼도(三道)를 두어 궁궐의 격식을 갖추었으며 조정에는 정1품부터 정9품까지의 품계석을 다. 월대의 귀퉁이나 계단 주위 난간 기둥에는 4신상과 12지신상을 포함하여 28수 별자리상 등을 간결하지만 재치있게 조각하였다. 내부 바닥은 전돌을 깔았고, 북쪽 가운데에 근정전 전체 전경 26. 4. 19. 오후 4:34 자리인 어좌를 설치하였다. 어좌 뒤에는 왕권을 상징하는 해와 달, 다섯 봉우리의 산이 그려진 ‘일월오봉도’를 놓았고 천장에는 칠조룡을 조각하여 장식하였다. 근정전에서는 정 세종, 세조, 중종, 선조가 왕위에 올랐으며, 1985년 국보로 지정되었다. 근정문(勤政門)은 근정전의 정문으로 앞면 3칸, 옆면 2칸의 우진각지붕의 형태로, 궁궐 정전의 정문 중 유일하게 2층 규모로 지었다. 근정문은 왕의 장례(국장)가 있을 때 다음 즉위식을 치렀던 곳으로 이곳에서 단종, 성종, 명종이 왕위에 올랐다. 근정문은 행각을 포함하여 1985년 보물로 지정되었다. ⊙ 근정전 행랑 주련(柱聯) 내용 (위치 : 근정문 동쪽 일화문 안) ① 立愛敦親 敎民以睦(입애돈친 교민이목) : 사랑을 확립하고 친족끼리 돈독하게 함으로써 백성들을 화목하게 하고, ② 好學樂善 爲世所宗(호학낙선 위세소종) : 배움을 좋아하고 선을 즐김으로써 세상 사람들이 받드는 바가 된다. ③ 序昭六親 殷道隆盛(서소육친 은도융성) : 질서가 육친(六親)에 밝으니 은나라의 도가 융성하고, ④ 德推九睦 治堯旪龢(덕추구목 치요협화) : 덕이 구족(九族)에 미치니 요임금의 정치가 화목하도다. ⑤ 列卿尙書 落花底春酒(열경상서 낙화저춘주) : 구경(九卿)과 상서(尙書)들은 떨어지는 꽃 아래에서 봄 술을 마시고, ⑥ 王孫公子 芳樹下淸歌(왕손공자 방수하청가) : 왕손과 공자들은 아름다운 나무 아래에서 청아한 노래를 부르도다. ⑦ 捍禦宗邦 維城維翰(한어종방 유성유한) : 나라를 막고 지키는 것",
                    contentMap = mapOf("ko" to "근정전(勤政殿)은 경복궁의 정전으로 왕의 즉위식, 신하들의 하례, 외국 사신의 접견, 궁중연회 등 중요한 국가행사를 치르던 곳이다. 근정전의 ‘근정’은 천하의 일을 부지런히 잘 다스리다‘라는 뜻으로, 궁궐 내에서도 가장 규모가 크고 격식을 갖춘 건물로 면적도 가장 넓게 차지하고 있다. 근정전은 2단의 월대 위에 다시 낮은 기단을 두고 그 위로 중층 올린 건물로 안에서 보면 층 구분이 없는 통층(通層)이다. 근정전 앞마당, 즉 조정(朝廷)은 다른 궁궐의 정전과 같이 박석이 깔려있고, 중앙에는 삼도(三道)를 두어 궁궐의 격식을 갖추었으며 조정에는 정1품부터 정9품까지의 품계석을 다. 월대의 귀퉁이나 계단 주위 난간 기둥에는 4신상과 12지신상을 포함하여 28수 별자리상 등을 간결하지만 재치있게 조각하였다. 내부 바닥은 전돌을 깔았고, 북쪽 가운데에 근정전 전체 전경 26. 4. 19. 오후 4:34 자리인 어좌를 설치하였다. 어좌 뒤에는 왕권을 상징하는 해와 달, 다섯 봉우리의 산이 그려진 ‘일월오봉도’를 놓았고 천장에는 칠조룡을 조각하여 장식하였다. 근정전에서는 정 세종, 세조, 중종, 선조가 왕위에 올랐으며, 1985년 국보로 지정되었다. 근정문(勤政門)은 근정전의 정문으로 앞면 3칸, 옆면 2칸의 우진각지붕의 형태로, 궁궐 정전의 정문 중 유일하게 2층 규모로 지었다. 근정문은 왕의 장례(국장)가 있을 때 다음 즉위식을 치렀던 곳으로 이곳에서 단종, 성종, 명종이 왕위에 올랐다. 근정문은 행각을 포함하여 1985년 보물로 지정되었다. ⊙ 근정전 행랑 주련(柱聯) 내용 (위치 : 근정문 동쪽 일화문 안) ① 立愛敦親 敎民以睦(입애돈친 교민이목) : 사랑을 확립하고 친족끼리 돈독하게 함으로써 백성들을 화목하게 하고, ② 好學樂善 爲世所宗(호학낙선 위세소종) : 배움을 좋아하고 선을 즐김으로써 세상 사람들이 받드는 바가 된다. ③ 序昭六親 殷道隆盛(서소육친 은도융성) : 질서가 육친(六親)에 밝으니 은나라의 도가 융성하고, ④ 德推九睦 治堯旪龢(덕추구목 치요협화) : 덕이 구족(九族)에 미치니 요임금의 정치가 화목하도다. ⑤ 列卿尙書 落花底春酒(열경상서 낙화저춘주) : 구경(九卿)과 상서(尙書)들은 떨어지는 꽃 아래에서 봄 술을 마시고, ⑥ 王孫公子 芳樹下淸歌(왕손공자 방수하청가) : 왕손과 공자들은 아름다운 나무 아래에서 청아한 노래를 부르도다. ⑦ 捍禦宗邦 維城維翰(한어종방 유성유한) : 나라를 막고 지키는 것")
                ),
                HeritageChunk(
                    chunkId = "gmun_01",
                    section = "역사",
                    title = "의례의 시작, 근정문",
                    titleMap = mapOf(
                        "ko" to "의례의 시작, 근정문",
                        "en" to "Beginning of Ceremony: Geunjeongmun",
                        "ja" to "儀礼の始まり、勤政門",
                        "zh" to "仪式的开始——勤政门"
                    ),
                    keywords = listOf("정문", "의례", "품계석", "어도"),
                    content = "근정문은 근정전 앞마당(조정)으로 들어가는 정문입니다. 조선 시대 신하들은 이 문을 통과하면서 마음을 가다듬고, 품계석(관직 등급 표시석)에 따라 자신의 위치에 섰습니다. 문 안쪽으로는 삼도(三道)가 이어지는데, 가운데 길(어도)은 왕만이 걸을 수 있는 길이었습니다. 근정문은 단순한 출입구가 아니라, 공적 의례 공간으로의 전환을 알리는 상징적 경계였습니다.",
                    contentMap = mapOf(
                        "ko" to "근정문은 근정전 앞마당(조정)으로 들어가는 정문입니다. 조선 시대 신하들은 이 문을 통과하면서 마음을 가다듬고, 품계석(관직 등급 표시석)에 따라 자신의 위치에 섰습니다. 문 안쪽으로는 삼도(三道)가 이어지는데, 가운데 길(어도)은 왕만이 걸을 수 있는 길이었습니다. 근정문은 단순한 출입구가 아니라, 공적 의례 공간으로의 전환을 알리는 상징적 경계였습니다.",
                        "en" to "Geunjeongmun is the main gate leading to Geunjeongjeon's front courtyard (Jojeong). Joseon officials composed themselves while passing through this gate, taking their positions according to the rank stones (Pumgyeseok). Inside the gate, three paths extend — the central path (Eodo) was reserved exclusively for the king. Geunjeongmun was not merely an entrance but a symbolic boundary announcing the transition into a formal ceremonial space.",
                        "ja" to "勤政門は勤政殿の前庭（朝廷）に入る正門です。朝鮮時代の臣下たちはこの門を通過しながら心を整え、品階石（官職の等級を示す石）に従って自分の位置に立ちました。門の内側には三道が続き、真ん中の道（御道）は王だけが歩ける道でした。勤政門は単なる出入口ではなく、公的な儀礼空間への転換を告げる象徴的な境界でした。",
                        "zh" to "勤政门是通往勤政殿前庭（朝廷）的正门。朝鲜时代的大臣们穿过此门时整肃心绪，按照品阶石（官职等级标识石）站在自己的位置上。门内延伸出三条道路，中间的御道只有国王才能行走。勤政门不仅是出入口，更是宣告进入正式仪式空间的象征性分界。"
                    )
                ),
                HeritageChunk(
                    chunkId = "gmun_02",
                    section = "건축",
                    title = "중층 문루의 위엄",
                    titleMap = mapOf(
                        "ko" to "중층 문루의 위엄",
                        "en" to "The Dignity of the Double-Story Gate Tower",
                        "ja" to "重層門楼の威厳",
                        "zh" to "重层门楼的威严"
                    ),
                    keywords = listOf("문루", "중층", "팔작지붕", "석축"),
                    content = "근정문은 석축 기단 위에 세워진 중층(2층) 문루 건물로, 팔작지붕 양식을 갖추고 있습니다. 문루에서 내려다보면 근정전 마당 전체가 한눈에 들어오며, 조선 시대에는 왕이 때때로 이 문루에서 신하들의 모습을 살피기도 했습니다. 좌우에는 일화문(日華門)과 월화문(月華門)이 있어, 각각 동쪽과 서쪽의 출입을 담당했습니다.",
                    contentMap = mapOf(
                        "ko" to "근정문은 석축 기단 위에 세워진 중층(2층) 문루 건물로, 팔작지붕 양식을 갖추고 있습니다. 문루에서 내려다보면 근정전 마당 전체가 한눈에 들어오며, 조선 시대에는 왕이 때때로 이 문루에서 신하들의 모습을 살피기도 했습니다. 좌우에는 일화문(日華門)과 월화문(月華門)이 있어, 각각 동쪽과 서쪽의 출입을 담당했습니다.",
                        "en" to "Geunjeongmun is a double-story gate tower built on a stone foundation, featuring a hip-and-gable roof (Paljak) style. From the tower, the entire Geunjeongjeon courtyard comes into view. During the Joseon era, the king would sometimes observe his officials from this tower. Ilhwamun (Sun Flower Gate) and Wolhwamun (Moon Flower Gate) flank it on east and west respectively.",
                        "ja" to "勤政門は石積みの基壇の上に建てられた重層（2階）の門楼建物で、八作屋根の様式を備えています。門楼から見下ろすと勤政殿の庭全体が一望でき、朝鮮時代には王が時々この門楼から臣下たちの様子を見ることもありました。左右には日華門と月華門があり、それぞれ東と西の出入りを担当しました。",
                        "zh" to "勤政门是建在石砌基台上的重层（两层）门楼建筑，采用八作屋顶样式。从门楼俯瞰，勤政殿庭院全貌尽收眼底，朝鲜时代国王有时会从门楼上观察大臣们的情形。左右两侧分别是日华门和月华门，各负责东西方向的出入。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "gmun_signboard",
                    displayName = mapOf("ko" to "근정문 현판", "en" to "Geunjeongmun Signboard", "ja" to "勤政門の扁額", "zh" to "勤政门匾额"),
                    visualHints = listOf("palace gate name plaque on two-story gate", "wooden sign above main entrance to throne hall courtyard", "nameplate on imposing palace gate building"),
                    linkedChunkId = "gmun_01",
                    locationHint = mapOf("ko" to "근정전 앞 2층 문루 정면 중앙의 근정문 현판.", "en" to "Geunjeongmun signboard at center of two-story gate tower.", "ja" to "勤政殿前の2階門楼正面中央の勤政門扁額。", "zh" to "勤政殿前二层门楼正面中央的勤政门匾额。")
                ),
                SubElement(
                    id = "gmun_samdo",
                    displayName = mapOf("ko" to "삼도 (세 갈래 길)", "en" to "Samdo (Three Paths)", "ja" to "三道（三つの道）", "zh" to "三道（三条路）"),
                    visualHints = listOf("three stone paths leading through palace courtyard", "central raised stone walkway flanked by two lower paths", "stone road divided into three sections in palace grounds"),
                    linkedChunkId = "gmun_01",
                    locationHint = mapOf("ko" to "근정문 안쪽 삼도 — 가운데는 왕의 어도.", "en" to "Three paths inside Geunjeongmun — central Eodo was the king's.", "ja" to "勤政門内側の三道 — 中央は王の御道。", "zh" to "勤政门内三道——中间是国王御道。")
                )
            ),
            coverImageAsset = "heritage/geunjeongmun_cover.jpg",
            galleryImageAssets = listOf("heritage/geunjeongmun_1.jpg", "heritage/geunjeongmun_2.jpg", "heritage/geunjeongmun_3.jpg")
        ),

        // ═══════════════════════════════════════════════════════════════
        // 영제교 (Yeongjeogyo) — 금천 위 다리
        // ═══════════════════════════════════════════════════════════════
        HeritageContent(
            id = "yeongjeogyo",
            title = "영제교 (Yeongjeogyo)",
            titleMap = mapOf(
                "ko" to "영제교 (Yeongjeogyo)",
                "en" to "Yeongjeogyo Bridge",
                "ja" to "永済橋 (ヨンジェギョ)",
                "zh" to "永济桥"
            ),
            aliases = listOf("영제교", "Yeongjeogyo", "Yeongjeogyo Bridge", "永済橋", "금천교"),
            palace = "경복궁 (Gyeongbokgung)",
            zone = "entrance",
            shortDescription = "경복궁 내 금천(禁川) 위에 놓인 돌다리로, 천록과 해태 조각이 새겨진 풍수적 경계입니다.",
            shortDescMap = mapOf(
                "ko" to "경복궁 내 금천(禁川) 위에 놓인 돌다리로, 천록과 해태 조각이 새겨진 풍수적 경계입니다.",
                "en" to "A stone bridge over the Geumcheon stream in Gyeongbokgung, a feng shui boundary carved with Cheonrok and Haetae sculptures.",
                "ja" to "景福宮内の禁川の上に架けられた石橋で、天禄と獬豸の彫刻が刻まれた風水的な境界です。",
                "zh" to "横跨景福宫内禁川的石桥，雕刻有天禄和獬豸，是风水意义上的分界线。"
            ),
            latitude = 37.5782,
            longitude = 126.9769,
            chunks = listOf(
                HeritageChunk(
                    chunkId = "yeo_01",
                    section = "역사",
                    title = "속세와 왕의 세계를 나누는 다리",
                    titleMap = mapOf(
                        "ko" to "속세와 왕의 세계를 나누는 다리",
                        "en" to "The Bridge Dividing the Mundane from the Royal World",
                        "ja" to "俗世と王の世界を分ける橋",
                        "zh" to "分隔凡尘与帝王之界的桥梁"
                    ),
                    keywords = listOf("금천", "풍수", "경계", "정화"),
                    content = "영제교는 경복궁 내 금천(禁川, 궁궐 앞을 흐르는 개천) 위에 놓인 돌다리입니다. 풍수적으로 금천은 궁궐의 기운을 보호하고 나쁜 기운을 씻어내는 역할을 합니다. 신하들은 이 다리를 건너면서 속세의 잡념을 버리고 왕의 공간으로 들어간다는 상징적 의미가 있었습니다. '영제(永濟)'란 '영원히 건넌다'는 뜻입니다.",
                    contentMap = mapOf(
                        "ko" to "영제교는 경복궁 내 금천(禁川, 궁궐 앞을 흐르는 개천) 위에 놓인 돌다리입니다. 풍수적으로 금천은 궁궐의 기운을 보호하고 나쁜 기운을 씻어내는 역할을 합니다. 신하들은 이 다리를 건너면서 속세의 잡념을 버리고 왕의 공간으로 들어간다는 상징적 의미가 있었습니다. '영제(永濟)'란 '영원히 건넌다'는 뜻입니다.",
                        "en" to "Yeongjeogyo is a stone bridge over the Geumcheon (Forbidden Stream, a waterway flowing before the palace) within Gyeongbokgung. In feng shui, the Geumcheon protects the palace's energy and washes away negative forces. Officials crossing this bridge symbolically shed worldly distractions before entering the king's domain. 'Yeongje' means 'to cross eternally.'",
                        "ja" to "永済橋は景福宮内の禁川（宮殿の前を流れる小川）の上に架けられた石橋です。風水的に禁川は宮殿の気を守り、悪い気を洗い流す役割を果たします。臣下たちはこの橋を渡りながら俗世の雑念を捨て、王の空間に入るという象徴的な意味がありました。「永済」とは「永遠に渡る」という意味です。",
                        "zh" to "永济桥是横跨景福宫内禁川（宫殿前方流淌的小溪）上的石桥。从风水角度看，禁川起着保护宫殿气场、洗涤不良气息的作用。大臣们渡过此桥，象征着抛弃世俗杂念，进入国王的空间。“永济”意为“永远渡过”。"
                    )
                ),
                HeritageChunk(
                    chunkId = "yeo_02",
                    section = "관람 포인트",
                    title = "다리 위 천록과 서수 조각",
                    titleMap = mapOf(
                        "ko" to "다리 위 천록과 서수 조각",
                        "en" to "Cheonrok and Auspicious Beast Sculptures on the Bridge",
                        "ja" to "橋の上の天禄と瑞獣の彫刻",
                        "zh" to "桥上的天禄与瑞兽雕刻"
                    ),
                    keywords = listOf("천록", "서수", "조각", "난간"),
                    content = "영제교 난간에는 천록(天祿)을 비롯한 서수(瑞獸, 상서로운 동물) 조각이 새겨져 있습니다. 천록은 사슴 모양의 상상의 동물로, 나쁜 기운을 막고 복을 가져다준다고 믿어졌습니다. 다리 아래 금천의 석벽에도 거북·용 등의 조각이 있어, 물의 흐름과 함께 나쁜 기운을 흘려보내는 풍수적 장치 역할을 합니다. 관람 시 다리 양쪽 난간과 물 아래 석벽을 꼼꼼히 살펴보세요.",
                    contentMap = mapOf(
                        "ko" to "영제교 난간에는 천록(天祿)을 비롯한 서수(瑞獸, 상서로운 동물) 조각이 새겨져 있습니다. 천록은 사슴 모양의 상상의 동물로, 나쁜 기운을 막고 복을 가져다준다고 믿어졌습니다. 다리 아래 금천의 석벽에도 거북·용 등의 조각이 있어, 물의 흐름과 함께 나쁜 기운을 흘려보내는 풍수적 장치 역할을 합니다. 관람 시 다리 양쪽 난간과 물 아래 석벽을 꼼꼼히 살펴보세요.",
                        "en" to "The bridge's balustrades feature carvings of Cheonrok (a deer-like mythical creature) and other auspicious beasts (seosoo). Cheonrok was believed to ward off evil and bring fortune. Below the bridge, the stone walls of Geumcheon also bear carvings of turtles and dragons, serving as feng shui devices that channel away negative energy with the water's flow. When visiting, look closely at both railings and the stone walls beneath the water.",
                        "ja" to "永済橋の欄干には天禄をはじめとする瑞獣（めでたい動物）の彫刻が刻まれています。天禄は鹿の形をした想像上の動物で、悪い気を防ぎ福をもたらすと信じられていました。橋の下の禁川の石壁にも亀・龍などの彫刻があり、水の流れと共に悪い気を流す風水的な装置の役割を果たしています。見学の際は橋の両側の欄干と水の下の石壁をよく見てみてください。",
                        "zh" to "永济桥栏杆上雕刻着天禄等瑞兽（吉祥动物）。天禄是鹿形的神兽，被认为能驱邪纳福。桥下禁川的石壁上也雕有龟、龙等，随着水流冲走不良气息，起到风水装置的作用。参观时请仔细观察桥两侧栏杆和水下的石壁。"
                    )
                )
            ),
            subElements = listOf(
                SubElement(
                    id = "yeo_cheonrok",
                    displayName = mapOf("ko" to "천록 조각", "en" to "Cheonrok Sculpture", "ja" to "天禄の彫刻", "zh" to "天禄雕刻"),
                    visualHints = listOf("stone carved mythical deer creature on bridge railing", "auspicious beast sculpture on stone bridge balustrade", "carved stone animal figure on palace bridge"),
                    linkedChunkId = "yeo_02",
                    locationHint = mapOf("ko" to "영제교 난간 양쪽 끝의 서수 석상.", "en" to "Mythical beast sculptures at both ends of Yeongjeogyo.", "ja" to "永済橋の欄干両端の瑞獣石像。", "zh" to "永济桥栏杆两端的瑞兽石像。")
                ),
                SubElement(
                    id = "yeo_geumcheon",
                    displayName = mapOf("ko" to "금천 (궁궐 개천)", "en" to "Geumcheon Stream", "ja" to "禁川（宮殿の小川）", "zh" to "禁川（宫殿小溪）"),
                    visualHints = listOf("stone-lined stream flowing under palace bridge", "narrow waterway with carved stone walls in palace grounds", "small stream with stone embankment running through palace"),
                    linkedChunkId = "yeo_01",
                    locationHint = mapOf("ko" to "영제교 아래를 흐르는 금천 — 돌벽 조각.", "en" to "Geumcheon stream flowing under Yeongjeogyo — carved stone walls.", "ja" to "永済橋の下を流れる禁川 — 石壁彫刻。", "zh" to "流经永济桥下的禁川——石壁雕刻。")
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
            longitude = 126.9766,
            imageAsset = "heritage/poi_gate_north_cover.jpg",
            descriptionMap = mapOf("ko" to "신무문(神武門)은 경복궁의 북문으로, ‘신무’는 ‘뛰어난 무용(武勇)‘ ’신령스러운 현무(玄武)‘라는 뜻이다. 신무문은 1433년(세종 15)에 지어졌다가 1475년(성종 6) 문의 이름을 문이라 붙였다.")
        ),

        // ── 동문 (건춘문) ────────────────────────────────────
        Poi(
            id = "gate_east",
            type = PoiType.GATE,
            title = "건춘문 (동문)",
            titleMap = mapOf(
                "ko" to "건춘문 (동문)",
                "en" to "Geonchunmun (East Gate)",
                "ja" to "建春門（東門）",
                "zh" to "建春门（东门）"
            ),
            latitude = 37.5800,
            longitude = 126.9793,
            imageAsset = "heritage/poi_gate_east_cover.jpg",
            descriptionMap = mapOf("ko" to "건춘문(建春門)은 경복궁의 동문으로, ‘건춘’의 뜻은 ‘봄을 세우다’라는 뜻이다. 서문인 영추문과 대비되는 개념의 이름으로 동쪽 방위의 개념에 맞게 지어졌다. 건춘문은 주로 왕세자와 동궁 영역이 위치한 관청에서 일하는 신하들이 이용하였다. 현재의 문은 고종 대에 경복궁을 다시 지을 때 지은 문이다. 건춘문 전경 26.")
        ),

        // ── 서문 (영추문) ────────────────────────────────────
        Poi(
            id = "gate_west",
            type = PoiType.GATE,
            title = "영추문 (서문)",
            titleMap = mapOf(
                "ko" to "영추문 (서문)",
                "en" to "Yeongchumun (West Gate)",
                "ja" to "迎秋門（西門）",
                "zh" to "迎秋门（西门）"
            ),
            latitude = 37.5800,
            longitude = 126.9740,
            imageAsset = "heritage/poi_gate_west_cover.jpg",
            descriptionMap = mapOf("ko" to "영추문(迎秋門)은 경복궁의 서문으로, ‘영추’는 ‘가을을 맞이한다’라는 뜻이다. 동문인 건춘문과 대비되는 개념의 이름으로 서쪽 방위의 개념에 맞게 지어졌다. 영추문은 주로 문 관이 출입하던 곳으로 특히 서쪽 궐내각사에 근무하던 신하들이 많이 이용하였다. 현재의 문은 1975년에 철근콘크리트 구조로 복원되었다.")
        ),

        // ── 흥례문 (외조 진입문) ─────────────────────────────
        Poi(
            id = "gate_heungnyemun",
            type = PoiType.GATE,
            title = "흥례문",
            titleMap = mapOf(
                "ko" to "흥례문",
                "en" to "Heungnyemun Gate",
                "ja" to "興禮門",
                "zh" to "兴礼门"
            ),
            latitude = 37.5770,
            longitude = 126.9769,
            imageAsset = "heritage/poi_gate_heungnyemun_cover.jpg",
            descriptionMap = mapOf("ko" to "흥례문(興禮門)은 경복궁의 중문으로 ‘흥례’는 ‘예를 일으킨다’라는 뜻이다. 원래 흥례문의 이름은 ‘홍례문’이었으나 1867년(고종 4) 경복궁을 다시 지을 때 지금의 이름으로 바 다. 흥례문은 일제강점기 때 조선총독부 청사를 지으면서 철거되었다가, 1996년 조선총독부 청사를 철거한 후 2001년에 복원하였다.")
        ),

        // ── 향원정 포토 스팟 (북쪽) ─────────────────────────
        Poi(
            id = "viewpoint_hyangwonjeong",
            type = PoiType.VIEWPOINT,
            title = "향원정 포토 스팟",
            titleMap = mapOf(
                "ko" to "향원정 포토 스팟",
                "en" to "Hyangwonjeong Photo Spot",
                "ja" to "香遠亭 フォトスポット",
                "zh" to "香远亭最佳拍照点"
            ),
            latitude = 37.5825,
            longitude = 126.9765
        )
    )

    override fun getExtraPoiList(): List<Poi> = extraPoiList
}
