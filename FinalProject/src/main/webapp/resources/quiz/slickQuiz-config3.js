// Setup your quiz text and questions here

// NOTE: pay attention to commas, IE struggles with those bad boys

var quizJSON = {
		
    "info": {
    	  "name":    "고급 문화재 Quiz",
          "main":    "<p>문화재 관련 퀴즈 입니다~ 문항을 잘 보고 풀어보세요.</p>",
          "results": "<h5 align='center'></h5><p>문제 풀이 하느라 고생하셨습니다.</p>",
          "level1":  "고급 문화재 Master!",
          "level2":  "참 잘했어요! 2% 아쉽군요~",
          "level3":  "기초 상식은 있으시군요!",
          "level4":  "문화재 공부좀 하세요ㅠㅠ",
          "level5":  "문화재 공부좀 해라~~" 
    },
    "questions": [
        { // Question 1
            "q": "원각사의 창건 내력을 적은 비로, 조선 성종 2년에 건립되었다. 비는 머릿돌을 따로 얹지 않고 비몸돌 위를 두 마리의 용을 감싸듯 표현되어 있어 복고적인 형식을 따르고 있는 이것은?",
            "a": [
                {"option": "진흥왕 순수비",     "correct": false},
                {"option": "대원각사비",     "correct": true},
                {"option": "태종무열왕릉비",     "correct": false},
                {"option": "장충단비",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
        { // Question 2
            "q": "조선시대의 궁궐로서 경운궁이라고 불리다가, 고종황제의 장수를 빈다는 뜻의 이 이름으로 고쳐 부르게 되었다. 조선후기에 궁궐로 갖추어진 곳이지만 구한말의 역사적 현장이었으며 전통목조건축과 서양식의 건축이 함께 남아있는 이 곳은?",
            "a": [
                {"option": "경복궁",    "correct": false},
                {"option": "창경궁",     "correct": false},
				 {"option": "창덕궁",    "correct": false},
				 {"option": "덕수궁",    "correct": true}
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
        { // Question 3
            "q": "말의 안장 양쪽에 달아 늘어뜨리는 장니에 그려진 말 그림으로 신라회화로서 현재까지 남아있는 거의 유일한 작품인 이것은?",
            "a": [
                {"option": "천마도",             "correct": true},
                {"option": "인왕제색도",           "correct": false},
                {"option": "세한도",          "correct": false},
                {"option": "십장색도",          "correct": false} 
            ],
           "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>"  
        },
        { // Question 4
            "q": "고려시대에 간행되었다고 해서 고려대장경이라고도 하고, 판수가 8만개에 달하여 8만대장경이라고도 부르는 불교경전의 총서인 이것은?",
            "a": [
                {"option": "해인사 대장경판",    "correct": true},
                {"option": "무구정광대다라니경",     "correct": false},
				 {"option": "동국정군",    "correct": false},
                {"option": "수어장대",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
        { // Question 5
            "q": "신라 제29대 왕인 태종무열왕의 능 앞에 세워진 석비로 통일신라시대에 세워졌던 비들의 최초의 예가 되고있는 이 비의 이름은?",
            "a": [
                {"option": "장충단비",   "correct": false},
                {"option": "충주 고구려비",          "correct": false},
                {"option": "태종무열왕릉비",  "correct": true},
                {"option": "진흥왕 순수비",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },

		{ // Question 6
            "q": "조선시대 서울도성을 둘러싸고 있던 성곽의 정문으로 남쪽에 있다고 해서 남대문이라고도 불렸다. 2008년 방화사건이 있었던 이 곳의 이름은?",
            "a": [
                {"option": "숭례문",   "correct": true},
                {"option": "동대문",          "correct": false},
                {"option": "화서문",  "correct": false},
                {"option": "팔달문",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },	
		{ // Question 7
            "q": "다음 중 유네스코(UNESCO)에 등재된 우리나라의 세계기록 유산이 아닌 것은?",
            "a": [
                {"option": "비변사 등록",   "correct": true},
                {"option": "일성록",          "correct": false},
                {"option": "동의보감",  "correct": false},
                {"option": "난중일기",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
		{ // Question 8
            "q": "남북국 시대에 대한 설명 중 옳지 않은 것은?",
            "a": [
                {"option": "발해의 무왕은 신라와 연합해 당을 공격하였다.",   "correct": true},
                {"option": "장보고는 청해진을 중심으로 동아시아의 무역을 장악하였다.",          "correct": false},
                {"option": "발해는 신라도라는 교통로를 이용해 신라와도 무역하였다.",  "correct": false},
                {"option": "발해는 일본과 교류하며 무역에도 힘썼다.",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
		{ // Question 9
            "q": "ㄱ: 백률사 석당, ㄴ: 정림사지 5층석탑, ㄷ: 창왕명석조사리감 ,ㄹ 법주사 쌍사자 석등  이중 백제의 문화제가 맞는것은?",
            "a": [
                {"option": "ㄴ,ㄷ",   "correct": true},
                {"option": "ㄱ,ㄹ",          "correct": false},
                {"option": "ㄷ,ㄹ",  "correct": false},
                {"option": "ㄱ,ㄴ",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
		{ // Question 10
            "q": "을미사변 - 아관파천 - x - 대한제국 수립, 다음 x 에들어갈 사건으로 옳은 것은?",
            "a": [
                {"option": "홍범 14조 반포",   "correct": false},
                {"option": "춘생문 사건 발발",          "correct": false},
                {"option": "독립협회 결성",  "correct": true},
                {"option": "단발령 공포",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
	
    ]
};