// Setup your quiz text and questions here

// NOTE: pay attention to commas, IE struggles with those bad boys

var quizJSON = {
		
    "info": {
        "name":    "초급 문화재 Quiz",
        "main":    "<p>문화재 관련 퀴즈 입니다~ 문항을 잘 보고 풀어보세요.</p>",
        "results": "<h5 align='center'></h5><p>문제 풀이 하느라 고생하셨습니다.</p>",
        "level1":  "초급 문화재 Master!",
        "level2":  "참 잘했어요! 2% 아쉽군요~",
        "level3":  "기초 상식은 있으시군요!",
        "level4":  "문화재 공부좀 하세요ㅠㅠ",
        "level5":  "문화재 공부좀 해라~~" 
    },
    "questions": [
        { // Question 1
            "q": "장영실이 만든 우리나라 최초의 자동 물시계는?",
            "a": [
                {"option": "해시계",     "correct": false},
                {"option": "혼천의",     "correct": false},
                {"option": "자격루",     "correct": true},
                {"option": "앙부일구",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
        { // Question 2
            "q": "세종 때 제작된 시간 측정 기구는?",
            "a": [
                {"option": "혼천의",    "correct": false},
                {"option": "앙부일구",     "correct": true},
				 {"option": "물시계",    "correct": false},
				 {"option": "자격루",    "correct": false}
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
        { // Question 3
            "q": "세계에서 가장 오래된 목판인쇄물 '무구정광대다라니경'이 발견된 곳은?",
            "a": [
                {"option": "석가탑",             "correct": true},
                {"option": "다보탑",           "correct": false},
                {"option": "해인사",          "correct": false},
                {"option": "미륵사지석탑",          "correct": false} 
            ],
           "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>"  
        },
        { // Question 4
            "q": "퇴계이황을 기리는 곳이자 1000원권 지폐에 있는 건축물은?",
            "a": [
                {"option": "도산서원",    "correct": true},
                {"option": "종묘",     "correct": false},
				 {"option": "경회루",    "correct": false},
                {"option": "안압지",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
        { // Question 5
            "q": "병자호란 때 인조가 피난 갔던 산성은?",
            "a": [
                {"option": "남한산성",   "correct": true},
                {"option": "수원화성",          "correct": false},
                {"option": "행주산성",  "correct": false},
                {"option": "강화산성",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },

		{ // Question 6
            "q": "다음 중 세계에서 가장 오래된 금속활자와 관련 있는 것은?",
            "a": [
                {"option": "직지심체요절",   "correct": true},
                {"option": "해인사",          "correct": false},
                {"option": "삼국유사",  "correct": false},
                {"option": "삼국사기",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },	
		{ // Question 7
            "q": "정약용이 고안해 수원화성 축조에 쓰인 기계는?",
            "a": [
                {"option": "거중기",   "correct": true},
                {"option": "간의",          "correct": false},
                {"option": "측우기",  "correct": false},
                {"option": "자격루",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
		{ // Question 8
            "q": "예전 1000원권 지폐에 있던 조선시대 건축물은?",
            "a": [
                {"option": "경회루",   "correct": true},
                {"option": "안압지",          "correct": false},
                {"option": "덕수궁",  "correct": false},
                {"option": "경복궁",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
		{ // Question 9
            "q": "다음 중 '신라의 미소'로 불리는 문화재는?",
            "a": [
                {"option": "얼굴무늬 수막새",   "correct": true},
                {"option": "서산 마애삼존불",          "correct": false},
                {"option": "지장보살상",  "correct": false},
                {"option": "석가모니불",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
		{ // Question 10
            "q": "도성의 남쪽 정문이면서 국보 제 1호인 문화재는?",
            "a": [
                {"option": "흥인지문",   "correct": false},
                {"option": "서대문",          "correct": false},
                {"option": "숭례문",  "correct": true},
                {"option": "동대문",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요!</span></p>",
            "incorrect": "<p><span>틀렸습니다!</span></p>" 
        },
	
    ]
};