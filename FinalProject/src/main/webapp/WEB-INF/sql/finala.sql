-- 밴드 테이블
create table final_band
(bcode     number(5) not null --밴드코드(시퀀스)
,bname     varchar2(100) not null --밴드이름
,badmin    varchar2(20) not null --밴드관리자 (밴드만든멤버아이디, final_member userid 참조)
,bthema    varchar2(20)  not null -- 밴드 테마코드
,binfo    clob -- 밴드정보소개
,bmembercnt   number  default 1 -- 밴드참가인원
,bregdate date  default sysdate --밴드생성일

,constraint  FK_final_band_badmin foreign key(badmin) -- 사용자아이디 참조
              references final_member(userid) 
,constraint PK_final_band primary key(bcode)  
);

ALTER TABLE final_band DROP CONSTRAINT UQ;
          

alter table final_band drop column fileName;
alter table final_band drop column orgFilename;

alter table final_band drop column fileSize;

commit;

alter table final_band
add status number(1) default 1;

update final_band 
set status = 1


commit;


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

--밴드테마테이블
create table final_bandthema
(tnum    number(8)     not null  -- 테마 대분류 번호(시퀀스)
,tcode    varchar2(20)  not null  -- 테마 코드(1000, 2000,3000)
,tname   varchar2(100) not null  -- 테마명
,constraint PK_final_bandthema_tnum primary key(tnum)
,constraint UQ_final_bandthema_tcode unique(tcode)
);

--테마시퀀스
create sequence seq_final_bandthema
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

insert into final_bandthema values(seq_final_bandthema.nextval, '100', '커뮤니티');
insert into final_bandthema values(seq_final_bandthema.nextval, '200', '정보');
insert into final_bandthema values(seq_final_bandthema.nextval, '300', '사진');
insert into final_bandthema values(seq_final_bandthema.nextval, '400', '일기');

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




desc final_thema;
desc final_member
desc final_band;

drop table final_band;
drop table final_thema;
drop SEQUENCE seq_final_thema;
drop table final_member;

select *
from final_band;

select*
from final_bandthema;


create table final_bandimage
(bandimageseq         number    not null       -- 밴드추가이미지 일련번호(Primary Key)
,fk_bcode           number    not null        -- 밴드번호(Foreign Key)
,imagefilename        varchar2(255) not null   -- 이미지파일명. WAS에 저장될 파일명(2016082545435345464367524654634.png)
,imageorgFilename     varchar2(255) not null   -- 진짜 이미지파일명(쉐보레우측.png) // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
,imagefileSize        number                   -- 파일크기
,thumbnailFileName    varchar2(255)            -- WAS에 저장될 썸네일 파일명(2016082513165790354388015717.png). 
                                               -- 썸네일 파일명을 받는 컬럼임. 
,constraint PK_final_bandimage primary key(bandimageseq)
,constraint FK_final_bandimage foreign key(fk_bcode)
                                   references final_band(bcode)
                                   on delete cascade
);


create sequence seq_final_bandimage
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

commit;




commit;

select *
from final_member;

select *
from final_thema;

select *
from final_band;

select *
from final_bandimage;


select *
from final_bandimage
where fk_bcode = 8;

drop table final_band;
drop table final_bandimage;
drop table final_bandthema;
drop table final_thema;

drop SEQUENCE seq_final_band;
drop SEQUENCE seq_final_bandthema;
drop SEQUENCE seq_final_bandplan;
drop SEQUENCE seq_final_bandimage;

commit;

select C.bcode, C.bname, C.badmin, C.bthema, C.binfo, C.bmembercnt, C.thumbnailfilename, D.tname
from
(
select A.bcode, A.bname, A.badmin, A.bthema, A.binfo, A.bmembercnt, B.thumbnailfilename
from final_band A left join (select *
                             from final_bandimage
                             where bandimageseq in (select min(bandimageseq)
                                                     from final_bandimage
                                                     group by fk_bcode)) B
 on A.bcode = B.fk_bcode
) C join (select *
          from final_bandthema)D
on C.bthema = D.tcode
order by 1 desc;          


,imagefilename        varchar2(255) not null   -- 이미지파일명. WAS에 저장될 파일명(2016082545435345464367524654634.png)
,imageorgFilename     varchar2(255) not null   -- 진짜 이미지파일명(쉐보레우측.png) // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
,imagefileSize        number                   -- 파일크기
,thumbnailFileName

select A.bcode, A.bname, A.badmin, A.bthema, A.binfo, A.bmembercnt, A.bregdate,
      B.bandimageseq, B.fk_bcode, B.imagefilename, B.imageorgFilename, B.imagefileSize, B.thumbnailFileName
from final_band A left join final_bandimage B
on  bcode = fk_bcode
where bcode = 17;

select A.bcode, A.bname, A.badmin, A.bthema, A.binfo, A.bmembercnt, A.bregdate,
		      B.bandimageseq, B.fk_bcode, B.imagefilename, B.imageorgFilename, B.imagefileSize, B.thumbnailFileName
		from final_band A left join final_bandimage B
		on  A.bcode = B.fk_bcode
		where A.bcode = '5';
    
    
--------------------페이징처리---------------------------
    
select bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum, tcode, tname
from
(
  select rownum as RNO, C.bcode, C.bname, C.badmin, C.bthema, C.binfo, C.bmembercnt, C.thumbnailfilename, D.tnum, D.tcode, D.tname
  from
	(
    select A.bcode, A.bname, A.badmin, A.bthema, A.binfo, A.bmembercnt, B.thumbnailfilename
    from final_band A left join 
      (select *
        from final_bandimage
        where bandimageseq in (select min(bandimageseq)
	                         from final_bandimage
	                        group by fk_bcode)) B
	 on A.bcode = B.fk_bcode
   where A.status = 1
   order by 1 desc
  ) C join (select *
	          from final_bandthema)D
	on C.bthema = D.tcode
  order by bcode desc  
 )T
where T.RNO >= 1 and T.RNO < 5;

  -----------------
  

order by bcode desc;
  
  select *
  from final_band;
  
  select count(*)
		from final_band
		where badmin like '%'|| 'le' ||'%'
  

create table final_myband
(fk_userid    varchar2(20) not null
,fk_bcode     number(5) not null --밴드코드(시퀀스)

);

select idx, ccbakdcd, ccbactcd, ccbaasno, ccmaname, gcodename, mcodename,ccbalcad, cccename, imageurl, subimage1, subimage2, subimage3
		from 
		(
		select rownum as RNO
		     , V.idx, V.ccbakdcd, V.ccbactcd, V.ccbaasno, V.ccmaname, V.gcodename, V.mcodename, V.ccbalcad, V.cccename, V.imageurl, V.subimage1, V.subimage2, V.subimage3
		from 
		(
		select idx, ccbakdcd, ccbactcd, ccbaasno, ccmaname, gcodename, mcodename,ccbalcad, cccename, imageurl, subimage1, subimage2, subimage3
		from final_culture
		where ccbakdcd = #{ccbakdcd}
		order by idx asc
		) V
		) T
		where T.RNO <![CDATA[>=]]> #{startRno} and T.RNO <![CDATA[<=]]> #{endRno}

-- 밴드 이미지 불러오기
(bandimageseq         number    not null       -- 밴드추가이미지 일련번호(Primary Key)
,fk_bcode           number    not null        -- 밴드번호(Foreign Key)
,imagefilename        varchar2(255) not null   -- 이미지파일명. WAS에 저장될 파일명(2016082545435345464367524654634.png)
,imageorgFilename     varchar2(255) not null   -- 진짜 이미지파일명(쉐보레우측.png) // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명 
,imagefileSize        number                   -- 파일크기
,thumbnailFileName

select bandimageseq, fk_bcode, imagefilename, imageorgFilename, imagefileSize, thumbnailFileName
from final_bandimage
where fk_bcode = 4;

select *
from final_band;

select *
from final_bandimage

alter table final_band
add fileName varchar2(255);
-- WAS(톰캣)에 저장될 파일명(20161121324325454354353333432.png)

alter table final_band
add orgFilename varchar2(255);
-- 진짜 파일명(강아지.png)   // 사용자가 파일을 업로드 하거나 파일을 다운로드 할때 사용되어지는 파일명

alter table final_band
add fileSize number;
-- 파일크기

select *
from final_band;

select *
from final_bandimage

select *
from final_member;


  -- 페이징
  select bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum,tcode, tname
  from
  (
  select  rownum as RNO, bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum,tcode, tname
	from
	(
	  select C.bcode, C.bname, C.badmin, C.bthema, C.binfo, C.bmembercnt, C.thumbnailfilename, D.tnum, D.tcode, D.tname
	  from
		(
	    select A.bcode, A.bname, A.badmin, A.bthema, A.binfo, A.bmembercnt, B.thumbnailfilename
	    from final_band A left join 
	      (select *
	        from final_bandimage
	        where bandimageseq in (select min(bandimageseq)
		                         from final_bandimage
		                        group by fk_bcode)) B
		 on A.bcode = B.fk_bcode
	  ) C join (select *
		          from final_bandthema)D
		on C.bthema = D.tcode
    order by bcode desc 
	 )T
   order by 1 asc 
   )V
   where V.RNO >= 1 and V.RNO < 10
    
   ---------
   select min(bandimageseq), fk_bcode
	from final_bandimage
	group by fk_bcode
  
  ---------------------
  select bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum,tcode, tname
	  from
	  (
	  select  rownum as RNO, bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum,tcode, tname
		from
		(
		  select C.bcode, C.bname, C.badmin, C.bthema, C.binfo, C.bmembercnt, C.thumbnailfilename, D.tnum, D.tcode, D.tname
		  from
			(
		    select A.bcode, A.bname, A.badmin, A.bthema, 
	           case when length(A.binfo) > 20 then substr(A.binfo, 1, 18)||'..'
			            else A.binfo end as binfo
	           , A.bmembercnt, B.thumbnailfilename
	    	from final_band A left join 
		      (select *
		        from final_bandimage
		        where bandimageseq in (select min(bandimageseq)
			                         from final_bandimage
			                        group by fk_bcode)) B
			 on A.bcode = B.fk_bcode
		  ) C join (select *
			          from final_bandthema)D
			on C.bthema = D.tcode
			where bname like '%'|| '테' ||'%'
	    order by bcode desc 
		 )T
	   order by 1 asc 
	   )V
	    where V.RNO >= 1 and V.RNO < 10
  
  
------------
-- 실행
select  bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum,tcode, tname
	  from
	  (
	  select  rownum as RNO, bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum,tcode, tname
		from
		(
		  select C.bcode, C.bname, C.badmin, C.bthema, C.binfo, C.bmembercnt, C.thumbnailfilename, D.tnum, D.tcode, D.tname
		  from
			(
		    select A.bcode, A.bname, A.badmin, A.bthema, 
	           case when length(A.binfo) > 20 then substr(A.binfo, 1, 18)||'..'
			            else A.binfo end as binfo
	           , A.bmembercnt, B.thumbnailfilename
	    	from final_band A left join 
		      (select *
		        from final_bandimage
		        where bandimageseq in (select min(bandimageseq)
			                         from final_bandimage
			                        group by fk_bcode)) B
			 on A.bcode = B.fk_bcode
       where A.status = 0
		  ) C join (select *
			          from final_bandthema)D
			on C.bthema = D.tcode
	    order by bcode desc 
		 )T
	   order by 1 asc 
	   )V
	    where V.RNO >= 1 and V.RNO < 10
      ---------
select *  
from final_band;

select *
from final_bandthema;

select count(*)
from final_bandimage
where fk_bcode =  9

select *
from final_bandimage
where fk_bcode =  21

delete from final_bandimage
where fk_bcode = '4'

	select count(*)
		from final_bandimage
		where fk_bcode =  16
    
    select * from final_bandimage
    
    
    
    select * from final_bandimage where fk_bcode = 16

-- 다음, 뒤로
select V.RNO, bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum,tcode, tname
	  from
	  (
	  select  rownum as RNO, bcode, bname, badmin, bthema, binfo, bmembercnt, thumbnailfilename, tnum,tcode, tname
		from
		(
		  select C.bcode, C.bname, C.badmin, C.bthema, C.binfo, C.bmembercnt, C.thumbnailfilename, D.tnum, D.tcode, D.tname
		  from
			(
		    select A.bcode, A.bname, A.badmin, A.bthema, 
	           case when length(A.binfo) > 20 then substr(A.binfo, 1, 18)||'..'
			            else A.binfo end as binfo
	           , A.bmembercnt, B.thumbnailfilename
	    	from final_band A left join 
		      (select *
		        from final_bandimage
		        where bandimageseq in (select min(bandimageseq)
			                         from final_bandimage
			                        group by fk_bcode)) B
			 on A.bcode = B.fk_bcode
       where A.status = 0
		  ) C join (select *
			          from final_bandthema)D
			on C.bthema = D.tcode
	    order by bcode desc 
		 )T
	   order by 1 asc 
	   )V
	    where V.RNO >= 1 and V.RNO < 10
-------------메인끝---------------------

-- 밴드 계획
create table final_bandplan
(fk_bcode        number(5) not null -- 밴드번호
,pnum           number(5) not null -- 플랜번호
,fk_userid        varchar2(30) not null -- 작성자
,pdate          varchar2(20) not null --플랜날짜
,ptitle         clob not null -- 일정제목
,cultureSearch   varchar2(100) --문화재이름
,pstime         varchar2(30) --출발시간
,petime         varchar2(30) --도착시간
,pmoney         number default 0 -- 예상비용
,pcontent       clob -- 글내용
,pwritedate       date default sysdate -- 글내용
,status     number(1)default 1
,commentCount  number default 0
,constraint FK_final_bandplan_fk_userid foreign key(fk_userid)
              references final_member(userid)
,constraint FK_final_bandplan_fk_bcode foreign key(fk_bcode)
              references final_band(bcode)
,constraint PK_final_bandplan primary key(pnum)  
);

drop table final_bandplan;
ALTER TABLE final_bandplan DROP column pimg;

alter table final_bandplan
add status number(1)default 1;

alter table final_bandplan
add pwritedate  date default sysdate;

alter table final_bandplan
add readCount  number default 0;

ALTER TABLE final_bandplan add column bcode;


update final_bandplan set status = 1

update final_bandplan set readCount = 0
commit;
--밴드 플랜 번호
create sequence seq_final_bandplan
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

desc final_bandplan;

insert into final_bandplan(fk_bcode, pnum, fk_userid, pdate, ptitle, cultureSearch, pstime, petime, pmoney, pcontent)
values(22,seq_final_bandplan.nextval, 'leess', '07/14/2018', '플랜1', '경주 불국사 다보탑', '1:00 AM', '7:30 PM', '50000', 'dugogs f<br>fsdfsdfsfsdf');
    
select rownum as rno, fk_bcode, pnum, fk_userid, pdate, ptitle, cultureSearch, pstime, petime, pmoney, pcontent, pwritedate,status
from final_bandplan
order by rno ;

select *
from final_bandplan;
--------------------------------------
-- 밴드 플랜 페이징
-- 1) 검색어 없는 밴드플랜 페이징
 select rno, fk_bcode, pnum, fk_userid, pdate, ptitle, cultureSearch, pstime, petime, pmoney, pcontent, pwritedate,status, readCount
	  from
	  (
	  select  rownum as RNO, fk_bcode, pnum, fk_userid, pdate, ptitle, cultureSearch, pstime, petime, pmoney, pcontent, pwritedate,status, readCount
		from final_bandplan
    where fk_bcode = 13 and status = 1
    order by pnum desc
		)V
where V.RNO >= 1 and V.RNO <20
order by rno desc;

 --  where V.RNO >= #{startRno} and V.RNO <![CDATA[<=]]> #{endRno}

select  *
from final_bandplan
where fk_bcode = 22 and status = 1

select *
from final_culture
where ccmaname = '부여 정림사지 오층석탑'

update final_bandplan set pcontent = '강화간다'
where pnum = 40;

commit;

--(fk_bcode        number(5) not null -- 밴드번호
--,pnum           number(5) not null -- 플랜번호

-- planCommentVO
-- 댓글테이블
create table final_pcomment
(seq        number              not null   -- 댓글번호
,userid     varchar2(20)         not null   -- 사용자ID
,name       varchar2(20)         not null   -- 성명
,content    varchar2(1000)       not null   -- 댓글내용
,regDate    date default sysdate not null   -- 작성일자
,parentBcode number(5)            not null  -- 밴드 번호
,parentPnum  number(5)            not null  -- 원게시물 글번호
,status     number(1) default 1  not null   -- 글삭제여부
                                            -- 1 : 사용가능한 글,  0 : 삭제된 글
                                            -- 댓글은 원글이 삭제되면 자동적으로 삭제되어야 한다.
,constraint PK_final_pcomment_seq primary key(seq)
,constraint FK_final_pcomment_userid foreign key(userid)
                                    references final_member(userid)
,constraint FK_final_pcomment_parentBcode foreign key(parentBcode) 
                                      references final_band(bcode)
,constraint FK_final_pcomment_parentPnum foreign key(parentPnum) 
                                      references final_bandplan(pnum)
,constraint CK_final_pcomment_status check( status in(1,0) ) 
);



create sequence pcommentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

drop table final_pcomment;

alter table final_bandplan
add commentCount  number default 0      not null;   -- 댓글수


select *
from final_bandplan;

select *
from final_pcomment;

select *
from final_comment;

-- 밴드 플랜 게시판 댓글 신고
create table final_policepcomment(
pnum      number        not null    -- 시퀀스(고유번호)
,fk_seq    number       not null   -- 댓글번호
,police_userid   varchar2(20) not null -- 신고자 아이디
,constraint PK_final_policepcomment_pnum primary key(pnum)
,constraint FK_final_policepcomment_fk_seq foreign key(fk_seq)
                        references final_pcomment(seq) on delete cascade 
);

alter
create sequence police_pcommentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;

-- 메인 자유게시판 댓글 신고
create table final_policecomment(
pnum      number        not null    -- 시퀀스(고유번호)
,fk_seq    number       not null   -- 댓글번호
,police_userid  varchar2(20) not null -- 신고자 아이디
,constraint PK_final_policecomment_pnum primary key(pnum)
,constraint FK_final_policecomment_fk_seq foreign key(fk_seq)
                        references final_comment(seq) on delete cascade
);


create sequence police_commentSeq
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;


drop table final_policecomment;
drop table final_policepcomment;


select *
from final_policepcomment;

select *
from final_policecomment;

select count(*)
from final_policepcomment
where fk_seq = 3 and police_userid = 'leess'

select count(*)
from final_policecomment
where fk_seq = 2 and police_userid = 'leess';

commit;



select *
from final_policepcomment
where fk_seq = 3 and police_userid = 'leess'



