-- 밴드 테이블
create table final_band
(bcode     number(5) not null --밴드코드(시퀀스)
,bname     varchar2(100) not null --밴드이름
,badmin    varchar2(20) not null --밴드관리자 (밴드만든멤버아이디, final_member userid 참조)
,fk_tcode     varchar2(20)  not null -- 테마코드, final_thema의 테마코드(tcode) 참조
,binfo    clob -- 밴드정보소개
,bimg1     varchar2(100) -- 밴드소개사진1
,bimg2     varchar2(100) -- 밴드소개사진2
,bmembercnt   number  default 1 -- 밴드참가인원
,bregdate date  default sysdate --밴드생성일
,constraint  FK_final_band_badmin foreign key(badmin) -- 사용자아이디 참조
              references final_member(userid) 
,constraint FK_final_thema_tcode foreign key(fk_tcode) -- 테마코드참조
              references final_thema(tcode)
,constraint   UQ_final_band_badmin unique(badmin)
,constraint PK_final_band primary key(bcode)  
);

alter table final_band
add bimgbanner varchar2(100);

--밴드코드 시퀀스 (bcode)
create sequence seq_final_band
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

--테마테이블
create table final_thema
(tnum    number(8)     not null  -- 테마 대분류 번호(시퀀스)
,tcode    varchar2(20)  not null  -- 테마 코드(1000, 2000,3000)
,tname   varchar2(100) not null  -- 테마명
,constraint PK_final_thema_tnum primary key(tnum)
,constraint UQ_final_thema_tcode unique(tcode)
);

--테마시퀀스
create sequence seq_final_thema
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

-- 테마 삽입 예시
insert into final_thema values(seq_final_thema_tnum.nextval, '10000', '지역');
insert into final_thema values(seq_final_thema_tnum.nextval, '20000', '문화재');
insert into final_thema values(seq_final_thema_tnum.nextval, '30000', '시대');


-- 밴드 플랜
create table final_plan
(pnum           number(5) not null -- 플랜번호
,fk_badmin        varchar2(20) not null --밴드관리자 (final_band의 badmin 참고)
,pdate          date  default sysdate --플랜날짜
,ptitle         clob -- 일정제목
,pcontent       clob -- 글내용
,pimg1          varchar2(100) -- 플랜사진1
,pimg2          varchar2(100) -- 플랜사진2
,pstartdate     date  default sysdate --출발시간
,penddate       date  default sysdate --도착시간
,pmoney         number default 0 -- 예상비용
,constraint FK_final_plan_fk_badmin foreign key(fk_badmin)
              references final_band(badmin)
,constraint PK_final_plan primary key(pnum)  
);

--밴드 플랜 번호
create sequence seq_final_bandplan
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

desc final_thema;
desc final_member;


drop table final_band;

select *
from final_band;