CREATE VIEW BD_BOARD_JOIN_MEMCATELIKE 
AS SELECT bb.BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, bm.MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT
FROM BD_BOARD bb --게시물 테이블
JOIN BD_MEMBER bm ON bb.MEMBER_NUM = bm.MEMBER_NUM --회원 테이블과 조인
JOIN BD_CATEGORY bc ON bb.BOARD_CATEGORY = bc.CATEGORY_NUM --카테고리테이블과 조인
--게시물 번호별 좋아요 수 계산
JOIN (SELECT BOARD_NUM, COUNT(LIKE_NUM) AS LIKE_COUNT FROM BD_LIKE GROUP BY BOARD_NUM) bl ON bb.BOARD_NUM = bl.BOARD_NUM;
--뷰 삭제
DROP VIEW BD_BOARD_JOIN_MEMCATELIKE;

CREATE TABLE BD_BOARD (
	BOARD_NUM			NUMBER								--게시물 넘버 PK
	,BOARD_TITLE		VARCHAR2(500) NOT NULL				--게시물 제목
	,BOARD_CONTENT		VARCHAR2(1000) NOT NULL				--게시물 내용
	,MEMBER_NUM			NUMBER 								--작성자 FK
	,BOARD_WRITE_DAY	DATE DEFAULT SYSDATE				--게시물 작성일시
	,BOARD_VISIBILITY	VARCHAR2(20) DEFAULT '공개' 			--공개 비공개 여부 (공개, 비공개)
	,BOARD_CATEGORY		NUMBER NOT NULL						--카테고리 FK
	,CONSTRAINT PK_BD_BOARD PRIMARY KEY(BOARD_NUM)
	,CONSTRAINT FK_BD_BOARD_MEMBER_NUM FOREIGN KEY(MEMBER_NUM) REFERENCES BD_MEMBER(MEMBER_NUM) ON DELETE SET NULL
	,CONSTRAINT FK_BD_BOARD_CATEGORY FOREIGN KEY(BOARD_CATEGORY) REFERENCES BD_CATEGORY(CATEGORY_NUM) ON DELETE CASCADE
	,CONSTRAINT CHECK_BOARD_VISIBILITY CHECK(BOARD_VISIBILITY IN('공개', '비공개'))
);

CREATE TABLE BD_LIKE (
	LIKE_NUM	NUMBER						--좋아요 PK
	,BOARD_NUM	NUMBER NOT NULL				--게시물 번호 FK
	,MEMBER_NUM	NUMBER						--좋아요 누른 사람 FK
	,CONSTRAINT PK_BD_LIKE PRIMARY KEY(LIKE_NUM)
	,CONSTRAINT FK_BD_LIKE_BOARD_NUM FOREIGN KEY(BOARD_NUM) REFERENCES BD_BOARD(BOARD_NUM) ON DELETE CASCADE 
	,CONSTRAINT FK_BD_LIKE_MEMBER_NUM FOREIGN KEY(MEMBER_NUM) REFERENCES BD_MEMBER(MEMBER_NUM) ON DELETE SET NULL
);

CREATE TABLE BD_IMAGE (
	IMAGE_NUM NUMBER						--이미지 넘버 PK
	,IMAGE_WAY VARCHAR2(500) NOT NULL		--이미지 서버 경로
	,BOARD_NUM NUMBER NOT NULL				--게시물 번호 FK
	,CONSTRAINT PK_BD_IMAGE PRIMARY KEY(IMAGE_NUM)
	,CONSTRAINT FK_BD_IMAGE_BOARD_NUM FOREIGN KEY(BOARD_NUM) REFERENCES BD_BOARD(BOARD_NUM) ON DELETE CASCADE 
);




SELECT * FROM BD_MEMBER;
SELECT * FROM BD_CATEGORY;
SELECT * FROM BD_BOARD;
SELECT * FROM BD_LIKE;
SELECT * FROM BD_IMAGE;
SELECT * FROM BD_REPLY;

INSERT INTO BD_MEMBER bm VALUES (1, 'test', 'test', '테스트', '010-1111-1111', '텟', 'test', '일반',SYSDATE);
INSERT INTO BD_LIKE bl VALUES ((SELECT NVL(MAX(LIKE_NUM)+1,1) FROM BD_LIKE), 1, 1);

--------------category crud
--값 넣기
INSERT INTO BD_CATEGORY(CATEGORY_NUM, CATEGORY_NAME) 
VALUES((SELECT NVL(MAX(CATEGORY_NUM)+1,1) FROM BD_CATEGORY), '문의')

INSERT INTO BD_CATEGORY(CATEGORY_NUM, CATEGORY_NAME) 
VALUES((SELECT NVL(MAX(CATEGORY_NUM)+1,1) FROM BD_CATEGORY), '일반')

--전체조회
SELECT CATEGORY_NUM, CATEGORY_NAME FROM BD_CATEGORY

--카테고리의 PK구하기
SELECT CATEGORY_NUM FROM BD_CATEGORY WHERE CATEGORY_NAME = '문의'

--카테고리 명 변경
UPDATE BD_CATEGORY SET CATEGORY_NAME = '문의' WHERE CATEGORY_NUM = 1

--카테고리 삭제
DELETE FROM BD_CATEGORY WHERE CATEGORY_NUM = 1



--------------board crud
--새 게시글 작성
INSERT INTO BD_BOARD(BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, MEMBER_NUM)
VALUES((SELECT NVL(MAX(BOARD_NUM)+1,1) FROM BD_BOARD), '제목', '내용', '공개', 1, 1)

INSERT INTO BD_BOARD(BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, MEMBER_NUM)
VALUES((SELECT NVL(MAX(BOARD_NUM)+1,1) FROM BD_BOARD), '제목', '내용', '공개', 2, 1)

INSERT INTO BD_BOARD(BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, MEMBER_NUM)
VALUES((SELECT NVL(MAX(BOARD_NUM)+1,1) FROM BD_BOARD), '제목2', '내용', '공개', 1, 1)

INSERT INTO BD_BOARD(BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, MEMBER_NUM)
VALUES((SELECT NVL(MAX(BOARD_NUM)+1,1) FROM BD_BOARD), '제목2-1', '내용', '공개', 2, 1)

--보드 (+멤버, 카테고리, 좋아요) 뷰 생성
--뷰 생성
CREATE VIEW BD_BOARD_JOIN_MEMCATELIKE 
AS SELECT bb.BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, bm.MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT
FROM BD_BOARD bb 
JOIN BD_MEMBER bm ON bb.MEMBER_NUM = bm.MEMBER_NUM
JOIN BD_CATEGORY bc ON bb.BOARD_CATEGORY = bc.CATEGORY_NUM
JOIN (SELECT BOARD_NUM, COUNT(LIKE_NUM) AS LIKE_COUNT FROM BD_LIKE GROUP BY BOARD_NUM) bl ON bb.BOARD_NUM = bl.BOARD_NUM;
--뷰 삭제
DROP VIEW BD_BOARD_JOIN_MEMCATELIKE;

--게시글 상세조회
SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NUM, MEMBER_NICKNAME, LIKE_COUNT 
FROM BD_BOARD_JOIN_MEMCATELIKE
WHERE BOARD_NUM = 1

--게시판 별 글 개수
SELECT NVL(MAX(BOARD_NUM),0) AS B_CNT FROM BD_BOARD bb JOIN BD_CATEGORY bc ON bb.BOARD_CATEGORY = bc.CATEGORY_NUM WHERE CATEGORY_NAME = '문의'

--게시판 별 목록
SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NICKNAME, LIKE_COUNT 
FROM BD_BOARD_JOIN_MEMCATELIKE
WHERE BOARD_CATEGORY = 1

--게시판 별 인기글 3개
SELECT ROWNUM, b.* FROM (
    SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NICKNAME, LIKE_COUNT
    FROM BD_BOARD_JOIN_MEMCATELIKE 
    WHERE CATEGORY_NAME = '문의' AND LIKE_COUNT > 5
    ORDER BY LIKE_COUNT DESC
) b
WHERE ROWNUM <= 3

--검색기능 게시판별 제목 작성자 내용
--제목
SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NICKNAME, LIKE_COUNT 
FROM BD_BOARD_JOIN_MEMCATELIKE 
WHERE CATEGORY_NAME = '문의' AND BOARD_TITLE LIKE '%'||''||'%'

--작성자
SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NICKNAME, LIKE_COUNT 
FROM BD_BOARD_JOIN_MEMCATELIKE 
WHERE CATEGORY_NAME = '문의' AND MEMBER_NICKNAME LIKE '%'||''||'%'

--내용
SELECT BOARD_NUM, BOARD_TITLE, BOARD_CONTENT, BOARD_VISIBILITY, BOARD_CATEGORY, CATEGORY_NAME, MEMBER_NICKNAME, LIKE_COUNT 
FROM BD_BOARD_JOIN_MEMCATELIKE  
WHERE CATEGORY_NAME = '문의' AND BOARD_CONTENT LIKE '%'||''||'%'

--게시글 수정
UPDATE BD_BOARD 
SET BOARD_TITLE = '3'
   ,BOARD_CONTENT = '3'
   ,BOARD_VISIBILITY = '공개'
WHERE BOARD_NUM = 1

--게시글 삭제
DELETE FROM BD_BOARD
WHERE BOARD_NUM = 1

--------------image crud
--이미지 추가
INSERT INTO BD_IMAGE
VALUES ((SELECT NVL(MAX(IMAGE_NUM)+1,1) FROM BD_IMAGE), '경로', 1)

--해당 게시물의 이미지 불러오기
SELECT IMAGE_NUM, IMAGE_WAY 
FROM BD_IMAGE
WHERE BOARD_NUM = 1

--이미지 삭제
DELETE FROM BD_IMAGE WHERE IMAGE_NUM = 1

