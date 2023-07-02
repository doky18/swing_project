--로그인 가입 테이블
CREATE TABLE logmember(
	member_idx NUMBER PRIMARY KEY
	,id varchar2(20)
	,pass varchar2(64)
	,email varchar2(50)
	,regdate DATE DEFAULT sysdate
);

CREATE SEQUENCE seq_logmember
INCREMENT BY 1
START WITH 1;

--책 기록 테이블 
CREATE TABLE JournalBoard(
	jBoard_idx NUMBER PRIMARY KEY
	,title varchar2(50)
	,content clob
	,regdate DATE DEFAULT sysdate
);

CREATE SEQUENCE seq_journalBoard
INCREMENT BY 1
START WITH 1;

insert into journalboard (jboard_idx, title, content) values ('1', 'osaka', 'still reading');

DELETE FROM JOURNALBOARD;

--책 장르 테이블 
CREATE TABLE genre (
   genre_idx NUMBER PRIMARY KEY
   , genre_name varchar2(30)
);

CREATE SEQUENCE seq_genre
INCREMENT BY 1
START WITH 1;

--카테고리 등록
INSERT INTO genre(genre_idx, genre_name)
values(seq_genre.nextval, '소설');

INSERT INTO genre(genre_idx, genre_name)
values(seq_genre.nextval, '시');

INSERT INTO genre(genre_idx, genre_name)
values(seq_genre.nextval, '에세이');

INSERT INTO genre(genre_idx, genre_name)
values(seq_genre.nextval, '공상과학');

INSERT INTO genre(genre_idx, genre_name)
values(seq_genre.nextval, '역사');

INSERT INTO genre(genre_idx, genre_name)
values(seq_genre.nextval, '취미');


--책에 대한 테이블 
CREATE TABLE book(
	book_idx NUMBER PRIMARY KEY  --얘가 부모 
	,title varchar2(50)
	,author varchar2(20)
	,genre varchar2(20)
	,publisher varchar2(30)
	,note varchar2(64)
	,regdate DATE DEFAULT sysdate
);

CREATE SEQUENCE seq_book
INCREMENT BY 1
START WITH 1;

insert into book(book_idx, title, author, genre, publisher, note) values('1','a','a','a','a','a');

DELETE FROM book;

create TABLE jdiary(
    diary_idx number primary key,
    yy number,
    mm number,
    dd number,
    content varchar2(1000),
    icon varchar2(20)    
);

create  sequence seq_jdiary
increment by 1
start with 1;





