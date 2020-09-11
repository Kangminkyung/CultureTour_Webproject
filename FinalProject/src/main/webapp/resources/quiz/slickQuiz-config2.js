// Setup your quiz text and questions here

// NOTE: pay attention to commas, IE struggles with those bad boys
 

var quizJSON = {
		
    "info": {
    	  "name":    "중급 문화재 Quiz",
          "main":    "<p>문화재 관련 퀴즈 입니다~ 문항을 잘 보고 풀어보세요.</p>",
          "results": "<h5 align='center'></h5><p>문제 풀이 하느라 고생하셨습니다.</p>",
          "level1":  "중급 문화재 Master!",
          "level2":  "참 잘했어요! 2% 아쉽군요~",
          "level3":  "기초 상식은 있으시군요!",
          "level4":  "문화재 공부좀 하세요ㅠㅠ",
          "level5":  "문화재 공부좀 해라~~" 
    },
    "questions": [
        { // Question 1
            "q": "신라 제27대 덕만(德曼)은 시호가 ( )이고, 성은 김씨이며 아버지는 진평왕이다.632년에 왕위에 올라 16년간 나라를 다스렸다. 법흥왕 무렵부터 성골만이 왕위에 오르게 하는 분위기가 무르익어,법흥 이후 직계후손으로 불리는 진흥·진지·진평으로 이어졌지만, 진평에게는 아들이 없어 왕위 계승에 문제가 생겼다.그러나 그것은 우리 역사의 새로운 장을 여는 기회였는지 모른다. 성골 계승의 틀이 강력히 잡힌 상황인데다, 비록 아들이 아니었지만, 선덕은 천성이 맑고 지혜로웠다.그런 그에게 첫 여왕의 영예가 돌아간 것이다. 그러나 여왕으로서 받는 정치적인 위험성은 컸고, [화랑세기]에 따르면 두 명의 남자와 세 번에 걸쳐 결혼생활을 하면서도 아이를 낳지 못했다. 강력한 신라로 가는 갈림길에 선 왕으로서 선덕은 어떤 고민을 하였고 어떤 왕정을 펼쳤을까." ,
            "a": [
                {"option": "선덕여왕",     "correct": true},
                {"option": "신문왕",     "correct": false},
                {"option": "진덕여왕",     "correct": false},
                {"option": "진성여왕",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        },
        { // Question 2
            "q": "유물은 그 시대의 상황을 알려 주는 중요한 자료이다. 중국과의 관계를 알게 해주는 중국 도자기가 출토되지 않은 유적은?",
            "a": [
                {"option": "원주 법천리고분군",    "correct": false},
                {"option": "경주 천마총",     "correct": true},
				{"option": "공주 수촌리고분군",     "correct": false},
				{"option": "경주 황남대총",     "correct": false}
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        },
        { // Question 3
            "q": "나라 밖으로 반출된 우리나라의 문화재가 가장 많이 있는 나라는 어디인가?",
            "a": [
                {"option": "미국",             "correct": false},
                {"option": "러시아",           "correct": false},
                {"option": "일본",          "correct": true},
                {"option": "독일",          "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        },
        { // Question 4
            "q": "백제는 두 번에 걸쳐 수도를 옮겼다. 이에 따라 백제의 시기구분에 있어 크게 3단계로 구분되게 되는데, 각 단계별 수도로 적절한 것은?",
            "a": [
                {"option": "웅진 부여 익산",    "correct": false},
                {"option": "한성 웅진 부여",     "correct": true},
				{"option": "한성 웅진 경주",     "correct": false}, 
			    {"option": "평양 한성 제천",     "correct": false} 

            ],
           "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        },
        { // Question 5
            "q": "이제부터 우리 고을 선비들이 하늘이 부여한 본성을 근본으로 하고 국가의 법을 준수하며 집에서나 고을에서나 각기 질서를 바로잡으면 나라에 좋은 선비가 될 것이요, 출세하든지 가난하게 살든지 서로 의지가 될 것이다.  .... 진실로 이를 알지 못하고 올바른 것을 어기고 예의를 해침으로써 우리 고을 풍속을 무너뜨리는 자는 바로 하늘의 뜻을 거역하는 백성이다.....이것이 바로 이것을 만들어 운영하는 까닭이다.",
            "a": [
                {"option": "서민 환자의 구제와 약재 판매를 담당하였다.",   "correct": false},
                {"option": "남녀노소 누구나 술과 노래를 즐기던 마을 축제이다.",          "correct": false},
                {"option": "공동 노동의 작업 공동체적 성격을 가지고 있다.",  "correct": true},
                {"option": "향촌사회의 질서유지와 함께 치안까지 담당하였다.",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        }, // no comma here
		{ // Question 6
            "q": "민범이는 이번 겨울방학을 맞아 여자친구와 함께 여행을 가려고 한다. 평소에 문화재에 관심이 많은 민범이와 여자친구는 여행에서 유적지도 가려고 하는데. 해당하는 지역은 어디인가? ㄱ. 도항리, 말산리 ,고분군 ㄴ. 옥전 ,고분군 ㄷ. 수정봉, 옥봉 , 고분군",
            "a": [
                {"option": "ㄱ: 수원  ㄴ: 안양  ㄷ: 연천",   "correct": false},
                {"option": "ㄱ: 옥천  ㄴ: 보은  ㄷ: 금산",          "correct": false},
                {"option": "ㄱ: 진주  ㄴ: 익산  ㄷ: 김제",  "correct": false},
                {"option": "ㄱ: 함안  ㄴ: 합천  ㄷ: 진주",     "correct": true} 
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        }, // no comma here
		{ // Question 7
            "q": "처음으로 소과(小科)에 급제한 진사(進士), 생원(生員)의 성명, 자(字), 생년간지(生年干支), 본관, 주소 등을 비롯하여, 부(父)의 관위(官位), 생존여부, 형제의 이름 · 자 등을 상세히 기록한 책을 무엇이라 하는가?",
            "a": [
                {"option": "동사강목",   "correct": false},
                {"option": "사마방목",          "correct": true},
                {"option": "용재총화",  "correct": false},
                {"option": "통감강목",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        }, // no comma here
		{ // Question 8
            "q": "다음 괄호 안에 들어갈 단어의 연결이 옳은 것은? 문화재는 전래되어 온 상태에 따라 ( ㉠  ) 와 (  ㉡ ) 로 구분하기도 한다. 전자는 사람의 손에서 손으로 전래되어 온 것을 말하며, 후자는 땅 속에서 발굴한 것을 말한다. 고미술품을 수장하고 애완하는 일본의 경우에는 고미술품을 ( ㉠ ) 와 ( ㉡ )라 하여 사람의 손에서 손으로 전래되어 온 것과 땅 속에서 캐낸 것을 나누어 보기도 한다. ",
            "a": [
                {"option": "전세고 - 토중고",   "correct": true},
                {"option": "토출고 - 전인고",          "correct": false},
                {"option": "전인고 - 토출고",  "correct": false},
                {"option": "토중고 - 전세고",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        }, // no comma here
		{ // Question 9
            "q": "강원 인제군에 위치한 곳으로 오세암 바로 앞의 봉우리로 공룡능선, 흑선동계곡, 나한봉 등의 절경을 한눈에 볼 수 있는 저명한 경관조망지점인 이곳은?",
            "a": [
                {"option": "오륙도",   "correct": false},
                {"option": "설악산",          "correct": false},
                {"option": "태종대",  "correct": false},
                {"option": "만경대",     "correct": true} 
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
        }, // no comma here
			 { // Question 10
            "q": "조선왕조 역대 임금의 신위를 모신 곳은?",
            "a": [
                {"option": "창덕궁",   "correct": false},
                {"option": "종묘",          "correct": true},
                {"option": "경복궁",  "correct": false},
                {"option": "창경궁",     "correct": false} 
            ],
            "correct": "<p><span>참 잘했어요</span></p>",
            "incorrect": "<p><span>심하시군요!</span></p>" 
			} // no comma here
		


		]

};